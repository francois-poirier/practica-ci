package es.codeurjc.daw.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.google.gson.JsonObject;

import es.codeurjc.daw.config.TestDataProvider;
import io.restassured.response.ValidatableResponse;

@DisplayName("Integration Tests of the EmployeeController REST endpoints")
public class EmployeeControllerITCase extends CrudControllerITCase {

    @Test
    @DisplayName("GET a list with 4 Employees")
    public void whenGETAllEmployees_thenGetListOf4Employees() {
        //when
        ValidatableResponse response = given()
                .when()
                .get( baseURL + "/employees/")

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.OK.value())
                .contentType("application/json")
                .body("size()", greaterThanOrEqualTo(3));
    }

    @Test
    @DisplayName("GET a Employee by Id")
    public void givenEmployeeId_thenGetSingleEmployee() {
        //given
        Long employeeId = 1L;

        //when
        ValidatableResponse response = given()
                .when()
                .get(baseURL + "/employees/" + employeeId)

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.OK.value())
                .contentType("application/json")
                .body("id", equalTo(1))
                .body("firstName", equalTo("Albert"));
    }

    @Test
    @DisplayName("POST a Employee to create it")
    public void givenEmployee_whenPOSTSave_thenGetSavedEmployee(){
        //given
        JsonObject employeeJson = TestDataProvider.getEmployeeJson();

        //when
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(employeeJson.toString())

                .when()
                .post(baseURL + "/employees/")

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("firstName", equalTo("Albert"));
    }

    @Test
    @DisplayName("DELETE a Employee by Id")
    public void givenEmployeeId_whenDELETEEmployee_thenEmployeeIsDeleted() {
        //given
        Long employeeId = 3L;

        //when
        ValidatableResponse response = given()
                .contentType("application/json")

                .when()
                .delete(baseURL + "/employees/" + employeeId)

                .prettyPeek()
                .then();

        response.statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("PUT a Employee by Id to update it")
    public void givenIdAndUpdatedEmployee_whenPUTUpdate_thenEmployeeIsUpdated() {
        //given
        Long employeeId = 4L;
        JsonObject employeeJson = TestDataProvider.getEmployeeJson();

        //when
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(employeeJson.toString())

                .when()
                .put(baseURL + "/employees/" + employeeId)

                .prettyPeek()
                .then();

        response.statusCode(HttpStatus.OK.value());
    }
}
