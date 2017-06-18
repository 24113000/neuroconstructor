package com.sbezgin.network.neuron;

import com.sbezgin.network.Synapse;
import com.sbezgin.network.neuron.Neuron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NeuronImpl implements Neuron {
    private final List<Synapse> inSynapses = new ArrayList<>();
    private final List<Synapse> outSynapses = new ArrayList<>();
    private final int levelNumber;
    private final int position;
    private final boolean isOutput;
    private final boolean isInput;

    public NeuronImpl(int levelNumber, int position, boolean isOutput, boolean isInput) {
        this.levelNumber = levelNumber;
        this.position = position;
        this.isOutput = isOutput;
        this.isInput = isInput;
    }

    public void addInSynapse(Synapse s) {
        inSynapses.add(s);
    }

    public void addOutSynapse(Synapse s) {
        outSynapses.add(s);
    }

    public List<Synapse> getInSynapses() {
        return Collections.unmodifiableList(inSynapses);
    }

    public List<Synapse> getOutSynapses() {
        return Collections.unmodifiableList(outSynapses);
    }

    public int getLayerNumber() {
        return levelNumber;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public boolean isInput() {
        return isInput;
    }

    @Override
    public boolean isOutput() {
        return isOutput;
    }

    @Override
    public String toString() {
        return "Layer-" + levelNumber + "; Position-" + position + "; isInput-" + isInput + "; isOutput-" + isOutput;
    }
}
