//Helper class for handling the message objects and storing them in a map
package tdt4140.gr1836.app.inbox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

@SuppressWarnings("serial")
public class Messages implements Serializable{
	private  Map<String,Message> messages;
	public Messages() {
		
	}
	public ArrayList<Message> toList() {
		if(messages==null) {
			return null;
		}
		ArrayList<Message> temp=new ArrayList<Message>();
		for(String key : this.messages.keySet()) {
			temp.add(this.messages.get(key));
		}
		//Sort on date
		temp.sort(Comparator.comparing(Message::getDate));
		return temp;
	}
	public Map<String,Message> getMessages(){
		return this.messages;
	}
	public void setMessages(Map<String,Message> messages) {
		this.messages = messages;
	}
}
