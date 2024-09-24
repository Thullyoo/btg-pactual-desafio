package com.thullyoo.btg_pactual.listener.dto;

import java.math.BigDecimal;

public record OrderItemQueuePayload(String produto,
                                    Integer quantidade,
                                    BigDecimal preco) {
}