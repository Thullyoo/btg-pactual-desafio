package com.thullyoo.btg_pactual.listener;

import com.thullyoo.btg_pactual.listener.dto.OrderQueuePayload;
import com.thullyoo.btg_pactual.services.QueueService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.thullyoo.btg_pactual.config.RabbitMqConfig.ORDER_CREATED_QUEUE;

@Component
public class OrderQueueListener {

    private final QueueService queueService;

    public OrderQueueListener(QueueService queueService) {
        this.queueService = queueService;
    }

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listener(Message<OrderQueuePayload> message){
        System.out.println(message.getPayload());

        queueService.save(message.getPayload());
    }

}
