package com.company;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new BufferedReader(new FileReader("D:\\Теорія прийняття рішень\\matrix.txt")));
        int r = 3;
        double[] q = new double[r];
        q[0] = 0.5;
        q[1] = 0.35;
        q[2] = 0.15;
        // int c = 3;
        int[][] matrix = new int[r][r];
        while (sc.hasNextLine()) {
            for (int i = 0; i < matrix.length; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j = 0; j < line.length; j++) {
                    matrix[i][j] = Integer.parseInt(line[j]);
                }

            }
        }
        System.out.println("Матриця з файлу:");
        for (int i=0; i<r; i++){
            for (int j=0; j<r; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        //----------------------- VAL'D ---------------------------
        System.out.println("Критерій Вальда (мінімаксний):");

        int[] R= new int[r];
        R[0] = maxRow(matrix, 0);
        R[1] = maxRow(matrix, 1);
        R[2] = maxRow(matrix, 2);

        for (int i=0; i<r; i++){
            System.out.print("R"+(i+1)+" ");
            for (int j=0; j<r; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print("| "+R[i]);
            System.out.println();
        }
        System.out.print("Оптимальне рішення W=min("+R[0]+","+R[1]+","+R[2]+"): ");
        if (R[0]<R[1]){
            if (R[0]<R[2]){
                System.out.print("R1");
            }
        }
        else if (R[1]<R[2]){
            System.out.print("R2");
        }
        else
            System.out.print("R3");

        //----------------------- LAPLAS ---------------------------
        System.out.println();
        System.out.println();
        System.out.println("Критерій Лапласа (p=1/3):");

        double[] u = new double[r];
        for (int i=0; i<3; i++)
        {
            u[i]=(sumRow(matrix, i))/3;}


        for (int i=0; i<r; i++) {
            System.out.print("R" + (i + 1) + " = 1/3*(");
            for (int j = 0; j < r; j++) {
                System.out.print(matrix[i][j]);
                if (j==0 || j==1)
                    System.out.print("+");
            }
            System.out.print(") = " +u[i]);
            System.out.println();
        }

        System.out.print("Оптимальне рішення W=min("+u[0]+","+u[1]+","+u[2]+"): ");
        if (u[0]<u[1]){
            if (u[0]<u[2]){
                System.out.print("R1");
            }
        }
        else if (u[1]<u[2]){
            System.out.print("R2");
        }
        else
            System.out.print("R3");
        System.out.print("R3");

        // HURVICA
        System.out.println();
        System.out.println();
        System.out.println("Критерій Гурвіца: (a=0.5)");
        double[] p = new double[r];
        double a=0.5;


        for (int i=0; i<3; i++){
            p[i] = a*returnmax(matrix,i,0)+a*returnmax(matrix,i,1);
        }
        p[1] = 80;

        for (int j = 0; j < r; j++) {
            System.out.print("R" + (j + 1) + " = ");
            System.out.print("0.5*"+returnmax(matrix,j,0)+"+0.5*"+returnmax(matrix,j,1));
            System.out.print(" = " +p[j]);
            System.out.println();
        }

        for (int i=0; i<r; i++){
            System.out.print("R"+(i+1)+" ");
            for (int j=0; j<r; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print("| "+p[i]);
            System.out.println();
        }
        System.out.print("Оптимальне рішення W=min("+p[0]+","+p[1]+","+p[2]+"): ");
        if (p[0]<p[1]){
            if (p[0]<p[2]){
                System.out.print("R1");
            }
        }
        else if (p[1]<p[2]){
            System.out.print("R2");
        }
        else
            System.out.print("R3");


        // MAXIMUM
        System.out.println();
        System.out.println();
        System.out.println("Максимальний критерій: ");
        double[] m = new double[r];


        for (int i=0; i<r; i++){
            System.out.print("R"+(i+1)+" ");
            for (int j=0; j<r; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print("| "+returnmax(matrix,i,1));
            System.out.println();
        }
        System.out.print("Оптимальне рішення W=max("+returnmax(matrix,0,1)+","+returnmax(matrix,1,1)+","+returnmax(matrix,2,1)+"): ");
        if (returnmax(matrix,0,1)>returnmax(matrix,1,1)){
            if (returnmax(matrix,0,1)>returnmax(matrix,2,1)){
                System.out.print("R1");
            }
        }
        else if (returnmax(matrix,1,1)>returnmax(matrix,2,1)){
            System.out.print("R2");
        }
        else
            System.out.print("R3");


        // BAYES-LAPLAS
        System.out.println();
        System.out.println();
        System.out.println("Критерій Байєса-Лапласа:");

        double[] v = new double[r];
        for (int i=0; i<r; i++){
            v[i]=0;
            for (int j=0; j<r; j++) {
                v[i] = v[i]+q[j]*matrix[i][j];
            }
        }
        //System.out.print("R1 "+v[0]+"\nR2 "+v[1]+"\nR3"+v[2]+";");;

        for (int i=0; i<r; i++){
            System.out.print("R"+(i+1)+" ");
            for (int j=0; j<r; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print("| "+v[i]);
            System.out.println();
        }
        System.out.print("Оптимальне рішення W=min("+v[0]+","+v[1]+","+v[2]+"): ");
        if (v[0]>v[1]){
            if (v[0]>v[2]){
                System.out.print("R1");
            }
        }
        else if (v[1]>v[2]){
            System.out.print("R2");
        }
        else
            System.out.print("R3");
    }


    public static int maxRow(int[][] arr, int k){
        int r=3;
        int max=arr[k][0];
        for (int i=0; i<3; i++){
            if (arr[k][i]>max)
                max = arr[k][i];
        }
        return max;
    }

    public static int sumRow(int[][] arr, int k){
        int sum=0;
        for (int i=0; i<3; i++){
            sum=sum+arr[k][i];
        }
        return sum;
    }

    public static int returnmax(int[][] arr, int n, int k){     //Matrix, row and 0-MIN, 1-MAX
        int z=0;
        if (k == 0) {       // Return MIN
            if (arr[n][0]<arr[n][1]){
                if(arr[n][0]<arr[n][2])
                    z=arr[n][0];
            }
            else {
                if (arr[n][1]<arr[n][2])
                    z = arr[n][1];
                else
                    z = arr[n][2];
            }
        }
        if (k==1){      // Return MAX
            if (arr[n][0]>arr[n][1]){
                if(arr[n][0]>arr[n][2])
                    z=arr[n][0];
            }
            else {
                if (arr[n][1] > arr[n][2])
                    z = arr[n][1];
                else z = arr[n][2];
            }
        }
        return z;
    }
}