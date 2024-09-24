package com.thullyoo.btg_pactual.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "tb_orders")
public class Ordem {

    @MongoId
    private Long id;

    @Indexed(name = "client_id")
    private Long clientId;

    private List<OrdemItem> itens;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal total;

    public Ordem() {
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<OrdemItem> getItens() {
        return itens;
    }

    public void setItens(List<OrdemItem> itens) {
        this.itens = itens;
    }
}
