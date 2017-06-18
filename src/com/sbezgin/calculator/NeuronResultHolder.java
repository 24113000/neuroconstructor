package com.sbezgin.calculator;

import com.sbezgin.network.NeuralNetwork;
import com.sbezgin.network.neuron.Neuron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeuronResultHolder {
    private Map<Integer, List<Double>> map;
    public NeuronResultHolder(NeuralNetwork neuralNetwork) {
        map = new HashMap<>();
        int layersNumber = neuralNetwork.getLayersNumber();
        for (int i = 1; i < layersNumber; i++) {
            List<Neuron> layer = neuralNetwork.getLayer(i);
            List<Double> doubles = new ArrayList<>(layer.size());
            map.put(i, doubles);
            for (Neuron neuron : layer) {
                doubles.add(0.0);
            }
        }
    }

    public void set(int layer, int neuronNumber, double value) {
        List<Double> list = map.get(layer);
        list.set(neuronNumber, value);
    }

    public double get(int layer, int neuronNumber) {
        List<Double> list = map.get(layer);
        return list.get(neuronNumber);
    }

    public double[] getResults(int layerNmber){
        List<Double> list = map.get(layerNmber);
        double[] result = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }
}
