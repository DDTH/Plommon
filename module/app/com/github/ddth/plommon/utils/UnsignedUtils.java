package com.github.ddth.plommon.utils;

import java.math.BigInteger;

/**
 * Unsigned longs and ints utility class.
 * 
 * Note: most of methods in this class are based on Google Guava's
 * UnsignedLongs' and UnsignedInts' source code. I reimplement most of
 * UnsignedLongs' and UnsignedInts' methods in this class so that we do not need
 * to include the whole Guava library as depencency.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.2.0
 */
public class UnsignedUtils {
    private final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    private static final long INT_MASK = 0xFFFFFFFFL;

    /**
     * Max radix: 62
     */
    public final static int MAX_RADIX = digits.length;

    public static final long MAX_VALUE = -1L; // Equivalent to 2^64 - 1
    // calculated as 0xffffffffffffffff / radix
    private static final long[] maxValueDivs = new long[MAX_RADIX + 1];
    private static final int[] maxValueMods = new int[MAX_RADIX + 1];
    private static final int[] maxSafeDigits = new int[MAX_RADIX + 1];
    static {
        BigInteger overflow = new BigInteger("10000000000000000", 16);
        for (int i = Character.MIN_RADIX; i <= MAX_RADIX; i++) {
            maxValueDivs[i] = divide(MAX_VALUE, i);
            maxValueMods[i] = (int) remainder(MAX_VALUE, i);
            maxSafeDigits[i] = overflow.toString(i).length() - 1;
        }
    }

