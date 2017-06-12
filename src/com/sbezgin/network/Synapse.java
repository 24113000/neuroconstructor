package com.sbezgin.network;

import java.util.Random;

public class Synapse {
    private double weight;
    private Neuron from;
    private Neuron to;

    public Synapse() {
        Random random = new Random();
        weight = random.nextDouble();
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
