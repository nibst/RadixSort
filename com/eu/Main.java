package com.eu;

public class Main {

    public static void main(String[] args) {

        RadixSort mySorter1 = new RadixSort (args[0], "frankenstein_ordenado.txt");
        RadixSort mySorter2 = new RadixSort(args[1],"war_and_peace_ordenado.txt");
        mySorter1.sortArraysInFile();
        mySorter2.sortArraysInFile();
    }
}
