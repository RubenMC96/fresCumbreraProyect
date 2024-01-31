package com.rmc.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rmc.app.domain.Usuario;
import com.rmc.app.service.UsuarioService;

import jakarta.validation.Valid;




@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    public UsuarioService usuarioService;


    @GetMapping({"/", "/list"})
    public String showList(Model model){
        model.addAttribute("listaUsuarios", usuarioService.obtenerLista());
        return "UsuarioView/ListUsuView";
    }

    @GetMapping("/nuevo")
    public String showNuevo(Model model){
        model.addAttribute("usuarioForm", new Usuario());
        return "UsuarioView/UsuFormNew";
    }

    @PostMapping("/nuevo/submit")
    public String showNuevoSubmit(@Valid Usuario usuarioForm,
        BindingResult bindingResult){
            if(bindingResult.hasErrors())
                return "redirect:/usuario/nuevo";
            usuarioService.añadir(usuarioForm);
                return "redirect:/usuario/list";
        }

    @GetMapping("/editar/{id}")
    public String ShowEdit(@PathVariable long id, Model model){
        Usuario usuario = usuarioService.obtenerPorId(id);
        if(usuario != null){
            model.addAttribute("usuarioForm", usuario);
            return "UsuarioView/UsuFormEdit";
        }
        return null;

    }

    @PostMapping("/editar/submit")
    public String showEditSubmir(@Valid Usuario usuarioForm,
    BindingResult bindingResult){
            if(bindingResult.hasErrors())
                return "redirect:/usuario/editar/{id}";
            usuarioService.añadir(usuarioForm);
                return "redirect:/usuario/list";
    }

    @GetMapping("/borrar/{id}")
    public String showDelete(@PathVariable long id){
        usuarioService.borrar(id);
        return "redirect:/usuario/list";
    }
}
