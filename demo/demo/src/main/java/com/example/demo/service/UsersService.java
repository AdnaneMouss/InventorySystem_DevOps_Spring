package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final CompteRepository compteRepository;  // Le nom de la variable doit être cohérent

    @Autowired
    public UsersService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    // Méthode pour authentifier un utilisateur
    public User authenticate(String username, String password) {
        return compteRepository.findByEmailAndPassword(username, password);  // Rechercher un utilisateur par email et mot de passe
    }

    // Récupérer un utilisateur par son nom d'utilisateur
    public User trouverParNomUtilisateur(String username) {
        return compteRepository.findByUsername(username);  // Trouver un utilisateur par son nom d'utilisateur
    }

    // Récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return compteRepository.findAll();  // Retourner tous les utilisateurs
    }

    // Récupérer un utilisateur par son ID
    public User getUserById(int id) {
        return compteRepository.findById((long) id).orElse(null);  // Recherche par ID, conversion en Long nécessaire
    }

    // Sauvegarder ou mettre à jour un utilisateur
    public User save(User user) {
        return compteRepository.save(user);  // Utilise la méthode save du repository pour sauvegarder ou mettre à jour l'utilisateur
    }

    // Supprimer un utilisateur par ID
    public void deleteUser(int id) {
        compteRepository.deleteById((long) id);  // Supprimer un utilisateur en utilisant son ID, conversion en Long
    }
}
