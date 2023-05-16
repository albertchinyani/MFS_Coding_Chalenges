import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class CatalogPage {
    WebDriver driver;

    public CatalogPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath="//a[@title='Women']")
    WebElement womenMenu ;
    @FindBy(xpath = "//a[@title='Dresses']")
    WebElement dressesSubMenu ;
    @FindBy(xpath = "//a[@title='Evening Dresses']")
    WebElement eveningDressesSubMenu;
    @FindBy(xpath = "//a[@title='Catalog']")
    WebElement catalogMenu ;
    @FindBy(xpath = "//label[contains(text(), 'M')]")
    WebElement sizeFilter;
    @FindBy(xpath = "//label[@style='background-color: pink;']")
    WebElement colorFilter ;
    @FindBy(xpath = "//div[@id='slider_price']/a[2]")
    WebElement priceRangeSlider;
    @FindBy(xpath = "//a[@class='product-name'][contains(text(), 'Printed Chiffon Dress')]")
    WebElement product;
    @FindBy(xpath ="//input[@id='quantity_wanted']" )
    WebElement quantityInput;
    @FindBy(xpath ="//select[@id='group_1']" )
    WebElement sizeSelect;
    @FindBy(xpath = "//a[@name='Pink']")
    WebElement colorSelect;
    @FindBy(xpath = "//span[contains(text(),'Add to cart')]")
    WebElement addToCartButton;
    @FindBy(xpath ="//span[@id='layer_cart_product_quantity']" )
    WebElement productQuantity ;
    @FindBy(xpath = "//span[@id='layer_cart_product_attributes']")
    WebElement productSize ;
    @FindBy(xpath ="//span[@id='layer_cart_product_color']" )
    WebElement productColor;
    @FindBy(xpath = "//span[@id='layer_cart_product_price']")
    WebElement productPrice;
    @FindBy(xpath = "//span[@class='ajax_cart_shipping_cost']")
    WebElement shippingCost;
    @FindBy(xpath = "//span[@id='layer_cart_product_price']")
    WebElement totalProductCost;



    public void selectProduct() {
        // Scroll up to the top of the page
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollTo(0, 0)");

        // Navigate to Women >> Dresses >> Evening Dresses
        Actions actions = new Actions(driver);
        actions.moveToElement(womenMenu).perform();
        actions.moveToElement(dressesSubMenu).perform();
        eveningDressesSubMenu.click();

        // Go to Catalog and filter out a dress: Size (M) >> Color (Pink) >> Set Range: $50.00 - $52.28
        catalogMenu.click();
        sizeFilter.click();
        colorFilter.click();
        int xOffset = 50;
        int yOffset = 0;
        actions.dragAndDropBy(priceRangeSlider, xOffset, yOffset).perform();

        // Once the entry is found, click on More.
        product.click();

        // Set Quantity= 3 >> Size= M >> Colour=Pink
        quantityInput.clear();
        quantityInput.sendKeys("3");
        sizeSelect.click();
        sizeSelect.findElement(By.xpath("//option[contains(text(),'M')]")).click();
        colorSelect.click();

        // Click on Add to Cart
        addToCartButton.click();
    }

    public void verifyProductDetailsAndTotalCost() {

        // Wait for the product popup to appear and switch to it
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement productPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='layer_cart']")));

        driver.switchTo().frame(productPopup);

        // Verify quantity, size, colour and total cost of the product on the pop-up

        assert productQuantity.getText().equals("3") : "Product quantity is incorrect";
        assert productSize.getText().equals("M") : "Product size is incorrect";
        assert productColor.getText().equals("Pink") : "Product color is incorrect";
        assert productPrice.getText().equals("$52.56") : "Product price is incorrect";

        // Find out the shipping cost
        // Verify total cost (total product cost + shipping cost)
        String totalCostString = productPopup.findElement(By.xpath("//span[@id='layer_cart_total']"))
                .getText().replace("$", "");
        double totalCost = Double.parseDouble(totalCostString);

        assert totalCost == Double.parseDouble(totalProductCost.getText().replace("$", ""))
                + Double.parseDouble(shippingCost.getText().replace("$", ""))
                : "Total cost is incorrect";

        // Switch back to the main window
        driver.switchTo().defaultContent();
    }

    public void printProductDetails() {

        String quantity = quantityInput.getAttribute("value");
        String size = sizeSelect.getAttribute("value");
        String color = colorSelect.getText();
        String productCost = productPrice.getText();
        String shipping = shippingCost.getText();

        // Remove any non-numeric characters and convert to a float
        float productCostFloat = Float.parseFloat(productCost.replaceAll("[^\\d.]", ""));
        float shippingFloat = Float.parseFloat(shipping.replaceAll("[^\\d.]", ""));

        // Calculate the total cost
        float totalCost = productCostFloat + shippingFloat;

        System.out.println("Quantity: " + quantity);
        System.out.println("Size: " + size);
        System.out.println("Colour: " + color);
        System.out.println("Total Product Cost: " + productCost);
        System.out.println("Shipping Cost: " + shipping);
        System.out.println("Total Cost: " + totalCost);
    }


}
