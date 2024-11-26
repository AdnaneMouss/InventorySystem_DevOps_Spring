package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService catService;

    // Méthode pour afficher toutes les catégories disponibles
    @GetMapping("/categories")
    public String getAllCategories(Model model) {
        List<Category> categories = catService.getAllCategories();  // Récupération des catégories
        model.addAttribute("allc", categories);                      // Envoi des catégories à la vue
        model.addAttribute("errorMessage", "");                      // Initialisation du message d'erreur
        return "dashboard_categories";                               // Nom du template HTML
    }

    // Méthode pour ajouter une nouvelle catégorie
    @PostMapping("/addCategory")
    public String addCategory(@RequestParam String catname, Model model) {
        Category categorie = new Category();
        categorie.setCatname(catname);

        // Tentative de création de la catégorie
        boolean isCategoryCreated = catService.createCategorie(categorie);

        if (isCategoryCreated) {
            return "redirect:/categories/categories";  // Redirige vers la liste des catégories
        } else {
            model.addAttribute("errorMessage", "La catégorie existe déjà !");  // Affiche un message d'erreur
        }
        return "redirect:/categories/categories";  // Redirige vers la même page avec un message d'erreur
    }

    // Méthode pour supprimer une catégorie
    @PostMapping("/delete")
    public String delete(@RequestParam int idC) {
        boolean isDeleted = catService.deleteCategorie(idC);
        if (isDeleted) {
            System.out.println("Catégorie supprimée avec succès.");
        } else {
            System.out.println("Échec de la suppression de la catégorie.");
        }
        return "redirect:/categories/categories";  // Redirige vers la liste des catégories
    }
}
