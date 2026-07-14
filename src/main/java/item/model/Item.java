package item.model;

import java.io.Serializable;

public class Item implements Serializable {
	private int itemid;
	private String itemname;
	private String itemdesc;
	private double itemprice;
	private String itemunit;

	private int maximumstock;
	private int minimumstock;
	private int currentstock;
	private String itemstatus;

	public String getItemstatus() {
		return itemstatus;
	}

	public void setItemstatus(String itemstatus) {
		this.itemstatus = itemstatus;
	}

	public Item() {

	}

	public int getItemid() {
		return itemid;
	}

	public void setItemid(int itemid) {
		this.itemid = itemid;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getItemdesc() {
		return itemdesc;
	}

	public void setItemdesc(String itemdesc) {
		this.itemdesc = itemdesc;
	}

	public double getItemprice() {
		return itemprice;
	}

	public void setItemprice(double itemprice) {
		this.itemprice = itemprice;
	}

	public String getItemunit() {
		return itemunit;
	}

	public void setItemunit(String itemunit) {
		this.itemunit = itemunit;
	}

	public int getMaximumstock() {
		return maximumstock;
	}

	public void setMaximumstock(int maximumstock) {
		this.maximumstock = maximumstock;
	}

	public int getMinimumstock() {
		return minimumstock;
	}

	public void setMinimumstock(int minimumstock) {
		this.minimumstock = minimumstock;
	}

	public int getCurrentstock() {
		return currentstock;
	}

	public void setCurrentstock(int currentstock) {
		this.currentstock = currentstock;
	}

}
