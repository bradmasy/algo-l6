package lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Lab 6: Hashing.
 *
 * @name: Bradley Masciotra
 * @student_number: A01247718
 * @set: G
 * @date: October 26th 2022
 * @purpose:
 * To read a given text file of names and create a hashtable based on several hashing functions provided and
 * record collision data.
 */
public class Lab5 {

    final static public int EXIT              = -1;
    final static public String[] FILENAMES    = {"37_names.txt","333_names.txt","5163_names.txt"};
    final static public int RESET             = 0;
    final static int ASCII_CONVERSION         = 64;
    final static int SECRET_SHIFT             = 6;
    final static int VALUE_SHIFT              = 1;
    final static int H2_POWER                 = 26;
    final static int AMOUNT_OF_HASH_FUNCTIONS = 3;
    final static int DOUBLE_TABLE             = 2;
    final static int QUAD_TABLE               = 5;
    final static int TENTH_TABLE              = 10;

    /**
     * Prints the hash.
     *
     * @param hashTable the hashtable whose contents we are printing.
     */
    public static void printHash(final String[] hashTable){

        for(int i = 0; i < hashTable.length; i++){
            System.out.println("[" + i +"]: " + hashTable[i]);
        }
    }

    /**
     * Creates a hashcode aka index for the string being mapped.
     *
     * @param str the string being mapped.
     * @param mod the mod being applied.
     * @return the hashed index.
     */
    public static int CreateHashCode3(final String str,final int mod){
        char[] strToChar = str.toCharArray();
        int val          = 0;

        for (char c : strToChar) {
            c   <<= SECRET_SHIFT;
            val  += c;
            val <<= VALUE_SHIFT;
        }
        return val % mod;
    }

    /**
     * Creates a hashcode aka index for the string being mapped.
     *
     * @param str the string being mapped.
     * @param mod the mod being applied.
     * @return the hashed index.
     */
    public static int CreateHashCode1(final String str,final int mod){
        char[] strToChar = str.toCharArray();
        int val          = 0;

        for (char c : strToChar) {
            int charVal = (int)c - ASCII_CONVERSION;
            val        += charVal;
        }
        return val % mod;
    }

    /**
     * Creates a hashcode aka index for the string being mapped.
     *
     * @param str the string being mapped.
     * @param mod the mod being applied.
     * @return the hashed index.
     */
    public static int CreateHashCode2(final String str, final int mod){
        char[] strToChar = str.toCharArray();
        double val       = 0;
        int iVal         = 0;

        for (char c : strToChar) {
            int charVal     = (c - ASCII_CONVERSION);
            double multiply = Math.pow(H2_POWER,iVal);
            val            += (charVal * multiply);
            iVal++;
        }
        return (int) (val % mod);
    }

    /**
     * Maps the location of the value to an index to the hashtable.
     *
     * @param index the index to map the value to.
     * @param hashTable the hashtable we are mapping.
     * @param value the value being set in the hashtable.
     */
    public static int linearHashData(int index,final String[] hashTable,final String value){
        int collisionCount = 0;
        if (hashTable[index] != null) {
            collisionCount++;
            while (hashTable[index] != null) {
                if(index == hashTable.length - 1){ // end of our table, reset the index
                    index = RESET;
                } else {
                    index++;
                }
            }
        }
        hashTable[index] = value;
        return collisionCount;
    }

    /**
     * Creates the table based on the hash code derived by the function call provided.
     *
     * @param names an array of names.
     * @param mod the mod we are applying to our hash function.
     * @param table the hashtable we are distributing the data into.
     * @param functionCall represents which hashcode algorithm we are applying to our given name.
     */
    public static void createTable(final String[] names, final int mod,final String[] table, final int functionCall){
        int collisionCount = 0;

        for (String name : names) {
            int index = 0;
            switch(functionCall){
                case 0:
                    index = CreateHashCode1(name, mod);
                    break;
                case 1:
                    index = CreateHashCode2(name,mod);
                    break;
                case 2:
                    index = CreateHashCode3(name,mod);
                    break;
            }
            collisionCount += linearHashData(index, table, name);
        }

        System.out.println("------------Collision Count Table[" + functionCall + "]: " + collisionCount);
    }

    /**
     * Processes the names from the file.
     *
     * @param names an array of string names.
     */
    public static void processFile(final String[] names){

        int mod = names.length;

        for(int i = 0; i < AMOUNT_OF_HASH_FUNCTIONS; i++){
            String[] H1hashTableN = new String[names.length];
            String[] H1hashTable2N = new String[names.length * DOUBLE_TABLE];
            String[] H1hashTable5N = new String[names.length * QUAD_TABLE];
            String[] H1hashTable10N = new String[names.length * TENTH_TABLE];

            createTable(names,mod,H1hashTableN,i);
            createTable(names,(mod * DOUBLE_TABLE),H1hashTable2N,i);
            createTable(names,(mod * QUAD_TABLE),H1hashTable5N,i);
            createTable(names,(mod * TENTH_TABLE),H1hashTable10N,i);
        }
    }

    /**
     * Reads the file.
     *
     * @param filename the filename we are reading.
     * @throws FileNotFoundException if the file is not found.
     */
    public static void readFile(String filename) throws FileNotFoundException{
        File f = new File(filename);
        String[] names;
        if(!f.exists()){
            System.exit(EXIT);
        } else {

                Scanner f_scan = new Scanner(f);
                names = f_scan.nextLine().split(",");
                processFile(names);
            }
    }

    public static void main(String[] args){

        for (String filename : FILENAMES) {
            try {
                System.out.println("Analyzing Text File: " + filename);
                readFile(filename);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
