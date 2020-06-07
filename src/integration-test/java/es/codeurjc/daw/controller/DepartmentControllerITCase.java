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

@DisplayName("Integration Tests of the DepartmentController REST endpoints")
public class DepartmentControllerITCase extends CrudControllerITCase {

    @Test
    @DisplayName("GET a list with 4 Departments")
    public void whenGETAllDepartments_thenGetListOf4Departments() {
        //when
        ValidatableResponse response = given()
                .when()
                .get( baseURL + "/departments/")

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.OK.value())
                .contentType("application/json")
                .body("size()", greaterThanOrEqualTo(3));
    }

    @Test
    @DisplayName("GET a Department by Id")
    public void givenDepartmentId_thenGetSingleDepartment() {
        //given
        Long departmentId = 1L;

        //when
        ValidatableResponse response = given()
                .when()
                .get(baseURL + "/departments/" + departmentId)

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.OK.value())
                .contentType("application/json")
                .body("id", equalTo(1))
                .body("departmentName", equalTo("Sales"));
    }

    @Test
    @DisplayName("POST a Department to create it")
    public void givenDepartment_whenPOSTSave_thenGetSavedDepartment(){
        //given
        JsonObject departmentJson = TestDataProvider.getDepartmentJson();

        //when
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(departmentJson.toString())

                .when()
                .post(baseURL + "/departments/")

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("departmentName", equalTo("Sales"));
    }

    @Test
    @DisplayName("DELETE a Department by Id")
    public void givenDepartmentId_whenDELETEDepartment_thenDepartmentIsDeleted() {
        //given
        Long departmentId = 3L;

        //when
        ValidatableResponse response = given()
                .contentType("application/json")

                .when()
                .delete(baseURL + "/departments/" + departmentId)

                .prettyPeek()
                .then();

        response.statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("PUT a Department by Id to update it")
    public void givenIdAndUpdatedDepartment_whenPUTUpdate_thenDepartmentIsUpdated() {
        //given
        Long departmentId = 4L;
        JsonObject departmentJson = TestDataProvider.getDepartmentJson();

        //when
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(departmentJson.toString())

                .when()
                .put(baseURL + "/departments/" + departmentId)

                .prettyPeek()
                .then();

        response.statusCode(HttpStatus.OK.value());
    }
}
