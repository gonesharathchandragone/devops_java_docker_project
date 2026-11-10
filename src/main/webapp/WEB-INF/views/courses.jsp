<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Courses | Sharath DevOps Institute</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <header class="topbar">
    <span class="brand">Sharath DevOps Institute</span>
    <span>
      Welcome, <b><c:out value="${sessionScope.user.fullName}"/></b>
      &nbsp;|&nbsp; <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </span>
  </header>

  <main class="container">
    <h2>Our Courses</h2>
    <div class="grid">
      <c:forEach var="course" items="${courses}">
        <div class="card">
          <h3><c:out value="${course.title}"/></h3>
          <p><c:out value="${course.description}"/></p>
          <div class="meta">
            <span>⏱ <c:out value="${course.duration}"/></span>
            <span class="fee">₹ <c:out value="${course.fee}"/></span>
          </div>
        </div>
      </c:forEach>
    </div>
  </main>
</body>
</html>
