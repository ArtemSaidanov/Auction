package by.saidanov.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Description: this class provides Hibernate integration. It is used to create, get and delete sessions
 * and to create and delete transactions
 *
 * @author Artiom Saidanov.
 */
public class HibernateUtil {

    private volatile static HibernateUtil instance = null;
    private SessionFactory sessionFactory = null;
    private final ThreadLocal<Session> sessions = new ThreadLocal<>();

    /**
     * This constructor creates session factory
     */
    private HibernateUtil() {
        try {
            Configuration configuration = new Configuration().configure();
            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
            serviceRegistryBuilder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable e) {
            AuctionLogger.getInstance().log(getClass(), e.getMessage());
            e.printStackTrace();
        }
    }

    public static synchronized HibernateUtil getHibernateUtil() {
        if (instance == null) {
            synchronized (HibernateUtil.class) {
                if (instance == null) {
                    instance = new HibernateUtil();
                }
            }
        }
        return instance;
    }

    /**
     * This method creates hibernate session if not exists
     * or gets you already created hibernate session.
     *
     * @return Session - hibernate session
     */
    public Session getSession() {
        Session session = sessions.get();
        if (session == null) {
            session = sessionFactory.openSession();
            sessions.set(session);
        }
        return session;
    }

    /**
     * This method close session and set null to TreadLocal variable
     */
    public void closeSession() {
        Session session = HibernateUtil.getHibernateUtil().getSession();
        if (session != null && session.isOpen()) {
            session.close();
            sessions.set(null);
        }
    }
}
