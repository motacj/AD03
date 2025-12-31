package dao;  // Declara el paquete (namespace) donde vive esta clase (dao = capa de acceso a datos)

import java.util.List;  // Importa List para manejar colecciones de resultados
import java.util.Scanner;  // Importa Scanner para leer datos desde consola
import org.hibernate.Session;  // Importa Session de Hibernate para abrir sesión con la BBDD
import org.hibernate.query.Query;  // Importa Query para ejecutar consultas HQL/JPQL
import model.Alumno;  // Importa la entidad Alumno (tabla/objeto persistente)
import util.HibernateUtil;  // Importa utilidad que crea/gestiona el SessionFactory
import model.Profesor;  // Importa la entidad Profesor (relación con Alumno)

public class AlumnoDAO {  // Define la clase DAO (Data Access Object) para operaciones con Alumno
    /**  // Inicio de comentario JavaDoc (documentación del método)
     * Inserta una nueva persona en la base de datos con Hibernate  // Explica el objetivo del método (insertar alumno)
     * @param alumno  // Parámetro: objeto Alumno a persistir en la base de datos
     */  // Fin del JavaDoc
    @SuppressWarnings("deprecation")  // Suprime avisos de compilación sobre APIs marcadas como obsoletas
    public static void insertAlumno(Alumno alumno) {//**************OK********************/  // Método estático para insertar un alumno
        // Código para insertar una persona en la base de datos  // Comentario: describe la intención del bloque
        Session session = HibernateUtil.getSessionFactory().openSession();  // Abre una sesión de Hibernate (conexión/unidad de trabajo)
        //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");  // Mensaje de depuración (comentado)
        //Iniciar transacción, guardar el objeto y confirmar la transacción  // Comentario: resume el flujo típico con transacciones
        session.beginTransaction();  // Inicia una transacción (necesaria para insertar/actualizar/borrar)
        session.persist(alumno);  // Marca el objeto como persistente (JPA-style) para que se inserte
        //Guardar el objeto persona  // Comentario: explica la siguiente instrucción
        session.save(alumno);  // Guarda el objeto (Hibernate-style); aquí es redundante con persist()
        //Confirmar la transacción  // Comentario: explica la siguiente instrucción
        session.getTransaction().commit();  // Confirma la transacción y aplica cambios en la BBDD
        //Cerrar la sesión  // Comentario: explica la siguiente instrucción
        session.close();  // Cierra la sesión y libera recursos/conexión
    }  // Fin del método insertAlumno

    /**  // Inicio JavaDoc
     * Listar todos los alumnos de la base de datos con Hibernate  // Describe: obtener y mostrar todos los alumnos
     * @return List<Alumno>  // Indica tipo de retorno esperado (aunque el método realmente no retorna nada)
     */  // Fin JavaDoc
    @SuppressWarnings({"rawtypes" })  // Suprime warnings (raw types) por el uso de Query sin genéricos explícitos
    public static void getAllAlumnos() {//**************OK********************/  // Método estático que lista alumnos en consola
        // Código para obtener una persona por su ID desde la base de datos  // Comentario heredado (aquí en realidad lista todos)
        Session session = HibernateUtil.getSessionFactory().openSession();  // Abre sesión con la base de datos
        //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");  // Depuración (comentado)
        Query query = session.createQuery("FROM Alumno", Alumno.class);  // Crea consulta HQL para traer todos los Alumno
        List<Alumno> alumnos = query.getResultList();  // Ejecuta la consulta y obtiene la lista de alumnos
        System.out.println("\n\n*****************************Listado de Alumnos:*****************************");  // Imprime cabecera en consola
        System.out.printf("%-15s %-20s %-6s %-20s%n","Nombre", "Apellidos", "Nota", "Profesor");  // Imprime columnas formateadas
        System.out.println("------------------------------------------------------------------------------------");  // Separador visual
        for(Alumno alumno : alumnos) {  // Recorre cada alumno recuperado
            System.out.printf("%-15s %-20s %-6.2f %-20s%n",  // Imprime una fila por alumno con formato
                alumno.getNombre(),  // Obtiene el nombre del alumno
                alumno.getApellidos(),  // Obtiene los apellidos del alumno
                alumno.getNota(),  // Obtiene la nota del alumno
                alumno.getProfesor().getNombre());  // Obtiene el nombre del profesor asociado (relación Alumno->Profesor)
        }  // Fin del bucle for
        System.out.println("------------------------------------------------------------------------------------");  // Separador final
        session.close();  // Cierra la sesión para liberar recursos
    }  // Fin del método getAllAlumnos

