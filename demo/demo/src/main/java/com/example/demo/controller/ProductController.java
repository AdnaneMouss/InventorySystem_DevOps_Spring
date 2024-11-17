package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    public ProductService productService;
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
}
