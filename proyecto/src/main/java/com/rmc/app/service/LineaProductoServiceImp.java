package com.rmc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmc.app.Repositories.CompraRepository;
import com.rmc.app.Repositories.LineaProductoRepository;
import com.rmc.app.Repositories.ProductoRepository;
import com.rmc.app.Repositories.UsuarioRepository;
import com.rmc.app.domain.Compra;
import com.rmc.app.domain.LineaProducto;
import com.rmc.app.domain.Producto;

@Service
public class LineaProductoServiceImp implements LineaProductoService {

    @Autowired
    LineaProductoRepository lineaProductoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    CompraRepository compraRepository;

    public List<LineaProducto> obtenerLista() {
        return lineaProductoRepository.findAll();
    }

    public LineaProducto obtenerPorId(long id) {

        LineaProducto lineaProducto = lineaProductoRepository.findById(id).orElse(null);// debería lanzar excepción si
                                                                                        // no encontrado
        if (lineaProducto != null) {
            return lineaProducto;
        }
        return null;

    }

    public void annadir(Long productoId, Integer cantidadProductos) {

        Producto producto = productoRepository.findById(productoId).orElse(null);
        if (producto != null) {
            LineaProducto lineaProducto = new LineaProducto(producto, cantidadProductos);
            lineaProductoRepository.save(lineaProducto);
        }

    }

    public LineaProducto editar(LineaProducto lineaProducto) {

        return lineaProductoRepository.save(lineaProducto);
    }

    public void borrar(Long id) {
        LineaProducto lineaProducto = lineaProductoRepository.findById(id).orElse(null);
        if (lineaProducto != null) {
            lineaProductoRepository.delete(lineaProducto);
        }

    }

    public void borrarTodo() {

        lineaProductoRepository.deleteAll();
    }

    public LineaProducto obtenerPorCompra(Compra compra) {

        LineaProducto lineaProducto = lineaProductoRepository.findByCompra(compra);

        if (lineaProducto != null) {
            return lineaProducto;
        } else
            return null;
    }
}