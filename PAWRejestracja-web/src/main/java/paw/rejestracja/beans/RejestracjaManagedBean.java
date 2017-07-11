package paw.rejestracja.beans;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import paw.rejestracja.ejb.RejestracjaLookupService;
import paw.studenci.jpa.StudenciDAO;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.logging.Logger;


@ManagedBean
@SessionScoped
public class RejestracjaManagedBean implements Serializable {


    private static final long serialVersionUID = -8257965639659172309L;
    protected Logger logger = Logger.getLogger("PawRejestracja");

    protected StudenciDAO studenci;
    protected int id;
    protected String imie;
    protected String nazwisko;
    protected String obywatelstwo;
    protected String dataurodzenia;
    protected String pesel;
    protected String nip;
    protected String adreszamieszkania;
    protected String adreskorespondencyjny;
    protected String telefon;
    protected String wyksztalcenie;
    protected String zatrudnienie;
    protected String uprawnienia;
    protected String rodzina;
    protected String komentarz;
    protected String email;
    protected String pageId;

    @ManagedProperty(value = "#{rejestracjaLookupDatabaseBean}")
    protected RejestracjaLookupService rejestracjaLookupService;

    public RejestracjaManagedBean() {
        super();
        logger.info("RejestracjaManagedBean created...");
        studenci = new StudenciDAO();
    }

    public RejestracjaLookupService getRejestracjaLookupService() {
        return rejestracjaLookupService;
    }

    public void setRejestracjaLookupService(RejestracjaLookupService rejestracjaLookupService) {
        this.rejestracjaLookupService = rejestracjaLookupService;
    }

    public StudenciDAO getStudenci() {
        return studenci;
    }

    public StudenciDAO zarejestrowanyStudent(String pageId) {
        //logger.info("zarejestrowanyStudent method invoked with pageId = " + pageId + "and returned " + this.rejestracjaLookupService.getStudenci(pageId));
        return this.rejestracjaLookupService.getStudenci(pageId);
    }

