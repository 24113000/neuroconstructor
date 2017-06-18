package com.sbezgin.network;

import com.sbezgin.network.neuron.Neuron;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Util {
    public static double getRandWeights() {
        double epsilon = 1.0;
        Random random = new Random();
        return (random.nextDouble() * 2 * epsilon) - epsilon;
    }

    public static List<Double[][]> createNNArray(NeuralNetwork neuralNetwork) {
        List<Double[][]> result = new ArrayList<>();
        int layersNumber = neuralNetwork.getLayersNumber();
        for (int lyr = 1; lyr < layersNumber; lyr++) {
            List<Neuron> layer = neuralNetwork.getLayer(lyr);
            List<Neuron> prevLayer = neuralNetwork.getLayer(lyr - 1);
            Double[][] arrayLayer = new Double[layer.size()][prevLayer.size()];
            Double zero = 0.0;
            Arrays.fill(arrayLayer[0], zero);
            Arrays.fill(arrayLayer, arrayLayer[0]);
            result.add(arrayLayer);
        }
        return result;
    }
}
