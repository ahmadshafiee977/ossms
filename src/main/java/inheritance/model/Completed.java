package inheritance.model;

import request.model.Request;
import java.sql.*;

public class Completed extends Request {

	private Date collecteddate;

	public Date getCollecteddate() {
		return collecteddate;
	}

	public void setCollecteddate(Date collecteddate) {
		this.collecteddate = collecteddate;
	}

}
