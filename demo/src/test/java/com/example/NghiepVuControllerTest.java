package com.example;

import io.quarkus.test.junit.QuarkusTest;
import net.bytebuddy.build.ToStringPlugin;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class NghiepVuControllerTest {

    @Test
    public void testGet1_Success () {
        given().when().get("nghiepvu/get1").then().statusCode(200);
    }

    @Test
    public void testGet2_Success () {
        given().when().get("nghiepvu/get2").then().statusCode(200);
    }
}
