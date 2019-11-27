package com.egorovsoft.myrain;

import java.util.ArrayList;
import java.util.List;

public final class Publisher {
    private static Publisher instance = null;
    private static Object syncObj = new Object();

    private List<Observer> observers;

    public Publisher() {
        observers = new ArrayList<>();
    }

    public static Publisher getInstance(){
        synchronized (syncObj) {
            if (instance == null) {
                instance = new Publisher();
            }

            return instance;
        }
    }

    public void subscribe(Observer observer) {
        synchronized (syncObj) {
            observers.add(observer);
        }
    }

    public void unsubscribe(Observer observer) {
        synchronized (syncObj) {
            observers.remove(observer);
        }
    }

    public void notifyCity(String text) {
        for (Observer observer : observers) {
            observer.updateCity(text);
        }
    }

    public void notifyTemp(String text) {
        for (Observer observer : observers) {
            observer.updateTemp(text);
        }
    }

    public void notifyErr(String text) {
        for (Observer observer : observers) {
            observer.updateError(text);
        }
    }

    public void notifyWind(String text) {
        for (Observer observer : observers) {
            observer.updateWind(text);
        }
    }

    public void notifyPressure(String text) {
        for (Observer observer : observers) {
            observer.updatePressure(text);
        }
    }

    public void notifyVisibleWindSpeed(boolean isVisible) {
        for (Observer observer : observers) {
            observer.setVisibleWindSpeed(isVisible);
        }
    }

    public void notifyVisiblePressure(boolean isVisible) {
        for (Observer observer : observers) {
            observer.setVisiblePressure(isVisible);
        }
    }
}
