package edu.wpi.cs3733.D21.teamE.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class csvDB {

	static Connection connection = makeConnection.makeConnection().getConnection();

	/**
	 * Reads csv & inserts into table
	 * @param tableName name of the table that needs to be populated
	 * @param file      file that is going to be read
	 */
	public static void populateTable(String tableName, File file) {

		try {
			// creates a file with the file name we are looking to read
			// File file = new File(csvFileName);
			// used to read data from a file
			FileReader fr = new FileReader(file);

			// used to read the text from a character-based input stream.
			BufferedReader br = new BufferedReader(fr);

			String line;
			String[] tempArr;

			// reads first line (this is the header of each file and we don't need it)
			br.readLine();

			// if there is something in the file (after line 1)
			while ((line = br.readLine()) != null) {

				// adds arguments into the array separated by the commas ("," is when it knows the next
				// index)
				tempArr = line.split(",");

				switch (tableName) {
					case "node":
						PreparedStatement nodePS = connection.prepareStatement("Insert Into node Values (?, ?, ?, ?, ?, ?, ?, ?)");
						nodePS.setString(1, tempArr[0]);
						nodePS.setInt(2, Integer.parseInt(tempArr[1]));
						nodePS.setInt(3, Integer.parseInt(tempArr[2]));
						nodePS.setString(4, tempArr[3]);
						nodePS.setString(5, tempArr[4]);
						nodePS.setString(6, tempArr[5]);
						nodePS.setString(7, tempArr[6]);
						nodePS.setString(8, tempArr[7]);
						nodePS.executeUpdate();
						nodePS.close();
						break;
					case "hasEdge":
						PreparedStatement hasEdgePS = connection.prepareStatement("Insert Into hasEdge Values (?, ?, ?, Null)");
						hasEdgePS.setString(1, tempArr[0]);
						hasEdgePS.setString(2, tempArr[1]);
						hasEdgePS.setString(3, tempArr[2]);
						hasEdgePS.executeUpdate();
						EdgeDB.addLength(tempArr[1], tempArr[2]);
						break;
					default:
						System.err.println("Table Name Not Recognized");
						break;
				}
			}
			// closes the BufferedReader
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("error in populateTable() from csvDB");
		}
	}

	/**
	 * Translates a table into a csv file which can be later returned to the user.
	 * @param tableName this is the table/data/information that will be translated into a csv file
	 */
	public static void getNewCSVFile(String tableName) {

		try (PreparedStatement prepState = connection.prepareStatement("Select * From " + tableName)) {

			ResultSet rSet = prepState.executeQuery();
			StringBuilder SB = new StringBuilder();

			switch (tableName) {
				case "node":
					while (rSet.next()) {
						SB.append(rSet.getString("nodeID")).append(",");
						SB.append(rSet.getInt("xCoord")).append(",");
						SB.append(rSet.getInt("yCoord")).append(",");
						SB.append(rSet.getString("floor")).append(",");
						SB.append(rSet.getString("building")).append(",");
						SB.append(rSet.getString("nodeType")).append(",");
						SB.append(rSet.getString("longName")).append(",");
						SB.append(rSet.getString("shortName")).append("\n");
					}
					rSet.close();

					FileWriter nodeWriter = new FileWriter("CSVs/out/nodeOutput.csv");
					nodeWriter.write("nodeID,xCoord,yCoord,floor,building,nodeType,longName,shortName\n");
					nodeWriter.write(String.valueOf(SB));
					nodeWriter.close();
					break;

				case "hasEdge":
					while (rSet.next()) {
						SB.append(rSet.getString("edgeID")).append(",");
						SB.append(rSet.getString("startNode")).append(",");
						SB.append(rSet.getString("endNode")).append(",");
						SB.append(rSet.getDouble("length")).append("\n");
					}
					rSet.close();

					FileWriter hasEdgeWriter = new FileWriter("CSVs/out/hasEdgeOutput.csv");
					hasEdgeWriter.write("edgeID,startNode,endNode,length\n");
					hasEdgeWriter.write(String.valueOf(SB));
					hasEdgeWriter.close();
					break;

				case "requests":
					while (rSet.next()) {
						SB.append(rSet.getString("requestID")).append(",");
						SB.append(rSet.getString("creatorID")).append(",");
						SB.append(rSet.getString("creationTime")).append(",");
						SB.append(rSet.getString("requestType")).append(",");
						SB.append(rSet.getString("requestStatus")).append(",");
						SB.append(rSet.getString("assigneeID")).append("\n");
					}
					rSet.close();

					FileWriter requestsWriter = new FileWriter("CSVs/out/requestsOutput.csv");
					requestsWriter.write("requestID,creatorID,creationTime,requestType,requestStat,assigneeID\n");
					requestsWriter.write(String.valueOf(SB));
					requestsWriter.close();
					break;

				default:
					System.err.println("Table Name Not Recognized");
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addRemovedPatientAppointmentHistory(int patientID) {

		String sqlQuery = "Select * From appointment Where patientid = ?";
		try (PreparedStatement prepStat = connection.prepareStatement(sqlQuery)) {
			prepStat.setInt(1, patientID);


			ResultSet rset = prepStat.executeQuery();

			StringBuilder strBuild = new StringBuilder();
			while (rset.next()) {

				//File nodeCSV = new File("src/main/resources/edu/wpi/cs3733/D21/teamE/output/outputNode.csv");
				strBuild.append(rset.getInt("appointmentID")).append(",");
				strBuild.append(rset.getString("patientID")).append(",");
				strBuild.append(rset.getString("doctorID")).append(",");
				strBuild.append(rset.getString("nodeID")).append(",");
				strBuild.append(rset.getTimestamp("startTime")).append(",");
				strBuild.append(rset.getTimestamp("endTime")).append("\n");
			}

			// create a file writer to write to files
			FileWriter nodeWriter = new FileWriter("CSVs/outputRemovedPatientAppointmentHistory.csv");
			nodeWriter.write("appointmentID, patientID, doctorID, nodeID, startTime, endTime\n");
			nodeWriter.write(String.valueOf(strBuild));
			nodeWriter.close();
			rset.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
