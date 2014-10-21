package com.example.phonebook.data;

public interface PhoneDAO {
	public int add(Phone p);
	public Phone[] getAll();
	public Phone getPhone(int ID);
	public Phone[] search(String keyword);
	public void removeAll();
	public void delete(int ID);
	public void edit(Phone p);
}
