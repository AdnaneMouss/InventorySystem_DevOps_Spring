package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
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
    public Optional<Product> getProduitById(Long id) {
        return produitRepository.findById((Long)id);
    }
    public boolean createProduit(Product produit) {
        boolean res=false;
        try{
            produitRepository.save(produit);
            res=true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return res;
    }
    public Product getProductById(Long id) {
        return produitRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
    public Product updateProduit(int id, Product newProductData, int categoryId) {
        Product existingProduct = getProductById((long)id);
        Optional<Category> categoryOptional = catService.getCategoryById(categoryId);
        Category category = categoryOptional.get();
        existingProduct.setLabel(newProductData.getLabel());
        existingProduct.setDescription(newProductData.getDescription());
        existingProduct.setQuantity(newProductData.getQuantity());
        existingProduct.setPrice(newProductData.getPrice());
        existingProduct.setColor(newProductData.getColor());
        existingProduct.setSize(newProductData.getSize());
        existingProduct.setCategorie(category);
        existingProduct.setPhoto(newProductData.getPhoto());
        return produitRepository.save(existingProduct);
    }
    public Product updateQuantity(int id, int quantity) {
        Product existingProduct = getProductById((long)id);
        Product p = new Product();
        p.setQuantity(quantity);
        existingProduct.setQuantity(p.getQuantity());
        return produitRepository.save(existingProduct);
    }
    public boolean deleteProduit(int id) {
        boolean res;
        if(produitRepository.existsById((long) id)) {
            produitRepository.deleteById((long) id);
            res=true;
        }
        else{
            res=false;
        }
        return res;
    }
}
