package com.sbezgin.network;

import com.sbezgin.network.Neuron;

public class Synapse {
    private double weight;
    private Neuron from;
    private Neuron to;

    public double getWeight() {
        return weight;
    }

    public Neuron getFrom() {
        return from;
    }

    public Neuron getTo() {
        return to;
    }
}
