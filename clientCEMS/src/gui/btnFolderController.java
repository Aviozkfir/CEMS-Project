package gui;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import entity.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * Controller for Folders in exams,questions, each folder is a button.
 * 
 * @author Sharon,Guy.
 * 
 */
public class btnFolderController {
	/**
	 * button name
	 */
	@FXML
	private Button bNmae;
	/**
	 * text on folder
	 */
	@FXML
	private Text text;
	/**
	 * path for the button
	 */
	private String chosenPath;
	/**
	 * instance of object
	 */
	private Object object;
	/**
	 * consumer that gets string and object for the lambda experssion that sends to
	 * the right path when clicking on the button
	 */
	private BiConsumer<String, Object> consumer;

	/**
	 * moving the user to the right path when clicking on the folder
	 * 
	 * @param event
	 * 
	 */
	@FXML
	void buttenPressed(ActionEvent event) {
		consumer.accept(chosenPath, object);
	}

	/**
	 * setter for object
	 * 
	 * @param object
	 * 
	 */
	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * setter for path that has chosen
	 * 
	 * @param chosenPath
	 * 
	 */
	public void setChosenPath(String chosenPath) {
		this.chosenPath = chosenPath;
	}

	/**
	 * setter for the constumer that gets the object and the path
	 * 
	 * @param consumer
	 * 
	 */
	public void setConsumer(BiConsumer<String, Object> consumer) {
		this.consumer = consumer;
	}

	/**
	 * setter for the text on the folder.
	 * 
	 * @param consumer
	 * 
	 */
	public void setText(String s) {
		this.text.setText(s);
	}

}
