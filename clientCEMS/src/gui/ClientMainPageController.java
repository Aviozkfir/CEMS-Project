package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import entity.Employee;
import entity.Subscriber;
import entity.Visitor;
import gui.ClientsConstants.AlertType;
import gui.ClientsConstants.Sizes;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * this class is the controller of the client's main page after logging in
 * initially it consists of all user's buttons,but upon being created it removes
 * every button that doesn't concern the connected user additionally it holds
 * the functionality of the client main frames
 */
public class ClientMainPageController implements Initializable {
	private Object user;
	private GUIControl guiControl = GUIControl.getInstance();
	private ArrayList<Button> btnList;
	@FXML
	private AnchorPane enableDisablePane;

	@FXML
	private AnchorPane switchPane;

	@FXML
	private VBox buttonVBox;

	@FXML
	private Button orderBtn;

	@FXML
	private Button entryControlBtn;

	@FXML
	private Button exitControlBtn;

	@FXML
	private Button reportsBtn;

	@FXML
	private Button orderTrakingBtn;

	@FXML
	private Button requestsBtn;

	@FXML
	private Button parametersBtn;

	@FXML
	private Button discountBtn;

	@FXML
	private Button registrationBtn;

	@FXML
	private Label mainLabel;

	@FXML
	private Button homeBtn;

	@FXML
	private Button logOutClick;

	@FXML
	private VBox alertPane;
	@FXML
	private Label alertTitle;
	@FXML
	private ScrollPane alertDesPane;
	@FXML
	private AnchorPane alertBody;
	@FXML
	private HBox alertButtons;

	@FXML
	void EntryControlBtnClick(ActionEvent event) {
		setOpacityOfRestOfButtons((Button) event.getSource());
		loadEntryControl();
		setSwitchPane(panesMap.get("entryControl"));
	}
	/**
	 * when discounts button is pressed, switch to discounts screen
	 * @param event
	 */
	@FXML
	void discountBtnClick(ActionEvent event) {
		setOpacityOfRestOfButtons((Button) event.getSource());
		loadParkManagerDiscountRequests();
		setSwitchPane(panesMap.get("discount"));
	}

	@FXML
	void exitControlBtnClick(ActionEvent event) {
		setOpacityOfRestOfButtons((Button) event.getSource());
		loadExitControl();
		setSwitchPane(panesMap.get("exitControl"));

	}

	@FXML
	void homeBtnClick(ActionEvent event) {
		setOpacityOfRestOfButtons(null);
		if (user instanceof Employee)
			loadEmployeeHome();
		setSwitchPane(panesMap.get("home"));
	}

	@FXML
	void logOutBtnClick(ActionEvent event) {
		guiControl.logOut();
		guiControl.openLoginPage();
	}

	@FXML
	void orderBtnClick(ActionEvent event) {
		setOpacityOfRestOfButtons((Button) event.getSource());
		if (guiControl.getUser() instanceof Employee) {
			loadRegularEmployeeOrder();
			setSwitchPane(panesMap.get("orderRegularEmployee"));
		} else {
			loadSubAndVisitorOrder();
			setSwitchPane(panesMap.get("orderSubAndVisitor"));
		}
	}

	@FXML
	void orderTrakingBtnClick(ActionEvent event) {
		setOpacityOfRestOfButtons((Button) event.getSource());
		loadOrderTracking();
		setSwitchPane(panesMap.get("orderTracking"));
	}
/**
 * when parameters update button is pressed, switch to parameters update screen
 * @param event
 */
	@FXML
	void parametersBtnClick(ActionEvent event) {
		setOpacityOfRestOfButtons((Button) event.getSource());
		loadParkManagerParametersUpdate();
		setSwitchPane(panesMap.get("parameters"));

	}

	/**
	 * 
	 * Switch to registration screen when the registration button is clicked
	 * 
	 */
	@FXML
	void registrationBtnClick(ActionEvent event) {
		setOpacityOfRestOfButtons((Button) event.getSource());
		loadRegistrationAndGuideScreen();
		setSwitchPane(panesMap.get("registration"));
	}

	@FXML
	void requestsBtnClick(ActionEvent event) {// liron
		setOpacityOfRestOfButtons((Button) event.getSource());
		loadRequestsDepManager();
		setSwitchPane(panesMap.get("requests"));
	}

	@FXML
	void reportsBtnClick(ActionEvent event) {
		setOpacityOfRestOfButtons((Button) event.getSource());
		Employee emp = (Employee) user;
		if (emp.getRole().equals("PARK_MANAGER"))
			loadParkManagerReports();
		else
			loadDepartmentManagerReports();
		setSwitchPane(panesMap.get("reports"));

	}

	private Map<String, Pane> panesMap;

	/**
	 * method called to set the user currently connected to the client and show his
	 * main screen and buttons
	 */
	public void setUser(Object user) {
		this.user = user;
		if (user instanceof Visitor) {
			setVisitorAndSubscriberButtons(btnList);
			loadVisitorHome();
		} else if (user instanceof Subscriber) {
			setVisitorAndSubscriberButtons(btnList);
			loadSubscriberHome();
		} else { // employee
			setEmployeeButtons(btnList);
			loadEmployeeHome();
		}
		btnList = new ArrayList<>();
		setBtnList(btnList);
		setSwitchPane(panesMap.get("home"));
	}

