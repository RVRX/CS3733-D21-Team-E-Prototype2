package edu.wpi.TeamE.databases;

import edu.wpi.TeamE.App;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DDL {
	public static int allTableCreate() {
		int numOfTableCreated = 0;
		numOfTableCreated += nodeTableCreate();
		numOfTableCreated += hasEdgeTableCreate();
		numOfTableCreated += userAccountTableCreate();
		numOfTableCreated += visitorAccountViewCreate();
		numOfTableCreated += patientAccountViewCreate();
		numOfTableCreated += doctorAccountViewCreate();
		numOfTableCreated += adminAccountViewCreate();
		numOfTableCreated += requestsTableCreate();
		numOfTableCreated += floralRequestsTableCreate();
		numOfTableCreated += sanitationRequestTableCreate();
		numOfTableCreated += extTransportTableCreate();
		numOfTableCreated += medDeliveryTableCreate();
		numOfTableCreated += securityServTableCreate();
		return numOfTableCreated;
	}

	public static int allTableDelete() {
		int numOfTableDeleted = 0;
		numOfTableDeleted += securityServTableDelete();
		numOfTableDeleted += medDeliveryTableDelete();
		numOfTableDeleted += extTransportTableDelete();
		numOfTableDeleted += sanitationRequestTableDelete();
		numOfTableDeleted += floralRequestsTableDelete();
		numOfTableDeleted += requestsTableDelete();
		numOfTableDeleted += adminAccountViewDelete();
		numOfTableDeleted += doctorAccountViewDelete();
		numOfTableDeleted += patientAccountViewDelete();
		numOfTableDeleted += visitorAccountViewDelete();
		numOfTableDeleted += userAccountTableDelete();
		numOfTableDeleted += hasEdgeTableDelete();
		numOfTableDeleted += nodeTableDelete();
		return numOfTableDeleted;
	}

	// Table Creating: public static int tableName + TableCreate () {}

	public static int nodeTableCreate() {
		String nodeTableCreateS = "Create Table node " +
				"(" +
				"nodeID    varchar(31) Primary Key, " +
				"xCoord    int        Not Null, " +
				"yCoord    int        Not Null, " +
				"floor     varchar(5) Not Null, " +
				"building  varchar(20), " +
				"nodeType  varchar(10), " +
				"longName  varchar(100), " +
				"shortName varchar(100), " +
				"Unique (xCoord, yCoord, floor), " +
				"Constraint floorLimit Check (floor In ('1', '2', '3', 'L1', 'L2')), " +
				"Constraint buildingLimit Check (building In ('BTM', '45 Francis', 'Tower', '15 Francis', 'Shapiro', 'Parking')), " +
				"Constraint nodeTypeLimit Check (nodeType In ('PARK', 'EXIT', 'WALK', 'HALL', 'CONF', 'DEPT', 'ELEV', 'INFO', " +
				"                                             'LABS', 'REST', 'RETL', 'STAI', 'SERV', 'ELEV', 'BATH')) " +
				")";
		try (PreparedStatement nodeTableCreatePS = App.connection.prepareStatement(nodeTableCreateS)) {
			nodeTableCreatePS.execute();
			//TODO if (nodeTableCreatePS.execute()) return 1;
			//TODO else return 0;
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("|--- Failed to create node table");
			return 0;
		}
		return 1; //TODO
	}

	public static int hasEdgeTableCreate() {
		String hasEdgeTableCreateS = "Create Table hasEdge " +
				"( " +
				"edgeID    varchar(63) Primary Key, " +
				"startNode varchar(31) Not Null References node (nodeid) On Delete Cascade, " +
				"endNode   varchar(31) Not Null References node (nodeid) On Delete Cascade, " +
				"length    float, " +
				"Unique (startNode, endNode) " +
				")";
		try (PreparedStatement hasEdgeTableCreatePS = App.connection.prepareStatement(hasEdgeTableCreateS)) {
			if (hasEdgeTableCreatePS.execute()) return 1;
			else return 0;
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("|--- Failed to create hasEdge table");
			return 0;
		}
	}

	public static int userAccountTableCreate() {
		return 0;
	}

	private static int visitorAccountViewCreate() {
		return 0;
	}

	private static int patientAccountViewCreate() {
		return 0;
	}

	private static int doctorAccountViewCreate() {
		return 0;
	}

	private static int adminAccountViewCreate() {
		return 0;
	}

	public static int requestsTableCreate() {
		return 0;
	}

	public static int floralRequestsTableCreate() {
		return 0;
	}

	public static int sanitationRequestTableCreate() {
		return 0;
	}

	public static int extTransportTableCreate() {
		return 0;
	}

	public static int medDeliveryTableCreate() {
		return 0;
	}

	public static int securityServTableCreate() {
		return 0;
	}

	// Table Deleting: public static int tableName + TableDelete () {}

	public static int nodeTableDelete() {
		String nodeTableDeleteS = "Drop Table node";
		try (PreparedStatement nodeTableDeletePS = App.connection.prepareStatement(nodeTableDeleteS)) {
			if (nodeTableDeletePS.execute()) return 1;
			else return 0;
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("|--- Failed to delete node table");
			return 0;
		}
	}

	public static int hasEdgeTableDelete() {
		String nodeTableDeleteS = "Drop Table hasedge";
		try (PreparedStatement nodeTableDeletePS = App.connection.prepareStatement(nodeTableDeleteS)) {
			if (nodeTableDeletePS.execute()) return 1;
			else return 0;
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("|--- Failed to delete node table");
			return 0;
		}
	}

	public static int userAccountTableDelete() {
		return 0;
	}

	private static int visitorAccountViewDelete() {
		return 0;
	}

	private static int patientAccountViewDelete() {
		return 0;
	}

	private static int doctorAccountViewDelete() {
		return 0;
	}

	private static int adminAccountViewDelete() {
		return 0;
	}

	public static int requestsTableDelete() {
		return 0;
	}

	public static int floralRequestsTableDelete() {
		return 0;
	}

	public static int sanitationRequestTableDelete() {
		return 0;
	}

	public static int extTransportTableDelete() {
		return 0;
	}

	public static int medDeliveryTableDelete() {
		return 0;
	}

	public static int securityServTableDelete() {
		return 0;
	}
}
