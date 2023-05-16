import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "email")
    WebElement emailInput;

    @FindBy(id = "passwd")
    WebElement passwordInput;

    @FindBy(id = "SubmitLogin")
    WebElement submitButton;

   public void Signin(String email, String password){
       emailInput.sendKeys(email);
       passwordInput.sendKeys(password);
       submitButton.click();

    }
}
