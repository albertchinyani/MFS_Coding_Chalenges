import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LandingPage {

     final WebDriver driver;



    @FindBy(xpath = "//a[@title='Popular']")
     WebElement popularCategory;

    @FindBy(xpath = "//ul[contains(@class,'product_list')]/li")
     List<WebElement> popularItems;

    public LandingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }





    /**
     * Retrieves the label and price information for each popular item on the landing page,
     * sorts the items by price in ascending order, and prints the label and price of each
     * item to the console.
     */
    public void printSortedPopularItemsByPrice() {



        popularCategory.click();


        // Create a list of maps to store the label and price information for each item
        List<Map<String, String>> itemList = popularItems.stream()
                .map(item -> {
                    // Extract the label and price information from the web element
                    String label = item.findElement(By.className("product-name")).getText();
                    String priceString = item.findElement(By.className("price")).getText().replace("$", "");
                    double price = Double.parseDouble(priceString);

                    // Store the label and price in a map and return it
                    return Map.of("label", label, "price", priceString);
                })
                // Sort the items by price in ascending order
                .sorted(Comparator.comparingDouble(item -> Double.parseDouble(item.get("price"))))
                // Convert the stream back to a list
                .collect(Collectors.toList());

        // Print the label and price of each item to the console
        for (Map<String, String> item : itemList) {
            System.out.println(item.get("label") + " - $" + item.get("price"));
        }
    }
}
