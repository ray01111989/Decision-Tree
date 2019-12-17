/*
###############################################################################################
#Name: Rawad Bader
#Course: CSE 223
#Programming Assignment 4
#Synopsis:
# This Java program is a decision tree, that emulates the "20 questions" game...
# and it is not limited to 20 questions. Each tree node holds 2 pointers
# to other tree nodes. One tree node is labeled yes and the other No. Also,
# each Tree node has a flag that checks if it's data is a question or an answer.
#  That is the first line of the file is Q: and under it is the question. Then the next line
# is either Q: or A: with the answer under A: and question under Q:. If the program
# guesses the wrong object it will ask for a yes/no question related to the answer
# thought off, and it will add it to the tree and file accordingly to make sure to ask 
# the question again to get the answer right. I made my code that take argument command line and if 
# you run the default file it will save to that file and read from it. 
# Additional thing is if there is no tree it will create tree in the default file also it will overwrite 
# the default file if you chose to not scan the previous tree in the default file. 
###############################################################################################
 */

// Here is some headers files. 
import java.io.*;
import java.util.*;

public class QueryTreeTest {
    // Determines whether or not to readIn the previous QueryTree

    private static QueryTree toReadIn(QueryTree tree, Scanner input, String filename){
    	try {// throws IOException{
    
        if(validEntryLoop(input, "Do you want to read in the previous tree?").equals("y"))// his to ask the user if they want to scan the previous tree.
        {
        	
            tree.readIn(new File(filename));// This will call the read in the data file method and passed to the tree
        }
    	 }catch(Exception e) // This to catch any exception when we scan the file.
    	{
    	    }
        return tree; // return the tree 
   	
    }

    // Loop that determines whether or not user input is valid

    private static String validEntryLoop(Scanner input, String phrase)
    {
        System.out.print(phrase + " (y/n)? "); // This to print the first message for the user to ask if he want to use the previous tree. 
        
        String affirm = input.nextLine().trim().toLowerCase(); // This will scan if there is a next line and take out the white spaces also convert to lower case then set it to String variable. 
        while(!(affirm.equals("y") || affirm.equals("n")))// while loop to check for yes and no answer. 
        {
            System.out.println("That was an invalid entry.");// If the answer not yes or no then display this message 
            System.out.print(phrase + " (y/n)? ");// This will ask the user to scan from the previous tree. 
            affirm = input.nextLine().trim().toLowerCase();//This will scan if there is a next line and take out the white spaces also convert to lower case then set it to String variable. 
        }
        return affirm; // will return the string we have after the previous method. 
    }

    public static void main(String[] args) throws IOException// main method.
    {
        Scanner input = new Scanner(System.in);// This will take from the stander in the set it to the scanner. 
        QueryTree tree = new QueryTree();// Declare memory for tree type of QuerTree. 
        System.out.println("Welcome to the 20 Questions game.");// Welcome message 
        String filename; // String variable call file name. 
        if(args.length == 0) // Make sure if there is no file declare in the file argument 
        {
        	filename="20ss.txt"; // This will take the default file 
        }
        else {
        	filename=args[0]; // This will scan the file was declare in the file argument. 
        }
        System.out.println(filename);// This will show if the file argument was successfully scanned. 
        tree = toReadIn(tree, input, filename);// this will call the method read in and set it to the tree. 
        System.out.println();

        boolean toRepeat = true;// A variable as boolean 
        while(toRepeat)// While loop for the object when it is guessed wrong 
        {
            System.out.println("Please think of an object for me to guess.");// Message for the user in the begging to think of something 
            while(tree.hasNextQuestion())// While loop to check for the next question 
            {
                tree.userResponse(validEntryLoop(input, tree.nextQuestion()).charAt(0));// Check the user response 
            }
            // This will ask the user if the answer computer guessed was right 
            if(validEntryLoop(input, ("Would your object happen to be " + tree.finalGuess() + "?")).equals("y"))
            {
                System.out.println("Great, I got it right!");
                tree.reset(); // This will call the method rest the tree. 
            }
// 'y');//
            else{
                System.out.print("What is the name of your object? ");// Ask for what is the object if it was not right guess. 
                String obj = input.nextLine().trim(); // Check for next line input 
                System.out.print("Please give a yes/no question that distinguishes between your "+ obj +" and "+ tree.finalGuess()+ " where the answer YES for your "+ obj +"."); // This ask the user for a question for the object he was thinking of. 
                String question = input.nextLine().trim(); // This will check the next line input and set it to question string 
                tree.updateTree(question, obj,  'y');//validEntryLoop(input, "And what is the answer for your object?").charAt(0)); // This will update the tree with the new input. 
            }

            System.out.println();// Print statement just for space 
            toRepeat = validEntryLoop(input, "Do you want to go again?").equals("y");// Ask for another game 
            
       // }
        if(args.length==0) // Check for argument command file if there is one 
        {
        tree.writeOut(new File("20ss.txt"));// write to the default file. 
        }
        else {
        	tree.writeOut(new File(args[0]));// This will write to the file was declared in the argument command line 
        }
        }
        System.out.println("Good Bye!"); // Good bye message 
    }
}