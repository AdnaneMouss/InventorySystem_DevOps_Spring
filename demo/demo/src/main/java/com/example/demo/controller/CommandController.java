package com.example.demo.controller;

import com.example.demo.model.Command;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.CommandService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/commands")
public class CommandController {


    @Autowired
    private CommandService commandService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UsersService usersService;

    // Récupérer toutes les commandes
    @GetMapping("/allcommand")
    public String getAllCommands(Model model) {
        List<Command> commands = commandService.getAllCommands();

        // Calculer la somme des prix pour chaque commande
        for (Command command : commands) {
            double totalPrice = 0.0;

            // Calcul de la somme des prix des produits
            for (Product product : command.getProduct()) {
                totalPrice += product.getPrice(); // Assurez-vous que getPrice() existe dans la classe Product
            }

            // Ajouter la somme au modèle pour chaque commande
            model.addAttribute("totalPrice", totalPrice);
        }

        model.addAttribute("allCommands", commands);
        return "DashCommand_admin";
    }



    // Récupérer une commande par ID
    @GetMapping("/{id}")
    public String getCommandById(@PathVariable int id, Model model) {
        try {
            // Récupérer la commande par son ID via le service
            Command command = commandService.getCommandById(id);

            // Vérifier si la commande existe
            if (command != null) {
                model.addAttribute("command", command);
            } else {
                model.addAttribute("error", "Command with ID " + id + " not found.");
            }
        } catch (Exception e) {
            // Gérer les erreurs et ajouter un message au modèle
            model.addAttribute("error", "An error occurred while retrieving the command: " + e.getMessage());
        }

        // Retourner la vue DashCommands_admin
        return "DashCommands_admin";
    }


    // Créer une nouvelle commande
    @PostMapping("/addcommand")
    public String createCommand(@RequestParam int userId, @RequestParam int productId,
                                @RequestParam int quantity, @RequestParam String deliveryDate) {
        // Récupérer l'utilisateur par son ID
        User user = usersService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Product product = productService.getProductById((long) productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }


        // Vérifier si la quantité demandée est disponible
        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for product ID: " + productId);
        }

        // Créer une nouvelle commande
        Command command = new Command();
        command.setUser(user);
        command.setProduct((List<Product>) product);
        command.setQuantity(quantity);
        command.setDeliveryDate(deliveryDate);
        command.setCreationDate(LocalDateTime.now());
        command.setDelivered(false); // La commande est non livrée par défaut

        // Mettre à jour le stock du produit
        productService.updateQuantity(productId, product.getQuantity() - quantity);

        // Enregistrer la commande
        commandService.createCommand(command);

        return "DashCommand_admin";
    }



    // Mettre à jour une commande
    @PutMapping("/commandmodif")
    public  String updateCommand(@ModelAttribute Command command, @RequestParam int idcommand) {
        commandService.updateCommand(command.getId(),command, idcommand);
        return "DashCommand_admin";
    }

    // Supprimer une commande
    @PostMapping("/deleteCommand")
    public String deleteCommand(@RequestParam int id, Model model) {
        boolean isDeleted = false;
        try {
            // Appeler le service pour supprimer la commande
            isDeleted = commandService.deleteCommand(id);
            model.addAttribute("deleted", isDeleted);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while deleting the command.");
        }
        return "DashCommands_admin"; // Vue pour gérer les commandes
    }


    // Ajouter un produit à une commande
    @PostMapping("/{id}/products")
    public String addProductToCommand(@PathVariable int id, @RequestBody Product product, Model model) {
        try {
            // Ajouter le produit à la commande via le service
            Command updatedCommand = commandService.addProductToCommand(id, product);

            // Vérifier si la commande a été mise à jour
            if (updatedCommand != null) {
                model.addAttribute("updatedCommand", updatedCommand);
                model.addAttribute("success", "Product added successfully to the command!");
            } else {
                model.addAttribute("error", "Command with ID " + id + " not found or update failed.");
            }
        } catch (Exception e) {
            // Gérer les erreurs et ajouter un message au modèle
            model.addAttribute("error", "An error occurred while adding the product: " + e.getMessage());
        }

        // Retourner la vue DashCommands_admin
        return "DashCommands_admin";
    }
    @PostMapping("/deliver/{id}")
    public String markAsDelivered(@PathVariable int id) {
        Command command = commandService.getCommandById(id);
        if (command != null && !command.isDelivered()) {
            command.setDelivered(true);
            commandService.updateCommand(id, command, command.getId());
        }
        return "redirect:/commands/allcommand";
    }
    @GetMapping("/commands/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Command command = commandService.getCommandById(id);
        model.addAttribute("command", command);
        model.addAttribute("allProducts", productService.getAllProduits());
        return "edit-command";
    }


}
