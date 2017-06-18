package com.sbezgin.calculator;

import java.util.ArrayList;
import java.util.List;

public class GradientDecentCalc {

    private final double rate;

    public GradientDecentCalc(double rate) {
        this.rate = rate;
    }

    public List<Double[][]> calc(List<Double[][]> thetas, List<Double[][]> partialDerivatives, int trainingNumber) {

        List<Double[][]> newThetas = new ArrayList<>(thetas.size());

        for (int layer = 0; layer < thetas.size(); layer++) {
            Double[][] layerTheta = thetas.get(layer);
            Double[][] newLayerTheta = new Double[layerTheta.length][layerTheta[0].length];
            Double[][] derivativesArr = partialDerivatives.get(layer);
            for (int i = 0; i < layerTheta.length; i++) {
                for (int j = 0; j < layerTheta[i].length; j++) {
                    double newVal = layerTheta[i][j] - (rate * (derivativesArr[i][j])/trainingNumber);
                    newLayerTheta[i][j] = newVal;
                }
            }
            newThetas.add(newLayerTheta);
        }

        return newThetas;
    }
}
