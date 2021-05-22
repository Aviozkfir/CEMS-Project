package gui;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import entity.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class btnFolderController {

    @FXML
    private Button bNmae;

    @FXML
    private Text text;

    private  String chosenPath;

    private Object object;
   

	private BiConsumer<String,Object> consumer;
    
    @FXML
    void buttenPressed(ActionEvent event) {
    	consumer.accept(chosenPath,object);
    }


	public void setObject(Object object) {
		this.object = object;
	}

	public void setChosenPath(String chosenPath) {
		this.chosenPath = chosenPath;
	}


	public void setConsumer( BiConsumer<String,Object> consumer) {
		this.consumer = consumer;
	}
	public void setText(String s) {
		this.text.setText(s);
	}

}
