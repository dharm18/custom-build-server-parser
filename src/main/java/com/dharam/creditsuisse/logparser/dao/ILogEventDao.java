package com.dharam.creditsuisse.logparser.dao;

import java.util.List;

import com.dharam.creditsuisse.logparser.domain.LogEvent;

public interface ILogEventDao {

	boolean saveLogEvent(LogEvent logEvent);
	
	public List<LogEvent> getLogEvents();
}
