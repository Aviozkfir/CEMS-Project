package gui;

import java.io.IOException;
import java.util.function.BiConsumer;

import entity.PersonCEMS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class PrincipalRequestTableRowControl {
	
	@FXML
	private CheckBox cBox;

	@FXML
	private Label num;

	@FXML
	private Label title;

	@FXML
	private Label currentDuration;

	@FXML
	private Label newDuration;

	private String chosenPath;

	private String TeacherID;

	private Object object;

	private BiConsumer<String, Object> consumer;

	@FXML
	void buttenPressed(ActionEvent event) {
		consumer.accept(chosenPath, object);
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public void setChosenPath(String chosenPath) {
		this.chosenPath = chosenPath;
	}

	public void setConsumer(BiConsumer<String, Object> consumer) {
		this.consumer = consumer;
	}

	public void setNum(String s) {
		this.num.setText(s);
	}

	public void setTitle(String Title) {
		this.title.setText(Title);
	}

	public void setCurrentDuration(String Cduration) {
		this.currentDuration.setText(Cduration);
	}

	public void setNewDuration(String Nduration) {
		this.newDuration.setText(Nduration);
	}

	public void setTeacherID(String Tid) {
		this.TeacherID = Tid;
	}
	
	@FXML
	void CBoxPressed(ActionEvent event) throws IOException {
		boolean check = cBox.isSelected();
		
		
	}

}
