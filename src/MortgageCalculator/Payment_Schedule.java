package MortgageCalculator;

import java.text.NumberFormat;
import java.util.Scanner;

public class Payment_Schedule {
    final static byte MONTHS_IN_YEAR = 12;
    final static byte PERCENT = 100;

    public static void main(String[] args) {
        int principal = (int) readNumber("Principal : ", 1000, 1_000_000);
        float annualInterest = (float) readNumber("Annual Interest Rate : ", 1, 30);
        byte years = (byte) readNumber("Period (Year) : ", 1, 30);

        printMortgage(principal, annualInterest, years);

        printPaymentSchedule(years, principal, annualInterest);

    }

    private static void printMortgage(int principal, float annualInterest, byte years) {
        double mortgage = calculateMortgage(principal, annualInterest, years);
        String mortgageFormatted = NumberFormat.getCurrencyInstance().format(mortgage);    // Formating Mortgage into Currency
        System.out.println();
        System.out.println("MORTGAGE");
        System.out.println("--------");
        System.out.println("Monthly Payments: " + mortgageFormatted);
    }

    private static void printPaymentSchedule(byte years, int principal, float annualInterest) {
        System.out.println();
        System.out.println("PAYMENT SCHEDULE");
        System.out.println("----------------");
        for(short month = 1; month <= years * MONTHS_IN_YEAR; month++) {
            double balance = calculateBalance(principal, annualInterest, years, month);
            System.out.println(NumberFormat.getCurrencyInstance().format(balance));
        }
    }

    public  static double calculateBalance(
            int principal,
            float annualInterest,
            byte years,
            short numberOfPaymentsMade) {

        float monthlyInterest = annualInterest / MONTHS_IN_YEAR / PERCENT;          // Calculating Monthly Interest
        short numberOfPayments = (short)(years * MONTHS_IN_YEAR);

        return principal
                * (Math.pow(1 + monthlyInterest, numberOfPayments) - Math.pow(1 + monthlyInterest, numberOfPaymentsMade))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);
    }

    public static double readNumber(String prompt, double min, double max) {
        Scanner scanner = new Scanner(System.in);
        double value;
        while (true) {
            System.out.print(prompt);
            value = scanner.nextFloat();
            if (value >= min && value <= max)
                break;
            System.out.println("Enter a value between " + min + " and " + max);
        }
        return value;
    }

    public static double calculateMortgage(
            int principal,
            float annualInterest,
            byte years) {

        float monthlyInterest = annualInterest / MONTHS_IN_YEAR / PERCENT;          // Calculating Monthly Interest
        short numberOfPayments = (short)(years * MONTHS_IN_YEAR);                  // Calculating Number of Payments
        double powerOfNumbers = Math.pow((1 + monthlyInterest), numberOfPayments);    // Calculating (1+r)power(n)

        return principal * ((monthlyInterest*powerOfNumbers)/(powerOfNumbers-1));   // Calculating Mortgage
    }
}
