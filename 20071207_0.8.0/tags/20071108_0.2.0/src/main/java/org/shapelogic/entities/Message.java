package org.shapelogic.entities;

import javax.persistence.*;

@Entity
@Table(name = "MESSAGES")
/** Test message class to be used with Hibernate  
 * 
 * @author Sami Badawi
 *
 */
public class Message {

  @Id @GeneratedValue @Column(name = "MESSAGE_ID")
  private Long id;

  @Column(name = "MESSAGE_TEXT")
  private String text;

  @ManyToOne(cascade = CascadeType.ALL) @JoinColumn(name = "NEXT_MESSAGE_ID")
  private Message nextMessage;

  public Message() {}

  public Message(String text) {
    this.text = text;
  }

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}

public Message getNextMessage() {
	return nextMessage;
}

public void setNextMessage(Message nextMessage) {
	this.nextMessage = nextMessage;
}
}
