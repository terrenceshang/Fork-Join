/**
*<h1>ForkJoin!</h1>
*@ ForkJoin.java program that invoke MedianCalculation to get work out the data smoothed output
*@ author Zenan Shang
*@ version 1.0
*@ since 19-08-21
*/

import java.util.concurrent.ForkJoinPool;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class ForkJoin {

/**
*holds the startTime the time of the clock is set to 0
*/
	static long startTime = 0;
   
   ForkJoinPool fjPool = new ForkJoinPool();
/**
*fjMethod Method for invoking MedianCalculation
*/
   public void fjMethod (Float[] arr, int size) {
      fjPool.invoke(new MedianCalculation(arr, 0, arr.length, size));
   }

/**
*This is the main method that read from file, work out the answer and write to the output file
*@param arrNumbers the array that store all the data from the input file
*@param fileName the name of the file
*@param outputFileName the file where I write the final answer to
*@param filterSize the filter size used to work out the data smoothing
*@param arrOutput the array that stores the output after data smoothing
*@return nothing
*@exception FileNotFoundException exception for not finding the file
*@see FileNotFoundException
*@param args[0] is the fileName
*@param args[1] is the filterSize
*@param args[2] is the outputFilename
*/
	public static void main (String[] args) {
		Scanner scanner = new Scanner (System.in);
		ForkJoin forkJoin= new ForkJoin();
		Float[] arrNumbers, arrOutput;
		
		String fileName = args[0];
		int filterSize = Integer.parseInt(args[1]); 
		String outputFileName = args[2];
		
		try { //Checking if the files exist
			File file = new File (fileName);
			Scanner fileScanner = new Scanner (file);
			
			arrNumbers = forkJoin.readFile(fileScanner, filterSize);
/* How I recorded the time  
         for (int i = 0; i <= 24; i++) {       
            tick(); 
            MedianCalculation mc = new MedianCalculation(arrNumbers, 0, arrNumbers.length, filterSize);
            forkJoin.fjMethod(arrNumbers, filterSize);
			   Float time = tock();
            if ((i==0) || (i==4) || (i==9) || (i==14) || (i==19) || (i==24))
			     System.out.println(Integer.toString(i+1) + ": " + time);     
         }    
*/
         //To data smooth using parallel codes
         MedianCalculation mc = new MedianCalculation(arrNumbers, 0, arrNumbers.length, filterSize); //creating MedianCalculation obj
         forkJoin.fjMethod(arrNumbers, filterSize);
         forkJoin.writeToFile(mc.ans, outputFileName); //mc.ans is the result of the dataSmoothing
			
			fileScanner.close();
		}
		
		catch(FileNotFoundException e) { //print error message if the input files cannot be found
			System.err.println("File cannot be found");
		}
		scanner.close();
	}
   
/** 
*this tick program is for start timing
*@return nothing
*/
	private static void tick() { 
		startTime = System.nanoTime();		 
	}
	   
/** 
*this program is  for end timing
*retrieves the time for end the timing
*@return the timing of the process
*/
   private static float tock() {
		return (System.nanoTime() - startTime) / 1000000.0f;
	}
	
/** 
Program for reading files
*This is the method for reading data from a file
*@return ArrNumbers the output after storing all the data in the output file into an array
*@param fileScanner scanner that stores the file
*@param temp the string used to store the lines read from file
*@param arrNumbers the output of the method
*@param count used to help arrNumber to store data 
*/
   private Float[] readFile (Scanner fileScanner, int filterSize) {
		int count = 0;
		Float[] arrNumbers = new Float[Integer.parseInt(fileScanner.nextLine())];
		while (fileScanner.hasNextLine()) {
			String temp = fileScanner.nextLine();
			temp = temp.substring(temp.indexOf(" ") + 1);
			arrNumbers[count] = Float.parseFloat(temp.replace(",", ".")); //replacing the , with . so that java can read it as numbers
			count ++;
		}
		return arrNumbers;
	}
   
/**
*this writeToFile program for writing to the text files
*@param line, the line that is to be outputed in the text file
*@param filename, the file name in string
*@exception IOException IOException On input error.
* @see IOException
*/
   private void writeToFile (Float[] arr, String fileName) {
		try {
			File file = new File(fileName); // creating a new text file if the output text files do not exist
			if (file.createNewFile()) {
				FileWriter myWriter = new FileWriter (fileName);
				myWriter.write(Integer.toString(arr.length) + "\n");
				for (int i = 0; i < arr.length; i++) {
					String line = Integer.toString(i) + " " + Float.toString(arr[i]).replace(".", ",") + "\n";// replacing the "." with ","
					myWriter.write(line);
				}
				myWriter.close();
			} else { 
				FileWriter fwOb = new FileWriter(fileName, false); //clearing the data in the output text file if it exists
		        PrintWriter pwOb = new PrintWriter(fwOb, false);
		        pwOb.flush();
		        pwOb.close();
		        fwOb.close();
		        
				FileWriter myWriter = new FileWriter (fileName); // write data to the text files
				myWriter.write(Integer.toString(arr.length) + "\n");
				for (int i = 0; i < arr.length; i++) {
					String line = Integer.toString(i) + " " + Float.toString(arr[i]).replace(".", ",") + "\n";
					myWriter.write(line);
				}
				myWriter.close();
			}
			
		} catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
		
	}

}