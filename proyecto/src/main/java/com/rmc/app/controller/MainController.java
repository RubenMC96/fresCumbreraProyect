package com.rmc.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
     @GetMapping({"/","/inicio"})
    public String showInicio(){
        return "Generales/indexView";
    }
}
