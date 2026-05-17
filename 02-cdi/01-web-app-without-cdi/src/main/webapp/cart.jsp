<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="layout/header.jsp" />

<h3>${title}</h3>

<c:choose>
<c:when test="${sessionScope['shopping-cart'] == null || sessionScope['shopping-cart'].items.isEmpty()}">
<div class="alert alert-warning">Lo sentimos no hay productos en el carro de compras!</div>
</c:when>
<c:otherwise>
<form name="formcarro" action="${pageContext.request.contextPath}/carts/update" method="post">
<table class="table table-hover table-striped">
    <tr>
        <th>id</th>
        <th>nombre</th>
        <th>precio</th>
        <th>cantidad</th>
        <th>total</th>
        <th>borrar</th>
    </tr>
    <c:forEach items="${sessionScope['shopping-cart'].items}" var="item">
    <tr>
        <td>${item.product.id}</td>
        <td>${item.product.name}</td>
        <td>${item.unitPrice}</td>
        <td><input type="text" size="4" name="quantity_${item.product.id}" value="${item.quantity}" /></td>
        <td>${item.subtotal}</td>
        <td><input type="checkbox" value="${item.product.id}" name="id-of-products-to-delete" /></td>
    </tr>
    </c:forEach>
    <tr>
        <td colspan="5" style="text-align: right">Total:</td>
        <td>${sessionScope['shopping-cart'].total}</td>
    </tr>
</table>
<a class="btn btn-primary" href="javascript:document.formcarro.submit();">Actualizar</a>
</form>
</c:otherwise>
</c:choose>
<div class="my-2">
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/index.jsp">volver</a>
    <a class="btn btn-success" href="${pageContext.request.contextPath}/products">seguir comprando</a>
</div>

<jsp:include page="layout/footer.jsp" />
