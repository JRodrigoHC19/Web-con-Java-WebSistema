package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Vector;
import modelos.*;
import dao.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "controladorPrincipal", urlPatterns = {"/controladorPrincipal"})
public class controladorPrincipal extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String accion = request.getParameter("accion");
        try {
            cBaseDatos objDatos = new cBaseDatos();
            
            if (accion == null){ accion = "bienvenida"; }
            
            if (accion.equals("bienvenida")) {
                if(!this.verificar(request)){ request.getRequestDispatcher("/index.html").forward(request, response); }
                
                request.getRequestDispatcher("/entorno.jsp").forward(request, response);
                
                
                
                
            } else if (accion.equals("listadoAreas")) {
                if(!this.verificar(request)){ request.getRequestDispatcher("/index.html").forward(request, response); }
                Vector arrAreas = (Vector) objDatos.getAreas();
                request.setAttribute("arrAreas", arrAreas);
                request.getRequestDispatcher("/mantenimientos/listadoAreas.jsp").forward(request, response);
                
            } else if (accion.equals("NuevoEliminarArea")) {
                if(!this.verificar(request)){ request.getRequestDispatcher("/index.html").forward(request, response); }
                if (request.getParameter("boton").compareTo("Nuevo Registro") == 0) {
                    Vector registro = new Vector();
                    registro.add("");
                    registro.add("");
                    registro.add("");
                    registro.add("");

                    request.setAttribute("registro", registro);
                    request.setAttribute("operacion", "INSERT");
                    request.setAttribute("titulo", "Nueva Area");
                    request.getRequestDispatcher("/mantenimientos/editarAreas.jsp").forward(request, response);
                    
                } else {
                    String[] datos = request.getParameterValues("xcod");
                    objDatos.eliminarAreas(datos);
                    request.getRequestDispatcher("/controladorPrincipal?accion=listadoAreas").forward(request,response );
                    
                }
            } else if ( accion.compareTo( "GRABAR_AREA") == 0 ) {
                if(!this.verificar(request)){ request.getRequestDispatcher("/index.html").forward(request, response); }
                if ( request.getParameter( "boton" ).compareTo( "GRABAR" ) == 0 ) {
                    String operacion = request.getParameter("operacion");
                    if ( operacion.equals("INSERT")) {
                       String[] datos = new String[3];
                       datos[0] = request.getParameter("xnom");
                       datos[1] = request.getParameter("xabr");
                       datos[2] = request.getParameter("xest");
                       objDatos.grabarNuevaArea(datos);
                   } else {
                        String[] datos = new String[4];
                        datos[0] = request.getParameter("xcod");
                        datos[1] = request.getParameter("xnom");
                        datos[2] = request.getParameter("xabr");
                        datos[3] = request.getParameter("xest");
                        objDatos.modificarArea(datos);
                    }

                }
                request.getRequestDispatcher("/controladorPrincipal?accion=listadoAreas").forward(request,response );
                
            } else if ( accion.compareTo( "modificarArea" ) == 0 ) {
                if(!this.verificar(request)){ request.getRequestDispatcher("/index.html").forward(request, response); }
                String xcod = request.getParameter( "xcod" );
                Vector registro = objDatos.buscarArea(xcod);

                request.setAttribute( "registro", registro );
                request.setAttribute( "operacion","UPDATE");
                request.setAttribute( "titulo","Modificar Area");
                request.getRequestDispatcher( "/mantenimientos/editarAreas.jsp" ).forward(request,response );
                
            }
            
            
            
            
            else if (accion.equals("listadoAlumnos")){
                List<Alumnos> arrAlumnos = new ArrayList<Alumnos>();
                IAlumnosDAO dao =  new AlumnoDAOImpl();
                arrAlumnos = dao.obtener();
                request.setAttribute("arrAlumnos", arrAlumnos);
                request.getRequestDispatcher( "/mantenimientos/listadoAlumnos.jsp" ).forward(request,response );
                
            } else if (accion.equals("NuevoEliminarAlumno")) {
                if(request.getParameter("boton").compareTo("Nuevo Registro") == 0){
                    Alumnos alumno = new Alumnos();
                    request.setAttribute("alumno",alumno);
                    request.setAttribute("operacion","INSERT");
                    request.setAttribute("titulo","Nuevo Alumno");
                    request.getRequestDispatcher( "/mantenimientos/editarAlumnos.jsp" ).forward(request,response );
                } else {
                    IAlumnosDAO dao =  new AlumnoDAOImpl();
                    String[] datos = request.getParameterValues("xcod");
                    dao.eliminar(datos);
                    request.getRequestDispatcher("/controladorPrincipal?accion=listadoAlumnos").forward(request,response );
                }
                
            } else if (accion.compareTo("GRABAR_ALUMNO") == 0) {
                if(request.getParameter("boton").compareTo("GRABAR") == 0){
                    String operacion = request.getParameter("operacion");
                    String strDate = request.getParameter("xfec");
                    Date xfec = Date.valueOf(strDate);
                    Alumnos alumno = new Alumnos();
                    
                    alumno.setCodigo(Integer.parseInt(request.getParameter("xcod")));
                    alumno.setNombre(request.getParameter("xnom"));
                    alumno.setDireccion(request.getParameter("xdir"));
                    alumno.setEmail(request.getParameter("xema"));
                    alumno.setTelefono(request.getParameter("xtel"));
                    alumno.setCelular(request.getParameter("xcel"));
                    alumno.setSexo(request.getParameter("xsex"));
                    alumno.setFec_nac(xfec);
                    alumno.setEstado(request.getParameter("xest"));
                    
                    if (operacion.equals("INSERT")){
                        IAlumnosDAO dao = new AlumnoDAOImpl();
                        dao.registrar(alumno);
                    } else {
                        IAlumnosDAO dao = new AlumnoDAOImpl();
                        dao.actualizar(alumno);
                    }
                }   
                request.getRequestDispatcher( "/controladorPrincipal?accion=listadoAlumnos" ).forward(request,response);
                
            } else if (accion.compareTo("modificarAlumno") == 0){
                String xcod = request.getParameter("xcod").trim();
                IAlumnosDAO dao = new AlumnoDAOImpl();
                Alumnos alumno = dao.buscar(Integer.parseInt(xcod));
                request.setAttribute("alumno", alumno);
                request.setAttribute("operacion", "UPDATE");
                request.setAttribute("titulo", "Modificar Alumno");
                request.getRequestDispatcher( "/mantenimientos/editarAlumnos.jsp" ).forward(request,response);
            }
            
            
            
            
            else if (accion.compareTo("listadoCursos") == 0) {
                List<Cursos> arrCursos = new ArrayList<Cursos>();
                ICursosDAO dao =  new CursoDAOImpl();
                arrCursos = dao.obtener();
                request.setAttribute("arrCursos", arrCursos);
                request.getRequestDispatcher( "/mantenimientos/listadoCursos.jsp" ).forward(request,response);
                    
            } else if (accion.compareTo("NuevoEliminarCurso") == 0) {
                if(request.getParameter("boton").compareTo("Nuevo Registro") == 0){
                    Cursos curso = new Cursos();
                    request.setAttribute("curso",curso);
                    request.setAttribute("operacion","INSERT");
                    request.setAttribute("titulo","Nuevo Curso");
                    request.getRequestDispatcher( "/mantenimientos/editarCursos.jsp" ).forward(request, response);
                } else {
                    ICursosDAO dao =  new CursoDAOImpl();
                    String[] datos = request.getParameterValues("xcod");
                    dao.eliminar(datos);
                    request.getRequestDispatcher("/controladorPrincipal?accion=listadoCursos").forward(request,response );
                }
            } else if (accion.compareTo("GRABAR_CURSO") == 0) {
                if(request.getParameter("boton").compareTo("GRABAR") == 0){
                    String operacion = request.getParameter("operacion");
                    String strDateini = request.getParameter("xfec_ini");
                    String strDatefin = request.getParameter("xfec_fin");
                    Date xfec_ini = Date.valueOf(strDateini);
                    Date xfec_fin = Date.valueOf(strDatefin);
                    Cursos curso = new Cursos();

                    curso.setCodigo(Integer.parseInt(request.getParameter("xcod")));
                    curso.setNombre(request.getParameter("xnom"));
                    curso.setCosto(Float.valueOf(request.getParameter("xcos")));
                    curso.setFec_ini(xfec_ini);
                    curso.setFec_fin(xfec_fin);          
                    curso.setDuracion(Integer.parseInt(request.getParameter("xdur")));
                    curso.setSesiones(Integer.parseInt(request.getParameter("xses")));
                    curso.setCapacidad(Integer.parseInt(request.getParameter("xcap")));
                    curso.setInscritos(Integer.parseInt(request.getParameter("xins")));
                    curso.setEstado(request.getParameter("xest"));

                    if (operacion.equals("INSERT")){
                        ICursosDAO dao = new CursoDAOImpl();
                        dao.registrar(curso);
                    } else {
                        ICursosDAO dao = new CursoDAOImpl();
                        dao.actualizar(curso);
                    }
                }   
                request.getRequestDispatcher( "/controladorPrincipal?accion=listadoCursos" ).forward(request,response);

            } else if (accion.compareTo("modificarCurso") == 0){
                String xcod = request.getParameter("xcod").trim();
                ICursosDAO dao = new CursoDAOImpl();
                Cursos cursos = dao.buscar(Integer.parseInt(xcod));
                request.setAttribute("curso", cursos);
                request.setAttribute("operacion", "UPDATE");
                request.setAttribute("titulo", "Modificar Curso");
                request.getRequestDispatcher( "/mantenimientos/editarCursos.jsp" ).forward(request,response);
            }
            
            
            
            
            
            
            
            
            else if(accion.compareTo("matriculaMostrarAlumnos")==0){
                if(request.getParameter("modo").compareTo("Lista")==0){
                    List<Alumnos> arrAlumnos = new ArrayList<Alumnos>();
                    Alumnos alumno = new Alumnos();
                    alumno.setNombre(" ");
                    IMatriculaDAO dao = new MatriculaDAOImpl();
                    arrAlumnos = dao.buscarAlumnos(alumno);
                    request.setAttribute("arrAlumnos", arrAlumnos);
                    request.getRequestDispatcher("/operaciones/matriculaMostrarAlumnos.jsp").forward(request, response);
                    
                } else if (request.getParameter("boton").compareTo("Buscar")==0){
                    List<Alumnos> arrAlumnos = new ArrayList<Alumnos>();
                    Alumnos alumno = new Alumnos();
                    alumno.setNombre(request.getParameter("xalu"));
                    IMatriculaDAO dao = new MatriculaDAOImpl();
                    arrAlumnos = dao.buscarAlumnos(alumno);
                    request.setAttribute("arrAlumnos", arrAlumnos);
                    request.setAttribute("nombre", alumno.getNombre());
                    request.getRequestDispatcher("/operaciones/matriculaMostrarAlumnos.jsp").forward(request, response);
                    
                } else {
                    int xcodAlumno = Integer.parseInt(request.getParameter("xcodAlumno"));
                    Alumnos alumno = new Alumnos();
                    IAlumnosDAO dao = new AlumnoDAOImpl();
                    alumno = dao.buscar(xcodAlumno);
                    List<Cursos> arrCursos = new ArrayList<Cursos>();
                    IMatriculaDAO daoMatri = new MatriculaDAOImpl();
                    arrCursos = daoMatri.buscarCursos();
                    
                    request.setAttribute("arrCursos", arrCursos);
                    request.setAttribute("alumno", alumno);
                    request.getRequestDispatcher("/operaciones/matriculaMostrarCursos.jsp").forward(request, response);
                    
                }
            } else if (accion.compareTo("matriculaMostrarSubtotal")==0){
                String xcodCursos[] = request.getParameterValues("xcodCurso");
                List<Cursos> arrCursos = new ArrayList<Cursos>();
                ICursosDAO dao = new CursoDAOImpl();
                double total = 0;
                for (int xc = 0; xc < xcodCursos.length; xc++){
                    Cursos curso = new Cursos();
                    curso = dao.buscar(Integer.parseInt(xcodCursos[xc]));
                    double costo = curso.getCosto();
                    total = total + costo;
                    arrCursos.add(curso);
                }
                int xcodAlumno = Integer.parseInt(request.getParameter("xcodAlumno"));
                Alumnos alumno = new Alumnos();
                IAlumnosDAO dao2 = new AlumnoDAOImpl();
                alumno = dao2.buscar(xcodAlumno);
                Conexion co  = new Conexion();
                String xcodMatricula = "MAT" + co.generarCodigo("matriculas","codigo" );
                request.setAttribute("arrCursos", arrCursos);
                request.setAttribute("alumno", alumno);
                request.setAttribute("total",total);
                request.setAttribute("xmatri",xcodMatricula);
                request.getRequestDispatcher("/operaciones/matriculaMostrarSubtotal.jsp").forward(request, response);
                
            } else if (accion.compareTo("matriculaGrabar")==0){
                if(request.getParameter("boton").compareTo("GRABAR")==0){
                    String xcodAlumno = request.getParameter("xcodAlumno");
                    String xcodCursos[] = request.getParameterValues("xcodCurso");
                    String xmontos[] = request.getParameterValues("xmonto");
                    
                    String[] datosMatricula = new String[3];
                    datosMatricula[0] = request.getParameter("xndoc");
                    datosMatricula[1] = xcodAlumno;
                    datosMatricula[2] = request.getParameter("xtotal");
                    
                    IMatriculaDAO dao = new MatriculaDAOImpl();
                    boolean rpta = dao.grabarMatricula(datosMatricula, xcodCursos, xmontos);
                    if(rpta){
                        out.println("<br><h2>Registro grabado correctamente!</h2>");
                    } else {
                        out.println("<br><h2>Error al grabar Matricula!</h2>");
                    }
                }
            }
            
            
            
            
            
            
            
            else if(accion.compareTo("matriculaMostrarRegistros")==0){
                if(request.getParameter("modo").compareTo("Lista")==0){
                    List<Matriculas> arrMatriculas = new ArrayList<Matriculas>();
                    Matriculas matricula = new Matriculas();
                    matricula.setNro_doc("");
                    IMatriculaDAO dao = new MatriculaDAOImpl();
                    arrMatriculas = dao.buscarMatriculas2(matricula);
                    request.setAttribute("arrMatriculas", arrMatriculas);
                    request.getRequestDispatcher("/operaciones/matriculaMostrarRegistros.jsp").forward(request, response);
                    
                } else if (request.getParameter("boton").compareTo("Buscar")==0){
                    List<Matriculas> arrMatriculas = new ArrayList<Matriculas>();
                    Matriculas matricula = new Matriculas();
                    matricula.setNro_doc(request.getParameter("xmat"));
                    IMatriculaDAO dao = new MatriculaDAOImpl();
                    arrMatriculas = dao.buscarMatriculas2(matricula);
                    request.setAttribute("arrMatriculas", arrMatriculas);
                    request.setAttribute("nombre", matricula.getNro_doc());
                    request.getRequestDispatcher("/operaciones/matriculaMostrarRegistros.jsp").forward(request, response);
                    
                } else{
                    List<Detalles> arrDetalles = new ArrayList<Detalles>();
                    List<Matriculas> arrMatricula = new ArrayList<Matriculas>();

                    String xcodMatricula = request.getParameter("xcodMatricula");
                    Matriculas matricula = new Matriculas();
                    matricula.setCodigo(Integer.parseInt(xcodMatricula));
                    matricula.setNro_doc("MAT" + xcodMatricula);
                    
                    IMatriculaDAO dao = new MatriculaDAOImpl();
                    
                    arrMatricula = dao.buscarMatriculas2(matricula);
                    arrDetalles = dao.buscarDetalles(matricula);
                    
                    request.setAttribute("arrMatricula", arrMatricula);
                    request.setAttribute("arrDetalles", arrDetalles);
                    
                    request.setAttribute("xmatri", matricula.getNro_doc());
                    request.setAttribute("xcodMat", matricula.getCodigo());
                    request.setAttribute("xcorAlm", matricula.getCorreo_alumno());
                    request.getRequestDispatcher("/operaciones/matriculaMostrarDetalles.jsp").forward(request, response);
                    
                }
            }
            
            
            
            
            else if(accion.compareTo("cerrar") == 0){
                cerrarsesion(request, response);
            } else{
                System.out.println("Accion: (" + accion + ") no reconocida...");
            }
            
        } catch(IOException | SQLException | ServletException ex) {
            System.out.println( "Error: " + ex.toString() );   
        } finally { 
             out.close();
        } 
    }
    
    private boolean verificar(HttpServletRequest request) throws SQLException{
        HttpSession sesion;
        cBaseDatos db = new cBaseDatos();
        sesion = request.getSession();
        
        if (sesion.getAttribute("nombre") == null && sesion.getAttribute("contrasenia") == null) {
            String getuser = request.getParameter("show-email");
            String getpassword = request.getParameter("show-password");

            if (db.logear(getuser, getpassword) == true){
                sesion.setAttribute("nombre",getuser);
                sesion.setAttribute("contrasenia", getpassword);
            } else {
                return false;
            }
        }
        
        return true;
    }
    
    private void cerrarsesion(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession sesion = request.getSession();
        sesion.setAttribute("nombre", null); sesion.setAttribute("contrasenia", null);
        sesion.invalidate();
        
        response.sendRedirect("/WebSistema");
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     **/
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

