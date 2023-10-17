/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import modelos.Detalles;
import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import modelos.Alumnos;
import modelos.Conexion;
import modelos.Cursos;
import modelos.Matriculas;

public class MatriculaDAOImpl implements IMatriculaDAO {
    @Override
    public List<Alumnos> buscarAlumnos(Alumnos alumno) {
        Connection co =null; Statement stm= null; ResultSet rs=null;
        String sql="SELECT * FROM alumnos WHERE nombre LIKE'%" + alumno.getNombre() + "%'";
        List<Alumnos> listaAlumnos= new ArrayList<Alumnos>();

        try {			
                Conexion con = new Conexion();
                co=con.Conectar();
                stm=co.createStatement();
                rs=stm.executeQuery(sql);
                while (rs.next()) {
                        Alumnos nalumno=new Alumnos();
                        nalumno.setCodigo(rs.getInt(1));
                        nalumno.setNombre(rs.getString(2));
                        nalumno.setDireccion(rs.getString(3));
                        nalumno.setEmail(rs.getString(4));
                        nalumno.setTelefono(rs.getString(5));
                        nalumno.setCelular(rs.getString(6));
                        nalumno.setSexo(rs.getString(7));
                        nalumno.setFec_nac(rs.getDate(8));
                        nalumno.setEstado(rs.getString(9));
                        listaAlumnos.add(nalumno);
                }
                stm.close();
                rs.close();
                co.close();
        } catch (SQLException e) {
                System.out.println("Error:Clase MatriculaDaoImpl,"
                        + "método buscarAlumnos");
        }
        return listaAlumnos;
    }

    @Override
    public List<Cursos> buscarCursos() {
        Connection co =null;
        Statement stm= null;
        ResultSet rs=null;
        String sql="SELECT * FROM cursos ORDER BY codigo";
        List<Cursos> listaCursos= new ArrayList<Cursos>();

        try {			
                Conexion con = new Conexion();
                co=con.Conectar();
                stm=co.createStatement();
                rs=stm.executeQuery(sql);
                while (rs.next()) {
                        Cursos curso=new Cursos();
                        curso.setCodigo(rs.getInt(1));
                        curso.setNombre(rs.getString(2));
                        curso.setCosto(rs.getFloat(3));
                        curso.setFec_ini(rs.getDate(4));
                        curso.setFec_fin(rs.getDate(5));
                        curso.setDuracion(rs.getInt(6));
                        curso.setSesiones(rs.getInt(7));
                        curso.setCapacidad(rs.getInt(8));
                        curso.setInscritos(rs.getInt(9));
                        curso.setEstado(rs.getString(10));
                        listaCursos.add(curso);
                }
                stm.close();
                rs.close();
                co.close();
        } catch (SQLException e) {
                System.out.println("Error:Clase MatriculaDaoImpl,"
                        + "método obtener");
        }
        return listaCursos;
    }
    
    @Override
    public List<Matriculas> buscarMatriculas(Matriculas matricula) {
        Connection co =null; Statement stm= null; ResultSet rs=null;
        String sql="SELECT * FROM matriculas WHERE nro_doc LIKE'%" + matricula.getNro_doc()+ "%'";
        List<Matriculas> listaMatriculas= new ArrayList<Matriculas>();

        try {			
                Conexion con = new Conexion();
                co=con.Conectar();
                stm=co.createStatement();
                rs=stm.executeQuery(sql);
                while (rs.next()) {
                        Matriculas nmatriculas=new Matriculas();
                        nmatriculas.setCodigo(rs.getInt(1));
                        nmatriculas.setFecha(rs.getDate(2));
                        nmatriculas.setNro_doc(rs.getString(3));
                        nmatriculas.setCodigo_alumno(rs.getInt(4));
                        nmatriculas.setTotal(rs.getFloat(5));
                        nmatriculas.setEstado(rs.getString(6));
                        listaMatriculas.add(nmatriculas);
                }
                stm.close();
                rs.close();
                co.close();
        } catch (SQLException e) {
                System.out.println("Error:Clase MatriculaDaoImpl,"
                        + "método buscarMatriculas");
        }
        return listaMatriculas;
    }

