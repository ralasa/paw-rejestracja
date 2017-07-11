package paw.rejestracja.beans;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import paw.studenci.jpa.StudenciDAO;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

/**
 * Ta klasa jest tylko po to żeby bean do dodawania miał inny okres życia niz do edycji.
 *
 */

// W tym przypadku dochodzi metoda do widoku "confirmation.xhtml"


@ManagedBean
@RequestScoped
public class RejestracjaManagedBean2 extends RejestracjaManagedBean {

    protected String viewParam;

    public String getViewParam() {
        return viewParam;
    }

    public void setViewParam(String viewParam) {
        this.viewParam = viewParam;
    }

    public void updatePageId(String pageId) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        this.studenci.setPageId(pageId);

        if (this.rejestracjaLookupService.merge(this.studenci)) {
            context.addMessage(null, new FacesMessage("Edycja studenta przebiegła poprawnie."));
        } else {
            context.addMessage(null, new FacesMessage("Błąd podczas edycji."));
        }
        return;
    }

    @Override

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
            if (UsersManagedBean.getSession().getAttribute("username")!=null) {
                return "details.xhtml?faces-redirect=true&id=" + this.studenci.getId();
            }
            else {
                this.studenci.setPageId(generatePageId(this.studenci.getId()));
                this.rejestracjaLookupService.merge(this.studenci);
                return "/public/confirmation.xhtml?faces-redirect=true&page_id=" + this.studenci.getPageId();
            }

        } else {
            context.addMessage(null, new FacesMessage("Błąd podczas dodania studenta."));
            return null;
        }

        //return "add.xhtml"; //"edit.xhtml?id=" + news.getId();
    }


    @Override

    public void createPDF() {
        try {
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
            Font h = new Font(helvetica,14);

            String relativeWebPath = "/resources/img/agh4pl-u.png";
            String absoluteDiskPath = context.getExternalContext().getRealPath(relativeWebPath);
            Image img = Image.getInstance(absoluteDiskPath);
            img.scalePercent(53);
            document.add(img);


            PdfPTable tabela = new PdfPTable(2);
            tabela.setSpacingBefore(20);

            StudenciDAO student = zarejestrowanyStudent(viewParam);

            dodajWiersz(tabela,"Imię", student.getImie());
            dodajWiersz(tabela,"Nazwisko", student.getNazwisko());
            dodajWiersz(tabela,"Obywatelstwo", student.getObywatelstwo());
            dodajWiersz(tabela, "Data urodzenia", student.getDataurodzenia());
            dodajWiersz(tabela, "PESEL", student.getPesel());
            dodajWiersz(tabela, "NIP", student.getNip());
            dodajWiersz(tabela, "Nr telefonu", student.getTelefon());
            dodajWiersz(tabela, "Adres e-mail", student.getEmail());
            dodajWiersz(tabela, "Adres zamieszkania", student.getAdreszamieszkania());
            dodajWiersz(tabela, "Adres do korespondencji", student.getAdreskorespondencyjny());
            dodajWiersz(tabela, "Wykształcenie", student.getWyksztalcenie());
            dodajWiersz(tabela, "Zatrudnienie", student.getZatrudnienie());
            dodajWiersz(tabela, "W razie wypadku powiadomić", student.getUprawnienia());
            dodajWiersz(tabela, "Rodzina", student.getRodzina());



            document.add(new Paragraph(" ", h));
            Paragraph p = new Paragraph("Potwierdzenie rejestracji na studia podyplomowe", h);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            document.add(tabela);


            //Keep modifying your pdf file (add pages and more)

            document.close();
            String fileName = "PotwierdzeniePAW-"+student.getImie()+"_"+student.getNazwisko();

            writePDFToResponse(context.getExternalContext(), baos, fileName);

            context.responseComplete();

        } catch (Exception e) {
            //e.printStackTrace();
        }
    }


/*
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
    */

    public RejestracjaManagedBean2() {
        super();
        logger.info("RejestracjaManagedBean2 created...");
    }

}
