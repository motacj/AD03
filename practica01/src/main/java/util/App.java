package util;

import java.util.List;
import dao.AlumnoDAO;
import dao.ProfesorDAO;
import model.Profesor;
import model.Alumno;

public class App 
{

    public static void main( String[] args )
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        Profesor nuevoProfesor = new Profesor("Vanessa","Santiago Gabriel","IES Ribera del Tajo","Matematicas");
        ProfesorDAO.insertProfesorByQuery(nuevoProfesor);
        List<Profesor> profesores = ProfesorDAO.getAllProfesores();
        System.out.println("Lista de Profesores:*****************************");
        for(Profesor profesor : profesores) {
            System.out.println(profesor.getIdProf() + " " + profesor.getNombre() + " " + profesor.getApellidos() + " " + profesor.getInstituto() + " " + profesor.getAsignatura());
        }
        Profesor profesorConAlumnos = ProfesorDAO.getProfesorById(1);
        System.out.println("Profesore:***************************************");
        System.out.println("Profesor con ID 1: \n\tNombre: " + profesorConAlumnos.getNombre() + "\n\tApellidos: "
            + profesorConAlumnos.getApellidos() + "\n\tInstituto: " + profesorConAlumnos.getInstituto() + "\n\tAsignatura: "
            + profesorConAlumnos.getAsignatura());
        System.out.println("Nota Meida:***************************************");
        dao.ProfesorDAO.getNotaMediaByProfesorId(1);
        System.out.println("Listado de alumnos por profesor:***************************************");
        dao.ProfesorDAO.getProfesorConAlumnosJoinExplicito(1);
        Alumno nuevoAlumno = new Alumno("Luis","Garcia Lopez",8.5,nuevoProfesor);
        AlumnoDAO.getAllAlumnos();
        AlumnoDAO.getAlumnosByProfesorId(5);
        AlumnoDAO.getAlumnosByNotaMinima(5.0);
        Profesor nullProfesor = new Profesor();
        AlumnoDAO.insertAlumnoYesKnowOrNotProfesor(nuevoAlumno, nullProfesor);
        
    }
}


