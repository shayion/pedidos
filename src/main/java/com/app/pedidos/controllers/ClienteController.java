package com.app.pedidos.controllers;

import com.app.pedidos.dtos.ClienteDTO;
import com.app.pedidos.models.ClienteModel;
import com.app.pedidos.services.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> atualizarCliente(@PathVariable Long id, @RequestBody ClienteModel clienteAtualizado) {
        return clienteService.buscarPorId(id).map(clienteExistente -> {
            clienteExistente.setNome(clienteAtualizado.getNome());
            clienteExistente.setCpf(clienteAtualizado.getCpf());
            clienteExistente.setTelefone(clienteAtualizado.getTelefone());
            clienteExistente.setEndereco(clienteAtualizado.getEndereco());
            ClienteModel clienteSalvo = clienteService.salvar(clienteExistente);
            return ResponseEntity.ok(clienteSalvo);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ClienteModel> listarTodos() {
        return clienteService.listarTodos();
    }

    // Ajuste aqui: Usando ClienteDTO
    @PostMapping
    public ResponseEntity<ClienteModel> salvar(@RequestBody ClienteDTO clienteDTO) {
        // Verifica se o CPF está presente no DTO, se não, retorna erro
        if (clienteDTO.getCpf() == null || clienteDTO.getCpf().isEmpty()) {
            return ResponseEntity.badRequest().body(null);  // Retorna erro 400
        }

        // Mapeando os dados do ClienteDTO para ClienteModel
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setNome(clienteDTO.getNome());
        clienteModel.setCpf(clienteDTO.getCpf());
        clienteModel.setTelefone(clienteDTO.getTelefone());
        clienteModel.setEndereco(clienteDTO.getEndereco());

        // Salvando o cliente
        ClienteModel clienteSalvo = clienteService.salvar(clienteModel);

        // Retorna o cliente salvo com status 201 (Criado)
        return ResponseEntity.status(201).body(clienteSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
