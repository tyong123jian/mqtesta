package com.jiejue.mqtest.controller;

import com.jiejue.mqtest.service.ConsumerService;
import com.jiejue.mqtest.service.ProducerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jianbin on 2017-11-25.
 */
@Controller
public class WelcomeController {
    private static Log log = LogFactory.getLog(WelcomeController.class);

    //队列名gzframe.demo
    @Resource(name="destination")
    private Destination demoQueueDestination;

    //队列消息生产者
    @Resource(name="producerService")
    private ProducerService producer;

    //队列消息消费者
    @Resource(name="consumerService")
    private ConsumerService consumer;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView welcome(){
        log.info("in welcome controller");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("welcome");
        return mv;
    }

    @RequestMapping("/producer")
    public ModelAndView generateMessage(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = sdf.format(new Date());
        ModelAndView mav = new ModelAndView("producer");
        mav.addObject("time", time);
        return mav;
    }

    @RequestMapping("/onSend")
    public ModelAndView sendToQueue(String message){
        log.info("message:"+message);
        ModelAndView mav = new ModelAndView("welcome");
        producer.sendMsg(demoQueueDestination, message+", zjb");
        return mav;
    }

    @RequestMapping("/consumer")
    public ModelAndView showMessage() throws JMSException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = sdf.format(new Date());
        ModelAndView mav = new ModelAndView("consumer");
        TextMessage message =  consumer.receive(demoQueueDestination);
        mav.addObject("message", message.getText());
        return mav;
    }

    @RequestMapping("/jms")
    public void testMq() throws IOException {
        JMXServiceURL url = new JMXServiceURL("");
        JMXConnector connector = JMXConnectorFactory.connect(url);
        connector.connect();
        MBeanServerConnection connection = connector.getMBeanServerConnection();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "http://119.23.76.53:9090/20170911/111dd977-af60-40f0-880b-57ca35ca31a6.png";
        String url2 = "http://bmp.happyptv.cn/20170911/111dd977-af60-40f0-880b-57ca35ca31a6.png";
        String str = "张建彬";
        System.out.println(url.length()+"url:"+url.getBytes("UTF-8").length+", "+url2.length()+" url2:"+url2.getBytes("UTF-8").length);
        System.out.println(str.length()+" str:"+str.getBytes().length);
    }

}
