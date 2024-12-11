package com.app.pedidos.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pedidos")
@Data
public class PedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    @NotNull(message = "Descrição não pode ser nula")
    @Size(min = 5, message = "Descrição deve ter no mínimo 5 caracteres")
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Valor não pode ser nulo")
    private BigDecimal valor;

    @Column(nullable = false)
    @NotNull(message = "Status não pode ser nulo")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteModel cliente;
}

