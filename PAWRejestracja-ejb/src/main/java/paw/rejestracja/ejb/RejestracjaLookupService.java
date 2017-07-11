package paw.rejestracja.ejb;

import java.util.List;

//import paw.news.jpa.News;
import paw.studenci.jpa.StudenciDAO;
import paw.studenci.jpa.UsersDAO;


public interface RejestracjaLookupService {
	public StudenciDAO getStudenci(int id);
	public StudenciDAO getStudenci(String page_id);
	public List<StudenciDAO> getAllStudenci();
	public boolean merge(StudenciDAO studenci);
	public boolean persist(StudenciDAO studenci);
	public boolean remove(int id);
	public List<UsersDAO> validate(String username, String password);
	
}
