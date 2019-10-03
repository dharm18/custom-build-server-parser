package com.dharam.creditsuisse.logparser.db.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.dharam.creditsuisse.logparser.domain.LogEvent;

public class DBConnectionUtil {
	
	private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();
				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "org.hsqldb.jdbcDriver");
				settings.put(Environment.URL, "jdbc:hsqldb:hsql://localhost/logdb;ifexists=true");
				settings.put(Environment.USER, "SA");
				settings.put(Environment.PASS, "");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.HSQLDialect");
				settings.put(Environment.SHOW_SQL, "true");
				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
				settings.put(Environment.HBM2DDL_AUTO, "create-drop");
				settings.put(Environment.SHOW_SQL, true);
				
				configuration.setProperties(settings);
				configuration.addAnnotatedClass(LogEvent.class);
				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				    .applySettings(configuration.getProperties()).build();
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return sessionFactory;
	}

}
