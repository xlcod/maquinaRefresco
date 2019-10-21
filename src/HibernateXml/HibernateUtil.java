package HibernateXml;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import java.util.logging.*;

public class HibernateUtil {

	private SessionFactory sessionFactory;

	public HibernateUtil() {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

		try {
			// Create the SessionFactory from standard (hibernate.cfg.xml) config file.
			sessionFactory = new Configuration().configure("HibernateXml/hibernate.cfg.xml").buildSessionFactory();

		} catch (Throwable ex) {
			// Log the exception.
			System.err.println("Initial SessionFactory creation failed." + ex);
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}

	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
