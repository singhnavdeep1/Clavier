import java.util.Map;
import java.util.*;

public class Layers implements Module {
    InterfaceClavier view;
    private List<Map<PhysicalKey, KeyLayers>> keyMap;
    private static int currentLayer = 0;

    public Layers(InterfaceClavier view) {
        this.view=view;
        keyMap = view.getDonne().getLayers();
    }    

    // Cherche la touche suivante dans les layers en partant du layer courant
    // Si elle est trouvée, la touche de l'événement est remplacée et l'événement est envoyé au module suivant
    @Override
    public void accept(Event e) {
        for (int i = currentLayer; i >= 0; --i) {
            int nextKey = searchInLayer(i, e.getKeyCode());
            if (nextKey != -1) {
                e.setKeyCode(nextKey);
                if (getNextModule()!= null) {
                    getNextModule().accept(e);
                }
                return;
            }
        }
    }

    // Cherche la touche correspondante dans un layer donné
    public int searchInLayer(int numLayer, int key) {
        int nextKey = -1;
        for (Map.Entry<PhysicalKey, KeyLayers> entry : keyMap.get(numLayer).entrySet()) {
            if (entry.getKey().getKeyCode() == key) {
                nextKey = entry.getValue().getKeyCode();
            }
        }
        return nextKey;
    }

    public Module getNextModule(){
        return  view.list_mod.get(2);
    }

    public static int getCurrentLayer(){
        return currentLayer;
    }

    public static void setCurrentLayer(int n) {
        currentLayer = n;
    }

    public List<Map<PhysicalKey, KeyLayers>> getKeyMap() {
        return keyMap;
    }
}