    /**
     * Returns the numeric value of the character {@code c} in the specified
     * radix.
     * 
     * @param c
     * @param radix
     * @return
     * @see #MAX_RADIX
     */
    public static int digit(char c, int radix) {
        for (int i = 0; i < MAX_RADIX && i < radix; i++) {
            if (digits[i] == c) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the digit character.
     * 
     * @param mod
     * @param radix
     * @return
     */
    public static char forDigit(int mod, int radix) {
        if (mod >= 0 && mod < radix && radix >= Character.MIN_RADIX && radix <= MAX_RADIX) {
            return digits[mod];
        }
        return '\0';
    }

    /**
     * A (self-inverse) bijection which converts the ordering on unsigned longs
     * to the ordering on longs, that is, {@code a <= b} as unsigned longs if
     * and only if {@code rotate(a) <= rotate(b)} as signed longs.
     */
    private static long flip(long a) {
        return a ^ Long.MIN_VALUE;
    }

    /**
     * Compares the two specified {@code long} values, treating them as unsigned
     * values between {@code 0} and {@code 2^64 - 1} inclusive.
     * 
     * @param a
     *            the first unsigned {@code long} to compare
     * @param b
     *            the second unsigned {@code long} to compare
     * @return a negative value if {@code a} is less than {@code b}; a positive
     *         value if {@code a} is greater than {@code b}; or zero if they are
     *         equal
     */
    public static int compare(long a, long b) {
        long flipa = flip(a);
        long flipb = flip(b);
        return (flipa < flipb) ? -1 : ((flipa > flipb) ? 1 : 0);
    }

    /**
     * Returns dividend / divisor, where the dividend and divisor are treated as
     * unsigned 64-bit quantities.
     * 
     * @param dividend
     *            the dividend (numerator)
     * @param divisor
     *            the divisor (denominator)
     * @throws ArithmeticException
     *             if divisor is 0
     */
    public static long divide(long dividend, long divisor) {
        if (divisor < 0) { // i.e., divisor >= 2^63:
            if (compare(dividend, divisor) < 0) {
                return 0; // dividend < divisor
            } else {
                return 1; // dividend >= divisor
            }
        }

        // Optimization - use signed division if dividend < 2^63
        if (dividend >= 0) {
            return dividend / divisor;
        }

        /*
         * Otherwise, approximate the quotient, check, and correct if necessary.
         * Our approximation is guaranteed to be either exact or one less than
         * the correct value. This follows from fact that floor(floor(x)/i) ==
         * floor(x/i) for any real x and integer i != 0. The proof is not quite
         * trivial.
         */
        long quotient = ((dividend >>> 1) / divisor) << 1;
        long rem = dividend - quotient * divisor;
        return quotient + (compare(rem, divisor) >= 0 ? 1 : 0);
    }

    /**
     * Returns dividend % divisor, where the dividend and divisor are treated as
     * unsigned 64-bit quantities.
     * 
     * @param dividend
     *            the dividend (numerator)
     * @param divisor
     *            the divisor (denominator)
     * @throws ArithmeticException
     *             if divisor is 0
     * @since 11.0
     */
    public static long remainder(long dividend, long divisor) {
        if (divisor < 0) { // i.e., divisor >= 2^63:
            if (compare(dividend, divisor) < 0) {
                return dividend; // dividend < divisor
            } else {
                return dividend - divisor; // dividend >= divisor
            }
        }

        // Optimization - use signed modulus if dividend < 2^63
        if (dividend >= 0) {
            return dividend % divisor;
        }

        /*
         * Otherwise, approximate the quotient, check, and correct if necessary.
         * Our approximation is guaranteed to be either exact or one less than
         * the correct value. This follows from fact that floor(floor(x)/i) ==
         * floor(x/i) for any real x and integer i != 0. The proof is not quite
         * trivial.
         */
        long quotient = ((dividend >>> 1) / divisor) << 1;
        long rem = dividend - quotient * divisor;
        return rem - (compare(rem, divisor) >= 0 ? divisor : 0);
    }

    /**
     * Returns the unsigned {@code int} value represented by the given decimal
     * string.
     * 
     * @throws NumberFormatException
     *             if the string does not contain a valid unsigned integer, or
     *             if the value represented is too large to fit in an unsigned
     *             {@code int}.
     * @throws NumberFormatException
     *             if the string does not contain a valid unsigned {@code int}
     *             value
     */
    public static int parseInt(String s) {
        return parseInt(s, 10);
    }

    /**
     * Returns the unsigned {@code int} value represented by a string with the
     * given radix.
     * 
     * @param s
     *            the string containing the unsigned integer representation to
     *            be parsed.
     * @param radix
     *            the radix to use while parsing {@code s}; must be between
     *            {@link Character#MIN_RADIX} and {@link #MAX_RADIX}.
     * @throws NumberFormatException
     *             if the string does not contain a valid unsigned {@code int},
     *             or if supplied radix is invalid.
     */
    public static int parseInt(String s, int radix) {
        if (s == null || s.length() == 0 || s.trim().length() == 0) {
            throw new NumberFormatException("Null or empty string");
        }
        long result = parseLong(s, radix);
        if ((result & INT_MASK) != result) {
            throw new NumberFormatException("Input [" + s + "] in base [" + radix
                    + "] is not in the range of an unsigned integer");
        }
        return (int) result;
    }

    /**
     * Returns the unsigned {@code long} value represented by the given decimal
     * string.
     * 
     * @throws NumberFormatException
     *             if the string does not contain a valid unsigned {@code long}
     *             value
     */
    public static long parseLong(String s) throws NumberFormatException {
        return parseLong(s, 10);
    }

    /**
     * Returns the unsigned {@code long} value represented by a string with the
     * given radix.
     * 
     * @param s
     *            the string containing the unsigned {@code long} representation
     *            to be parsed.
     * @param radix
     *            the radix to use while parsing {@code s}
     * @throws NumberFormatException
     *             if the string does not contain a valid unsigned {@code long}
     *             with the given radix, or if {@code radix} is not between
     *             {@link Character#MIN_RADIX} and {@link MAX_RADIX}.
     */
    public static long parseLong(String s, int radix) throws NumberFormatException {
        if (s == null || s.length() == 0 || s.trim().length() == 0) {
            throw new NumberFormatException("Null or empty string");
        }

        if (radix < Character.MIN_RADIX || radix > MAX_RADIX) {
            throw new NumberFormatException("Illegal radix [" + radix + "]");
        }

        int max_safe_pos = maxSafeDigits[radix] - 1;
        long value = 0;
        for (int pos = 0; pos < s.length(); pos++) {
            int digit = digit(s.charAt(pos), radix);
            if (digit == -1) {
                throw new NumberFormatException(s);
            }
            if (pos > max_safe_pos && overflowInParse(value, digit, radix)) {
                throw new NumberFormatException("Too large for unsigned long: " + s);
            }
            value = (value * radix) + digit;
        }

        return value;
    }

    /**
     * Returns true if (current * radix) + digit is a number too large to be
     * represented by an unsigned long. This is useful for detecting overflow
     * while parsing a string representation of a number. Does not verify
     * whether supplied radix is valid, passing an invalid radix will give
     * undefined results or an ArrayIndexOutOfBoundsException.
     */
    private static boolean overflowInParse(long current, int digit, int radix) {
        if (current >= 0) {
            if (current < maxValueDivs[radix]) {
                return false;
            }
            if (current > maxValueDivs[radix]) {
                return true;
            }
            // current == maxValueDivs[radix]
            return (digit > maxValueMods[radix]);
        }

        // current < 0: high bit is set
        return true;
    }

    /**
     * Converts a unsigned long to string.
     * 
     * @param x
     * @return
     */
    public static String toString(long x) {
        return toString(x, 10);
    }

    /**
     * Converts a unsigned int to string.
     * 
     * @param x
     * @return
     */
    public static String toString(int x) {
        return toString(x, 10);
    }

    /**
     * Converts a unsigned long to string for the given radix.
     * 
     * @param x
     * @param radix
     * @return {@link IllegalArgumentException} if {@code radix} is not between
     *         {@link Character#MIN_RADIX} and {@link #MAX_RADIX}.
     */
    public static String toString(long x, int radix) throws IllegalArgumentException {
        if (radix < Character.MIN_RADIX || radix > MAX_RADIX) {
            throw new IllegalArgumentException("Radix [" + radix
                    + "] must be between Character.Min_RADIX and UnsignedUtils.MAX_RADIX");
        }

        if (x == 0) {
            return "0";
        } else {
            char[] buf = new char[64];
            int i = buf.length;
            if (x < 0) {
                // Split x into high-order and low-order halves.
                // Individual digits are generated from the bottom half into
                // which
                // bits are moved continously from the top half.
                long top = x >>> 32;
                long bot = (x & 0xffffffffl) + ((top % radix) << 32);
                top /= radix;
                while ((bot > 0) || (top > 0)) {
                    buf[--i] = forDigit((int) (bot % radix), radix);
                    bot = (bot / radix) + ((top % radix) << 32);
                    top /= radix;
                }
            } else {
                // Simple modulo/division approach
                while (x > 0) {
                    buf[--i] = forDigit((int) (x % radix), radix);
                    x /= radix;
                }
            }
            // Generate string
            return new String(buf, i, buf.length - i);
        }
    }

    /**
     * Converts a unsigned int to string for the given radix.
     * 
     * @param x
     * @param radix
     * @return {@link IllegalArgumentException} if {@code radix} is not between
     *         {@link Character#MIN_RADIX} and {@link #MAX_RADIX}.
     */
    public static String toString(int x, int radix) throws IllegalArgumentException {
        long asLong = x & INT_MASK;
        return toString(asLong, radix);
    }

    public static void main(String[] args) {
        long l = 0xFFFFFFFFFF000000L;
        String sl16 = toString(l, 16);
        String sl34 = toString(l, 34);
        String slmax = toString(l, MAX_RADIX);
        System.out.println(sl16);
        System.out.println(sl34);
        System.out.println(slmax);
        long l16 = parseLong(sl16, 16);
        System.out.println(l == l16);
        System.out.println(compare(l, l16));
        long l34 = parseLong(sl34, 34);
        System.out.println(l == l34);
        System.out.println(compare(l, l34));
        long lmax = parseLong(slmax, MAX_RADIX);
        System.out.println(l == lmax);
        System.out.println(compare(l, lmax));

        System.out.println();

        int i = 0xFFFFF123;
        System.out.println(toString(i, 16));
        System.out.println(toString(i, 34));
        System.out.println(toString(i, MAX_RADIX));
        String si16 = toString(i, 16);
        String si34 = toString(i, 34);
        String simax = toString(i, MAX_RADIX);
        System.out.println(si16);
        System.out.println(si34);
        System.out.println(simax);
        long i16 = parseInt(si16, 16);
        System.out.println(i == i16);
        System.out.println(compare(i, i16));
        long i34 = parseInt(si34, 34);
        System.out.println(i == i34);
        System.out.println(compare(i, i34));
        long imax = parseInt(simax, MAX_RADIX);
        System.out.println(i == imax);
        System.out.println(compare(i, imax));
    }
}
