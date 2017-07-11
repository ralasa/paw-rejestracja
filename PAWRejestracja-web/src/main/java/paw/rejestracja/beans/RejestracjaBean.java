package paw.rejestracja.beans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import paw.studenci.jpa.StudenciDAO;
;



@Stateless(mappedName = "RejestracjaBean")
public class RejestracjaBean {

	public static final int DEFAULT_COUNT = 20;
	
	protected Logger logger = Logger.getLogger("PawRejestracja");

	@PersistenceUnit(unitName = "PawRejestracja")
	EntityManagerFactory emf;
	
	public RejestracjaBean() {
		logger.info("RejestracjaBean created...");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean persist(Object o) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			logger.info("Saving object: " + o);
			entityManager.persist(o);
			logger.info("Object saved: " + o);
			return true;
		} catch (Exception e) {
			logger.severe("RejestracjaBean::persist: Error writing to DB: " + e);
			logger.severe("" + e.fillInStackTrace());
			return false;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean remove(int id) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			Query q = entityManager.createQuery("DELETE FROM StudenciDAO n WHERE n.id = " + id);
			q.executeUpdate();
			logger.info("Object removed");
			return true;
		} catch (Exception e) {
			logger.severe("RejestracjaBean::remove: " + e);
			logger.severe("" + e.fillInStackTrace());
			return false;
		}
	}	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean merge(Object o) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			logger.info("Updating object: " + o);
			entityManager.merge(o);
			logger.info("Object updated: ");
			return true;
		} catch (Exception e) {
			logger.severe("RejestracjaBean::merge: Error writing to DB: " + e);
			logger.severe("" + e.fillInStackTrace());
			return false;
		}
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List<StudenciDAO> getLatestStudenci(int count) {
		if (count < 0)
			count = DEFAULT_COUNT;
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery("FROM StudenciDAO n ORDER BY n.createdAt DESC", StudenciDAO.class);
			if (count > 0)
				query.setMaxResults(count);
			return query.getResultList();
		} catch (Exception e) {
			logger.warning("RejestracjaBean::getLatestStudenci, error while executing query: " + e);
		}
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)		
	public StudenciDAO getStudenci(int id) {
		//logger.info("RejestracjaBean.getStudenci() invoked with id = "+id);
		EntityManager em = emf.createEntityManager();
		return em.find(StudenciDAO.class, id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public StudenciDAO getStudenci(String pageId) {
		//logger.info("RejestracjaBean.getStudenci() invoked with pageId = "+pageId);
		EntityManager em = emf.createEntityManager();
		//testowe
		//logger.info("em find wynik "+em.find(StudenciDAO.class,110).getNazwisko());
		//logger.info("getStudenci(110) wynik "+getStudenci(110));
		//koniec testowych
		try {
			//docelowo zamienic zeby zwracalo jeden wynik
			Query query = em.createQuery("FROM StudenciDAO n WHERE n.pageId = :pageParam", StudenciDAO.class).setParameter("pageParam",pageId);
			List<StudenciDAO> lista = query.getResultList();
			//logger.info("Query \"SELECT n.id FROM StudenciDAO n WHERE n.pageId ='"+pageId+"'\" performed with result = " +lista.get(0).getId()+" and returned "+ em.find(StudenciDAO.class, lista.get(0).getId()));
			return em.find(StudenciDAO.class,lista.get(0).getId());
		} catch (Exception e){
			logger.warning("RejestracjaBean::getStudenci(String pageId), error while executing query: "+e);
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List<StudenciDAO> getStudentFromDate(Date fromDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery("FROM StudenciDAO n WHERE n.updatedAt > '" + sdf.format(fromDate) + "' ORDER BY n.createdAt DESC", StudenciDAO.class);
			return query.getResultList();
		} catch (Exception e) {
			logger.warning("RejestracjaBean::getStudentFromDate, error while executing query: " + e);
		}		
		return null;
	}

}
