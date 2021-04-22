package edu.wpi.TeamE;

import edu.wpi.TeamE.algorithms.Edge;
import edu.wpi.TeamE.algorithms.Node;
import edu.wpi.TeamE.databases.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class DatabaseTests {

	makeConnection connection;

	@BeforeAll
	public void setConnection() {
		connection = makeConnection.makeConnection();
	}

	@BeforeEach
	public void setupTables() {


		try {
			connection.deleteAllTables(/*connection.getTablesToThatExist()*/);
			System.out.println("Tables were reset");
		} catch (Exception e) {
			//e.printStackTrace();
		}
		NodeDB.createNodeTable();
		EdgeDB.createEdgeTable();
		UserAccountDB.createUserAccountTable();
		RequestsDB.createRequestsTable();
		RequestsDB.createFloralRequestsTable();
		RequestsDB.createSanitationTable();
		RequestsDB.createExtTransportTable();
		RequestsDB.createMedDeliveryTable();
		RequestsDB.createSecurityServTable();
		System.out.println("Tables were created");
	}

	@Test
	public void testing() {

	}

	@Test
	@DisplayName("testGetNodeInfo")
	public void testGetNodeInfo() {
		NodeDB.addNode("test123", 12, 13, "1", "45 Francis", "PARK", "longName", "shortName");

		Node testNode1 = new Node("test123", 12, 13, "1", "45 Francis", "PARK", "longName", "shortName");

		Node testNode2 = NodeDB.getNodeInfo("test123");

		assertTrue(testNode1.equals(testNode2));
	}


	@Test
	@DisplayName("GetNodeInfo for a node with null values")
	public void testGetNodeInfo2() {
		NodeDB.addNode("test123", 12, 13, "1", "BTM", "PARK", "long", null);

		Node testNode1 = new Node("test123", 12, 13, "1", "BTM", "PARK", "long", null);

		Node testNode2 = NodeDB.getNodeInfo("test123");

		assertTrue(testNode1.equals(testNode2));
	}


	@Test
	@DisplayName("testGetNodeLite")
	public void testGetNodeLite() {
		NodeDB.addNode("test233", 22, 33, "1", "BTM", "WALK", "long", null);

		Node testNode1 = new Node("test233", 22, 33, "1", "BTM", "WALK", "long", null);

		Node testNode2 = NodeDB.getNodeLite("test233");

		assertTrue(testNode1.equals(testNode2));
	}

	@Test
	@DisplayName("testGetEdgeInfo")
	public void testGetEdgeInfo() {
		NodeDB.addNode("testEdge1", 121, 122, "1", "BTM", "EXIT", "String", "String");
		NodeDB.addNode("testEdge2", 12, 15, "1", "BTM", "EXIT", "String", "String");
		NodeDB.addNode("testEdge3", 122, 123, "1", "BTM", "EXIT", "String", "String");
		NodeDB.addNode("testEdge4", 124, 153, "1", "BTM", "EXIT", "String", "String");

		EdgeDB.addEdge("testEdge1_testEdge2", "testEdge1", "testEdge2");
		EdgeDB.addEdge("testEdge3_testEdge4", "testEdge3", "testEdge4");
		EdgeDB.addEdge("testEdge2_testEdge4", "testEdge2", "testEdge4");

		ArrayList<Pair<Float, String>> listOfEdgeInfo = new ArrayList<>();
		ArrayList<Pair<Float, String>> resultEdgeInfo;

		double length3 = Math.pow((122 - 124), 2);
		double length4 = Math.pow((123 - 153), 2);

		double length34 = Math.sqrt((length3 + length4));

		double length1 = Math.pow((12 - 124), 2);
		double length2 = Math.pow((15 - 153), 2);

		double length24 = Math.sqrt((length1 + length2));

		listOfEdgeInfo.add(new Pair<>((float) length34, "testEdge3"));
		listOfEdgeInfo.add(new Pair<>((float) length24, "testEdge2"));

		resultEdgeInfo = EdgeDB.getEdgeInfo("testEdge4");

		assertEquals(resultEdgeInfo, listOfEdgeInfo);

	}


	@Test
	@DisplayName("testGetAllNodes")
	public void testGetAllNodes() {


		NodeDB.addNode("testEdge1", 121, 122, "1", "BTM", "EXIT", "String", "String");
		NodeDB.addNode("testEdge2", 12, 15, "1", "BTM", "EXIT", "String", "String");
		NodeDB.addNode("testEdge3", 122, 123, "1", "BTM", "EXIT", "String", "String");
		NodeDB.addNode("testEdge4", 124, 153, "1", "BTM", "EXIT", "String", "String");


		Node n1 = new Node("testEdge1", 121, 122, "1", "BTM", "EXIT", "String", "String");
		Node n2 = new Node("testEdge2", 12, 15, "1", "BTM", "EXIT", "String", "String");
		Node n3 = new Node("testEdge3", 122, 123, "1", "BTM", "EXIT", "String", "String");
		Node n4 = new Node("testEdge4", 124, 153, "1", "BTM", "EXIT", "String", "String");

		ArrayList<Node> testNodeArray = new ArrayList<>();
		testNodeArray.add(n1);
		testNodeArray.add(n2);
		testNodeArray.add(n3);
		testNodeArray.add(n4);

		ArrayList<Node> nodeArray = NodeDB.getAllNodes();

		boolean allCorrect = true;
		boolean nodeID = false;
		boolean xCoord = false;
		boolean yCoord = false;
		boolean floor = false;
		boolean building = false;
		boolean nodeType = false;
		boolean longName = false;
		boolean shortName = false;

		if (testNodeArray.size() == nodeArray.size()) {
			for (int node = 0; node < nodeArray.size(); node++) {
				Node returnedNode = nodeArray.get(node);
				Node correctNode = testNodeArray.get(node);
				if (returnedNode.get("id").equals(correctNode.get("id"))) {
					nodeID = true;
				}
				if (returnedNode.getX() == correctNode.getX()) {
					xCoord = true;
				}
				if (returnedNode.getY() == correctNode.getY()) {
					yCoord = true;
				}
				if (returnedNode.get("floor").equals(correctNode.get("floor"))) {
					floor = true;
				}
				if (returnedNode.get("building").equals(correctNode.get("building"))) {
					building = true;
				}
				if (returnedNode.get("type").equals(correctNode.get("type"))) {
					nodeType = true;
				}
				if (returnedNode.get("longName").equals(correctNode.get("longName"))) {
					longName = true;
				}
				if (returnedNode.get("shortName").equals(correctNode.get("shortName"))) {
					shortName = true;
				}
				if (nodeID && xCoord && yCoord && floor && building && nodeType && longName && !shortName) {
					allCorrect = false;
				}
			}
		} else {
			allCorrect = false;
		}

		assertTrue(allCorrect);

	}


	@Test
	@DisplayName("testGetAllNodesByFloor: 2 nodes with the given floor")
	public void testGetAllNodesByFloor() {
		NodeDB.addNode("nodeID1", 0, 0, "1", "Tower", "PARK", "longName1", "shortName1");
		NodeDB.addNode("nodeID2", 1, 0, "1", "Tower", "PARK", "longName2", "shortName2");

		ArrayList<Node> nodes = NodeDB.getAllNodesByFloor("1");

		ArrayList<Node> correctNodes = new ArrayList<>();
		Node node1 = new Node("nodeID1", 0, 0, "1", "Tower", "PARK", "longName1", "shortName1");
		Node node2 = new Node("nodeID2", 1, 0, "1", "Tower", "PARK", "longName2", "shortName2");

		correctNodes.add(node1);
		correctNodes.add(node2);

		boolean allCorrect = true;
		boolean nodeID = false;
		boolean xCoord = false;
		boolean yCoord = false;
		boolean floor = false;
		boolean building = false;
		boolean nodeType = false;
		boolean longName = false;
		boolean shortName = false;

		if (nodes.size() == correctNodes.size()) {
			for (int node = 0; node < nodes.size(); node++) {
				Node returnedNode = nodes.get(node);
				Node correctNode = correctNodes.get(node);
				if (returnedNode.get("id").equals(correctNode.get("id"))) {
					nodeID = true;
				}
				if (returnedNode.getX() == correctNode.getX()) {
					xCoord = true;
				}
				if (returnedNode.getY() == correctNode.getY()) {
					yCoord = true;
				}
				if (returnedNode.get("floor").equals(correctNode.get("floor"))) {
					floor = true;
				}
				if (returnedNode.get("building").equals(correctNode.get("building"))) {
					building = true;
				}
				if (returnedNode.get("type").equals(correctNode.get("type"))) {
					nodeType = true;
				}
				if (returnedNode.get("longName").equals(correctNode.get("longName"))) {
					longName = true;
				}
				if (returnedNode.get("shortName").equals(correctNode.get("shortName"))) {
					shortName = true;
				}
				if (nodeID && xCoord && yCoord && floor && building && nodeType && longName && !shortName) {
					allCorrect = false;
				}
			}
		} else {
			allCorrect = false;
		}

		assertTrue(allCorrect);
	}

	@Test
	@DisplayName("testGetAllNodesByFloor: no nodes with the given floor")
	public void testGetAllNodesByFloor2() {
		NodeDB.addNode("nodeID1", 0, 0, "1", "Tower", "ELEV", "longName1", "shortName1");
		NodeDB.addNode("nodeID2", 1, 0, "1", "Tower", "ELEV", "longName2", "shortName2");
		NodeDB.addNode("nodeID3", 3, 0, "1", "Tower", "ELEV", "longName3", "shortName3");

		ArrayList<Node> nodes = NodeDB.getAllNodesByFloor("3");

		assertEquals(0, nodes.size());
	}


	@Test
	@DisplayName("testGetAllNodeLongNamesByFloor: 2 nodes with the given floor")
	public void testGetAllNodeLongNamesByFloor() {
		NodeDB.addNode("nodeID1", 0, 0, "1", "Tower", "ELEV", "longName1", "shortName1");
		NodeDB.addNode("nodeID2", 1, 0, "1", "Tower", "ELEV", "longName2", "shortName2");

		ObservableList<String> longNames = NodeDB.getAllNodeLongNamesByFloor("1");

		ObservableList<String> correctLongNames = FXCollections.observableArrayList();

		correctLongNames.add("longName1");
		correctLongNames.add("longName2");

		assertEquals(longNames, correctLongNames);

	}

	@Test
	@DisplayName("testGetAllNodeLongNamesByFloor: no nodes with the given floor")
	public void testGetAllNodeLongNamesByFloor2() {
		NodeDB.addNode("nodeID1", 0, 0, "1", "Tower", "ELEV", "longName1", "shortName1");
		NodeDB.addNode("nodeID2", 1, 0, "1", "Tower", "ELEV", "longName2", "shortName2");
		NodeDB.addNode("nodeID3", 0, 0, "1", "Tower", "ELEV", "longName3", "shortName3");

		ObservableList<String> longNames = NodeDB.getAllNodeLongNamesByFloor("3");

		assertEquals(0, longNames.size());
	}

	@Test
	@DisplayName("testGetListOfNodeIDSByFloor: 2 nodes with the given floor")
	public void testGetListOfNodeIDSByFloor() {
		NodeDB.addNode("nodeID1", 0, 0, "1", "Tower", "ELEV", "longName1", "shortName1");
		NodeDB.addNode("nodeID2", 1, 0, "1", "Tower", "ELEV", "longName2", "shortName2");

		ArrayList<String> nodeIDs = NodeDB.getListOfNodeIDSByFloor("1");

		ArrayList<String> correctNodeIDs = new ArrayList<>();

		correctNodeIDs.add("nodeID1");
		correctNodeIDs.add("nodeID2");

		assertEquals(nodeIDs, correctNodeIDs);
	}

	@Test
	@DisplayName("testGetListOfNodeIDSByFloor: no nodes with the given floor")
	public void testGetListOfNodeIDSByFloor2() {
		NodeDB.addNode("nodeID1", 0, 0, "1", "Tower", "ELEV", "longName1", "shortName1");
		NodeDB.addNode("nodeID2", 1, 0, "1", "Tower", "ELEV", "longName2", "shortName2");
		NodeDB.addNode("nodeID3", 3, 0, "1", "Tower", "ELEV", "longName3", "shortName3");

		ArrayList<String> nodeIDs = NodeDB.getListOfNodeIDSByFloor("3");


		assertEquals(0, nodeIDs.size());
	}


	@Test
	@DisplayName("testGetAllEdges")
	public void testGetAllEdges() {
		NodeDB.addNode("test1", 0, 0, "1", "Tower", "ELEV", "test", "test");
		NodeDB.addNode("test2", 2, 2, "1", "Tower", "ELEV", "test", "test");
		EdgeDB.addEdge("test1_test2", "test1", "test2");

		ArrayList<Edge> listOfEdges = new ArrayList<>();


		double length1 = Math.pow((-2), 2);
		double length2 = Math.pow((-2), 2);

		double length12 = Math.sqrt((length1 + length2));

		Edge edge1 = new Edge("test1_test2", "test1", "test2", length12);

		listOfEdges.add(edge1);

		ArrayList<Edge> resultListOfEdges = EdgeDB.getAllEdges();

		boolean allCorrect = true;
		boolean edgeID = false;
		boolean length = false;
		if (listOfEdges.size() == resultListOfEdges.size()) {
			for (int edge = 0; edge < resultListOfEdges.size(); edge++) {
				Edge returnedEdge = resultListOfEdges.get(edge);
				Edge correctEdge = listOfEdges.get(edge);
				if (returnedEdge.getLength().equals(correctEdge.getLength())) {
					length = true;
				}
				if (returnedEdge.getId().equals(correctEdge.getId())) {
					edgeID = true;
				}
				if (length && !edgeID) {
					allCorrect = false;
				}
			}
		} else {
			allCorrect = false;
		}
		assertTrue(allCorrect);
	}


	@Test
	@DisplayName("testGetAllEdges: there are no edges")
	public void testGetAllEdges2() {
		NodeDB.addNode("test1", 0, 0, "1", "Tower", "ELEV", "test", "test");
		NodeDB.addNode("test2", 2, 2, "1", "Tower", "ELEV", "test", "test");

		ArrayList<Edge> listOfEdges = EdgeDB.getAllEdges();

		assertEquals(0, listOfEdges.size());
	}

	@Test
	@DisplayName("testAddNode")
	public void testAddNode() {
		int testResult;

		// if this works, testResult should be 1
		testResult = NodeDB.addNode("testNode", 111, 222, "1", "Tower", "ELEV", "String", "String");

		assertEquals(1, testResult);
	}

	@Test
	@DisplayName("testAddNode: the node added already exists")
	public void testAddNode2() {
		NodeDB.addNode("testNode12", 121, 222, "1", "Tower", "ELEV", "String", "String");

		int testResult = NodeDB.addNode("testNode12", 121, 222, "1", "Tower", "ELEV", "String", "String");

		assertEquals(0, testResult);
	}


	@Test
	@DisplayName("testAddEdge")
	public void testAddEdge() {

		int testResult;

		NodeDB.addNode("testEdge1", 121, 122, "1", "Tower", "ELEV", "String", "String");
		NodeDB.addNode("testEdge2", 12, 15, "1", "Tower", "ELEV", "String", "String");

		// if this works, testResult should be 1
		testResult = EdgeDB.addEdge("testEdge1_testEdge2", "testEdge1", "testEdge2");

		assertEquals(1, testResult);

	}

	@Test
	@DisplayName("testAddEdge: the edgeID already exists")
	public void testAddEdge2() {
		NodeDB.addNode("testEdge1", 121, 122, "1", "Tower", "ELEV", "String", "String");
		NodeDB.addNode("testEdge2", 12, 15, "1", "Tower", "ELEV", "String", "String");

		EdgeDB.addEdge("testEdge1_testEdge2", "testEdge1", "testEdge2");

		int testResult = EdgeDB.addEdge("testEdge1_testEdge2", "testEdge1", "testEdge2");

		assertEquals(0, testResult);
	}

	@Test
	@DisplayName("testAddEdge: the startNode does not exist")
	public void testAddEdge3() {
		NodeDB.addNode("testEdge1", 121, 122, "1", "Tower", "ELEV", "String", "String");
		NodeDB.addNode("testEdge2", 12, 15, "1", "Tower", "ELEV", "String", "String");

		// if this works, testResult should be 1
		int testResult = EdgeDB.addEdge("testEdge1_testEdge2", "testEdge3", "testEdge2");

		assertEquals(0, testResult);
	}

	@Test
	@DisplayName("testAddEdge: the endNode does not exist")
	public void testAddEdge4() {
		NodeDB.addNode("testEdge1", 121, 122, "1", "Tower", "ELEV", "String", "String");
		NodeDB.addNode("testEdge2", 12, 15, "1", "Tower", "ELEV", "String", "String");

		// if this works, testResult should be 1
		int testResult = EdgeDB.addEdge("testEdge1_testEdge2", "testEdge1", "testEdge3");

		assertEquals(0, testResult);
	}

	@Test
	@DisplayName("testModifyNode")
	public void testModifyNode() {

		int testResult;

		NodeDB.addNode("originalNode", 121, 122, "1", "Tower", "ELEV", "String", "String");

		testResult = NodeDB.modifyNode("originalNode", 100, null, null, null, null, null, null);


		assertEquals(1, testResult);
	}

	@Test
	@DisplayName("testDeleteEdge")
	public void testDeleteEdge() {

		int testResult;

		NodeDB.addNode("testEdge1", 121, 122, "1", "Tower", "ELEV", "String", "String");
		NodeDB.addNode("testEdge2", 12, 15, "1", "Tower", "ELEV", "String", "String");

		EdgeDB.addEdge("testEdge1_testEdge2", "testEdge1", "testEdge2");

		testResult = EdgeDB.deleteEdge("testEdge1", "testEdge2");

		System.out.println(testResult);

		assertEquals(0, testResult);
	}

	@Test
	@DisplayName("testDeleteNode")
	public void testDeleteNode() {

		int testResult;

		NodeDB.addNode("testEdge1", 121, 122, "1", "Tower", "ELEV", "String", "String");
		testResult = NodeDB.deleteNode("testEdge1");

		assertEquals(1, testResult);
	}

	@Test
	@DisplayName("testGetListOfNodeIDS")
	public void testGetListOfNodeIDS() {

		NodeDB.addNode("test1", 0, 0, "1", "Tower", "ELEV", "test", "test");
		NodeDB.addNode("test2", 2, 2, "1", "Tower", "ELEV", "test", "test");
		NodeDB.addNode("test3", 3, 3, "1", "Tower", "ELEV", "test", "test");
		NodeDB.addNode("test4", 4, 4, "1", "Tower", "ELEV", "test", "test");

		ArrayList<String> listOfNodeIDs = new ArrayList<>();

		listOfNodeIDs.add("test1");
		listOfNodeIDs.add("test2");
		listOfNodeIDs.add("test3");
		listOfNodeIDs.add("test4");

		assertEquals(listOfNodeIDs, NodeDB.getListOfNodeIDS());
	}

	@Test
	@DisplayName("Testing countNodeTypeOnFloor()")
	public void testCountNodeTypeOnFloor() {
		NodeDB.addNode("test1", 534, 0, "1", "Tower", "INFO", "long", "asd");
		NodeDB.addNode("Test2", 54, 2, "1", "Tower", "ELEV", "name", "test");
		NodeDB.addNode("test3", 43, 3, "1", "Tower", "ELEV", "test", "hert");
		NodeDB.addNode("test4", 544, 4, "1", "Tower", "ELEV", "fun", "test");
		NodeDB.addNode("Test5", 4350, 0, "1", "Tower", "ELEV", "long", "asd");
		NodeDB.addNode("test6", 5342, 2, "1", "Tower", "ELEV", "name", "test");
		NodeDB.addNode("test7", 433, 3, "1", "Tower", "ELEV", "test", "hert");
		NodeDB.addNode("test8", 344, 4, "1", "Tower", "ELEV", "fun", "test");
		NodeDB.addNode("test9", 430, 0, "1", "Tower", "ELEV", "long", "asd");
		NodeDB.addNode("test10", 432, 2, "1", "Tower", "ELEV", "name", "test");

		int returned = NodeDB.countNodeTypeOnFloor("t", "1", "INFO");
		assertEquals(1, returned);
		int returned2 = NodeDB.countNodeTypeOnFloor("t", "1", "PARK");
		assertEquals(0, returned2);
	}


	@Test
	@DisplayName("testCreateUserAccountTable")
	public void testCreateUserAccountTable() {

	}

	@Test
	@DisplayName("testAddSanitationRequest")
	public void testAddSanitationRequest() {
		NodeDB.addNode("test", 0, 0, "1", "Tower", "INFO", "longName", "shortName");
		UserAccountDB.addUserAccount("test@email.com", "testPassword", "Testing", "Queen");

		RequestsDB.addSanitationRequest(1, "bob","test", "Urine Cleanup", "description here", "Low", "Nupur Shukla");
	}

	@Test
	@DisplayName("testAddExternalPatientRequest")
	public void testAddExternalPatientRequest() {
		NodeDB.addNode("test", 0, 0, "1", "Tower", "INFO", "longName", "shortName");
		UserAccountDB.addUserAccount("test@gmail.com", "testPass", "Nubia", "Shukla");

		RequestsDB.addExternalPatientRequest(1, "bob", "test", "Ambulance","severe", "123", "15 mins", "headache");
	}

	@Test
	@DisplayName("testAddMedicineRequest")
	public void testAddMedicineRequest() {
		NodeDB.addNode("test", 0, 0, "2", "Tower", "INFO", "longName", "shortName");
		UserAccountDB.addUserAccount("test@gmail.com", "testPass", "Nubia", "Shukla");
		RequestsDB.addMedicineRequest(1, "bob","test", "drugs", 2, "100ml", "take once a day", "Nupur");
	}

	@Test
	@DisplayName("testSecurityRequest")
	public void testAddSecurityRequest() {
		NodeDB.addNode("test", 0, 0, "2", "Tower", "INFO", "longName", "shortName");

		UserAccountDB.addUserAccount("test@gmail.com", "testPass", "Nubia", "Shukla");

		RequestsDB.addSecurityRequest(1, "bob", "test", "low", "Low");
	}

	@Test
	@DisplayName("testAddFloralRequest")
	public void testAddFloralRequest() {
		NodeDB.addNode("test", 0, 0, "2", "Tower", "INFO", "longName", "shortName");
		UserAccountDB.addUserAccount("test@gmail.com", "testPass", "Nubia", "Shukla");

		RequestsDB.addFloralRequest(1,"bob", "test", "Nupur", "Roses", 1, "Tall", "feel better");

	}

	@Test
	@DisplayName("testAddSpecialUserType")
	public void testAddSpecialUserType() {
		UserAccountDB.addSpecialUserType("test1@gmail.com", "testPassword", "patient", "patientFirstName", "patientLastName");
		UserAccountDB.addSpecialUserType("test2@gmail.com", "testPassword1", "admin", "adminFirstName", "adminLastName");
		UserAccountDB.addSpecialUserType("test3@gmail.com", "testPassword2", "doctor", "doctorFirstName", "doctorLastName");

	}


	@Test
	@DisplayName("testGetRequestStatus")
	public void testGetRequestStatus() {
		NodeDB.addNode("test", 0, 0, "2", "Tower", "INFO", "longName", "shortName");
		UserAccountDB.addUserAccount("test@gmail.com", "testPass", "Nubia", "Shukla");
		UserAccountDB.addUserAccount("test2@gmail.com", "testPass", "Nubia", "Shukla");
		RequestsDB.addExternalPatientRequest(1, "bob","test", "Ambulance", "severe", "123", "15 mins", "headache");
		RequestsDB.addExternalPatientRequest(1, "bob","test", "Ambulance", "severe", "123", "15 mins", "migraine");
		RequestsDB.addExternalPatientRequest(2, "bob","test", "Ambulance", "severe", "123", "15 mins", "migraine");
		RequestsDB.addFloralRequest(1, "bob","test", "Nupur", "Roses", 1, "Tall", "feel better");

		ArrayList<String> returnedStatus = new ArrayList<String>();
		ArrayList<String> correctStatus = new ArrayList<String>();

		correctStatus.add("inProgress");
		correctStatus.add("inProgress");

		returnedStatus = RequestsDB.getRequestStatus("extTransport", 1);

		assertTrue(correctStatus.equals(returnedStatus));
	}

	@Test
	@DisplayName("testGetRequestStatus2")
	public void testGetRequestStatus2() {
		NodeDB.addNode("test", 0, 0, "2", "Tower", "INFO", "longName", "shortName");
		UserAccountDB.addUserAccount("test@gmail.com", "testPass", "Nubia", "Shukla");
		UserAccountDB.addUserAccount("test2@gmail.com", "testPass", "Nubia", "Shukla");
		RequestsDB.addExternalPatientRequest(1, "bob","test", "Ambulance", "severe", "123", "15 mins", "headache");
		RequestsDB.addExternalPatientRequest(1, "bob","test", "Ambulance", "severe", "123", "15 mins", "migraine");
		RequestsDB.addExternalPatientRequest(2, "bob","test", "Ambulance", "severe", "123", "15 mins", "migraine");
		RequestsDB.addFloralRequest(1, "bob","test", "Nupur", "Roses", 1, "Tall", "feel better");

		ArrayList<String> returnedStatus = new ArrayList<String>();
		ArrayList<String> correctStatus = new ArrayList<String>();

		correctStatus.add("inProgress");
		correctStatus.add("inProgress");
		correctStatus.add("inProgress");

		returnedStatus = RequestsDB.getRequestStatus("extTransport", -1);

		assertTrue(correctStatus.equals(returnedStatus));
	}

	@Test
	@DisplayName("testGetRequestStatus3 : there is no data ")
	public void testGetRequestStatu3() {

		ArrayList<String> returnedStatus = new ArrayList<String>();
		returnedStatus = RequestsDB.getRequestStatus("extTransport", 1);

		assertTrue(returnedStatus.size() == 0);
	}


	@Test
	@DisplayName("testGetRequestIDs")
	public void testGetRequestIDs() {

		NodeDB.addNode("test", 0, 0, "2", "Tower", "INFO", "longName", "shortName");
		UserAccountDB.addUserAccount("test@gmail.com", "testPass", "Nubia", "Shukla");
		UserAccountDB.addUserAccount("test2@gmail.com", "testPass", "Nubia", "Shukla");
		RequestsDB.addExternalPatientRequest(1, "bob","test", "Ambulance", "severe", "123", "15 mins", "headache");
		RequestsDB.addExternalPatientRequest(2, "bob","test", "Ambulance", "severe", "123", "15 mins", "migraine");
		RequestsDB.addFloralRequest(1, "bob","test", "Nupur", "Roses", 1, "Tall", "feel better");

		ArrayList<String> returnedIDs = new ArrayList<String>();
		ArrayList<String> correctIDs = new ArrayList<String>();

		correctIDs.add("1");

		returnedIDs = RequestsDB.getRequestIDs("extTransport", 1);

		assertTrue(correctIDs.equals(returnedIDs));
	}

	@Test
	@DisplayName("testGetRequestIDs2")
	public void testGetRequestIDs2() {

		NodeDB.addNode("test", 0, 0, "2", "Tower", "INFO", "longName", "shortName");
		UserAccountDB.addUserAccount("test@gmail.com", "testPass", "Nubia", "Shukla");
		UserAccountDB.addUserAccount("test2@gmail.com", "testPass", "Nubia", "Shukla");
		RequestsDB.addExternalPatientRequest(1, "bob","test", "Ambulance", "severe", "123", "15 mins", "headache");
		RequestsDB.addExternalPatientRequest(2, "bob","test", "Ambulance", "severe", "123", "15 mins", "migraine");
		RequestsDB.addFloralRequest(1, "bob","test", "Nupur", "Roses", 1, "Tall", "feel better");

		ArrayList<String> returnedIDs = new ArrayList<String>();
		ArrayList<String> correctIDs = new ArrayList<String>();

		correctIDs.add("1");
		correctIDs.add("2");

		returnedIDs = RequestsDB.getRequestIDs("extTransport", -1);

		assertTrue(correctIDs.equals(returnedIDs));
	}

	@Test
	@DisplayName("testGetRequestIDs3: there is no data")
	public void testGetRequestIDs3() {

		ArrayList<String> returnedIDs = new ArrayList<String>();
		returnedIDs = RequestsDB.getRequestIDs("extTransport", -1);

		assertTrue(returnedIDs.size() == 0);
	}



	@Test
	@DisplayName("testGetRequestAssignees")
	public void testGetRequestAssignees(){

		NodeDB.addNode("test", 0, 0, "2", "Tower", "INFO", "long name #1", "shortName");
		NodeDB.addNode("test3", 0, 0, "L1", "Tower", "INFO", "long name #2", "shortName");
		UserAccountDB.addUserAccount("test@gmail.com", "testPass", "Nubia", "Shukla");
		RequestsDB.addMedicineRequest(1, "bob","test", "drugs", 2, "100ml", "take once a day", "Nupur");
		RequestsDB.addMedicineRequest(1, "kim","test3", "drugs2", 3, "10ml", "take once a day", "Nupur");

		NodeDB.addNode("test2", 0, 0, "3", "Tower", "INFO", "long name #3", "shortName");
		UserAccountDB.addUserAccount("test2@gmail.com", "testPass", "Nubia", "Shukla");
		RequestsDB.addMedicineRequest(2, "dell","test2", "drugs2", 3, "10ml", "take once a day", "Nupur");

		ArrayList<String> returnedAssignees = RequestsDB.getRequestAssignees("medDelivery", 1);
		ArrayList<String> correctAssignees = new ArrayList<String>();

		correctAssignees.add("bob");
		correctAssignees.add("kim");

		assertTrue(correctAssignees.equals(returnedAssignees));
	}

	@Test
	@DisplayName("testGetRequestAssignees2")
	public void testGetRequestAssignees2(){

		NodeDB.addNode("test", 0, 0, "2", "Tower", "INFO", "long name #1", "shortName");
		NodeDB.addNode("test3", 0, 0, "L1", "Tower", "INFO", "long name #2", "shortName");
		UserAccountDB.addUserAccount("test@gmail.com", "testPass", "Nubia", "Shukla");
		RequestsDB.addMedicineRequest(1, "bob","test", "drugs", 2, "100ml", "take once a day", "Nupur");
		RequestsDB.addMedicineRequest(1, "kim","test3", "drugs2", 3, "10ml", "take once a day", "Nupur");

		NodeDB.addNode("test2", 0, 0, "3", "Tower", "INFO", "long name #3", "shortName");
		UserAccountDB.addUserAccount("test2@gmail.com", "testPass", "Nubia", "Shukla");
		RequestsDB.addMedicineRequest(2, "dell","test2", "drugs2", 3, "10ml", "take once a day", "Nupur");

		//"floral", "medDelivery", "sanitation", "security", "extTransport".

		ArrayList<String> returnedAssignees = RequestsDB.getRequestAssignees("medDelivery", -1);
		ArrayList<String> correctAssignees = new ArrayList<String>();

		correctAssignees.add("bob");
		correctAssignees.add("kim");
		correctAssignees.add("dell");

		assertTrue(correctAssignees.equals(returnedAssignees));
	}

	@Test
	@DisplayName("testGetRequestAssignees3: no data")
	public void testGetRequestAssignees3(){
		ArrayList<String> returnedAssignees = RequestsDB.getRequestAssignees("medDelivery", -1);
		assertTrue(returnedAssignees.size() == 0);
	}



	@Test
	@DisplayName("testGetRequestLocations")
	public void testGetRequestLocations(){

		NodeDB.addNode("test", 0, 0, "2", "Tower", "INFO", "long name #1", "shortName");
		NodeDB.addNode("test3", 0, 0, "L1", "Tower", "INFO", "long name #2", "shortName");
		UserAccountDB.addUserAccount("test@gmail.com", "testPass", "Nubia", "Shukla");
		RequestsDB.addMedicineRequest(1, "bob","test", "drugs", 2, "100ml", "take once a day", "Nupur");
		RequestsDB.addMedicineRequest(1, "kim","test3", "drugs2", 3, "10ml", "take once a day", "Nupur");

		NodeDB.addNode("test2", 0, 0, "3", "Tower", "INFO", "long name #3", "shortName");
		UserAccountDB.addUserAccount("test2@gmail.com", "testPass", "Nubia", "Shukla");
		RequestsDB.addMedicineRequest(2, "kim","test2", "drugs2", 3, "10ml", "take once a day", "Nupur");

		ArrayList<String> returnedLocations = RequestsDB.getRequestLocations("medDelivery", 1);
		ArrayList<String> correctLocations = new ArrayList<String>();

		correctLocations.add("long name #1");
		correctLocations.add("long name #2");

		for(String e: returnedLocations)
			System.out.println(e);

		assertTrue(correctLocations.equals(returnedLocations));

	}

	@Test
	@DisplayName("testGetRequestLocations2")
	public void testGetRequestLocations2(){

		NodeDB.addNode("test", 0, 0, "2", "Tower", "INFO", "long name #1", "shortName");
		NodeDB.addNode("test3", 0, 0, "L1", "Tower", "INFO", "long name #2", "shortName");
		UserAccountDB.addUserAccount("test@gmail.com", "testPass", "Nubia", "Shukla");
		RequestsDB.addMedicineRequest(1, "bob","test", "drugs", 2, "100ml", "take once a day", "Nupur");
		RequestsDB.addMedicineRequest(1, "kim","test3", "drugs2", 3, "10ml", "take once a day", "Nupur");

		NodeDB.addNode("test2", 0, 0, "3", "Tower", "INFO", "long name #3", "shortName");
		UserAccountDB.addUserAccount("test2@gmail.com", "testPass", "Nubia", "Shukla");
		RequestsDB.addMedicineRequest(2, "kim","test2", "drugs2", 3, "10ml", "take once a day", "Nupur");

		ArrayList<String> returnedLocations = RequestsDB.getRequestLocations("medDelivery", -1);
		ArrayList<String> correctLocations = new ArrayList<String>();

		correctLocations.add("long name #1");
		correctLocations.add("long name #2");
		correctLocations.add("long name #3");

		for(String e: returnedLocations)
			System.out.println(e);

		assertTrue(correctLocations.equals(returnedLocations));

	}

	@Test
	@DisplayName("testGetRequestLocations3 : no data")
	public void testGetRequestLocations3(){

		ArrayList<String> returnedLocations = RequestsDB.getRequestLocations("medDelivery", -1);
		assertTrue(returnedLocations.size() == 0);

	}


	@Test
	@DisplayName("testEditSanitationRequest")
	public void testEditSanitationRequest(){

		NodeDB.addNode("test", 0, 0, "1", "Tower", "INFO", "longName", "shortName");
		UserAccountDB.addUserAccount("test@email.com", "testPassword", "Testing", "Queen");

		RequestsDB.addSanitationRequest(1, "bob","test", "Urine Cleanup", "description here", "Low", "Nupur Shukla");

		int result = 0;
		result = RequestsDB.editSanitationRequest(1, "test", null, null, null, "hello test");

		assertTrue(result == 1);
	}

	@Test
	@DisplayName("testEditExternalPatientRequest")
	public void testEditExternalPatientRequest(){

		NodeDB.addNode("test", 0, 0, "1", "Tower", "INFO", "longName", "shortName");
		UserAccountDB.addUserAccount("test@email.com", "testPassword", "Testing", "Queen");

		RequestsDB.addExternalPatientRequest(1, "bob", "test", "Ambulance","severe", "123", "15 mins", "headache");

		int result = 0;
		result = RequestsDB.editExternalPatientRequest(1, "test", null, null, null, null, "15 mins");

		assertTrue(result == 1);
	}

	@Test
	@DisplayName("testEditMedicineRequest")
	public void testEditMedicineRequest(){

		NodeDB.addNode("test", 0, 0, "1", "Tower", "INFO", "longName", "shortName");
		UserAccountDB.addUserAccount("test@email.com", "testPassword", "Testing", "Queen");

		RequestsDB.addMedicineRequest(1, "bob","test", "drugs", 2, "100ml", "take once a day", "Nupur");

		int result = 0;
		result = RequestsDB.editMedicineRequest(1,"test", "Tylenol", null, null, "Take twice everyday", null);

		assertTrue(result == 1);
	}

	@Test
	@DisplayName("testEditFloralRequest")
	public void testEditFloralRequest(){

		NodeDB.addNode("test", 0, 0, "1", "Tower", "INFO", "longName", "shortName");
		UserAccountDB.addUserAccount("test@email.com", "testPassword", "Testing", "Queen");

		RequestsDB.addFloralRequest(1,"bob", "test", "Nupur", "Roses", 1, "Tall", "feel better");

		int result = 0;
		result = RequestsDB.editFloralRequest(1,"test", "Ashley", "Tulips", null, null, null);

		assertTrue(result == 1);
	}

	@Test
	@DisplayName("testEditSecurityRequest")
	public void testEditSecurityRequest(){

		NodeDB.addNode("test", 0, 0, "1", "Tower", "INFO", "longName", "shortName");
		UserAccountDB.addUserAccount("test@email.com", "testPassword", "Testing", "Queen");

		RequestsDB.addSecurityRequest(1, "bob", "test", "low", "Low");

		int result = 0;
		result = RequestsDB.editSecurityRequest(1,null, "high", "High");

		assertTrue(result == 1);
	}

	@Test
	@DisplayName("testChangeRequestStatus")
	public void testChangeRequestStatus(){

		NodeDB.addNode("test", 0, 0, "2", "Tower", "INFO", "longName", "shortName");
		UserAccountDB.addUserAccount("test@gmail.com", "testPass", "Nubia", "Shukla");
		RequestsDB.addMedicineRequest(1, "bob","test", "drugs", 2, "100ml", "take once a day", "Nupur");
		RequestsDB.addMedicineRequest(1, "bob1","test", "drugs2", 3, "10ml", "take once a day", "Nupur");

		ArrayList<String> IDS = RequestsDB.getRequestIDs("medDelivery", 1);

		int rowsChanged = RequestsDB.changeRequestStatus(1, "complete");

		assertEquals(1, rowsChanged);
	}

	@Test
	@DisplayName("testDataForPresentation")
	public void testDataForPresentation(){

		//Floral Delivery Nodes:
		NodeDB.addNode("ADEPT00101",1401,2628,"1","BTM","DEPT","Neuroscience Waiting Room","Neuro Waiting Room");
		NodeDB.addNode("ADEPT00102",1395,2674,"2","BTM","DEPT","Orthopedics and Rhemutalogy","Orthopedics and Rhemutalogy");
		NodeDB.addNode("ADEPT00201",1720,2847,"1","BTM","DEPT","MS Waiting","MS Waiting");
		NodeDB.addNode("ADEPT00301",986,2852,"1","BTM","DEPT","CART Waiting","CART Waiting");
		NodeDB.addNode("DDEPT00102",4330,700,"2","15 Francis","DEPT","Chest Diseases Floor 2","Chest Diseases");


		//Sanitation Nodes:
		NodeDB.addNode("AREST00101",1556,2604,"1","BTM","REST","Restroom S elevator 1st floor","Restroom");
		NodeDB.addNode("AREST00103",1552,2854,"3","BTM","REST","Restroom BTM conference center 3rd floor","Restroom");
		NodeDB.addNode("ARETL00101",1619,2522,"1","BTM","RETL","Cafe","Cafe");
		NodeDB.addNode("IREST00103",2255,1255,"3","45 Francis","REST","Restroom 1 - Family","R1");
		NodeDB.addNode("IREST00203",2570,1257,"3","45 Francis","REST","Restroom 2","R2");
		NodeDB.addNode("IREST00303",2745,1147,"3","45 Francis","REST","Restroom 3","R3");
		NodeDB.addNode("IREST00403",2300,1018,"3","45 Francis","REST","Restroom 4 - M wheelchair","R4");
		NodeDB.addNode("HRETL00102",1935,860,"2","Tower","RETL","Garden Cafe","Garden Cafe");


		//med delivery Nodes:
		NodeDB.addNode("BLABS00102",2246,1350,"2","45 Francis","LABS","Vascular Diagnostic Lab","Labs B0102");
		NodeDB.addNode("BLABS00202",2945,995,"2","45 Francis","LABS","Outpatient Specimen Collection","Labs B0202");
		NodeDB.addNode("IDEPT00103",2323,1328,"3","45 Francis","DEPT","Center for Infertility and Reproductive Surgery","D1");
		NodeDB.addNode("IDEPT00203",2448,1328,"3","45 Francis","DEPT","Gynecology Oncology MIGS","D2");
		NodeDB.addNode("IDEPT00303",2730,1315,"3","45 Francis","DEPT","General Surgical Specialties Suite A","D3");
		NodeDB.addNode("IDEPT00403",2738,1227,"3","45 Francis","DEPT","General Surgical Specialties Suite B","D4");
		NodeDB.addNode("IDEPT00503",2868,1075,"3","45 Francis","DEPT","Urology","D5");
		NodeDB.addNode("IDEPT00603",2333,764,"3","45 Francis","DEPT","Maternal Fetal Practice","D6");
		NodeDB.addNode("IDEPT00703",2400,764,"3","45 Francis","DEPT","Obstetrics","D7");
		NodeDB.addNode("IDEPT00803",2492,887,"3","45 Francis","DEPT","Fetal Med & Genetics","D8");
		NodeDB.addNode("IDEPT00903",2631,851,"3","45 Francis","DEPT","Gynecology","D9");


		//Security Nodes:
		NodeDB.addNode("HDEPT00203",1690,830,"3","Tower","DEPT","MICU 3BC Waiting Room","MICU 3BC WR");
		NodeDB.addNode("WELEV00E01",3265,830,"1","45 Francis","ELEV","Elevator E Floor 1","Elevator E1");
		NodeDB.addNode("ePARK00101",381,1725,"1","Parking","PARK","Left Parking Lot Spot 001","Parking Left 001");
		NodeDB.addNode("ePARK00201",406,1725,"1","Parking","PARK","Left Parking Lot Spot 002","Parking Left 002");
		NodeDB.addNode("eWALK00701",1730,1544,"1","Parking","WALK","Entrance Sidewalk","Walkway");
		NodeDB.addNode("BDEPT00302",2385,753,"2","45 Francis","DEPT","Lee Bell Breast Center","DEPT B0302");
		NodeDB.addNode("BDEPT00402",2439,902,"2","45 Francis","DEPT","Jen Center for Primary Care","DEPT B0402");
		NodeDB.addNode("CCONF002L1",2665,1043,"L1","45 Francis","CONF","Medical Records Conference Room Floor L1","Conf C002L1");

		//External transport:
		NodeDB.addNode("FDEPT00501",2128,1300,"1","Tower","DEPT","Emergency Department","Emergency");
		NodeDB.addNode("EEXIT00101",2275,785,"1","45 Francis","EXIT","Ambulance Parking Exit Floor 1","AmbExit 1");

		connection.addDataForPresentation();
	}

//	@Test
//	@DisplayName("testGetTablesToThatExist")
//	public void testGetTablesToThatExist(){
////		try {
////			connection.deleteAllTables();
////			connection.createNodeTable();
////		} catch (Exception e) {
////			connection.createNodeTable();
////		}
//		ArrayList<String> names = connection.getTablesToThatExist();
//
//		for(String name : names){
//			System.out.println(name);
//		}
//		assertTrue(true);
//
//	}

}