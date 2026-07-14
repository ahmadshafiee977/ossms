package department.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionManager;
import department.model.Department;

public class DepartmentDAO {

	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static String sql;

	public static void addDepartment(Department dept) {
		try {
			con = ConnectionManager.getConnection();

			sql = "INSERT INTO department (departmentname) VALUES (?)";

			ps = con.prepareStatement(sql);
			ps.setString(1, dept.getDepartmentname());
			ps.executeUpdate();

			con.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateDepartment(Department dept) {
		try {
			con = ConnectionManager.getConnection();

			sql = "UPDATE department SET departmentname = ?  WHERE departmentid = ?";

			ps = con.prepareStatement(sql);
			ps.setString(1, dept.getDepartmentname());
			ps.setInt(2, dept.getDepartmentid());
			ps.executeUpdate();

			con.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Department> getAllDepartments() {

		List<Department> depts = new ArrayList<Department>();

		try {
			con = ConnectionManager.getConnection();
			sql = "SELECT * FROM department";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Department dept = new Department();
				dept.setDepartmentid(rs.getInt("departmentid"));
				dept.setDepartmentname(rs.getString("departmentname"));
				depts.add(dept);
			}

			con.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return depts;

	}

	public static Department getDepartmentById(int departmentid) {
		Department dept = new Department();

		try {
			con = ConnectionManager.getConnection();
			sql = "SELECT * FROM department WHERE departmentid = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, departmentid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dept.setDepartmentid(rs.getInt("departmentid"));
				dept.setDepartmentname(rs.getString("departmentname"));
			}
			con.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dept;
	}

}
