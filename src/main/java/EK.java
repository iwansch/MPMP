import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.format;
import static org.openqa.selenium.By.xpath;

public class EK {

    private By citySelect = xpath("//div[@class='ib h']");
    private By cityField = xpath("//div[@class='posr']//input");
    private By allLinks = xpath("//td[@class='model-shop-name']//a");

    String link = "(//td[@class='model-shop-name']//a)[%s]";

    public EK doEKTask() {
        Selenide.open("http://ek.ua/m217.htm");
        $(citySelect).click();
        $(cityField).val("Харьков").sendKeys(Keys.ENTER);
        Selenide.open("https://ek.ua/ek-list.php?search_=Minerva+M819B&katalog_from_search_=217");
        int linksAmount = $$(allLinks).size();
        for (int i = 1; i <linksAmount + 1 ; i++) { $(xpath(format(link, i))).click();}
        return this;
    }
}
