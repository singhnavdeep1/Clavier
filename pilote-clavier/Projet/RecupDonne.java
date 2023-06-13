import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.*;
import java.io.FileReader;

public class RecupDonne {

    private HashMap<String, Integer> dictionnaire1;
    private HashMap<Integer, String> dictionnaire2;
    private List<Map<PhysicalKey, KeyLayers>> initLayers;
    private Map<PhysicalKey,Integer> initSwitchLayers;
    private List<List<PhysicalKey>> initHold;

    public RecupDonne(){
        this.dictionnaire1 = initDictionnaire1();
        this.dictionnaire2 = initDictionnaire2();
        this.initLayers = initLayers();
        this.initSwitchLayers = initSwitchLayers();
        this.initHold = initHold();
    }

    // Initialise le dictionnaire pour convertir les noms en codes
    public HashMap<String, Integer> initDictionnaire1() {
        String fileName = "Dictionnaire.json";
        File file = new File(fileName);
        String absolutePath = file.getAbsolutePath();
        JSONParser jsonP = new JSONParser();
        HashMap<String, Integer> dico = new HashMap<String, Integer>();
        try {
            JSONObject data = (JSONObject) jsonP.parse(new FileReader(absolutePath));
            JSONArray dictionnaire = (JSONArray) data.get("Dictionnaire");
            for (int i = 0; i < dictionnaire.size(); i++) {
                JSONObject entry = (JSONObject) dictionnaire.get(i);
                String nom = (String) entry.get("nom");
                int code = Integer.parseInt(entry.get("code").toString());
                dico.put(nom, code);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dico;
    }

    // Initialise le dictionnaire pour convertir les codes en sorties
    public HashMap<Integer, String> initDictionnaire2() {
        String fileName = "Dictionnaire.json";
        File file = new File(fileName);
        String absolutePath = file.getAbsolutePath();
        JSONParser jsonP = new JSONParser();
        HashMap<Integer, String> dico = new HashMap<Integer, String>();
        try {
            JSONObject data = (JSONObject) jsonP.parse(new FileReader(absolutePath));
            JSONArray dictionnaire = (JSONArray) data.get("Dictionnaire");
            for (int i = 0; i < dictionnaire.size(); i++) {
                JSONObject entry = (JSONObject) dictionnaire.get(i);
                int code  = Integer.parseInt(entry.get("code").toString());
                String caract = (String) entry.get("sortie");
                dico.put(code, caract);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dico;
    }
    
    // Initialise les couches de la configuration des touches
    public List<Map<PhysicalKey, KeyLayers>> initLayers() {
        String fileName = "keymap.json";
        File file = new File(fileName);
        String absolutePath = file.getAbsolutePath();
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject data = (JSONObject) jsonP.parse(new FileReader(absolutePath));
            List<Map<PhysicalKey, KeyLayers>> keyMapList = new ArrayList<>();
            JSONArray layers = (JSONArray) data.get("layers");
            for (int i = 0; i < layers.size(); i++) {
                JSONObject layer = (JSONObject) layers.get(i);
                Map<PhysicalKey, KeyLayers> map = new HashMap<>();
                JSONArray keys = (JSONArray) layer.get("keys");
                for (int j = 0; j < keys.size(); j++) {
                    JSONObject key = (JSONObject) keys.get(j);
                    String physicalKey = (String) key.get("physicalKey");
                    String keyCode = (String) key.get("Key");
                    if (dictionnaire1.get(physicalKey)!=null&&dictionnaire1.get(keyCode)!=null){
                        map.put(new PhysicalKey(dictionnaire1.get(physicalKey)), new KeyLayers(dictionnaire1.get(keyCode)));
                    }
                }
                keyMapList.add(map);
            }
            return keyMapList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Initialise le mappage pour changer de couche
    public Map<PhysicalKey,Integer> initSwitchLayers(){
        String filename = "keymap.json";
        File file = new File(filename);
        String absolutePath = file.getAbsolutePath();
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject data = (JSONObject) jsonP.parse(new FileReader(absolutePath));
            Map<PhysicalKey,Integer> map = new HashMap<>();
            JSONArray layers = (JSONArray) data.get("layers");
            for (int i = 0; i < layers.size(); i++) {
                JSONObject layer = (JSONObject) layers.get(i);
                JSONArray keys = (JSONArray) layer.get("keys");
                for (int j = 0; j < keys.size(); j++) {
                    JSONObject key = (JSONObject) keys.get(j);
                    String physicalKey = (String) key.get("physicalKey");
                    String val = (String) key.get("layers");
                    if (dictionnaire1.get(physicalKey)!=null&&val!=null){
                        map.put(new PhysicalKey(dictionnaire1.get(physicalKey)), Integer.valueOf(val));
                    }
                }
            }
            return map;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Initialise la liste des touches de maintien pour chaque couche
    public List<List<PhysicalKey>> initHold() {
        String fileName = "keymap.json";
        File file = new File(fileName);
        String absolutePath = file.getAbsolutePath();
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject data = (JSONObject) jsonP.parse(new FileReader(absolutePath));
            List<List<PhysicalKey>> layershold = new ArrayList<>();
            JSONArray layers = (JSONArray) data.get("layers");
            for (int i = 0; i < layers.size(); i++) {
                JSONObject layer = (JSONObject) layers.get(i);
                List<PhysicalKey> holdkey = new ArrayList<>();
                JSONArray keys = (JSONArray) layer.get("keys");
                for (int j = 0; j < keys.size(); j++) {
                    JSONObject key = (JSONObject) keys.get(j);
                    String hold = (String) key.get("Hold");
                    if (dictionnaire1.get(hold) != null) {
                        String physicalKey = (String) key.get("physicalKey");
                        holdkey.add(new PhysicalKey(dictionnaire1.get(physicalKey)));
                    }
                }
                layershold.add(holdkey);
            }
            return layershold;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Initialise la liste des combos  pour chaque couche
    public List<List<PhysicalKey>> initCombo() {
        String fileName = "keymap.json";
        File file = new File(fileName);
        String absolutePath = file.getAbsolutePath();
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject data = (JSONObject) jsonP.parse(new FileReader(absolutePath));
            List<List<PhysicalKey>> layershold = new ArrayList<>();
            JSONArray layers = (JSONArray) data.get("layers");
            for (int i = 0; i < layers.size(); i++) {
                JSONObject layer = (JSONObject) layers.get(i);
                List<PhysicalKey> holdkey = new ArrayList<>();
                JSONArray keys = (JSONArray) layer.get("keys");
                for (int j = 0; j < keys.size(); j++) {
                    JSONObject key = (JSONObject) keys.get(j);
                    String hold = (String) key.get("Combo");
                    if (dictionnaire1.get(hold) != null) {
                        String physicalKey = (String) key.get("physicalKey");
                        holdkey.add(new PhysicalKey(dictionnaire1.get(physicalKey)));
                    }
                }
                layershold.add(holdkey);
            }
            return layershold;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Initialise le mappage des touches modifiées pour les diéfferents action
    public List<Map<PhysicalKey, KeyLayers>> initChangeTo(String action) {
        String fileName = "keymap.json";
        File file = new File(fileName);
        String absolutePath = file.getAbsolutePath();
        JSONParser jsonP = new JSONParser();
        try {
            JSONObject data = (JSONObject) jsonP.parse(new FileReader(absolutePath));
            List<Map<PhysicalKey, KeyLayers>> keyMapList = new ArrayList<>();
            JSONArray layers = (JSONArray) data.get("layers");
            for (int i = 0; i < layers.size(); i++) {
                JSONObject layer = (JSONObject) layers.get(i);
                Map<PhysicalKey, KeyLayers> map = new HashMap<>();
                JSONArray keys = (JSONArray) layer.get("keys");
                for (int j = 0; j < keys.size(); j++) {
                    JSONObject key = (JSONObject) keys.get(j);
                    String physicalKey = (String) key.get("physicalKey");
                    String keyCode = (String) key.get(action);
                    if (dictionnaire1.get(physicalKey) != null && dictionnaire1.get(keyCode) != null) {
                        map.put(new PhysicalKey(dictionnaire1.get(physicalKey)), new KeyLayers(dictionnaire1.get(keyCode)));
                    }
                }
                keyMapList.add(map);
            }
            return keyMapList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<String, Integer> getDictionnaire1() {
        return dictionnaire1;
    }

    public HashMap<Integer, String> getDictionnaire2() {
        return dictionnaire2;
    }

    public List<Map<PhysicalKey, KeyLayers>> getLayers() {
        return initLayers;
    }

    public Map<PhysicalKey, Integer> getSwitchLayers() {
        return initSwitchLayers;
    }

    public List<List<PhysicalKey>> getHold() {
        return initHold;
    }
    
}
