package com.example.util;

import android.util.Log;

import java.math.BigDecimal;

public class MatrixUtil {

    public static double [][] matrixTransposition( double [][] changedMatrix){
        int colums = changedMatrix.length;
        double targetMatrix [][] = new double[colums][colums];
        for(int i = 0; i< colums ;i++){
            for(int j = 0; j< colums; j++){
                targetMatrix[i][j] = changedMatrix[j][i];
            }
        }
        return targetMatrix;
    }

    //矩阵的乘法，前一个列数与后一个行数相同
    public static  double[][] matrixMultiply(double[][] previousMatrix , double [][] laterMatrix){
        if(previousMatrix[0].length!= laterMatrix.length){
            return null;
        }
        int lines = previousMatrix.length;   //新的行数
        int colums = laterMatrix[0].length;  //列数
        int commonLines = laterMatrix.length;  //前一个的列数与后一个的行数
        double[][] targetMatrix = new double[lines][colums];
        for(int i = 0; i < lines ; i++){
            for(int j = 0; j< colums ;j++){
                BigDecimal sum = BigDecimal.valueOf(0.0);
                for(int k = 0 ;k < commonLines ;k++){
                    sum = sum.add(BigDecimal.valueOf(previousMatrix[i][k]).multiply(BigDecimal.valueOf(laterMatrix[k][j])));
                }
                targetMatrix[i][j] = sum.doubleValue();
            }
        }
        return targetMatrix;
    }

    //求矩阵的行列式,我们可以求解四次的
    public static double matrixValues(double [][] solvMatrix){
        if(solvMatrix.length!= solvMatrix[0].length)
            return 0.;
        if (solvMatrix.length==1)
            return solvMatrix[0][0];
        else if (solvMatrix.length==2)
            return matrix2Det(solvMatrix);
        else if (solvMatrix.length==3)
            return matrix3Det(solvMatrix);
        else {    //可以求解四次的
            double sum = 0.0;
            for (int i = 0; i < 4; i++) {
                sum += Math.pow(-1, i + 2) * solvMatrix[0][i] * matrix3Det(companionMatrix(solvMatrix, 0, i));
            }
            return sum;
        }
    }
    //  根据位置，求得伴随矩阵
    public static double [][] companionMatrix(double [][] matrix ,int line ,int column){
        int lines = matrix.length;
        int columns = matrix[0].length;
        double [][] targetMarix = new double[lines-1][columns-1];
        int dx=0;
        for(int i=0;i < lines ;++i){
            if(i!=line){
                int dy=0;
                for (int j=0;j < columns ;++j){
                    if (j!=column){
                        targetMarix[dx][dy++] = matrix[i][j];
                    }
                }
                ++dx;
            }
        }
        return targetMarix;
    }
    //求解三阶的行列式值
    public static double matrix3Det(double[][] solvMatrix) {
        if(solvMatrix.length!=solvMatrix[0].length){
            return 0.;
        }
        double [][] A0 = companionMatrix(solvMatrix,0,0);
        double [][] A1 = companionMatrix(solvMatrix,0,1);
        double [][] A2 = companionMatrix(solvMatrix,0,2);
        BigDecimal Part1 = BigDecimal.valueOf(solvMatrix[0][0]).multiply(BigDecimal.valueOf(matrix2Det(A0)));
        BigDecimal Part2 = BigDecimal.valueOf(solvMatrix[0][1]).multiply(BigDecimal.valueOf(matrix2Det(A1)));
        BigDecimal Part3 = BigDecimal.valueOf(solvMatrix[0][2]).multiply(BigDecimal.valueOf(matrix2Det(A2)));
        BigDecimal part1_2 = Part1.subtract(Part2);
        BigDecimal final_result = part1_2.add(Part3);
        return final_result.doubleValue();
    }
    //求解2阶的行列式值
    public static double matrix2Det(double[][] solvMatrix) {
        BigDecimal first = BigDecimal.valueOf(solvMatrix[0][0]).multiply(BigDecimal.valueOf(solvMatrix[1][1]));
        BigDecimal second = BigDecimal.valueOf(solvMatrix[0][1]).multiply(BigDecimal.valueOf(solvMatrix[1][0]));
        return first.subtract(second).doubleValue();
    }

    //求逆矩阵
    public static double[][] matrixInv(double[][] solveMatrix){
        //行数与列数必须相等
        if (solveMatrix.length!=solveMatrix[0].length)
            return null;
        int lines = solveMatrix.length;
        int columns = solveMatrix[0].length;
        double A = matrixValues(solveMatrix);
        double[][] targetMatrix =new double[lines][columns];
        for(int i=0;i < lines;++i){
            for (int j=0;j< columns ;++j){
                double[][] temp= companionMatrix(solveMatrix,i,j);
                targetMatrix[j][i]=BigDecimal.valueOf(matrixValues(temp)).divide(BigDecimal.valueOf(A),40,BigDecimal.ROUND_DOWN).doubleValue() * Math.pow(-1,i+j);
            }
        }
        return targetMatrix;
    }
}
