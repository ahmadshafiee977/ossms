package requestitem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.ConnectionManager;
import requestitem.model.Request_Item;

public class Request_ItemDAO {

	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql;

	public static void addRequestItem(Request_Item ri) {
		try {
			con = ConnectionManager.getConnection();

			sql = "INSERT INTO request_item (requestid, itemid, requestedAmount, note) VALUES (?,?,?,?)";
			ps = con.prepareStatement(sql);

			ps.setInt(1, ri.getRequestid());
			ps.setInt(2, ri.getItemid());
			ps.setInt(3, ri.getRequestedamount());
			ps.setString(4, ri.getNote());
			ps.executeUpdate();

			con.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void deleteByRequestId(int reqid) {
		try {
			con = ConnectionManager.getConnection();

			sql = "DELETE FROM request_item WHERE requestid = ?";
			ps = con.prepareStatement(sql);

			ps.setInt(1, reqid);

			ps.executeUpdate();

			con.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void updateStatus(int requestid, int itemid, String status) {
		try {
			con = ConnectionManager.getConnection();

			sql = "UPDATE request_item SET status = ? WHERE requestid = ? AND itemid = ?";

			ps = con.prepareStatement(sql);
			ps.setString(1, status);
			ps.setInt(2, requestid);
			ps.setInt(3, itemid);

			ps.executeUpdate();

			con.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
