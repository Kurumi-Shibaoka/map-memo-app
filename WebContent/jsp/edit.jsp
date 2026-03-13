<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- JSTLコアタグを使えるようにする（c:if など） -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 数値や日付の整形用 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- fn:escapeXml を使うため -->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${restaurant.name} | Edit</title>
<!-- CSS読み込み -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header>
	<h1>🌟️️ Edit Spot 🌟️️</h1>
</header>
<!-- レイアウト用wrapper -->
<div class="form-wrapper">
<!-- 編集データをEditServletへ送信 -->
<form action="edit" method="post">
	<!-- 更新対象のIDを送る -->
	<input type="hidden" name="id" value="${restaurant.id}">

	<label>店名</label>
	<input type="text" name="name" value="${restaurant.name}"><br><br>

	<label>GoogleマップURL</label>
	<!-- 特殊文字対策としてエスケープ -->
	<input type="text" name="mapUrl" value="${fn:escapeXml(restaurant.mapUrl)}"><br><br>

	<label>最寄駅</label>
	<input type="text" name="station" value="${restaurant.station}"><br><br>

	<label>カテゴリー</label>
	<!-- 現在のcategoryと一致するoptionにselectedを付ける -->
	<select name="category" required>
		<option value="">Select Category</option>
    <option value="和食"
		<c:if test="${restaurant.category == '和食'}">selected</c:if>>
			和食
    </option>
    <option value="洋食"
		<c:if test="${restaurant.category == '洋食'}">selected</c:if>>
			洋食
    </option>
    <option value="中華"
		<c:if test="${restaurant.category == '中華'}">selected</c:if>>
			中華
    </option>
    <option value="カフェ"
		<c:if test="${restaurant.category == 'カフェ'}">selected</c:if>>
			カフェ
    </option>
    <option value="居酒屋"
		<c:if test="${restaurant.category == '居酒屋'}">selected</c:if>>
			居酒屋
    </option>
    <option value="その他"
		<c:if test="${restaurant.category == 'その他'}">selected</c:if>>
			その他
    </option>
	</select><br><br>

	<label>メモ</label>
	<!-- textarea内もエスケープして安全に表示 -->
	<textarea name="memo">${fn:escapeXml(restaurant.memo)}</textarea><br><br>

	<!-- visitがtrue/falseに応じてradioを選択状態にする -->
	<div class="radio-group">
		<p class="form-label">このお店は？</p>

		<label class="radio-item">
			<input type="radio" name="visit" value="true"
			<c:if test="${restaurant.visit == true}">checked</c:if>>
		<span>行った！</span>
		</label>

		<label class="radio-item">
			<input type="radio" name="visit" value="false"
			<c:if test="${restaurant.visit == false}">checked</c:if>>
		<span>気になる！</span>
		</label>
    </div>

	<!-- goodはtrueのときだけチェックを付ける -->
    <label class="checkbox-item">
		<input type="checkbox"  name="good" value="true"
			<c:if test="${restaurant.good == true}">checked</c:if>>
		<span>Mark as Favorite 💖</span>
	</label><br>

	<!-- 更新ボタン（このformの入力値がEditServletに送信される） -->
	<div class="buttons">
		<input type="submit" value="Update" class="btn btn-primary">
	</form>
	<!-- 削除ボタン（別formで送信） -->
	<form action="delete" method="post" style="display:inline;">
		<input type="hidden" name="id" value="${restaurant.id}">
		<input type="button" value="Delete"  class="btn btn-secondary"
			onclick="confirmDelete(this.form)">
	</form>
</div>
</div>
<br>
<!-- 一覧画面へ戻る -->
<a href="list" class="back-link">Back to Home</a>
<!-- JS読み込み -->
<script src="https://unpkg.com/twemoji@latest/dist/twemoji.min.js"></script>
<script src="js/script.js"></script>
</body>
</html>