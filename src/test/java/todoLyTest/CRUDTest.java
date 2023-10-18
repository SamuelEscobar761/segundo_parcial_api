package todoLyTest;

import config.Configuration;
import factoryRequest.FactoryRequest;
import factoryRequest.RequestInfo;
import models.Item;
import models.Project;
import org.json.JSONArray;
import org.json.JSONException;
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
        JSONObject body = new JSONObject();
        body.put("Content","Proyecto1Pregunta1");
        this.createProject(Configuration.host + "/api/projects.json", body, post);
        this.deleteUser();
        body = new JSONObject();
        body.put("Content","null");
        this.createProject(Configuration.host + "api/projects.json", body, post);
    }

    @Test
    public void createUpdateDeleteProject(){
        JSONObject body = new JSONObject();
        body.put("Content","Refactor");

        this.createProject(Configuration.host + "/api/projects.json", body, post);
        int idProject = response.then().extract().path("Id");
        this.readProject(idProject, get, body);
        body.put("Content","Refactor1");
        this.updateProject(Configuration.host + "/api/projects/" + idProject + ".json", body, put);
        this.deleteProject(idProject, delete, body);
    }

    @Test
    public void deleteAllProjects(){
        requestInfo.setUrl(Configuration.host + "/api/projects.json");
        response = FactoryRequest.make(get).send(requestInfo);

        String answer = response.getBody().asString();

        List<Project> projects = extractProjects(answer);

        for (Project project : projects) {
            JSONObject body = new JSONObject();
            body.put("Content", project.getContent());
            deleteProject(project.getId(), delete, body);
        }
    }

    public static List<Project> extractProjects(String jsonString) {
        List<Project> projects = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject projectObject = jsonArray.getJSONObject(i);
                int id = projectObject.getInt("Id");
                String content = projectObject.getString("Content");
                Project project = new Project(id, content);
                projects.add(project);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return projects;
    }

    private void deleteProject(int idProject, String delete, JSONObject body) {
        requestInfo.setUrl(Configuration.host + "/api/projects/" + idProject + ".json");
        response = FactoryRequest.make(delete).send(requestInfo);
        response.then().statusCode(200).
                body("Content", equalTo(body.get("Content")));
    }

    private void updateProject(String host, JSONObject body, String put) {
        requestInfo.setUrl(host)
                .setBody(body.toString());
        response = FactoryRequest.make(put).send(requestInfo);
        response.then().statusCode(200).
                body("Content", equalTo(body.get("Content")));
    }

    private void readProject(int idProject, String get, JSONObject body) {
        requestInfo.setUrl(Configuration.host + "/api/projects/" + idProject + ".json");
        response = FactoryRequest.make(get).send(requestInfo);
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

}
