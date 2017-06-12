package com.sbezgin.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Neuron {
    private final List<Synapse> inSynapses = new ArrayList<>();
    private final List<Synapse> outSynapses = new ArrayList<>();
    private final int levelNumber;
    private double currentResult;

    public Neuron(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public void addInSynapse(Synapse s) {
        s.setTo(this);
        inSynapses.add(s);
    }

    public void addOutSynapse(Synapse s) {
        s.setFrom(this);
        outSynapses.add(s);
    }

    public List<Synapse> getInSynapses() {
        return Collections.unmodifiableList(inSynapses);
    }

    public List<Synapse> getOutSynapses() {
        return Collections.unmodifiableList(outSynapses);
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public double getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(double currentResult) {
        this.currentResult = currentResult;
    }
}
