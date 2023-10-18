package todoLyTest;

import config.Configuration;
import factoryRequest.FactoryRequest;
import factoryRequest.RequestInfo;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.hamcrest.Matchers.equalTo;

public class TestBaseAuthBasic {
    public String post ="post";
    public String put = "put";
    public String get ="get";
    public String delete ="delete";

    public RequestInfo requestInfo;
    public Response response;
    @BeforeEach
    public void before(){
        requestInfo = new RequestInfo();
        String credential= Configuration.user+":"+Configuration.password;
        requestInfo.setHeaders("Authorization","Basic "+ Base64.getEncoder().encodeToString(credential.getBytes()).toString());

    }

    @AfterEach
    public void after(){

    }

    @Test
    public void createUser(){
        JSONObject body = new JSONObject();
        body.put("Email", "samuelescobarbejarano@gmail.com");
        body.put("Password","1234");
        body.put("FullName", "Samuel Matias Escobar Bejarano");
        requestInfo = new RequestInfo();
        requestInfo.setUrl(Configuration.host + "/api/user.json");
        requestInfo.setBody(body.toString());
        response = FactoryRequest.make("POST").send(requestInfo);
        response.then().statusCode(200).
                body("Email", equalTo(body.get("Email")));
    }

    @Test
    public void deleteUser(){
        String email = "samuelescobarbejarano@gmail.com";
        String password = "1234";
        Configuration.user = email;
        Configuration.password = password;
        requestInfo = new RequestInfo();
        String credential= Configuration.user+":"+Configuration.password;
        requestInfo.setHeaders("Authorization","Basic "+ Base64.getEncoder().encodeToString(credential.getBytes()).toString());
        requestInfo.setUrl(Configuration.host + "/api/user/0.json");
        response = FactoryRequest.make("DELETE").send(requestInfo);
        response.then().statusCode(200).
                body("Email", equalTo(Configuration.user));
    }

}
