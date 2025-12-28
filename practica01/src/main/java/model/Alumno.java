package model;

import jakarta.persistence.Column;

public class Alumno {

    private Integer idAlumno;
    @Column(name="nombre", length=100, nullable=false)
    private String nombre;
    @Column(name="apellidos", length=100, nullable=false)
    private String apellidos;
    private double nota;
    private Profesor profesor;

    // Constructor vacío requerido por Hibernate
    public Alumno() {
    }

    public Alumno(String nombre, String apellidos, double nota, Profesor profesor) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nota = nota;
        this.profesor = profesor;
    }

    public Integer getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
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

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
}