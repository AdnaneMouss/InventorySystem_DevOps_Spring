package com.example.demo.controller;
import com.example.demo.model.User;
import com.example.demo.service.ComptesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/comptes")
public class ComptesController {
    @Autowired
    private ComptesService daoComptes;
    @Autowired
    private ComptesService comptesService;
    public ComptesController(ComptesService daoComptes) {
        this.daoComptes = daoComptes;
    }
    @GetMapping
    public  List<User> getAllAccs() {return daoComptes.getAllAccs();
    }
    @GetMapping("/{id}")
    public User getComptesById(@PathVariable int id) {
        Optional<User> comptes = daoComptes.getAccById((long)id);
        return comptes.orElse(null);
    }
}
