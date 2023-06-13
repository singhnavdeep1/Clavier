public interface Module {

    public Module getNextModule();

    public void accept(Event e);
}
