package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;
import myprojects.automation.assignment5.utils.Properties;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlaceOrderTest extends BaseTest {

    @Test
    public void checkSiteVersion() {
        actions.checkSiteVersion(Properties.getBaseUrl());
        if(isMobileTesting){
            System.out.println("test using mobile browser");
        }else {
            System.out.println("test using browser");
        }
    }

    @Test
    public void createNewOrder() {

        // open random product
        actions.openRandomProduct();

        // save product parameters
        actions.saveProductParameters();

        // add product to Cart and validate product information in the Cart
        actions.addProductToCart();
        Assert.assertTrue(actions.productsTitle.contains(actions.productTitleInCart));
        Assert.assertTrue(actions.productPrice.contains(actions.productPriceInCart));
        //Assert.assertTrue(actions.quantity.contains("1"));

        // proceed to order creation, fill required information
        actions.orderCreation("MyNameIs", "MySurNameIs",
                "mail@mail.mail", "Street", "12345", "Odessa");
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='content-hook_order_confirmation']/div/div/div/h3"))
                .getText().contains("Ваш заказ подтвержден"));
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='order-items']/div/div/div[3]/div/div[1]"))
                .getText().contains(actions.productPrice));
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='order-items']/div/div/div[3]/div/div[2]"))
                .getText().contains("1"));
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='order-items']/div/div/div[2]/span"))
                .getText().contains(actions.productsTitle));

        // check updated In Stock value
        actions.checkUpdatedInStock();
        String productsCount = driver.findElement(By.xpath("//span[contains(.,'299 Товары')]")).getText();
        System.out.println("count of products is: " + productsCount);
    }
}