package ExamenFilaATest;

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
}
