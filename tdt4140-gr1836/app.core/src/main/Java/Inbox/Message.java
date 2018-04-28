
package Inbox;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Message implements Serializable{
	private String from, to, message, date;

	public Message(String m, String to, String from){
		this.from= from;
		this.to= to;
		this.message= m;
		this.date=""+ new Date().getTime();
	}
	public Message() {
		
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getMessage() {
		return message;
	}

	public String getDate() {
		return date;
	}
}
