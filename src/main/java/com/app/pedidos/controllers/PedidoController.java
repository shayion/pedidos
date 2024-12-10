package com.app.pedidos.controllers;

import com.app.pedidos.dtos.PedidoDTO;
import com.app.pedidos.models.PedidoModel;
import com.app.pedidos.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<PedidoModel> listarTodos() {
        return pedidoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<PedidoModel> criarPedido(@Valid @RequestBody PedidoDTO pedidoDTO) {
        PedidoModel novoPedido = pedidoService.criarPedido(pedidoDTO);
        return ResponseEntity.ok(novoPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoModel> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoModel> atualizarPedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        PedidoModel pedidoAtualizado = pedidoService.atualizarPedido(id, pedidoDTO);
        return ResponseEntity.ok(pedidoAtualizado);
    }
}
