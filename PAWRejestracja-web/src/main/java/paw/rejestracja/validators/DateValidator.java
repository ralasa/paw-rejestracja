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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;


@FacesValidator("DateValidator")

public class DateValidator implements Validator {

    private Pattern pattern;
    private Matcher matcher;
    //private static int[] WEIGHTS = new int[]{1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
    //private static int[] weights = new int[]{6, 5, 7, 2, 3, 4, 5, 6, 7};
    private static final String DATE_PATTERN ="^((19|20)[0-9][0-9])-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])";

    @Override

    public void validate(FacesContext context, UIComponent component, Object value) /*throws ValidatorException*/ {

        try {
            if (!isValidDate(value.toString())) {

                FacesMessage msg = new FacesMessage("Data jest nieprawidłowa", "Data jest nieprawidłowa");

                msg.setSeverity(FacesMessage.SEVERITY_ERROR);

                throw new ValidatorException(msg);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public DateValidator() {
        pattern = Pattern.compile(DATE_PATTERN);
    }

    private boolean isValidDate(String dataurodzenia) throws ParseException {

        matcher = pattern.matcher(dataurodzenia);


        if(matcher.matches()){

            matcher.reset();

            if(matcher.find()){

                try {
                    LocalDate dzisiejszaData = LocalDate.now();
                    LocalDate dataUrodzenia = LocalDate.parse(dataurodzenia);
                    boolean jestPozniej = dataUrodzenia.isAfter(dzisiejszaData);

                    if (jestPozniej) {
                        return false;
                    }

                    /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.S");
                    Date dataUrodzenia = sdf.parse(dataurodzenia+" 00:00:00.1");
                    Date currentDate = new Date();
                    boolean isAfter = currentDate.after(dataUrodzenia);
                    if(!isAfter) {return false;}*/
                } catch (Exception e) {
                    return false;
                }
                String day = matcher.group(4);
                String month = matcher.group(3);
                int year = Integer.parseInt(matcher.group(1));

                if (day.equals("31") &&
                        (month.equals("4") || month .equals("6") || month.equals("9") ||
                                month.equals("11") || month.equals("04") || month .equals("06") ||
                                month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if(((year % 4==0)&&(year % 100!=0))||(year % 400==0)){
                        if(day.equals("30") || day.equals("31")){
                            return false;
                        }else{
                            return true;
                        }
                    }else{
                        if(day.equals("29")||day.equals("30")||day.equals("31")){
                            return false;
                        }else{
                            return true;
                        }
                    }
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
