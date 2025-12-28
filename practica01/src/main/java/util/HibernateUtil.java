package util;

import org.hibernate.SessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();


    private static SessionFactory buildSessionFactory() {
        try {
            // Crea la SessionFactory de hibernate.cfg.xml
            return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        } catch (MappingException ex) {
            System.out.println("Error de mapeo Hibernate: problema en archivos .hbm.xml o clases mal configuradas.");
            throw new ExceptionInInitializerError(ex);
        } catch (HibernateException ex) {
            System.out.println("Error interno de Hibernate: fallo al crear la SessionFactory.");
            throw new ExceptionInInitializerError(ex);
        } catch (Throwable ex) {
            System.out.println("Error crítico durante la inicialización de Hibernate.");
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Cierra caches y conexiones de la SessionFactory
        getSessionFactory().close();
    }
}