    /**  // Inicio JavaDoc
     * Listar todos los alumnos de un profesor por su ID de la base de datos con Hibernate  // Describe: filtrar por profesor
     * @param idProfesor  // Parámetro: ID del profesor por el que se filtra
     * @return List<Alumno>  // Indica retorno (pero el método no retorna; solo imprime)
     */  // Fin JavaDoc
    @SuppressWarnings({ "rawtypes" })  // Suprime warnings de tipos sin parametrizar
    public static void getAlumnosByProfesorId(int idProfesor) {//**************OK********************/  // Lista alumnos de un profesor concreto
        // Código para obtener una persona por su ID desde la base de datos  // Comentario (aquí filtra alumnos por profesor)
        Session session = HibernateUtil.getSessionFactory().openSession();  // Abre sesión
        //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");  // Depuración (comentado)
        Query query = session.createQuery("FROM Alumno WHERE profesor.idProf = :idProfesor", Alumno.class);  // Consulta HQL con parámetro
        query.setParameter("idProfesor", idProfesor);  // Asigna el valor al parámetro :idProfesor
        List<Alumno> alumnosDelProfesor = query.getResultList();  // Ejecuta la consulta y obtiene alumnos del profesor
        System.out.printf("\n\n*****************************Lista de Alumnos del profesor con ID %d:*****************************\n", idProfesor);  // Título con ID
        System.out.printf("%-15s %-20s %-6s %-20s%n","Nombre", "Apellidos", "Nota", "Profesor");  // Cabecera de columnas
        System.out.println("------------------------------------------------------------------------------------");  // Separador
        for(Alumno alumno : alumnosDelProfesor) {  // Recorre los alumnos del profesor
            System.out.printf("%-15s %-20s %-6.2f %-20s%n",  // Imprime datos del alumno
                alumno.getNombre(),  // Nombre del alumno
                alumno.getApellidos(),  // Apellidos del alumno
                alumno.getNota(),  // Nota del alumno
                alumno.getProfesor().getNombre());  // Nombre del profesor asociado
        }  // Fin del bucle
        System.out.println("------------------------------------------------------------------------------------");  // Separador final
        session.close();  // Cierra la sesión
    }  // Fin del método getAlumnosByProfesorId

    /**  // Inicio JavaDoc
     * Buscar alumnos con nota mayor o igual a la indicada  // Describe: filtrar por nota mínima
     * @param notaMinima  // Parámetro: nota mínima que debe cumplir el alumno
     */  // Fin JavaDoc
    @SuppressWarnings({ "rawtypes" })  // Suprime warnings de raw types
    public static void getAlumnosByNotaMinima(double notaMinima) {//**************OK********************/  // Lista alumnos con nota >= notaMinima
        // Código para obtener una persona por su ID desde la base de datos  // Comentario (aquí filtra por nota)
        Session session = HibernateUtil.getSessionFactory().openSession();  // Abre sesión
        //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");  // Depuración (comentado)
        Query query = session.createQuery("FROM Alumno WHERE nota >= :notaMinima ORDER BY nota DESC", Alumno.class);  // Consulta con filtro y orden
        query.setParameter("notaMinima", notaMinima);  // Asigna el parámetro :notaMinima
        List<Alumno> alumnosConNotaMinima = query.getResultList();  // Ejecuta y obtiene resultados
        System.out.printf("\n\n*****************************Lista de Alumnos con nota minima %.1f::*****************************\n", notaMinima);  // Título con nota
        System.out.printf("%-15s %-20s %-6s %-20s%n","Nombre", "Apellidos", "Nota", "Profesor");  // Cabecera de columnas
        System.out.println("------------------------------------------------------------------------------------");  // Separador
        for(Alumno alumno : alumnosConNotaMinima) {  // Recorre alumnos filtrados
            System.out.printf("%-15s %-20s %-6.2f %-20s%n",  // Imprime fila con formato
                alumno.getNombre(),  // Nombre
                alumno.getApellidos(),  // Apellidos
                alumno.getNota(),  // Nota
                alumno.getProfesor().getNombre());  // Profesor asociado
        }  // Fin del bucle
        System.out.println("------------------------------------------------------------------------------------");  // Separador final
        session.close();  // Cierra sesión
    }  // Fin del método getAlumnosByNotaMinima

