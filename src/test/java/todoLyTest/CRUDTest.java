package todoLyTest;

import config.Configuration;
import factoryRequest.FactoryRequest;
import factoryRequest.RequestInfo;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class CRUDTest extends TestBaseAuthBasic {

    @Test
    public void preguntaUno(){
        String email = "samuelescobarbejarano@gmail.com";
        String password = "1234";
        createUser();
        Configuration.user = email;
        Configuration.password = password;
        requestInfo = new RequestInfo();
        String credential= Configuration.user+":"+Configuration.password;
        requestInfo.setHeaders("Authorization","Basic "+ Base64.getEncoder().encodeToString(credential.getBytes()).toString());
        JSONObject body = new JSONObject();
        body.put("Content","Proyecto1Pregunta1");
        this.createProject(Configuration.host + "/api/projects.json", body, post);
        this.deleteUser();
        body = new JSONObject();
        body.put("ErrorCode", 105);
        this.createProjectFail(Configuration.host + "/api/projects.json", body, post);
    }

    @Test
    public void preguntaDos(){
        List<Integer> ids = new ArrayList<Integer>();
        List<JSONObject> contents = new ArrayList<JSONObject>();
        JSONObject body = new JSONObject();
        for(int i = 0; i < 4; i++){
            body = new JSONObject();
            body.put("Content","Proyecto" + (i+1) + "Pregunta2");
            contents.add(body);
            this.createProject(Configuration.host + "/api/projects.json", body, post);
            ids.add(response.then().extract().path("Id"));
        }

        for(int i = 0; i < ids.size(); i++){
            this.deleteProject(ids.get(i), delete, contents.get(i));
        }
    }

    private void deleteProject(int idProject, String delete, JSONObject body) {
        requestInfo.setUrl(Configuration.host + "/api/projects/" + idProject + ".json");
        response = FactoryRequest.make(delete).send(requestInfo);
        response.then().statusCode(200).
                body("Content", equalTo(body.get("Content")));
    }

    private void createProject(String host, JSONObject body, String post) {
        requestInfo.setUrl(host)
                .setBody(body.toString());
        response = FactoryRequest.make(post).send(requestInfo);
        response.then().statusCode(200).
                body("Content", equalTo(body.get("Content")));
    }
    private void createProjectFail(String host, JSONObject body, String post) {
        requestInfo.setUrl(host)
                .setBody(body.toString());
        response = FactoryRequest.make(post).send(requestInfo);
        response.then().statusCode(200).
                body("ErrorCode", equalTo(body.get("ErrorCode")));
    }

}
