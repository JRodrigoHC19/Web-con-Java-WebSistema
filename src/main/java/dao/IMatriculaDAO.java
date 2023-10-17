/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import modelos.Detalles;
import java.util.List;
import modelos.Alumnos;
import modelos.Cursos;
import modelos.Matriculas;

public interface IMatriculaDAO {
    public List<Alumnos> buscarAlumnos(Alumnos alumno);
    public List<Cursos> buscarCursos();
    public List<Matriculas> buscarMatriculas(Matriculas matricula);
    public boolean grabarMatricula(String[] datosMatricula, String[] codigosCurso, String[] montos);
    
    public List<Matriculas> buscarMatriculas2(Matriculas matricula);
    public List<Detalles> buscarDetalles(Matriculas matricula);
}
