package dao;  // Declara el paquete (namespace) de esta clase (capa DAO)

  // Línea en blanco para separar secciones (mejora legibilidad)

import model.Profesor;  // Importa la entidad Profesor (objeto persistente / tabla)
import model.Alumno;  // Importa la entidad Alumno (se usa en joins y consultas)
import util.HibernateUtil;  // Importa utilidad para obtener el SessionFactory
import org.hibernate.query.Query;  // Importa Query (Hibernate) para consultas HQL/JPQL
import java.util.List;  // Importa List para manejar colecciones de resultados
import org.hibernate.PersistentObjectException;  // Importa excepción: problemas con objetos persistentes
import org.hibernate.QueryException;  // Importa excepción: errores de consulta HQL
import org.hibernate.Session;  // Importa Session: sesión de trabajo con Hibernate
import org.hibernate.SessionException;  // Importa excepción: errores relacionados con la sesión
import org.hibernate.exception.ConstraintViolationException;  // Importa excepción: violación de constraints (PK/UNIQUE/FK)
import dao.ProfesorDAO;  // Importa la propia clase (realmente redundante si está en el mismo paquete)
import jakarta.persistence.EntityExistsException;  // Importa excepción JPA: entidad ya existe al persistir

public class ProfesorDAO {  // Define la clase DAO para operaciones CRUD/consultas sobre Profesor
    /**  // Inicio JavaDoc (documentación del método)
     * Listar todos los profesores de la base de datos con Hibernate  // Describe qué hace el método
     * @return List<Profesor>  // Devuelve una lista con todos los profesores
     */  // Fin JavaDoc
    @SuppressWarnings({ "rawtypes" })//**************OK********************/  // Suprime warnings por uso de tipos raw en Query
    public static List<Profesor> getAllProfesores() {  // Método estático: obtiene e imprime todos los profesores
        // Código para obtener una persona por su ID desde la base de datos  // Comentario heredado (aquí lista todos)
        Session session = HibernateUtil.getSessionFactory().openSession();  // Abre una sesión con la BBDD
        //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");  // Mensaje de depuración (comentado)
        Query query = session.createQuery("FROM Profesor", Profesor.class);  // Consulta HQL: trae todos los profesores
        List<Profesor> profesores = query.getResultList();  // Ejecuta consulta y recupera lista
        if(profesores.isEmpty()) {  // Comprueba si no hay registros
            System.out.println("*****************************Lista de Profesores:*****************************");  // Imprime cabecera
            System.out.println("No hay profesores en la base de datos.");  // Mensaje informando que no hay datos
        } else {  // Si sí hay profesores
            System.out.println("*****************************Lista de Profesores:*****************************");  // Cabecera del listado
            System.out.printf("%-5s %-15s %-20s %-30s %-20s%n", "ID", "Nombre", "Apellidos", "Instituto", "Asignatura"  // Imprime títulos de columnas
            );  // Cierra la llamada a printf (formato de cabecera)
            System.out.println("------------------------------------------------------------------------------------");  // Separador visual
            for(Profesor profesor : profesores) {  // Recorre cada profesor
                System.out.printf("%-5d %-15s %-20s %-30s %-20s%n",  // Imprime una fila con formato
                        profesor.getIdProf(),  // Obtiene el id del profesor
                        profesor.getNombre(),  // Obtiene el nombre
                        profesor.getApellidos(),  // Obtiene los apellidos
                        profesor.getInstituto(),  // Obtiene el instituto
                        profesor.getAsignatura());  // Obtiene la asignatura
            }  // Fin del bucle for
            System.out.println("------------------------------------------------------------------------------------");  // Separador final
        }  // Fin del if/else
        session.close();  // Cierra la sesión para liberar recursos
        return profesores; // Implementar la lógica para recuperar la persona   // Devuelve la lista recuperada
    }  // Fin del método getAllProfesores

