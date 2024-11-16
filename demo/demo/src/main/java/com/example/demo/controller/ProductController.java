package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    public ProductService productService;
    @GetMapping("/analytics")
    public String analytics(Model model) {
int c = productService.getAllProduits();
System.out.println(c);
        model.addAttribute("product", c);
        return "Dashboard_Analytics";
    }
}
