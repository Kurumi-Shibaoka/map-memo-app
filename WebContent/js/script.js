// ページ内の絵文字をSVG画像に置き換える
twemoji.parse(document.body, {
	folder: 'svg',
	ext: '.svg'
});

// 削除ボタン用の確認ダイアログ
function confirmDelete(form) {
	// 確認ダイアログを表示
	if (confirm('本当に削除しますか？')) {
	// OKを押したら削除実行
	form.submit();
	} else {
	// キャンセルを押したらedit.jspに戻る
	window.location.href = "edit?id="
	+ form.querySelector('input[name="id"]').value;
	}
}
