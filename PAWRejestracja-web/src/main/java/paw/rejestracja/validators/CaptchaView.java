package paw.rejestracja.validators;

/**
 * Created by DELLikt on 04/06/2017.
 */

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class CaptchaView {

    public void submit() {
        //FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Weryfikacja reCaptcha powiodla sie", "Swietnie, swietnie :)");
        //FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
