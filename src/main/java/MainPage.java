import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;
import static java.lang.String.format;
import static org.openqa.selenium.By.xpath;

public class MainPage {

    private By lettersBlock = xpath("//a[contains(text(),'Письма')]");
    private By tasksBlock = xpath("//a[contains(text(),'Задания')]");
    private By startTaskButton = xpath("//body//input[4]");
    private By startSurfButton = xpath("(//input[@type='submit'])[1]");
    private By confirmTaskButton = xpath("//form[1]//input[3]");
    private By confirmField = xpath("//input[@id='zdtext']");
    private By sendButton = xpath("//tr[@class='line']//td//input");
    private By taskAlreadyDone = xpath("//tr[15]//td[1]");
    private By taskHasHref = xpath("//tr[15]//td[1]/a");
    private By taskHasBalance = xpath("//h1");
    private By ranOutMessage = xpath("//strong[@class='blue']");
    private By firstLetter = xpath("(//table[@class='tbl']//tr//td[2])[1]/a");
    private By blueLink = xpath("(//a[@class='blue'])[4]");
    private By moneyFrame = xpath("//frame[@name='timerfrm']");

    String Button = "//a[%s]";
    private By successMoneyMessage = xpath("//strong[text()='Деньги зачислены']");
    private By message = xpath("//span");

    public MainPage openLetters() {
        Selenide.open("/index.php?cf=mail-viewpmail");
        return this;
    }

    public MainPage openTask(String taskID) {
        Selenide.open("/index.php?cf=uzd-readtask&zdid="+taskID);
        return this;
    }

    public MainPage clickFirstLetter() {
        boolean stop;
        int attempt = 0;
        if (!(($(withText("Письма"))).getText()).equals("Письма")) {stop = false;}
        else { stop = true; System.out.println("There are no letters");}

        while (!stop){//пока первое письмо есть выполняем цикл
            $(firstLetter).click();
            $(blueLink).shouldBe(visible).click();
            switchTo().window(1);
            switchTo().frame($(moneyFrame));
            $(withText("Открыть ссылку в новом окне")).waitUntil(visible, 63000);
        if ($(message).getText().equals("Деньги зачислены")) {
            System.out.println("A letter was read");
            getWebDriver().close();
            switchTo().window(0);
            $(lettersBlock).click();
        }
        else {
            $(xpath(format(Button, 2))).waitUntil(visible, 3000).click();
            attempt = attempt+1;
                if ($(message).waitUntil(visible, 1000).getText().equals("Деньги зачислены")) {
                    System.out.println($(message).getText());
                    System.out.println("Attempt №"+attempt+" is successful");
                    System.out.println("A captcha letter was read");}
                else { System.out.println("Attempt №"+attempt+" failed");}
            getWebDriver().close();
            switchTo().window(0);
            $(lettersBlock).click();
        }
        if (!(($(withText("Письма"))).getText()).equals("Письма")) {stop = false;}
        else { stop = true;
            System.out.println("All the letters are read");
            }
        }
        return this;
    }

    public MainPage clickSurfing () {
        boolean stop;
        if (!(($(withText("Серфинг"))).getText()).equals("Серфинг")) {
            stop = false;
        }
        else { stop = true; System.out.println("There are no letters for surfing");}
        while (!stop){
            $(withText("Серфинг")).click();
            $(startSurfButton).click();
            switchTo().window(1);
            switchTo().frame($(moneyFrame));
            $(withText("Открыть ссылку в новом окне")).waitUntil(visible, 63000);
            boolean success;
            try {$(successMoneyMessage).exists(); success = false;} catch (Exception e) {success = true;}

            if (!success) {
                System.out.println("A letter was read");
                getWebDriver().close();
                switchTo().window(0);
                $(lettersBlock).click();
            }
            else {
                try {Thread.sleep(4000);} catch (InterruptedException e) {}
                $(xpath(format(Button, 2))).shouldBe(visible).click();
                try {Thread.sleep(4000);} catch (InterruptedException e) {}
                getWebDriver().close();
                switchTo().window(0);
                $(lettersBlock).click();
            }
            if (!(($(withText("Серфинг"))).getText()).equals("Серфинг")) {stop = false;}
            else { stop = true;
                System.out.println("All the surfing letters are read");
            }
        }
        return this;
}

        public MainPage doingTask(String answer) {

        if ($(taskHasBalance).getText().contains("Оплачиваемое задание")) {
            if ($(startTaskButton).isDisplayed()) {
                $(startTaskButton).click();
                switchTo().window(1);
                getWebDriver().close();
                switchTo().window(0);
                $(confirmTaskButton).click();
                $(confirmField).sendKeys(answer);
                $(sendButton).click();
                return this;
            }
            else if ($(taskHasHref).exists()) {
                $(confirmTaskButton).click();
                $(confirmField).sendKeys(answer);
                $(sendButton).click();
                return this;
            }
            else {
                System.out.println($(taskAlreadyDone).getText());
                System.out.println("The task is not available");
                return this;
            }
        }
        else {
            System.out.println(($(ranOutMessage).getText())+"На балансе задания закончились деньги!");
            return this;
        }
    }

    public Double getBalance() {
        Double balance = Double.parseDouble($("#ubalance").getText());
        return balance;
    }
}
