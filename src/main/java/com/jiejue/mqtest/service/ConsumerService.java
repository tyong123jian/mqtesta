package com.jiejue.mqtest.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Created by jianbin on 2017-11-25.
 */
@Service("consumerService")
public class ConsumerService {
    private static final Log log = LogFactory.getLog(ConsumerService.class);
    @Autowired
    private JmsTemplate jmsTemplate;

    public TextMessage receive(Destination destination){
        TextMessage textMessage = (TextMessage) jmsTemplate.receive(destination);
        try {
            log.info("receive destination:"+destination.toString()+"receive message:"+textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return textMessage;

    }
}
