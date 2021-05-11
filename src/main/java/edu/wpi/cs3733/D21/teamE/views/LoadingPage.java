package edu.wpi.cs3733.D21.teamE.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import edu.wpi.cs3733.D21.teamE.App;
import edu.wpi.cs3733.D21.teamE.DB;
import edu.wpi.cs3733.D21.teamE.QRCode;
import edu.wpi.cs3733.D21.teamE.database.UserAccountDB;
import edu.wpi.cs3733.D21.teamE.map.Node;
import edu.wpi.cs3733.D21.teamE.states.DefaultState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoadingPage {

	public static String previousScannedResult = null;

	@FXML
	private AnchorPane appBarAnchorPane;

	@FXML // fx:id="imageView"
	private ImageView hospitalImageView;

	@FXML // fx:id="imageView"
	private ImageView logoImageView;

	@FXML // fx:id="imageAnchorPane"
	private AnchorPane imageAnchorPane;

	@FXML // fx:id="rightAnchorPane"
	private AnchorPane rightAnchorPane;

	@FXML // fx:id="stackPane"
	private StackPane stackPane; //main stack pane used for JFXDialog popups

	@FXML // fx:id="mapEditorButton"
	private JFXButton mapEditorButton;

	@FXML // fx:id="serviceRequestButton"
	private JFXButton serviceRequestButton;

	@FXML // fx:id="userManagementButton"
	private JFXButton userManagementButton;

	@FXML // fx:id="scheduleAppointmentButton"
	private JFXButton scheduleAppointmentButton;

	@FXML // fx:id="covidSurveyStatusButton"
	private JFXButton covidSurveyStatusButton;

	@FXML // fx:id="algo"
	private JFXComboBox algo;

	@FXML // fx:id="applyChange"
	private JFXButton applyChange;

	@FXML // fx:id="algoTextTop"
	private Label algoTextTop;

	@FXML // fx:id="algoTextBottom"
	private Label algoTextBottom;

	@FXML // fx:id="carParkedText"
	private Label carParkedText;

	@FXML // fx:id="LinkToParking"
	private Hyperlink LinkToParking;

	@FXML // fx:id="imageStackPane"
	private StackPane imageStackPane;

	@FXML
	private JFXSpinner loadingSpinner;




	/**
	 * Switch to a different scene
	 * @param e tells which button was pressed
	 */
	@FXML
	private void switchScene(ActionEvent e) {
		DefaultState defaultState = new DefaultState();
		defaultState.switchScene(e);
	}

//    @FXML
//    private void toAppointmentPage(ActionEvent e) {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D21/teamE/fxml/updatedServiceRequests/Appointment.fxml"));
//            App.setDraggableAndChangeScene(root);
//        } catch (IOException ex) {
//            System.out.println("Hi");
//            ex.printStackTrace();
//        }
//    }


	public void initialize() {

		//init appBar
		javafx.scene.Node appBarComponent = null;
		try {
			App.setShowHelp(false);
			App.setShowLogin(true);
			App.setStackPane(stackPane);
			App.setPageTitle("Home");
			App.setHelpText(""); //todo help text
			appBarComponent = FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D21/teamE/fxml/AppBarComponent.fxml"));
			appBarAnchorPane.getChildren().add(appBarComponent); //add FXML to this page's anchorPane element
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Set up images
		Stage primaryStage = App.getPrimaryStage();

		Image hospital = new Image("edu/wpi/cs3733/D21/teamE/hospital.jpg");
		hospitalImageView.setImage(hospital);
//		hospitalImageView.setEffect(new GaussianBlur());
		hospitalImageView.setPreserveRatio(true);

		hospitalImageView.fitHeightProperty().bind(primaryStage.heightProperty());
		hospitalImageView.fitWidthProperty().bind(primaryStage.widthProperty());
		imageAnchorPane.prefWidthProperty().bind(primaryStage.widthProperty());
		imageAnchorPane.prefHeightProperty().bind(primaryStage.heightProperty());


		Image logo = new Image("edu/wpi/cs3733/D21/teamE/fullLogo.png");
		logoImageView.setImage(logo);
		logoImageView.setPreserveRatio(true);
		//logoImageView.fitWidthProperty().bind(rightAnchorPane.widthProperty());
		rightAnchorPane.prefWidthProperty().bind(primaryStage.widthProperty());
		rightAnchorPane.prefHeightProperty().bind(primaryStage.heightProperty());



		hospitalImageView.setEffect(new GaussianBlur());
		rightAnchorPane.setEffect(new GaussianBlur());


		loadingSpinner.setRadius(50);


//		//Set up algorithm choices
//		algoNames = FXCollections.observableArrayList();
//		algoNames.add("A* Search");
//		algoNames.add("Depth First Search");
//		algoNames.add("Breadth First Search");
//		algoNames.add("Dijkstra Search");
//		algoNames.add("Best First");
//
//		algo.setItems(algoNames);
//		algo.setValue(algoNames.get(App.getSearchAlgo()));
//
//		String userType = UserAccountDB.getUserType(App.userID);
//		if(App.userID == 0) {
//			serviceRequestButton.setVisible(false);
//		}
//		if(!userType.equals("admin")) {
//			mapEditorButton.setVisible(false);
//			algoTextTop.setVisible(false);
//			algoTextBottom.setVisible(false);
//			algo.setVisible(false);
//			applyChange.setVisible(false);
//			userManagementButton.setVisible(false);
//			covidSurveyStatusButton.setVisible(false);
//		}


	}


}

