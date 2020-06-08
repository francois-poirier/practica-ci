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

@DisplayName("Integration Tests of the JobController REST endpoints")
public class JobControllerITCase extends CrudControllerITCase {

    @Test
    @DisplayName("GET a list with 4 Jobs")
    public void whenGETAllJobs_thenGetListOf4Jobs() {
        //when
        ValidatableResponse response = given()
                .when()
                .get( baseURL + "/jobs/")

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.OK.value())
                .contentType("application/json")
                .body("size()", greaterThanOrEqualTo(3));
    }

    @Test
    @DisplayName("GET a Job by Id")
    public void givenJobId_thenGetSingleJob() {
        //given
        Long jobId = 1L;

        //when
        ValidatableResponse response = given()
                .when()
                .get(baseURL + "/jobs/" + jobId)

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.OK.value())
                .contentType("application/json")
                .body("id", equalTo(1))
                .body("jobTitle", equalTo("Software Architect"));
    }

    @Test
    @DisplayName("POST a Job to create it")
    public void givenJob_whenPOSTSave_thenGetSavedJob(){
        //given
        JsonObject jobJson = TestDataProvider.getJobJson();

        //when
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(jobJson.toString())

                .when()
                .post(baseURL + "/jobs/")

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("jobTitle", equalTo("Software Architect"));
    }

    @Test
    @DisplayName("DELETE a Job by Id")
    public void givenJobId_whenDELETEJob_thenJobIsDeleted() {
        //given
        Long jobId = 3L;

        //when
        ValidatableResponse response = given()
                .contentType("application/json")

                .when()
                .delete(baseURL + "/jobs/" + jobId)

                .prettyPeek()
                .then();

        response.statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("PUT a Job by Id to update it")
    public void givenIdAndUpdatedJob_whenPUTUpdate_thenJobIsUpdated() {
        //given
        Long jobId = 4L;
        JsonObject jobJson = TestDataProvider.getJobJson();

        //when
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(jobJson.toString())

                .when()
                .put(baseURL + "/jobs/" + jobId)

                .prettyPeek()
                .then();

        response.statusCode(HttpStatus.OK.value());
    }
}
