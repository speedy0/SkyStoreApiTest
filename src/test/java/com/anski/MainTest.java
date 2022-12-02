package com.anski;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.is;

public class MainTest {
    static HashMap<String, String> headerMap = new HashMap<String, String>();
    static HashMap<String, String> queryParamsMap = new HashMap<String, String>();

    void setTheLink(){
        RestAssured.baseURI = "https://www.skystore.com";
        RestAssured.basePath = "/api/web/v2/catalog/assets/top/";
    }

    void fillHashMaps(){
        headerMap.put("x-api-user-agent", "Web/5.69.0");
        headerMap.put("x-api-device-model", "Chrome");
        headerMap.put("x-api-device-manufacturer", "MAC OS");
        headerMap.put("x-api-key", "l_web_sparrow");

        queryParamsMap.put("uuid", "9fe6ace0-0ba3-43d4-8ce2-8b39331244e0");
        queryParamsMap.put("genre", "new-to-buy");
    }

    @Test
    @DisplayName("When accessing API, return 200 code")
    public void whenAccessingApiReturn200Code(){
        fillHashMaps();
        setTheLink();
        RequestSpecification httpRequest = RestAssured.given().queryParams(queryParamsMap).headers(headerMap);
        Response response = httpRequest.get(RestAssured.baseURI + RestAssured.basePath);
        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    @DisplayName("When getting the body, check that the response is not null")
    public void whenGettingTheBodyCheckThatTheResponseIsNotNull(){
        fillHashMaps();
        setTheLink();

        RequestSpecification httpRequest = RestAssured.given().queryParams(queryParamsMap).headers(headerMap);
        Response response = httpRequest.get(RestAssured.baseURI + RestAssured.basePath);
        ResponseBody body = response.getBody();

        MatcherAssert.assertThat(body.asString(), is(IsNull.notNullValue()));
    }

    @Test
    @DisplayName("When getting the body, count the amount of assets in the json")
    public void whenGettingTheBodyCountTheAmountOfAssetsInTheJson(){
        fillHashMaps();
        setTheLink();

        RequestSpecification httpRequest = RestAssured.given().queryParams(queryParamsMap).headers(headerMap);
        Response response = httpRequest.get(RestAssured.baseURI + RestAssured.basePath);
        ResponseBody body = response.getBody();

        DocumentContext jsonContext = JsonPath.parse(body.asString());
        List<String> jsonAssetLocation = jsonContext.read("$.content.assets");

        System.out.println("The amount of assets are: " + jsonAssetLocation.size());
    }
    
    @Test
    @DisplayName("When taking the first asset from the json, verify title is correct")
    public void whenTakingTheFirstAssetFromTheJsonVerifyTitleIsCorrect(){
        fillHashMaps();
        setTheLink();

        RequestSpecification httpRequest = RestAssured.given().queryParams(queryParamsMap).headers(headerMap);
        Response response = httpRequest.get(RestAssured.baseURI + RestAssured.basePath);
        ResponseBody body = response.getBody();

        DocumentContext jsonContext = JsonPath.parse(body.asString());
        String jsonAssetTitle = jsonContext.read("$.content.assets[0].title");

        Assertions.assertEquals(".45",jsonAssetTitle);
    }
    
    @Test
    @DisplayName("When taking the first asset from the json, verify that year is 2006")
    public void whenTakingTheFirstAssetFromTheJsonVerifyThatYearIs2006(){
        fillHashMaps();
        setTheLink();

        RequestSpecification httpRequest = RestAssured.given().queryParams(queryParamsMap).headers(headerMap);
        Response response = httpRequest.get(RestAssured.baseURI + RestAssured.basePath);
        ResponseBody body = response.getBody();

        DocumentContext jsonContext = JsonPath.parse(body.asString());
        int jsonAssetYear = jsonContext.read("$.content.assets[0].year");

        Assertions.assertEquals(2006,jsonAssetYear);
    }

    @Test
    @DisplayName("When taking the first asset from the json, verify that assetType is Programme")
    public void whenTakingTheFirstAssetFromTheJsonVerifyThatAssetTypeIsProgramme(){
        fillHashMaps();
        setTheLink();

        RequestSpecification httpRequest = RestAssured.given().queryParams(queryParamsMap).headers(headerMap);
        Response response = httpRequest.get(RestAssured.baseURI + RestAssured.basePath);
        ResponseBody body = response.getBody();

        DocumentContext jsonContext = JsonPath.parse(body.asString());
        String jsonAssetAssetType = jsonContext.read("$.content.assets[0].assetType");

        Assertions.assertEquals("Programme",jsonAssetAssetType);
    }

    @Test
    @DisplayName("When taking the first asset from the json, verify that catalogSection is Movies")
    public void whenTakingTheFirstAssetFromTheJsonVerifyThatCatalogSectionIsMovies(){
        fillHashMaps();
        setTheLink();

        RequestSpecification httpRequest = RestAssured.given().queryParams(queryParamsMap).headers(headerMap);
        Response response = httpRequest.get(RestAssured.baseURI + RestAssured.basePath);
        ResponseBody body = response.getBody();

        DocumentContext jsonContext = JsonPath.parse(body.asString());
        String jsonAssetCatalogSection = jsonContext.read("$.content.assets[0].catalogSection");

        Assertions.assertEquals("Movies",jsonAssetCatalogSection);
    }
}