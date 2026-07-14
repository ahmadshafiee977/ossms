package staff.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import staff.dao.StaffDAO;
import staff.model.Staff;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public login() {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ic = request.getParameter("ic");
		String password = request.getParameter("password");

		Staff staff = StaffDAO.login(ic, password);
		if (staff == null) {
			request.setAttribute("error", "No Kad Pengenalan atau kata laluan tidak sah.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
			return;
		}

		if (staff.getStaffstatus().equals("TIDAK AKTIF")) {
			request.setAttribute("error",
					"Maaf, akaun anda adalah tidak aktif, sila hubungi pegawai IT Majlis Daerah Tangkak.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
			return;
		}
		HttpSession session = request.getSession();
		session.setAttribute("staff", staff);

		if (!staff.getStaffrole().equals("SUPERADMIN"))
			response.sendRedirect("RequestController?action=list&login=success");
		else
			response.sendRedirect("StaffController?action=list&login=success");

	}

}
