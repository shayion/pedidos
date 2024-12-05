package com.app.pedidos.services;

import com.app.pedidos.dtos.PedidoDTO;
import com.app.pedidos.models.PedidoModel;
import com.app.pedidos.repositories.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;  // Injeção de ClienteService

    // Injeção de dependência de PedidoRepository e ClienteService
    public PedidoService(PedidoRepository pedidoRepository, ClienteService clienteService) {
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
    }

    // Criar um pedido a partir do DTO
    public PedidoModel criarPedido(PedidoDTO pedidoDTO) {
        PedidoModel pedido = new PedidoModel();
        pedido.setDescricao(pedidoDTO.getDescricao());
        pedido.setValor(pedidoDTO.getValor());
        pedido.setStatus(pedidoDTO.getStatus());

        // Usar clienteService para buscar o cliente
        pedido.setCliente(clienteService.buscarPorId(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));

        return salvar(pedido);  // Salvar o pedido
    }

    // Método para salvar o pedido no banco de dados
    public PedidoModel salvar(PedidoModel pedido) {
        return pedidoRepository.save(pedido);
    }

    // Buscar um pedido por ID
    public Optional<PedidoModel> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    // Listar todos os pedidos
    public List<PedidoModel> listarTodos() {
        return pedidoRepository.findAll();
    }

    // Deletar um pedido
    public void deletar(Long id) {
        pedidoRepository.deleteById(id);
    }
}
