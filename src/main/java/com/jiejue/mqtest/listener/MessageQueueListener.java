package com.jiejue.mqtest.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by jianbin on 2017-11-27.
 */
public class MessageQueueListener implements MessageListener {

    private static final Log log = LogFactory.getLog(MessageListener.class);

    @Override
    public void onMessage(Message message) {
        try {

            log.info("reveive queue message:"+ ((TextMessage)message).getText());
            message.clearBody();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
