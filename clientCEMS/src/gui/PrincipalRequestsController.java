package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Principal;
import entity.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;
/**
 * @author On Avioz, Kfir Avioz.
 * Controller of principal "Request" screen.
 * @extends PrincipalMainPageController.
 */
public class PrincipalRequestsController extends PrincipalMainPageController {
	/**
	 * The principal instance.
	 */
	Principal principal = (Principal) guiControl.getUser();
	/**
	 * Request controller list that holds all dynamic requests that we have added.
	 */
	ArrayList<PrincipalRequestTableRowControl> requestControllerList = new ArrayList<PrincipalRequestTableRowControl> ();

	/**
	 * The grid for requests.
	 */
	@FXML
	private GridPane grid;

	/**
	 * Approve button.
	 */
	@FXML
	private Button Approve;

	/**
	 * Decline button.
	 */
	@FXML
	private Button Decline;

	/**
	 * @param event ActionEvent
	 * @throws IOException
	 * This method works when approve button pressed, 
	 * he sets all chose requests status from "StandBy" to "Approved" in DB.
	 */
	@FXML
	void ApproveButtonPressed(ActionEvent event) throws IOException {
		ArrayList<String> CheckedExams = new ArrayList<String>();
		ArrayList<Request> requestList = principal.getRequestList();
		for(int i = 0 ; i< requestList.size();i++) {
			if(requestList.get(i).getcBox()) {
				CheckedExams.add(requestList.get(i).getNum());
			}
		}
		SendAprrovedRequests(CheckedExams);
		CheckIfEmpty(CheckedExams);
	}
	
	
	/**
	 * @param event ActionEvent
	 * @throws IOException
	 * This method works when decline button pressed, 
	 * he sets all chose requests status from "StandBy" to "Declined" in DB.
	 */
	@FXML
	void DeclineButtonPressed(ActionEvent event) throws IOException {
		ArrayList<String> CheckedExams = new ArrayList<String>();
		ArrayList<Request> requestList = principal.getRequestList();
		for(int i = 0 ; i< requestList.size();i++) {
			if(requestList.get(i).getcBox()) {
				CheckedExams.add(requestList.get(i).getNum());
			}
		}
		SendDeclinedRequests(CheckedExams);
		CheckIfEmpty(CheckedExams);
	}
	
	/**
	 * This method sending request message to server, and getting back requests list, setting requests dynamically inside the grid.
	 * setting new requests text if necessary.
	 * the user can choose multiple requests.
	 */
	public void setPrincipalRequests() {
		int i;
		Principal principal = (Principal) guiControl.getUser();
		ArrayList<Request> requestList = principal.getRequestList();
		
		for (i = 0; i < requestList.size(); i++)
			try {

				PrincipalRequestTableRowControl controller;
				FXMLLoader fxmlLoader = new FXMLLoader(
						getClass().getResource(ClientsConstants.Screens.PRINCIPAL_REQUEST_TABLE_ROW.path));
				AnchorPane root = fxmlLoader.load();
				controller = (PrincipalRequestTableRowControl) fxmlLoader.getController();
				controller.setChosenPath(ClientsConstants.Screens.PRINCIPAL_REQUESTS_PAGE.path);
				controller.setObject(requestList.get(i));
				controller.setNum(requestList.get(i).getNum());
				controller.setTitle(requestList.get(i).getTitle());
				controller.setCurrentDuration(requestList.get(i).getCurrentDuration());
				controller.setNewDuration(requestList.get(i).getNewDuration());
				controller.setConsumer((fxmlLocation, request) -> {
					try {
						PrincipalRequestsController contr = (PrincipalRequestsController) GUIControl.instance
								.loadStage(fxmlLocation);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				});

				grid.add(root, 0, i);
				requestControllerList.add(controller);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	/**
	 * This method sending request msg, and gets requests list from DB.
	 */
	public void GetRequestListFromDB() {
		ClientMessage msg = new ClientMessage(ClientMessageType.PRINCIPAL_REQUESTS_INFORMATION,

				((Principal) guiControl.getUser()).getId());

		guiControl.sendToServer(msg);

		if (guiControl.getServerMsg().getType() == ServerMessageTypes.PRINCIPAL_REQUESTS_ADDED) {

			ArrayList<Request> requestList = (ArrayList<Request>) guiControl.getServerMsg().getMessage();

			principal.setRequestList(requestList);
			
			ArrayList<String> requestNumList = new ArrayList<String>();
			for(int i = 0 ; i< requestList.size();i++) {
				requestNumList.add(requestList.get(i).getNum());
			}
			UpdateRequestStatus(requestNumList);

		} else {

			GUIControl.popUpError("Error in loading request list for Principal");

		}
	}

	/**
	 * @param ApprovedRequests array list that holds all chose requests that principal want to approve.
	 * This method sending request msg, and sets the chose requests as "Approved" in DB.
	 */
	public void SendAprrovedRequests(ArrayList<String> ApprovedRequests) {
		ClientMessage msg = new ClientMessage(ClientMessageType.PRINCIPAL_APPROVED_REQUESTS_UPDATE,ApprovedRequests);
		guiControl.sendToServer(msg);

		if (guiControl.getServerMsg().getType() == ServerMessageTypes.PRINCIPAL_APPROVED_REQUESTS_ADDED) {
			
		} else {

			GUIControl.popUpError("Error-sending update for requests");

		}
	}
	/**
	 * @param DeclinedRequests array list that holds all chose requests that principal want to decline.
	 * This method sending request msg, and sets the chose requests as "Approved" in DB.
	 */
	public void SendDeclinedRequests(ArrayList<String> DeclinedRequests) {
		ClientMessage msg = new ClientMessage(ClientMessageType.PRINCIPAL_DECLINED_REQUESTS_UPDATE,DeclinedRequests);
		guiControl.sendToServer(msg);

		if (guiControl.getServerMsg().getType() == ServerMessageTypes.PRINCIPAL_DECLINED_REQUESTS_ADDED) {
		} else {

			GUIControl.popUpError("Error-sending update for requests");

		}
	}
	
	/**
	 * @param allRequests array list that holds all requests.
	 * This method update for all requests the status2 from "Waiting" to "Working" in DB, when principal clicks on request button in menu.
	 */
	public void UpdateRequestStatus(ArrayList<String> allRequests) {
		ClientMessage msg = new ClientMessage(ClientMessageType.PRINCIPAL_UPDATE_REQUESTS_STATUS,allRequests);
		guiControl.sendToServer(msg);

		if (guiControl.getServerMsg().getType() == ServerMessageTypes.PRINCIPAL_UPDATE_REQUESTS_STATUS_SUCCESS) {
		} else {
			GUIControl.popUpError("Error-sending update for requests status");
		}
		
	}
	
	/**
	 * @param CheckedRequests Array list that holds all the checked requests and check if there is requests that go checked.
	 * if none requests checked , the user gets error pop up , else the user get success pop up.  
	 */
	public void CheckIfEmpty(ArrayList<String> CheckedRequests) {
		if(CheckedRequests.isEmpty()) {
			GUIControl.popUpError("Error - No requests has been chosed");
		}
		else {
			GUIControl.popUpError("Requests has sent successfully");
		}
	}
}