    /**  // Inicio JavaDoc
     * Insertar alumno dependiendo de si el profesor ya existe  // Describe: inserta alumno reutilizando o creando profesor
     * @param alumno  // Parámetro: alumno a insertar
     * @param profesor  // Parámetro: profesor propuesto (puede existir o no)
     */  // Fin JavaDoc
    public static void insertAlumnoYesKnowOrNotProfesor(Alumno alumno, Profesor profesor) {  // Método que decide si usa profesor existente o crea uno nuevo
        Session session = HibernateUtil.getSessionFactory().openSession();  // Abre sesión (aunque en este método no se usa para persistir directamente)
        //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");  // Depuración (comentado)
        Profesor profesorExistente = ProfesorDAO.getProfesorByObject(profesor);  // Busca si el profesor ya existe según criterios del DAO
        if(profesorExistente != null) {  // Si se ha encontrado un profesor existente
            System.out.printf("\n\n*****************************Insertar alumno con profesor existente*****************************\n");  // Mensaje informativo
            System.out.println("El profesor ya existe. Se usará el profesor existente.");  // Explica lo que ocurrirá
            alumno.setProfesor(profesorExistente);  // Asocia el alumno al profesor ya existente
            insertAlumno(alumno);  // Inserta el alumno (con profesor ya asociado)
        } else {  // Si el profesor no existe en la base de datos
            System.out.printf("\n\n*****************************Insertar alumno sin profesor existente*****************************\n");  // Mensaje informativo
            System.out.println("El profesor no existe. Se insertará un nuevo profesor.");  // Explica la acción
            System.out.println("Dame los nuevos datos del profesor.");  // Pide datos por consola
            Profesor nuevoProfesor = new Profesor();  // Crea un objeto Profesor vacío para rellenar
            Scanner sr = new Scanner(System.in);  // Crea un Scanner para leer desde teclado
            System.out.println("¿Nombre del nuevo profesor?");  // Pregunta por nombre
            nuevoProfesor.setNombre(sr.next());  // Lee el nombre y lo asigna al profesor
            System.out.println("¿Apellidos del nuevo profesor?");  // Pregunta por apellidos
            nuevoProfesor.setApellidos(sr.next());  // Lee apellidos y los asigna
            System.out.println("¿Nombre del Instituto del nuevo profesor?");  // Pregunta por instituto
            nuevoProfesor.setInstituto(sr.next());  // Lee instituto y lo asigna
            System.out.println("¿Asignatura que imparte el nuevo profesor?");  // Pregunta por asignatura
            nuevoProfesor.setAsignatura(sr.next());  // Lee asignatura y la asigna
            sr.close();  // Cierra el Scanner (cierra también System.in en muchos casos)
            ProfesorDAO.insertProfesorByQuery(nuevoProfesor);  // Inserta el nuevo profesor en la base de datos
            alumno.setProfesor(nuevoProfesor);  // Asocia el alumno al profesor recién creado
            AlumnoDAO.insertAlumno(alumno);  // Inserta el alumno en la base de datos
            System.out.println("El alumno se ha insertado correctamente.");  // Confirmación por consola
        }  // Fin del if/else
        session.close();  // Cierra la sesión abierta al inicio del método
    }  // Fin del método insertAlumnoYesKnowOrNotProfesor
}  // Fin de la clase AlumnoDAO
