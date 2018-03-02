package RKSir;
/*
 * Copyright 2011 David Simmons
 * http://cafbit.com/entry/implementing_des
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Super-slow DES implementation for the overly patient.
 *
 * The following resources proved valuable in developing and testing
 * this code:
 *
 * "Data Encryption Standard" from Wikipedia, the free encyclopedia
 * http://en.wikipedia.org/wiki/Data_Encryption_Standard
 *
 * "The DES Algorithm Illustrated" by J. Orlin Grabbe
 * http://orlingrabbe.com/des.htm
 *
 * "DES Calculator" by Lawrie Brown
 * http://www.unsw.adfa.edu.au/~lpb/src/DEScalc/DEScalc.html
 *
 * April 6, 2011
 *
 * @author David Simmons - http://cafbit.com/
 *
 */
public class DESown {

    private long c[] = new long[17];
    private long d[] = new long[17];
    private long k_plus[] = new long[17];

    /**
     * PC1 Permutation.  The supplied 64-bit key is permuted according
     * to this table into a 56-bit key.  (This is why DES is only a
     * 56-bit algorithm, even though you provide 64 bits of key
     * material.)
     */
    private static final byte[] PC1 = {
            57, 49, 41, 33, 25, 17, 9,
            1,  58, 50, 42, 34, 26, 18,
            10, 2,  59, 51, 43, 35, 27,
            19, 11, 3,  60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7,  62, 54, 46, 38, 30, 22,
            14, 6,  61, 53, 45, 37, 29,
            21, 13, 5,  28, 20, 12, 4
    };

    /**
     * PC2 Permutation.  The subkey generation process applies this
     * permutation to transform its running 56-bit keystuff value into
     * the final set of 16 48-bit subkeys.
     */
    private static final byte[] PC2 = {
            14, 17, 11, 24, 1,  5,
            3,  28, 15, 6,  21, 10,
            23, 19, 12, 4,  26, 8,
            16, 7,  27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };

    /**
     * Input Permutation.  The message block is permuted by this
     * permutation at the beginning of the algorithm.
     */
    private static final byte[] IP = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9,  1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    public long binStringToLong (String binString){
        // Replace the spaces
        binString = binString.replace(" ","");
        return Long.parseLong(binString,2);
    }

    public long hexStringToLong (String binString){
        // Replace the spaces
        binString = binString.replace(" ","");
        return Long.parseLong(binString,16);
    }


    private void displayLongValues(long disp){
        System.out.println("Long value: "+disp);
        System.out.println("Hex value: "+Long.toHexString(disp));
    }

    /**
     * Permute an input value "src" of srcWidth bits according to the
     * supplied permutation table.  (Note that our permutation tables,
     * supplied by Wikipedia, start counting with the left-most bit as
     * "1".)
     */
    private static long permute(byte[] table, int srcWidth, long src) {
        long dst = 0;
        for (int i=0; i<table.length; i++) {
            int srcPos = srcWidth - table[i];
            dst = (dst<<1) | (src>>srcPos & 0x01);
        }
        return dst;
    }

    /**
     * c[i] and d[i] are 28 bits instead of long's 64 bit.
     * So C leftshifted by 28 bits, keeping first 8 bits of a long intact.
     * Since C is 28 bit long the first 4bits are always 0,
     * these get added to the leftshift and hence there are 8 0's int he beginning.
     * D's first bit is at the same position as the first 0 of C. (ie 33rd bit of long)
     * Now we can perform OR to get the final key again.
     */
    private long getKPlus(long c, long d) {
        long joined = (c&0xFFFFFFFFL)<<28 | (d&0xFFFFFFFFL);
        return joined;
    }


    /**
     * This function generates 16 subkeys from the initial provided keys.
     * Based on the current iteration number, the previous keys are either LShift'd 1 or 2 times.
     * The new subkey is generated from the above operation.
     */
    private void generateSubkeys(long c0, long d0) {
        for (int i = 1; i <= 16; i++) {
            if ((i == 1) || (i == 2) || (i == 9) || (i == 16) ){
                // rotate by 1 bit
                c[i] = ((c[i-1]<<1) & 0x0FFFFFFF) | (c[i-1]>>27);
                d[i] = ((d[i-1]<<1) & 0x0FFFFFFF) | (d[i-1]>>27);
            }
            else {
                // rotate by 2 bits
                c[i] = ((c[i-1]<<2) & 0x0FFFFFFF) | (c[i-1]>>26);
                d[i] = ((d[i-1]<<2) & 0x0FFFFFFF) | (d[i-1]>>26);
            }
            // join the two keystuff halves together.
            long cd = (c[i]&0xFFFFFFFFL)<<28 | (d[i]&0xFFFFFFFFL);
            System.out.println("\n\nGenSubkeys: "+i);
            displayLongValues(cd);

            k_plus[i] = permute(PC2,56,cd);
            System.out.println("Subkeys: "+i);
            displayLongValues(k_plus[i]); // Should display 48-Bit numbers

        }
    }
    private void start() {
        String msg,key;
        long m,disp,l,r,k,ipmsg;

        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter equiv hex");
//        msg = scanner.nextLine();
        msg = "0123456789ABCDEF";
        m = hexStringToLong(msg);
        displayLongValues(m);

        l = m>>32;
        displayLongValues(l);

        r = m<<32;
        displayLongValues(r);

        // Step 1: Create 16 subkeys, each of which is 48-bits long.
        System.out.println("Enter key hex");
//        key = scanner.nextLine();
        key = "133457799BBCDFF1";
        k = hexStringToLong(key);
        displayLongValues(k);

        k_plus[0] = permute(PC1,64,k);
        System.out.println("\nk_plus:");
        displayLongValues(k_plus[0]);

        System.out.println("Splitting keys: ");
        // First half
        c[0] = k_plus[0]>>28;
        // Second half, only final 7 bits are identical (7*f).
        d[0] = (k_plus[0]&0x0FFFFFFF);
        k_plus[0] = getKPlus(c[0],d[0]);
        System.out.println("c: ");
        displayLongValues(c[0]);
        System.out.println("d: ");
        displayLongValues(d[0]);
        displayLongValues(k_plus[0]);


        // Now to get the remaining 16 subkeys
        generateSubkeys(c[0],d[0]);

        // Step 2: Encode each 64-bit block of data.
        ipmsg = permute(IP,64,m);
        System.out.println("IP message:");
        displayLongValues(ipmsg);

    }


    public static void main(String[] args) {
        DESown obj1 = new DESown();
        obj1.start();
    }


}
