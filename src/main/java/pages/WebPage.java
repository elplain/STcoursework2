package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebPage {
    // TODO – Locators
    //locators may be changed due to problems with name understanding; always check the action comments below
    
    private WebDriver driver;
    private final By usernameBy =  By.name("user-name");
    private final By passwordBy =  By.name("password");
    private final By submitBy   =  By.name("login-button");
    private final By itemLink1 = By.id("item_4_img_link");
    private final By itemLink2 = By.id("item_1_img_link");
    private final By itemTitleLink1 = By.id("item_5_title_link");
    private final By itemTitleLink2 = By.id("item_3_title_link");
    private final By addToCartButton1 = By.cssSelector("[data-test='add-to-cart-sauce-labs-fleece-jacket']");
    private final By addToCartButton2 = By.cssSelector("[data-test='add-to-cart-test.allthethings()-t-shirt-(red)']");
    private final By addToCartButtons = By.cssSelector("button[data-test^='add-to-cart']");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By removeMainItem1 = By.id("remove-sauce-labs-fleece-jacket");
    private final By removeMainItem2 = By.id("remove-test.allthethings()-t-shirt-(red)");
    private final By moveToCart = By.id("shopping_cart_container");
    private final By cartItems = By.className("cart_item");
    private final By cartItemName = By.className("inventory_item_name");
    private final By cartItemQuantity = By.className("cart_quantity");
    private final By backShoppingButton = By.id("continue-shopping");
    private final By checkoutButton = By.id("checkout");
    private final By firstname = By.id("first-name");
    private final By lastname = By.id("last-name");
    private final By postalcode = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By finishButton = By.id("finish");
    private final By priceTotal = By.cssSelector(".summary_total_label");
    private final By removeButton = By.id("remove");
    private WebDriverWait wait;





    //TODO – Constructor
    //constructor to initialize the WebDriver and WebDriverWait
    public WebPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    //TODO – Actions
    //login page methods
    public void enterUsername(String username) {driver.findElement(usernameBy).sendKeys(username);}
    public void enterPassword(String password) {driver.findElement(passwordBy).sendKeys(password);}
    //clicks the login button on the login page
    public void clickSubmit() {driver.findElement(submitBy).click();}
    //clicks a specific item image link, used for testing the item image links on inventory page
    public void clickItemLink1() {
        scrollTo(itemLink1);
        clickViaJS(itemLink1);
        waitForUrlContains("/inventory-item.html?id=4");
    }

    public void clickItemLink2() {
        scrollTo(itemLink2);
        clickViaJS(itemLink2);
        waitForUrlContains("/inventory-item.html?id=1");
    }

    //method to scroll to element
    private void scrollTo(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element);
    }
    //JS click method
    private void clickViaJS(By locator) {
        WebElement element = driver.findElement(locator);
        wait.until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
    //method to wait for URL to contain specific fragment
    private void waitForUrlContains(String expectedFragment) {
        wait.until(ExpectedConditions.urlContains(expectedFragment));
    }

    // due to issues scrolling to the element, click via JS and then wait for the item detail page
    public void clickItemTitleLink1() {
        scrollTo(itemTitleLink1);
        clickViaJS(itemTitleLink1);
        waitForUrlContains("/inventory-item.html?id=5");
    }

    // due to issues scrolling to the element, click via JS and then wait for the item detail page
    public void clickItemTitleLink2() {
        scrollTo(itemTitleLink2);
        clickViaJS(itemTitleLink2);
        waitForUrlContains("/inventory-item.html?id=3");
    }


    //adds to cart a specofic item by its add to cart button, used for testing the add to cart buttons on item pages; same for remove from cart buttons
    public void clickAddToCart1() {driver.findElement(addToCartButton1).click();}
    public void clickAddToCart2() {driver.findElement(addToCartButton2).click();}

    public void clickRemoveMainItem1() {driver.findElement(removeMainItem1).click();}
    public void clickRemoveMainItem2() {driver.findElement(removeMainItem2).click();}

    public void clickMoveToCart() {driver.findElement(moveToCart).click();}
    //checkout page methods
    public void clickBackShopping() {driver.findElement(backShoppingButton).click();}

    public void clickCheckout() {driver.findElement(checkoutButton).click();}

    public void enterFirstName(String firstName) {driver.findElement(firstname).sendKeys(firstName);}
    public void enterLastName(String lastName) {driver.findElement(lastname).sendKeys(lastName);}
    public void enterPostalCode(String postalCode) {driver.findElement(postalcode).sendKeys(postalCode);}

    public void clickContinue() {driver.findElement(continueButton).click();}
    public void clickFinish() {driver.findElement(finishButton).click();}
    //general add to cart button for all items in their windows+wait for it to be clickable
    public void addToCartButtons() {wait.until(ExpectedConditions.elementToBeClickable(addToCartButtons)).click();}
    //general remove button for all items in their windows+wait for it to be clickable
    public void removeFromCart() {wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();}



    //get number of items in cart badge, if fails return 0
    public String getCartBadgeText() {
        try {
            return driver.findElement(cartBadge).getText();
        } catch (Exception e) {return "0";}
    }

    
    public String getCurrentUrl() {return driver.getCurrentUrl();}
    //Get names of items and return it as a list of strings, if fails return empty list
    public List<String> getCartItemNames() {
        List<WebElement> items = driver.findElements(cartItems);
        List<String> itemNames = new ArrayList<>();
        for (WebElement item : items) {
            WebElement nameElement = item.findElement(cartItemName);
            itemNames.add(nameElement.getText());
        }
        return itemNames;
    }
    //Get data from cart item quantity element and return it as a list of strings, if fails return empty list
    public List<String> getCartItemQuantities() {
        List<WebElement> items = driver.findElements(cartItems);
        List<String> quantities = new ArrayList<>();
        for (WebElement item : items) {
            WebElement quantityElement = item.findElement(cartItemQuantity);
            quantities.add(quantityElement.getText());
        }
        return quantities;
    }
    //List<String> to print cart contents to console, if fails return N/A
    public void printCartContents() {
        List<String> names = getCartItemNames();
        List<String> quantities = getCartItemQuantities();

        for (int i = 0; i < names.size(); i++) {
            String quantity = (i < quantities.size()) ? quantities.get(i) : "N/A";
            System.out.println("Item: " + names.get(i) + " | Qty: " + quantity);
        }
    }
    //String through webdriver to get total price and print it to console, if fails return N/A
    public String printTotalPrice() {
        try {
            String total = driver.findElement(priceTotal).getText();
            System.out.println("Total Price: " + total);
            return total;
        } catch (Exception e) {
            return "N/A";
        }
    }


    public void goBack() {
        driver.navigate().back();
        waitForUrlContains("/inventory.html");
    }


    //to be used in future tests for navigation testing
//    public void goForward() {driver.navigate().forward();}
//    public void refreshPage() {driver.navigate().refresh();}
}
