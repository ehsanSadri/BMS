package com.sadri.bms.model.service.observer;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Subject {

    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    protected abstract void notifyObservers(Object transaction);
}