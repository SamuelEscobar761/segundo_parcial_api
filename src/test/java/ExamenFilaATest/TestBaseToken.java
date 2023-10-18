package ExamenFilaATest;

import config.Configuration;
import factoryRequest.FactoryRequest;
import factoryRequest.RequestInfo;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.hamcrest.Matchers.equalTo;

public class TestBaseToken {
    public String post ="post";
    public String put = "put";
    public String get ="get";
    public String delete ="delete";

    public RequestInfo requestInfo;
    public Response response;
    public String token;
    @BeforeEach
    public void before(){
        requestInfo = new RequestInfo();
        String credential= Configuration.user+":"+Configuration.password;
        requestInfo.setHeaders("Authorization","Basic "+ Base64.getEncoder().encodeToString(credential.getBytes()).toString());
        requestInfo.setUrl(Configuration.host + "/api/authentication/token.json");
        response = FactoryRequest.make("get").send(requestInfo);
        token =response.then().extract().path("TokenString");
        // request get token
        requestInfo = new RequestInfo();
        requestInfo.setHeaders("Token",token);
    }

    @AfterEach
    public void after(){

    }

    @Test
    public void deleteToken(){
        requestInfo.setUrl(Configuration.host + "/api/authentication/token.json");
        response = FactoryRequest.make("DELETE").send(requestInfo);
        response.then().statusCode(200).
                body("TokenString", equalTo(token));
    }

}
