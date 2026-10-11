package behavioural_observer;

public interface IObserver {
    void update(String subjectName, String event);
    String getName();
}
