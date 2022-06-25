/**
*<h1>sequential</h1>
*@ sequential.java program that uses sequential method to get work out the data smoothed output
*@ author Zenan Shang
*@ version 1.0
*@ since 19-08-21
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Arrays;

public class Sequential {

/**
*holds startTime the time of the clock is set to 0
*/
	static long startTime = 0;
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
		Sequential sequential = new Sequential();
		Float[] arrNumbers, arrOutput;
		
		String fileName = args[0];
		int filterSize = Integer.parseInt(args[1]); 
		String outputFileName = args[2];
		
		try { //Checking if the files exist
			File file = new File (fileName);
			Scanner fileScanner = new Scanner (file);

			arrNumbers = sequential.readFile(fileScanner, filterSize); //read from file
			arrOutput = sequential.dataSmooth(arrNumbers, filterSize); //get output
			sequential.writeToFile(arrOutput, outputFileName); //write output to file
			
			// The codes I used to calculate my timing the data smoothing process
/*			for (int i = 0; i <= 24; i++) {        
			   tick(); // start timing
			   arrOutput = sequential.dataSmooth(arrNumbers, filterSize);		
			   float time = tock(); //end timing
          		   if ((i==0) || (i==4) || (i==9) || (i==14) || (i==19) || (i==24))
			      System.out.println(Integer.toString(i+1) + ": " + time);
			   sequential.writeToFile(arrOutput, outputFileName);
			}*/

			fileScanner.close();
		
		}

		catch(FileNotFoundException e) { //print error message if the input files cannot be found
			System.err.println("File cannot be found");
		}
		scanner.close();
	}
	
/** 
*this method is for start timing
*This is the method for start timing
*@return nothing
*/
	private static void tick() { 
		startTime = System.nanoTime();		 
	}
	   
/** 
*this method for end timing
*This is the method for end the timing
*@return the timing of the process
*/
	private static float tock() {
		return (System.nanoTime() - startTime) / 1000000.0f;
	}
	
/** 
*this method for using an array, sort them into tempArrays and find median then write the median into an output array and return it
*@param output the array that will return the final answer
*@param tempArray the array that will store the part of the array that is used to be smoothed
(@param arr the input array
*@size the filter size for data smoothing
*@return output the array that stores the output of the data after datasmoothing
*/
   private Float[] dataSmooth (Float[] arr, int size) {
		Float[] output = new Float[arr.length];
		Float[] tempArray = new Float[size];
		
		for (int i = 0; i < arr.length; i++) { 
			
			if ( ((size-1)/2 <= i) && (i < arr.length - (size-1)/2) ){
				for (int j = 0; j < size; j++) {
					tempArray[j] = arr[i - (size-1)/2 + j]; // Creating a temporary array to store the filter size samples for time smoothing
				}
				Arrays.sort(tempArray);
				output[i] = tempArray[(size-1)/2]; // adding the data that is processed to the output array
			} 
			else {
				output[i] = arr[i]; // if the data is at the border
			}
		}
		return output;
	}
	
/** 
*this method for reading files
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
*this method for writing to the text files
*@param line, the line that is to be outputed in the text file
*@param filename, the file name in string
*@exception IOException IOException On input error.
*@see IOException
*/
	private void writeToFile (Float[] arr, String fileName) {
		try {
			File file = new File(fileName); // creating a new text file if the output text files do not exist
			if (file.createNewFile()) {
				FileWriter myWriter = new FileWriter (fileName);
				myWriter.write(Integer.toString(arr.length) + "\n");
				for (int i = 0; i < arr.length; i++) {
					String line = Integer.toString(i) + " " + Float.toString(arr[i]).replace(".", ",") + "\n";
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
					String line = Integer.toString(i) + " " + Float.toString(arr[i]).replace(".", ",") + "\n";// replacing the "." with ","
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