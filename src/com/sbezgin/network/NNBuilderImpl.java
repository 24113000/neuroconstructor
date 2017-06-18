package com.sbezgin.network;

import com.sbezgin.network.neuron.Neuron;
import com.sbezgin.network.neuron.NeuronImpl;

import java.util.*;

public class NNBuilderImpl implements NNBuilder {

    private static final int NOT_INITIALIZE = -1;

    private Map<Integer, List<Neuron>> map = new HashMap<>();

    private int currentLevel = -1;

    @Override
    public NNBuilder addInputLayer(int neuronsNumber) {
        if (currentLevel != NOT_INITIALIZE) {
            throw new IllegalStateException("Input Layer already added");
        }
        currentLevel++;
        List<Neuron> neurons = buildNeuronList(neuronsNumber, false, true);
        map.put(currentLevel, neurons);

        return this;
    }

    @Override
    public NNBuilder addLayer(int neuronsNumber) {
        if (currentLevel == NOT_INITIALIZE) {
            throw new IllegalStateException("Cannot add regular layer if input layer is not add");
        }
        addLayer(neuronsNumber, false);
        return this;
    }

    @Override
    public NNBuilder addOutputLayer(int neuronsNumber) {
        if (currentLevel == NOT_INITIALIZE) {
            throw new IllegalStateException("Cannot add regular output layer if input layer is not add");
        }
        addLayer(neuronsNumber, true);
        return this;
    }

    private void addLayer(int neuronsNumber, boolean isOutput) {
        currentLevel++;
        List<Neuron> newLayerRegularNeurons = buildNeuronList(neuronsNumber, isOutput, false);
        map.put(currentLevel, newLayerRegularNeurons);
        List<Neuron> prevLayer = map.get(currentLevel - 1);

        for (Neuron currLayerNeuron : newLayerRegularNeurons) {
            for (Neuron prevLayerNeuron : prevLayer) {
                Synapse synapse = new Synapse();

                synapse.setTo(currLayerNeuron);
                synapse.setFrom(prevLayerNeuron);

                prevLayerNeuron.addOutSynapse(synapse);
                currLayerNeuron.addInSynapse(synapse);
            }
        }
    }

    private List<Neuron> buildNeuronList(int neuronsNumber, boolean isOutput, boolean isInput) {
        List<NeuronImpl> result = new ArrayList<>(neuronsNumber);
        for (int i = 0; i < neuronsNumber; i++) {
            result.add(new NeuronImpl(currentLevel, i, isOutput, isInput));
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public NeuralNetwork build() {
        return new NeuralNetwork(map);
    }
}
