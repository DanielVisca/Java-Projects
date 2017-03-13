package fastfriends;

public class Contact {
	private String firstName;
	private String lastName;
	private int phoneNumber;
	private String email;
	
	public Contact(String first,String last, int number, String email){
		this.firstName = first;
		this.lastName = last;
		this.phoneNumber = number;
		this.email = email;
	}
	public String getName(){
		return this.firstName + " " + this.lastName;
	}
	public int getNumber(){
		return this.phoneNumber;
	}
	public String getEmail(){
		return this.email;
	}
}
