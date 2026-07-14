package staff.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import request.dao.RequestDAO;
import request.model.Request;
import staff.dao.StaffDAO;
import staff.model.Staff;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import department.dao.DepartmentDAO;
import item.dao.ItemDAO;

/**
 * Servlet implementation class StaffController
 */
@WebServlet("/StaffController")
public class StaffController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StaffController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		try {
			if (action.equalsIgnoreCase("list"))
				listStaff(request, response);
			else if (action.equalsIgnoreCase("sadmineditform"))
				showEditForm(request, response);
			else if (action.equalsIgnoreCase("ownProfile"))
				showOwnProfile(request, response);
			else if (action.equals("resetPassword"))
				resetPassword(request, response);
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String staffeditid = request.getParameter("staffeditid");
		String action = request.getParameter("action");
		try {
			if (staffeditid == null)
				addStaff(request, response);
			else if (staffeditid != null && action.equals("sadminedit"))
				editStaff(request, response);
			else if (action.equals("editOwnInfo"))
				editOwnInfo(request, response);
			else if (action.equals("changePassword"))
				changePassword(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		int staffeditid = Integer.parseInt(request.getParameter("staffeditid"));
		request.setAttribute("depts", DepartmentDAO.getAllDepartments());
		request.setAttribute("staff", StaffDAO.getStaffById(staffeditid));
		request.setAttribute("managers", StaffDAO.getAllStaff());
		RequestDispatcher dispatcher = request.getRequestDispatcher("code/edit_pengguna.jsp");
		dispatcher.forward(request, response);
	}

	private void showOwnProfile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		Staff staff = (Staff) session.getAttribute("staff");
		int staffid = staff.getStaffid();

		request.setAttribute("depts", DepartmentDAO.getAllDepartments());
		request.setAttribute("staff", StaffDAO.getStaffById(staffid));
		request.setAttribute("managers", StaffDAO.getAllStaff());
		RequestDispatcher dispatcher = request.getRequestDispatcher("code/profil_pengguna.jsp");
		dispatcher.forward(request, response);
	}

	private void addStaff(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		Staff staff = new Staff();
		String rawPassword = "P@ssword.123";
		String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
		staff.setStaffname(request.getParameter("nama"));
		staff.setStaffic(request.getParameter("no-kp"));
		staff.setStaffphonenumber(request.getParameter("no-tel"));
		staff.setStaffrole(request.getParameter("tahap"));
		if (request.getParameter("pengurus") != null)
			staff.setManagerid(Integer.parseInt(request.getParameter("pengurus")));
		staff.setStaffpassword(hashedPassword);
		staff.setDepartmentid(Integer.parseInt(request.getParameter("jabatan")));
		staff.setStaffstatus(request.getParameter("status"));

		StaffDAO.addStaff(staff);

		response.sendRedirect("StaffController?action=list&alert=addstaff");
	}

	private void editStaff(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		System.out.println("dalam edit staff");
		int staffeditid = Integer.parseInt(request.getParameter("staffeditid"));
		Staff staff = new Staff();

		staff.setStaffid(staffeditid);
		staff.setStaffname(request.getParameter("u-name"));
		staff.setStaffic(request.getParameter("u-ic"));
		staff.setStaffphonenumber(request.getParameter("u-pn"));
		staff.setStaffrole(request.getParameter("u-role"));
		if (request.getParameter("u-manager") != null)
			staff.setManagerid(Integer.parseInt(request.getParameter("u-manager")));
		staff.setDepartmentid(Integer.parseInt(request.getParameter("u-dep")));
		staff.setStaffstatus(request.getParameter("u-status"));

		StaffDAO.editStaff(staff);

		response.sendRedirect("StaffController?action=list&alert=editstaff");
	}

	private void editOwnInfo(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		System.out.println("Dalam edit own info");
		int staffid = Integer.parseInt(request.getParameter("staffid"));
		Staff staff = new Staff();

		staff.setStaffid(staffid);
		staff.setStaffname(request.getParameter("nama"));
		staff.setStaffic(request.getParameter("no-kp"));
		staff.setStaffphonenumber(request.getParameter("no-tel"));
		staff.setDepartmentid(Integer.parseInt(request.getParameter("jabatan")));
		System.out.println("deptid: " + staff.getDepartmentid());

		StaffDAO.editOwnInfo(staff);

		response.sendRedirect("StaffController?action=ownProfile&alert=editOwn");
	}

	private void listStaff(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		List<Staff> staffs = new ArrayList<Staff>();
		HttpSession session = request.getSession(false);
		Staff staff = (Staff) session.getAttribute("staff");
		System.out.println("Role: " + staff.getStaffrole());
		if ("SUPERADMIN".equalsIgnoreCase(staff.getStaffrole())) {
			staffs = StaffDAO.getAllStaff();

		}
		request.setAttribute("depts", DepartmentDAO.getAllDepartments());
		request.setAttribute("staffs", staffs);
		RequestDispatcher req = request.getRequestDispatcher("code/pengguna_dashboard.jsp");
		req.forward(request, response);
	}

	private void changePassword(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		Staff staff = new Staff();

		int staffeditid = Integer.parseInt(request.getParameter("staffeditid"));
		String rawPassword = request.getParameter("kata-laluan-baru");
		String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

		staff.setStaffid(staffeditid);
		staff.setStaffpassword(hashedPassword);

		StaffDAO.changePassword(staff);

		response.sendRedirect("StaffController?action=ownProfile&alert=changePassword");

	}

	private void resetPassword(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		System.out.println("dalam reset password");
		Staff staff = new Staff();

		int staffeditid = Integer.parseInt(request.getParameter("staffeditid"));
		String rawPassword = "P@ssword.123";
		String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

		staff.setStaffid(staffeditid);
		staff.setStaffpassword(hashedPassword);

		StaffDAO.changePassword(staff);

		response.sendRedirect("StaffController?action=list&alert=resetPassword");

	}

}
