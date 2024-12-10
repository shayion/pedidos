package com.app.pedidos.services;

import com.app.pedidos.dtos.PedidoDTO;
import com.app.pedidos.models.ClienteModel;
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

    public PedidoModel atualizarPedido(Long id, PedidoDTO pedidoDTO) {
        // Lógica para buscar o pedido pelo id
        PedidoModel pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // Atualizando as propriedades do pedido
        pedidoExistente.setDescricao(pedidoDTO.getDescricao());
        pedidoExistente.setValor(pedidoDTO.getValor());
        pedidoExistente.setStatus(pedidoDTO.getStatus());

        // Salvar o pedido atualizado
        return pedidoRepository.save(pedidoExistente);
    }

    // Criar um pedido a partir do DTO
    public PedidoModel criarPedido(PedidoDTO pedidoDTO) {
        // Log para verificar os dados recebidos
        System.out.println("Recebendo PedidoDTO: ");
        System.out.println("Descricao: " + pedidoDTO.getDescricao());
        System.out.println("Valor: " + pedidoDTO.getValor());
        System.out.println("Status: " + pedidoDTO.getStatus());
        System.out.println("Cliente ID: " + pedidoDTO.getClienteId());

        // Verificar se o cliente existe
        Optional<ClienteModel> clienteOpt = clienteService.buscarPorId(pedidoDTO.getClienteId());
        if (clienteOpt.isPresent()) {
            System.out.println("Cliente encontrado: " + clienteOpt.get().getNome());
        } else {
            System.out.println("Cliente não encontrado!");
        }


        PedidoModel pedido = new PedidoModel();
        pedido.setDescricao(pedidoDTO.getDescricao());
        pedido.setValor(pedidoDTO.getValor());
        pedido.setStatus(pedidoDTO.getStatus());

        // Usar clienteService para buscar o cliente
        pedido.setCliente(clienteOpt.orElseThrow(() -> new RuntimeException("Cliente não encontrado")));

        // Salvar o pedido
        PedidoModel savedPedido = salvar(pedido);

        // Log para verificar o pedido salvo
        System.out.println("Pedido salvo: " + savedPedido.getId());

        return savedPedido;  // Salvar o pedido
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
