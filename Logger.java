package assignmentLogger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
	private static String logFile;
	private static Logger logger=null;
	private static BufferedWriter out;
	
	private static boolean fileUpdated = false;
	
	private Logger()
	{	
        	    
	}	
    public void close() {
    	try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    public static void reopenFile() throws IOException {
    	
    	out = new BufferedWriter(new FileWriter(logFile, true));
    }

    public static void setLogFile(String s) {
    	logFile = s;
    	fileUpdated = true;
    }
	@SuppressWarnings("static-access")
	public static Logger getInstance() throws IOException 
    {
		if(logger == null) {
			logger = new Logger();
		}
		
		if(fileUpdated) {
			logger.reopenFile();
			fileUpdated = false;
		}
		
			
		return logger;
    }
    public void log(String data){
    	try {
			out.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     	
    }
    public static void main(String[] args) throws IOException {
    	Logger.setLogFile("/Users/bishalghimire/eclipse-workspace/AssignmentLogger/src/assignmentLogger/n" + 
    			"log.txt");
    	Logger logger = Logger.getInstance();
    	logger.log("Log line number one");
    	logger.close();
    }
   
}
