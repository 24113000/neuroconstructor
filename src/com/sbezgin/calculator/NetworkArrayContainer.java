package com.sbezgin.calculator;

import com.sbezgin.network.NeuralNetwork;
import com.sbezgin.network.Neuron;
import com.sbezgin.network.Synapse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkArrayContainer {

    private List<Double[][]> array;

    public NetworkArrayContainer(NetworkArrayContainer other) {
        int levelNumber = other.array.size();
        array = new ArrayList<>(levelNumber);
        for (int i = 0; i < levelNumber; i++) {
            Double[][] arrayLevel = other.array.get(i);
            int neuronNumber = arrayLevel.length;
            array.add(new Double[neuronNumber][arrayLevel[0].length]);
        }
    }

    public NetworkArrayContainer(NeuralNetwork neuralNetwork) {
        int levelNumber = neuralNetwork.getLevelNumber();
        array = new ArrayList<>(levelNumber);
        for (int i = 0; i < levelNumber; i++) {
            List<Neuron> level = neuralNetwork.getLevel(i);
            List<Synapse> inSynapses = level.get(0).getInSynapses();
            array.add(new Double[level.size()][inSynapses.size()]);
        }
    }

    public void set(int level, int i, int j, double value) {
        Double[][] doubles = array.get(level);
        doubles[i][j] = value;
    }

    public void add(int level, int i, int j, double value) {
        Double[][] doubles = array.get(level);
        if (doubles[i][j] == null) {
            doubles[i][j] = 0.0;
        }
        doubles[i][j] += value;
    }

    public double get(int level, int i, int j) {
        Double[][] doubles = array.get(level);
        return doubles[i][j];
    }

    public List<Double[][]> getArrays() {
        return Collections.unmodifiableList(array);
    }
}
