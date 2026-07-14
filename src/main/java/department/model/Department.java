package department.model;

import java.io.Serializable;

public class Department implements Serializable {
	private int departmentid;
	private String departmentname;

	public Department() {

	}

	public int getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(int departmentid) {
		this.departmentid = departmentid;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

}
