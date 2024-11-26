package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categRepository;

    // Récupérer toutes les catégories
    public List<Category> getAllCategories() {
        return categRepository.findAll();
    }

    // Récupérer une catégorie par son ID
    public Optional<Category> getCategoryById(int id) {
        return categRepository.findById((long) id);
    }

    // Créer une catégorie si elle n'existe pas déjà
    public boolean createCategorie(Category categorie) {
        boolean categoryExists = categRepository.existsByCatname(categorie.getCatname());
        if (categoryExists) {
            return false;  // La catégorie existe déjà
        }
        try {
            categRepository.save(categorie);  // Sauvegarde la catégorie
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Supprimer une catégorie
    public boolean deleteCategorie(int id) {
        boolean res = false;
        try {
            if (categRepository.existsById((long) id)) {
                categRepository.deleteById((long) id);  // Suppression de la catégorie
                res = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
}
