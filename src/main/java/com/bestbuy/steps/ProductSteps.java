package com.bestbuy.steps;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.constants.Path;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductSteps {
    @Step("Creating student with name : {0}, type: {1}, upc: {2}, description: {3} ,model:{4},price:{5},shipping:{6} and manufacturer: {7}")

    public ValidatableResponse createProduct(String name, String type, String upc, String description, String model, int price, int shipping, String manufacturer){
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setModel(model);
        productPojo.setPrice(price);
        productPojo.setShipping(shipping);
        productPojo.setManufacturer(manufacturer);
        return SerenityRest.given().log().all()
                .basePath(Path.PRODUCT)
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)
                .post().then();
    }
    @Step("Getting the product information ")

    public HashMap<String,Object> getProductInfoByName(int price){
        String p1 = "data.findAll{it.price = ";
        String p2 = "}.get(0)";
        return SerenityRest.given().log().all()
                .basePath(Path.PRODUCT)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .path(p1+price+p2);

    }
    @Step("Updating product information")

    public ValidatableResponse updateProduct(int productId,String name,String type,String upc,String description, String model, int price, int shipping,String manufacturer){
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setModel(model);
        productPojo.setPrice(price);
        productPojo.setShipping(shipping);
        return SerenityRest.given().log().all()
                .basePath(Path.PRODUCT)
                .header("Accept", "application/json")
                .pathParam("id",productId)
                .body(productPojo)
                .when()
                .patch(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then();
    }
    @Step("Deleting product")

    public ValidatableResponse deleteProduct(int productId){
        return  SerenityRest.given().log().all()
                .basePath(Path.PRODUCT)
                .pathParam("id",productId)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then();
    }
    @Step("Getting the single product by ID")

    public ValidatableResponse getProductId(int productId){
        return SerenityRest.given().log().all()
                .basePath(Path.PRODUCT)
                .pathParam("id",productId)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();

    }
}
