package com.rmc.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rmc.app.domain.LineaProducto;
import com.rmc.app.domain.Producto;
import com.rmc.app.domain.Usuario;
import com.rmc.app.service.CompraService;
import com.rmc.app.service.LineaProductoService;
import com.rmc.app.service.ProductoService;
import com.rmc.app.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/lineaProducto")
public class LineaProductoController {

    @Autowired
    public CompraService compraService;
    @Autowired
    public ProductoService productoService;
    @Autowired
    public LineaProductoService lineaProductoService;
    @Autowired
    public UsuarioService usuarioService;

    @GetMapping({ "/list/" })
    public String showList(Model model) {

        List <LineaProducto> lineas = lineaProductoService.obtenerPorUsuario();
        if(lineas != null && !lineas.isEmpty()){
            model.addAttribute("listaLineaProducto", lineas);
            Double importe = lineaProductoService.obtenerImporte(lineas);
            model.addAttribute("importe", importe);
            return "LineaProductosView/ListLineaProductoView";
        }
        else{
            model.addAttribute("listaVacia", "El carrito está vacío, visita nuestra fantática sección de productos");
            return "LineaProductosView/ListaVacia";
        }
        
        
    }

    // @GetMapping("/nuevo/{idCompra}")
    // public String showAñadir(@PathVariable long idCompra, Model model) {
    //     Compra compra = compraService.obtenerPorId(idCompra);

    //     model.addAttribute("listaProducto", productoService.obtenerLista());
    //     model.addAttribute("compra", compra);
    //     model.addAttribute("lineaForm", new LineaProducto(null, null, compra, null));
    //     return "LineaProductosView/FormLineaProductoNew";
    // }

    @PostMapping("/annadir/{idProducto}")
    public String showAnnadirLinea(@PathVariable Long idProducto, @Valid LineaProducto lineaForm,
                                    BindingResult bindingResult){

        Producto producto = productoService.obtenerPorId(idProducto); 

        
        LineaProducto linea = new LineaProducto(0L, lineaForm.getCantidadProductos(),null ,producto);
        lineaProductoService.annadir(linea);
        
        Long idCategoria = producto.getCategoria().getId();

        return "redirect:/categoria/listProductos/" + idCategoria;

    }



    // @GetMapping("/nuevoLinea/{idProducto}/{cantidad}")
    // public String showNewline(@PathVariable long idProducto, Integer cantidad) {
    //     lineaProductoService.addNuevaLinea(idProducto, cantidad);
    //     return "redirect:/producto/list";
    // }

    // @PostMapping("/nuevo/submit")
    // public String showNuevoSubmit(
    //         @Valid LineaProducto lineaForm,
    //         BindingResult bindingResult) {
    //     if (bindingResult.hasErrors())
    //         return "redirect:/lineaProducto/nuevo/" + lineaForm.getCompra().getId();
    //     lineaProductoService.annadir(lineaForm);
    //     return "redirect:/compra/usuario/" + lineaForm.getCompra().getId();
    // }

    @PostMapping("/editar/submit")
    public String showEditSubmit(
            @Valid LineaProducto lineaProductoForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "redirect:/lineaProducto/editar/{id}";
        lineaProductoService.editar(lineaProductoForm);
        return "redirect:/lineaProducto/list";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        LineaProducto lineaProducto = lineaProductoService.obtenerPorId(id);
        // el commandobject del formulario es el empleado con el id solicitado
        if (lineaProducto != null) {
            model.addAttribute("lineaProductoForm", lineaProducto);
            return "lineaProductoView/lineaProductoFormEdit";
        }
        // si no lo encuentra vuelve a la página de inicio.
        return "redirect:/lineaProducto/list";
    }

    @GetMapping("/borrar/{id}")
    public String showDelete(@PathVariable long id) {
        lineaProductoService.borrar(id);
        return "redirect:/lineaProducto/list/";

    }

    @GetMapping("/borrar")
    public String showDeleteAll(Model model) {
        lineaProductoService.borrarTodo();
        model.addAttribute("mensaje", "La lineaProducto está vacía");
        return "redirect:/lineaProducto/list/";

    }

}