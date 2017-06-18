package com.sbezgin.network;

import com.sbezgin.network.neuron.Neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NeuralNetwork {

    private int layersNumber;
    private Map<Integer, List<Neuron>> nnMap;
    private List<Integer> levelIds;

    protected NeuralNetwork(Map<Integer, List<Neuron>> nnMap) {
        this.nnMap = nnMap;
        this.layersNumber = nnMap.size();

        levelIds = new ArrayList<>(nnMap.keySet());
        levelIds.sort(Integer::compareTo);
    }

    public List<Neuron> getLayer(int levelNumber) {
        return nnMap.get(levelNumber);
    }

    public int getLayersNumber() {
        return layersNumber;
    }

    public List<Integer> getLevelIds() {
        return levelIds;
    }

    public List<Double[][]> getWeights() {
        List<Double[][]> array = new ArrayList<>(layersNumber - 1);
        for (int lyr = 1; lyr < layersNumber; lyr++) {
            List<Neuron> layer = nnMap.get(lyr);
            List<Neuron> prevLayer = nnMap.get(lyr - 1);

            Double[][] arrayLayer = new Double[layer.size()][prevLayer.size()];

            for (int i = 0; i < layer.size(); i++) {
                Neuron neuron = layer.get(i);
                List<Synapse> inSynapses = neuron.getInSynapses();
                for (int j = 0; j < inSynapses.size(); j++) {
                    arrayLayer[i][j] = inSynapses.get(j).getWeight();
                }
            }

            array.add(arrayLayer);
        }

        return array;
    }
}
