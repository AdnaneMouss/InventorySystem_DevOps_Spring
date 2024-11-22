package com.example.demo.controller;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ComptesService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductsEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Controller
@RequestMapping("/product")
public class ProductsEmployeeController {
    @Autowired
    private ProductsEmployeeService productService;
    @Autowired
    private CategoryService categorieService;
    @Autowired
    private ComptesService daoComptes;
    @GetMapping("/analyticss")
    public String analytics(Model model) {
        List<Product> c = productService.getAllProduits();
        System.out.println(c);
        model.addAttribute("product", c);
        return "Dashboard_Analytics";
    }
    @GetMapping("/cataloguee")
    public String getAllProduits(Model model) {
        List<Product> produits = productService.getAllProduits();
        Map<Integer, Integer> productRatings = new HashMap<>(); // Map to store product ratings
        model.addAttribute("all", produits);
        List<Category> c = categorieService.getAllCategories();
        model.addAttribute("allc", c);
        return "DashProds_employe";
    }
    @GetMapping("/cataloguesForAll")
    public String getAllProduitsForAll(Model model) {
        List<Product> produits = productService.getAllProduits();
        model.addAttribute("all", produits);
        List<Category> c = categorieService.getAllCategories();
        model.addAttribute("allc", c);
        return "cataloguee";
    }
    @GetMapping("/viewdetail")
    public String details(@RequestParam int id) {
        return "redirect:/products/singleproduct/" + id;
    }
    @PostMapping("/addProducts")
    public String addProduct(@RequestParam String label, @RequestParam int price, @RequestParam String color,
                             @RequestParam String photo, @RequestParam String size, @RequestParam int categoryId, @RequestParam int stock,@RequestParam String description) {
        Optional<Category> categoryOptional = categorieService.getCategoryById(categoryId);
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
        return "DashProds_employe";
    }
    @PostMapping("/modifyy")
    public String modifyProduct(@ModelAttribute Product product, @RequestParam int categoryid) {
        productService.updateProduit(product.getId(), product, categoryid);
        return "DashProds_employe";
    }
}
