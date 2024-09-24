package com.thullyoo.btg_pactual.services;

import com.thullyoo.btg_pactual.controller.dto.OrderResponse;
import com.thullyoo.btg_pactual.entity.Order;
import com.thullyoo.btg_pactual.entity.OrderItem;
import com.thullyoo.btg_pactual.listener.dto.OrderQueuePayload;
import com.thullyoo.btg_pactual.repository.OrderRepository;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


@Service
public class QueueService {

    private final OrderRepository orderRepository;

    private final MongoTemplate mongoTemplate;

    public QueueService(OrderRepository orderRepository, MongoTemplate mongoTemplate) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void save(OrderQueuePayload orderQueue){
        Order order = new Order();
        order.setClientId(orderQueue.codigoCliente());
        order.setId(orderQueue.codigoPedido());
        order.setItens(getOrderQueueItens(orderQueue));
        order.setTotal(getTotal(orderQueue));

        orderRepository.save(order);
    }

    public Page<OrderResponse> findByClientId(Long clientId, PageRequest pageRequest){
        var orders =  orderRepository.findByClientId(clientId, pageRequest);

        return orders.map(OrderResponse::toOrderResponse);
    }

    public BigDecimal findTotalForClientId(Long clientId){
        var aggregations = newAggregation(
                match(Criteria.where("clientId").is(clientId)),
                group().sum("total").as("total")
        );

        var response =  mongoTemplate.aggregate(aggregations, "tb_orders", Document.class);

        return new BigDecimal(response.getUniqueMappedResult().get("total").toString());
    }

    private BigDecimal getTotal(OrderQueuePayload orderQueue) {
        return orderQueue.itens().stream().map( item -> item.preco().multiply(BigDecimal.valueOf(item.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private List<OrderItem> getOrderQueueItens(OrderQueuePayload orderQueue) {
        return orderQueue.itens().stream().map((item) ->{
            return new OrderItem(item.produto(),item.quantidade(),item.preco());
        }).toList();
    }


}

