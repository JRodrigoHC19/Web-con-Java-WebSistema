<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    if(session.getAttribute("nombre") != null){
%>
<html>
    <head>
        <title>SistemaWeb</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
         
    </head>
        <frameset rows="80,*" border="0" frameborder="0" y framespacing="40">
          <frame src=titulo.html name=titulo>
          <frameset cols="250,*">
              <frame src=opciones.html name=izquierda>
              <frame src=contenido.html name=derecha>
          </frameset>
       </frameset>
</html>

<%
    } else{
    response.sendRedirect("/WebSistema");
}
%>