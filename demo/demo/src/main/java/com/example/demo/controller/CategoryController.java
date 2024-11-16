package com.example.demo.controller;
import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService catService;
    @Autowired
    private ProductService produitService;

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable int id) {
        Optional<Category> c = catService.getCategoryById(id);
        return c.orElse(null);
    }
    @GetMapping("/categories")
    public String getAllCategories(Model model) {
        List<Category> c = catService.getAllCategories();
        model.addAttribute("allc", c);
        return "dashboard_categories";
    }
    @PostMapping("/addCategory")
    public String addCategory(@RequestParam String catname, Model model) {
        Category categorie = new Category();
        categorie.setCatname(catname);
        boolean isCategoryCreated = catService.createCategorie(categorie);
        if (isCategoryCreated) {
            return "redirect:/categories/categories";
        } else {
            System.out.println("exists already");
            model.addAttribute("errorMessage", "Category already exists!");
        }
        return "redirect:/categories/categories";
    }
    @PostMapping("/delete")
    public String delete(@RequestParam int idC) {
        boolean isDeleted = catService.deleteCategorie(idC);
        if (isDeleted) {
            System.out.println("categ deleted successfully.");
        } else {
            System.out.println("Failed to delete categ.");
        }
        return "redirect:/categories/categories";
    }
}
