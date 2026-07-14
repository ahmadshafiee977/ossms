package inheritance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.ConnectionManager;
import inheritance.model.Approved;
import inheritance.model.Rejected;
import item.model.Item;
import requestitem.model.Request_Item;

public class RejectedDAO {

	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql;

	public static void addRejected(Rejected rejected) {
		try {
			con = ConnectionManager.getConnection();
			sql = "INSERT INTO rejected (requestid, rejectedreason ) VALUES (?,?)";

			ps = con.prepareStatement(sql);
			ps.setInt(1, rejected.getRequestid());
			ps.setString(2, rejected.getRejectedreason());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Rejected getRejectedByRequestId(int requestid) {
		Rejected rej = new Rejected();

		try {
			con = ConnectionManager.getConnection();
			sql = "SELECT r.*, ri.*, i.*, rj.* FROM request r" + " JOIN request_item ri ON r.requestid = ri.requestid"
					+ " JOIN item i ON ri.itemid = i.itemid JOIN rejected rj  ON rj.requestid = r.requestid "
					+ " WHERE r.requestid = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, requestid);
			rs = ps.executeQuery();
			while (rs.next()) {
				rej.setRequestid(rs.getInt("requestid"));
				rej.setSubmitteddate(rs.getDate("submitteddate"));
				rej.setRequesterid(rs.getInt("requesterid"));
				rej.setProcesserid(rs.getInt("processerid"));
				rej.setProcesseddate(rs.getDate("processeddate"));
				rej.setRequeststatus(rs.getString("requeststatus"));
				rej.setRejectedreason(rs.getString("rejectedreason"));

				Item item = new Item();

				item.setItemid(rs.getInt("itemid"));
				item.setItemname(rs.getString("itemname"));

				Request_Item ri = new Request_Item();

				ri.setRequestid(rs.getInt("requestid"));
				ri.setItemid(rs.getInt("itemid"));
				ri.setRequestedamount(rs.getInt("requestedamount"));
				ri.setStatus(rs.getString("status")); // use your actual column name
				ri.setItem(item);

				rej.getRequest_Item().add(ri);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rej;
	}
}