    public void setStudenci(StudenciDAO studenci) {
        this.studenci = studenci;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        if (id == 0) {
            this.studenci = null;
            this.imie = "";
            this.nazwisko = "";
            this.obywatelstwo = "";
            this.dataurodzenia = "";
            this.pesel = "";
            this.nip = "";
            this.telefon = "";
            this.email = "";

        }
        if (this.rejestracjaLookupService != null && id != 0) {
            this.studenci = this.rejestracjaLookupService.getStudenci(id);
            if (this.studenci != null) {
                this.imie = this.studenci.getImie();
                this.nazwisko = this.studenci.getNazwisko();
                this.obywatelstwo = this.studenci.getObywatelstwo();
                this.dataurodzenia = this.studenci.getDataurodzenia();
                this.pesel = this.studenci.getPesel();
                this.nip = this.studenci.getNip();
                this.adreszamieszkania = this.studenci.getAdreszamieszkania();
                this.adreskorespondencyjny = this.studenci.getAdreskorespondencyjny();
                this.telefon = this.studenci.getTelefon();
                this.email = this.studenci.getEmail();
                this.wyksztalcenie = this.studenci.getWyksztalcenie();
                this.zatrudnienie = this.studenci.getZatrudnienie();
                this.uprawnienia = this.studenci.getUprawnienia();
                this.rodzina = this.studenci.getRodzina();
                this.komentarz = this.studenci.getKomentarz();
                this.pageId = this.studenci.getPageId();

            }
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
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

    public String getDataurodzenia() {
        return dataurodzenia;
    }

    public void setDataurodzenia(String dataurodzenia) {
        this.dataurodzenia = dataurodzenia;
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

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getObywatelstwo() {
        return obywatelstwo;
    }

    public void setObywatelstwo(String obywatelstwo) {
        this.obywatelstwo = obywatelstwo;
    }

    public String getKomentarz() {
        return komentarz;
    }

    public void setKomentarz(String komentarz) {
        this.komentarz = komentarz;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String update() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        this.studenci.setUpdatedAt(new Date());
        this.studenci.setImie(imie);
        this.studenci.setNazwisko(nazwisko);
        this.studenci.setObywatelstwo(obywatelstwo);
        this.studenci.setDataurodzenia(dataurodzenia);

        this.studenci.setPesel(pesel);
        this.studenci.setNip(nip);
        this.studenci.setAdreszamieszkania(adreszamieszkania);
        this.studenci.setAdreskorespondencyjny(adreskorespondencyjny);
        this.studenci.setTelefon(telefon);
        this.studenci.setEmail(getEmail());
        this.studenci.setWyksztalcenie(wyksztalcenie);
        this.studenci.setZatrudnienie(zatrudnienie);
        this.studenci.setUprawnienia(uprawnienia);
        this.studenci.setRodzina(rodzina);
        this.studenci.setKomentarz(komentarz);
        this.studenci.setPageId(pageId);

        if (this.rejestracjaLookupService.merge(this.studenci)) {
            context.addMessage(null, new FacesMessage("Edycja studenta przebiegła poprawnie."));
        } else {
            context.addMessage(null, new FacesMessage("Błąd podczas edycji."));
        }
        return "details.xhtml?id=" + studenci.getId() + "&faces-redirect=true";
    }

    public String powrotDoStronyGlownej() {
        return "/public/add.xhtml?faces-redirect=true";
    }

    public String powrotDoEdycjiStudenta() {
        return "edit.xhtml?id=" + studenci.getId() + "&faces-redirect=true";
    }

    public String add() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        FacesContext context = FacesContext.getCurrentInstance();
        this.studenci = new StudenciDAO();
        this.studenci.setUpdatedAt(new Date());
        this.studenci.setCreatedAt(new Date());
        this.studenci.setImie(imie);
        this.studenci.setNazwisko(nazwisko);
        this.studenci.setObywatelstwo(obywatelstwo);
        this.studenci.setDataurodzenia(dataurodzenia);

        this.studenci.setPesel(pesel);
        this.studenci.setNip(nip);
        this.studenci.setAdreszamieszkania(adreszamieszkania);
        this.studenci.setAdreskorespondencyjny(adreskorespondencyjny);
        this.studenci.setTelefon(telefon);
        this.studenci.setEmail(getEmail());
        this.studenci.setWyksztalcenie(wyksztalcenie);
        this.studenci.setZatrudnienie(zatrudnienie);
        this.studenci.setUprawnienia(uprawnienia);
        this.studenci.setRodzina(rodzina);
        this.studenci.setKomentarz(komentarz);
        //logger.info("metoda add przyjela parametr id = " + this.studenci.getId());


        if (this.rejestracjaLookupService.persist(this.studenci)) {

            context.addMessage(null, new FacesMessage("Dodano nowego studenta."));
            //context.getExternalContext().dispatch("/confirmation.xhtml");
            //return "/public/confirmation.xhtml?id="+this.studenci.getId()+"&faces-redirect=true";
            //this.studenci.setPageId(generatePageId(this.studenci.getId()));
            //this.rejestracjaLookupService.merge(this.studenci);
            return "details.xhtml?faces-redirect=true&id=" + this.studenci.getId();

        } else {
            context.addMessage(null, new FacesMessage("Błąd podczas dodania studenta."));
            return null;
        }

        //return "add.xhtml"; //"edit.xhtml?id=" + news.getId();
    }


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

    /* private List<StudenciDAO> tutorials = new ArrayList<StudenciDAO>();

    public List<StudenciDAO> getTutorials() {
        return tutorials;
    }

    public void setTutorials(List<StudenciDAO> tutorials) {
        this.tutorials = tutorials;
    }

    public String register() {
        this.tutorials.add(studenci);
        this.studenci = new StudenciDAO();
        return "";
    } */

    public void createPDF() {
        try { //catch better your exceptions, this is just an example
            FacesContext context = FacesContext.getCurrentInstance();

            Document document = new Document();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            PdfWriter.getInstance(document, baos);


            if (!document.isOpen()) {
                document.open();
                logger.info("createPDF.document.open()");
            }
            document.addLanguage("PL-pl");
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font h = new Font(helvetica, 14);
            String relativeWebPath = "/resources/img/agh4pl-u.png";
            String absoluteDiskPath = context.getExternalContext().getRealPath(relativeWebPath);
            Image img = Image.getInstance(absoluteDiskPath);

            img.scalePercent(53);

            document.add(img);


            PdfPTable tabela = new PdfPTable(2);
            tabela.setSpacingBefore(20);
            tabela.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);




            dodajWiersz(tabela, "Imię", imie);
            dodajWiersz(tabela, "Nazwisko", nazwisko);
            dodajWiersz(tabela, "Obywatelstwo", obywatelstwo);
            dodajWiersz(tabela, "Data urodzenia", dataurodzenia);
            dodajWiersz(tabela, "PESEL", pesel);
            dodajWiersz(tabela, "NIP", nip);
            dodajWiersz(tabela, "Nr telefonu", telefon);
            dodajWiersz(tabela, "Adres e-mail", email);
            dodajWiersz(tabela, "Adres zamieszkania", adreszamieszkania);
            dodajWiersz(tabela, "Adres do korespondencji", adreskorespondencyjny);
            dodajWiersz(tabela, "Wykształcenie", wyksztalcenie);
            dodajWiersz(tabela, "Zatrudnienie", zatrudnienie);
            dodajWiersz(tabela, "W razie wypadku powiadomić", uprawnienia);
            dodajWiersz(tabela, "Rodzina", rodzina);

            document.add(new Paragraph(" ", h));
            Paragraph p = new Paragraph("Potwierdzenie rejestracji na studia podyplomowe", h);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            document.add(tabela);


            //Keep modifying your pdf file (add pages and more)

            document.close();
            String fileName = "PotwierdzeniePAW-"+imie+"_"+nazwisko;

            writePDFToResponse(context.getExternalContext(), baos, fileName);

            context.responseComplete();

        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    protected void dodajWiersz(PdfPTable tabela, String nazwaPola, String wartosc) throws IOException, DocumentException {
        if (wartosc.isEmpty()) wartosc = "-";
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font h = new Font(helvetica, 12);
        PdfPCell cell = new PdfPCell();
        cell.setMinimumHeight(28);
        //cell.setRowspan(1);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.addElement(new Paragraph(nazwaPola, h));
        tabela.addCell(cell);
        cell = new PdfPCell();
        cell.setMinimumHeight(28);
        //cell.setRowspan(1);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.addElement(new Paragraph(wartosc, h));
        tabela.addCell(cell);

    }

    protected void writePDFToResponse(ExternalContext externalContext, ByteArrayOutputStream baos, String fileName) {
        try {
            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Expires", "0");
            externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            externalContext.setResponseHeader("Pragma", "public");
            externalContext.setResponseHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf");
            externalContext.setResponseContentLength(baos.size());
            OutputStream out = externalContext.getResponseOutputStream();
            baos.writeTo(out);
            externalContext.responseFlushBuffer();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    protected String generatePageId(Integer id) throws NoSuchAlgorithmException, InvalidKeySpecException {
        logger.info("Zainicjalizowano metode generatePageId z id = " + id);
        String saltString = "79ee0d566dfb19651e056cf73e1ccfb3";
        int iterations = 1000;
        //Integer iId = id;
        char[] chars = id.toString().toCharArray();
        byte[] salt = DatatypeConverter.parseHexBinary(saltString);

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        logger.info("Metoda generatePageId zwrocila wynik: " + DatatypeConverter.printHexBinary(hash));
        return DatatypeConverter.printHexBinary(hash);

    }


}
