package com.sbezgin.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeuralNetwork {
    private int levelNumber;
    private Map<Integer, List<Neuron>> levels;

    public NeuralNetwork(int levelNumber, Map<Integer, List<Neuron>> levels) {
        this.levels = new HashMap<>(levels);
        this.levelNumber = levelNumber;
    }

    public List<Neuron> getLevel(int levelNumber) {
        return levels.get(levelNumber);
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
