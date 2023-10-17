package modelos;

import java.sql.*;

public class Conexion {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost/test?useSSL=false&serverTimezone=UTC";
    String usuario = "root";
    String clave = "";
    
    public Connection Conectar(){
        try{
            Class.forName(driver);
            Connection xcon = DriverManager.getConnection(url, usuario, clave);
            return xcon;
        } catch (ClassNotFoundException | SQLException e){
            System.out.println(e.toString());
        }
        return null;
    }
    
    public String generarCodigo(String tabla, String campo) {       
        String rpta = "";
        String sql = "select count(*) from " + tabla;
        Connection xcon = this.Conectar();
        try {
            Statement st;
                st = xcon.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            int cant = Integer.parseInt(rs.getString(1));
            if ( cant <= 0 )
                rpta = "1";
            else {
                sql = "select max(" + campo + ") from " + tabla;
                rs = st.executeQuery(sql);
                rs.next();
                cant = Integer.parseInt(rs.getString(1)) + 1;
                rpta = "" + cant;
            }
            xcon.close();
            return rpta;
        } catch (SQLException ex) {
            System.out.println("Error al generar codigo");
            ex.printStackTrace();
        }
        return rpta;   
    }
    
    public void eliminarAlumnos( String[] codigos ) throws SQLException {
        boolean inicio;
        if ( codigos.length <= 0 )
           return;
        String sql  = "DELETE FROM alumnos WHERE codigo in ( ";
        inicio = true;
        for (String codigo : codigos) {
            if ( inicio )
                sql += "?";
            else
                sql += ",?";
            inicio = false;
        }
        sql += ")";
        Connection xcon = this.Conectar();
        PreparedStatement ps = xcon.prepareStatement(sql);
        for( int xc = 0 ; xc < codigos.length ; xc++ ) 
           ps.setString(xc+1, codigos[xc]);
        ps.executeUpdate();
        xcon.close();
    }
}