	/**
	 * method that adds every button loaded into a button list
	 */
	private void setBtnList(List<Button> list) {
		list.add(orderBtn);
		list.add(entryControlBtn);
		list.add(exitControlBtn);
		list.add(orderTrakingBtn);
		list.add(parametersBtn);
		list.add(registrationBtn);
		list.add(requestsBtn);
		list.add(reportsBtn);
		list.add(discountBtn);
	}

	/**
	 * method that removes every button that doesn't concern a visitor and a
	 * subscriber from display
	 */
	private void setVisitorAndSubscriberButtons(List<Button> list) {
		list.remove(orderBtn);
		list.remove(orderTrakingBtn);
		for (Button b : list) {
			b.setVisible(false);
			b.setManaged(false);
		}
	}

	/**
	 * method that removes every button that doesn't concern a certain employee type
	 * (decided by the type of employee that connected)
	 */
	private void setEmployeeButtons(List<Button> list) {
		Employee emp = (Employee) user;
		switch (emp.getRoleEnum()) {
		case PARK_MANAGER:
			list.remove(parametersBtn);
			list.remove(discountBtn);
			list.remove(reportsBtn);
			break;
		case DEP_MANAGER:
			list.remove(reportsBtn);
			list.remove(requestsBtn);
			break;
		case SERVICE:
			list.remove(registrationBtn);
			break;
		case REGULAR:
			list.remove(orderBtn);
			list.remove(entryControlBtn);
			list.remove(exitControlBtn);
			break;
		}
		for (Button b : list) {
			b.setVisible(false);
			b.setManaged(false);
		}
	}

	/**
	 * method that takes a pane and displays it and it's content in the small window
	 * set for it in the client display
	 */
	public void setSwitchPane(Pane toSwitch) {
		switchPane.getChildren().clear();
		switchPane.getChildren().add(toSwitch);
		AnchorPane.setLeftAnchor(toSwitch, 20.0);
		AnchorPane.setRightAnchor(toSwitch, 20.0);
		AnchorPane.setBottomAnchor(toSwitch, 10.0);
		AnchorPane.setTopAnchor(toSwitch, 0.0);
	}

	private void setOpacityOfRestOfButtons(Button button) {
		if (button != null) {
			for (Button b : btnList) {
				b.setOpacity(0.5);
			}
			button.setOpacity(1);
		} else
			for (Button b : btnList)
				b.setOpacity(1);
	}

	private void loadVisitorHome() {
		FXMLLoader fxmlLoader1 = new FXMLLoader(
				getClass().getResource(ClientConstants.Screens.VISITOR_MAIN_PAGE.toString()));
		VBox root = null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VisitorHomePageController vmpc = fxmlLoader1.getController();
		vmpc.setId(((Visitor) user).getId());
		panesMap.put("home", root);
	}

	private void loadParkManagerReports() {
		FXMLLoader fxmlLoader1 = new FXMLLoader(
				getClass().getResource(ClientConstants.Screens.PARK_MANAGER_REPOTRS.toString()));
		VBox root = null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		panesMap.put("reports", root);
	}
/**
 * a method that switch to parameters and discount requests for department manager
 */
	private void loadRequestsDepManager()
	{
		FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource(ClientConstants.Screens.REQUESTS.toString()));
		VBox root = null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		panesMap.put("requests", root);
	}
/**
 * a method that switch to parameters update screen for park manager
 */
	private void loadParkManagerParametersUpdate()
	{
		FXMLLoader fxmlLoader1 = new FXMLLoader(
				getClass().getResource(ClientConstants.Screens.PARK_MANAGER_PATAMETERS_UPDATE.toString()));
		VBox root = null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		panesMap.put("parameters", root);
	}
