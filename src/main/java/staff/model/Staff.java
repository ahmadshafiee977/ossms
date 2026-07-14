package staff.model;

import java.io.Serializable;

import department.model.Department;

public class Staff implements Serializable {

	private int staffid;
	private String staffic;
	private String staffpassword;
	private String staffname;
	private String staffphonenumber;
	private String staffrole;
	private String staffstatus;
	private int departmentid;
	private Integer managerid;

	private Department dept;
	private Staff manager;

	public Staff getManager() {
		return manager;
	}

	public void setManager(Staff manager) {
		this.manager = manager;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public int getStaffid() {
		return staffid;
	}

	public void setStaffid(int staffid) {
		this.staffid = staffid;
	}

	public String getStaffic() {
		return staffic;
	}

	public void setStaffic(String staffic) {
		this.staffic = staffic;
	}

	public String getStaffpassword() {
		return staffpassword;
	}

	public void setStaffpassword(String staffpassword) {
		this.staffpassword = staffpassword;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getStaffphonenumber() {
		return staffphonenumber;
	}

	public void setStaffphonenumber(String staffphonenumber) {
		this.staffphonenumber = staffphonenumber;
	}

	public String getStaffrole() {
		return staffrole;
	}

	public void setStaffrole(String staffrole) {
		this.staffrole = staffrole;
	}

	public String getStaffstatus() {
		return staffstatus;
	}

	public void setStaffstatus(String staffstatus) {
		this.staffstatus = staffstatus;
	}

	public int getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(int departmentid) {
		this.departmentid = departmentid;
	}

	public Integer getManagerid() {
		return managerid;
	}

	public void setManagerid(Integer managerid) {
		this.managerid = managerid;
	}

}
