package com.app.pedidos.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PedidoDTO {
    private String descricao;
    private BigDecimal valor;
    private String status;
    private Long clienteId;
}
