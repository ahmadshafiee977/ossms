package department.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import department.dao.DepartmentDAO;
import department.model.Department;

/**
 * Servlet implementation class DepartmentController
 */
@WebServlet("/DepartmentController")
public class DepartmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DepartmentController() {
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
				listDepartments(request, response);
			else if (action.equalsIgnoreCase("getDepartmentJSON"))
				getDepartmentJSON(request, response);
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
		String deptid = request.getParameter("deptid");
		System.out.print("received deptid: " + deptid);
		try {
			if (deptid == null) {
				addDepartment(request, response);
			} else {
				updateDepartment(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void listDepartments(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		request.setAttribute("depts", DepartmentDAO.getAllDepartments());

		RequestDispatcher req = request.getRequestDispatcher("code/jabatan_dashboard.jsp");
		req.forward(request, response);
	}

	private void getDepartmentJSON(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		int deptid = Integer.parseInt(request.getParameter("deptid"));
		Department dept = DepartmentDAO.getDepartmentById(deptid);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("{");
		out.print("\"departmentid\":" + dept.getDepartmentid() + ",");
		out.print("\"departmentname\":\"" + dept.getDepartmentname() + "\"");
		out.print("}");

		out.flush();

	}

	private void addDepartment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		Department dept = new Department();
		dept.setDepartmentname(request.getParameter("t-name"));

		DepartmentDAO.addDepartment(dept);
		response.sendRedirect("DepartmentController?action=list&alert=adddept");

	}

	private void updateDepartment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		int deptid = Integer.parseInt(request.getParameter("deptid"));
		Department dept = DepartmentDAO.getDepartmentById(deptid);

		dept.setDepartmentname(request.getParameter("s-name"));

		DepartmentDAO.updateDepartment(dept);
		response.sendRedirect("DepartmentController?action=list&alert=editdept");

	}
}
