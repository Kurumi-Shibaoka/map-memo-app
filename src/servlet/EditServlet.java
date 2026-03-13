package servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBConnection;
import dao.RestaurantDao;
import dto.Restaurant;

/**
 * 店舗情報の編集・更新を担当するServlet
 * GET：編集画面の表示（対象データを取得してフォームに表示）
 * POST：更新処理の実行
 * 削除処理はDeleteServletで別管理
 */
@WebServlet("/edit")
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 編集画面を表示（GET）
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// URLパラメータからidを取得（文字列）
		String idSt = request.getParameter("id");
		// 数値に変換（DB検索で必要）
		int id = Integer.parseInt(idSt);

		Restaurant r = null;

		// DB接続（try-with-resourcesで自動的にcloseされる）
		try (Connection con = DBConnection.getConnection()) {
			RestaurantDao dao = new RestaurantDao();
			// idを使って編集対象データを1件取得
			r = dao.findByIdForEdit(con, id);
		} catch (Exception e) {
			// エラー内容をコンソールに出力
			e.printStackTrace();
		}

		// edit.jsp にデータを渡す
		request.setAttribute("restaurant", r);

		// edit.jsp へ画面遷移（フォワード）
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("edit.jsp");
		dispatcher.forward(request, response);
	}

	// 更新処理（POST）
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 文字化け対策
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// フォームから送られてきた値を取得
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String mapUrl = request.getParameter("mapUrl");
		String station = request.getParameter("station");
		String memo = request.getParameter("memo");
		String category = request.getParameter("category");

		// ラジオボタンの値（"true" / "false"）文字列をbooleanに変換
		boolean visit = Boolean.parseBoolean(request.getParameter("visit"));
		// チェックボックスは未チェックの場合nullになるため、nullでなければtrueとする
		boolean good = request.getParameter("good") != null;

		// 更新データを格納するインスタンス
		Restaurant r = new Restaurant();
		// フォームの値をセット
		r.setId(id); // 更新対象ID
		r.setName(name);
		r.setMapUrl(mapUrl);
		r.setStation(station);
		r.setVisit(visit);
		r.setMemo(memo);
		r.setGood(good);
		r.setCategory(category);

		// データベース接続（try-with-resourcesで自動的にcloseされる）
		try (Connection con = DBConnection.getConnection()) {
			RestaurantDao dao = new RestaurantDao();
			// updateメソッドでDBを更新
			dao.update(con, r);
		} catch (Exception e) {
			// エラー内容をコンソールに出力
			e.printStackTrace();
		}
		// 更新後は一覧画面へリダイレクト（二重送信防止）
		response.sendRedirect("list");
	}
}
