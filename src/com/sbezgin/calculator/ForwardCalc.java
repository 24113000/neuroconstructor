package com.sbezgin.calculator;

import com.sbezgin.network.NeuralNetwork;
import com.sbezgin.network.Neuron;
import com.sbezgin.network.Synapse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForwardCalc {
    private final NeuralNetwork neuralNetwork;
    private final List<Double> inputValues;
    private int currentLevel;
    private boolean isCalculationFinished;

    public ForwardCalc(NeuralNetwork neuralNetwork, List<Double> inputValues) {
        this.neuralNetwork = neuralNetwork;
        this.inputValues = new ArrayList<>(inputValues);
    }

    public CalcResult evaluateNextLevel() {
        List<Neuron> level = neuralNetwork.getLevel(currentLevel);
        List<Double> values;
        if (currentLevel == 0) {
            values = inputValues;
        } else {
            List<Neuron> prevNeurons = neuralNetwork.getLevel(currentLevel - 1);
            values = new ArrayList<>(prevNeurons.size());
            for (Neuron prevNeuron : prevNeurons) {
                values.add(prevNeuron.getCurrentResult());
            }
        }

        for (Neuron neuron : level) {
            List<Synapse> inSynapses = neuron.getInSynapses();
            double sum = 0.0;
            for (int i = 0; i < inSynapses.size(); i++) {
                Synapse synapse = inSynapses.get(i);
                sum += synapse.getWeight() * values.get(i);
            }
            double neuronOut = sigmoid(sum);
            neuron.setCurrentResult(neuronOut);
        }

        if (currentLevel >= neuralNetwork.getLevelNumber()) {
            isCalculationFinished = true;
        }
        currentLevel++;
        return null;
    }

    public boolean isCalculationFinished() {
        return isCalculationFinished;
    }

    public void reset() {
        currentLevel = 0;
        isCalculationFinished = false;
        System.out.print(Arrays.toString(inputValues.toArray()) + " -- ");
        System.out.println("  Result = " + neuralNetwork.getLevel(2).get(0).getCurrentResult());
    }

    private double sigmoid(double val){
        return 1.0 / (1.0 + Math.exp(-1*val));
    }
}
