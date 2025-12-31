package model;  // Declara el paquete (namespace) donde se encuentra la entidad Alumno

import jakarta.persistence.Entity;  // Importa @Entity para marcar la clase como entidad persistente JPA
import jakarta.persistence.GeneratedValue;  // Importa @GeneratedValue para indicar generación automática de la PK
import jakarta.persistence.GenerationType;  // Importa GenerationType para elegir la estrategia de generación de PK
import jakarta.persistence.Id;  // Importa @Id para marcar el atributo como clave primaria
import jakarta.persistence.JoinColumn;  // Importa @JoinColumn para definir la columna FK en la tabla
import jakarta.persistence.ManyToOne;  // Importa @ManyToOne para definir relación muchos-a-uno
import jakarta.persistence.Table;  // Importa @Table para indicar el nombre de la tabla asociada
import jakarta.persistence.Column;  // Importa @Column para configurar columnas (nombre, longitud, null, etc.)

@Entity  // Indica que esta clase es una entidad JPA (se mapeará a una tabla)
@Table(name = "alumno")  // Asocia esta entidad con la tabla "alumno" en la base de datos
public class Alumno {  // Define la clase Alumno (representa una fila de la tabla alumno)
    @Id  // Marca este campo como clave primaria (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // La PK se genera automáticamente por la BD (auto-increment)
    @Column(name = "idAlumno")  // Mapea este campo a la columna "idAlumno" en la tabla
    private Integer idAlumno;  // Atributo que almacena el identificador del alumno

    @Column(name="nombre", length=100, nullable=false)  // Columna "nombre": máximo 100 caracteres y no permite null
    private String nombre;  // Atributo que almacena el nombre del alumno

    @Column(name="apellidos", length=100, nullable=false)  // Columna "apellidos": máximo 100 caracteres y no permite null
    private String apellidos;   // Atributo que almacena los apellidos del alumno

    @Column(name="nota", nullable=false)  // Columna "nota": no permite null
    private double nota;  // Atributo numérico con la nota del alumno

    @ManyToOne  // Relación: muchos alumnos pueden pertenecer a un profesor
    @JoinColumn(name="idProf", nullable=false)  // Define la FK "idProf" en alumno que apunta a Profesor; no permite null
    private Profesor profesor;  // Atributo que referencia al profesor asignado (entidad relacionada)

  // Línea en blanco para separar secciones (legibilidad)

    // Constructor vacío requerido por Hibernate  // Hibernate/JPA necesita constructor sin parámetros para instanciar la entidad
    public Alumno() {  // Constructor por defecto (sin argumentos)
    }  // Fin del constructor vacío

    public Alumno(String nombre, String apellidos, double nota, Profesor profesor) {  // Constructor con campos principales
        this.nombre = nombre;  // Asigna el parámetro nombre al atributo de la clase
        this.apellidos = apellidos;  // Asigna el parámetro apellidos al atributo de la clase
        this.nota = nota;  // Asigna el parámetro nota al atributo de la clase
        this.profesor = profesor;  // Asigna el profesor asociado (relación ManyToOne)
    }  // Fin del constructor con parámetros

    public Integer getIdAlumno() {  // Getter del identificador (leer idAlumno)
        return idAlumno;  // Devuelve el valor actual del idAlumno
    }  // Fin del getter

    public void setIdAlumno(Integer idAlumno) {  // Setter del identificador (modificar idAlumno)
        this.idAlumno = idAlumno;  // Asigna el nuevo idAlumno al atributo
    }  // Fin del setter

    public String getNombre() {  // Getter del nombre (leer nombre)
        return nombre;  // Devuelve el nombre actual
    }  // Fin del getter

    public void setNombre(String nombre) {  // Setter del nombre (modificar nombre)
        this.nombre = nombre;  // Asigna el nuevo nombre al atributo
    }  // Fin del setter

    public String getApellidos() {  // Getter de apellidos (leer apellidos)
        return apellidos;  // Devuelve los apellidos actuales
    }  // Fin del getter

    public void setApellidos(String apellidos) {  // Setter de apellidos (modificar apellidos)
        this.apellidos = apellidos;  // Asigna los nuevos apellidos al atributo
    }  // Fin del setter

    public double getNota() {  // Getter de nota (leer nota)
        return nota;  // Devuelve la nota actual
    }  // Fin del getter

    public void setNota(double nota) {  // Setter de nota (modificar nota)
        this.nota = nota;  // Asigna la nueva nota al atributo
    }  // Fin del setter

    public Profesor getProfesor() {  // Getter del profesor asociado (leer relación)
        return profesor;  // Devuelve el objeto Profesor asociado al alumno
    }  // Fin del getter

    public void setProfesor(Profesor profesor) {  // Setter del profesor asociado (modificar relación)
        this.profesor = profesor;  // Asigna el nuevo profesor al alumno
    }  // Fin del setter
}  // Fin de la clase Alumno
