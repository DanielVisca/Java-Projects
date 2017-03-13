package fastfriends;

import java.util.ArrayList;

public class PersonalInfo {
	
	private String firstName;
	private String lastName;
	private int phoneNumber;
	private String email;
	private ArrayList<Contact> contacts;
	
	private PersonalInfo(String first,String last,int number, String email){
		this.firstName = first;
		this.lastName = last;
		this.phoneNumber = number;
		this.email = email;	
	}
	private void addContact(Contact newContact){
		this.contacts.add(newContact);
	}
	private void removeContact(Contact existingContact){
		for(Contact contact:contacts){
			if (existingContact.equals(contact)){
				
			}
		}
	}
}
