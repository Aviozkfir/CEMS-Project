package gui;

import java.io.IOException;

public interface IFxmlManager {

		public int setPickedYear();

		public TeacherFinalReportControll loadStage(String path) throws IOException;
}
