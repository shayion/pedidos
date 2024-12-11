package com.app.pedidos.services;

import com.app.pedidos.dtos.PedidoDTO;
import com.app.pedidos.models.ClienteModel;
import com.app.pedidos.models.PedidoModel;
import com.app.pedidos.repositories.PedidoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    // Atualizar um pedido existente
    public PedidoModel atualizarPedido(Long id, PedidoDTO pedidoDTO) {
        // Lógica para buscar o pedido pelo id
        PedidoModel pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // Verificar se os campos obrigatórios estão presentes
        validarCamposObrigatorios(pedidoDTO);

        // Atualizando as propriedades do pedido
        pedidoExistente.setDescricao(pedidoDTO.getDescricao());
        pedidoExistente.setValor(pedidoDTO.getValor());
        pedidoExistente.setStatus(pedidoDTO.getStatus());

        // Salvar o pedido atualizado
        return pedidoRepository.save(pedidoExistente);
    }

    // Criar um pedido a partir do DTO
    public PedidoModel criarPedido(PedidoDTO pedidoDTO) {
        // Lógica para verificar e validar os campos obrigatórios
        validarCamposObrigatorios(pedidoDTO);

        // Log para verificar os dados recebidos
        System.out.println("Recebendo PedidoDTO: ");
        System.out.println("Descricao: " + pedidoDTO.getDescricao());
        System.out.println("Valor: " + pedidoDTO.getValor());
        System.out.println("Status: " + pedidoDTO.getStatus());
        System.out.println("Cliente ID: " + pedidoDTO.getClienteId());

        // Verificar se o cliente existe
        Optional<ClienteModel> clienteOpt = clienteService.buscarPorId(pedidoDTO.getClienteId());
        if (!clienteOpt.isPresent()) {
            throw new RuntimeException("Cliente não encontrado!");
        }

        // Criando o novo pedido
        PedidoModel pedido = new PedidoModel();
        pedido.setDescricao(pedidoDTO.getDescricao());
        pedido.setValor(pedidoDTO.getValor());
        pedido.setStatus(pedidoDTO.getStatus());
        pedido.setCliente(clienteOpt.get());  // Associe o cliente encontrado

        // Salvar o pedido
        PedidoModel savedPedido = salvar(pedido);

        // Log para verificar o pedido salvo
        System.out.println("Pedido salvo: " + savedPedido.getId());

        return savedPedido;  // Retorna o pedido salvo
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

    // Método para validar campos obrigatórios do PedidoDTO
    private void validarCamposObrigatorios(PedidoDTO pedidoDTO) {
        if (pedidoDTO.getDescricao() == null || pedidoDTO.getDescricao().isEmpty()) {
            throw new RuntimeException("Descrição do pedido é obrigatória!");
        }
        if (pedidoDTO.getValor() == null || pedidoDTO.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor do pedido deve ser maior que zero!");
        }
        if (pedidoDTO.getClienteId() == null) {
            throw new RuntimeException("Cliente é obrigatório!");
        }
    }
}
