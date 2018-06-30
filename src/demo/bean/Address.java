package demo.bean;

public class Address {
	private String address;

	public String getAddress() {
		if (this.address == null) {
			return "";
		}
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String toString() {
		return "address:" + getAddress();
	}
}
