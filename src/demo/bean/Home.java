package demo.bean;

public class Home {
	private Address address;
	
	private int number;
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Home() {
		System.out.println("call home constructor now!");
	}
	
	public Home(Address address) {
		System.out.println("call home constructor now!");
		this.address = address;
	}
	
	public Home(Address address, int num) {
		System.out.println("call home constructor now!");
		this.address = address;
		this.number = num;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
	public String toString() {
		if (this.address == null) {
			return "home:";
		}
		return "home:" + this.address.toString() + " number:" + this.number;
	}
}
