public class Event {

    private final int originalKey;
    private Key key;
    private long pressTime;
    private boolean isReleased;
    private boolean estChange;

    Event(Key key,int originalKey,  long pressTime, boolean isReleased) {
        estChange=false;
        this.key = key;
        this.originalKey=originalKey;
        this.pressTime = pressTime;
        this.isReleased = isReleased;
    }

    public int getOriginalKey(){
        return this.originalKey;
    }

    public int getKeyCode() {
        return key.getKeyCode();
    }

    public long getPressTime() {
        return pressTime;
    }

    public boolean isReleased() {
        return isReleased;
    }
    public boolean estChange(){
        return estChange;
    }

    public void setKeyCode(int keyCode) {
        key.setKeyCode(keyCode);
    }
    public void setEstChange(boolean b){
        estChange=b;
    }
    public void setPressTime(long pressTime) {
        this.pressTime = pressTime;
    }

    public void setReleased (boolean isReleased) {
        this.isReleased = isReleased;
    }

}
