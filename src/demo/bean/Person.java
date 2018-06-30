package demo.bean;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class Person {
	private String name;
	private int age;
	private String city;
	private Home home;
	
	private Map<String, String> books;

	public Person() {		
	}
	
	public Person(String city, int age) {
		System.out.println("call person constructor now!");
		this.city = city;
		this.age = age;
	}

	@Autowired
	public void setHome(Home home) {
		this.home = home;
	}
	public void setBooks(Map<String, String> books) {
		this.books = books;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}	
	public void close() {
		System.out.println("close person " + name + " now!");
	}
	
	public void info(){
		String address = "";
		if (home != null && home.getAddress() != null) {
			address = home.toString();
		}
		System.out.println("name:" + name + " age:" + age + " city:" + this.city + " home:" + address);
	}
	
	public void booksInfo(){
		if (books == null) {
			return;
		}
		System.out.println("books:");
		for (String key : books.keySet()) {
			System.out.println("\tkey:" + key + " book:" + books.get(key));
		}
	}
	
	private void init() {
		System.out.println("init person " + name + " now!");		
	}
}
