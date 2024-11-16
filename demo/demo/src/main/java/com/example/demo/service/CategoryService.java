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

    public List<Category> getAllCategories() {
        return categRepository.findAll();
    }
    public Optional<Category> getCategoryById(int id) {
        return categRepository.findById((long) id);
    }
    public boolean createCategorie(Category categorie) {
        boolean categoryExists = categRepository.existsByCatname(categorie.getCatname());
        if (categoryExists) {
            return false;
        }
        try {
            categRepository.save(categorie);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    public boolean deleteCategorie(int id) {
        boolean res = false;
        try {
            if(categRepository.existsById((long) id)) {
                categRepository.deleteById((long) id);
                res=true;
            }
            else{
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public Category findCategorieByName(String name){
        return categRepository.findCategorieByCatname(name);
    }
}
