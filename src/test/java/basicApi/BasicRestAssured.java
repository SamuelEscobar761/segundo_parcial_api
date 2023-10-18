package basicApi;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BasicRestAssured {

    /*
      given() --> configuration > header / params / body / Auth
      when() --> method (put/post/get/delete) > url
      then() --> response -> boyd /code/ msg /headers
     */
    @Test
    public void createProjectByApi(){
      given()
                .auth()
                .preemptive()
                .basic("upbapi@upbapi.com","12345")
                .body("{\n" +
                        " \"Content\":\"Eynar RESTASSURED\",\n" +
                        " \"Icon\":2 \n" +
                        "}\n")
                 .log()
                 .all().
       when()
                .post("https://todo.ly/api/projects.json").
       then()
              .log()
              .all()
              .statusCode(200)
              .body("Content",equalTo("Eynar RESTASSURED"))
              .body("Icon",equalTo(2));
    }

    @Test
    public void createProjectByApiWithJsonObject(){
        JSONObject bodyProject = new JSONObject();
        bodyProject.put("Content","EynarJSON");
        bodyProject.put("Icon",2);

        given()
                .auth()
                .preemptive()
                .basic("upbapi@upbapi.com","12345")
                .body(bodyProject.toString())
                .log()
                .all().
        when()
                .post("https://todo.ly/api/projects.json").
        then()
                .log()
                .all()
                .statusCode(200)
                .body("Content",equalTo(bodyProject.get("Content")))
                .body("Icon",equalTo(2));
    }
}
