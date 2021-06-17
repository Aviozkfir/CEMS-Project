import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LoginPageControllerTest.class, TeacherFinalReportControllTest.class,
		TeacherMainReportControllerTest.class })
public class AllTests {

}
