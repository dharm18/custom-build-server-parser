package com.dharam.creditsuisse.logparser.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="log_event")
public class LogEvent {

	@Id
	@Column
	private String eventId;
	@Column(name="event_duration")
	private long eventDuration;
	@Column
	private String type;
	@Column
	private String host;
	@Column
	private boolean alert;
	
	public LogEvent() {
		super();
	}
	public LogEvent(String eventId, long eventDuration, String type, String host, boolean alert) {
		super();
		this.eventId = eventId;
		this.eventDuration = eventDuration;
		this.type = type;
		this.host = host;
		this.alert = alert;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public long getEventDuration() {
		return eventDuration;
	}
	public void setEventDuration(long eventDuration) {
		this.eventDuration = eventDuration;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public boolean isAlert() {
		return alert;
	}
	public void setAlert(boolean alert) {
		this.alert = alert;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LogEvent [eventId=");
		builder.append(eventId);
		builder.append(", eventDuration=");
		builder.append(eventDuration);
		builder.append(", type=");
		builder.append(type);
		builder.append(", host=");
		builder.append(host);
		builder.append(", alert=");
		builder.append(alert);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
