package servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBConnection;
import dao.RestaurantDao;

/**
 * 店舗情報の削除を担当するServlet
 * POST：削除処理の実行（データ変更のためGETは使用しない）
 */
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 削除処理（POST）
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 文字化け対策
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// 削除対象のIDを取得（文字列型）
		String idStr = request.getParameter("id");
		// データベースのIDをint型に変換
		int id = Integer.parseInt(idStr);

		// データベース接続（try-with-resourcesで自動的にcloseされる）
		try (Connection con = DBConnection.getConnection()) {
			RestaurantDao dao = new RestaurantDao();
			// 指定されたIDのレコードを削除
			dao.delete(con, id);
		} catch (Exception e) {
			// エラー内容をコンソールに出力
			e.printStackTrace();
		}
		// 削除完了後は一覧画面へリダイレクト（二重送信防止）
		response.sendRedirect("list");
	}
}
