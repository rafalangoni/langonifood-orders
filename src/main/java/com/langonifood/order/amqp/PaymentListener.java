package com.langonifood.order.amqp;

import com.langonifood.order.dto.PaymentDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    @RabbitListener(queues = "payments.order-details")
    public void receiveMessage(PaymentDto paymentDto){

        String message= """
                Payment information: %s
                Order number: %s
                Value $: %s
                Status: %s
                """.formatted(paymentDto.getId(), paymentDto.getOrderId(), paymentDto.getPaymentValue(), paymentDto.getPaymentStatus());
        System.out.println(message);
    }
}
