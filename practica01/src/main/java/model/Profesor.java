package model;  // Declara el paquete (namespace) donde se encuentra la entidad Profesor

import jakarta.persistence.Column;  // Importa @Column para configurar las columnas de la tabla
import jakarta.persistence.Entity;  // Importa @Entity para marcar la clase como entidad persistente JPA
import jakarta.persistence.GeneratedValue;  // Importa @GeneratedValue para indicar generación automática de la PK
import jakarta.persistence.GenerationType;  // Importa GenerationType para definir la estrategia de generación
import jakarta.persistence.Id;  // Importa @Id para marcar la clave primaria
import jakarta.persistence.Table;  // Importa @Table para indicar el nombre de la tabla asociada

@Entity  // Indica que esta clase es una entidad JPA (se mapeará a una tabla)
@Table(name = "profesor")  // Asocia esta entidad con la tabla "profesor" en la base de datos
public class Profesor {  // Define la clase Profesor (representa una fila de la tabla profesor)
    @Id  // Marca este atributo como clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // La PK se genera automáticamente por la base de datos
    @Column(name = "idProf")  // Mapea el atributo a la columna "idProf"
    private Integer idProf;  // Atributo que almacena el identificador del profesor

    @Column(name="nombre", length=100, nullable=false)  // Columna "nombre": máximo 100 caracteres, no admite null
    private String nombre;  // Atributo que almacena el nombre del profesor

    @Column(name="apellidos", length=100, nullable=false)  // Columna "apellidos": máximo 100 caracteres, no admite null
    private String apellidos;  // Atributo que almacena los apellidos del profesor

    @Column(name="instituto", length=100, nullable=false)  // Columna "instituto": máximo 100 caracteres, no admite null
    private String instituto;  // Atributo que almacena el nombre del instituto

    @Column(name="asignatura", length=100, nullable=false)  // Columna "asignatura": máximo 100 caracteres, no admite null
    private String asignatura;  // Atributo que almacena la asignatura impartida

  // Línea en blanco para separar secciones (legibilidad)

    // Constructor vacío requerido por Hibernate  // JPA/Hibernate necesita un constructor sin argumentos
    public Profesor() {  // Constructor por defecto
    }  // Fin del constructor vacío

    public Profesor(String nombre, String apellidos, String instituto, String asignatura) {  // Constructor con datos principales
        //this.idProf = idProf;  // Comentado: la PK no se asigna manualmente (la genera la base de datos)
        this.nombre = nombre;  // Asigna el nombre recibido al atributo de la clase
        this.apellidos = apellidos;  // Asigna los apellidos recibidos
        this.instituto = instituto;  // Asigna el instituto recibido
        this.asignatura = asignatura;  // Asigna la asignatura recibida
    }  // Fin del constructor con parámetros

    public Integer getIdProf() {  // Getter del identificador del profesor
        return idProf;  // Devuelve el valor actual del idProf
    }  // Fin del getter

    public void setIdProf(Integer idProf) {  // Setter del identificador del profesor
        this.idProf = idProf;  // Asigna un nuevo valor al idProf
    }  // Fin del setter

    public String getNombre() {  // Getter del nombre
        return nombre;  // Devuelve el nombre actual
    }  // Fin del getter

    public void setNombre(String nombre) {  // Setter del nombre
        this.nombre = nombre;  // Asigna el nuevo nombre al atributo
    }  // Fin del setter

    public String getApellidos() {  // Getter de los apellidos
        return apellidos;  // Devuelve los apellidos actuales
    }  // Fin del getter

    public void setApellidos(String apellidos) {  // Setter de los apellidos
        this.apellidos = apellidos;  // Asigna los nuevos apellidos
    }  // Fin del setter

    public String getInstituto() {  // Getter del instituto
        return instituto;  // Devuelve el instituto actual
    }  // Fin del getter

    public void setInstituto(String instituto) {  // Setter del instituto
        this.instituto = instituto;  // Asigna el nuevo instituto
    }  // Fin del setter

    public String getAsignatura() {  // Getter de la asignatura
        return asignatura;  // Devuelve la asignatura actual
    }  // Fin del getter

    public void setAsignatura(String asignatura) {  // Setter de la asignatura
        this.asignatura = asignatura;  // Asigna la nueva asignatura
    }  // Fin del setter
}  // Fin de la clase Profesor
