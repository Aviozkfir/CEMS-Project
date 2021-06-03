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

public class PrincipalRequestsController extends PrincipalMainPageController {
	Principal principal = (Principal) guiControl.getUser();
	ArrayList<PrincipalRequestTableRowControl> requestControllerList = new ArrayList<PrincipalRequestTableRowControl> ();

	@FXML
	private GridPane grid;

	@FXML
	private Button Approve;

	@FXML
	private Button Decline;

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
	}
	
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
		
	}
	
	
	
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

	public void GetRequestListFromDB() {
		ClientMessage msg = new ClientMessage(ClientMessageType.PRINCIPAL_REQUESTS_INFORMATION,

				((Principal) guiControl.getUser()).getId());

		guiControl.sendToServer(msg);

		if (guiControl.getServerMsg().getType() == ServerMessageTypes.PRINCIPAL_REQUESTS_ADDED) {

			ArrayList<Request> requestList = (ArrayList<Request>) guiControl.getServerMsg().getMessage();

			principal.setRequestList(requestList);

		} else {

			GUIControl.popUpError("Error in loading request list for Principal");

		}
	}

	public void SendAprrovedRequests(ArrayList<String> ApprovedRequests) {
		ClientMessage msg = new ClientMessage(ClientMessageType.PRINCIPAL_APPROVED_REQUESTS_UPDATE,ApprovedRequests);
		guiControl.sendToServer(msg);

		if (guiControl.getServerMsg().getType() == ServerMessageTypes.PRINCIPAL_APPROVED_REQUESTS_ADDED) {
			GUIControl.popUpError("Approved Requests has sent successfully");
		} else {

			GUIControl.popUpError("Error-sending update for requests");

		}
	}
	public void SendDeclinedRequests(ArrayList<String> DeclinedRequests) {
		ClientMessage msg = new ClientMessage(ClientMessageType.PRINCIPAL_DECLINED_REQUESTS_UPDATE,DeclinedRequests);
		guiControl.sendToServer(msg);

		if (guiControl.getServerMsg().getType() == ServerMessageTypes.PRINCIPAL_DECLINED_REQUESTS_ADDED) {
			GUIControl.popUpError("Declined Requests has sent successfully");
		} else {

			GUIControl.popUpError("Error-sending update for requests");

		}
	}
}
