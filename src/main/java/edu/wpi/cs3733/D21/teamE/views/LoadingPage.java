/**
 * Sample Skeleton for 'LoadingPage.fxml' Controller Class
 */

package edu.wpi.cs3733.D21.teamE.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.wpi.cs3733.D21.teamE.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class LoadingPage {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="appBarAnchorPane"
	private AnchorPane appBarAnchorPane; // Value injected by FXMLLoader

	@FXML // fx:id="stackPane"
	private StackPane stackPane; // Value injected by FXMLLoader

	@FXML // fx:id="hospitalImageView"
	private ImageView hospitalImageView; // Value injected by FXMLLoader

	@FXML // fx:id="imageAnchorPane"
	private AnchorPane imageAnchorPane; // Value injected by FXMLLoader

	@FXML // fx:id="rightAnchorPane"
	private AnchorPane rightAnchorPane; // Value injected by FXMLLoader

	@FXML // fx:id="logoImageView"
	private ImageView logoImageView; // Value injected by FXMLLoader

	@FXML // fx:id="covidSurvey"
	private JFXButton covidSurvey; // Value injected by FXMLLoader

	@FXML // fx:id="pathFinderButton"
	private JFXButton pathFinderButton; // Value injected by FXMLLoader

	@FXML // fx:id="ScanQRCodeButton"
	private JFXButton ScanQRCodeButton; // Value injected by FXMLLoader

	@FXML // fx:id="DirectionsButton"
	private JFXButton DirectionsButton; // Value injected by FXMLLoader

	@FXML // fx:id="scheduleAppointmentButton"
	private JFXButton scheduleAppointmentButton; // Value injected by FXMLLoader

	@FXML // fx:id="serviceRequestButton"
	private JFXButton serviceRequestButton; // Value injected by FXMLLoader

	@FXML // fx:id="covidSurveyStatusButton"
	private JFXButton covidSurveyStatusButton; // Value injected by FXMLLoader

	@FXML // fx:id="userManagementButton"
	private JFXButton userManagementButton; // Value injected by FXMLLoader

	@FXML // fx:id="mapEditorButton"
	private JFXButton mapEditorButton; // Value injected by FXMLLoader

	@FXML // fx:id="carParkedText"
	private Label carParkedText; // Value injected by FXMLLoader

	@FXML // fx:id="LinkToParking"
	private Hyperlink LinkToParking; // Value injected by FXMLLoader

	@FXML // fx:id="algoTextTop"
	private Label algoTextTop; // Value injected by FXMLLoader

	@FXML // fx:id="algoTextBottom"
	private Label algoTextBottom; // Value injected by FXMLLoader

	@FXML // fx:id="algo"
	private JFXComboBox<?> algo; // Value injected by FXMLLoader

	@FXML // fx:id="applyChange"
	private JFXButton applyChange; // Value injected by FXMLLoader

	@FXML
	void changeAlgo(ActionEvent event) {

	}

	@FXML
	void switchScene(ActionEvent event) {

	}

	@FXML
	void toDirections(ActionEvent event) {

	}

	@FXML
	void toParking(ActionEvent event) {

	}

	@FXML
	void toPathFinder(ActionEvent event) {

	}

	@FXML
	void toScanQRCode(ActionEvent event) {

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {

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

		assert appBarAnchorPane != null : "fx:id=\"appBarAnchorPane\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert stackPane != null : "fx:id=\"stackPane\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert hospitalImageView != null : "fx:id=\"hospitalImageView\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert imageAnchorPane != null : "fx:id=\"imageAnchorPane\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert rightAnchorPane != null : "fx:id=\"rightAnchorPane\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert logoImageView != null : "fx:id=\"logoImageView\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert covidSurvey != null : "fx:id=\"covidSurvey\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert pathFinderButton != null : "fx:id=\"pathFinderButton\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert ScanQRCodeButton != null : "fx:id=\"ScanQRCodeButton\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert DirectionsButton != null : "fx:id=\"DirectionsButton\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert scheduleAppointmentButton != null : "fx:id=\"scheduleAppointmentButton\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert serviceRequestButton != null : "fx:id=\"serviceRequestButton\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert covidSurveyStatusButton != null : "fx:id=\"covidSurveyStatusButton\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert userManagementButton != null : "fx:id=\"userManagementButton\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert mapEditorButton != null : "fx:id=\"mapEditorButton\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert carParkedText != null : "fx:id=\"carParkedText\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert LinkToParking != null : "fx:id=\"LinkToParking\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert algoTextTop != null : "fx:id=\"algoTextTop\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert algoTextBottom != null : "fx:id=\"algoTextBottom\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert algo != null : "fx:id=\"algo\" was not injected: check your FXML file 'LoadingPage.fxml'.";
		assert applyChange != null : "fx:id=\"applyChange\" was not injected: check your FXML file 'LoadingPage.fxml'.";

	}
}
