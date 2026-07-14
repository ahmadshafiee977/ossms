package request.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

import connection.ConnectionManager;
import department.model.Department;
import inheritance.model.Approved;
import item.model.Item;
import staff.model.Staff;

import request.model.Request;
import requestitem.model.Request_Item;

public class RequestDAO {

	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql;

	public List<Map<String, Object>> getQuarterlySummary() {
		List<Map<String, Object>> list = new ArrayList<>();

		try {
			con = ConnectionManager.getConnection();
			sql = "SELECT " + "    'Q' || TO_CHAR(r.submitteddate, 'Q') AS quarter, "
					+ "    COUNT(DISTINCT r.requestid) AS total_requests, "
					+ "    SUM(CASE WHEN r.requeststatus = 'BARU' THEN 1 ELSE 0 END) AS baru, "
					+ "    SUM(CASE WHEN r.requeststatus = 'DILULUSKAN' THEN 1 ELSE 0 END) AS diluluskan, "
					+ "    SUM(CASE WHEN r.requeststatus = 'DITOLAK' THEN 1 ELSE 0 END) AS ditolak, "
					+ "    SUM(CASE WHEN r.requeststatus = 'SELESAI' THEN 1 ELSE 0 END) AS selesai, "
					+ "    SUM(ri.requestedamount) AS total_items_requested, "
					+ "    SUM(CASE WHEN ri.status = 'LULUS' THEN ri.requestedamount ELSE 0 END) AS total_items_lulus, "
					+ "    SUM(CASE WHEN ri.status = 'TOLAK' THEN ri.requestedamount ELSE 0 END) AS total_items_tolak, "
					+ "    (SELECT SUM(currentstock * itemprice) FROM item) AS current_stock_value " + "FROM request r "
					+ "JOIN request_item ri ON r.requestid = ri.requestid "
					+ "WHERE EXTRACT(YEAR FROM r.submitteddate) = EXTRACT(YEAR FROM CURRENT_DATE) "
					+ "GROUP BY TO_CHAR(r.submitteddate, 'Q') " + "ORDER BY TO_CHAR(r.submitteddate, 'Q')";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Map<String, Object> row = new HashMap<>();

				row.put("quarter", rs.getString("quarter"));
				row.put("total_requests", rs.getInt("total_requests"));
				row.put("baru", rs.getInt("baru"));
				row.put("diluluskan", rs.getInt("diluluskan"));
				row.put("ditolak", rs.getInt("ditolak"));
				row.put("selesai", rs.getInt("selesai"));
				row.put("total_items_requested", rs.getInt("total_items_requested"));
				row.put("total_items_lulus", rs.getInt("total_items_lulus"));
				row.put("total_items_tolak", rs.getInt("total_items_tolak"));
				row.put("current_stock_value", rs.getDouble("current_stock_value"));

				list.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public static void deleteRequestById(int reqid) {
		try {
			con = ConnectionManager.getConnection();
			sql = "DELETE FROM request WHERE requestid = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, reqid);
			ps.executeUpdate();

			con.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Request getRequestById(int reqid) {
		Request req = new Request();
		try {
			con = ConnectionManager.getConnection();
			sql = "SELECT r.*, ri.*, i.* FROM request r" + " JOIN request_item ri ON r.requestid = ri.requestid"
					+ " JOIN item i ON ri.itemid = i.itemid" + " WHERE r.requestid = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, reqid);
			rs = ps.executeQuery();
			while (rs.next()) {
				req.setRequestid(rs.getInt("requestid"));
				req.setSubmitteddate(rs.getDate("submitteddate"));
				req.setRequesterid(rs.getInt("requesterid"));
				req.setProcesserid(rs.getInt("processerid"));
				req.setProcesseddate(rs.getDate("processeddate"));
				req.setRequeststatus(rs.getString("requeststatus"));

				Item item = new Item();
				item.setItemid(rs.getInt("itemid"));
				item.setItemname(rs.getString("itemname"));
				item.setItemdesc(rs.getString("itemdesc"));
				item.setItemprice(rs.getDouble("itemprice"));
				item.setCurrentstock(rs.getInt("currentstock"));
				item.setMaximumstock(rs.getInt("maximumstock"));
				item.setMinimumstock(rs.getInt("minimumstock"));

				// Create RequestItem
				Request_Item requestItem = new Request_Item();
				requestItem.setRequestid(rs.getInt("requestid"));
				requestItem.setItemid(rs.getInt("itemid"));
				requestItem.setRequestedamount(rs.getInt("requestedamount"));
				requestItem.setNote(rs.getString("note"));
				// requestItem.setStatus("status");
				requestItem.setItem(item);

				// Add to the Request
				req.getRequest_Item().add(requestItem);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(req.getRequest_Item().size());
		return req;
	}

	public static List<Request> getOwnRequests(int staffid) {
		List<Request> requests = new ArrayList<Request>();

		try {
			con = ConnectionManager.getConnection();
			// sql = "SELECT * FROM request WHERE requesterid = ?";
			sql = "SELECT r.*, s.staffname, d.departmentname, ap.collectiondate "
					+ "FROM request r JOIN staff s ON r.requesterid = s.staffid "
					+ "LEFT OUTER JOIN department d ON d.departmentid = s.departmentid "
					+ "FULL OUTER JOIN approved ap ON r.requestid = ap.requestid WHERE requesterid = ? "
					+ "ORDER BY submitteddate desc";
			ps = con.prepareStatement(sql);
			ps.setInt(1, staffid);
			rs = ps.executeQuery();

			while (rs.next()) {

				Staff staff = new Staff();

				staff.setStaffid(rs.getInt("requesterid"));
				staff.setStaffname(rs.getString("staffname"));

				Department dept = new Department();
				dept.setDepartmentname(rs.getString("departmentname"));

				Request req = new Request();
				req.setRequestid(rs.getInt("requestid"));
				req.setSubmitteddate(rs.getDate("submitteddate"));
				req.setRequesterid(rs.getInt("requesterid"));
				req.setProcesserid(rs.getInt("processerid"));
				req.setProcesseddate(rs.getDate("processeddate"));
				req.setRequeststatus(rs.getString("requeststatus"));

				Approved apr = new Approved();
				apr.setCollectiondate(rs.getDate("collectiondate"));

				req.setApproved(apr);
				req.setRequester(staff);
				req.setDepartment(dept);
				requests.add(req);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return requests;
	}

	public static List<Request> getAllRequests() {
		List<Request> requests = new ArrayList<Request>();

		try {
			con = ConnectionManager.getConnection();
			sql = "SELECT r.*, s.staffname, d.departmentname, ap.collectiondate "
					+ "FROM request r JOIN staff s ON r.requesterid = s.staffid "
					+ "LEFT OUTER JOIN department d ON d.departmentid = s.departmentid "
					+ "LEFT OUTER JOIN approved ap ON r.requestid = ap.requestid " + "ORDER BY submitteddate desc";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Staff staff = new Staff();

				staff.setStaffid(rs.getInt("requesterid"));
				staff.setStaffname(rs.getString("staffname"));

				Department dept = new Department();
				dept.setDepartmentname(rs.getString("departmentname"));

				Request req = new Request();
				req.setRequestid(rs.getInt("requestid"));
				req.setSubmitteddate(rs.getDate("submitteddate"));

				req.setProcesserid(rs.getInt("processerid"));
				req.setProcesseddate(rs.getDate("processeddate"));
				req.setRequeststatus(rs.getString("requeststatus"));
				System.out.println("req status: " + req.getRequeststatus());
				Approved apr = new Approved();
				apr.setCollectiondate(rs.getDate("collectiondate"));

				req.setApproved(apr);

				req.setRequester(staff);
				req.setDepartment(dept);
				requests.add(req);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return requests;
	}

	public static int addRequest(int requesterid) {
		int requestid = 0;
		try {
			con = ConnectionManager.getConnection();

			String seqSql = "SELECT nextval('request_id_seq')";
			ps = con.prepareStatement(seqSql);
			rs = ps.executeQuery();
			if (rs.next()) {
				requestid = rs.getInt(1);
			}

			sql = "INSERT INTO request (requestid, requesterid) VALUES (?, ?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, requestid);
			ps.setInt(2, requesterid);

			ps.executeUpdate();

			con.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return requestid;

	}

	public static void updateStatus(int requestid, int processerid, String status) {
		try {
			con = ConnectionManager.getConnection();

			sql = "UPDATE request SET requeststatus = ?, processerid = ? WHERE requestid = ?";

			ps = con.prepareStatement(sql);
			ps.setString(1, status);
			ps.setInt(2, processerid);
			ps.setInt(3, requestid);

			ps.executeUpdate();

			con.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
