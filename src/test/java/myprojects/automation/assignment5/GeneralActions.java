package myprojects.automation.assignment5;


import myprojects.automation.assignment5.model.ProductData;
import myprojects.automation.assignment5.utils.logging.CustomReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {

    public String productsTitle;
    public String productPrice;

    public String productTitleInCart;
    public String productPriceInCart;
    public String quantity;

    private WebDriver driver;
    private WebDriverWait wait;

    private By allProductsButton = By.xpath("//a[@class='all-product-link pull-xs-left pull-md-right h4']");
    private By randomProduct = By.xpath("//*[@class ='product-miniature js-product-miniature']");
    private By cartInTheRandomProduct = By.xpath("//button[@class='btn btn-primary add-to-cart']");
    private By cartNextStep = By.xpath("//a[@class='btn btn-primary']");
    private By productTitleInTheCart = By.xpath("//*[@id='main']/div/div[1]/div[1]/div[2]/ul/li/div/div[2]/div[1]/a");
    private By productPriceInTheCart = By.xpath(".//*[@id='main']/div/div[1]/div[1]/div[2]/ul/li/div/div[2]/div[2]/span");
    private By quant = By.xpath("//*[@id='main']/div/div[1]/div[1]/div[2]/ul/li/div/div[3]/div/div[2]/div/div[1]/div/input");
    private By orderButton = By.xpath("//a[contains(.,'Оформление заказа')]");
    private By nameField = By.xpath("//input[@name='firstname']");
    private By surNameField = By.xpath("//input[@name='lastname']");
    private By emailField = By.xpath("//*[@id='customer-form']/section/div[4]/div[1]/input");
    private By nextStepOrder = By.xpath("//button[@data-link-action='register-new-customer']");
    private By addressField = By.xpath("//input[@name='address1']");
    private By indexField = By.xpath("//input[@name='postcode']");
    private By cityField = By.xpath("//input[@name='city']");
    private By confirmAddressButton = By.xpath("//button[@name='confirm-addresses']");
    private By confirmDeliveryButton = By.xpath("//button[@name='confirmDeliveryOption']");
    private By paymentOptionsCheckbox = By.xpath("//input[@id='payment-option-1']");
    private By approveRadioButton = By.xpath("//input[@id='conditions_to_approve[terms-and-conditions]']");
    private By orderPayButton = By.xpath("//button[@class='btn btn-primary center-block']");
    private By updatedProduct = By.xpath("//*[text()='" + productsTitle + "']");
    private By productDetails = By.xpath("//a[@class='nav-link']");
    private By productInfo = By.xpath("//*[@id='content-hook_order_confirmation']/div/div/div/h");

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public void checkSiteVersion(String url){
        driver.navigate().to(url);
    }

    public void openRandomProduct() {
        waitForContentLoad(allProductsButton);
        WebElement allProductsButton = driver.findElement(this.allProductsButton);
        scrollToElement(allProductsButton);
        allProductsButton.click();
        waitForContentLoad(randomProduct);
        List<WebElement> randomProduct = driver.findElements(this.randomProduct);
        Random random = new Random();
        int randomValue = random.nextInt(randomProduct.size());
        randomProduct.get(randomValue).click();
    }

    public void saveProductParameters(){
        waitForContentLoad(cartInTheRandomProduct);
        productsTitle = driver.getTitle();
        WebElement productsPrice = driver.findElement(By.xpath("//*[@class ='current-price']"));
        productPrice = productsPrice.getText();
    }

    public void addProductToCart(){
        WebElement cartButton = driver.findElement(this.cartInTheRandomProduct);
        cartButton.click();
        waitForContentLoad(cartNextStep);
        WebElement cartNextStep = driver.findElement(this.cartNextStep);
        cartNextStep.click();
        waitForContentLoad(productTitleInTheCart);
        WebElement productTitleInTheCart = driver.findElement(this.productTitleInTheCart);
        WebElement productPriceInTheCart = driver.findElement(this.productPriceInTheCart);
        WebElement quant = driver.findElement(this.quant);
        productTitleInCart = productTitleInTheCart.getText();
        productPriceInCart = productPriceInTheCart.getText();
        quantity = quant.getAttribute("value");
    }

    public void orderCreation(String name, String surname, String email, String address,
                              String index, String city) {
        WebElement orderButton = driver.findElement(this.orderButton);
        orderButton.click();
        waitForContentLoad(nameField);
        WebElement nameField = driver.findElement(this.nameField);
        nameField.sendKeys(name);
        WebElement surNameField = driver.findElement(this.surNameField);
        surNameField.sendKeys(surname);
        WebElement emailField = driver.findElement(this.emailField);
        emailField.sendKeys(email);
        WebElement nextStepOrder = driver.findElement(this.nextStepOrder);
        scrollToElement(nextStepOrder);
        nextStepOrder.click();
        waitForContentLoad(addressField);
        WebElement addressField = driver.findElement(this.addressField);
        addressField.sendKeys(address);
        WebElement indexField = driver.findElement(this.indexField);
        scrollToElement(indexField);
        indexField.sendKeys(index);
        WebElement cityField = driver.findElement(this.cityField);
        cityField.sendKeys(city);
        WebElement confirmAddressButton = driver.findElement(this.confirmAddressButton);
        scrollToElement(confirmAddressButton);
        confirmAddressButton.click();
        WebElement confirmDeliveryButton = driver.findElement(this.confirmDeliveryButton);
        confirmDeliveryButton.click();
        WebElement paymentOptionsCheckbox = driver.findElement(this.paymentOptionsCheckbox);
        paymentOptionsCheckbox.click();
        WebElement approveRadioButton = driver.findElement(this.approveRadioButton);
        scrollToElement(approveRadioButton);
        approveRadioButton.click();
        WebElement orderPayButton = driver.findElement(this.orderPayButton);
        scrollToElement(orderPayButton);
        orderPayButton.click();
        waitForContentLoad(productInfo);
    }

    public void checkUpdatedInStock(){
        waitForContentLoad(updatedProduct);
        WebElement updatedProduct = driver.findElement(this.updatedProduct);
        scrollToElement(updatedProduct);
        updatedProduct.click();
        waitForContentLoad(productDetails);
        WebElement productDetails = driver.findElement(this.productDetails);
        scrollToElement(productDetails);
        productDetails.click();
    }

    public void scrollToElement(WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void waitForContentLoad(By element) {
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
    }

    /**
     * Extracts product information from opened product details page.
     *
     * @return
     */
    public ProductData getOpenedProductInfo() {
        CustomReporter.logAction("Get information about currently opened product");
        // TODO extract data from opened page
        throw new UnsupportedOperationException();
    }
}