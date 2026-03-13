<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- タイトルに店舗名を表示 -->
<title>${restaurant.name} | Map</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
<header>
	<!-- ヘッダーに店舗名を表示 -->
	<h1>${restaurant.name} 📍</h1>
</header>
<section>
	<!-- GoogleマップのiframeコードをそのままJSPで展開して地図を表示 -->
	<div class="iframe-wrapper iframe">
		${restaurant.mapUrl}
	</div>
</section>
<!-- 一覧画面へ戻る -->
<a href="list" class="back-link">Back to Home</a>
<!-- JS読み込み -->
<script src="https://unpkg.com/twemoji@latest/dist/twemoji.min.js"></script>
<script src="js/script.js"></script>
</body>
</html>
