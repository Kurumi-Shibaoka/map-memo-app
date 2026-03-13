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
 * Googleマップ表示を担当するServlet
 * GET：指定された店舗のマップ画面を表示
 */
@WebServlet("/map")
public class MapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RestaurantDao dao = new RestaurantDao();
		Restaurant restaurant = null;

		// URLパラメータからidを取得（文字列）
		String restaurantsId = request.getParameter("id");
		// 文字列をintに変換
		int id = Integer.parseInt(restaurantsId);

		// DB接続（try-with-resourcesで自動的にcloseされる）
		try (Connection con = DBConnection.getConnection()) {
			// 指定されたIDの店舗データを1件取得
			restaurant = dao.findById(con, id);
		} catch (SQLException | ClassNotFoundException e) {
			// エラー内容をコンソールに出力
			e.printStackTrace();
		}

		// map.jsp にデータを渡す
		request.setAttribute("restaurant", restaurant);
		// map.jsp へ画面遷移（フォワード）
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("map.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
