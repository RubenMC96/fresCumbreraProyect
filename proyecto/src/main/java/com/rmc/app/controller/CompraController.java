package com.rmc.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rmc.app.domain.Compra;
import com.rmc.app.domain.LineaProducto;
import com.rmc.app.domain.Usuario;
import com.rmc.app.service.CompraService;
import com.rmc.app.service.LineaProductoService;
import com.rmc.app.service.ProductoService;
import com.rmc.app.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/compra")
public class CompraController {

    @Autowired
    public CompraService compraService;
    @Autowired
    public UsuarioService usuarioService;
    @Autowired
    public LineaProductoService lineaProductoService;
    @Autowired
    public ProductoService productoService;

    @GetMapping({ "/", "/list" })
    public String showList(Model model) {
        model.addAttribute("listaCompra", compraService.obtenerTodos());
        return "CompraView/ListCompraView";
    }

    @GetMapping({ "/usuario/{id}" })
    public String showPedidos(@PathVariable Long id ,Model model) {
        //Puede que en la app final tenga que volver a obtener el usuario conectado.
        model.addAttribute("listaCompra", compraService.obtenerPorUsuario(id));
        // model.addAttribute("compra", compraService.obtenerCompraPorUsuario(usuario));
        Usuario usuario = usuarioService.obtenerUsuarioConectado();
        model.addAttribute("usuario", usuario);
        return "CompraView/ListCompraView";
    }

    @GetMapping("/detalles/{idCompra}")
    public String showDetallePedido(@PathVariable int idCompra ,Model model){

        Compra compra = compraService.obtenerPorId(idCompra);
        List<LineaProducto> lineasCompra = lineaProductoService.obtenerPorUsuario();

        model.addAttribute("detallesCompra", compra);
        model.addAttribute("productos", lineasCompra);
        return "CompraView/DetallesCompraView";
    }


    // @PostMapping("/nuevo/{listaCompra}")
    // public String showNuevo(@PathVariable List<LineaProducto> listaCompra, BindingResult bindingResult) {
    //     if(bindingResult.hasErrors())
    //             return "redirect:/usuario/nuevo";
    //     else{
    //         Usuario usuario = usuarioService.obtenerUsuarioConectado();

    //         Compra compra = compraService.crearCompra(listaCompra);
    //         compraService.añadir(compra);
    //         return "redirect:/compra/usuario/" + usuario.getId();
    //     }
    @GetMapping("/nuevo")
    public String showNuevo() {
        
            List<LineaProducto> lineasCompra = lineaProductoService.obtenerPorUsuario();
            List<LineaProducto> productosComprar = new ArrayList<>();
            for(LineaProducto producto: lineasCompra){
                if(producto.getProducto().getStock() > 0 && producto.getProducto().getStock() >= producto.getCantidadProductos()){
                     productosComprar.add(producto);
                     productoService.actualizarStock(producto.getId() ,producto.getProducto().getStock() - producto.getCantidadProductos());

                }
            }            
            
            if(!productosComprar.isEmpty()){
                Compra compra = compraService.crearCompra(productosComprar);
                compraService.añadir(compra);
                lineaProductoService.borrarTodo();
            }
            
            Usuario usuario = usuarioService.obtenerUsuarioConectado();

            return "redirect:/compra/usuario/" + usuario.getId();
        }

    // @PostMapping("/nuevo/submit")
    // public String showNuevoSubmit(
    // @Valid Compra compraForm,
    // BindingResult bindingResult) {
    // if (bindingResult.hasErrors())
    // return "redirect:/compra/nuevo";
    // compraService.añadir(compraForm);
    // return "redirect:/compra/list";
    // }

    @PostMapping("/editar/submit")
    public String showEditSubmit(
            @Valid Compra compraForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "redirect:/compra/editar/{id}";
        compraService.editar(compraForm);
        return "redirect:/compra/list";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Compra compra = compraService.obtenerPorId(id);
        // el commandobject del formulario es el empleado con el id solicitado
        if (compra != null) {
            //model.addAttribute("listacompra", lineaProductoService.obtenerPorCompra(compra));
            model.addAttribute("compra", compra);
            return "LineaProductosView/ListLineaProductoView";
        }
        // si no lo encuentra vuelve a la página de inicio.
        return "redirect:/compra/usuario/" + id;
    }

    @GetMapping("/borrar/{id}")
    public String showDelete(@PathVariable long id) {
        Compra compra = compraService.obtenerPorId(id);
        compraService.borrar(id);
        return "redirect:/compra/usuario/" + compra.getUsuario().getId();
        // Falta visualizar de nuevo la lista actualizada
    }
}