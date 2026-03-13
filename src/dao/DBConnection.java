package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * データベース接続を管理するクラス
 */
public class DBConnection {

	/**
	 * データベースへの接続を取得するメソッド
	 * @return Connection データベース接続オブジェクト
	 * @throws SQLException SQL例外
	 * @throws ClassNotFoundException JDBCドライバが見つからない場合の例外
	 */
	public static Connection getConnection()
			throws SQLException, ClassNotFoundException {

		// MySQLのJDBCドライバを読み込む
		Class.forName("com.mysql.cj.jdbc.Driver");

		// データベースに接続して接続オブジェクトを返す
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/mapmemo_app?useUnicode=true&characterEncoding=UTF-8",
				"root", // ← ここも必要に応じて変更
				"your_password" // ← ここを変更
		);
		return con;
	}
}
