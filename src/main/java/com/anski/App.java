package com.anski;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class App
{
    public static void main( String[] args )
    {
        RestAssured.baseURI = "https://www.skystore.com/";
        given().queryParam("uuid", "9fe6ace0-0ba3-43d4-8ce2-8b39331244e0")
                .header("x-api-user-agent", "Web/5.69.0")
                .header(" x-api-device-model", "Chrome")
                .header("x-api-device-manufacturer", "MAC OS")
                .header("x-api-key", "l_web_sparrow")
                .when().get("api/web/v2/catalog/assets/top")
                .then().assertThat().statusCode(200);
    }
}
