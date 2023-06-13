import java.util.*;
import java.util.LinkedList;
import java.util.List;

// La classe HoldTap implémente l'interface ConsumerModule pour traiter les événements de touches hold/tap.
public class HoldTap implements Module {

    InterfaceClavier view;
    List<List<PhysicalKey>> HoldPossible; // Liste des hold possibles
    LinkedList<Event> hold = new LinkedList<Event>(); // Liste des événements hold
    LinkedList<Event> eventReleased = new LinkedList<Event>();
     // Liste des événements released
    List<Map<PhysicalKey, KeyLayers>> changedTo1;
    List<Map<PhysicalKey, KeyLayers>> changedTo2;
    List<Map<PhysicalKey, KeyLayers>> changedTo3;
    
    int currentLayer;

    // Constructeur HoldTap
    public HoldTap(InterfaceClavier view, int currentLayer) {
        this.view = view;
        this.currentLayer = currentLayer;
        HoldPossible = view.getDonne().getHold();
        changedTo1 = view.getDonne().initChangeTo("ChangedTo1");
        changedTo2 = view.getDonne().initChangeTo("ChangedTo2");
        changedTo3 = view.getDonne().initChangeTo("ChangedTo3");
    }

    // La méthode accept traite l'événement reçu et décide si elle doit être traitée comme un hold, un tap ou passer à l'étape suivante.
    @Override
    public void accept(Event e) {
        List<PhysicalKey> list = HoldPossible.get(currentLayer);
        for (PhysicalKey ht : list) {
            if (ht.getKeyCode() == e.getKeyCode() && !e.isReleased()) {
                for (Event h : hold) {
                    if (h.getKeyCode() == e.getKeyCode()) {
                        return;
                    }
                }
                hold.add(e);
                return;

            }
        }
        if (e.isReleased()) {
            for (Event h : hold) {
                if (h.getKeyCode() == e.getKeyCode() && e.isReleased()) {
                    if (e.getPressTime() - h.getPressTime() < 10000) {
                        hold.remove(h);
                        for (Event r : eventReleased) {
                            if (h.getPressTime() < r.getPressTime() && e.getPressTime() > r.getPressTime()) {
                                return;
                            }
                        }
                        // On envoie directement au module suivant (ce n'est pas une touche avec un hold)
                        getNextModule().accept(e);
                        return;
                    }
                }
            }
            return;
        }
        toTakeAction(e);
    }

    // La méthode toTakeAction vérifie si une action doit être prise pour l'événement donné.
    public void toTakeAction(Event e){
        eventReleased.add(e);
        for(Event h:hold){    
            if(h.getPressTime()<=e.getPressTime()){
                        List<PhysicalKey> list =HoldPossible.get(currentLayer);
                        int i=1;
                        int nbr=1;
                        Map<PhysicalKey,KeyLayers> map =changedTo1.get(currentLayer);
                        for(PhysicalKey ht : list){
                            if(ht.getKeyCode()==h.getKeyCode()){
                                nbr=i;
                            }
                            i++;
                        }
                        if(nbr==2){
                            map =changedTo2.get(currentLayer);
                        }
                        if(nbr==3){
                            map =changedTo3.get(currentLayer);
                        }
                        Iterator it = map.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<PhysicalKey, KeyLayers> entry = (Map.Entry) it.next();
                            if (e.getKeyCode() == entry.getKey().getKeyCode()) {
                                KeyLayers keyLayers= entry.getValue();
                                int key = keyLayers.getKeyCode();
                                Key keyChanged=new PhysicalKey(key);
                                Event eventChanged = new Event(keyChanged,e.getKeyCode(),e.getPressTime(), false);
                                getNextModule().accept(eventChanged);
                                return;
                            }
                        
                        }
            }
        }
        for(Event h:hold){
            return;
        }
        //on envoie direct au module (ce n'est pas une touche avec un hold) 
        getNextModule().accept(e);
    } 

    public Module getFirstModule(){
        return view.list_mod.get(0);
            
    }


    @Override
    public Module getNextModule() {
        currentLayer=Layers.getCurrentLayer();
        return view.list_mod.get(4);    }
}
