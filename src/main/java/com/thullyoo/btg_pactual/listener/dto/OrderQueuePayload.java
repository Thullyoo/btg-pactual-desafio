package com.thullyoo.btg_pactual.listener.dto;

import java.util.List;

public record OrderQueuePayload(Long codigoPedido,
                                Long codigoCliente,
                                List<OrderItemQueuePayload> itens) {
}