package inheritance.model;

import request.model.Request;

public class Rejected extends Request {
	private String rejectedreason;

	public String getRejectedreason() {
		return rejectedreason;
	}

	public void setRejectedreason(String rejectedreason) {
		this.rejectedreason = rejectedreason;
	}

}
