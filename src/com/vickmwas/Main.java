package com.vickmwas;

import java.math.BigInteger;
import java.util.*;

/**
 *
 * @author Victor
 *
 * Java Implementation of the RSA Algorithm.
 *
 * */
public class Main {
    private static List<Integer> primes;
    private static int p,q;
    private  static BigInteger e;
    private  static BigInteger d;

    public static void main(String[] args) {
        BigInteger n, phi;

        generatePrimeNumbers();

        selectRandomPandQ();

        //generate n = pq
        n = new BigInteger(String.valueOf(p * q));

        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("n(p,q) = " + n);



        // generate phi = (p-1)(q-1)
        phi = new BigInteger(String.valueOf((p - 1) * (q - 1)));
        System.out.println("phi = (p-1)(q-1) = " + phi);




        // generate e, using the formula GCD(e,n) = 1, using the constraint 1 < e < phi
        for (int i = 5; i < phi.intValue(); i++){
            if (GCD.get(i , n.intValue()) == 1){
                e = new BigInteger(String.valueOf(i));
            }
        }

        System.out.println("\ngcd(" + e + "," + n +") = 1");
        System.out.println("e = "  + e);




        //generate d, such that d*e mod (phi) = 1.
        //This is done by looping between 1 and 1000000, checking for values that satisfy that condition.
        for (int i = 1; i < 1000000; i++){
            if ((i * e.intValue()) % phi.intValue() == 1){
                d = new BigInteger(String.valueOf(i));
                break;
            }
        }

        System.out.println("d = " + d);




        //Input the text to be encrypted
        System.out.print("\n\nEnter text to encrypt : ");
        String stringToEncrypt = new Scanner(System.in).nextLine();



        //convert each character to it's ASCII equivalent
        String[] charactersInString = stringToEncrypt.split("");
        List<Integer> asciiCharactersList = new ArrayList<>();
        for (String s : charactersInString)
            asciiCharactersList.add((int) s.charAt(0));



        //Encrypt every character in the ASCII array
        List<BigInteger> encryptedCharacters = new ArrayList<>();
        for (int ch : asciiCharactersList) {
            encryptedCharacters.add(encryptInteger(ch,e, n));
        }

        System.out.print("Encrypting " + stringToEncrypt + "  = ");
        for (BigInteger encryptedChar : encryptedCharacters) System.out.print( encryptedChar + " ");




        //Decrypt every character in the ASCII array, printing it out
        System.out.print("\n\nDecrypting....\nThe Decrypted string is = ");
        for (BigInteger encryptedChar : encryptedCharacters){
            int decryptedChar = decryptInteger(encryptedChar, d, n).intValue();
            char originalCh = (char) decryptedChar;
            System.out.print(originalCh);

        }

    }

    //function to encrypt, using the formula :
    //      encrypted = C^e mod n
    private static BigInteger encryptInteger(int character, BigInteger e, BigInteger n){
        return new BigInteger(character+"").modPow(e,n);
    }

    //function to decrypt, using the formula :
    //      decrypted = encrypted^d mod n
    private static BigInteger decryptInteger(BigInteger encrypted, BigInteger d, BigInteger n){
        return encrypted.modPow(d,n);
    }


    /**
     * Function to generate prime numbers between 1 and 100
     * */
    private static void generatePrimeNumbers(){
        primes = new ArrayList<>();

        for (int i = 1; i <= 100; i++){
            boolean isPrime = true;
            for(int check = 2; check < i; ++check) {
                if(i % check == 0) {
                    isPrime = false;
                }
            }
            if (isPrime) primes.add(i);
        }

    }

    /**
     * Function to select a random P and Q, from the generated prime numbers array.
     * */
    private static void selectRandomPandQ(){
        int random = new Random().nextInt(primes.size()-2);
        p = primes.get(random + 2);
        if (random == primes.size() - 1)
            q = primes.get(random-1+2);
        else
            q = primes.get(random+1+2);
        }


    /**
     * Class used to find the GCD between any 2 numbers.
     * */
    static class GCD {
        static int get(int a, int b){
            int gcd = 1;
            for (int i = 1; i <= a && i <= b; ++i)
                if (a%i == 0 && b%i == 0) gcd = i;
            return gcd;
        }
    }
}
