package paw.rejestracja.validators;

/**
 * Created by DELLikt on 13/02/2017.
 */

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("NipValidator")

public class NipValidator implements Validator {


    //private static int[] WEIGHTS = new int[]{1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
    private static int[] weights = new int[]{ 6, 5, 7, 2, 3, 4, 5, 6, 7 };

    @Override

    public void validate(FacesContext context, UIComponent component, Object value) /*throws ValidatorException*/ {

        if (!isValidNIP(value.toString())) {

            FacesMessage msg = new FacesMessage("Weryfikacja poprawności numeru NIP nie powiodła się", "Niepoprawny numer NIP");

            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(msg);

        }

    }

    private static boolean isValidNIP(String nip) {
        int nsize = nip.length();
        if (nsize != 10) {
            return false;
        }
        //int[] weights = { 6, 5, 7, 2, 3, 4, 5, 6, 7 };
        int j = 0, sum = 0, control = 0;
        int csum = Integer.valueOf(nip.substring(nsize - 1));
        for (int i = 0; i < nsize - 1; i++) {
            char c = nip.charAt(i);
            j = Integer.valueOf(String.valueOf(c));
            sum += j * weights[i];
        }
        control = sum % 11;
        return (control == csum);

    }


    /*private boolean isPesel(String pesel) {

        if (pesel.length() != 11) {

            return false;

        }

        int sum = 0;

        int currentCheck = 0;

        int check = new Integer(pesel.substring(pesel.length() - 1));

        for (int i = 0; i < pesel.length() - 1; i++) {

            sum += new Integer(String.valueOf(pesel.charAt(i))) * WEIGHTS[i];

        }

        currentCheck = 10 - (sum % 10);

        if (currentCheck > 9) {

            currentCheck = 0;

        }

        return (currentCheck == check);

    }*/


}





