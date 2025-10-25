package StepDefs;

import POJOs.AuthorsPojo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.PendingException;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.RestAssured;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class APIStepDefs {

    Response authorsResponse;
    protected ArrayList<AuthorsPojo> authResponseList;

    @Given("The user requests a get operation for {string} and {string}")
    public void theUserRequestsAGetOperationForAnd(String uri, String path) {
        RestAssured.baseURI = uri;
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecification request =
                given()
                        .log()
                        .all()
                        .accept(ContentType.JSON)
                        .header("User-Agent", "PostmanRuntime/7.49.0")
                        .header("Accept", "*/*")
                        .header("Accept-Encoding", "gzip, defalte, br")
                        .header("Connection", "keep-alive");

        authorsResponse = request.get(path);
        System.out.println(authorsResponse.asString());

    }

    @Then("Deserialize the response and compare the values returned")
    public void deserializeTheResponseAndCompareTheValuesReturned(List<Map<String, String>> validatingAuthor) {
        //Assign the data from the response to the POJOs
        AuthorsPojo author = authorsResponse.as(AuthorsPojo.class);

        //Loop through the list of results provided in the feature file
        for (Map<String, String> auth : validatingAuthor) {

            //Check for any options that are looking for personal_name
            if (auth.get("Key").equalsIgnoreCase("personal_name")) {
                System.out.println("Comparing the personal name from feature: " + auth.get("Value") + " to the name from API: " + author.getPersonal_name());
                Assert.assertTrue(author.getPersonal_name().equalsIgnoreCase(auth.get("Value")));

            //Check for any options that are looking for alternate_names
            } else if (auth.get("Key").equalsIgnoreCase("alternate_names")) {
                System.out.println("Comparing the alternate name from feature: " + auth.get("Value") + " to the names from API: \n" + author.getAlternate_names());

                //Boolean to be used to confirm a match has been found
                Boolean isMatched = false;
                int count = 0;
                //Loop through the personal_name arrayList to get the matching option to feature file
                do {
                    isMatched = false;
                    if (author.getAlternate_names().get(count).equalsIgnoreCase(auth.get("Value"))) {
                        System.out.println("Matched values: " + auth.get("Value"));
                    }
                    count++;
                } while (count < author.getPersonal_name().length() && !isMatched);
            }
        }
    }

}
