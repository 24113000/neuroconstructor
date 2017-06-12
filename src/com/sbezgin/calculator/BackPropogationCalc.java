package com.sbezgin.calculator;

import com.sbezgin.network.NeuralNetwork;
import com.sbezgin.network.Neuron;
import com.sbezgin.network.Synapse;

import java.util.ArrayList;
import java.util.List;

public class BackPropogationCalc {
    private final GradientDecentCalc decentCalc;
    private final NeuralNetwork neuralNetwork;
    private NetworkArrayContainer partialDerivatives;

    public BackPropogationCalc(NeuralNetwork neuralNetwork, GradientDecentCalc decentCalc) {
        this.neuralNetwork = neuralNetwork;
        this.decentCalc = decentCalc;
        partialDerivatives  = new NetworkArrayContainer(neuralNetwork);
    }

    public void collectDeltaError(double[] expectedResult) {
        int lastLevelId = neuralNetwork.getLevelNumber() - 1;
        Double[][] deltas = new Double[neuralNetwork.getLevelNumber()][];

        for (int levelNum = lastLevelId; levelNum >= 0; levelNum--) {
            List<Neuron> level = neuralNetwork.getLevel(levelNum);

            deltas[levelNum] = new Double[level.size()];

            if (levelNum == lastLevelId) {
                for (int i = 0; i < level.size(); i++) {
                    Neuron neuron = level.get(0);
                    double currentResult = neuron.getCurrentResult();
                    deltas[levelNum][i] = currentResult - expectedResult[i];
                }
            } else {
                int prevLevel = ++levelNum;
                for (int i=0; i < level.size(); i++) {
                    Neuron neuron = level.get(i);
                    List<Synapse> outSynapses = neuron.getOutSynapses();
                    for (int j = 0; j < outSynapses.size(); j++) {
                        Synapse synapse = outSynapses.get(j);
                        double weight = synapse.getWeight();

                        Double[] prevDelta = deltas[prevLevel];

                        deltas[levelNum][i] += weight * prevDelta[j];
                    }

                    deltas[levelNum][i] *= sigmoidDerivatives(neuron.getCurrentResult());
                }
            }

            if (levelNum != lastLevelId) {
                Double[] currDelta = deltas[levelNum + 1];
                for (int i = 0; i < level.size(); i++) {
                    Neuron neuron = level.get(i);
                    List<Synapse> outSynapses = neuron.getOutSynapses();

                    for (int j = 0; j < outSynapses.size(); j++) {
                        double delta = currDelta[j];
                        partialDerivatives.set(levelNum, j, i, neuron.getCurrentResult() * delta);
                    }
                }
            }
        }
    }

    public void updateSynapses() {
        NetworkArrayContainer theta = getCurrentTheta();
        NetworkArrayContainer newThetas = decentCalc.calc(theta, partialDerivatives);
        List<Double[][]> arrays = newThetas.getArrays();
        for (int k = 0; k < arrays.size(); k++) {
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
    }

    private NetworkArrayContainer getCurrentTheta() {
        NetworkArrayContainer theta = new NetworkArrayContainer(neuralNetwork);

        int lastLevelId = neuralNetwork.getLevelNumber() - 1;
        for (int levelNum = lastLevelId; levelNum >= 0; levelNum--) {
            List<Neuron> level = neuralNetwork.getLevel(levelNum);
            for (int i = 0; i < level.size(); i++) {
                Neuron neuron = level.get(0);
                List<Synapse> inSynapses = neuron.getInSynapses();
                for (int j = 0; i < inSynapses.size(); i++) {
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
