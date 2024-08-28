package com.alivia.bussiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

@Component
public class MessageProducer {

	@Autowired
	JmsTemplate template;

	@Value("${my.app.dest.name}")
	private String destination;
	

	public void sendMessage(Student message) {
		
		
		 MessageCreator messageCreator = new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				
				TextMessage textMessage = session.createTextMessage(destination);
//				session.createObjectMessage(message);
				System.out.println("Usage  " + textMessage.getClass().getCanonicalName());
				return textMessage;
			}
		};
		template.send(destination,messageCreator);
		
//		template.send(destination, messageCreator);
	}

}
