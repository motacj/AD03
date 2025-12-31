package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "profesor")
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProf")
    private Integer idProf;
    @Column(name="nombre", length=100, nullable=false)
    private String nombre;
    @Column(name="apellidos", length=100, nullable=false)
    private String apellidos;
    @Column(name="instituto", length=100, nullable=false)
    private String instituto;
    @Column(name="asignatura", length=100, nullable=false)
    private String asignatura;

    // Constructor vacío requerido por Hibernate
    public Profesor() {
    }

    public Profesor(String nombre, String apellidos, String instituto, String asignatura) {
        //this.idProf = idProf;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.instituto = instituto;
        this.asignatura = asignatura;
    }       
    public Integer getIdProf() {
        return idProf;
    }
    public void setIdProf(Integer idProf) {
        this.idProf = idProf;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getInstituto() {
        return instituto;
    }
    public void setInstituto(String instituto) {
        this.instituto = instituto;
    }
    public String getAsignatura() {
        return asignatura;
    }
    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }
}
