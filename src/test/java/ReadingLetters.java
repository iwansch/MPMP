import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.*;
import java.io.*;
import static com.codeborne.selenide.Configuration.*;

public class ReadingLetters {

    private static LoginPage LoginPage;
    @BeforeClass()
    public static void setUp() {
        Configuration.headless = true;
        Configuration.startMaximized = true;
        Configuration.timeout = 60000;
        Configuration.pageLoadStrategy = "none";
        baseUrl = ("http://wmmail.ru");
    }

    @DataProvider(name = "Credentials")
    public Object[][] provideData() throws IOException {
        String usernameIw = getCreds(1,1,1);
        String passwordIw = getCreds(1,1,2);
        String usernameSig = getCreds(1,5,1);
        String passwordSig = getCreds(1,5,2);
        String usernameVla = getCreds(1,7,1);
        String passwordVla = getCreds(1,7,2);

        return new Object[][] {
                { usernameSig, passwordSig },
                { usernameIw, passwordIw },
                { usernameVla, passwordVla }
        };
    }

    public String getCreds (int sheetNum, int rowNum, int cellNum) throws IOException {
        File src=new File("/Users/ivan/Google Диск/Working/Credentials/Credentials.xlsx");
        return new XSSFWorkbook(new FileInputStream(src)).getSheetAt(sheetNum).getRow(rowNum).getCell(cellNum).toString();
    }

    @Test(dataProvider = "Credentials")
    public void letters (String login, String password) {
        String resetText = "\033[0m";
        String textInGREEN = "\033[1;92m";
        LoginPage = new LoginPage();
        Selenide.clearBrowserCookies();
        MainPage newMainPage = LoginPage.login(login, password);
        Double previousBalance = newMainPage.getBalance();
        System.out.println();
        System.out.println("$$$ previousBalance = "+previousBalance);
        System.out.println();
        newMainPage.openLetters().clickFirstLetter();
        Selenide.refresh();
        Double lastBalance = newMainPage.getBalance();
        System.out.println();
        System.out.println("$$$lastBalance = "+lastBalance);
        System.out.println();
        System.out.println("Reading letters for <"+login+"> is completed!");
        System.out.println(
                "Balance increases on " +
                (String.format("%.1f", (lastBalance - previousBalance)*1000)) +
                "¢ = " +
                textInGREEN +
                (String.format("%.2f", (lastBalance - previousBalance)*2400)) +
                "kon." +
                resetText);
    }

    @Test(dataProvider = "Credentials")
    public void surfing (String login, String password) {
        LoginPage = new LoginPage();
        Selenide.clearBrowserCookies();
        MainPage newMainPage = LoginPage.login(login, password);
        Double previousBalance =  newMainPage.getBalance();
        System.out.println("previousBalance = "+previousBalance);
        newMainPage.clickSurfing();
        Selenide.refresh();
        Double lastBalance =  newMainPage.getBalance();
        System.out.println("lastBalance = "+lastBalance);
        System.out.println("Reading letters for <"+login+"> is completed!");
        System.out.println("Balance increases on "+(String.format("%.1f", (lastBalance - previousBalance)*1000))+"¢ = "+(String.format("%.2f", (lastBalance - previousBalance)*2400))+"kon.");

    }

    @Test(dataProvider = "Credentials")
    public void tasks (String login, String password) {

        String taskNumber = "1830046";//голосование
        String answer = "байден";

        LoginPage = new LoginPage();
        Selenide.clearBrowserCookies();
        MainPage newMainPage = LoginPage.login(login, password);
        Double previousBalance =  newMainPage.getBalance();
        System.out.println("previousBalance = "+previousBalance);
        newMainPage.openTask(taskNumber).doingTask(answer);
        Selenide.refresh();
        Double lastBalance =  newMainPage.getBalance();
        System.out.println("lastBalance = "+lastBalance);
        System.out.println("The task for <"+login+"> is completed!");
        System.out.println();
        System.out.println("Balance increases on "+(String.format("%.1f", (lastBalance - previousBalance)*1000))+"¢ = "+(String.format("%.2f", (lastBalance - previousBalance)*2750))+"kon.");
    }
}