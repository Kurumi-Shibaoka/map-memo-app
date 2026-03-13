package dto;

import java.sql.Timestamp;

/**
 * 店舗情報を格納するDTOクラス
 * データベースのrestaurantsテーブルの1件分のデータに対応する
 */
public class Restaurant {

	private int id;           // 店舗ID（主キー・自動採番）
	private String name;      // 店名
	private String mapUrl;    // GoogleマップのiframeコードURL
	private String station;   // 最寄り駅
	private boolean visit;    // 訪問済みフラグ（true:行った / false:気になる）
	private String memo;      // メモ
	private boolean good;     // お気に入りフラグ（true:お気に入り）
	private String category;  // カテゴリー（和食・洋食・中華など）
	private Timestamp createdAt; // 登録日時
	private Timestamp updatedAt; // 更新日時

	// --- id ---
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	// --- name ---
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	// --- mapUrl ---
	public String getMapUrl() {
		return mapUrl;
	}
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}

	// --- station ---
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}

	// --- visit ---
	public boolean isVisit() {
		return visit;
	}
	public void setVisit(boolean visit) {
		this.visit = visit;
	}

	// --- memo ---
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	// --- good ---
	public boolean isGood() {
		return good;
	}
	public void setGood(boolean good) {
		this.good = good;
	}

	// --- category ---
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	// --- createdAt ---
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	// --- updatedAt ---
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}
