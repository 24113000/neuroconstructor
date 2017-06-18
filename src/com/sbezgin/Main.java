package com.sbezgin;

import com.sbezgin.calculator.BackPropogationCalc;
import com.sbezgin.calculator.ForwardCalc;
import com.sbezgin.calculator.GradientDecentCalc;
import com.sbezgin.calculator.NeuronResultHolder;
import com.sbezgin.network.NNBuilderImpl;
import com.sbezgin.network.NeuralNetwork;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        NeuralNetwork neuralNetwork =  new NNBuilderImpl()
                .addInputLayer(2)
                .addLayer(2)
                .addLayer(2)
                .addOutputLayer(1)
                .build();

        Map<List<Double>, Integer> trainingExample = new HashMap<>();
        trainingExample.put(Arrays.asList(1.0, 1.0),1);
        trainingExample.put(Arrays.asList(0.0, 1.0),0);
        trainingExample.put(Arrays.asList(1.0, 0.0),0);
        trainingExample.put(Arrays.asList(0.0, 0.0),0);

        GradientDecentCalc decentCalc = new GradientDecentCalc(0.3);
        NeuronResultHolder resultHolder = new NeuronResultHolder(neuralNetwork);

        for (int i = 0; i < 9000; i++) {
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
}
