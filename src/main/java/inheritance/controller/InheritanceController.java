package inheritance.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import request.dao.RequestDAO;
import request.model.Request;
import requestitem.dao.Request_ItemDAO;
import staff.model.Staff;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import inheritance.dao.ApprovedDAO;
import inheritance.dao.CompletedDAO;
import inheritance.dao.RejectedDAO;
import inheritance.model.Approved;
import inheritance.model.Completed;
import inheritance.model.Rejected;
import item.dao.ItemDAO;

/**
 * Servlet implementation class InheritanceController
 */
@WebServlet("/InheritanceController")
public class InheritanceController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InheritanceController() {
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
			if (action.equalsIgnoreCase("viewForm"))
				showViewForm(request, response);
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
		String action = request.getParameter("action");
		String requestId = request.getParameter("requestid");
		try {
			if (requestId != null) {
				if (action.equals("approve"))
					approveRequest(request, response);
				else if (action.equals("reject"))
					rejectRequest(request, response);
				else if (action.equals("complete"))
					completeRequest(request, response);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void showViewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		int requestId = Integer.parseInt(request.getParameter("requestid"));
		Request req = RequestDAO.getRequestById(requestId);

		switch (req.getRequeststatus()) {
		case "DILULUSKAN":
			Approved approved = ApprovedDAO.getApprovedByRequestId(requestId);
			request.setAttribute("req", approved);
			break;

		case "DITOLAK":
			Rejected rejected = RejectedDAO.getRejectedByRequestId(requestId);
			request.setAttribute("req", rejected);
			break;

		case "SELESAI":
			Completed completed = CompletedDAO.getCompletedByRequestId(requestId);
			request.setAttribute("req", completed);
			break;
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("code/view_request.jsp");
		dispatcher.forward(request, response);
	}

	private void approveRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		HttpSession session = request.getSession(false);
		Staff staff = (Staff) session.getAttribute("staff");

		int requestId = Integer.parseInt(request.getParameter("requestid"));
		int processerid = staff.getStaffid();
		String[] items = request.getParameterValues("item");
		String[] qtys = request.getParameterValues("quant");
		String[] status = request.getParameterValues("status");

		Date collectionDate = Date.valueOf(request.getParameter("col-date"));

		RequestDAO.updateStatus(requestId, processerid, "DILULUSKAN");

		for (int i = 0; i < items.length; i++) {

			Request_ItemDAO.updateStatus(requestId, Integer.parseInt(items[i]), status[i]);

			if ("LULUS".equals(status[i])) {

				ItemDAO.deductStock(Integer.parseInt(items[i]), Integer.parseInt(qtys[i]));
			}
		}

		Approved approved = new Approved();
		approved.setRequestid(requestId);
		approved.setCollectiondate(collectionDate);

		ApprovedDAO.addApproved(approved);

		response.sendRedirect("RequestController?action=list&alert=approvereq");
	}

	private void rejectRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		HttpSession session = request.getSession(false);
		Staff staff = (Staff) session.getAttribute("staff");

		int requestId = Integer.parseInt(request.getParameter("requestid"));
		int processerid = staff.getStaffid();
		String rejectedreason = request.getParameter("rej-reason");
		RequestDAO.updateStatus(requestId, processerid, "DITOLAK");

		Rejected rejected = new Rejected();
		rejected.setRequestid(requestId);
		rejected.setRejectedreason(rejectedreason);
		RejectedDAO.addRejected(rejected);

		response.sendRedirect("RequestController?action=list&alert=rejectreq");
	}

	private void completeRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		HttpSession session = request.getSession(false);
		Staff staff = (Staff) session.getAttribute("staff");

		int requestId = Integer.parseInt(request.getParameter("requestid"));
		int processerid = staff.getStaffid();

		RequestDAO.updateStatus(requestId, processerid, "SELESAI");

		Completed completed = new Completed();
		completed.setRequestid(requestId);
		completed.setCollecteddate(Date.valueOf(request.getParameter("collected-date")));
		CompletedDAO.addCompleted(completed);

		response.sendRedirect("RequestController?action=list&alert=completereq");
	}
}
