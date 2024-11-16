package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private CategoryRepository categorieRepository;
    @Autowired
    private ProductRepository produitRepository;
    @Autowired
    private CategoryService catService;
    public ProductService(ProductRepository produitRepository) {
        this.produitRepository = produitRepository;
    }
    public ProductService() {}
    public int getAllProduits() {
        return (int) produitRepository.count();
    }
}
