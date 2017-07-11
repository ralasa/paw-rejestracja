package paw.studenci.jpa;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the studenci database table.
 * 
 */
@Entity
@Table(name = "studenci", schema = "rafal")

@NamedQuery(name="Studenci.findAll", query="SELECT s FROM StudenciDAO s")

public class StudenciDAO implements Serializable {
	private static final long serialVersionUID = -400871998443587020L;

		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable=false)
	protected int id;

	
	@Column(name="created_at")
	protected Date createdAt;

	@Lob
	protected String imie;

	@Lob
	protected String nazwisko;

	
	@Column(name="updated_at")
	protected Date updatedAt;
	
	@Lob
	protected String obywatelstwo;
	
	protected String dataurodzenia;
	
	@Lob
	protected String pesel; 
	
	@Lob
	protected String nip ;
	
	@Lob
	protected String adreszamieszkania ;
	@Lob
	protected String adreskorespondencyjny;
	@Lob
	protected String telefon;
    @Lob
	protected String email;
	@Lob
	protected String wyksztalcenie ;
	@Lob
	protected String zatrudnienie ;
	@Lob
	protected String uprawnienia ;
	@Lob
	protected String rodzina;
	@Lob
	protected String komentarz;

	@Column(name="page_id")
	protected String pageId;
	
	public StudenciDAO() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}




	public StudenciDAO(int id, String imie, String nazwisko, String dataurodzenia, String email,
					   String obywatelstwo, String pesel, String nip, String adreszamieszkania, String adreskorespondencyjny, String telefon, String wyksztalcenie, String zatrudnienie
			, String uprawnienia, String rodzina, String komentarz, String pageId) {
		super();
		this.id = id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.obywatelstwo= obywatelstwo;
        this.dataurodzenia= dataurodzenia;
		this.pesel=pesel;
		this.nip=nip;
		this.adreszamieszkania=adreszamieszkania;
		this.adreskorespondencyjny=adreskorespondencyjny;
		this.telefon=telefon;
        this.email=email;
		this.wyksztalcenie=wyksztalcenie;
		this.zatrudnienie=zatrudnienie;
		this.uprawnienia=uprawnienia;
		this.rodzina=rodzina;
		this.komentarz=komentarz;
		this.pageId=pageId;
		
		
		createdAt = new Date();
		updatedAt = new Date();
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, length = 29)
	@XmlTransient
	public java.util.Date getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getImie() {
		return this.imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return this.nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}



	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false, length = 29)
	@XmlTransient
	public Date getUpdatedAt() {
		return updatedAt;
	}
	@Transient
	@XmlElement(name = "createdAt")
	protected long getCreatedAtTimestamp() {
		return createdAt.getTime();
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Transient
	protected void setCreatedAtTimestamp(long createdAtTimestamp) {
		createdAt.setTime(createdAtTimestamp);
	}
	
	@Transient
	@XmlTransient
	protected long getUpdatedAtTimestamp() {
		return updatedAt.getTime();
	}
		
	@Transient
	protected void setUpdatedAtTimestamp(long updatedAtTimestamp) {
		updatedAt.setTime(updatedAtTimestamp);
	}
		
	public void setUpdatedAt(java.util.Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	
	@XmlTransient
	@Transient	
	public String getCreatedAtAbbr() {
		String ret = "" + createdAt;
		return ret.substring(0, 16);
	}
	
	@XmlTransient
	@Transient	
	public String getUpdatedAtAbbr() {
		String ret = "" + updatedAt;
		return ret.substring(0, 16);
	}

	@Override
	public String toString() {
		return "StudenciDAO [id=" + id + ", createdAt=" + createdAt + ", imie=" + imie
				+ ", nazwisko=" + nazwisko + ", updatedAt=" + updatedAt + ", obywatelstwo=" + obywatelstwo
				+ ", dataurodzenia=" + dataurodzenia + ", pesel=" + pesel + ", nip=" + nip + ", adreszamieszkania=" + adreszamieszkania
				+ ", adreskorespondencyjny=" + adreskorespondencyjny + ", telefon=" + telefon + ", email=" + getEmail() + " wyksztalcenie=" + wyksztalcenie
				+ ", zatrudnienie=" + zatrudnienie + ", uprawnienia=" + uprawnienia + ", rodzina=" + rodzina +", komentarz=" + komentarz + ", pageId"+ pageId+ "]";
	}

	public String getObywatelstwo() {
		return obywatelstwo;
	}

	public void setObywatelstwo(String obywatelstwo) {
		this.obywatelstwo = obywatelstwo;
	}

	public String getDataurodzenia() {
		return dataurodzenia;
	}

	public void setDataurodzenia(String dataurodzenia) {
		this.dataurodzenia = dataurodzenia;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getAdreszamieszkania() {
		return adreszamieszkania;
	}

	public void setAdreszamieszkania(String adreszamieszkania) {
		this.adreszamieszkania = adreszamieszkania;
	}

	public String getAdreskorespondencyjny() {
		return adreskorespondencyjny;
	}

	public void setAdreskorespondencyjny(String adreskorespondencyjny) {
		this.adreskorespondencyjny = adreskorespondencyjny;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getWyksztalcenie() {
		return wyksztalcenie;
	}

	public void setWyksztalcenie(String wyksztalcenie) {
		this.wyksztalcenie = wyksztalcenie;
	}

	public String getZatrudnienie() {
		return zatrudnienie;
	}

	public void setZatrudnienie(String zatrudnienie) {
		this.zatrudnienie = zatrudnienie;
	}

	public String getUprawnienia() {
		return uprawnienia;
	}

	public void setUprawnienia(String uprawnienia) {
		this.uprawnienia = uprawnienia;
	}

	public String getRodzina() {
		return rodzina;
	}

	public void setRodzina(String rodzina) {
		this.rodzina = rodzina;
	}

	public String getKomentarz() {return komentarz; }

	public void setKomentarz(String komentarz) {this.komentarz = komentarz;}

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }




	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
}