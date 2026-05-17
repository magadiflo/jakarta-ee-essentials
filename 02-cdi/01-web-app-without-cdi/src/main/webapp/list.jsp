<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="layout/header.jsp" />
<h3>${title}</h3>
<c:if test="${username.present}">
   <div class="alert alert-info">Hola ${username.get()}, bienvenido!</div>
   <a class="btn btn-primary my-2" href="${pageContext.request.contextPath}/products/form">crear [+]</a>
</c:if>
<table class="table table-hover table-striped">
    <tr>
        <th>id</th>
        <th>nombre</th>
        <th>tipo</th>
        <c:if test="${username.present}">
        <th>precio</th>
        <th>agregar</th>
        <th>editar</th>
        <th>eliminar</th>
        </c:if>
    </tr>
    <c:forEach items="${products}" var="p">
    <tr>
        <td>${p.id}</td>
        <td>${p.name}</td>
        <td>${p.category.name}</td>
        <c:if test="${username.present}">
        <td>${p.price}</td>
        <td><a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/carts/add?productId=${p.id}">agregar al carro</a></td>
        <td><a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/products/form?productId=${p.id}">editar</a></td>
        <td><a class="btn btn-sm btn-danger" onclick="return confirm('esta seguro que desea eliminar?');"
        href="${pageContext.request.contextPath}/products/delete?productId=${p.id}">eliminar</a></td>
        </c:if>
    </tr>
    </c:forEach>
</table>
<jsp:include page="layout/footer.jsp" />
