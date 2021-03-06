package com.testing.serenitySteps;

import com.testing.requestBodies.BaseRequestBody;
import cucumber.api.DataTable;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.io.IOException;
import java.util.Map;

import static net.serenitybdd.core.Serenity.sessionVariableCalled;

public class BookerSteps extends BaseSteps {
  private final static String _AUTH_ = "/auth/";
  private final static String _BOOKING_ = "/booking/";

  @Steps
  public BookerSteps bookerSteps;

  @Step
  public static void getToken(String username, String password) throws IOException {
    BaseRequestBody baseRequestBody = new BaseRequestBody();

    baseRequestBody.addKey("username", username);
    baseRequestBody.addKey("password", password);
    sendRequestWithBodyJson(POST, _AUTH_, baseRequestBody.getBody());
    assertStatusCode(200);
    saveValueInPathToSessionVariable("token", "token");
  }

  @Step
  public static void getBookingIds(){
    sendRequest(GET, _BOOKING_);
  }

  @Step
  public static void validateAmountOfBookingIds(int amount){
    Response response = Serenity.sessionVariableCalled(RESPONSE);
    Assert.assertEquals("Amount of Booking Ids", amount, response.jsonPath().getList("bookingid").size());
  }
}
