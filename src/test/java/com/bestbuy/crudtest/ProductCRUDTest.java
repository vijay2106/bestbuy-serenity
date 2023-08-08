package com.bestbuy.crudtest;

import com.bestbuy.steps.ProductSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

    @RunWith(SerenityRunner.class)
    public class ProductCRUDTest extends TestBase {
        static String name ="Duracell11"+ TestUtils.getRandomValue();
        static String type ="MOBILE";
        static String upc ="BDD";
        static String model ="RRR";
        static int price = 10;
        static int shipping = 20;
        static  String description ="MEDIUM";
        static  String manufacturer = "SAMSUNG";

        static int productId;
        @Steps
        ProductSteps productSteps;

        @Title("This will create new product")
        @Test
        public void test001() {
            ValidatableResponse response = productSteps.createProduct( name,type,upc,description,model,price,shipping,manufacturer);
            response.log().all().statusCode(201);
            productId=response.log().all().extract().path("id");
            System.out.println("created id "+productId);
        }
        @Title("Verify if the product was added successfully")
        @Test
        public void test002(){

            HashMap<String, Object> productMap = productSteps.getProductInfoByName(price);
            System.out.println("name"+name);
            Assert.assertThat(productMap, hasValue(price));
            productId = (int) productMap.get("id");
            System.out.println("created new data"+productId);

        }
        @Title("Update the product and verify the updated product")
        @Test
        public void test003() {
            String p1 = "data.findAll{it.price = ";
            String p2 = "}.get(0)";
            name = name+"-updated-battery";
            System.out.println("ID of product"+productId);
            productSteps.updateProduct(productId,name,type,upc,description,model,price,shipping,manufacturer)
                    .log().all().statusCode(200);
            HashMap<String,?> storeMap = productSteps.getProductInfoByName(price);
            Assert.assertThat(storeMap, hasValue(price));
        }
        @Title("Delete the product and verify if the product  is deleted!")
        @Test
        public void test004() {
            System.out.println("Id which wanted to delete"+productId);
            productSteps.deleteProduct(productId).statusCode(200);
            productSteps.getProductId(productId).statusCode(404);

        }
}
