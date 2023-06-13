import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InterfaceClavier extends JFrame implements KeyListener {

    private JTextArea textArea;
    private LinkedList<Event> events;
    private RecupDonne donne;

    private HashMap<Integer, String> dictionnaire2;
    public static LinkedList<Module> list_mod;


    public InterfaceClavier() {
        events = new LinkedList<Event>();
        list_mod = new LinkedList<Module>();
        setTitle("Écrivez ici :");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        Font font = new Font("Arial", Font.PLAIN, 24);

        textArea = new JTextArea();
        textArea.setFont(font);
        textArea.setMargin(new Insets(10, 10, 10, 10));

        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);

        mainPanel.add(textArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.BLACK);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        JButton closeButton = new JButton("x");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        buttonPanel.add(closeButton);

        this.add(mainPanel);
        textArea.addKeyListener(this);
        textArea.requestFocus();
        setVisible(true);

        this.donne = new RecupDonne();
        this.dictionnaire2 = donne.getDictionnaire2();
    }

    // Exécute une action en fonction de l'événement reçu
    public void executeAction(Event e) throws AWTException {
        String character = dictionnaire2.get(e.getKeyCode());
        if(e.estChange()){
            textArea.setText(textArea.getText().substring(0, textArea.getText().length() - 1));
        }
        if (character != null){
            if (character.equals("date")){
                updateTextArea(String.valueOf(CurrentDate.getCurrentDate()));
            }else if (character.equals("heure")){
                updateTextArea(String.valueOf(CurrentDate.getCurrentTime()));
            }else if (character.equals("supprimer")){
                textArea.setText("");
            }else{
            updateTextArea(String.valueOf(character));
            }
        } 
    }

    public void setList_mod(LinkedList<Module> l) {
        list_mod = l;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        e.consume();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Key key = new PhysicalKey(e.getKeyCode());
        Event event = new Event(key, e.getKeyCode(), System.currentTimeMillis(), false);
        events.add(event);
        list_mod.get(0).accept(event);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Key key = new PhysicalKey(e.getKeyCode());
        Event event = new Event(key, e.getKeyCode(), System.currentTimeMillis(), true);
        events.add(event);
        list_mod.get(0).accept(event);
    }

    public List<Event> getEvents() {
        return events;
    }

    public void updateTextArea(String text) {
        textArea.setText(textArea.getText() + text);
    }

    public RecupDonne getDonne(){
        return donne;
    }

    public static void main(String[] args) {
        InterfaceClavier view = new InterfaceClavier();
        LinkedList<Module> l = new LinkedList<>();
        l.add(new GoToLayer(view));
        l.add(new Layers(view));
        l.add(new Combo(view, Layers.getCurrentLayer()));
        l.add(new HoldTap(view, Layers.getCurrentLayer()));
        l.add(new ModuleFinal(view));
        view.setList_mod(l);
    }
}
