package tdt4140.gr1836.app.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import tdt4140.gr1836.app.inbox.Message;
import tdt4140.gr1836.app.inbox.Messages;

public class InboxTest {
	
	@Test 
	public void testMessageList() throws InterruptedException {
		Message message1 = new Message("Hello", "Me", "You");
        Thread.sleep(500);
		Message message2 = new Message("Hei", "Me", "You");
		Thread.sleep(500);
		Message message3 = new Message("Cool", "Me", "You");
		Messages messages = new Messages();
		assertEquals(messages.toList(), null);
		Map<String,Message> allMessages = new HashMap<String, Message>();
		allMessages.put(message1.getDate(), message1);
		allMessages.put(message2.getDate(), message2);
		allMessages.put(message3.getDate(), message3);
		messages.setMessages(allMessages);
		ArrayList<Message> messagesArray = messages.toList();
		assertEquals(messagesArray.get(2).getMessage(), "Cool");
		assertEquals(messagesArray.get(1).getMessage(), "Hei");
		assertEquals(messagesArray.get(0).getMessage(), "Hello");
	}

}
