package com.dharam.creditsuisse.logparser.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dharam.creditsuisse.logparser.db.util.DBConnectionUtil;
import com.dharam.creditsuisse.logparser.domain.LogEvent;

public class LogEventDao implements ILogEventDao {

	@Override
	public boolean saveLogEvent(LogEvent logEvent) {
		Transaction transaction = null;
		try (Session session = DBConnectionUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(logEvent);
			transaction.commit();
		} catch(Exception e) {
			if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<LogEvent> getLogEvents() {
        try (Session session = DBConnectionUtil.getSessionFactory().openSession()) {
            return session.createQuery("from LogEvent", LogEvent.class).list();
        }
    }

}
