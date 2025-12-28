package dao;

import java.util.List;
import java.util.Scanner;
import dao.ProfesorDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;
import model.Alumno;
import util.HibernateUtil;
import model.Profesor;

public class AlumnoDAO {
    /**
     * Inserta una nueva persona en la base de datos con Hibernate
     * @param alumno
     */
    @SuppressWarnings("deprecation")
    public static void insertAlumno(Alumno alumno) {//**************OK********************/
        // Código para insertar una persona en la base de datos
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");
        //Iniciar transacción, guardar el objeto y confirmar la transacción
        session.beginTransaction();
        session.persist(alumno);
        //Guardar el objeto persona
        session.save(alumno); 
        //Confirmar la transacción
        session.getTransaction().commit();
        //Cerrar la sesión
        session.close();
    }
    /**
     * Listar todos los alumnos de la base de datos con Hibernate
     * @return List<Alumno>
     */
    @SuppressWarnings({"rawtypes" })
    public static void getAllAlumnos() {//**************OK********************/
        // Código para obtener una persona por su ID desde la base de datos
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");
        Query query = session.createQuery("FROM Alumno", Alumno.class);
        List<Alumno> alumnos = query.getResultList();
        System.out.println("Lista de Alumnos:*****************************");
        for(Alumno alumno : alumnos) {
            System.out.println(alumno.getNombre() + " " + alumno.getApellidos() + " " + alumno.getNota() + " " + alumno.getProfesor().getNombre());
        }
        session.close();
    }
    /**
     * Listar todos los alumnos de un profesor por su ID de la base de datos con Hibernate
     * @param idProfesor
     * @return List<Alumno>
     */
    @SuppressWarnings({ "rawtypes" })
    public static void getAlumnosByProfesorId(int idProfesor) {//**************OK********************/
        // Código para obtener una persona por su ID desde la base de datos
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");
        Query query = session.createQuery("FROM Alumno WHERE profesor.idProf = :idProfesor", Alumno.class);
        query.setParameter("idProfesor", idProfesor);
        List<Alumno> alumnosDelProfesor = query.getResultList();
        System.out.printf("Lista de Alumnos del profesor con ID %d:*****************************", idProfesor);
        for(Alumno alumno : alumnosDelProfesor) {
            System.out.println(alumno.getNombre() + " " + alumno.getApellidos() + " " + alumno.getNota() + " " + alumno.getProfesor().getNombre());
        }
        session.close();
    }
    /**
     * Buscar alumnos con nota mayor o igual a la indicada
     * @param notaMinima
     */
    @SuppressWarnings({ "rawtypes" })
    public static void getAlumnosByNotaMinima(double notaMinima) {//**************OK********************/
        // Código para obtener una persona por su ID desde la base de datos
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");
        Query query = session.createQuery("FROM Alumno WHERE nota >= :notaMinima ORDER BY nota DESC", Alumno.class);
        query.setParameter("notaMinima", notaMinima);
        List<Alumno> alumnosConNotaMinima = query.getResultList();
        System.out.printf("Lista de Alumnos con nota minima %.1f:*****************************\n", notaMinima);
        for(Alumno alumno : alumnosConNotaMinima) {
            System.out.println(alumno.getNombre() + " " + alumno.getApellidos() + " " + alumno.getNota() + " " + alumno.getProfesor().getNombre());
        }
        session.close();
    }
    /**
     * Insertar alumno dependiendo de si el profesor ya existe
     * @param alumno
     * @param profesor
     */
    public static void insertAlumnoYesKnowOrNotProfesor(Alumno alumno, Profesor profesor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");
        Profesor profesorExistente = ProfesorDAO.getProfesorByObject(profesor);
        if(profesorExistente != null) {
            System.out.println("El profesor ya existe. Se usará el profesor existente.");
            alumno.setProfesor(profesorExistente);
            insertAlumno(alumno);
        } else {
            System.out.println("El profesor no existe. Se insertará un nuevo profesor.");
            System.out.println("Dame los nuevos datos del profesor.");
            Profesor nuevoProfesor = new Profesor();
            Scanner sr = new Scanner(System.in);
            System.out.println("¿Nombre del nuevo profesor?");
            nuevoProfesor.setNombre(sr.next());
            System.out.println("¿Apellidos del nuevo profesor?");
            nuevoProfesor.setApellidos(sr.next());
            System.out.println("¿Nombre del Instituto del nuevo profesor?");
            nuevoProfesor.setInstituto(sr.next());
            System.out.println("¿Asignatura que imparte el nuevo profesor?");
            nuevoProfesor.setAsignatura(sr.next());
            sr.close();
            ProfesorDAO.insertProfesorByQuery(nuevoProfesor);
            alumno.setProfesor(nuevoProfesor);
            AlumnoDAO.insertAlumno(alumno);
            System.out.println("El alumno se ha insertado correctamente.");
        }
        session.close();
    }
}
