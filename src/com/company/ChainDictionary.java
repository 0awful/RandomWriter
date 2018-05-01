package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static javafx.application.Platform.exit;

public class ChainDictionary {
    private int chainLength;
    private HashMap<String, ArrayList<String>> dictionary;
    private String start;

    public ChainDictionary(int chainLength) {
        this.chainLength = chainLength;
        this.dictionary = new HashMap<>();
    }

    public void read(String fileName) {
        File file = new File(fileName);
        Scanner s;
        try {
            s = new Scanner(file);
            addWords(s);
        } catch (Exception e) {
            e.printStackTrace();
            exit();
        }
    }

    public void write(int desiredPrintLength) {
        String[] workingArray = new String[chainLength+1];
        hashMapToWorkingArrayInitialSetup(start,workingArray);
        initialPrint(workingArray);


        int printed = workingArray.length;

        while (workingArray[workingArray.length-1] != null && printed < desiredPrintLength) {
            updateWorkingArray(workingArray, true);
            hashMapToWorkingArray(workingArray);
            if (workingArray[workingArray.length-1] != null) {
                printed ++;
                System.out.print(workingArray[workingArray.length-1] + " ");
            }

        }
    }

    private void addWords(Scanner s){
        String[] workingDict = new String[chainLength + 1];

        fillNullArray(s, workingDict);
        arrayToHashMap(workingDict);
        while (s.hasNext()){
            updateWorkingArray(workingDict, false);
            workingDict[workingDict.length-1] = s.next();
            arrayToHashMap(workingDict);
        }
    }

    private void fillNullArray(Scanner s, String[] dict) {
        for (int i = 0; i < dict.length; i ++){
            if (dict[i] == null){
                dict[i] = s.next();
            }
        }
    }

    private void arrayToHashMap(String[] dict){
        String value = dict[dict.length-1];
        String key = getKeyFromWorkingArray(dict);

        if (dictionary.get(key) == null) {
            ArrayList<String> arr = new ArrayList<>();
            arr.add(value);
            dictionary.put(key, arr);
        } else {
            ArrayList<String> arr = dictionary.get(key);
            arr.add(value);
        }

        if (start == null){
            start = key;
        }
    }

    private void updateWorkingArray(String[] array, Boolean setNulls){
        for (int i = 1; i < array.length; i++){
            array[i-1] = array[i];
        }
        if (setNulls) {
            array[array.length-1] = null;
        }
    }

    private void hashMapToWorkingArrayInitialSetup(String key, String[] array){

        String value = randomValueFromKey(key);

        // this is always going to work because it is splitting on the spaces, and we have constructed this to have spaces
        // These words are equal to the size of an nGram so we can expect this to be shorter than the given array
        String[] words = start.split("\\s+");


        for (int i = 0; i < words.length; i++){
            array[i] = words[i];
        }


        array[array.length-1] = value;

    }

    private void hashMapToWorkingArray(String[] array){
        String key = getKeyFromWorkingArray(array);
        String value = randomValueFromKey(key);


        array[array.length-1] = value;

    }

    private String getKeyFromWorkingArray(String[] array){
        String key = "";

        for (int i = 0; i < array.length-1; i++){
            key += array[i];
            if (i != array.length-2){
                key += " ";
            }
        }

        return key;
    }

    private String randomValueFromKey(String key){
        ArrayList<String> listOfPossibles = dictionary.get(key);

        int randIndex = (int) Math.floor(Math.random()* listOfPossibles.size());
        return listOfPossibles.get(randIndex);
    }

    private void initialPrint(String[] array){
        for (String s:array
             ) {
            System.out.print(s + " ");
        }
    }

}