    @Override
    public boolean grabarMatricula(String[] datosMatricula, String[] codigoCursos, String[] montos) {
        Conexion co = new Conexion();
        String xcodm = co.generarCodigo("matriculas","codigo");
        boolean registrar = true;
	Statement stm= null;
        Connection con=null;      
        
        String sql = "insert into matriculas (codigo,fecha,nro_doc," +
                 "codigo_alumno,total,estado) values (?,?,?,?,?,'A') ";
        String xfech = this.getFecha();
        String xndoc = datosMatricula[0];
        String xcoda = datosMatricula[1];
        String xtota = datosMatricula[2];

        try {			    
            con=co.Conectar();
            stm= con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, xcodm);
            ps.setString(2, xfech);
            ps.setString(3, xndoc);
            ps.setString(4, xcoda);
            ps.setString(5, xtota);
            ps.executeUpdate();
            
            for( int xc=0 ; xc < codigoCursos.length ; xc++ ){
                String xcodCurso = codigoCursos[xc];
                this.grabarNuevoDetalle(con,xcodm,xcodCurso,montos[xc]);
            }

            
            stm.close();
            con.close();
        } catch (SQLException e) {
                System.out.println("Error: Clase MatriculaDaoImpl, "
                        + "método grabarMatricula");
                e.printStackTrace();
                return false;
        }
        return registrar;
    }
    
    @Override
    public List<Matriculas> buscarMatriculas2(Matriculas matricula) {
        Connection co =null; Statement stm= null; ResultSet rs=null;
        String sql="SELECT matriculas.codigo, matriculas.fecha, matriculas.nro_doc, a.email, matriculas.total, matriculas.estado, matriculas.codigo_alumno FROM matriculas "
                + "INNER JOIN alumnos a ON matriculas.codigo_alumno = a.codigo "
                + "WHERE matriculas.nro_doc LIKE'%" + matricula.getNro_doc() + "%'";
        List<Matriculas> listaMatriculas= new ArrayList<Matriculas>();

        try {			
                Conexion con = new Conexion();
                co=con.Conectar();
                stm=co.createStatement();
                rs=stm.executeQuery(sql);
                while (rs.next()) {
                        Matriculas nmatriculas=new Matriculas();
                        nmatriculas.setCodigo(rs.getInt(1));
                        nmatriculas.setFecha(rs.getDate(2));
                        nmatriculas.setNro_doc(rs.getString(3));
                        nmatriculas.setCorreo_alumno(rs.getString(4));
                        nmatriculas.setTotal(rs.getFloat(5));
                        nmatriculas.setEstado(rs.getString(6));
                        nmatriculas.setCodigo_alumno(rs.getInt(7));
                        listaMatriculas.add(nmatriculas);
                }
                stm.close();
                rs.close();
                co.close();
        } catch (SQLException e) {
                System.out.println("Error:Clase MatriculaDaoImpl, método buscarMatriculas, " + e);
        }
        return listaMatriculas;
    }

    @Override
    public List<Detalles> buscarDetalles(Matriculas matricula) {
        Connection co =null;
        Statement stm= null;
        ResultSet rs=null;
        String sql="SELECT detalles.codigo_matricula, detalles.codigo_curso, detalles.monto, detalles.asistencias, detalles.nota, detalles.estado, c.nombre "
                + "FROM detalles "
                + "INNER JOIN cursos c ON detalles.codigo_curso = c.codigo "
                + "WHERE detalles.codigo_matricula="+ matricula.getCodigo();
        List<Detalles> listaDetalles= new ArrayList<Detalles>();

        try {			
                Conexion con = new Conexion();
                co=con.Conectar();
                stm=co.createStatement();
                rs=stm.executeQuery(sql);
                while (rs.next()) {
                        Detalles detalle=new Detalles();
                        detalle.setCodigo_matricula(rs.getInt(1));
                        detalle.setCodigo_curso(rs.getInt(2));
                        detalle.setMonto(rs.getFloat(3));
                        detalle.setAsistencias(rs.getInt(4));
                        detalle.setNota(rs.getInt(5));
                        detalle.setEstado(rs.getString(6));
                        detalle.setNombre_curso(rs.getString(7));
                        listaDetalles.add(detalle);
                }
                stm.close();
                rs.close();
                co.close();
        } catch (SQLException e) {
                System.out.println("Error:Clase MatriculaDaoImpl, método buscarDetalles, " + e);
        }
        return listaDetalles;
    }
    
    
    
    public void grabarNuevoDetalle( Connection xcon,String xcodm, String xcodc,String xmonto ) 
        throws SQLException {
        String sql = "insert into detalles (codigo_matricula,codigo_curso," +
                     "monto,asistencias,nota,estado) values (?,?,?,0,0,'A') ";

        PreparedStatement ps = xcon.prepareStatement(sql);
        ps.setString(1, xcodm);
        ps.setString(2, xcodc);
        ps.setString(3, xmonto);
        ps.executeUpdate();

        // actualizar nro de inscritos en curso
        sql = "update cursos set inscritos=inscritos+1 where codigo=?";
        PreparedStatement psc = xcon.prepareStatement(sql);
        psc.setString(1, xcodc);
        psc.executeUpdate();
    }

    public String getFecha() {
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        DateFormat formato = new SimpleDateFormat( "yyyy-MM-dd" );
        return formato.format( date ) ;
    }

}
