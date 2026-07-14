package item.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.ConnectionManager;
import item.model.Item;

public class ItemDAO {

	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql;

	public static void addItem(Item item) {
		try {
			con = ConnectionManager.getConnection();

			sql = "INSERT INTO item (itemid, itemname, itemdesc, itemprice, itemunit, maximumstock, minimumstock, currentstock, itemstatus) VALUES(nextval('item_id_seq'), ?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, item.getItemname());
			ps.setString(2, item.getItemdesc());
			ps.setDouble(3, item.getItemprice());
			ps.setString(4, item.getItemunit());
			ps.setInt(5, item.getMaximumstock());
			ps.setInt(6, item.getMinimumstock());
			ps.setInt(7, item.getCurrentstock());
			ps.setString(8, item.getItemstatus());

			ps.executeUpdate();

			con.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateItem(Item item, int restock_qty) {
		try {
			con = ConnectionManager.getConnection();

			sql = "UPDATE item SET itemname = ?, itemdesc = ?, itemprice = ?, itemunit = ?, maximumstock = ?, minimumstock = ?, currentstock = currentstock + ?, itemstatus = ? WHERE itemid = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, item.getItemname());
			ps.setString(2, item.getItemdesc());
			ps.setDouble(3, item.getItemprice());
			ps.setString(4, item.getItemunit());
			ps.setInt(5, item.getMaximumstock());
			ps.setInt(6, item.getMinimumstock());
			ps.setInt(7, restock_qty);
			ps.setString(8, item.getItemstatus());
			ps.setInt(9, item.getItemid());

			ps.executeUpdate();

			con.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Item getItemById(int itemId) {
		Item item = new Item();

		try {
			con = ConnectionManager.getConnection();
			sql = "SELECT * FROM item WHERE itemid = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, itemId);
			rs = ps.executeQuery();
			if (rs.next()) {
				item.setItemid(rs.getInt("itemid"));
				item.setItemname(rs.getString("itemname"));
				item.setItemdesc(rs.getString("itemdesc"));
				item.setItemprice(rs.getDouble("itemprice"));
				item.setItemunit(rs.getString("itemunit"));
				item.setItemstatus(rs.getString("itemstatus"));
				item.setMaximumstock(rs.getInt("maximumstock"));
				item.setMinimumstock(rs.getInt("minimumstock"));
				item.setCurrentstock(rs.getInt("currentstock"));

			}
			con.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	public static List<Item> getActiveItems() {

		List<Item> items = new ArrayList<Item>();

		try {

			con = ConnectionManager.getConnection();
			sql = "SELECT * FROM item  WHERE itemstatus = 'AKTIF' ORDER BY itemid";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Item item = new Item();
				item.setItemid(rs.getInt("itemid"));
				item.setItemname(rs.getString("itemname"));
				item.setItemdesc(rs.getString("itemdesc"));
				item.setItemprice(rs.getDouble("itemprice"));
				item.setItemunit(rs.getString("itemunit"));
				item.setMaximumstock(rs.getInt("maximumstock"));
				item.setMinimumstock(rs.getInt("minimumstock"));
				item.setCurrentstock(rs.getInt("currentstock"));
				items.add(item);
			}
			con.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}

	public static List<Item> getAllItems() {

		List<Item> items = new ArrayList<Item>();

		try {

			con = ConnectionManager.getConnection();
			sql = "SELECT itemid, itemname, itemdesc, itemprice, itemunit, itemstatus, maximumstock, minimumstock, currentstock FROM item ORDER BY itemid";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {

				Item item = new Item();
				item.setItemid(rs.getInt("itemid"));
				item.setItemname(rs.getString("itemname"));
				item.setItemdesc(rs.getString("itemdesc"));
				item.setItemprice(rs.getDouble("itemprice"));
				item.setItemunit(rs.getString("itemunit"));
				item.setMaximumstock(rs.getInt("maximumstock"));
				item.setMinimumstock(rs.getInt("minimumstock"));
				item.setCurrentstock(rs.getInt("currentstock"));
				item.setItemstatus(rs.getString("itemstatus"));

				items.add(item);
			}

			con.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}

	public static void deductStock(int itemid, int qty) {
		try {
			con = ConnectionManager.getConnection();

			sql = "UPDATE item SET currentstock = currentstock - ? WHERE itemid = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, qty);
			ps.setInt(2, itemid);
			ps.executeUpdate();

			con.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
