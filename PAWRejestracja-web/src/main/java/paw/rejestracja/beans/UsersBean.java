package paw.rejestracja.beans;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

//import paw.studenci.jpa.StudenciDAO;
import paw.studenci.jpa.UsersDAO;;


@Stateless(mappedName = "UsersBean")
public class UsersBean {
	private Logger logger = Logger.getLogger("PawRejestracja");
	@PersistenceUnit(unitName = "PawRejestracja")
	
	EntityManagerFactory emf;
	public UsersBean() {
		logger.info("User bean created...");
	}
	
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List<UsersDAO> getUsers() {
		
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery("FROM UsersDAO n  ", UsersDAO.class);
			return query.getResultList();
		} catch (Exception e) {
			logger.warning("UsersBean::getUsers, error while executing query: " + e);
		}		
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<UsersDAO> validate(String username, String password ) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			Query q = entityManager.createQuery("FROM UsersDAO n WHERE n.username = '" + username+"' and n.password = '" + password +"'" , UsersDAO.class);
			if( q.getResultList()!= null)
			{
				return q.getResultList() ;
			}
		} catch (Exception e) {
			
			return null;
		}
		return null;
	}	
	
}
