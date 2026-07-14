package inheritance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.ConnectionManager;
import inheritance.model.Approved;
import inheritance.model.Completed;
import item.model.Item;
import requestitem.model.Request_Item;

public class CompletedDAO {

	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql;

	public static void addCompleted(Completed completed) {
		try {
			con = ConnectionManager.getConnection();
			sql = "INSERT INTO completed (requestid, collecteddate) VALUES (?,?)";

			ps = con.prepareStatement(sql);
			ps.setInt(1, completed.getRequestid());
			ps.setDate(2, completed.getCollecteddate());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Completed getCompletedByRequestId(int requestid) {
		Completed com = new Completed();
		System.out.println("dalam function getcomplete by id: " + requestid);
		try {
			con = ConnectionManager.getConnection();
			sql = "SELECT r.*, ri.*, i.*, c.* FROM request r" + " JOIN request_item ri ON r.requestid = ri.requestid"
					+ " JOIN item i ON ri.itemid = i.itemid JOIN completed c ON c.requestid = r.requestid "
					+ " WHERE r.requestid = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, requestid);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("dalam rs next");
				com.setRequestid(rs.getInt("requestid"));
				com.setSubmitteddate(rs.getDate("submitteddate"));
				com.setRequesterid(rs.getInt("requesterid"));
				com.setProcesserid(rs.getInt("processerid"));
				com.setProcesseddate(rs.getDate("processeddate"));
				com.setRequeststatus(rs.getString("requeststatus"));
				com.setCollecteddate(rs.getDate("collecteddate"));

				Item item = new Item();

				item.setItemid(rs.getInt("itemid"));
				item.setItemname(rs.getString("itemname"));

				Request_Item ri = new Request_Item();

				ri.setRequestid(rs.getInt("requestid"));
				ri.setItemid(rs.getInt("itemid"));
				ri.setRequestedamount(rs.getInt("requestedamount"));
				ri.setStatus(rs.getString("status")); // use your actual column name
				ri.setItem(item);

				com.getRequest_Item().add(ri);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return com;
	}
}
