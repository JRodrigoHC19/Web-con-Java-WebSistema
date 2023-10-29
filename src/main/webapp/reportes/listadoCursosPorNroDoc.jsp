<%@page import="net.sf.jasperreports.engine.JasperRunManager"%>
<%@page import="modelos.Conexion" %>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>

<%
 Conexion con = new Conexion();
 String accion = request.getParameter("accion");
 
if (accion.compareTo ("MOSTRAR") == 0) {
    File reportFile = new File(application.getRealPath( 
                                 "/reportes/listadoMatriculas.jasper"));
    Map parameters = new HashMap();
    //-------------------------------------------
    String nro_doc=request.getParameter("nro-doc");
    parameters.put("p_nro", nro_doc);

    byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath (),
                      parameters, con.Conectar());
    response.setContentType("application/pdf");
    response.setContentLength(bytes.length);
    ServletOutputStream ouputStream = response.getOutputStream();
    ouputStream.write(bytes, 0, bytes.length);
    ouputStream.flush();
    ouputStream.close();
 }
%>
