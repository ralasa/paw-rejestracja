package paw.rejestracja.beans;

import paw.rejestracja.ejb.RejestracjaLookupService;
import paw.studenci.jpa.StudenciDAO;
import paw.studenci.jpa.UsersDAO;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@ManagedBean(eager = true)
@ApplicationScoped
public class RejestracjaLookupDatabaseBean implements RejestracjaLookupService, Serializable {

	private static final long serialVersionUID = -5442331602203781978L;

	private Logger logger = Logger.getLogger("PawRejestracja");

	@EJB(name = "RejestracjaBean")
	private RejestracjaBean rejestracjaBean;
	@EJB(name = "UserBean")
	private UsersBean userBean;

	public RejestracjaLookupDatabaseBean() {
		logger.info("RejestracjaLookupDatabaseBean created");
	}

	public RejestracjaBean getRejestracjaBean() {
		return rejestracjaBean;
	}

	public void setRejestracjaBean(RejestracjaBean rejestracjaBean) {
		this.rejestracjaBean = rejestracjaBean;
	}

	@Override
	public List<StudenciDAO> getAllStudenci() {
		return rejestracjaBean.getLatestStudenci(0);
	}
	
	
	@Override
	public StudenciDAO getStudenci(int id) {
		// TODO Auto-generated method stub
		StudenciDAO n = rejestracjaBean.getStudenci(id);
		if (n == null)
			logger.info("LookupService returning null for id=" + id);
		else
			logger.info("Student " + id );
		return n;
	}

	@Override
	public StudenciDAO getStudenci(String pageId) {
		StudenciDAO n = rejestracjaBean.getStudenci(pageId);
		if (n==null) logger.info("Lookup service returning null for pageId = " + pageId);
		//else logger.info("Lookup service returning student "+n.getImie()+" "+n.getNazwisko()+" for pageId = "+pageId);
		return n;
	}

	@Override
	public boolean merge(StudenciDAO studenci) {
		return rejestracjaBean.merge(studenci);
	}

	@Override
	public boolean persist(StudenciDAO studenci) {
		return rejestracjaBean.persist(studenci);
	}

	@Override
	public boolean remove(int id) {
		return rejestracjaBean.remove(id);
 	}
	
	@Override
	public List<UsersDAO> validate(String username, String password) {
		//StudenciDAO n = rejestracjaBean.getStudenci(id);
		List<UsersDAO> n = userBean.validate(username, password);
		if (n.isEmpty()){
			return null;
		}
		return n	;
				//userBean.validate(username,password);
 	}

}
