package com.jingyuyao.shortener.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides static functions to "hash" numbers to strings and vice versa.
 *
 * <see>http://stackoverflow.com/a/561704</see>
 */
public class IdEncoder {
    private static char[] ALPHABET =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static int BASE = ALPHABET.length;
    private static Map<Character, Integer> ALPHABET_REVERSE = new HashMap<>();
    static {
        for (int i = 0; i < BASE; i++) {
            ALPHABET_REVERSE.put(ALPHABET[i], i);
        }
    }

    /**
     * Encode a number as a string.
     * @param number the number to be encoded
     * @return the encoded string
     */
    public static String encode(int number) {
        StringBuilder builder = new StringBuilder();
        while (number != 0) {
            int characterIndex = number % BASE;
            builder.append(ALPHABET[characterIndex]);

            number = number / BASE;
        }
        return builder.reverse().toString();
    }

    /**
     * Decode a string back to its original number
     * @param str the string to be decoded
     * @return the decoded number
     */
    public static int decode(String str) {
        int number = 0;
        for (int i = 0; i < str.length(); i++) {
            char character = str.charAt(i);
            number = number * BASE + ALPHABET_REVERSE.get(character);
        }
        return number;
    }
}
