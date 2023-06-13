import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDate {
    
    // Renvoie la date courante sous la forme "dd/MM/yyyy"
    public static String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(currentDate);
    }
    
    // Renvoie l'heure courante sous la forme "HH:mm:ss"
    public static String getCurrentTime() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(currentDate);
    }
}
