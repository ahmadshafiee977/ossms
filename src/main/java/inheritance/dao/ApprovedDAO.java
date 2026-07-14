package inheritance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.ConnectionManager;
import inheritance.model.Approved;
import item.model.Item;
import requestitem.model.Request_Item;

public class ApprovedDAO {

	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql;

	public static void addApproved(Approved approved) {
		try {
			con = ConnectionManager.getConnection();
			sql = "INSERT INTO approved (requestid, collectiondate) VALUES (?,?)";

			ps = con.prepareStatement(sql);
			ps.setInt(1, approved.getRequestid());
			ps.setDate(2, approved.getCollectiondate());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Approved getApprovedByRequestId(int requestid) {
		Approved apr = new Approved();

		try {
			con = ConnectionManager.getConnection();
			sql = "SELECT r.*, ri.*, i.*, ap.* FROM request r" + " JOIN request_item ri ON r.requestid = ri.requestid"
					+ " JOIN item i ON ri.itemid = i.itemid JOIN approved ap ON ap.requestid = r.requestid "
					+ " WHERE r.requestid = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, requestid);
			rs = ps.executeQuery();
			while (rs.next()) {
				apr.setRequestid(rs.getInt("requestid"));
				apr.setSubmitteddate(rs.getDate("submitteddate"));
				apr.setRequesterid(rs.getInt("requesterid"));
				apr.setProcesserid(rs.getInt("processerid"));
				apr.setProcesseddate(rs.getDate("processeddate"));
				apr.setRequeststatus(rs.getString("requeststatus"));
				apr.setCollectiondate(rs.getDate("collectiondate"));

				Item item = new Item();

				item.setItemid(rs.getInt("itemid"));
				item.setItemname(rs.getString("itemname"));

				Request_Item ri = new Request_Item();

				ri.setRequestid(rs.getInt("requestid"));
				ri.setItemid(rs.getInt("itemid"));
				ri.setRequestedamount(rs.getInt("requestedamount"));
				ri.setStatus(rs.getString("status")); // use your actual column name
				ri.setItem(item);

				apr.getRequest_Item().add(ri);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return apr;
	}
}
