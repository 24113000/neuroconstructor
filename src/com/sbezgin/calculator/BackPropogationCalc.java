package com.sbezgin.calculator;

import com.sbezgin.network.NeuralNetwork;
import com.sbezgin.network.Neuron;
import com.sbezgin.network.Synapse;

import java.util.Arrays;
import java.util.List;

public class BackPropogationCalc {
    private final GradientDecentCalc decentCalc;
    private final NeuralNetwork neuralNetwork;
    private NetworkArrayContainer partialDerivatives;
    private double costResult = 0.0;

    public BackPropogationCalc(NeuralNetwork neuralNetwork, GradientDecentCalc decentCalc) {
        this.neuralNetwork = neuralNetwork;
        this.decentCalc = decentCalc;
        partialDerivatives  = new NetworkArrayContainer(neuralNetwork);
    }

    public void collectDeltaError(double[] expectedResult, Double[] inputValues) {
        int lastLevelId = neuralNetwork.getLevelNumber() - 1;
        double[][] deltas = new double[neuralNetwork.getLevelNumber()][];

        for (int levelNum = lastLevelId; levelNum >= 0; levelNum--) {
            List<Neuron> level = neuralNetwork.getLevel(levelNum);

            deltas[levelNum] = new double[level.size()];

            if (levelNum == lastLevelId) {
                for (int i = 0; i < level.size(); i++) {
                    Neuron neuron = level.get(i);
                    double currentResult = neuron.getCurrentResult();
                    deltas[levelNum][i] = currentResult - expectedResult[i];
                    System.out.println("BACK " + Arrays.toString(inputValues) + " Expected result: " + Arrays.toString(expectedResult) + " DIFF: " + deltas[levelNum][i]);
                    costResult += (deltas[levelNum][i] * deltas[levelNum][i]);
                }
            } else {
                int nextLevel = levelNum + 1;
                for (int i=0; i < level.size(); i++) {
                    Neuron neuron = level.get(i);
                    List<Synapse> outSynapses = neuron.getOutSynapses();
                    for (int j = 0; j < outSynapses.size(); j++) {
                        Synapse synapse = outSynapses.get(j);
                        double weight = synapse.getWeight();

                        double[] prevDelta = deltas[nextLevel];

                        deltas[levelNum][i] += weight * prevDelta[j];
                    }

                    deltas[levelNum][i] *= sigmoidDerivatives(neuron.getCurrentResult());
                }
            }

            double[] currDelta = deltas[levelNum];
            for (int i = 0; i < level.size(); i++) {
                Neuron neuron = level.get(i);
                List<Synapse> inSynapses = neuron.getInSynapses();
                double delta = currDelta[i];
                for (int j = 0; j < inSynapses.size(); j++) {
                    Synapse synapse = inSynapses.get(j);
                    Neuron from = synapse.getFrom();
                    if (from != null) {
                        partialDerivatives.set(levelNum, i, j, from.getCurrentResult() * delta);
                    } else {
                        partialDerivatives.set(levelNum, i, j, inputValues[j] * delta);
                    }
                }
            }
        }
    }

    public double updateSynapses() {
        double result = costResult;
        costResult = 0.0;
        NetworkArrayContainer theta = getCurrentTheta();
        NetworkArrayContainer newThetas = decentCalc.calc(theta, partialDerivatives);
        List<Double[][]> arrays = newThetas.getArrays();
        for (int k = 1; k < arrays.size(); k++) {
            Double[][] lvlTheta = arrays.get(k);
            List<Neuron> level = neuralNetwork.getLevel(k);
            for (int i = 0; i < lvlTheta.length; i++) {
                for (int j = 0; j < lvlTheta[i].length; j++) {
                    Neuron neuron = level.get(i);
                    List<Synapse> inSynapses = neuron.getInSynapses();
                    Synapse currentSynapse = inSynapses.get(j);
                    currentSynapse.setWeight(newThetas.get(k, i, j));
                }
            }
        }

        return result/(2*1);
    }

    private NetworkArrayContainer getCurrentTheta() {
        NetworkArrayContainer theta = new NetworkArrayContainer(neuralNetwork);

        int lastLevelId = neuralNetwork.getLevelNumber() - 1;
        for (int levelNum = lastLevelId; levelNum >= 0; levelNum--) {
            List<Neuron> level = neuralNetwork.getLevel(levelNum);
            for (int i = 0; i < level.size(); i++) {
                Neuron neuron = level.get(i);
                List<Synapse> inSynapses = neuron.getInSynapses();
                for (int j = 0; j < inSynapses.size(); j++) {
                    Synapse synapse = inSynapses.get(j);
                    theta.set(levelNum, i, j, synapse.getWeight());
                }
            }
        }
        return theta;
    }

    private double sigmoidDerivatives(double val) {
        return val * (1 - val);
    }
}
