package paw.rejestracja.beans;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import paw.studenci.jpa.StudenciDAO;


@ManagedBean
@RequestScoped
public class RejestracjaManager implements Serializable {
	private static final long serialVersionUID = 3754037223174977077L;
	private static final int resultPerPage = 10;

	private List<StudenciDAO> allStudentList;
	private List<StudenciDAO> studentList;

	
	private int removeId;
	
	private Logger logger = Logger.getLogger("PawRejestracja");
	
	@ManagedProperty(value="#{rejestracjaLookupDatabaseBean}")
	private RejestracjaLookupDatabaseBean rejestracjaLookupService;
	
	//atrybuty potrzebne do wyświetlania listy s
	private int page = 1, nextPage = 1, lastPage = 1, firstPage = 1, previousPage = 1;
	private int allPages = 0;
	private long recordsCount = 0;
	private String tableListCaption = "Brak rekordów do wyświetlenia";
	
	public RejestracjaManager() {
		logger.info("RejestracjaManager bean created.");
	}
	
	public RejestracjaLookupDatabaseBean getRejestracjaLookupService() {
		return rejestracjaLookupService;
	}

	public void setRejestracjaLookupService(RejestracjaLookupDatabaseBean rejestracjaLookupService) {
		logger.info("SetRejestracjaBean() invoked. Trybing to receive message list.");		
		this.rejestracjaLookupService = rejestracjaLookupService;
		if (this.rejestracjaLookupService != null) {
			allStudentList = this.rejestracjaLookupService.getAllStudenci();//pobieramy wszystkie
			calculatePages();
		}
		else
			logger.info("Lookup service is NULL. Injection failed.");
	}

	public int getRemoveId() {
		return removeId;
	}

	public void setRemoveId(int removeId) {
		this.removeId = removeId;
		if (removeId != 0) {
			//usuwamy studenta
			FacesContext context = FacesContext.getCurrentInstance();
			if (this.rejestracjaLookupService != null) {
				if (rejestracjaLookupService.remove(removeId))
					context.addMessage(null, new FacesMessage("Usunięto studenta o id=" + removeId));				
				else
					context.addMessage(null, new FacesMessage("Błąd usuwania studenta id=" + removeId));									
				allStudentList = this.rejestracjaLookupService.getAllStudenci();//pobieramy wszystkie newsy jeszcze raz
				calculatePages();
			}
			else
				context.addMessage(null, new FacesMessage("Błąd usuwania studenta id=" + removeId));
			
		}
	}	
	
	private void calculatePages() {
		if (allStudentList != null) {
			recordsCount = allStudentList.size();
			allPages = (int) Math.ceil((double) ( (double)recordsCount / RejestracjaManager.resultPerPage));			
		}
		firstPage = 1;
		if (recordsCount == 0 || recordsCount <= RejestracjaManager.resultPerPage) {
			page = 1;
			nextPage = 1;
			lastPage = 1;
			previousPage = 1;
		}
		else {
			lastPage = allPages;
			if (page < lastPage)
				nextPage = page + 1;
			else
				nextPage = page;
			if (page > 1)
				previousPage = page - 1;
			else
				previousPage = page;
		}
		generateTableCaption();
		//wsadzamy do StudentList te co trzeba
		int start = RejestracjaManager.resultPerPage * (page-1);
		int end = start + RejestracjaManager.resultPerPage;
		if (end > recordsCount)
			end = (int) recordsCount;
		studentList = allStudentList.subList(start, end);
	}

	private void generateTableCaption() {
		String caption = "";
		String str = recordsCount + "";
		String lastDigitStr = str.substring(str.length()-1);
		int lastDigit = new Integer(lastDigitStr).intValue();

		if (recordsCount == 0) {
			caption = "Brak rekordów do wyświetlenia";			
		}		
		if (recordsCount == 1) {
			caption = "1 wynik (strona 1 z 1)";	
		}
		else if (recordsCount > 1 && (lastDigit == 2 || lastDigit == 3 || lastDigit == 3 || lastDigit == 4) ) {
			caption = recordsCount + " wyniki (strona " + page + " z " + allPages + ")";						
		}
		else {
			caption = recordsCount + " wyników (strona " + page + " z " + allPages + ")";									
		}
		this.tableListCaption = caption;
	}
	
	public String getTableListCaption() {
		return tableListCaption;
	}

	public void setTableListCaption(String tableListCaption) {
		this.tableListCaption = tableListCaption;
	}
	
	public List<StudenciDAO> getstudentList() {
		if (studentList != null)
			logger.info("Students list size: " + studentList.size());
		else
			logger.info("Students list is NULL");			
		return studentList;
	}
	


	public void setstudentList(List<StudenciDAO> studentList) {
		this.studentList = studentList;
	}

	public int getPage() {
		return page;
	}
	
	public int getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}

	public void setPage(int page) {
		this.page = page;
		calculatePages();
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getAllPages() {
		return allPages;
	}

	public void setAllPages(int allPages) {
		this.allPages = allPages;
	}
	
	public long getRecordsCount() {
		return recordsCount;
	}

	public void setRecordsCount(long recordsCount) {
		this.recordsCount = recordsCount;
	}	
	
}
