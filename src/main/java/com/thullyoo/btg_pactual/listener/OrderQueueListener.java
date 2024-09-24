package com.thullyoo.btg_pactual.listener;

import com.thullyoo.btg_pactual.listener.dto.OrderQueuePayload;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.thullyoo.btg_pactual.config.RabbitMqConfig.ORDER_CREATED_QUEUE;

@Component
public class OrderQueueListener {


    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listener(Message<OrderQueuePayload> message){
        System.out.println(message.getPayload());
    }

}
