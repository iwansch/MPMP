import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private By loginField = By.xpath("//input[@name='ulogin']");
    private By passwordField = By.xpath("//input[@name='pass']");
    private By loginButton = By.xpath("//input[@class='nobrd']");
    private By logo = By.xpath("//body/div[@class='top_fon']/div[@class='top_logo_fon']/table[@class='bot_right']/tbody/tr/td[@class='bot_left']/table[1]/tbody[1]/tr[1]/td[1]/a[1]");

        public MainPage login(String email, String password) {

            Selenide.open("");
            System.out.println("now");
            Selenide.sleep(2000);
            Selenide.actions().sendKeys(Keys.chord(Keys.COMMAND, Keys.ARROW_DOWN));
            $(loginField).val(email);
            $(passwordField).val(password);
            $(loginButton).click();
            return new MainPage();
        }
}
