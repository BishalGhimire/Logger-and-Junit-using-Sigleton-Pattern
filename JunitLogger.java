package assignmentLogger;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class JunitLogger {
	Logger logger;
	
	
	@Test
	void testWriteInNewFile() throws IOException {
		// get a random temporary file
		String prefix = "log";
	    String suffix = ".txt";
	    
	    String log_str = "Something";
	    
		File tempFile = File.createTempFile(prefix, suffix, new File("/tmp"));
		
		String fileName = tempFile.getAbsolutePath();
		Logger.setLogFile(fileName);
		
		// create a logger
		logger = Logger.getInstance();
		
		// log something
		logger.log(log_str);
		
		// Close the logger
		logger.close();
		
		System.out.println("File name is "+fileName);
		
		
		
		// check if the file content in the temporary file is as expected.
		BufferedReader br = new BufferedReader(new FileReader(fileName)); 
		  
		  String st; 
		  while ((st = br.readLine()) != null) { 
			  System.out.println("St length: "+st.length());
			  if(st.equals(log_str)) {
				  return;
			  }else {
				  fail("Expected: "+log_str+", got: "+st);
			  }
		  }
		  
		tempFile.delete();
		
		fail("Error");
	}
	
	@Test
	void testWriteInExistingFile() throws IOException {
		// get a random temporary file
		String prefix = "log";
	    String suffix = ".txt";
	    
	    String preexisting_str = "Preexisting log";
	    String log_str = "Logger String";
	    
		File tempFile = File.createTempFile(prefix, suffix, new File("/tmp"));
		
		String fileName = tempFile.getAbsolutePath();
		// write something
		BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));
		System.out.println(tempFile);
		out.write(preexisting_str+"\n");
		out.close();
		Logger.setLogFile(fileName);
		
		// create a logger
		logger = Logger.getInstance();
		
		// log something
		logger.log(log_str);
		
		// Close the logger
		logger.close();
		
		System.out.println("File name is "+fileName);
		
		
		
		// check if the file content in the temporary file is as expected.
		BufferedReader br = new BufferedReader(new FileReader(fileName)); 
		  
		  String st;
		  int index = 0; 
		  while ((st = br.readLine()) != null) { 
			  System.out.println(st + "and "+  preexisting_str);
			  if(index == 0) {
				  assert(st.equals(preexisting_str));
				  index++;
			  }else {
				  assert(st.equals(log_str));
				  index++;
			  }
			  
			  if(index > 2) fail("Too many lines written.");
			  
		  }
		  br.close();
		  tempFile.delete();
		
	}
	@Test
	void testUnwritableFile() {
		
		String fileName = "/sdklfjfdskljfd.txt";
		Logger.setLogFile(fileName);
		
		try {
			
			// instantiate logger
			logger = Logger.getInstance();
			
			// try to write something
			logger.log("Something");
			
			logger.close();
			
			fail("Written in an unwritable document!");
		
		}catch(IOException e){
			return;
		}
		
		
	}
}
