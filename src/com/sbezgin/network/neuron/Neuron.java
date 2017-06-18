package com.sbezgin.network.neuron;

import com.sbezgin.network.Synapse;

import java.util.List;

public interface Neuron {

    int INPUT_LAYER_NUMBER = 0;

    boolean isInput();

    boolean isOutput();

    List<Synapse> getInSynapses();

    List<Synapse> getOutSynapses();

    void addInSynapse(Synapse s);

    void addOutSynapse(Synapse s);


    int getLayerNumber();

    int getPosition();
}
