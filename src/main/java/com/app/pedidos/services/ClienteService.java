package com.app.pedidos.services;

import com.app.pedidos.models.ClienteModel;
import com.app.pedidos.repositories.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Listar todos os clientes
    public List<ClienteModel> listarTodos() {
        return clienteRepository.findAll();
    }

    // Buscar cliente por ID
    public Optional<ClienteModel> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    // Salvar cliente com validação
    public ClienteModel salvar(ClienteModel cliente) {
        // Valida se o CPF já está cadastrado
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new RuntimeException("CPF já cadastrado!");
        }

        // Validações de campos obrigatórios (pode ser feito via anotação @NotNull no modelo, mas vou exemplificar aqui também)
        if (cliente.getCpf() == null || cliente.getNome() == null) {
            throw new RuntimeException("CPF e Nome são obrigatórios!");
        }

        return clienteRepository.save(cliente);
    }

    // Deletar cliente
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}
