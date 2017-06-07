package com.sbezgin.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Neuron {
    private final List<Synapse> inSynapses;
    private final List<Synapse> outSynapses;
    private final int levelNumber;

    public Neuron(List<Synapse> inSynapses, List<Synapse> outSynapses, int levelNumber) {
        this.inSynapses = new ArrayList<Synapse>(inSynapses);
        this.outSynapses = new ArrayList<Synapse>(outSynapses);
        this.levelNumber = levelNumber;
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
}
