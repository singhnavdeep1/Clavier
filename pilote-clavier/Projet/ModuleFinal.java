import java.awt.*;

public class ModuleFinal implements Module {

    InterfaceClavier view;

    public ModuleFinal(InterfaceClavier view) {
        this.view=view;
    }

    public void accept(Event e) {
        try {
            // Exécute l'action correspondante à l'événement reçu
            view.executeAction(e);
        } catch (AWTException err) {
            System.out.println("Echec évent");
        }

    }

    // Renvoie null car c'est le dernier module de la chaîne de responsabilité
    public Module getNextModule(){
        return null;
    }
}
