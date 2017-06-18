package com.sbezgin.network;


import com.sbezgin.network.neuron.Neuron;

public class Synapse {
    private double weight;
    private Neuron from;
    private Neuron to;

    public Synapse() {
        weight = Util.getRandWeights();
    }

    public double getWeight() {
        return weight;
    }

    public Neuron getFrom() {
        return from;
    }

    public Neuron getTo() {
        return to;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setFrom(Neuron from) {
        this.from = from;
    }

    public void setTo(Neuron to) {
        this.to = to;
    }
}
