import java.text.DecimalFormat;
import java.util.Scanner;

public class FloatingPoints {
    static final int UNSIGNED_BITS = 15;  // the bit length minus sign bit
    /**
     * Converts a 16-bit half-precision floating point binary value to its decimal form, with a breakdown of
     * each step used to calculate the result.
     * @param bits a 16 character string consisting of 1 and 0.
     * @param exponentBits the number of bits used for the exponent of the float.
     */
    public static void convertToDecimal(int bits, int exponentBits) {
        String bitsString = Integer.toBinaryString(bits);

        // calculate sign
        int sign = (bits >> UNSIGNED_BITS == 0) ? 1 : -1;  // bit shift 15 spaces to find first bit (0 = +ve, 1 = -ve)
        String signStr = (Integer.toString(sign).equals("1")) ? "+ve" : "-ve";
        System.out.printf("%s has a %s sign%n", bitsString, signStr);

        // calculate exponent
        int exponent;
        if (bits >> UNSIGNED_BITS == 0) {
            // leaves us with [exponentBits] number of bits
            exponent = (bits >> (UNSIGNED_BITS - exponentBits));
        } else {
            // remove the leading sign bit by subtracting 2^[exponentBits] (using some bitwise trickery)
            exponent = (bits >> (UNSIGNED_BITS - exponentBits)) - (0b1 << exponentBits);
        }

        String exponentStr = Integer.toBinaryString(exponent);

        // check exponent has enough leading zeroes
        if (exponentBits > exponentStr.length()) {
            exponentStr = "0".repeat(exponentBits - exponentStr.length()) + exponentStr;
        }

        System.out.printf("%s has a exponent of %s => %d%n",
                bitsString, exponentStr, exponent);

        // calculate biased exponent
        int bias = (int) Math.pow(2, exponentBits - 1) - 1;  // 2^(bit length - 1) - 1
        int biasedExponent = exponent - bias;
        System.out.printf("%s has a bias of %d and biased exponent of %d%n",
                bitsString, bias, biasedExponent);

        // calculate mantissa
        int mantissaLength = UNSIGNED_BITS - exponentBits;
        int mantissa = bits - (bits >> (UNSIGNED_BITS - exponentBits) << (UNSIGNED_BITS - exponentBits));
        String mantissaStr = Integer.toBinaryString(mantissa);

        // check mantissa has enough leading zeroes
        if (mantissaLength > mantissaStr.length()) {
            mantissaStr = "0".repeat(mantissaLength - mantissaStr.length()) + mantissaStr;
        }

        System.out.printf("%s has a mantissa of %s%n", bitsString, mantissaStr);

        // calculate fraction from mantissa
        int power = -1;
        char[] digits = mantissaStr.toCharArray();
        double fraction = 1;  // we will add 2^(power) each time there is a 1 in the digits array

        // for each digit in mantissa: if digit is 1, record the power - otherwise continue to the next digit
        for (char d : digits) {
            if (String.valueOf(d).equals("1")) {
                fraction += Math.pow(2, power);
            }
            power--;
        }
        System.out.println("The fraction is " + fraction);

        // put it all together to get the result
        DecimalFormat resultDF = new DecimalFormat("#.########");
        double resultExponent = Math.pow(2, biasedExponent);
        double result = sign * resultExponent * fraction;
        System.out.printf("%d * 2^%d * %f = %s%n",
                sign, biasedExponent, fraction, resultDF.format(result));
    }

    public static void run() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter a 16-bit floating point binary value:");
        boolean validBits = false;
        int bits = 0;
        while (!validBits) {
            String input = in.nextLine();
            try {
                final int BIT_LENGTH = 16;
                if (input.length() == BIT_LENGTH) {
                    bits = Integer.parseInt(input, 2);
                    validBits = true;
                } else {
                    System.out.printf("A 16 bit value is required (you entered %d), please try again:%n",
                            input.length());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please try again:");
            }
        }

        boolean validExponent = false;
        int exponentBits = 0;
        do {
            try {
                System.out.println("How many exponent bits?");
                exponentBits = Integer.parseInt(in.nextLine());
                if (exponentBits > 0 && exponentBits < UNSIGNED_BITS) {
                    validExponent = true;
                } else {
                    System.out.printf("Exponent must be between 1 and %d, please try again:%n", UNSIGNED_BITS - 1);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please try again:");
            }
        } while (!validExponent);

        convertToDecimal(bits, exponentBits);
    }
}
