package paw.rejestracja.validators;

/**
 * Created by DELLikt on 13/02/2017.
 */

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.logging.Logger;


@FacesValidator("PeselValidator")

public class PeselValidator implements Validator {


    private static final int[] WEIGHTS = new int[]{1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

    protected Logger logger = Logger.getLogger("PawRejestracja");

    @Override

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        UIInput date = (UIInput) component.getAttributes().get("date");
        UIInput pesel = (UIInput) component.getAttributes().get("numerPesel");
        Object dataUrodzenia = date.getValue();
        Object numerPesel = pesel.getValue();

        logger.info("Atrybut dataUrodzenia: "+dataUrodzenia.toString());
        logger.info("Numer PESEL przekazany z formularza: "+numerPesel.toString());


        if (!isPesel(numerPesel.toString())) {

            FacesMessage msg = new FacesMessage("Niepoprawny numer PESEL");

            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(msg);

        }

        if(!datePeselCheck(numerPesel.toString(),dataUrodzenia.toString())) {

            FacesMessage msg = new FacesMessage("Numer PESEL jest niezgodny z datą urodzenia");

            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(msg);
        }

    }


    private boolean isPesel(String pesel) {

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

    }

    private boolean datePeselCheck (String pesel, String date){
        logger.info("String PESEL przekazany do metody datePeselCheck: "+pesel);
        logger.info("Integer.valueOf(pesel.substring(0,1))"+Integer.valueOf(pesel.substring(0,1)));
        logger.info("Integer.valueOf(pesel.substring(1,2))"+Integer.valueOf(pesel.substring(1,2)));
        Integer birthYear = 10 * (Integer.valueOf(pesel.substring(0,1)));
        logger.info("birthYear = "+birthYear);

        birthYear += Integer.valueOf(pesel.substring(1,2));
        logger.info("birthYear = "+birthYear);
        Integer birthMonth = 10 * Integer.valueOf(pesel.substring(2,3));
        logger.info("birthMonth = "+birthMonth);
        birthMonth += Integer.valueOf(pesel.substring(3,4));
        logger.info("birthMonth = "+birthMonth);

        if (birthMonth >=1 && birthMonth <=12) birthYear += 1900;
        if (birthMonth >=21 && birthMonth <=32) {
            birthYear += 2000;
            birthMonth-=20;
        }

        String birthYearString = birthYear.toString();
        logger.info("birthYearString = "+birthYearString);
        String birthMonthString = birthMonth.toString();
        if(birthMonthString.length()==1) birthMonthString="0"+birthMonthString;
        logger.info("birthMonthString = "+birthMonthString);

        String dataUrodzeniaZPesel = birthYearString+"-"+birthMonthString+"-"+pesel.substring(4,5)+pesel.substring(5,6);
        logger.info("data urodzenia z pesel: "+dataUrodzeniaZPesel);
        boolean testPeselData = (date.equals(dataUrodzeniaZPesel));
        logger.info("testPeselData dla data urodzenia: *"+date+"* i daty wyliczonej z Pesel: *"+dataUrodzeniaZPesel+"* dal wynik: "+ testPeselData);



        if (date.equals(dataUrodzeniaZPesel)) {logger.info("Pesel zgodny z data"); return true;}
        else {logger.info("Pesel niezgodny z data"); return false;}


    }

}





