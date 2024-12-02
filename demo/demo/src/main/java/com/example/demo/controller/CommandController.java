package com.example.demo.controller;

import com.example.demo.model.Command;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/commands")
public class CommandController {

    @Autowired
    private CommandService commandService;

    // Récupérer toutes les commandes
    @GetMapping
    public List<Command> getAllCommands() {
        return commandService.getAllCommands();
    }

    // Récupérer une commande par ID
    @GetMapping("/{id}")
    public ResponseEntity<Command> getCommandById(@PathVariable int id) {
        Command command = commandService.getCommandById(id);
        return command != null ? ResponseEntity.ok(command) : ResponseEntity.notFound().build();
    }

    // Créer une nouvelle commande
    @PostMapping
    public ResponseEntity<Command> createCommand(@RequestBody Command command) {
        Command createdCommand = commandService.createCommand(command);
        return ResponseEntity.ok(createdCommand);
    }

    // Mettre à jour une commande
    @PutMapping("/{id}")
    public ResponseEntity<Command> updateCommand(@PathVariable int id, @RequestBody Command command) {
        Command updatedCommand = commandService.updateCommand(id, command);
        return updatedCommand != null ? ResponseEntity.ok(updatedCommand) : ResponseEntity.notFound().build();
    }

    // Supprimer une commande
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommand(@PathVariable int id) {
        boolean deleted = commandService.deleteCommand(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Ajouter un produit à une commande
    @PostMapping("/{id}/products")
    public ResponseEntity<Command> addProductToCommand(@PathVariable int id, @RequestBody Product product) {
        Command updatedCommand = commandService.addProductToCommand(id, product);
        return updatedCommand != null ? ResponseEntity.ok(updatedCommand) : ResponseEntity.notFound().build();
    }

    @GetMapping("/supplier/{id}")
    public String getSuppCommands(Model model, @PathVariable("id") int id){
        List<Command> list = commandService.getSupplierCommands(id);
        model.addAttribute("commands",list);
        System.out.println(list.size());
        return "Dashboard_Supplier";
    }
    @PostMapping("/supplier/delivered")
    public String updateDeliveredStatus(@RequestParam int commandId) {
        Command command = commandService.getCommandById(commandId); // Retrieve the command by ID
        if (command != null) {
            command.setDelivered(true); // Set delivered status to true
            commandService.updateeCommand(command); // Save updated command
        }
        return "redirect:/commands/supplier/" + command.getUser().getId(); // Redirect to the supplier's page or wherever needed
    }
}
