package com.example.demo.service;

import com.example.demo.model.Command;
import com.example.demo.model.Product;
import com.example.demo.repository.CommandRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommandService {

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private ProductRepository productRepository;

    public int countAll(){
        return (int) commandRepository.count();
    }

    public int countDelivered(boolean delivered){
        return commandRepository.countByDelivered(delivered);
    }

    public int countCommandsPerMonth(String month) {
        int commandCount = 0;
        List<Command> commands = commandRepository.findAll(); // Assume this method fetches all commands
        for (Command command : commands) {
            try {
                LocalDateTime creationDate = command.getCreationDate();
                if (creationDate == null) {
                    // Skip this command and continue with the next one
                    continue;
                }
                // Extract the month from the creation date
                int commandMonth = creationDate.getMonthValue();
                if (commandMonth == Integer.parseInt(month)) {
                    // Count the command if it was created in the given month
                    commandCount++;
                }
            } catch (Exception e) {
                // Handle any other exceptions if necessary
                e.printStackTrace();
            }
        }
        return commandCount;
    }



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
    public Command updateCommand(int id, Command newCommandData, int idcommand) {
        // Récupérer la commande existante par son identifiant
        Command existingCommand = getCommandById(id);

        // Mettre à jour les champs de la commande
        existingCommand.setUser(newCommandData.getUser());
        existingCommand.setCreationDate(newCommandData.getCreationDate());
        existingCommand.setProduct(newCommandData.getProduct());
        existingCommand.setQuantity(newCommandData.getQuantity());
        existingCommand.setDeliveryDate(newCommandData.getDeliveryDate());
        existingCommand.setDelivered(newCommandData.isDelivered());

        // Sauvegarder les modifications dans le repository
        return commandRepository.save(existingCommand);
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
            //product.setCommand(command); // Associe le produit à la commande
            productRepository.save(product); // Sauvegarde le produit dans le repository
            //command.getProducts().add(product); // Ajoute le produit à la liste des produits de la commande
            return commandRepository.save(command); // Sauvegarde la commande mise à jour
        }
        return null;
    }

    public List<Command> getSupplierCommands(int id){
        return commandRepository.findAllByUserId(id);
    }

    public void updateCommand(Command command) {
            commandRepository.save(command);
    }
}
