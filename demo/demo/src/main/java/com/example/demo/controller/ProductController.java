package com.example.demo.controller;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ComptesService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categorieService;
    @Autowired
    private ComptesService daoComptes;
    @GetMapping("/analytics")
    public String analytics(Model model) {
        //Stock
        List<Product> products= productService.getAllProduits();
        List<String> productNames= new ArrayList<>();
        List<Integer> productQuantity = new ArrayList<>();
        for (Product product : products) {
            productNames.add(product.getLabel());
            productQuantity.add(product.getQuantity());
        }
        model.addAttribute("productNames", productNames);
        model.addAttribute("productQuantity", productQuantity);

        //Most sold product
        Product cheapest = productService.getCheapest();
        String cheapestPhoto = cheapest.getPhoto();
        model.addAttribute("cheapestPhoto", cheapestPhoto);
        System.out.println(cheapestPhoto);

        Product priciest = productService.getPriciest();
        String priciestPhoto = priciest.getPhoto();
        model.addAttribute("priciestPhoto", priciestPhoto);
        System.out.println(cheapestPhoto);
        return "Dashboard_Analytics";
    }



    @GetMapping("/catalogue")
    public String getAllProduits(Model model) {
        List<Product> produits = productService.getAllProduits();
        Map<Integer, Integer> productRatings = new HashMap<>(); // Map to store product ratings
        model.addAttribute("all", produits);
        List<Category> c = categorieService.getAllCategories();
        model.addAttribute("allc", c);
        return "DashProds_admin";
    }
    @GetMapping("/catalogueForAll")
    public String getAllProduitsForAll(Model model) {
        List<Product> produits = productService.getAllProduits();
        model.addAttribute("all", produits);
        List<Category> c = categorieService.getAllCategories();
        model.addAttribute("allc", c);
        return "catalogue";
    }
    @GetMapping("/viewdetails")
    public String details(@RequestParam int id) {
        return "redirect:/products/singleproduct/" + id;
    }
    @PostMapping("/addProduct")
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
        return "DashProds_admin";
    }
    @PostMapping("/modify")
    public String modifyProduct(@ModelAttribute Product product,@RequestParam int categoryid) {
        productService.updateProduit(product.getId(), product, categoryid);
        return "DashProds_admin";
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
        return "DashProds_admin";
    }
}
