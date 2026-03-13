<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MAP MEMO | Home</title>
<!-- CSS読み込み -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
<header>
	<h1>✨ MAP MEMO ✨</h1>
	<p>Collect Your Favorites.</p>
</header>
<div class="form-wrapper">
	<div class="form-cards">
		<!-- 登録フォームへ移動 -->
		<div class="menu-box">
		<h2>🎀 Add Spot 🎀</h2>
			<a href="add" class="btn btn-primary btn-small">＋ New</a>
		</div>

		<!-- 検索フォーム -->
		<div class="menu-box">
		<form action="list" method="get">
			<div class="select-group">

				<!-- 駅を選択 -->
				<select name="station">
					<option value="">--- 駅名 ---</option>
					<!-- 現在選択中の駅名をselected状態で表示 -->
					<c:forEach var="s" items="${stationList}">
						<option value="${s}"
							<c:if test="${s == station}">selected</c:if>>
							${s}
						</option>
					</c:forEach>
				</select>

				<!-- カテゴリーを選択 -->
				<select name="category">
					<option value="">--- カテゴリー ---</option>
					<!-- 現在選択中のカテゴリーをselected状態で表示 -->
					<c:forEach var="c" items="${categoryList}">
						<option value="${c}"
							<c:if test="${c == category}">selected</c:if>>
							${c}
						</option>
					</c:forEach>
				</select>
			</div>
			<!-- ボタン：検索/クリア -->
			<button type="submit" class="btn btn-secondary">Search</button>
			<a href="list" class="btn btn-secondary">Clear</a>
		</form>
	</div>
	</div>
</div>

<section>
<h2>💫 Favorites 💫</h2>
	<!-- メモ一覧を表示 -->
	<div class="grid">
		<c:choose>
			<%-- 検索結果なし --%>
			<c:when test="${empty restaurantList}">
				<p class="no-result-message">
					👀 No spots found ⚠️<br>該当するお店がありません！
				</p>
			</c:when>

			<%-- 店舗情報を1件ずつカード形式で表示 --%>
			<c:otherwise>
				<c:forEach var="r" items="${restaurantList}">
					<!-- カード自体を地図画面へのリンクにする -->
					<div class="card card-hover"
						onclick="location.href='map?id=${r.id}'">

						<!-- 店名 -->
						<h3>${r.name}</h3>

						<div class="status">
							<!-- お気に入りの場合のみハートマークを表示 -->
							<c:if test="${r.good}">
								<span class="heart">💖</span>
							</c:if>
							<!-- true = 行った , false = 気になる -->
							<span class="bold-text">
								<c:choose>
									<c:when test="${r.visit}">
									行った！
									</c:when>
									<c:otherwise>
									気になる！
									</c:otherwise>
								</c:choose>
							</span>
						</div>

						<!-- 駅名・カテゴリー・メモ・登録日・更新日 -->
						<div class="card-body">
							<p>
							Station：${r.station}<br>
							Category：${r.category}<br>
							Note：${r.memo}<br>
							Created：
							<fmt:formatDate value="${r.createdAt}" pattern="yyyy-MM-dd" /><br>
								<!-- 更新日：updatedAtがcreatedAtと日付が異なるときだけ表示 -->
								<fmt:formatDate var="created" value="${r.createdAt}" pattern="yyyy-MM-dd" />
								<fmt:formatDate var="updated" value="${r.updatedAt}" pattern="yyyy-MM-dd" />
									<c:if test="${created ne updated}">
										Updated：
										<fmt:formatDate value="${r.updatedAt}" pattern="yyyy-MM-dd" />
										<br>
									</c:if><br>
							<!-- 編集/削除リンク -->
							<a href="edit?id=${r.id}" class="edit-link">Edit / Delete</a>
							</p>
						</div>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</section>
<!-- script読み込み -->
<script src="https://unpkg.com/twemoji@latest/dist/twemoji.min.js"></script>
<script src="js/script.js"></script>
</body>
</html>
