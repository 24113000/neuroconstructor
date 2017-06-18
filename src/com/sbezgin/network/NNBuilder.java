package com.sbezgin.network;

public interface NNBuilder {

    NNBuilder addInputLayer(int neuronsNumber);

    NNBuilder addLayer(int neuronsNumber);

    NNBuilder addOutputLayer(int neuronsNumber);

    NeuralNetwork build();
}
