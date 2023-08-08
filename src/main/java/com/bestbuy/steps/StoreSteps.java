package com.bestbuy.steps;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.constants.Path;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;
import java.util.Map;

public class StoreSteps {
    @Step("Creating user with name : {0}, address: {1},city: {2},state: {3},zip: {4},type:{5},address2:{6},lat:{7},lng:{8},hours:{9} and services: {10}")

    public ValidatableResponse createStore(String name, String address, String city, String state, String zip, String type, String address2, int lat, int lng, String hours, Map<String,Object> services){
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setType(type);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLng(lng);
        storePojo.setLat(lat);
        storePojo.setHours(hours);
        storePojo.setServices(services);
        return SerenityRest.given().log().all()
                .basePath(Path.STORE)
                .contentType(ContentType.JSON)
                .when()
                .body(storePojo)
                .post().then();

    }
    @Step("Getting the store information with name: {0}")

    public HashMap<String, Object> getProductInfoByName(int lat)throws IndexOutOfBoundsException {
        String p1 = "data.findAll{it.lat = ";
        String p2 = "}.get(0)";
        return SerenityRest.given().log().all()
                .basePath(Path.STORE)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .path(p1+lat+p2);

    }
    @Step("Updating store information with storeId")

    public ValidatableResponse updateStore(int storeId,String name, String address, String city, String state, String zip, String type, String address2, int lat, int lng, String hours, Map<String,Object> services){
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setType(type);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLng(lng);
        storePojo.setLat(lat);
        storePojo.setHours(hours);
        storePojo.setServices(services);
        return SerenityRest.given().log().all()
                .basePath(Path.STORE)
                .header("Accept", "application/json")
                .pathParam("id",storeId)
                .body(storePojo)
                .when()
                .patch(EndPoints.UPDATE_STORE_BY_ID)
                .then();
    }
    @Step("Deleting the store information")

    public ValidatableResponse deleteProduct(int productId){
        return  SerenityRest.given().log().all()
                .basePath(Path.STORE)
                .pathParam("id",productId)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then();
    }
    @Step("Getting store information withId")

    public ValidatableResponse getProductId(int productId){
        return SerenityRest.given().log().all()
                .basePath(Path.STORE)
                .pathParam("id",productId)
                .when()
                .get(EndPoints.GET_SINGLE_STORE_BY_STORE_ID)
                .then();

    }
}
