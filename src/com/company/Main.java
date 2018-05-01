package com.company;

public class Main {

    public static void main(String[] args) {

        ChainDictionary dict = new ChainDictionary(2);
        dict.read("metamorphasis.txt");
        dict.write(1000);
    }
}
