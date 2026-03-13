<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Spot | New</title>
<!-- CSS読み込み -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header>
	<h1>🍽️️ Add Spot 🍽️️</h1>
</header>
<section>
	<h2>How to Add a Map 📍</h2>
	<div class="howto-box">
		<ul class="howto-list">
		<li><strong>GoogleマップURL入力手順</strong><br>
		共有 → "地図を埋め込む" → HTMLをコピー</li>
		</ul>
		<!-- 説明用の画像 -->
		<img src="img/map.png" alt="説明画像">
	</div>
</section>
<!-- レイアウト用wrapper -->
<div class="form-wrapper">
<!-- フォーム開始。addにPOST送信 → AddServletのdoPostが呼ばれる -->
<form action="add" method="post">

    <!-- 店名入力欄。requiredで必須入力 -->
    <label>店名</label>
    <input type="text" name="name" required><br><br>

	<!-- マップURL入力欄。placeholderは入力例の表示 -->
    <label>GoogleマップURL</label>
    <input type="text" name="mapUrl"
			placeholder="<iframe src= から始まるコードを貼り付け"
			required><br><br>

    <!-- 最寄駅入力欄。requiredで必須入力  -->
    <label>最寄駅</label>
    <input type="text" name="station" required><br><br>

    <label>カテゴリー</label>
    <!-- プルダウンメニュー -->
    <select name="category" required>
		<!-- 選択肢 -->
		<option value="">選択してください</option>
		<option value="和食">和食</option>
		<option value="洋食">洋食</option>
		<option value="中華">中華</option>
		<option value="カフェ">カフェ</option>
		<option value="居酒屋">居酒屋</option>
		<option value="その他">その他</option>
	</select><br><br>

    <label>メモ</label>
    <!-- 複数行入力できるメモ欄 -->
    <textarea name="memo"></textarea><br><br>

<!-- true = 行った , false = 気になる をラジオボタンで選択 -->
<div class="radio-group">
	<p class="form-label">このお店は？</p>

	<!-- 行った場合はtrue（デフォルト） -->
	<label class="radio-item">
	<input type="radio" name="visit" value="true" checked>
	<span>行った！</span>
	</label>

	<!-- 気になる場合はfalse -->
	<label class="radio-item">
	<input type="radio" name="visit" value="false">
	<span>気になる！</span>
	</label>
</div>

<!-- チェックされたときだけパラメータが送られる -->
<label class="checkbox-item">
    <input type="checkbox" name="good" value="true">
    <span>Mark as Favorite 💖</span>
</label>
<br>
	<!-- 登録ボタン -->
    <input type="submit" value="Save" class="btn btn-primary">
</form>
</div>
<br>
<!-- 一覧画面へ戻る -->
<a href="list" class="back-link">Back to Home</a>
<!-- JS読み込み -->
<script src="https://unpkg.com/twemoji@latest/dist/twemoji.min.js"></script>
<script src="js/script.js"></script>
</body>
</html>
