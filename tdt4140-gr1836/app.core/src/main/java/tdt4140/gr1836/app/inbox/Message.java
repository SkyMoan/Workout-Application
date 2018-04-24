//Helper class for handling the message objects used for storing messages between users
package tdt4140.gr1836.app.inbox;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Message implements Serializable{
	private String from;
	private String to;
	private String message;
	private String date;

	public Message(String m, String to, String from){
		this.from=(from);
		this.to=(to);
		this.message=(m);
		this.date=(""+ new Date().getTime());
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
