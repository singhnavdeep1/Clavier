import java.util.*;
import java.util.LinkedList;
import java.util.List;

public class Combo implements Module{
    // Déclaration des variables d'instance
    InterfaceClavier view;
    int currentLayer;
    List<List<PhysicalKey>> ComboPossible;
    LinkedList<Event> comboReleased=new LinkedList<Event>();
    List<Map<PhysicalKey,KeyLayers>> ResultCombo1;
    List<Map<PhysicalKey,KeyLayers>> ResultCombo2;
    List<Map<PhysicalKey,KeyLayers>> ResultCombo3;

    // Constructeur de la classe Combo
    public Combo(InterfaceClavier view,int currentLayer){
        this.currentLayer=currentLayer;
        this.view=view;
        ComboPossible= view.getDonne().initCombo();
        ResultCombo1 = view.getDonne().initChangeTo("ResultCombo1");
        ResultCombo2=view.getDonne().initChangeTo("ResultCombo2");
        ResultCombo3=view.getDonne().initChangeTo("ResultCombo3");
    }

    // Méthode accept pour traiter les événements de touches
    public void accept(Event e){
        List<PhysicalKey> list =ComboPossible.get(currentLayer);
        int i=0;
        while(i<list.size()){
            PhysicalKey ht=list.get(i);
            if(e.isReleased()&&ht.getKeyCode()==e.getKeyCode() ){
                comboReleased.add(e);
                if(i==0||i%2==0){
                    toTakeAction(e,list.get(i),list.get(i+1),i);
                    return;
                }else {
                    toTakeAction(e,list.get(i),list.get(i-1),i-1);
                    return;
                }
            }else {
                if(!e.isReleased()&&ht.getKeyCode()==e.getKeyCode() ){
                    return;
                }
            }
            i++;
        }
        getNextModule().accept(e);
    }

    // Méthode pour effectuer une action en fonction de la combinaison de touches
    public void toTakeAction(Event e,PhysicalKey p1,PhysicalKey p2,int i){
        Event combo1=null;
        Event combo2=null;
        for(Event cr:comboReleased){
            if(cr.getKeyCode()==p1.getKeyCode()){
                combo1=cr;
            }
            if(cr.getKeyCode()==p2.getKeyCode()){
                combo2=cr;
            }
        }
        if(combo1==null||combo2==null){
            e.setReleased(false);
            getNextModule().accept(e);
            return;
        }else {
            if(combo1.getPressTime()-combo2.getPressTime()<300){
                Map<PhysicalKey,KeyLayers> map =ResultCombo1.get(currentLayer);
                if(i==2){
                    map =ResultCombo2.get(currentLayer);
                }
                if(i==4){
                    map =ResultCombo3.get(currentLayer);
                }
                Iterator it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<PhysicalKey, KeyLayers> entry = (Map.Entry) it.next();
                    KeyLayers keyLayers= entry.getValue();
                    int key = keyLayers.getKeyCode();
                    Key keyChanged=new PhysicalKey(key);
                    Event eventChanged = new Event(keyChanged,e.getKeyCode(),e.getPressTime(), false);
                    eventChanged.setEstChange(true);
                    getNextModule().accept(eventChanged);
                    return;

                }
            }else {
                e.setReleased(false);
                getNextModule().accept(e);
            }
        }
    }

    // Méthode pour obtenir le module suivant dans la chaîne de traitement
    public Module getNextModule(){
        currentLayer=Layers.getCurrentLayer();
        return view.list_mod.get(3);
    }
}