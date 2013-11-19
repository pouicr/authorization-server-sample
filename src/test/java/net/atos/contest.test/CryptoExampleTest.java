/*
****************************************************
* Atos Worldline -- Global Platforms and Solutions

* Atos Worldline is an Atos company
* All rights reserved
*
*****************************************************
*/
package net.atos.contest.test;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CryptoExampleTest {

    @Test
    public void example() throws IOException {
        String sCurrentLine;
        BufferedReader br = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("authorizationTest.csv")));
        System.out.println("###############");
        while ((sCurrentLine = br.readLine()) != null) {
            String[] data = sCurrentLine.split(",");
            StringBuilder sb0 = new StringBuilder();
            sb0.append(data[0]);
            sb0.append(data[1]);
            sb0.append(data[2]);
            sb0.append(data[3]);

            String cryptogram = crypt(sb0.toString());
            System.out.println(cryptogram);
        }
    }

    public String crypt(String input) {
        System.out.println(input);
        System.out.println("input as byte : ");
        byte[] bigArray = input.getBytes();
        for (byte b : bigArray) {
            System.out.print(b);
        }
        int chunkNb = bigArray.length / 8 + (bigArray.length % 8 > 0 ? 1 : 0);
        List<byte[]> list = new ArrayList<byte[]>();
        byte[] chunk;

        System.out.println();
        //divide array in 8 sized chunks
        for (int i = 0; i < chunkNb; i++) {
            chunk = new byte[8];
            if ((bigArray.length - i * 8) < 8) {
                System.arraycopy(bigArray, i * 8, chunk, 0, bigArray.length - i * 8);
            } else {
                System.arraycopy(bigArray, i * 8, chunk, 0, 8);
            }
            list.add(chunk);
            System.out.print("chunks " + i + " : ");
            for (byte b : chunk) {
                System.out.print(b);
            }
            System.out.println();
        }


        // xor
        byte[] result = list.get(0).clone();
        for (int bitCpt = 0; bitCpt < 8; bitCpt++) {
            for (int chunkCpt = 1; chunkCpt < chunkNb; chunkCpt++) {
                result[bitCpt] = (byte) (result[bitCpt] ^ list.get(chunkCpt)[bitCpt]);

            }
        }
        StringBuilder sb = new StringBuilder();
        System.out.println("byte result");
        for (byte aResult : result) {
            System.out.print(aResult);
            System.out.print(";");
            sb.append(toHex(aResult));
        }
        System.out.println();
        return sb.toString();
    }

    public static String toHex(byte a) {
        String s = "00" + Integer.toHexString(a);
        return s.substring(s.length() - 2);
    }
}
