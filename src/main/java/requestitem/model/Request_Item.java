package requestitem.model;

import java.io.Serializable;

import item.model.Item;

public class Request_Item implements Serializable {

	private int requestid;
	private int itemid;
	private int requestedamount;
	private String note;
	private String status;

	private Item item;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Request_Item() {

	}

	public int getRequestid() {
		return requestid;
	}

	public void setRequestid(int requestid) {
		this.requestid = requestid;
	}

	public int getItemid() {
		return itemid;
	}

	public void setItemid(int itemid) {
		this.itemid = itemid;
	}

	public int getRequestedamount() {
		return requestedamount;
	}

	public void setRequestedamount(int requestedamount) {
		this.requestedamount = requestedamount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
