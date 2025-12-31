package util;  // Declara el paquete util (clases de apoyo/infraestructura)

import org.hibernate.SessionFactory;  // Importa SessionFactory: fábrica de sesiones de Hibernate
import org.hibernate.cfg.Configuration;  // Importa Configuration: configuración principal de Hibernate
import model.Alumno;  // Importa la entidad Alumno para registrarla en Hibernate
import model.Profesor;  // Importa la entidad Profesor para registrarla en Hibernate
import org.hibernate.HibernateException;  // Importa excepción general de Hibernate
import org.hibernate.MappingException;  // Importa excepción por errores de mapeo ORM

public class HibernateUtil {  // Define clase utilitaria para centralizar la configuración de Hibernate

    private static final SessionFactory sessionFactory = buildSessionFactory();  // Crea una única SessionFactory al cargar la clase (Singleton)


    private static SessionFactory buildSessionFactory() {  // Método privado que construye la SessionFactory
        try {  // Bloque try para capturar errores durante la inicialización
            Configuration configuration = new Configuration();  // Crea el objeto de configuración de Hibernate

            configuration.configure(); // lee hibernate.cfg.xml (configuración de BD, dialecto, propiedades)

            // REGISTRO DE CLASES ANOTADAS  // Comentario: registro manual de entidades JPA
            configuration.addAnnotatedClass(Profesor.class);  // Registra la entidad Profesor en Hibernate
            configuration.addAnnotatedClass(Alumno.class);  // Registra la entidad Alumno en Hibernate

            return configuration.buildSessionFactory();  // Construye y devuelve la SessionFactory
        } catch (MappingException ex) {  // Captura errores de mapeo (anotaciones/clases mal configuradas)
            System.out.println("Error de mapeo Hibernate: problema en archivos .hbm.xml o clases mal configuradas.");  // Mensaje informativo
            throw new ExceptionInInitializerError(ex);  // Detiene la inicialización lanzando error crítico
        } catch (HibernateException ex) {  // Captura errores internos de Hibernate
            System.out.println("Error interno de Hibernate: fallo al crear la SessionFactory.");  // Mensaje informativo
            throw new ExceptionInInitializerError(ex);  // Relanza como error de inicialización
        } catch (Throwable ex) {  // Captura cualquier otro error grave (Error, RuntimeException, etc.)
            System.out.println("Error crítico durante la inicialización de Hibernate.");  // Mensaje genérico
            throw new ExceptionInInitializerError(ex);  // Impide que la aplicación continúe
        }
    }  // Fin del método buildSessionFactory

    public static SessionFactory getSessionFactory() {  // Método público para obtener la SessionFactory
        return sessionFactory;  // Devuelve la instancia única de SessionFactory
    }  // Fin del getter

    public static void shutdown() {  // Método para cerrar Hibernate al finalizar la aplicación
        // Cierra caches y conexiones de la SessionFactory  // Comentario: limpieza de recursos
        getSessionFactory().close();  // Cierra la SessionFactory y libera conexiones
    }  // Fin del método shutdown
}  // Fin de la clase HibernateUtil
