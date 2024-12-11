package com.app.pedidos.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cliente")
@Data
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull(message = "CPF não pode ser nulo")
    @Pattern(regexp = "\\d{11}", message = "CPF inválido. Deve conter 11 dígitos.")
    private String cpf;

    @Column(nullable = false)
    @NotNull(message = "Nome não pode ser nulo")
    @Size(min = 2, message = "Nome deve ter no mínimo 2 caracteres")
    private String nome;

    @Size(max = 15, message = "Telefone não pode ter mais de 15 caracteres")
    private String telefone;

    @Size(max = 255, message = "Endereço não pode ter mais de 255 caracteres")
    private String endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoModel> pedidos;
}

