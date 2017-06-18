package com.sbezgin;

import com.sbezgin.calculator.BackPropogationCalc;
import com.sbezgin.calculator.ForwardCalc;
import com.sbezgin.calculator.GradientDecentCalc;
import com.sbezgin.calculator.NeuronResultHolder;
import com.sbezgin.network.NNBuilderImpl;
import com.sbezgin.network.NeuralNetwork;
import com.sbezgin.network.Synapse;
import com.sbezgin.network.neuron.Neuron;
import com.sbezgin.network.neuron.NeuronImpl;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork(buildMap()); /*new NNBuilderImpl()
                .addInputLayer(2)
                .addLayer(2)
                .addLayer(2)
                .addOutputLayer(1)
                .build();*/

        Map<List<Double>, Integer> trainingExample = new HashMap<>();
        trainingExample.put(Arrays.asList(1.0, 1.0),1);
        trainingExample.put(Arrays.asList(0.0, 1.0),1);
        trainingExample.put(Arrays.asList(1.0, 0.0),0);
        trainingExample.put(Arrays.asList(0.0, 0.0),0);

        GradientDecentCalc decentCalc = new GradientDecentCalc(0.3);
        NeuronResultHolder resultHolder = new NeuronResultHolder(neuralNetwork);

        for (int i = 0; i < 150000; i++) {
            BackPropogationCalc backPropogationCalc = new BackPropogationCalc(neuralNetwork, decentCalc, resultHolder);

            for (Map.Entry<List<Double>, Integer> entry : trainingExample.entrySet()) {

                List<Double> inputParams = entry.getKey();
                Integer expectedResult = entry.getValue();

                ForwardCalc forwardCalc = new ForwardCalc(neuralNetwork, inputParams, resultHolder);
                forwardCalc.evaluate();

                backPropogationCalc.collectDeltaError(new double[]{expectedResult}, inputParams.toArray(new Double[inputParams.size()]));
            }

            System.out.println("COST == " + backPropogationCalc.updateSynapses(trainingExample.size()));
        }

        int finalLayer = neuralNetwork.getLayersNumber() - 1;

        ForwardCalc forwardCalcTrue11= new ForwardCalc(neuralNetwork, Arrays.asList(1.0, 1.0), resultHolder);
        forwardCalcTrue11.evaluate();
        double[] result = resultHolder.getResults(finalLayer);
        System.out.println("Final 1 1 result : " + Arrays.toString(result));

        ForwardCalc forwardCalc00= new ForwardCalc(neuralNetwork, Arrays.asList(0.0, 0.0), resultHolder);
        forwardCalc00.evaluate();

        result = resultHolder.getResults(finalLayer);
        System.out.println("Final 0 0 result : " + Arrays.toString(result));

        ForwardCalc forwardCalc01= new ForwardCalc(neuralNetwork, Arrays.asList(0.0, 1.0), resultHolder);
        forwardCalc01.evaluate();

        result = resultHolder.getResults(finalLayer);
        System.out.println("Final 0 1 result : " + Arrays.toString(result));

        ForwardCalc forwardCalc10= new ForwardCalc(neuralNetwork, Arrays.asList(1.0, 0.0), resultHolder);
        forwardCalc10.evaluate();

        result = resultHolder.getResults(finalLayer);
        System.out.println("Final 1 0 result : " + Arrays.toString(result));
    }

    private static Map<Integer, List<Neuron>> buildMap() {
        Synapse s00L0 = new Synapse(0.02);
        Synapse s01L0 = new Synapse(0.03);
        Synapse s10L0 = new Synapse(0.04);
        Synapse s11L0 = new Synapse(-0.02);

        Synapse s00L1 = new Synapse(0.015);
        Synapse s01L1 = new Synapse(-0.015);
        Synapse s10L1 = new Synapse(-0.05);
        Synapse s11L1 = new Synapse(0.06);

        Synapse s00L2 = new Synapse(-0.07);
        Synapse s01L2 = new Synapse(-0.065);

        Neuron neuronIn0 = new NeuronImpl(0, 0, false, true);
        s00L0.setFrom(neuronIn0);
        neuronIn0.addOutSynapse(s00L0);
        s10L0.setFrom(neuronIn0);
        neuronIn0.addOutSynapse(s10L0);

        Neuron neuronIn1 = new NeuronImpl(0, 1, false, true);
        s01L0.setFrom(neuronIn1);
        neuronIn1.addOutSynapse(s01L0);
        s11L0.setFrom(neuronIn1);
        neuronIn1.addOutSynapse(s11L0);

        Neuron neuron00 = new NeuronImpl(1, 0, false, false);
        s00L0.setTo(neuron00);
        neuron00.addInSynapse(s00L0);
        s01L0.setTo(neuron00);
        neuron00.addInSynapse(s01L0);

        s00L1.setFrom(neuron00);
        neuron00.addOutSynapse(s00L1);
        s10L1.setFrom(neuron00);
        neuron00.addOutSynapse(s10L1);

        Neuron neuron01 = new NeuronImpl(1, 1, false, false);

        s10L0.setTo(neuron01);
        neuron01.addInSynapse(s10L0);
        s11L0.setTo(neuron01);
        neuron01.addInSynapse(s11L0);

        s01L1.setFrom(neuron01);
        neuron01.addOutSynapse(s01L1);
        s11L1.setFrom(neuron01);
        neuron01.addOutSynapse(s11L1);

        Neuron neuron10 = new NeuronImpl(2, 0, false, false);

        s00L1.setTo(neuron10);
        neuron10.addInSynapse(s00L1);
        s01L1.setTo(neuron10);
        neuron10.addInSynapse(s01L1);

        s00L2.setFrom(neuron10);
        neuron10.addOutSynapse(s00L2);

        Neuron neuron11 = new NeuronImpl(2, 1, false, false);

        s10L1.setTo(neuron11);
        neuron11.addInSynapse(s10L1);
        s11L1.setTo(neuron11);
        neuron11.addInSynapse(s11L1);

        s01L2.setFrom(neuron11);
        neuron11.addOutSynapse(s01L2);

        Neuron neuron20 = new NeuronImpl(3, 0, true, false);
        neuron20.addInSynapse(s00L2);
        neuron20.addInSynapse(s01L2);

        Map<Integer, List<Neuron>> map = new HashMap<>();
        map.put(0, Arrays.asList(neuron00, neuron01));
        map.put(1, Arrays.asList(neuron00, neuron01));
        map.put(2, Arrays.asList(neuron10, neuron11));
        map.put(3, Arrays.asList(neuron20));
        return map;
    }

}
