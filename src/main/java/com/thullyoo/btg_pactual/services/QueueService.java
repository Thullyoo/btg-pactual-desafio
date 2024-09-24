package com.thullyoo.btg_pactual.services;

import com.thullyoo.btg_pactual.entity.Order;
import com.thullyoo.btg_pactual.entity.OrderItem;
import com.thullyoo.btg_pactual.listener.dto.OrderQueuePayload;
import com.thullyoo.btg_pactual.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class QueueService {

    private final OrderRepository orderRepository;

    public QueueService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void save(OrderQueuePayload orderQueue){
        Order order = new Order();
        order.setClientId(orderQueue.codigoCliente());
        order.setId(orderQueue.codigoPedido());
        order.setItens(getOrderQueueItens(orderQueue));
        order.setTotal(getTotal(orderQueue));

        orderRepository.save(order);
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

