package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;


public class ClientBLL {

	private List<Validator<Client>> validators;
	private ClientDAO studentDAO;

	public ClientBLL() {
		validators = new ArrayList<Validator<Client>>();
		validators.add(new EmailValidator());

		studentDAO = new ClientDAO();
	}
	/**
	 @param id : id-ul clientului de cautat
	 @return : id-ul gasit
	 @throws NoSuchElementException daca nu exista clientul in baza de date
	 */
	public Client findStudentById(int id) {
		Client st = studentDAO.findById(id);
		if (st == null) {
			throw new NoSuchElementException("The student with id =" + id + " was not found!");
		}
		return st;
	}
	/**
	 @param id,nume,email : toate campurile de inserat a tabelei: id,nume,email
	 @return : noul client inserat
	 */
	public int AddClient(int id,String nume,String email)
	{
		Client c = new Client(id,email,nume);
		for(Validator<Client> v: validators)
		{
			try{
				v.validate(c);
			}
			catch(IllegalArgumentException BUMTZI)
			{
			 	return -10;
			}
		}
		int st = studentDAO.insert(c);
		return st;
	}
	/**
	 @param nume,email: toate campurile care pot fi actualizate a tabelei: nume,email
	 @return : noul client actualizat
	 */
	public Client UpdateClient(int id,String nume,String email)
	{

		if(!Objects.equals(nume," "))
			studentDAO.update(findStudentById(id),"name",nume,id);
		if(!Objects.equals(email," "))
			studentDAO.update(findStudentById(id),"email",email,id);
		return new Client(-1,null,null);
	}
	/**
	 @param id: toate campurile de inserat a tabelei: id,nume,email
	 */
	public void DeleteClient(int id)
	{
		studentDAO.delete(id);
		System.out.println("am sters clientul");
	}

}
