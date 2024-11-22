package com.example.demo.service;

import com.example.demo.model.Command;
import com.example.demo.model.Product;
import com.example.demo.repository.CommandRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandService {

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private ProductRepository productRepository;

    // Récupérer toutes les commandes
    public List<Command> getAllCommands() {
        return commandRepository.findAll();
    }

    // Récupérer une commande par ID
    public Command getCommandById(int id) {
        return commandRepository.findById(id).orElse(null);
    }

    // Créer une nouvelle commande
    public Command createCommand(Command command) {
        return commandRepository.save(command);
    }

    // Mettre à jour une commande
    public Command updateCommand(int id, Command updatedCommand) {
        Optional<Command> optionalCommand = commandRepository.findById(id);
        if (optionalCommand.isPresent()) {
            Command existingCommand = optionalCommand.get();
            existingCommand.setUser(updatedCommand.getUser());
            existingCommand.setProducts(updatedCommand.getProducts());
            return commandRepository.save(existingCommand);
        }
        return null;
    }

    // Supprimer une commande
    public boolean deleteCommand(int id) {
        if (commandRepository.existsById(id)) {
            commandRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Ajouter un produit à une commande
    public Command addProductToCommand(int commandId, Product product) {
        Optional<Command> optionalCommand = commandRepository.findById(commandId);
        if (optionalCommand.isPresent()) {
            Command command = optionalCommand.get();
            product.setCommand(command); // Associe le produit à la commande
            productRepository.save(product); // Sauvegarde le produit dans le repository
            command.getProducts().add(product); // Ajoute le produit à la liste des produits de la commande
            return commandRepository.save(command); // Sauvegarde la commande mise à jour
        }
        return null;
    }
}
