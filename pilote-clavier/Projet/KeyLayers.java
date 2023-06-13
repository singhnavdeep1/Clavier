public class KeyLayers extends Key{
    
    private int keyCode;

    public KeyLayers(int keycode ){
        this.keyCode = keycode;
    }

    public int getKeyCode() {
        return keyCode;
    }
    public void setKeyCode(int keyCode){
        this.keyCode=keyCode;
    }
}
