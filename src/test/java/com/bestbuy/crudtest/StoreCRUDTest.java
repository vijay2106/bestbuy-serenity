package com.bestbuy.crudtest;

import com.bestbuy.steps.StoreSteps;
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
import java.util.Map;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoreCRUDTest extends TestBase {
    static String name ="SURYA TEMPLE"+ TestUtils.getRandomValue();
    static String type ="Pop General";
    static String address="21,yog Street";
    static String address2="holend";
    static String city="Parish"+TestUtils.getRandomValue();
    static String state="Hemlehemstate";
    static String zip="W4 1HL";
    static Integer lat =100;
    static Integer lng =200;
    static String hours ="10:15";
    static int id;
    @Steps
    StoreSteps storeSteps;
    @Title("This will create new store")
    @Test
    public void test001() {

        Map<String,Object> services= new HashMap<>();
        Map<String,Object>servicesData=new HashMap<String,Object>();
        servicesData.put("id",100);
        servicesData.put("name","WHite Land");
        servicesData.put("createdAt","2023-05-10");
        servicesData.put("updatedAt","2023-05-22");
        Map<String,Object>storeServices=new HashMap<String,Object>();
        storeServices.put("createdAt","2023-05-10");
        storeServices.put("updatedAt","2023-05-22");
        storeServices.put("stordId",101);
        storeServices.put("serviceId",11);
        servicesData.put("storeservices",storeServices);
        services.put("services",servicesData);
        ValidatableResponse response = storeSteps.createStore(name,address,city,state,zip,type,address2,lat,lng,hours,servicesData);
        response.log().all().statusCode(201);
        id = response.log().all().extract().path("id");

    }
    @Title("Verify if the store was added ")
    @Test
    public void test002()throws IndexOutOfBoundsException{
        System.out.println("Find by lat "+lat);
        HashMap<String, Object> studentMap = storeSteps.getProductInfoByName(lat);
        Assert.assertThat(studentMap, hasValue(lat));
        id = (int) studentMap.get("id");
        System.out.println("newly added id :"+id);

    }

    @Title("Update the information and verify")
    @Test
    public void test003() {
        name = name+"-updated-data";
        Map<String,Object>services= new HashMap<>();
        Map<String,Object>servicesData=new HashMap<String,Object>();
        servicesData.put("id",201);
        servicesData.put("name","Donna");
        servicesData.put("createdAt","2023-05-12");
        servicesData.put("updatedAt","2023-05-23");
        Map<String,Object> storeServices=new HashMap<String,Object>();
        storeServices.put("createdAt","2023-05-10");
        storeServices.put("updatedAt","2023-05-22");
        storeServices.put("stordId",101);
        storeServices.put("serviceId",11);
        servicesData.put("storeservices",storeServices);

        services.put("services",servicesData);
        storeSteps.updateStore(id,name,address,city,state,zip,type,address2,lat,lng,hours,services)
                .log().all().statusCode(200);
        HashMap<String, Object> studentMap = storeSteps.getProductInfoByName(lat);
        Assert.assertThat(studentMap, hasValue(lat));
    }


    @Title("Delete the data and verify !")
    @Test
    public void test004() {
        storeSteps.deleteProduct(id).statusCode(200);
        storeSteps.getProductId(id).statusCode(404);


    }

}
