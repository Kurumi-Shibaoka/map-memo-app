package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
 * 店舗情報の新規登録を担当するServlet
 * GET：登録画面の表示
 * POST：登録処理の実行
 */
@WebServlet("/add")
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 登録画面を表示（GET）
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// add.jsp へ画面遷移（フォワード）
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("add.jsp");
		dispatcher.forward(request, response);
	}

	// 登録処理（POST）
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 文字化け対策
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// フォームの値を取得
		String name = request.getParameter("name");
		String mapUrl = request.getParameter("mapUrl");
		String station = request.getParameter("station");
		String memo = request.getParameter("memo");
		String category = request.getParameter("category");
		String visitSt = request.getParameter("visit");

		// ラジオボタンの値（"true" / "false"）文字列をbooleanに変換
		boolean visit = Boolean.parseBoolean(visitSt);
		// チェックボックスは未チェックの場合nullになるため、nullでなければtrueとする
		boolean good = request.getParameter("good") != null;

		// データベース接続（try-with-resourcesで自動的にcloseされる）
		try (Connection con = DBConnection.getConnection()) {
			RestaurantDao dao = new RestaurantDao();
			// 登録データを格納するインスタンスを作成
			Restaurant r = new Restaurant();
			// フォームの値をセット
			r.setName(name);
			r.setMapUrl(mapUrl);
			r.setStation(station);
			r.setVisit(visit);
			r.setMemo(memo);
			r.setGood(good);
			r.setCategory(category);
			// insertメソッドでDBに登録
			dao.insert(con, r);
		} catch (SQLException | ClassNotFoundException e) {
			// エラー内容をコンソールに出力
			e.printStackTrace();
		}
		// 登録後は一覧画面へリダイレクト（二重送信防止）
		response.sendRedirect("list");
	}
}
