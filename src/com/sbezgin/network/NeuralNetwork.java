package com.sbezgin.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeuralNetwork {
    private int levelNumber;
    private Map<Integer, List<Neuron>> levels;
    private List<Integer> levelIds;
    public NeuralNetwork(Map<Integer, List<Neuron>> levels) {
        this.levels = new HashMap<>(levels);
        this.levelNumber = levels.size();

        levelIds = new ArrayList<>(levels.keySet());
        levelIds.sort(Integer::compareTo);
    }

    public List<Neuron> getLevel(int levelNumber) {
        return levels.get(levelNumber);
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public List<Integer> getLevelIds() {
        return levelIds;
    }
}
