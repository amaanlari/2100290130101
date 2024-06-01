package com.lari.averagecalculator.util;

public class NumberUtils {

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isFibonacci(int n) {
        int a = 0, b = 1;
        while (b < n) {
            int temp = b;
            b = a + b;
            a = temp;
        }
        return b == n || a == n;
    }

    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    public static boolean isRandom(int n) {
        return !isPrime(n) && !isFibonacci(n) && !isEven(n);
    }
}
