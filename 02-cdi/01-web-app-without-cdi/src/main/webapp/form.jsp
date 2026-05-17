<%@page contentType="text/html" pageEncoding="UTF-8" import="java.time.format.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="layout/header.jsp" />

<h3>${title}</h3>

<form action="${pageContext.request.contextPath}/products/form" method="post">
    <div class="row mb-2">
        <label for="name" class="col-form-label col-sm-2">Nombre</label>
        <div class="col-sm-4">
            <input type="text" name="name" id="name" value="${product.name}" class="form-control">
        </div>
    </div>

    <div class="row mb-2">
        <label for="price" class="col-form-label col-sm-2">Precio</label>
        <div class="col-sm-4">
            <input type="number" name="price" id="price" value="${product.price > 0 ? product.price: ""}" class="form-control">
        </div>
    </div>

    <div class="row mb-2">
        <label for="sku" class="col-form-label col-sm-2">Sku</label>
        <div class="col-sm-4">
            <input type="text" name="sku" id="sku" value="${product.sku}" class="form-control">
        </div>
    </div>

    <div class="row mb-2">
        <label for="createdAt" class="col-form-label col-sm-2">Fecha Registro</label>
        <div class="col-sm-4">
            <input class="form-control" type="date" name="createdAt" id="createdAt" value="${product.createdAt != null? product.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")): ""}">
        </div>
    </div>

    <div class="row mb-2">
        <label for="categoryId" class="col-form-label col-sm-2">Categoria</label>
        <div class="col-sm-4">
            <select name="categoryId" id="categoryId" class="form-select">
                <option value="">--- seleccionar ---</option>
                <c:forEach items="${categories}" var="c">
                <option value="${c.id}" ${c.id.equals(product.category.id) ? "selected": ""}>${c.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="row mb-2">
      <div>
         <input class="btn btn-primary" type="submit" value="${product.id != null && product.id > 0 ? "Editar": "Crear"}">
      </div>
    </div>
    <input type="hidden" name="productId" value="${product.id}">
</form>

<jsp:include page="layout/footer.jsp" />
