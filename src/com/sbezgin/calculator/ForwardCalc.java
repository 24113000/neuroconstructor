package com.sbezgin.calculator;

import com.sbezgin.network.NeuralNetwork;

import java.util.ArrayList;
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
        CalcResult calcResult = evaluate();
        currentLevel++;
        if (currentLevel >= neuralNetwork.getLevelNumber()) {
            isCalculationFinished = true;
        }
        return calcResult;
    }

    private CalcResult evaluate() {
        return null;
    }

    public boolean isCalculationFinished() {
        return isCalculationFinished;
    }

    public void reset() {
        currentLevel = 0;
        isCalculationFinished = false;
    }
}
