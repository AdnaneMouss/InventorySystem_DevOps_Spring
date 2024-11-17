package com.example.demo.controller;
import com.example.demo.model.User;
import com.example.demo.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/log")
public class UsersController {
    @Autowired
    private UsersService userService;
    @GetMapping("/login")
    public String authenticateUser(String email, String password, Model model, HttpSession session) {
        User compte = userService.authenticate(email, password);
        if (compte != null) {
            model.addAttribute("this",compte);
            session.setAttribute("authenticatedname", compte.getUsername());
            session.setAttribute("authenticatedId", compte.getId());
            System.out.println(session);
            model.addAttribute("authenticatedUser", compte);
            if (compte.getType().equalsIgnoreCase("employe")) {
                return "redirect:/products/products";
            } else if (compte.getType().equalsIgnoreCase("admin")) {
                return "redirect:/products/catalogue";
            } else if (compte.getType().equalsIgnoreCase("fournisseur")) {
                return "" ;
            }
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "loginPage";
        }
        return "loginPage";
    }
    @GetMapping("/apropos")
    public String apropos() {
        return "aboutus";
    }
    @GetMapping("/home")
    public String home() {
        return "homePage";
    }
}
