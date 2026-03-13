package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Restaurant;

/**
 * restaurantsテーブルに対するCRUD処理をまとめたDAOクラス
 */
public class RestaurantDao {

	////// 一覧表示用 全取得 //////
	// restaurantsテーブルの全件を取得するメソッド（Read）
	public List<Restaurant> findAll(Connection con)
			throws SQLException {// SQL例外を呼び出し元に投げる

		// restaurants テーブルから全件取得
		String sql = "SELECT * FROM restaurants";
		// 取得した結果を格納するための空のリスト
		List<Restaurant> restaurantList = new ArrayList<>();

		// SQLを実行し結果を取得
		try (PreparedStatement ps = con.prepareStatement(sql);// SQL準備
			ResultSet rs = ps.executeQuery()) {// 実行して結果取得
			// whileで1件ずつデータを取得
			while (rs.next()) {
				// インスタンスを作成
				Restaurant r = new Restaurant();
				// 各カラムの値をセットする
				r.setId(rs.getInt("id"));
				r.setName(rs.getString("name"));
				r.setMapUrl(rs.getString("map_url"));
				r.setStation(rs.getString("station"));
				r.setVisit(rs.getBoolean("visit"));
				r.setMemo(rs.getString("memo"));
				r.setGood(rs.getBoolean("good"));
				r.setCategory(rs.getString("category"));
				r.setCreatedAt(rs.getTimestamp("created_at"));
				r.setUpdatedAt(rs.getTimestamp("updated_at"));
				// リストに追加する
				restaurantList.add(r);
			}
		}
		// 結果を返す
		return restaurantList;
	}

