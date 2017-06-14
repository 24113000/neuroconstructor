package com.sbezgin;

import com.sbezgin.calculator.BackPropogationCalc;
import com.sbezgin.calculator.ForwardCalc;
import com.sbezgin.calculator.GradientDecentCalc;
import com.sbezgin.network.NeuralNetwork;
import com.sbezgin.network.Neuron;
import com.sbezgin.network.Synapse;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Map<Integer, List<Neuron>> map = buildMap();
        NeuralNetwork neuralNetwork = new NeuralNetwork(map);
        Map<List<Double>, Integer> trainingExample = new HashMap<>();

        trainingExample.put(Arrays.asList(1.0, 1.0),1);
/*
        trainingExample.put(Arrays.asList(0.0, 1.0),0);
        trainingExample.put(Arrays.asList(1.0, 0.0),0);
        trainingExample.put(Arrays.asList(0.0, 0.0),0);
*/

        for (int i = 0; i < 10000; i++) {
            BackPropogationCalc backPropogationCalc = new BackPropogationCalc(neuralNetwork, new GradientDecentCalc(0.003));

            for (Map.Entry<List<Double>, Integer> entry : trainingExample.entrySet()) {
                List<Double> inputParams = entry.getKey();
                Integer expectedResult = entry.getValue();
                ForwardCalc forwardCalc = new ForwardCalc(neuralNetwork, inputParams);

                forwardCalc.evaluateNextLevel();
                forwardCalc.evaluateNextLevel();
                forwardCalc.evaluateNextLevel();

                backPropogationCalc.collectDeltaError(new double[]{expectedResult}, inputParams.toArray(new Double[inputParams.size()]));

                forwardCalc.reset();
            }

            System.out.println("COST == " + backPropogationCalc.updateSynapses());
        }

        ForwardCalc forwardCalcTrue= new ForwardCalc(neuralNetwork, Arrays.asList(1.0, 1.0));
        forwardCalcTrue.evaluateNextLevel();
        forwardCalcTrue.evaluateNextLevel();
        forwardCalcTrue.evaluateNextLevel();

        double trueResult = neuralNetwork.getLevel(2).get(0).getCurrentResult();
        System.out.println("Final true result : " + trueResult);

        ForwardCalc forwardCalcNotTrue= new ForwardCalc(neuralNetwork, Arrays.asList(0.0, 1.0));
        forwardCalcNotTrue.evaluateNextLevel();
        forwardCalcNotTrue.evaluateNextLevel();
        forwardCalcNotTrue.evaluateNextLevel();

        double notTrueResult = neuralNetwork.getLevel(2).get(0).getCurrentResult();
        System.out.println("Final NOT true result : " + notTrueResult);
    }

    private static Map<Integer, List<Neuron>> buildMap() {
        Synapse s00L0 = new Synapse();
        Synapse s01L0 = new Synapse();
        Synapse s10L0 = new Synapse();
        Synapse s11L0 = new Synapse();

        Synapse s00L1 = new Synapse();
        Synapse s01L1 = new Synapse();
        Synapse s10L1 = new Synapse();
        Synapse s11L1 = new Synapse();

        Synapse s00L2 = new Synapse();
        Synapse s01L2 = new Synapse();

        Neuron neuron00 = new Neuron(0);

        neuron00.addInSynapse(s00L0);
        neuron00.addInSynapse(s01L0);

        neuron00.addOutSynapse(s00L1);
        neuron00.addOutSynapse(s10L1);

        Neuron neuron01 = new Neuron(0);

        neuron01.addInSynapse(s10L0);
        neuron01.addInSynapse(s11L0);

        neuron01.addOutSynapse(s01L1);
        neuron01.addOutSynapse(s11L1);

        Neuron neuron10 = new Neuron(1);

        neuron10.addInSynapse(s00L1);
        neuron10.addInSynapse(s01L1);

        neuron10.addOutSynapse(s00L2);

        Neuron neuron11 = new Neuron(1);

        neuron11.addInSynapse(s10L1);
        neuron11.addInSynapse(s11L1);

        neuron11.addOutSynapse(s01L2);

        Neuron neuron20 = new Neuron(2);
        neuron20.addInSynapse(s00L2);
        neuron20.addInSynapse(s01L2);

        Map<Integer, List<Neuron>> map = new HashMap<>();
        map.put(0, Arrays.asList(neuron00, neuron01));
        map.put(1, Arrays.asList(neuron10, neuron11));
        map.put(2, Arrays.asList(neuron10));
        return map;
    }
}
