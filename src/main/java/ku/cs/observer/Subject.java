package ku.cs.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private final List<Observer> observers;

    public Subject() {
        observers = new ArrayList<>();
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for (Observer o : observers)
            o.update();
    }
}
