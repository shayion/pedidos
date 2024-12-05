package com.app.pedidos.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pedidos") // Nome da tabela no banco de dados
public class PedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática de IDs
    private Long id;

    @Column(nullable = false, length = 255) // Garantir limite de caracteres na descrição
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2) // Configuração para valores monetários
    private BigDecimal valor;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY) // Evitar carregamento excessivo ao buscar Pedidos
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteModel cliente;

    // Construtor padrão (obrigatório para JPA)
    public PedidoModel() {
    }

    // Construtor com argumentos (opcional, para facilitar uso)
    public PedidoModel(String descricao, BigDecimal valor, String status, ClienteModel cliente) {
        this.descricao = descricao;
        this.valor = valor;
        this.status = status;
        this.cliente = cliente;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }
}
