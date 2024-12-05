package com.app.pedidos.controllers;

import com.app.pedidos.dtos.PedidoDTO;
import com.app.pedidos.models.PedidoModel;
import com.app.pedidos.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    // Injeção de dependência do PedidoService
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Listar todos os pedidos
    @GetMapping
    public List<PedidoModel> listarTodos() {
        return pedidoService.listarTodos();
    }

    // Buscar um pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoModel> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo pedido
    @PostMapping
    public PedidoModel criar(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.criarPedido(pedidoDTO);  // Chamando o método do PedidoService
    }

    // Atualizar um pedido existente
    @PutMapping("/{id}")
    public ResponseEntity<PedidoModel> atualizar(@PathVariable Long id, @RequestBody PedidoModel pedidoAtualizado) {
        return pedidoService.buscarPorId(id).map(pedidoExistente -> {
            pedidoExistente.setDescricao(pedidoAtualizado.getDescricao());
            pedidoExistente.setValor(pedidoAtualizado.getValor());
            pedidoExistente.setStatus(pedidoAtualizado.getStatus());
            PedidoModel pedidoSalvo = pedidoService.salvar(pedidoExistente);
            return ResponseEntity.ok(pedidoSalvo);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Deletar um pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (pedidoService.buscarPorId(id).isPresent()) {
            pedidoService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/teste")
    public ResponseEntity<String> testeConexao() {
        // Simplesmente criar um pedido de teste
        PedidoModel pedido = new PedidoModel("Teste", new BigDecimal("99.99"), "PENDENTE", null);
        pedidoService.salvar(pedido);
        return ResponseEntity.ok("Pedido criado com sucesso!");
    }

}
