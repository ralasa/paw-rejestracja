package paw.rejestracja.beans;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.logging.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
//import org.primefaces.context.RequestContext;
//

import paw.rejestracja.ejb.RejestracjaLookupService;
import paw.studenci.jpa.UsersDAO;

@ManagedBean
@SessionScoped
public class UsersManagedBean implements Serializable {

    private static final long serialVersionUID = -8159404281668108211L;

    protected Logger logger = Logger.getLogger("PawRejestracja");

    protected UsersDAO users;
    protected int id;
    protected String username;
    protected String password;
    protected String sessionuser;
    private List<UsersDAO> userList;

    @ManagedProperty(value = "#{rejestracjaLookupDatabaseBean}")
    private RejestracjaLookupService rejestracjaLookupService;
//	private RejestracjaLookupDatabaseBean rejestracjaLookupDatabaseBean;

    public UsersManagedBean() {
        super();
        logger.info("UsersDAO bean created...");
        users = new UsersDAO();
    }

    public RejestracjaLookupService getRejestracjaLookupService() {
        return rejestracjaLookupService;
    }


    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsersDAO getUsers() {
        return users;
    }

    public String getSessionuser() {
        return sessionuser;
    }

    public void setSessionuser(String sessionuser) {
        this.sessionuser = sessionuser;
    }

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public String login() throws InvalidKeySpecException, NoSuchAlgorithmException {

        //haszowanie wprowadzonego hasla
        String originalPassword = password;
        String generatedSecuredPasswordHash = generateStrongPasswordHash(originalPassword);


        userList = this.rejestracjaLookupService.validate(username, generatedSecuredPasswordHash);
        FacesContext context = FacesContext.getCurrentInstance();

        if (userList != null) {
            HttpSession session = getSession();
            session.setAttribute("username", username);
            logger.info("Logowanie uzytkownika "+username+" powiodlo sie.");

            return "success";
        } else
            context.addMessage(null, new FacesMessage("Błąd logowania"));

        return "failure";
    }

    private static String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String saltString = "be367c62118c3006408a73a0906592cb";
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = DatatypeConverter.parseHexBinary(saltString);

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return DatatypeConverter.printHexBinary(hash);
    }

    public void logout() {

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = getSession();
        session.invalidate();

        try {
            ec.redirect("/PAWRejestracja/index.jsp");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //return "";

    }


    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
    }

    public void setRejestracjaLookupService(RejestracjaLookupService rejestracjaLookupService) {
        this.rejestracjaLookupService = rejestracjaLookupService;
    }

    public String getSeesionuser() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
    }


}
