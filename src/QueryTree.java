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

// Some headers for the methods. 
import java.io.*;
import java.util.Scanner;


public class QueryTree // The begging of QueryTree methods.
{
    //overall root of QueryTree
    private QueryTreeNode overallRoot;
    //current location in QueryTree (as per the UI client class)
    private QueryTreeNode current;

    // Creates a QueryTree with a single, default item

    public QueryTree(){
        overallRoot = new QueryTreeNode("computer", null, null);// This will have the root of the root of the tree to be computer and the left right Null. 
        //starting location
        current = overallRoot; // Set the root to current which is a node tree 
    }

    // Checks if there is another question in QueryTree

    public boolean hasNextQuestion()
    {
        return !current.isLeaf(); // Check if the tree do not have the final guess and return.
    }

    // Returns next question in QueryTree

    public String nextQuestion()
    {
        if(current.isLeaf()) //Check the leaf node what it has in the next node.
        {
            throw new IllegalStateException();
        }
        return current.data;// return the data was found in the node current.
    }

    //Updates current location in QueryTree according user's response

    public void userResponse(char input){
        //cannot update location once final guess is reached
        if(current.isLeaf()){
            throw new IllegalArgumentException();
        }
        if(Character.toLowerCase(input) == 'y')// this will check for the answer entered and convert the letter to lower case.
        {
            current = current.yes;// Take the right node of the current tree node and set it to current node
        }
        else if(Character.toLowerCase(input) == 'n')// this will check for the answer entered and convert the letter to lower case.
        {
            current = current.no;// Take the left node of the current tree node and set it to current node
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    //Returns the final guess based on all the answers so far

    public String finalGuess(){
        //can't return a question as a final guess
        if(!current.isLeaf())
        {
            throw new IllegalStateException();
        }
        return current.data; // This will return the data node of the current node tree.
    }

    // Updates QueryTree with user's input if final guess is not correct

    public void updateTree(String q, String item, char input)// This methods will update the tree it take two string and char value. 
    {
        if(!current.isLeaf()) //can't return a question as a final guess
        {
            throw new IllegalStateException();
        }
        String currVal = current.data;// it take the data field of the current node and set it to the currVale 
        current.data = q;// Set the q string to the data field of the root node 
        if(Character.toLowerCase(input) == 'y')// This will covert the input to lower case letter 
        {
            current.no = new QueryTreeNode(currVal, null, null);// it create a node with currval the root and left right node is null and set it to the left node of the main tree 
            current.yes = new QueryTreeNode(item, null, null);// it create a node with string item the root and left right node is null and set it to the right node of the main tree
        }
        else if(Character.toLowerCase(input) == 'n')// This will covert the input to lower case letter
        {
            current.yes = new QueryTreeNode(currVal, null, null);// it create a node with currval the root and left right node is null and set it to the left node of the main tree 
            current.no = new QueryTreeNode(item, null, null);// it create a node with string item the root and left right node is null and set it to the right node of the main tree      
            }
        else{
            throw new IllegalArgumentException();
        }
        reset(); // Here i call rest methods. 
    }

    // Resets current location to that of overall root

    public void reset()
    {
        current = overallRoot;
    }

    // Writes QueryTree out to a provided File

    public void writeOut(File f) throws IOException{
        if(!f.exists() || !f.canWrite())// This will return true if and only if the file or directory denoted by this abstract pathname exists; false otherwise or true if and only if the file system actually contains a file denoted by this abstract pathname and the application is allowed to write to the file; false otherwise.
        {
            throw new IOException("File either doesn't exist or can't be written to.");
        }
        if(overallRoot != null)// Will check if the root do not equal null.
        {
            writeOut(new PrintStream(f), overallRoot); // Here it take the root and it Creates a new print stream, without automatic line flushing
        }
    }

    // Recursive method that writes out the QueryTree to a File using provided PrintStream

    private void writeOut(PrintStream output, QueryTreeNode current)
    {
        if(current.isLeaf())// This is statement check if the is it the final guess and pass it to the current tree node 
        {
        	
            output.println("A:");// This will put to the stream the answer and print it the passed it to the printstream.
            output.println(current.data);// pass the data field to the current and print it the passed it to the printstream. 
        }
        else// this else will print the question then take the data of current and print it also take the left right nodes passed it to current and print them to the file. 
        {
            output.println("Q:");
            output.println(current.data);
            writeOut(output, current.yes);
            writeOut(output, current.no);
        }
    }

    // Reads a File and sets up QueryTree accordingly

   
	public void readIn(File f) throws IOException{
        if(!f.exists() || !f.canRead())// This if statement will make sure true if and only if the file or directory denoted by this abstract pathname exists; false otherwise true if and only if the file specified by thisabstract pathname exists and can be read by the application; false otherwise
        {
            throw new IOException("File either doesn't exist or can't be read.");
        }
        Scanner file = new Scanner(f); // This is a scanner name file 
        if(!file.hasNext()) // This if statement to make sure there is next line in input. 
        {
            throw new IllegalArgumentException("Empty File.");
        }
        overallRoot = readIn(file); // This will call the method again and set it to the overall root. 
        current = overallRoot; // set the overall root to current node 
    }

    // Recursive method that uses a provided Scanner to read in a File and set up QueryTree accordingly

    private QueryTreeNode readIn(Scanner file)// Create scanner file method. 
    {
        if(!file.hasNextLine())//Will check the the file if has next line if not return null..
        {
            return null; // This will return null if the file has no next line. 
        }
        String type = file.nextLine().trim();// This will set the next line in the file without and white space
        boolean isQ = type.equals("Q:");// This will check if the type equals to question and set it to boolean variable
        if(!isQ && !type.equals("A:"))// This if statement check for and exceptions 
        {
            throw new IllegalArgumentException("Invalid File."); //This will throw any exception if there is illegal file 
        }
        if(!file.hasNextLine())// This if statement check for and exceptions 
        {
            throw new IllegalArgumentException("Incomplete File."); //This will throw any exception if there is illegal  incomplete file
        }
        String phrase = file.nextLine();// check for the next line for the file and set it to the string phrase.
        if(!isQ)// if isQ equal to false will return the line it found as node with left right nodes null.. 
        {
            return new QueryTreeNode(phrase, null, null);
        }
        return new QueryTreeNode(phrase, readIn(file), readIn(file));// returning the string and the two scanners and return them.
    }

    //Node of a QueryTree

    private class QueryTreeNode{
        //a question or a final guess
        public String data;
        //objects that answer 'no' to question
        public QueryTreeNode no;
        //objects that answer 'yes' to question
        public QueryTreeNode yes;

        // Creates a QueryTreeNode

        public QueryTreeNode(String data, QueryTreeNode yes, QueryTreeNode no) //QueryTreeNode no, QueryTreeNode yes)// initializing the right left nodes and the data field.
        {
            this.data = data; // This is the data variable 
            this.no = no; // This is the no variable 
            this.yes = yes; // This is the yes variable 
        }

        // Checks if a QueryTreeNode is a final guess

        public boolean isLeaf(){
            return yes == null && no == null;
        }
    }
}