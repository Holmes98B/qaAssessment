package StepDefs;

import Utils.Hooks;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class StampDutySteps {

    private final SelenideElement pageHeader = $(By.tagName("h1"));
    private final SelenideElement checkOnlineBtn = $(By.xpath("//a[contains(text(), 'Check online')]"));
    private final SelenideElement calculatorPageHeader = $(By.tagName("h2"));
    private final SelenideElement pricePaidField = $(By.id("purchasePrice"));
    private final SelenideElement calculateBtn = $(By.xpath("//button[@type='submit']"));
    private final SelenideElement calculatorResultsHeader = $(By.xpath("//h4[contains(text(), 'Calculation')]"));
    private final SelenideElement passengerVehicleCalcResult = $(By.xpath("//td[contains(text(),'passenger')]/following-sibling::td"));
    private final SelenideElement purchasePriceResult = $(By.xpath("//td[contains(text(),'Purchase price')]/following-sibling::td"));
    private final SelenideElement dutyPayableResult = $(By.xpath("//td[contains(text(),'Duty payable')]/following-sibling::td"));

    //Calculate the tax amount based on amount paid. Tax percentage is set at 3%
    public double calculateDutyPayable(int amountPayed){
        int dutyTax = 3;
        return (double) amountPayed / 100 * dutyTax;

    }

    @Given("The user launches {string}")
    public void theUserLaunches(String expectedHeader) {
        open("https://www.service.nsw.gov.au/transaction/check-motor-vehicle-stamp-duty");
        Assert.assertEquals(expectedHeader, pageHeader.getText());
    }

    @Then("Click the Check Online button")
    public void clickTheCheckOnlineButton() {
        Assert.assertTrue(checkOnlineBtn.isDisplayed());
        checkOnlineBtn.click();
    }

    @Then("Complete the {string} screen with options {string} and {string}")
    public void completeTheScreenWithOptionsAnd(String expectedHeader, String passengerVehicle, String purchasePrice) {
        //Verify the calculator page opens
        Assert.assertEquals(expectedHeader, calculatorPageHeader.getText());

        //Select the option based on passenger vehicle value
        $(By.xpath("//label[@for='passenger_" + passengerVehicle.charAt(0) + "']")).click();

        //Set the price paid field
        pricePaidField.setValue(purchasePrice);

        //Submit form
        calculateBtn.click();
    }


    @And("Validate the calculator returned content")
    public void validateTheCalculatorReturnedContent(List<Map<String, String>> vehicleDetails) {
        //Check the calculator result loads
        calculatorResultsHeader.shouldBe(Condition.visible);

        //Loop through the details provided in the data table
        for (Map<String, String> vehicle : vehicleDetails) {

            //Check for passenger vehicle response
            if (vehicle.get("Key").equalsIgnoreCase("passengerVehicle"))
                Assert.assertEquals(vehicle.get("Value"), passengerVehicleCalcResult.getText());

            //Check for purchase price
            else if (vehicle.get("Key").equalsIgnoreCase("purchasePrice")) {
                Assert.assertTrue(purchasePriceResult.getText()
                        .replaceAll("\\$", "").replaceAll(",", "")
                        .contains(vehicle.get("Value")));

                //Using the purchase price, convert it to amount of duty paid
                double dutyAmount = calculateDutyPayable(Integer.parseInt(vehicle.get("Value")));

                String dutyAmountAsString = String.valueOf(dutyAmount);
                Assert.assertTrue(dutyPayableResult.getText().contains(dutyAmountAsString));
            }
        }

    }
}
