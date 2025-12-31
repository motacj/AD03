package dao;


import model.Profesor;
import model.Alumno;
import util.HibernateUtil;
import org.hibernate.query.Query;
import java.util.List;
import org.hibernate.PersistentObjectException;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.exception.ConstraintViolationException;
import dao.ProfesorDAO;
import jakarta.persistence.EntityExistsException;

public class ProfesorDAO {
    /**
     * Listar todos los profesores de la base de datos con Hibernate
     * @return List<Profesor>
     */
    @SuppressWarnings({ "rawtypes" })//**************OK********************/
    public static List<Profesor> getAllProfesores() {
        // Código para obtener una persona por su ID desde la base de datos
        Session session = HibernateUtil.getSessionFactory().openSession();
        //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");
        Query query = session.createQuery("FROM Profesor", Profesor.class);
        List<Profesor> profesores = query.getResultList();
        if(profesores.isEmpty()) {
            System.out.println("*****************************Lista de Profesores:*****************************");
            System.out.println("No hay profesores en la base de datos.");
        } else {
            System.out.println("*****************************Lista de Profesores:*****************************");
            System.out.printf("%-5s %-15s %-20s %-30s %-20s%n", "ID", "Nombre", "Apellidos", "Instituto", "Asignatura"
            );
            System.out.println("------------------------------------------------------------------------------------");
            for(Profesor profesor : profesores) {
                System.out.printf("%-5d %-15s %-20s %-30s %-20s%n",
                        profesor.getIdProf(),
                        profesor.getNombre(),
                        profesor.getApellidos(),
                        profesor.getInstituto(),
                        profesor.getAsignatura());
            }
            System.out.println("------------------------------------------------------------------------------------");
        }
        session.close();
        return profesores; // Implementar la lógica para recuperar la persona  
    }
    /**
     * Llama a un elemento Profesor de la base de datos por su ID
     * @param idProf
     * @return Profesor
     */
    public static Profesor getProfesorById(int idProf) {//**************OK********************/
        // Código para obtener una persona por su ID desde la base de datos
        Session session = HibernateUtil.getSessionFactory().openSession();
        //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");
        Profesor profesor = session.get(Profesor.class, idProf);
        session.close();
        if(profesor == null) {
            System.out.println("El con ID " + idProf + " no existe.");
        }
        return profesor; // Implementar la lógica para recuperar la persona  
    }
    /**
     * inserta un nuevo profesor en la base de datos con Hibernate comprobando antes si ya existe
     * @param idProf 
     */
    @SuppressWarnings({ "deprecation", "rawtypes" })
    public static void insertProfesorByQuery(Profesor profesor) {//**************OK********************/
        // Código para obtener una persona por su ID desde la base de datos
            try{
                Session session = HibernateUtil.getSessionFactory().openSession();
                //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");
                Query query = session.createQuery("FROM Profesor WHERE nombre = :nombre " +
                            "  AND apellidos = :apellidos " +
                            "  AND instituto = :instituto " +
                            "  AND asignatura = :asignatura",
                            Profesor.class);
                query.setParameter("nombre", profesor.getNombre());
                query.setParameter("apellidos", profesor.getApellidos());
                query.setParameter("instituto", profesor.getInstituto());
                query.setParameter("asignatura", profesor.getAsignatura());
                List<Profesor> profesores = query.getResultList();
                if(profesores.isEmpty()) {
                    System.out.println("El profesor no existe.\n Se procedera a insertarla.");
                    session.beginTransaction();
                    session.persist(profesor);
                    //Guardar el objeto persona
                    session.save(profesor);
                    //Confirmar la transacción
                    session.getTransaction().commit();
                    System.out.println("Profesor insertado: " + profesor.getNombre() + " "
                    + profesor.getApellidos() + " " + profesor.getInstituto() + " " + profesor.getAsignatura());
                } else {
                    System.out.println(profesores.size() + " profesor(es) encontrado(s).\n No se insetara el registro porque ya existe");
                }
                session.close();  
            } catch (NullPointerException e) {
                System.out.println("Error: objeto o atributo que no ha sido inicializado\n" + e.getMessage());
            } catch (IllegalStateException e) {
                System.out.println("Error: la sesión de Hibernate no está en un estado válido\n" + e.getMessage());
            } catch (EntityExistsException e) {
                System.out.println("Error: el profesor ya existe en la base de datos\n" + e.getMessage());
            } catch (PersistentObjectException e) {
                System.out.println("Error: el objeto persistente no es válido\n" + e.getMessage());
            } catch (ConstraintViolationException e) {
                System.out.println("Error: violación de restricción en la base de datos\n" + e.getMessage());
            } catch (QueryException e) {
                System.out.println("Error: problema con la consulta HQL\n" + e.getMessage());
            } catch (SessionException e) {
                System.out.println("Error: problema con la sesión de Hibernate\n" + e.getMessage());
            }
        
    }
    /**
     * Nota media de los alumnos de un profesor
     * @param idProf
     */
    @SuppressWarnings({ "rawtypes" })
    public static void getNotaMediaByProfesorId(int idProfesor) {//**************OK********************/
        // Código para obtener una persona por su ID desde la base de datos
        Session session = HibernateUtil.getSessionFactory().openSession();
        //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");
        Query query = session.createQuery("SELECT AVG(a.nota) FROM Alumno a WHERE a.profesor.idProf = :idProfesor", Double.class);
        query.setParameter("idProfesor", idProfesor);
        Double notaMedia = (Double) query.getSingleResult();
        session.close();
        if(notaMedia != null) {
            System.out.printf("La nota media de los alumnos del profesor con ID %d es: %.2f\n" ,idProfesor ,notaMedia);
        } else {
            System.out.printf("El profesor con ID %d no tiene alumnos.\n",idProfesor);
        }
    }
    /**
     * Profesor con sus alumnos en un JOIN explicito
     * @param idProf
     */
    @SuppressWarnings({ "rawtypes" })
    public static void getProfesorConAlumnosJoinExplicito(int idProfesor) {//**************OK********************/
        // Código para obtener una persona por su ID desde la base de datos
        Session session = HibernateUtil.getSessionFactory().openSession();
        //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");
        Query query = session.createQuery("SELECT p, a FROM Profesor p JOIN Alumno a ON p.idProf = a.profesor.idProf WHERE " +
            "p.idProf = :idProfesor", Object[].class);
        query.setParameter("idProfesor", idProfesor);
        List<Object[]> resultados = query.getResultList();
        for (Object[] fila : resultados) {
            Profesor profesor = (Profesor) fila[0];
            Alumno alumno = (Alumno) fila[1];
            System.out.println("Profesor: " + profesor.getNombre() + " " + profesor.getApellidos() + 
                " - Alumno: " + alumno.getNombre() + " " + alumno.getApellidos() + " Nota: " + alumno.getNota());
        }
        session.close();
    }
    /**
     * Buscar profesor por objeto profesor
     * @param profesor
     * @return Profesor
     */
    public static Profesor getProfesorByObject(Profesor profesor) {//**************OK********************/
        // Código para obtener una persona por su ID desde la base de datos
        Session session = HibernateUtil.getSessionFactory().openSession();
        //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");
        Query<Profesor> query = session.createQuery("FROM Profesor WHERE nombre = :nombre " +
                            "  AND apellidos = :apellidos " +
                            "  AND instituto = :instituto " +
                            "  AND asignatura = :asignatura",
                            Profesor.class);
        query.setParameter("nombre", profesor.getNombre());
        query.setParameter("apellidos", profesor.getApellidos());
        query.setParameter("instituto", profesor.getInstituto());
        query.setParameter("asignatura", profesor.getAsignatura());
        List<Profesor> profesores = query.getResultList();
        session.close();
        if(profesores.isEmpty()) {
            System.out.println("El profesor no existe.");
            return null;
        } else {
            System.out.println(profesores.size() + " profesor(es) encontrado(s).");
            return profesores.get(0);
        }
    }
}
