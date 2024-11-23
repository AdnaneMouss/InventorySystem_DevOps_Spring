package com.example.demo.service;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CompteRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private CategoryRepository categorieRepository;

    public ProductService() {}

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService catService;
    @Autowired
    private CompteRepository compteRepository;

    public List<Product> getAllProduits() {
        return productRepository.findAll();
    }

    public int countAll(){
        return (int) productRepository.count();
    }
    public Optional<Product> getProduitById(Long id) {
        return productRepository.findById((Long)id);
    }

    public boolean createProduit(Product produit) {
        boolean res=false;
        try{
            productRepository.save(produit);
            res=true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return res;
    }


    public  Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
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
        return productRepository.save(existingProduct);
    }
    public Product updateQuantity(int id, int quantity) {
        Product existingProduct = getProductById((long)id);
        Product p = new Product();
        p.setQuantity(quantity);
        existingProduct.setQuantity(p.getQuantity());
        return productRepository.save(existingProduct);
    }
    public boolean deleteProduit(int id) {
        boolean res;
        if(productRepository.existsById((long) id)) {
            productRepository.deleteById((long) id);
            res=true;
        }
        else{

            res=false;
        }
        return res;
    }
    public Product getCheapest(){
        return productRepository.findCheapestProduct();
    }
    public Product getPriciest() {
        return productRepository.findPriciestProduct();
    }

    public int countByType(String type){
        return compteRepository.countAllByType(type);
    }

    public Map<String, Long> getProductCountsByCategory() {
        List<Object[]> results = productRepository.countProductsByCategory();
        Map<String, Long> counts = new HashMap<>();
        for (Object[] result : results) {
            counts.put((String) result[0], (Long) result[1]);
        }
        return counts;
    }

    public List<Product> getExpiredProducts() {
        LocalDate today = LocalDate.now(); // Get today's date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Ensure consistent format

        return productRepository.findAll().stream()
                .filter(product -> {
                    try {
                        LocalDate expiryDate = LocalDate.parse(product.getExpiryDate(), formatter);
                        return expiryDate.isBefore(today); // Check if expired
                    } catch (Exception e) {
                        // Log or handle invalid date formats
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }
    }


