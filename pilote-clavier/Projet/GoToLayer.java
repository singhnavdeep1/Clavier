import java.util.*;

public class GoToLayer implements Module {

    InterfaceClavier view;
    Module  nextModule ;
    Map<PhysicalKey, Integer> switchLayers = new HashMap<>();

    //Constructeur de la classe GoToLayer
    public GoToLayer(InterfaceClavier view) {
        this.view=view;
        switchLayers = view.getDonne().getSwitchLayers();
    }

    //Renvoie le prochain module à exécuter
    public Module getNextModule(){
        return view.list_mod.get(1);
    }

    /* Fonction appelée lorsque l'utilisateur appuie sur une touche
     * Si la touche correspond à une couche, on passe à cette couche
     * Sinon, on appelle le module suivan */
    public void accept(Event e) {
        Iterator it = switchLayers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<PhysicalKey, Integer> entry = (Map.Entry) it.next();
            if (e.getKeyCode() == entry.getKey().getKeyCode()) {
                Layers.setCurrentLayer(entry.getValue());
                return;
            }
        }
        if (getNextModule() != null){
            getNextModule().accept(e);
        }
    } 

}
