package item.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import item.dao.ItemDAO;
import item.model.Item;

/**
 * Servlet implementation class ItemController
 */
@WebServlet("/ItemController")
public class ItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ItemController() {
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
				listItems(request, response);
			else if (action.equalsIgnoreCase("getItemJSON"))
				getItemJSON(request, response);
			else if (action.equalsIgnoreCase("getStock"))
				getStock(request, response);
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
		String itemid = request.getParameter("itemid");
		// System.out.println("Received itemid: " + itemid);
		try {
			if (itemid == null) {
				addItem(request, response);
			} else {
				updateItem(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void getItemJSON(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int itemid = Integer.parseInt(request.getParameter("itemid"));
		Item item = ItemDAO.getItemById(itemid);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		out.print("{");
		out.print("\"itemid\":" + item.getItemid() + ",");
		out.print("\"itemname\":\"" + item.getItemname() + "\",");
		out.print("\"itemdesc\":\"" + item.getItemdesc() + "\",");
		out.print("\"itemprice\":" + item.getItemprice() + ",");
		out.print("\"itemunit\":\"" + item.getItemunit() + "\",");
		out.print("\"itemstatus\":\"" + item.getItemstatus() + "\",");
		out.print("\"maximumstock\":" + item.getMaximumstock() + ",");
		out.print("\"minimumstock\":" + item.getMinimumstock() + ",");
		out.print("\"currentstock\":" + item.getCurrentstock());
		out.print("}");

		out.flush();

	}

	private void listItems(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		request.setAttribute("items", ItemDAO.getAllItems());

		RequestDispatcher req = request.getRequestDispatcher("code/barang_dashboard.jsp");
		req.forward(request, response);
	}

	private void getStock(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		int itemId = Integer.parseInt(request.getParameter("itemId"));

		Item item = ItemDAO.getItemById(itemId);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		out.print("{\"currentStock\":" + item.getCurrentstock() + "}");

		out.flush();
	}

	private void addItem(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		Item i = new Item();

		i.setItemname(request.getParameter("t-name"));
		i.setItemdesc(request.getParameter("t-desc"));
		i.setItemprice(Double.parseDouble(request.getParameter("t-price")));
		i.setItemunit(request.getParameter("t-unit"));
		i.setItemstatus(request.getParameter("t-status"));
		i.setMaximumstock(Integer.parseInt(request.getParameter("t-max")));
		i.setMinimumstock(Integer.parseInt(request.getParameter("t-min")));
		i.setCurrentstock(Integer.parseInt(request.getParameter("t-cur")));

		ItemDAO.addItem(i);

		response.sendRedirect("ItemController?action=list&alert=additem");
	}

	private void updateItem(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		int itemId = Integer.parseInt(request.getParameter("itemid"));
		int restock_qty = 0;
		Item i = ItemDAO.getItemById(itemId);

		String rs_quant = request.getParameter("rs-quant");
		if (rs_quant != null) {
			int old_qty = i.getCurrentstock();
			int new_qty = old_qty + Integer.parseInt(rs_quant);

			i.setCurrentstock(new_qty);

		} else {
			i.setItemname(request.getParameter("s-name"));
			i.setItemdesc(request.getParameter("s-desc"));
			i.setItemprice(Double.parseDouble(request.getParameter("s-price")));
			i.setItemunit(request.getParameter("s-unit"));
			i.setItemstatus(request.getParameter("s-status"));
			i.setMaximumstock(Integer.parseInt(request.getParameter("s-max")));
			i.setMinimumstock(Integer.parseInt(request.getParameter("s-min")));
			i.setCurrentstock(Integer.parseInt(request.getParameter("s-cur")));

			restock_qty = Integer.parseInt(request.getParameter("s-restock"));
		}
		ItemDAO.updateItem(i, restock_qty);
		response.sendRedirect("ItemController?action=list&alert=edititem");
	}

}
