package edu.wpi.cs3733.D21.teamE;


import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

	//define logger
	static Logger logger = Logger.getLogger("BWH");

	/**
	 * Used to indicate if a certain log indicates a crash
	 */
	static boolean isSafeExitedLog0;
	static boolean isSafeExitedLog1;


	/**
	 * Checks log files for indications of a crash or unexpected exit.
	 * Sets up logger. Due to the nature of checking for a crash,
	 * events cannot be logged until after the check is complete
	 * @param args
	 */
	public static void main(String[] args) {
		boolean isNew0 = true; //used to indicate if logfile 0 is new
		boolean isNew1 = true; //user to indicate if logfile 1 is new

		//determine if log files have already been created or not. Necessary for detecting crashes.
		try {

			//create log files if not already there
			File log1 = new File("BWHApplication.log.1");
			File log0 = new File("BWHApplication.log.0");

			//check if new
			isNew0 = log0.createNewFile();
			isNew1 = log1.createNewFile(); // if file already exists will do nothing

			//For both logs; if not new, check for safe exit. If new, mark as safe exit.
			if (!isNew0) { //if pre-existing log
				String log0Tail = tail(log0); //get tail
				if (log0Tail != null) { //if tail isn't empty
					isSafeExitedLog0 = log0Tail.equals("INFO: Exiting") || log0Tail.contains("crash detected");
				}
			} else { //if new log
				isSafeExitedLog0 = true;
			}
			if (!isNew1) {
				String log1Tail = tail(log1);
				isSafeExitedLog1 = log1Tail.equals("INFO: Exiting") || log1Tail.contains("crash detected");
			} else { //if new log
				isSafeExitedLog1 = true;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		//report a new line of contents to crashed logs so they no longer indicate a crash
		//This prevents reading that a log indicates a crash more than once.
		if (!isSafeExitedLog0 || isNew0) {
			try { //try log 0
				Writer bufferedWriterLog0 = new BufferedWriter(new FileWriter("BWHApplication.log.0", true));
				bufferedWriterLog0.append("crash detected or new file created");
				bufferedWriterLog0.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!isSafeExitedLog1 || isNew1) {
			try {//try log 1
				Writer bufferedWriterLog1 = new BufferedWriter(new FileWriter("BWHApplication.log.1", true));
				bufferedWriterLog1.append("crash detected or new file created");
				bufferedWriterLog1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}



		//setting up logger
		try {
			//define handler
			FileHandler handler = new FileHandler("BWHApplication.log", 1000000,2); //1MB of logs per file, two files.
			SimpleFormatter formatter = new SimpleFormatter();
			handler.setFormatter(formatter);
			handler.setLevel(Level.ALL);

			//add handler to logger
			logger.addHandler(handler);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.setLevel(Level.ALL);


		logger.info("Starting Application");
		App.launch(App.class, args);
	}

	public static String tail(File file) {
		RandomAccessFile fileHandler = null;
		try {
			fileHandler = new RandomAccessFile( file, "r" );
			long fileLength = fileHandler.length() - 1;
			StringBuilder sb = new StringBuilder();

			for(long filePointer = fileLength; filePointer != -1; filePointer--){
				fileHandler.seek( filePointer );
				int readByte = fileHandler.readByte();

				if( readByte == 0xA ) {
					if( filePointer == fileLength ) {
						continue;
					}
					break;

				} else if( readByte == 0xD ) {
					if( filePointer == fileLength - 1 ) {
						continue;
					}
					break;
				}

				sb.append( ( char ) readByte );
			}

			String lastLine = sb.reverse().toString();
			return lastLine;
		} catch( java.io.FileNotFoundException e ) {
			logger.warning("File was not found, " + e);
			e.printStackTrace();
			return null;
		} catch( java.io.IOException e ) {
			logger.warning("IO Exception, " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (fileHandler != null )
				try {
					fileHandler.close();
				} catch (IOException e) {
					/* ignore */
				}
		}
	}
}
