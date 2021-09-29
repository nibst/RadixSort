package com.eu;

public class Main {

    public static void main(String[] args) {

        RadixSort mySorter = new RadixSort (args[0]);
        mySorter.sortArraysInFile();
    }
}
