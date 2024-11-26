package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/login")
    public String authenticateUser(String email, String password, Model model) {
        User compte = usersService.authenticate(email, password);  // Authentification de l'utilisateur
        if (compte != null) {
            model.addAttribute("authenticatedUser", compte);
            return "redirect:/dashboard";  // Redirection vers le tableau de bord
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "loginPage";
        }
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        List<User> users = usersService.getAllUsers();
        model.addAttribute("users", users);
        return "Dashboard_GestionUtilisateurs";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());  // Créer un nouvel utilisateur vide pour le formulaire
        return "addOrEditUser";  // Afficher le formulaire d'ajout
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user) {
        usersService.save(user);  // Sauvegarder l'utilisateur
        return "redirect:/users/dashboard";  // Redirection vers le tableau de bord après l'enregistrement
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        User user = usersService.getUserById(id);
        model.addAttribute("user", user);
        return "addOrEditUser";  // Afficher le formulaire de modification
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        usersService.deleteUser(id);  // Supprimer l'utilisateur
        return "redirect:/users/dashboard";  // Redirection vers le tableau de bord après suppression
    }
}
