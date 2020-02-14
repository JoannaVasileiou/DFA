/*
Author: Joanna Vasileiou
Date: 3/2/2020
                            DFA
Reading from file "dfa.txt" the properties for the automato
Enter the word from keyboard and check if "accepted" or "rejected".

dfa.txt

3       // summary of alphabet           
0 1     // alphabet
0       // first state
0 1     // final states
0 1 1   // path of states: q0 goes to q1 when it reads 1. The first and the third number are states
0 0 0
1 1 2   // q1 goes to q2 when it reads 1 the same things under
1 0 0
2 1 2
2 0 2

 */

package dfaexercise;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author DELL
 */
public class DFAExercise {
    
private String numstates;    
private String[] startstate;
private static int path[][];
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException  {
        
        Scanner input = null;       //create a Scanner object
        input = new Scanner(System.in);

        System.out.print("*******************************************\n");
        System.out.println("\033[1m** Deterministic Finite Automaton (DFA) **  \033[0m ");
         System.out.print("*******************************************\n");
        
        
       File dfa = importDFA();    // user provides fileName
       boolean flag=false;
       
       try(BufferedReader br = new BufferedReader(new FileReader(dfa)))
       {   
        //---- Reading line by line from the file ---//
           
        String numstates =  br.readLine();               //input number the states of dfa   
        String alphabet[] = br.readLine().split ( " " ); //input the alphabet
        String startstate = br.readLine();               //input the start state 
        String finalstates[]=br.readLine().split(" ");   //input the final states
         
        
        //--- make final states and put it in table ---//
          
        int lengthfinalstate = finalstates.length;
        char[] f_finalstates = new char[lengthfinalstate]; 
        for (int i = 0; i < lengthfinalstate; i++) { 
            f_finalstates[i] = finalstates[i].charAt( 0 ); 
        } 
        
            
        //--- make ch_alphabet and put it in table ---//
        
        int lengthalphabet = alphabet.length; //put the alphabet length 
        char[] ch_alphabet = new char[lengthalphabet]; 
        for (int i = 0; i < lengthalphabet; i++) { 
            ch_alphabet[i] = alphabet[i].charAt( 0 ); 
        } 
        
        //--- Read the states and char from file example 001 ---//
        
      int numstatesint= Integer.parseInt(numstates); //convert string to int, numbers of states

     	path= new int [numstatesint][lengthalphabet];
         for ( int i = 0; i < (numstatesint * lengthalphabet); i++ ){           
            String pathstr[] = br.readLine().split ( " " );
            int first = Integer.parseInt ( pathstr[0] );
            int second = Integer.parseInt ( pathstr[2] );
            for ( int t = 0; t < lengthalphabet; t++ )	{
		if ( pathstr[1].charAt( 0 ) == ch_alphabet[t] )	{
		path[first][t]=second;
                break;
		}
            }          
	}

        br.close(); //close bufferreader
        
        //---- Input String from keyboard ---//
        
        String inputString = getString(input); 
        char[] tab = inputString.toCharArray(); //make table with char charArray

        
       //-- Check the String with DFA --//

    //final states convert to integer
  int [] ffinal=new int[lengthfinalstate];  
  for (int i = 0; i < lengthfinalstate; i++) { 
       ffinal[i] =Integer.parseInt(String.valueOf(f_finalstates[i]));; 
    } 
 
          int nextstate = 0;
          for (int index = 0; index < tab.length; index++){
             for ( int t = 0; t < lengthalphabet; t++ )	{
		if ( tab[index] == ch_alphabet[t] )	{ 
                    if(index==0){ 
                        nextstate= path[0][t];
                    }else{ 
                        nextstate=path[nextstate][t];} 
		}
               }
          
            if((index+1)==tab.length){
                for (int i = 0; i < lengthfinalstate; i++) {
                    if(nextstate==ffinal[i]){ flag=true;}
                    if((nextstate!=ffinal[i])&& flag!=true) {flag=false;}
                  } 
               }             
            }
          

       }catch (IOException ioe1){
        
        System.out.println("This file is invalid or isn't formatted properly.");
        }
       
        if(flag==true)
        { System.out.println("\n\tACCEPT ");}
        else 
        { System.out.println("\n\tREJECT ");}
        
        input.close();
        System.out.println("\nGood Bye... ");
    }



//---- importDFA function -----//
    
public static File importDFA(){
    
	File dfa = new File("dfa.txt");

	try{ BufferedReader br = new BufferedReader(new FileReader(dfa));            
                
	} catch (FileNotFoundException fnf1){
		System.out.println("Your file could not be found. Please input the path of your dfa file.");
		Scanner input = new Scanner(System.in);
		dfa = new File(input.next());
	}
	return dfa;
	}


//--- getString for keyboard ---//

protected static String getString(Scanner input) {
        System.out.print("\nPlease enter a string to test: ");
        return input.nextLine();
       
    }


}