    /**  // Inicio JavaDoc
     * Llama a un elemento Profesor de la base de datos por su ID  // Describe: buscar profesor por ID
     * @param idProf  // Parámetro: ID del profesor
     * @return Profesor  // Devuelve el profesor o null si no existe
     */  // Fin JavaDoc
    public static Profesor getProfesorById(int idProf) {//**************OK********************/  // Obtiene profesor por clave primaria
        // Código para obtener una persona por su ID desde la base de datos  // Comentario: búsqueda por ID
        Session session = HibernateUtil.getSessionFactory().openSession();  // Abre sesión de Hibernate
        //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");  // Depuración (comentado)
        Profesor profesor = session.get(Profesor.class, idProf);  // Recupera por PK; devuelve null si no existe
        session.close();  // Cierra la sesión
        if(profesor == null) {  // Si no se encontró
            System.out.println("El con ID " + idProf + " no existe.");  // Mensaje informativo (falta la palabra “profesor”)
        }  // Fin del if
        return profesor; // Implementar la lógica para recuperar la persona   // Devuelve el profesor (o null)
    }  // Fin del método getProfesorById

    /**  // Inicio JavaDoc
     * inserta un nuevo profesor en la base de datos con Hibernate comprobando antes si ya existe  // Inserta si no existe
     * @param idProf   // Comentario: el JavaDoc indica idProf, pero el método recibe un Profesor (inconsistencia documental)
     */  // Fin JavaDoc
    @SuppressWarnings({ "deprecation", "rawtypes" })  // Suprime warnings por API obsoleta (save) y raw types
    public static void insertProfesorByQuery(Profesor profesor) {//**************OK********************/  // Inserta profesor si no existe (buscando por campos)
        // Código para obtener una persona por su ID desde la base de datos  // Comentario heredado (aquí inserta por búsqueda previa)
            try{  // Inicio del bloque try para capturar errores comunes
                Session session = HibernateUtil.getSessionFactory().openSession();  // Abre sesión
                //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");  // Depuración (comentado)
                Query query = session.createQuery("FROM Profesor WHERE nombre = :nombre " +  // Consulta HQL con parámetros (búsqueda por atributos)
                            "  AND apellidos = :apellidos " +  // Condición por apellidos
                            "  AND instituto = :instituto " +  // Condición por instituto
                            "  AND asignatura = :asignatura",  // Condición por asignatura
                            Profesor.class);  // Indica que devuelve entidades Profesor
                query.setParameter("nombre", profesor.getNombre());  // Asigna valor al parámetro :nombre
                query.setParameter("apellidos", profesor.getApellidos());  // Asigna valor al parámetro :apellidos
                query.setParameter("instituto", profesor.getInstituto());  // Asigna valor al parámetro :instituto
                query.setParameter("asignatura", profesor.getAsignatura());  // Asigna valor al parámetro :asignatura
                List<Profesor> profesores = query.getResultList();  // Ejecuta consulta y obtiene coincidencias
                if(profesores.isEmpty()) {  // Si no hay coincidencias (no existe)
                    System.out.println("El profesor no existe.\n Se procedera a insertarla.");  // Informa que se insertará (texto dice “insertarla”)
                    session.beginTransaction();  // Inicia transacción (necesaria para insertar)
                    session.persist(profesor);  // Marca la entidad como persistente (JPA-style)
                    //Guardar el objeto persona  // Comentario: describe lo que viene
                    session.save(profesor);  // Guarda la entidad (Hibernate-style); es redundante con persist()
                    //Confirmar la transacción  // Comentario: describe lo que viene
                    session.getTransaction().commit();  // Confirma la transacción (aplica inserción)
                    System.out.println("Profesor insertado: " + profesor.getNombre() + " "  // Imprime confirmación con datos del profesor
                    + profesor.getApellidos() + " " + profesor.getInstituto() + " " + profesor.getAsignatura());  // Completa el mensaje con campos
                } else {  // Si ya existe al menos uno
                    System.out.println(profesores.size() + " profesor(es) encontrado(s).\n No se insetara el registro porque ya existe");  // Informa que no inserta
                }  // Fin del if/else
                session.close();  // Cierra sesión
            } catch (NullPointerException e) {  // Captura nulls (objeto o campos no inicializados)
                System.out.println("Error: objeto o atributo que no ha sido inicializado\n" + e.getMessage());  // Mensaje de error por null
            } catch (IllegalStateException e) {  // Captura estado inválido (p. ej., transacción/sesión mal gestionada)
                System.out.println("Error: la sesión de Hibernate no está en un estado válido\n" + e.getMessage());  // Mensaje del error
            } catch (EntityExistsException e) {  // Captura intento de persistir entidad ya existente (JPA)
                System.out.println("Error: el profesor ya existe en la base de datos\n" + e.getMessage());  // Mensaje del error
            } catch (PersistentObjectException e) {  // Captura error de Hibernate por objeto persistente inválido
                System.out.println("Error: el objeto persistente no es válido\n" + e.getMessage());  // Mensaje del error
            } catch (ConstraintViolationException e) {  // Captura violación de constraints (PK/UNIQUE/FK)
                System.out.println("Error: violación de restricción en la base de datos\n" + e.getMessage());  // Mensaje del error
            } catch (QueryException e) {  // Captura problemas en la consulta HQL (sintaxis, propiedades, etc.)
                System.out.println("Error: problema con la consulta HQL\n" + e.getMessage());  // Mensaje del error
            } catch (SessionException e) {  // Captura errores de sesión (Hibernate)
                System.out.println("Error: problema con la sesión de Hibernate\n" + e.getMessage());  // Mensaje del error
            }  // Fin del try/catch
        
    }  // Fin del método insertProfesorByQuery

