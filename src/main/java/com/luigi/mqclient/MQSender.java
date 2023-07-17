package com.luigi.mqclient;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.JMSContext;
import javax.jms.Destination;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.jms.JMSException;

public class MQSender {

    private static final String HOST = "localhost";
    private static final int PORT = 1414;
    private static final String CHANNEL = "DEV.APP.SVRCONN";
    private static final String QMGR = "QM1";
    private static final String QUEUE_NAME = "DEV.QUEUE.1";

    private static final String APP_USER = "app";
    private static final String APP_PASSWORD = "passw0rd";

    public static void main(String[] args) {
        try {
            MQQueueConnectionFactory cf = new MQQueueConnectionFactory();

            cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
            cf.setIntProperty(WMQConstants.WMQ_PORT, PORT);
            cf.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
            cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
            cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, QMGR);
            cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "JmsPutGet (JMS)");
            cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
            cf.setStringProperty(WMQConstants.USERID, APP_USER);
            cf.setStringProperty(WMQConstants.PASSWORD, APP_PASSWORD);

            JMSContext context = cf.createContext();
            Destination destination = context.createQueue("queue:///" + QUEUE_NAME);

            TextMessage message = context.createTextMessage("Hello world!");

            JMSProducer producer = context.createProducer();
            producer.send(destination, message);

            System.out.println("Sent message:\n" + message);

            context.close();

        } catch (JMSException jmsex) {
            System.out.println("An exception occurred: " + jmsex.getMessage());
            jmsex.printStackTrace();
        }
    }
}
