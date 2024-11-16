package com.example.demo.controller;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    public ProductService productService;
    @Autowired
    private CategoryService catService;
    @Autowired
    private CategoryService categorieService;
    @GetMapping("/analytics")
    public String analytics(Model model) {
    int c = productService.getAllProduits();
    System.out.println(c);
    model.addAttribute("product", c);
    return "Dashboard_Analytics";
    }
    @GetMapping("/catalogue")
    public String getAllProduits(Model model) {
        int produits = productService.getAllProduits();
        model.addAttribute("all", produits);
        List<Category> c = catService.getAllCategories();
        model.addAttribute("allc", c);
        return "shop";
    }
    @GetMapping("/viewdetails")
    public String details(@RequestParam int id) {
        return "redirect:/products/singleproduct/" + id;
    }
    @PostMapping("/addProduct")
    public String addProduct(@RequestParam String label, @RequestParam int price, @RequestParam String color,
                             @RequestParam String photo, @RequestParam String size, @RequestParam int categoryId, @RequestParam int stock,@RequestParam String description) {
        Optional<Category> categoryOptional = catService.getCategoryById(categoryId);
        Category category = categoryOptional.get();
        Product product = new Product();
        product.setLabel(label);
        product.setPrice(price);
        product.setColor(color);
        product.setPhoto(photo);
        product.setSize(size);
        product.setQuantity(stock);
        product.setDescription(description);
        product.setCategorie(category);
        productService.createProduit(product);
        return "redirect:/products/products";
    }
    @PostMapping("/modify")
    public String modifyProduct(@ModelAttribute Product product,@RequestParam int categoryid) {
        productService.updateProduit(product.getId(), product, categoryid);
        return "redirect:/products/products";
    }
    @PostMapping("/delete")
    public String delete(@RequestParam int id,Model model) {
        boolean isDeleted=false;
        try {
            isDeleted = productService.deleteProduit(id);
            model.addAttribute("deleted", isDeleted);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/products/products";
    }
}
