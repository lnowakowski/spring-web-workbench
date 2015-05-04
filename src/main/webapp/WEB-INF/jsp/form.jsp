<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="pl">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Spring Web Tests</title>

<c:url value="/" var="mainUrl" />

<!-- Bootstrap -->
<link href="${mainUrl}/static/css/bootstrap.min.css" rel="stylesheet"
	media="all">
<link href="${mainUrl}/static/css/main.css" rel="stylesheet" media="all">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Spring Workbench</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="${mainUrl}">Home</a></li>
					<li class="active"><a href="${mainUrl}form">Form</a></li>
					<li><a href="#">About</a></li>
					<li><a href="#">Contact</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
		<!--/.container-fluid -->
	</nav>
	<!-- /.navbar -->

	<div class="container" style="width: 800px;">
		<h1>
			<spring:message code="label.hello" />
		</h1>

		<hr />

		<c:url value="/form" var="formUrl" />
		<form:form action="${formUrl}" method="POST" modelAttribute="item"
			class="form-horizontal">
			<div class="form-group">
				<label for="inputNumber" class="control-label col-sm-3"><spring:message
						code="label.number" />:</label>
				<div class="col-sm-9">
					<form:input id="inputNumber" type="number" path="number"
						class="form-control" />
					<form:errors path="number" class="alert-danger" role="alert" />
				</div>
			</div>
			<div class="form-group">
				<label for="inputName" class="control-label col-sm-3"><spring:message
						code="label.name" />:</label>
				<div class="col-sm-9">
					<form:input id="inputName" path="name" class="form-control"
						maxlength="50" />
					<form:errors path="name" class="alert-danger" role="alert" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-3 col-sm-9">
					<button type="submit" class="btn btn-primary">
						<spring:message code="label.submit" />
					</button>
					<c:if test="${item.id gt 0}">
						<a href="${formUrl}" class="btn btn-default">Powrót</a>
					    ID: <b>${item.id}</b>
						<c:if test="${item.version gt 0}">
							<small>v.${item.version}</small>
						</c:if>
					</c:if>
				</div>
			</div>
		</form:form>

		<c:if test="${not empty items}">
			<ul>
				<c:forEach items="${items}" var="it">
					<li>
						<c:url value="/form/edit/${it.id}" var="editUrl" />
						<c:url value="/form/delete/${it.id}" var="deleteUrl" />
						<a href="${editUrl}"><code>${it.name}</code> <small><fmt:formatDate
									pattern="yyyy-MM-dd HH:mm:ss" value="${it.created}" /></small> (ID: <b><fmt:formatNumber
									pattern="####" minIntegerDigits="4" value="${it.id}" /></b>) </a> <a
							href="${deleteUrl}"><span class="glyphicon glyphicon-remove"></span></a>
					</li>
				</c:forEach>
			</ul>
		</c:if>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="${mainUrl}/static/js/bootstrap.min.js"></script>
</body>
</html>
