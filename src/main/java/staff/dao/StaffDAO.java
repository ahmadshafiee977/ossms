package staff.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.ConnectionManager;
import department.model.Department;
import request.model.Request;
import staff.model.Staff;
import org.mindrot.jbcrypt.BCrypt;

public class StaffDAO {
	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql;

	public static Staff login(String ic, String password) {
		Staff staff = null;

		try {
			con = ConnectionManager.getConnection();

			sql = "SELECT * FROM staff WHERE staffic = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, ic);
			rs = ps.executeQuery();

			if (rs.next()) {
				if (BCrypt.checkpw(password, rs.getString("staffpassword"))) {
					staff = new Staff();
					staff.setStaffid(rs.getInt("staffid"));
					staff.setStaffname(rs.getString("staffname"));
					staff.setStaffrole(rs.getString("staffrole"));
					staff.setDepartmentid(rs.getInt("departmentId"));
					staff.setStaffstatus(rs.getString("staffstatus"));
				}
			}
			con.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException(e);
		}
		return staff;
	}

	public static void addStaff(Staff staff) {

		try {
			con = ConnectionManager.getConnection();

			sql = "INSERT INTO staff (staffid, staffic, staffpassword, staffname, staffphonenum, staffrole, staffstatus, departmentid, managerid) VALUES (nextval('staff_id_seq'), ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, staff.getStaffic());
			ps.setString(2, staff.getStaffpassword());
			ps.setString(3, staff.getStaffname());
			ps.setString(4, staff.getStaffphonenumber());
			ps.setString(5, staff.getStaffrole());
			ps.setString(6, staff.getStaffstatus());
			ps.setInt(7, staff.getDepartmentid());

			if (staff.getManagerid() == null) {
				ps.setNull(8, java.sql.Types.INTEGER);
			} else {
				ps.setInt(8, staff.getManagerid());
			}
			// ps.setInt(8, staff.getManagerid());

			ps.executeUpdate();

			con.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void editStaff(Staff staff) {

		try {
			con = ConnectionManager.getConnection();

			sql = "UPDATE staff SET staffic = ?, staffname = ?, staffphonenum = ?, staffrole = ?, staffstatus = ?, departmentid = ?, managerid = ? WHERE staffid = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, staff.getStaffic());
			ps.setString(2, staff.getStaffname());
			ps.setString(3, staff.getStaffphonenumber());
			ps.setString(4, staff.getStaffrole());
			ps.setString(5, staff.getStaffstatus());
			ps.setInt(6, staff.getDepartmentid());

			if (staff.getManagerid() == null) {
				ps.setNull(7, java.sql.Types.INTEGER);
			} else {
				ps.setInt(7, staff.getManagerid());
			}
			ps.setInt(8, staff.getStaffid());
			// ps.setInt(8, staff.getManagerid());

			ps.executeUpdate();

			con.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void editOwnInfo(Staff staff) {

		try {
			con = ConnectionManager.getConnection();

			sql = "UPDATE staff SET staffic = ?, staffname = ?, staffphonenum = ?, departmentid = ?  WHERE staffid = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, staff.getStaffic());
			ps.setString(2, staff.getStaffname());
			ps.setString(3, staff.getStaffphonenumber());
			ps.setInt(4, staff.getDepartmentid());

			ps.setInt(5, staff.getStaffid());

			ps.executeUpdate();

			con.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void changePassword(Staff staff) {

		try {
			con = ConnectionManager.getConnection();

			sql = "UPDATE staff SET staffpassword = ?  WHERE staffid = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, staff.getStaffpassword());
			ps.setInt(2, staff.getStaffid());

			ps.executeUpdate();

			con.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Staff getStaffById(int staffid) {
		Staff staff = new Staff();

		try {
			con = ConnectionManager.getConnection();
			sql = "SELECT s.*, m.staffname AS managername, d.departmentname FROM staff s"
					+ " FULL OUTER JOIN staff m ON s.managerid = m.staffid "
					+ "JOIN department d ON s.departmentid = d.departmentid WHERE s.staffid = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, staffid);
			rs = ps.executeQuery();
			if (rs.next()) {
				staff.setStaffid(rs.getInt("staffid"));
				staff.setStaffic(rs.getString("staffic"));
				staff.setStaffname(rs.getString("staffname"));
				staff.setStaffphonenumber(rs.getString("staffphonenum"));
				staff.setStaffrole(rs.getString("staffrole"));
				staff.setStaffstatus(rs.getString("staffstatus"));
				staff.setDepartmentid(rs.getInt("departmentid"));

				int temp = rs.getInt("managerid");
				if (!rs.wasNull()) {
					staff.setManagerid(temp);
				}

				Staff manager = new Staff();
				manager.setStaffname(rs.getString("managername"));
				staff.setManager(manager);

				Department dept = new Department();
				dept.setDepartmentname(rs.getString("departmentname"));

				staff.setDept(dept);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return staff;
	}

	public static List<Staff> getAllStaff() {
		List<Staff> staffs = new ArrayList<Staff>();

		try {
			con = ConnectionManager.getConnection();
			// sql = "SELECT * FROM request WHERE requesterid = ?";
			sql = "SELECT * FROM staff s " + "LEFT JOIN department d " + "ON d.departmentid = s.departmentid";
			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {

				Staff staff = new Staff();

				staff.setStaffid(rs.getInt("staffid"));
				staff.setStaffname(rs.getString("staffname"));
				staff.setStaffic(rs.getString("staffic"));
				staff.setStaffphonenumber(rs.getString("staffphonenum"));
				staff.setStaffrole(rs.getString("staffrole"));
				staff.setStaffstatus(rs.getString("staffstatus"));

				Department dept = new Department();
				dept.setDepartmentname(rs.getString("departmentname"));

				staff.setDept(dept);
				staffs.add(staff);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return staffs;
	}

}
