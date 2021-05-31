package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.PersonCEMS;
import entity.Principal;
import entity.Request;
import entity.Teacher;
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
	PersonCEMS person = (PersonCEMS) guiControl.getUser();

	@FXML
	private GridPane grid;

	@FXML
	private Button Approve;

	@FXML
	private Button Decline;

	@FXML
	void ApproveButtonPressed(ActionEvent event) throws IOException {
		PrincipalRequestTableRowControl controller;
		
		
	}
	
	@FXML
	void DeclineButtonPressed(ActionEvent event) throws IOException {

		
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

			((Principal) person).setRequestList(requestList);

		} else {

			GUIControl.popUpError("Error in loading request list for Principal");

		}
	}

}
