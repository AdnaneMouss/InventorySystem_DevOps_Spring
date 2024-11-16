package com.example.demo.service;
import com.example.demo.model.User;
import com.example.demo.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class ComptesService {
    public ComptesService(){}

    public ComptesService(CompteRepository compteRepository){
        this.compteRepository=compteRepository;
    }
    @Autowired
    private CompteRepository compteRepository;

    public List<User> getAllAccs() {
        return compteRepository.findAll();
    }
    public User findByUsername(String username){
        return compteRepository.findByUsername(username);
    }
    public Optional<User> getAccById(Long id) {
        return compteRepository.findById(id);
    }
    public User getAccountById(Long id) {
        return compteRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
    public User getAccountByusername(String username) {
        return compteRepository.findByUsername(username);
    }
    public List<User> findByType(String type){
        return compteRepository.findAllByType(type);
    }
}
