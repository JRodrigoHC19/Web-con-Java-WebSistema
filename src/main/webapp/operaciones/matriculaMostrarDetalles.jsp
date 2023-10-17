<%-- 
    Document   : matriculaMostrarSubtotal
    Created on : 16 oct. 2023, 18:55:26
    Author     : prett
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="total" value="0"/>

<!DOCTYPE html>
<html>
    <head>
         <link rel="stylesheet" 
              href="webjars/bootstrap/5.1.0/css/bootstrap.min.css" type="text/css" />
    </head>
    <body>
    <div class="card" style="width: 50rem; padding: 30px 0px 0px 30px;">
        <form method=POST action="/WebSistema/controladorPrincipal">
            <input type=HIDDEN name=accion value="matriculaGrabar">
            <h4 class="display-8">Datos de Alumno</h4>
            <table class="table table-striped table-hover">
                <c:forEach items="${arrMatricula}" var="datos">
                <tr>
                    <th>Codigo</th>
                    <td><input name="xcodAlumno" size="10" 
                          value="<c:out value='${datos.codigo_alumno}'/>" readonly></td>
                    <th>Correo</th>
                    <td><input name="xnorAlumno" size="50" 
                          value="<c:out value='${datos.correo_alumno}'/>" readonly></td>
                </tr>
                <tr>
                    <th>Nro Doc</th>
                    <td><input name="xndoc" size="15" 
                               value="<c:out value='${datos.nro_doc}'/>" readonly></td>
                </tr>
                </c:forEach>
            </table>
            <h4 class="display-8">Cursos seleccionados</h4>
            <table class="table table-striped table-hover">
                <tr><th>Codigo</th><th>Curso</th><th>Monto</th></tr>
                <c:forEach items="${arrDetalles}" var="detalles">
                    <tr>
                        <td><input name="xcodCurso" size="5" 
                             value="<c:out value='${detalles.codigo_curso}'/>" readonly></td>
                        <td><input name="xnomCurso" size="40" 
                             value="<c:out value='${detalles.nombre_curso}'/>" readonly></td>
                        <td><input name="xmonto" size="9" 
                             value="<c:out value='${detalles.monto}'/>" ></td>
                    </tr>
                    
                    <c:set var="total" value="${total+detalles.monto}"/>
                    
                </c:forEach>
                <tr>
                    <td colspan="2" align="right"><b>Total:</b></td>
                    <td><input name="xtotal" size="9" value="<c:out value='${total}'/>" readonly></td>
                </tr>
                <tr><td>
                        <a class="btn btn-danger" href="/WebSistema/controladorPrincipal?accion=matriculaMostrarRegistros&modo=Lista">Regresar</a>
                </td></tr>
            </table>
        </form>
    </div>
    </body>
</html>
