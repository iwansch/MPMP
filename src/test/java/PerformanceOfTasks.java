import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PerformanceOfTasks {

    @BeforeClass()
    public static void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.startMaximized = true;
        Configuration.holdBrowserOpen = false;
    }

    @Test()
    public void doEKTask () {
        EK EK = new EK();
        EK.doEKTask();
    }
}
