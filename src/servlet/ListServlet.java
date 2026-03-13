package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
 * 店舗一覧・検索機能を担当するServlet（アプリのメイン画面）
 * GET/POST：一覧表示・検索結果の表示
 */
@WebServlet("/list")
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RestaurantDao dao = new RestaurantDao();
		// データベースから取得した店舗一覧
		List<Restaurant> restaurantList = null;

		// 検索フォームで選択された駅名・カテゴリーを取得
		String station = request.getParameter("station");
		String category = request.getParameter("category");

		// プルダウン表示用の駅一覧・カテゴリー一覧
		List<String> stationList = null;
		List<String> categoryList = null;

		// DB接続（try-with-resourcesで自動的にcloseされる）
		try (Connection con = DBConnection.getConnection()) {
			// 条件に合う店舗情報を取得（条件未指定の場合は全件取得）
			restaurantList = dao.findByCondition(con, station, category);
			// プルダウンに表示する駅一覧・カテゴリー一覧を取得
			stationList = dao.findAllStations(con);
			categoryList = dao.findAllCategories(con);
		} catch (SQLException | ClassNotFoundException e) {
			// エラー内容をコンソールに出力
			e.printStackTrace();
		}

		// JSPへデータを渡す
		request.setAttribute("restaurantList", restaurantList);
		request.setAttribute("stationList", stationList);
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("station", station);
		request.setAttribute("category", category);

		// list.jsp へ画面遷移（フォワード）
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("list.jsp");
		dispatcher.forward(request, response);
	}

	// 検索フォームのPOST送信もdoGetで処理する
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
