/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import modelos.Cursos;

public interface ICursosDAO {
    public boolean registrar(Cursos curso);
    public List<Cursos> obtener();
    public boolean actualizar(Cursos curso);
    public boolean eliminar(String[] codigos) throws SQLException;
    public Cursos buscar (int codigo);
}
