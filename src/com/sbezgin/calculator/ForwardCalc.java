package com.sbezgin.calculator;

import com.sbezgin.network.NeuralNetwork;
import com.sbezgin.network.Synapse;
import com.sbezgin.network.neuron.Neuron;

import java.util.ArrayList;
import java.util.List;

public class ForwardCalc {
    public static final int FIRST_NOT_INPUT_LAYER = 1;
    private final NeuralNetwork neuralNetwork;
    private final List<Double> inputValues;
    private final NeuronResultHolder resultHolder;

    public ForwardCalc(NeuralNetwork neuralNetwork, List<Double> inputValues, NeuronResultHolder resultHolder) {
        this.neuralNetwork = neuralNetwork;
        this.inputValues = new ArrayList<>(inputValues);
        this.resultHolder = resultHolder;
    }

    public void evaluate() {
        for (int currentLayer = FIRST_NOT_INPUT_LAYER; currentLayer < neuralNetwork.getLayersNumber(); currentLayer++) {
            List<Neuron> layer = neuralNetwork.getLayer(currentLayer);
            List<Double> values;
            if (currentLayer == FIRST_NOT_INPUT_LAYER) {
                values = inputValues;
            } else {
                int prevLayerNumber = currentLayer - 1;
                List<Neuron> neurons = neuralNetwork.getLayer(prevLayerNumber);
                values = new ArrayList<>(neurons.size());
                for (int i = 0; i < neurons.size(); i++) {
                    values.add(resultHolder.get(prevLayerNumber, i));
                }
            }

            for (Neuron neuron : layer) {
                List<Synapse> inSynapses = neuron.getInSynapses();
                double sum = 0.0;
                for (int i = 0; i < inSynapses.size(); i++) {
                    Synapse synapse = inSynapses.get(i);
                    sum += synapse.getWeight() * values.get(i);
                }
                double neuronResult = sigmoid(sum);
                resultHolder.set(currentLayer, neuron.getPosition(), neuronResult);
            }
        }
    }

    private double sigmoid(double val){
        return 1.0 / (1.0 + Math.exp(-1*val));
    }
}
