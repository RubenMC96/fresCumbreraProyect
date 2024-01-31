package com.rmc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmc.app.Repositories.UsuarioRepository;
import com.rmc.app.domain.Usuario;

@Service
public class UsuarioServiceImp implements UsuarioService{
    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario añadir(Usuario usuario) {
        
        return usuarioRepository.save(usuario); 
    }
    public void borrar(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if(usuario != null){
            usuarioRepository.delete(usuario);
        }
    }
     public Usuario editar(Usuario usuario ) {
        return usuarioRepository.save(usuario);
    }
    public Usuario obtenerPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if(usuario != null){
            return usuario;
        }
        else return null;

    }
    public List<Usuario> obtenerLista(){

        return usuarioRepository.findAll();

    }
    public Usuario obtenerUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if(usuario != null){
            return usuario;
        }
        else return null;
    }  
}
    
 


