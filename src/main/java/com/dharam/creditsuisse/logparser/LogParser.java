package com.dharam.creditsuisse.logparser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dharam.creditsuisse.logparser.dao.ILogEventDao;
import com.dharam.creditsuisse.logparser.dao.LogEventDao;
import com.dharam.creditsuisse.logparser.domain.Log;
import com.dharam.creditsuisse.logparser.domain.LogEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LogParser {
    
	private static final Logger logger = LoggerFactory.getLogger(LogParser.class);
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private ILogEventDao logEventDao = new LogEventDao();
	
	public static void main(String[] args) throws Exception {
		
		if(args.length == 1) {
			String serverLogfile = args[0];
			
			new LogParser().processFile(serverLogfile);
	        
		}
		else {
			throw new Exception("Log file path is required as a argument. For example, LogParser logfile.txt");
		}
	}
	
	/**
	 * Takes the file path, process the log line by line into Java object through GSON library and flags the event.
	 * @param serverLogfile
	 * @return
	 */
	public List<LogEvent> processFile(String serverLogfile) {
		Map<String, List<Log>> logs = new HashMap<>();
		List<LogEvent> logEvents = new ArrayList<>();
		
		long start = System.currentTimeMillis();
		final AtomicInteger total = new AtomicInteger(0);
        final AtomicInteger failed = new AtomicInteger(0);
		
		try (Stream<String> stream = Files.lines(Paths.get(serverLogfile))) {

			logs = stream
					.map(line -> {
					try {
						total.incrementAndGet();
						Log log	= gson.fromJson(line, Log.class);
						return log;
					}
					catch(Exception e) {
						logger.error("Error in parsing line {} with error message",total.get(), e.getMessage());
						logger.error("error: ", e);
						failed.incrementAndGet();
						return null;
					}
				})
				.collect(Collectors.groupingBy(Log::getId));
			
			logger.debug("logs :{}", logs);
			
			Comparator<Log> comparator = Comparator.comparing(Log::getState);
			
			logs.forEach((k,v)->{
				if(v.size()==2) {
					v.sort(comparator.reversed());
					logger.debug("reversed list with started and finished objects. {}", v);
					final long eventDuration = v.get(1).getTimestamp()-v.get(0).getTimestamp();
					logger.debug("Event duration : {}", eventDuration);
					
					LogEvent tempLogEvent = new LogEvent(k, eventDuration, v.get(0).getType(), v.get(0).getHost(), eventDuration > 4);
					logEventDao.saveLogEvent(tempLogEvent);
					
					logEvents.add(tempLogEvent);
				}
			});
			
			logger.debug("LogEvents : {}", logEvents);
			
			logger.debug("LogEvents HSQLDB: {} ", logEventDao.getLogEvents());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		long took = System.currentTimeMillis() - start;
        logger.info("Parsed {} lines with {} failures. Took {}ms", total.get(), failed.get(), took);
        
        return logEvents;
	}
}
