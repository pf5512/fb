/*
package serviceTest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

public class UserServiceTest {


    @Test
    public void testCreateUserDetail() {
        String url = "http://localhost:8080/api/mobile/v1/user-service/userDetail/create/fe229e3e-c754-46c7-8984-29b1dad73ff1/100000/e7afdd55291a3e3796a6d3d83e70d6dd";

        Map map = new HashMap();
        map.put("name", "Zidan");

        JSONObject json = new JSONObject(map);
        json.toString();

        System.out.println("json:" + json.toString());

        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, json.toString());

        System.out.println("response:" + clientResponse.getStatus());

    }

    @Test
    public void testCreatePlayer() {
        String url = "http://localhost:8080/api/mobile/v1/user-service/player/create/333c72e3-91c4-442f-8f6f-c5727ef77aa2/100000/e7afdd55291a3e3796a6d3d83e70d6dd";

        Map map = new HashMap();
        map.put("nickName", "Zidan");
        map.put("isLeft", true);
        map.put("position", "front");

        JSONObject json = new JSONObject(map);
        json.toString();

        System.out.println("json:" + json.toString());

        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, json.toString());

        System.out.println("response:" + clientResponse.getStatus());
    }

    @Test
    public void testFindFootballPlayerByPlayerId() {
        String url = "http://localhost:8080/api/mobile/v1/user-service/findPlayer/5/333c72e3-91c4-442f-8f6f-c5727ef77aa2/100000/e7afdd55291a3e3796a6d3d83e70d6dd";

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

    public static void main(String[] args) {

        testGet();
        testPost();
        testPut();
    }

    public static void testGet() {
        Client c = Client.create();
        WebResource r = c
                .resource("http://localhost:8080/footballer/api/mobile/v1/user-service/register/ian2/ian/100000/e7afdd55291a3e3796a6d3d83e70d6dd");
        System.out.println("JSON>> " + r.get(String.class));
    }

    public static void testPost() {
        String url = "http://localhost:8080/footballer/api/mobile/v1/team-service/create";
        String input = "{\"logo\":\"Metallica\",\"name\":\"Post test\",\"captainUserId\":\"1\",\"viceCaptainUserId\":\"2\",\"number\":\"15\",\"technical\":\"反击\"}";

        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);

        //Team output = response.getEntity(Team.class);
        System.out.println("post response Output from Server =>" + response.getClientResponseStatus());

    }


    public static void testPut() {
        String url = "http://localhost:8080/footballer/api/mobile/v1/team-service/create";
        String input = "{\"logo\":\"Metallica\",\"name\":\"Put test\",\"captainUserId\":\"1\",\"viceCaptainUserId\":\"2\",\"number\":\"20\",\"technical\":\"反击\"}";

        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, input);

        //Team output = response.getEntity(Team.class);
        System.out.println("put response Output from Server =>" + response.getStatus());

    }



}
*/