    /**  // Inicio JavaDoc
     * Nota media de los alumnos de un profesor  // Describe: calcular nota media
     * @param idProf  // Parámetro en JavaDoc (realmente el método recibe idProfesor)
     */  // Fin JavaDoc
    @SuppressWarnings({ "rawtypes" })  // Suprime warnings raw types
    public static void getNotaMediaByProfesorId(int idProfesor) {//**************OK********************/  // Calcula la media de notas de alumnos de un profesor
        // Código para obtener una persona por su ID desde la base de datos  // Comentario heredado (aquí es una agregación AVG)
        Session session = HibernateUtil.getSessionFactory().openSession();  // Abre sesión
        //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");  // Depuración (comentado)
        Query query = session.createQuery("SELECT AVG(a.nota) FROM Alumno a WHERE a.profesor.idProf = :idProfesor", Double.class);  // Consulta HQL con AVG
        query.setParameter("idProfesor", idProfesor);  // Asigna el parámetro del profesor
        Double notaMedia = (Double) query.getSingleResult();  // Obtiene un único resultado (la media)
        session.close();  // Cierra sesión
        if(notaMedia != null) {  // Si la media existe (puede ser null si no hay filas según DB/consulta)
            System.out.printf("La nota media de los alumnos del profesor con ID %d es: %.2f\n" ,idProfesor ,notaMedia);  // Imprime la media
        } else {  // Si no hay media (p. ej., no tiene alumnos)
            System.out.printf("El profesor con ID %d no tiene alumnos.\n",idProfesor);  // Mensaje alternativo
        }  // Fin del if/else
    }  // Fin del método getNotaMediaByProfesorId

