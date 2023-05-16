
import org.testng.Assert;
import org.testng.annotations.*;

public class TestScenario extends TestBase {
    @Test
    public void testLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.Signin(Config.getProperty("username"), Config.getProperty("password"));
        String expectedTitle = "My account - My Store";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }
    @Test(priority = 1)
    public void testLandingPage(){
        LandingPage landingpage = new LandingPage(driver);
        landingpage.printSortedPopularItemsByPrice();

    }

    @Test(priority = 2)
    public void testCatalogPage() {
        CatalogPage catalogPage = new CatalogPage(driver);
        catalogPage.selectProduct();
        catalogPage.verifyProductDetailsAndTotalCost();
        catalogPage.printProductDetails();

    }
}
