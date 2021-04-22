package edu.wpi.TeamE;

import edu.wpi.TeamE.databases.DDL;
import edu.wpi.TeamE.databases.DML;
import edu.wpi.TeamE.databases.RequestDB;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class App extends Application {

	public static Connection connection;
	public static int userID = 0;
	private static Stage primaryStage;

	private double x, y;

	public static void setDraggableAndChangeScene(Parent root) {
//		ResizeHelper.addResizeListener(App.getPrimaryStage()); //todo this is no longer necessary, making pretty much this whole fcn unnecessary?
//		ResizeHelper.addResizeListener(primaryStage,435,325,Double.MAX_VALUE,Double.MAX_VALUE);
		App.getPrimaryStage().getScene().setRoot(root);
	}

	public static void setDraggableAndChangeScene(Parent root, double minWidth, double minHeight, double maxWidth, double maxHeight) {
		ResizeHelper.addResizeListener(App.getPrimaryStage(), minWidth, minHeight, maxWidth, maxHeight);
		App.getPrimaryStage().getScene().setRoot(root);
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		App.primaryStage = primaryStage;
	}

	@Override
	public void init() {
		System.out.println("Starting connection to Apache Derby");
		try {
			Properties props = new Properties();
			props.put("user", "admin");
			props.put("password", "admin");
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			try {
				connection = DriverManager.getConnection("jdbc:derby:BWDB;create=true", props);
				System.out.println("\nConnected to the DB");
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("Error with the DriverManager");
				System.err.println("\n\n\n---------- Did you connect to database with IntelliJ? ----------\n\n\n");
				return;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("error with the EmbeddedDriver class");
			return;
		}

		System.out.println("\nFor TESTING only: Deleting all tables");
		int numOfTableDeleted = DDL.allTableDelete();
		int dasd = RequestDB.addFloral();
		int dasda = UpdateDB.addFloral();
		if (numOfTableDeleted == 0 || numOfTableDeleted == 1) {
			System.out.println(numOfTableDeleted + " table Deleted");
		} else {
			System.out.println(numOfTableDeleted + " tables Deleted");
		}

		System.out.println("\nPopulate Tables from csv if table not exist");
		if (DDL.nodeTableCreate() == 1) {
			System.out.println("|--- node Table created, populating...");
			File nodes = new File("CSVs/MapEAllnodes.csv");
			DML.populateTable("node", nodes);
		} else {
			//System.out.println("|--- node table already exist");
		}
		if (DDL.hasEdgeTableCreate() == 1) {
			System.out.println("|--- hasEdge Table created, populating...");
			File edges = new File("CSVs/MapEAlledges.csv");
			DML.populateTable("hasEdge", edges);
		} else {
			//System.out.println("|--- node table already exist");
		}

		System.out.println("\nCreate Tables");
		int numOfTableCreated = DDL.allTableCreate();
		if (numOfTableCreated == 0 || numOfTableCreated == 1) {
			System.out.println(numOfTableCreated + " table Created");
		} else {
			System.out.println(numOfTableCreated + " tables Created");
		}

		//connection.addDemoData();
		System.out.println("Demo data added");
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		App.primaryStage = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
			primaryStage.initStyle(StageStyle.UNDECORATED); //set undecorated
			Scene scene = new Scene(root); //init
			primaryStage.setScene(scene);
			primaryStage.setWidth(1050);
			primaryStage.setHeight(675);
			root.minWidth(576);
			primaryStage.show();
			ResizeHelper.addResizeListener(primaryStage, 950, 640, Double.MAX_VALUE, Double.MAX_VALUE);
		} catch (IOException e) {
			e.printStackTrace();
			Platform.exit();
		}
	}

	@Override
	public void stop() {
		System.out.println("Shutting Down");
		System.exit(0);
	}
}