    /**  // Inicio JavaDoc
     * Profesor con sus alumnos en un JOIN explicito  // Describe: consulta join explícita
     * @param idProf  // Parámetro documentado (el método recibe idProfesor)
     */  // Fin JavaDoc
    @SuppressWarnings({ "rawtypes" })  // Suprime warnings raw types
    public static void getProfesorConAlumnosJoinExplicito(int idProfesor) {//**************OK********************/  // Muestra profesor y alumnos mediante JOIN explícito
        // Código para obtener una persona por su ID desde la base de datos  // Comentario heredado (aquí hace join)
        Session session = HibernateUtil.getSessionFactory().openSession();  // Abre sesión
        //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");  // Depuración (comentado)
        Query query = session.createQuery("SELECT p, a FROM Profesor p JOIN Alumno a ON p.idProf = a.profesor.idProf WHERE " +  // Consulta HQL: devuelve pares (Profesor, Alumno)
            "p.idProf = :idProfesor", Object[].class);  // Devuelve cada fila como Object[] (dos posiciones: p y a)
        query.setParameter("idProfesor", idProfesor);  // Asigna el parámetro de filtro
        List<Object[]> resultados = query.getResultList();  // Ejecuta la consulta y obtiene la lista de filas
        for (Object[] fila : resultados) {  // Recorre cada fila (par profesor-alumno)
            Profesor profesor = (Profesor) fila[0];  // Extrae el profesor de la posición 0
            Alumno alumno = (Alumno) fila[1];  // Extrae el alumno de la posición 1
            System.out.println("Profesor: " + profesor.getNombre() + " " + profesor.getApellidos() +  // Imprime datos del profesor
                " - Alumno: " + alumno.getNombre() + " " + alumno.getApellidos() + " Nota: " + alumno.getNota());  // Imprime datos del alumno y nota
        }  // Fin del bucle
        session.close();  // Cierra la sesión
    }  // Fin del método getProfesorConAlumnosJoinExplicito

    /**  // Inicio JavaDoc
     * Buscar profesor por objeto profesor  // Describe: buscar un profesor por sus atributos
     * @param profesor  // Parámetro: objeto profesor con campos usados como filtro
     * @return Profesor  // Devuelve el profesor encontrado o null
     */  // Fin JavaDoc
    public static Profesor getProfesorByObject(Profesor profesor) {//**************OK********************/  // Busca profesor por (nombre, apellidos, instituto, asignatura)
        // Código para obtener una persona por su ID desde la base de datos  // Comentario heredado (aquí busca por atributos)
        Session session = HibernateUtil.getSessionFactory().openSession();  // Abre sesión
        //System.out.println("Conexion exitosa a la base de datos H2 con Hibernate");  // Depuración (comentado)
        Query<Profesor> query = session.createQuery("FROM Profesor WHERE nombre = :nombre " +  // Consulta HQL parametrizada
                            "  AND apellidos = :apellidos " +  // Condición por apellidos
                            "  AND instituto = :instituto " +  // Condición por instituto
                            "  AND asignatura = :asignatura",  // Condición por asignatura
                            Profesor.class);  // Tipo de retorno: Profesor
        query.setParameter("nombre", profesor.getNombre());  // Asigna parámetro :nombre
        query.setParameter("apellidos", profesor.getApellidos());  // Asigna parámetro :apellidos
        query.setParameter("instituto", profesor.getInstituto());  // Asigna parámetro :instituto
        query.setParameter("asignatura", profesor.getAsignatura());  // Asigna parámetro :asignatura
        List<Profesor> profesores = query.getResultList();  // Ejecuta la consulta y obtiene lista de coincidencias
        session.close();  // Cierra sesión
        if(profesores.isEmpty()) {  // Si no se encontró ningún profesor con esos datos
            System.out.println("El profesor no existe.");  // Mensaje informativo
            return null;  // Devuelve null para indicar “no encontrado”
        } else {  // Si se encontró al menos uno
            System.out.println(profesores.size() + " profesor(es) encontrado(s).");  // Informa cuántos coincidieron
            return profesores.get(0);  // Devuelve el primero (asume que debería ser único)
        }  // Fin del if/else
    }  // Fin del método getProfesorByObject
}  // Fin de la clase ProfesorDAO
