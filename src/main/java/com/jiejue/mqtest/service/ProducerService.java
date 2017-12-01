package com.jiejue.mqtest.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by jianbin on 2017-11-25.
 */
@Service("producerService")
public class ProducerService {
    private static final Log log = LogFactory.getLog(ProducerService.class);
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMsg(Destination destination, final String msg){
        log.info("destination:"+destination.toString()+", msg:"+msg);
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return  session.createTextMessage(msg);
            }
        });
    }

    public  void sendMsg(final String msg){
        log.info("msg:"+msg);
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }
}
