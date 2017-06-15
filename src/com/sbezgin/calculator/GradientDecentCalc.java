package com.sbezgin.calculator;

import java.util.List;

public class GradientDecentCalc {

    private final double rate;

    public GradientDecentCalc(double rate) {
        this.rate = rate;
    }

    public NetworkArrayContainer calc(NetworkArrayContainer theta, NetworkArrayContainer partialDerivatives, int trainingNumber){
        NetworkArrayContainer result = new NetworkArrayContainer(theta);
        List<Double[][]> thArrays = theta.getArrays();

        for (int k = 0; k < thArrays.size(); k++) {
            Double[][] lvlTheta = thArrays.get(k);
            for (int i = 0; i < lvlTheta.length; i++) {
                for (int j = 0; j < lvlTheta[i].length; j++) {
                    double newVal = lvlTheta[i][j] - (rate * (partialDerivatives.get(k, i, j))/trainingNumber);
                    result.set(k, i, j, newVal);
                }
            }
        }

        return result;
    }
}
