package com.sbezgin.calculator;

import com.sbezgin.network.NeuralNetwork;
import com.sbezgin.network.Util;
import com.sbezgin.network.neuron.Neuron;
import com.sbezgin.network.Synapse;

import java.util.*;

public class BackPropogationCalc {
    private final GradientDecentCalc decentCalc;
    private final NeuralNetwork neuralNetwork;
    private final NeuronResultHolder resultHolder;
    private List<Double[][]> partialDerivatives;
    private double costResult = 0.0;

    public BackPropogationCalc(NeuralNetwork neuralNetwork, GradientDecentCalc decentCalc, NeuronResultHolder resultHolder) {
        this.neuralNetwork = neuralNetwork;
        this.decentCalc = decentCalc;
        this.partialDerivatives = Util.createNNArray(neuralNetwork);
        this.resultHolder = resultHolder;
    }

    public void collectDeltaError(double[] expectedResult, Double[] inputValues) {
        int lastLayerId = neuralNetwork.getLayersNumber() - 1;
        Map<Integer, Double[]> deltasMap = new HashMap<>();

        for (int layerNum = lastLayerId; layerNum > 0; layerNum--) {
            List<Neuron> layer = neuralNetwork.getLayer(layerNum);

            Double[] deltas = new Double[layer.size()];
            Arrays.fill(deltas, new Double(0.0));
            deltasMap.put(layerNum, deltas);

            if (layerNum == lastLayerId) {
                for (int i = 0; i < layer.size(); i++) {
                    Neuron neuron = layer.get(i);
                    double currentResult = resultHolder.get(layerNum, neuron.getPosition());
                    double delta = currentResult - expectedResult[i];
                    deltas[i] = delta;
                    System.out.println("BACK " + Arrays.toString(inputValues) + " Expected result: " + Arrays.toString(expectedResult) + " DIFF: " + Arrays.toString(deltas));
                    costResult += (delta * delta);
                }
            } else {
                int nextLevel = layerNum + 1;
                for (int i=0; i < layer.size(); i++) {
                    Neuron neuron = layer.get(i);
                    List<Synapse> outSynapses = neuron.getOutSynapses();
                    Double[] nextLayerDeltas = deltasMap.get(nextLevel);
                    for (int j = 0; j < outSynapses.size(); j++) {
                        Synapse synapse = outSynapses.get(j);

                        double weight = synapse.getWeight();
                        deltas[i] += weight * nextLayerDeltas[j];
                    }

                    double nResult = resultHolder.get(layerNum, neuron.getPosition());
                    deltas[i] *= sigmoidDerivatives(nResult);
                }
            }

            Double[] currDelta = deltasMap.get(layerNum);
            Double[][] derivativesArr = partialDerivatives.get(layerNum - 1);
            for (int i = 0; i < layer.size(); i++) {
                Neuron neuron = layer.get(i);
                List<Synapse> inSynapses = neuron.getInSynapses();
                double delta = currDelta[i];
                for (int j = 0; j < inSynapses.size(); j++) {
                    Synapse synapse = inSynapses.get(j);
                    Neuron fromNeuron = synapse.getFrom();
                    if (fromNeuron.isInput()) {
                        derivativesArr[i][j] += inputValues[j] * delta;
                    } else {
                        double neuronResult = resultHolder.get(fromNeuron.getLayerNumber(), fromNeuron.getPosition());
                        derivativesArr[i][j] += neuronResult * delta;
                    }
                }
            }
        }
    }

    public double updateSynapses(int trainingNumber) {
        double result = costResult;
        costResult = 0.0;
        List<Double[][]> theta = neuralNetwork.getWeights();
        List<Double[][]> newThetas = decentCalc.calc(theta, partialDerivatives, trainingNumber);
        for (int layer = 0; layer < theta.size(); layer++) {
            List<Neuron> neuronLayer = neuralNetwork.getLayer(layer + 1);
            Double[][] newLayerArr = newThetas.get(layer);
            for (int i = 0; i < neuronLayer.size(); i++) {
                Neuron neuron = neuronLayer.get(i);
                List<Synapse> inSynapses = neuron.getInSynapses();
                for (int j = 0; j < inSynapses.size(); j++) {
                    Synapse synapse = inSynapses.get(j);
                    synapse.setWeight(newLayerArr[i][j]);
                }
            }
        }

        return result/(2*trainingNumber);
    }

    private double sigmoidDerivatives(double val) {
        return val * (1 - val);
    }
}