	////// 地図表示用 //////
	// 指定IDの地図表示に必要な項目だけ取得するメソッド（Read）
	public Restaurant findById(Connection con, int id)
			throws SQLException {
		// ID・店名・map_urlを取得
		String sql = "SELECT id, name, map_url FROM restaurants WHERE id = ?";
		// 取得結果を入れる
		Restaurant r = null;

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			// ? に id をセットする
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				// ifで取得する
				if (rs.next()) {// データがあれば
					// インスタンス生成
					r = new Restaurant();
					// id・name・map_urlの値をセット
					r.setId(rs.getInt("id"));
					r.setName(rs.getString("name"));
					r.setMapUrl(rs.getString("map_url"));
				}
			}
		}
		// 1件返す
		return r;
	}

	////// 登録処理用 //////
	// 新規データを登録するメソッド（Create）
	public void insert(Connection con, Restaurant r)
			throws SQLException {

		String sql = "INSERT INTO restaurants "
				+ "(name, map_url, station, visit, memo, good, category) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			// DTOの値をSQLにセット
			ps.setString(1, r.getName());
			ps.setString(2, r.getMapUrl());
			ps.setString(3, r.getStation());
			ps.setBoolean(4, r.isVisit());
			ps.setString(5, r.getMemo());
			ps.setBoolean(6, r.isGood());
			ps.setString(7, r.getCategory());
			// INSERT実行
			ps.executeUpdate();
		}
	}

	////// 編集画面表示用（1件取得） //////
	// 編集画面に表示するため、全ての項目を取得するメソッド（Read）
	public Restaurant findByIdForEdit(Connection con, int id)
			throws SQLException {
		// 指定のIDのデータを全項目取得
		String sql = "SELECT * FROM restaurants WHERE id = ?";
		// 格納用
		Restaurant r = null;

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			// ? に id をセットする
			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				// ifで取得する
				if (rs.next()) {
					// インスタンス生成
					r = new Restaurant();
					// 全カラムセット
					r.setId(rs.getInt("id"));
					r.setName(rs.getString("name"));
					r.setMapUrl(rs.getString("map_url"));
					r.setStation(rs.getString("station"));
					r.setVisit(rs.getBoolean("visit"));
					r.setMemo(rs.getString("memo"));
					r.setGood(rs.getBoolean("good"));
					r.setCategory(rs.getString("category"));
					r.setCreatedAt(rs.getTimestamp("created_at"));
					r.setUpdatedAt(rs.getTimestamp("updated_at"));
				}
			}
		}
		// 返す
		return r;
	}

	////// 更新処理（Update）//////
	// 既存データを更新するメソッド
	public void update(Connection con, Restaurant r)
			throws SQLException {

		// idで対象データを特定して更新する
		// updated_at=NOW() で更新日時を明示的にセットする
		String sql = "UPDATE restaurants "
				+ "SET name=?, map_url=?, station=?, visit=?, memo=?, good=?, category=?, "
				+ "updated_at=NOW() "
				+ "WHERE id=?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			// 更新値セット
			ps.setString(1, r.getName());
			ps.setString(2, r.getMapUrl());
			ps.setString(3, r.getStation());
			ps.setBoolean(4, r.isVisit());
			ps.setString(5, r.getMemo());
			ps.setBoolean(6, r.isGood());
			ps.setString(7, r.getCategory());
			ps.setInt(8, r.getId()); // 更新対象ID
			// UPDATE実行
			ps.executeUpdate();
		}
	}

	////// 削除処理（Delete）//////
	// 指定IDのデータを削除するメソッド
	public void delete(Connection con, int id)
			throws SQLException {

		// idで削除対象を指定する
		String sql = "DELETE FROM restaurants WHERE id=?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			// 削除対象IDをセット
			ps.setInt(1, id);
			// DELETE実行
			ps.executeUpdate();
		}
	}

	////// 検索用 駅名一覧取得 //////
	// 駅名を重複なしで取得するメソッド（Read）
	public List<String> findAllStations(Connection con)
			throws SQLException {

		// 重複しない駅名だけを取得するSQL
		String sql = "SELECT DISTINCT station FROM restaurants";
		// 取得した駅名を入れるための空のリストを作成
		List<String> stationList = new ArrayList<>();
		// SQLを準備して実行し、結果を受け取る
		try (PreparedStatement ps = con.prepareStatement(sql);// SQLをデータベース用に準備
			 ResultSet rs = ps.executeQuery()) { // SQLを実行して結果を取得
			// 結果を1行ずつ取り出す
			while (rs.next()) {
				// stationカラムの値（駅名）を取り出す
				String station = rs.getString("station");
				// 取り出した駅名をリストに追加する
				stationList.add(station);
			}
		}
		// 集めた駅名リストを呼び出し元へ返す
		return stationList;
	}

	////// 検索用 指定駅の店舗取得 //////
	// 指定した駅名に一致する店舗を取得するメソッド（Read）
	public List<Restaurant> findByStation(Connection con, String station)
			throws SQLException {

		// 駅名で絞り込むSQL
		String sql = "SELECT * FROM restaurants WHERE station = ?";
		// 取得した結果を格納するための空のリスト
		List<Restaurant> restaurantList = new ArrayList<>();

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			// ? に station をセットする
			ps.setString(1, station);

			try (ResultSet rs = ps.executeQuery()) {
				// whileで1件ずつデータを取得
				while (rs.next()) {
					// インスタンスを作成
					Restaurant r = new Restaurant();
					// 各カラムの値をセットする
					r.setId(rs.getInt("id"));
					r.setName(rs.getString("name"));
					r.setMapUrl(rs.getString("map_url"));
					r.setStation(rs.getString("station"));
					r.setVisit(rs.getBoolean("visit"));
					r.setMemo(rs.getString("memo"));
					r.setGood(rs.getBoolean("good"));
					r.setCategory(rs.getString("category"));
					r.setCreatedAt(rs.getTimestamp("created_at"));
					r.setUpdatedAt(rs.getTimestamp("updated_at"));
					// リストに追加する
					restaurantList.add(r);
				}
			}
		}
		// 結果を返す
		return restaurantList;
	}

	////// 検索用 条件検索（駅名・カテゴリー） //////
	// 駅名・カテゴリーの指定有無に応じてSQLを動的に組み立てるメソッド（Read）
	public List<Restaurant> findByCondition(Connection con, String station, String category)
			throws SQLException {

		// WHERE 1=1 を基点にして条件を動的に追加する
		StringBuilder sql = new StringBuilder("SELECT * FROM restaurants WHERE 1=1");
		// セットするパラメータを格納するリスト
		List<Object> params = new ArrayList<>();

		// 駅名が指定されていれば条件に追加する
		if (station != null && !station.isEmpty()) {
			sql.append(" AND station = ?");
			params.add(station);
		}

		// カテゴリーが指定されていれば条件に追加する
		if (category != null && !category.isEmpty()) {
			sql.append(" AND category = ?");
			params.add(category);
		}

		// 取得した結果を格納するための空のリスト
		List<Restaurant> restaurantList = new ArrayList<>();

		try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
			// パラメータを順番にセットする
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}

			try (ResultSet rs = ps.executeQuery()) {
				// whileで1件ずつデータを取得
				while (rs.next()) {
					// インスタンスを作成
					Restaurant r = new Restaurant();
					// 各カラムの値をセットする
					r.setId(rs.getInt("id"));
					r.setName(rs.getString("name"));
					r.setMapUrl(rs.getString("map_url"));
					r.setStation(rs.getString("station"));
					r.setVisit(rs.getBoolean("visit"));
					r.setMemo(rs.getString("memo"));
					r.setGood(rs.getBoolean("good"));
					r.setCategory(rs.getString("category"));
					r.setCreatedAt(rs.getTimestamp("created_at"));
					r.setUpdatedAt(rs.getTimestamp("updated_at"));
					// リストに追加する
					restaurantList.add(r);
				}
			}
		}
		// 結果を返す
		return restaurantList;
	}

	////// 検索用 カテゴリー一覧取得 //////
	// カテゴリーを重複なしで取得するメソッド（Read）
	public List<String> findAllCategories(Connection con)
			throws SQLException {

		// 重複しないカテゴリーだけを取得するSQL
		String sql = "SELECT DISTINCT category FROM restaurants";
		// 取得したカテゴリーを入れるための空のリストを作成
		List<String> categoryList = new ArrayList<>();

		try (PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {// SQLを実行して結果を取得
			// 結果を1行ずつ取り出す
			while (rs.next()) {
				// categoryカラムの値を取り出す
				String category = rs.getString("category");
				// 取り出したカテゴリーをリストに追加する
				categoryList.add(category);
			}
		}
		// 集めたカテゴリーリストを呼び出し元へ返す
		return categoryList;
	}
}
