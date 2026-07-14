package request.model;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import department.model.Department;
import inheritance.model.Approved;
import item.model.Item;
import requestitem.model.Request_Item;
import staff.model.Staff;

public class Request implements Serializable {

	private int requestid;
	private Date submitteddate;
	private int requesterid;
	private int processerid;
	private Date processeddate;
	private String requeststatus;

	private Staff requester;
	private Department department;
	private Approved approved;

	public Approved getApproved() {
		return approved;
	}

	public void setApproved(Approved approved) {
		this.approved = approved;
	}

	private List<Request_Item> request_Item = new ArrayList<>();
	private Item item;

	public List<Request_Item> getRequest_Item() {
		return request_Item;
	}

	public void setRequest_Item(List<Request_Item> reqItem) {
		this.request_Item = reqItem;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Request() {

	}

	public int getRequestid() {
		return requestid;
	}

	public void setRequestid(int requestid) {
		this.requestid = requestid;
	}

	public Date getSubmitteddate() {
		return submitteddate;
	}

	public void setSubmitteddate(Date submitteddate) {
		this.submitteddate = submitteddate;
	}

	public int getRequesterid() {
		return requesterid;
	}

	public void setRequesterid(int requesterid) {
		this.requesterid = requesterid;
	}

	public int getProcesserid() {
		return processerid;
	}

	public void setProcesserid(int processerid) {
		this.processerid = processerid;
	}

	public Date getProcesseddate() {
		return processeddate;
	}

	public void setProcesseddate(Date processeddate) {
		this.processeddate = processeddate;
	}

	public String getRequeststatus() {
		return requeststatus;
	}

	public void setRequeststatus(String requeststatus) {
		this.requeststatus = requeststatus;
	}

	public Staff getRequester() {
		return requester;
	}

	public void setRequester(Staff requester) {
		this.requester = requester;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
