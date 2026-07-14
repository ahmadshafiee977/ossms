package inheritance.model;

import request.model.Request;
import java.sql.*;

public class Approved extends Request {

	private Date collectiondate;

	public Date getCollectiondate() {
		return collectiondate;
	}

	public void setCollectiondate(Date collectiondate) {
		this.collectiondate = collectiondate;
	}

}
