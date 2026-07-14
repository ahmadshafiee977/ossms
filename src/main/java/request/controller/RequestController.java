package request.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import requestitem.model.Request_Item;
import requestitem.dao.Request_ItemDAO;
import request.dao.RequestDAO;
import request.model.Request;
import staff.model.Staff;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import item.dao.ItemDAO;

/**
 * Servlet implementation class RequestController
 */
@WebServlet("/RequestController")
public class RequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public RequestController() {
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
				listRequests(request, response);
			else if (action.equalsIgnoreCase("addForm"))
				showAddForm(request, response);
			else if (action.equalsIgnoreCase("editForm"))
				showEditForm(request, response);
			else if (action.equalsIgnoreCase("viewForm"))
				showViewForm(request, response);
			else if (action.equalsIgnoreCase("report"))
				showReport(request, response);
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
		String requestId = request.getParameter("requestid");
		String action = request.getParameter("action");
		try {
			if (requestId == null)
				addRequest(request, response);
			else if ("delete".equals(action))
				deleteRequest(request, response);
			else
				editRequest(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void showReport(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDAO requestDAO = new RequestDAO();

		List<Map<String, Object>> report = requestDAO.getQuarterlySummary();

		request.setAttribute("report", report);

		RequestDispatcher dispatcher = request.getRequestDispatcher("code/laporan_dashboard.jsp");
		dispatcher.forward(request, response);
	}

	private void listRequests(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		List<Request> requests;
		int baru = 0;
		int lulus = 0;
		int selesai = 0;
		int tolak = 0;
		HttpSession session = request.getSession(false);
		Staff staff = (Staff) session.getAttribute("staff");
		if ("BIASA".equalsIgnoreCase(staff.getStaffrole())) {
			requests = RequestDAO.getOwnRequests(staff.getStaffid());
		} else {
			requests = RequestDAO.getAllRequests();
		}

		for (Request req : requests) {
			switch (req.getRequeststatus()) {
			case "BARU":
				baru++;
				break;
			case "DILULUSKAN":
				lulus++;
				break;
			case "SELESAI":
				selesai++;
				break;
			case "DITOLAK":
				tolak++;
				break;
			}
		}

		request.setAttribute("baruCount", baru);
		request.setAttribute("lulusCount", lulus);
		request.setAttribute("selesaiCount", selesai);
		request.setAttribute("tolakCount", tolak);
		request.setAttribute("requests", requests);
		RequestDispatcher req = request.getRequestDispatcher("code/permintaan_dashboard.jsp");
		req.forward(request, response);
	}

	private void addRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		HttpSession session = request.getSession(false);
		Staff staff = (Staff) session.getAttribute("staff");

		int requesterid = staff.getStaffid();
		// req.setRequesterid(Integer.parseInt());

		int newrequestId = RequestDAO.addRequest(requesterid);

		String[] items = request.getParameterValues("item");
		String[] qtys = request.getParameterValues("quant");
		String[] notes = request.getParameterValues("note");

		for (int i = 0; i < items.length; i++) {

			Request_Item ri = new Request_Item();

			ri.setRequestid(newrequestId);
			ri.setItemid(Integer.parseInt(items[i]));
			ri.setRequestedamount(Integer.parseInt(qtys[i]));
			ri.setNote(notes[i]);
			Request_ItemDAO.addRequestItem(ri);
		}

		response.sendRedirect("RequestController?action=list&alert=addreq");
	}

	private void deleteRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int requestId = Integer.parseInt(request.getParameter("requestid"));

		Request_ItemDAO.deleteByRequestId(requestId);
		RequestDAO.deleteRequestById(requestId);

		response.sendRedirect("RequestController?action=list&alert=delreq");
	}

	private void editRequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int requestId = Integer.parseInt(request.getParameter("requestid"));

		// Delete old items
		Request_ItemDAO.deleteByRequestId(requestId);

		// Read the new values
		String[] items = request.getParameterValues("item");
		String[] qtys = request.getParameterValues("quant");
		String[] notes = request.getParameterValues("note");

		// Insert them again
		for (int i = 0; i < items.length; i++) {

			Request_Item ri = new Request_Item();

			ri.setRequestid(requestId);
			ri.setItemid(Integer.parseInt(items[i]));
			ri.setRequestedamount(Integer.parseInt(qtys[i]));
			ri.setNote(notes[i]);

			Request_ItemDAO.addRequestItem(ri);
		}

		response.sendRedirect("RequestController?action=list&alert=editreq");
	}

	private void showAddForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub

		request.setAttribute("items", ItemDAO.getActiveItems());
		RequestDispatcher dispatcher = request.getRequestDispatcher("code/add_request.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		int reqId = Integer.parseInt(request.getParameter("requestid"));
		request.setAttribute("items", ItemDAO.getAllItems());
		request.setAttribute("req", RequestDAO.getRequestById(reqId));
		RequestDispatcher dispatcher = request.getRequestDispatcher("code/edit_request.jsp");
		dispatcher.forward(request, response);
	}

	private void showViewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		int reqId = Integer.parseInt(request.getParameter("requestid"));

		request.setAttribute("req", RequestDAO.getRequestById(reqId));
		RequestDispatcher dispatcher = request.getRequestDispatcher("code/view_request.jsp");
		dispatcher.forward(request, response);
	}

}
