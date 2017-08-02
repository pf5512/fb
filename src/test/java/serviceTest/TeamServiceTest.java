/*
package serviceTest;

import com.footballer.rest.api.v1.vo.Field;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamServiceTest {

    @Test
    public void testFindTeamPlayersByTeamId() {
        String url = "http://localhost:8080/api/mobile/v1/team-service/findTeamPlayers/5/333c72e3-91c4-442f-8f6f-c5727ef77aa2/100000/e7afdd55291a3e3796a6d3d83e70d6dd";
        Client client = Client.create();

        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        String output = response.getEntity(String.class);

        System.out.println("Output from Server .... \n");
        System.out.println(output);
    }

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, JSONException {

        testGet();
        Post_createTeam();
        PUT_updateTeam();

        //Post_createTeamField();
        //PUT_updateTeamField();
    }

    private static void testGet() {
        Client c = Client.create();
        WebResource r;
        r = c.resource("http://localhost:8080/footballer/api/mobile/v1/team-service/findByName/Post2/100000/e7afdd55291a3e3796a6d3d83e70d6dd");
        System.out.println("findByName ==>> " + r.get(String.class));

        r = c.resource("http://localhost:8080/footballer/api/mobile/v1/team-service/findById/2/100000/e7afdd55291a3e3796a6d3d83e70d6dd");
        System.out.println("findById ==>> " + r.get(String.class));

//		r = c.resource("http://localhost:8080/footballer/api/mobile/v1/team-service/findTeamsByFieldId/1/100000/e7afdd55291a3e3796a6d3d83e70d6dd");
//		System.out.println("findFieldsByTeamId ==>> " + r.get(String.class));
    }

    private static void Post_createTeam() throws JsonParseException, JsonMappingException, IOException, JSONException {
        String url = "http://localhost:8080/footballer/api/mobile/v1/team-service/create/100000/e7afdd55291a3e3796a6d3d83e70d6dd";

        String input = "{\"logo\":\"Metallica\",\"name\":\"Post2\",\"captainUserId\":\"1\"" +
                ",\"viceCaptainUserId\":\"2\",\"number\":\"15\",\"technical\":\"反击\"}";


        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, input);

        String output = response.getEntity(String.class);
        System.out.println("createTeam without Field =>" + output);

        // create team with two fields
        // jackson convert map to json.

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "Zidan");
        map.put("logo", "Zidan");
        map.put("captainUserId", "1");
        map.put("viceCaptainUserId", "2");
        map.put("number", "15");
        map.put("technical", "Zidan");

        List<Field> fieldsList = new ArrayList<Field>();
        Field field = new Field();
        //field.setId(100L);
        field.setName("football Park");
        field.setConditions("很好");
        field.setNumber(5);
        field.setOwnerUserId(1L);
        field.setPic("asdfasdf");
        field.setPrice(350D);
        field.setType("假草");

        Field field2 = new Field();
        //field.setId(200L);
        field2.setName("Tibet Restaurant");
        field2.setConditions("good");
        field2.setNumber(1);
        field2.setOwnerUserId(2L);
        field2.setPic("asdfasfasfsadfasfasdf");
        field2.setPrice(300D);
        field2.setType("假草");

        fieldsList.add(field);
        fieldsList.add(field2);

        ObjectMapper mapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        try {
            mapper.writeValue(sw, fieldsList);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray(sw.toString());

        map.put("fields", jsonArray);

        JSONObject json = new JSONObject(map);
        //System.out.println("json:" + json.toString());

        client = Client.create();
        webResource = client.resource(url);
        response = webResource.type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, json.toString());

        output = response.getEntity(String.class);
        System.out.println("createTeam with two fields =>" + output);
    }

    private static void Post_createTeamField() {
        String url = "http://localhost:8080/footballer/api/mobile/v1/team-service/createTeamField/100000/e7afdd55291a3e3796a6d3d83e70d6dd";
        String input = "{\"teamId\":\"100\",\"fieldId\":\"200\"}";

        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, input);

        String output = response.getEntity(String.class);
        System.out.println("createTeamField =>" + output);
    }

    private static void PUT_updateTeam() {
        String url = "http://localhost:8080/footballer/api/mobile/v1/team-service/update/100000/e7afdd55291a3e3796a6d3d83e70d6dd";
        String input = "{\"id\":\"1\",\"logo\":\"Metallica123\",\"name\":\"Put test\",\"captainUserId\":\"1\",\"viceCaptainUserId\":\"2\",\"number\":\"20\",\"technical\":\"反击\"}";

        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
                .put(ClientResponse.class, input);

        String output = response.getEntity(String.class);
        System.out.println("updateTeam =>" + output);
    }

    private static void PUT_updateTeamField() {
        String url = "http://localhost:8080/footballer/api/mobile/v1/team-service/updateTeamField/100000/e7afdd55291a3e3796a6d3d83e70d6dd";
        String input = "{\"id\":\"4\",\"teamId\":\"100\",\"fieldId\":\"999\"}";

        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
                .put(ClientResponse.class, input);

        String output = response.getEntity(String.class);
        System.out.println("updateTeamField =>" + output);
    }

}
*/