/**
 * a method that switch to discount requests for park manager
 */
	private void loadParkManagerDiscountRequests()
	{
		FXMLLoader fxmlLoader1 = new FXMLLoader(
				getClass().getResource(ClientConstants.Screens.PARK_MANAGER_DISCOUNT_REQUESTS.toString()));
		VBox root = null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		panesMap.put("discount", root);
	}

	private void loadDepartmentManagerReports() {
		FXMLLoader fxmlLoader1 = new FXMLLoader(
				getClass().getResource(ClientConstants.Screens.DEPARTMENT_MANAGER_REPOTRS.toString()));
		VBox root = null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// DepartmentManagerReportsController dmrc=fxmlLoader1.getController();
		panesMap.put("reports", root);
	}

	private void loadSubscriberHome() {
		FXMLLoader fxmlLoader1 = new FXMLLoader(getClass()
				.getResource(user instanceof Subscriber ? ClientConstants.Screens.SUBSCRIBER_MAIN_PAGE.toString()
						: ClientConstants.Screens.GUIDE_MAIN_PAGE.toString()));
		VBox root = null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SubscriberHomePageController shpc = fxmlLoader1.getController();
		shpc.setLabels((Subscriber) user);
		panesMap.put("home", root);
	}

	private void loadEmployeeHome() {
		FXMLLoader fxmlLoader1 = new FXMLLoader(
				getClass().getResource(ClientConstants.Screens.EMPLOYEE_MAIN_PAGE.toString()));
		VBox root = null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EmployeeHomePageController ehpc = fxmlLoader1.getController();
		ehpc.setLabels((Employee) user);
		panesMap.put("home", root);
	}

	private void loadSubAndVisitorOrder() {
		FXMLLoader fxmlLoader1 = new FXMLLoader(
				getClass().getResource(ClientConstants.Screens.SUB_AND_VISITOR_ORDER_PAGE.toString()));
		AnchorPane root = null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panesMap.put("orderSubAndVisitor", root);
	}

	private void loadRegularEmployeeOrder() {
		FXMLLoader fxmlLoader1 = new FXMLLoader(
				getClass().getResource(ClientConstants.Screens.REGULAR_ORDER_PAGE.toString()));
		GridPane root = null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// DepartmentManagerReportsController dmrc=fxmlLoader1.getController();
		panesMap.put("orderRegularEmployee", root);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		btnList = new ArrayList<>();
		setBtnList(btnList);
		panesMap = new HashMap<String, Pane>();
		alertTitle.prefWidthProperty().bind(alertPane.widthProperty());
		// alertBody.prefWidthProperty().bind(alertPane.widthProperty());
		alertDesPane.prefHeightProperty().bind(alertPane.heightProperty().subtract(alertTitle.heightProperty())
				.subtract(alertButtons.heightProperty()));
		alertPane.setVisible(false);
		enableDisablePane.disableProperty().bind(alertPane.visibleProperty());
		homeBtn.disableProperty().bind(alertPane.visibleProperty());
		logOutClick.disableProperty().bind(alertPane.visibleProperty());
	}

	/**
	 * Load subscriber and guide registration screen- OR
	 */
	private void loadRegistrationAndGuideScreen() {
		FXMLLoader fxmlLoader1 = new FXMLLoader(
				getClass().getResource(ClientConstants.Screens.REGISTRATION_PAGE.toString()));
		AnchorPane root = null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panesMap.put("registration", root);
	}

	public void hideAlert() {
		this.alertPane.setVisible(false);
	}

	public void showAlert(AlertType alertType, String alertTitleString, Node nodeBody, List<Button> alertButtonsList) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				alertBody.getChildren().clear();
				alertBody.getChildren().add(nodeBody);
				alertTitle.getStyleClass().clear();
				alertTitle.getStyleClass().add(alertType.getAlertTypeStyleClass());
				alertTitle.setText(alertTitleString);
				alertButtons.getChildren().clear();
				AnchorPane.setRightAnchor(nodeBody, 20.0);
				AnchorPane.setLeftAnchor(nodeBody, 20.0);
				if (alertButtonsList == null || alertButtonsList.size() == 0) {
					alertButtons.setVisible(false);
				} else {
					for (Button button : alertButtonsList) {
						alertButtons.getChildren().add(button);
					}
					alertButtons.setVisible(true);
				}
				alertPane.setVisible(true);

			}
		});

	}

	public void showAlert(AlertType alertType, String alertTitle, String alertBody, List<Button> alertButtons) {
		Label aletBodyLabel = new Label(alertBody);
		showAlert(alertType, alertTitle, aletBodyLabel, alertButtons);
	}

	public void showAlertWithOkButton(AlertType alertType, String alertTitle, String alertBody,
			List<Button> alertButtons) {
		if (alertButtons == null)
			alertButtons = new LinkedList<Button>();
		GuiButton okButton = new GuiButton("OK", AlertType.Info, Sizes.Medium);
		okButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				hideAlert();
			}
		});
		alertButtons.add(0, okButton);
		showAlert(alertType, alertTitle, alertBody, alertButtons);
	}

	public void showAlertWithOkButton(AlertType alertType, String alertTitle, Node alertBody,
			List<Button> alertButtons) {
		if (alertButtons == null)
			alertButtons = new LinkedList<Button>();
		GuiButton okButton = new GuiButton("OK", AlertType.Info, Sizes.Medium);
		okButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				hideAlert();
			}
		});
		alertButtons.add(0, okButton);
		showAlert(alertType, alertTitle, alertBody, alertButtons);
	}

	private void loadOrderTracking() {
		FXMLLoader fxmlLoader1 = new FXMLLoader(
				getClass().getResource(ClientConstants.Screens.ORDER_TRACKING.toString()));
		GridPane root = null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panesMap.put("orderTracking", root);
	}

	private void loadEntryControl() {
		FXMLLoader fxmlLoader1 = new FXMLLoader(
				getClass().getResource(ClientConstants.Screens.ENTRY_CONTROL_PAGE.toString()));
		VBox root = null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panesMap.put("entryControl", root);
	}

	private void loadExitControl() {
		FXMLLoader fxmlLoader1 = new FXMLLoader(
				getClass().getResource(ClientConstants.Screens.EXIT_CONTROL_PAGE.toString()));
		VBox root = null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panesMap.put("exitControl", root);
	}
}