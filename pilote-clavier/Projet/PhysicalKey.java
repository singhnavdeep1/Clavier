public class PhysicalKey extends Key {
    
    private int keyCode;

    public PhysicalKey(int Keycode) {
        this.keyCode = Keycode;
    }

    public int getKeyCode() {
        return keyCode;
    }
    public void setKeyCode(int keyCode){
        this.keyCode=keyCode;
    }
}