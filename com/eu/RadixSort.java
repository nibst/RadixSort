package com.eu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class RadixSort {

    private static final Logger LOG = Logger.getLogger(RadixSort.class.getName());
    private final String OUTPUT = "output.txt";
    private Scanner scanner;
    private PrintWriter printWriter;
    private final int NLETTERS = 26;

    public RadixSort(final String fileName) {
        File inputFile = new File(fileName);
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(OUTPUT);
            printWriter = new PrintWriter(fileWriter);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "No output file");
        }
        try {
            scanner = new Scanner(inputFile);
        } catch (Exception exception) {
            LOG.log(Level.SEVERE, "No input file");
        }
    }

    public void sortArraysInFile() {

        int size = 0;
        List<String> listStrings = new ArrayList<String>();
        String string;
        //pega apenas strings contendo apenas letras de A até Z e que sejam maior que 4
        scanner.useDelimiter("[^A-Z]");
        while (scanner.hasNext()) {
            if ((string = scanner.next()).length() >= 4) {
                listStrings.add(string);
                size++;
            }
        }
        //transformar lista pra array pra facilitar
        String[] unsortedStrings = new String[listStrings.size()];
        listStrings.toArray(unsortedStrings);

        for (int i = 0; i < 200; i++) {
            System.out.print(unsortedStrings[i] + " ");
        }
        System.out.println("");
        String[] aux = new String[size];
        radixSort(unsortedStrings, 0, 0, size - 1, aux);
        for (int i = 0; i < 300; i++) {
            System.out.print(unsortedStrings[i] + " ");
        }
        printOutput();
        printWriter.close();
        scanner.close();
    }


    private void printOutput() {
        //TODO
    }

    private void placeOnAccVector(int[] accVector, String string, int posLetter) {
        int posAccVector = 0;
        //os caracteres A-Z vão de 65 até 90. Se retirar 64 de todos fica de 1 até 26
        // o primeiro espaço do accVector fica reservado pra quando não há caracter
        posAccVector = (int) (string.charAt(posLetter)) - 64;
        accVector[posAccVector]++;
    }

    private void radixSort(String[] sArr, int posLetter, int lo, int hi, String[] aux) {
        //todos elementos do vetor acumulacao vão para 0 por default
        //numero de letras +1 porque o elemento na pos 0 é reservado para o caracter vazio
        int[] accVector = new int[NLETTERS + 1];
        //parada de recursao
        if (lo >= hi)
            return;
        for (int i = lo; i <= hi; i++) {
            // apenas se a string tiver tamanho igual ou maior a posicao da letra que está analisando
            if (sArr[i].length() > posLetter) {
                //colocar no vetor acumulacao
                placeOnAccVector(accVector, sArr[i], posLetter);
            } else
                accVector[0]++;
        }
        //somar as frequencias do vetor para virar um vetor acumulação
        for (int i = 0; i < NLETTERS; i++) {
            accVector[i + 1] += accVector[i];
        }

        //montar no vetor auxiliar usando o vetor acumulacao
        for (int i = hi; i >= lo; i--) {
            // apenas se a string tiver tamanho igual ou maior a posicao da letra que está analisando
            if (sArr[i].length() > posLetter) {
                //coloca sList.get(i) na posicao certa do vetor aux (de acordo com accVector)
                aux[accVector[(int) (sArr[i].charAt(posLetter)) - 64] - 1] = sArr[i];
                //tira 1 daquela posicao do vetor acumulacao
                accVector[(int) (sArr[i].charAt(posLetter)) - 64]--;
            } else {
                //senao fazer o mesmo de cima só que para letra vazia
                aux[accVector[0] - 1] = sArr[i];
                accVector[0]--;
            }
        }
        //copiar mudanças do aux para o array principal
        for (int i = lo; i <= hi; i++) {
            sArr[i] = aux[i - lo];
        }
        //comecar do r=1 porque não faz sentido dar sort entre as palavras que na posLetter atual estão com letra vazia
        for (int r = 1; r < NLETTERS; r++) {
            //sort na proxima posicao de letra nas palavras que começam com as mesmas letras
            radixSort(sArr, posLetter + 1, lo + accVector[r], lo + accVector[r + 1] - 1, aux);
        }
    }
}
