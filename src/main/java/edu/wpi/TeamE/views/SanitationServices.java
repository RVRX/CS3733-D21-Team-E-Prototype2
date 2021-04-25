package edu.wpi.TeamE.views;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.TeamE.App;
import edu.wpi.TeamE.databases.makeConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import edu.wpi.TeamE.databases.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;

public class SanitationServices extends ServiceRequestFormComponents {


  @FXML private JFXTextField assignedIndividual;
  @FXML private JFXTextField Signature;
  @FXML private JFXTextArea detailedInstructionsInput;


  @FXML private JFXComboBox<String> locationInput;

  @FXML private JFXComboBox<String> ServiceTypeinput;
  @FXML private JFXComboBox<String> Severity;
  @FXML private JFXButton cancel;
  @FXML private JFXButton submit;


  @FXML private RequiredFieldValidator validator = new RequiredFieldValidator();




  /**
   * Sets value of drop down list to the selected value(departments)
   * @param actionEvent
   */

  /**
   * Detects if the user has entered all required fields
   *
   */
  private boolean validateInput(){

    validator.setMessage("Input required");

    ServiceTypeinput.getValidators().add(validator);
    assignedIndividual.getValidators().add(validator);
    locationInput.getValidators().add(validator);

    detailedInstructionsInput.getValidators().add(validator);
    Signature.getValidators().add(validator);
    Severity.getValidators().add(validator);

    return locationInput.validate() && ServiceTypeinput.validate() && assignedIndividual.validate() && detailedInstructionsInput.validate() && Severity.validate() && Signature.validate();


  }


  /**
   * records inputs from user into a series of String variables and returns to the main page
   * @param actionEvent
   */
  @FXML
  private void saveData(ActionEvent actionEvent){


    if(validateInput()){
      //String detailedInstructions = sdetailedInstructionsInput.getText();
      //creating the service request

      //System.out.println(request.getAssignmentField());
      //Adding service request to table
      //makeConnection connection = makeConnection.makeConnection();
      //connection.addRequest("sanitationServices", request);
      ArrayList<String> nodeIDS = NodeDB.getListOfNodeIDS();
      String serviceKind = ServiceTypeinput.getValue();
      int assigneeID = 99999;
      String details = detailedInstructionsInput.getText();
      String severity = Severity.getValue();
      String signature = Signature.getText();
      int nodeIDIndex = locationInput.getSelectionModel().getSelectedIndex();
      String nodeID = nodeIDS.get(nodeIDIndex);
      RequestsDB.addSanitationRequest(15,assigneeID,nodeID, serviceKind,details,severity,signature);
      //DB changed the assignee in the function call to an int (not string) --> we need the assignee's userID
      System.out.println(serviceKind);

      super.handleButtonSubmit(actionEvent);
      //Setting up all variables to be entered
    }




  }


  @FXML
  void initialize(){

    assert ServiceTypeinput != null : "fx:id=\"ServiceTypeinput\" was not injected: check your FXML file '/edu/wpi/TeamE/fxml/Sanitation.fxml'.";

    ObservableList<String> Services  = FXCollections.observableArrayList();
    Services.setAll("Urine Cleanup","Feces Cleanup","Trash Removal");

    ServiceTypeinput.setItems(Services);

    assert  locationInput != null : "fx:id=\"locationInput\" was not injected: check your FXML file '/edu/wpi/TeamE/fxml/Sanitation.fxml'.";
    ObservableList<String> locations  = NodeDB.getAllNodeLongNames();

    locationInput.setItems(locations);
    assert Severity != null : "fx:id=\"Severity\" was not injected: check your FXML file '/edu/wpi/TeamE/fxml/Sanitation.fxml'.";
    ObservableList<String> rating  = FXCollections.observableArrayList();
    rating.setAll("Low","Medium","High","Critical");
    Severity.setItems(rating);

    assert assignedIndividual != null : "fx:id=\"assignedIndividual\" was not injected: check your FXML file '/edu/wpi/TeamE/fxml/Sanitation.fxml'.";
    assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file '/edu/wpi/TeamE/fxml/ExternalPatient.fxml'.";
    assert submit != null : "fx:id=\"submit\" was not injected: check your FXML file '/edu/wpi/TeamE/fxml/ExternalPatient.fxml'.";

  }
}
