package com.example.demo.controller;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.CategoryService;
import com.example.demo.service.CommandService;
import com.example.demo.service.ComptesService;
import com.example.demo.service.ProductService;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categorieService;
    @Autowired
    private CommandService commandService;
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

        //Cheapest product
        Product cheapest = productService.getCheapest();
        String cheapestPhoto = cheapest.getPhoto();
        model.addAttribute("cheapestPhoto", cheapestPhoto);

        //Priciest product
        Product priciest = productService.getPriciest();
        String priciestPhoto = priciest.getPhoto();
        model.addAttribute("priciestPhoto", priciestPhoto);

        //Number of users per type
        int numberOfEmployees = productService.countByType("Employee");
        int numberOfAdmins = productService.countByType("Admin");
        int numberOfSuppliers = productService.countByType("Supplier");

        model.addAttribute("numberOfEmployees", numberOfEmployees);
        model.addAttribute("numberOfSuppliers", numberOfSuppliers);
        model.addAttribute("numberOfAdmins", numberOfAdmins);

        //Expired products
        List<Product> expired = productService.getExpiredProducts();
        List<String> expiredImages = expired.stream()
                .map(Product::getPhoto)
                .collect(Collectors.toList());
        model.addAttribute("expired", expired);
        model.addAttribute("expiredImages", expiredImages);
        int numberOfProducts = productService.countAll();
        model.addAttribute("numberOfProducts",numberOfProducts);
        //Number of commands
        int numberOfCommands = commandService.countAll();
        model.addAttribute("numberOfCommands", numberOfCommands);

        int delivered = commandService.countDelivered(true);
        int notDelivered = commandService.countDelivered(false);
        model.addAttribute("delivered", delivered);
        model.addAttribute("notDelivered", notDelivered);
        model.addAttribute("january", commandService.countCommandsPerMonth("01"));
        model.addAttribute("february", commandService.countCommandsPerMonth("02"));
        model.addAttribute("march", commandService.countCommandsPerMonth("03"));
        model.addAttribute("april", commandService.countCommandsPerMonth("04"));
        model.addAttribute("may", commandService.countCommandsPerMonth("05"));
        model.addAttribute("june", commandService.countCommandsPerMonth("06"));
        model.addAttribute("july", commandService.countCommandsPerMonth("07"));
        model.addAttribute("august", commandService.countCommandsPerMonth("08"));
        model.addAttribute("september", commandService.countCommandsPerMonth("09"));
        model.addAttribute("october", commandService.countCommandsPerMonth("10"));
        model.addAttribute("november", commandService.countCommandsPerMonth("11"));
        model.addAttribute("december", commandService.countCommandsPerMonth("12"));
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
                             @RequestParam String photo, @RequestParam String size, @RequestParam int categoryId,@RequestParam String expiryDate,@RequestParam String productionDate, @RequestParam int stock,@RequestParam String description) {
        // Fetch the category object by ID
        Optional<Category> categoryOptional = categorieService.getCategoryById(categoryId);

        Category category = categoryOptional.get();

        Product product = new Product();
        product.setLabel(label);
        product.setProductionDate(productionDate);
        product.setExpiryDate(expiryDate);
        product.setPrice(price);
        product.setColor(color);
        product.setPhoto(photo);
        product.setSize(size);
        product.setQuantity(stock);
        product.setDescription(description);
        product.setCategorie(category);

        // Save the product to the database
        productService.createProduit(product);

        // Redirect to the desired page
        return "redirect:/products/catalogue";
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
