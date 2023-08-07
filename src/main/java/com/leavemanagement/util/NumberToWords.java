package com.leavemanagement.util;

import org.springframework.stereotype.Service;


public class NumberToWords {
	private static final String EMPTY = "";
	 
    private static final String[] X =
    {
        EMPTY, "One ", "Two ", "Three ", "Four ", "Five ", "Six ",
        "Seven ", "Eight ", "Nine ", "Ten ", "Eleven ","Twelve ",
        "Thirteen ", "Fourteen ", "Fifteen ", "Sixteen ",
        "Seventeen ", "Eighteen ", "Nineteen "
    };
 
    private static final String[] Y =
    {
        EMPTY, EMPTY, "Twenty ", "Thirty ", "Forty ", "Fifty ",
        "Sixty ", "Seventy ", "Eighty ", "Ninety "
    };
 
    // Function to convert a single-digit or two-digit number Longo words
    private static String convertToDigit(long n, String suffix)
    {
        // if `n` is zero
        if (n == 0) {
            return EMPTY;
        }
 
        // split `n` if it is more than 19
        if (n > 19) {
            return Y[(int) (n / 10)] + X[(int) (n % 10)] + suffix;
        }
        else {
            return X[(int) n] + suffix;
        }
    }
 
    // Function to convert a given number (max 9-digits) Longo words
    public static String convert(long n)
    {
        StringBuilder res = new StringBuilder();
 
        res.append(convertToDigit((n / 1000000000) % 100, "Billion "));
 
        res.append(convertToDigit((n / 10000000) % 100, "Crore "));
 
        res.append(convertToDigit(((n / 100000) % 100), "Lakh "));
 
        res.append(convertToDigit(((n / 1000) % 100), "Thousand "));
 
        res.append(convertToDigit(((n / 100) % 10), "Hundred "));
 
        if ((n > 100) && (n % 100 != 0)) {
            res.append("and ");
        }
 
        // add digits at ones and tens place
        res.append(convertToDigit((n % 100), ""));
 
        return res.toString().trim()
                .replace(", and", " and")
                .replaceAll("^(.*),$", "$1");        // remove trailing comma
    }
}
