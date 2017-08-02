package serviceTest;

/*
import javax.ws.rs.core.MediaType;

import com.footballer.rest.api.v1.vo.Team;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class ArenaServiceTest {
	public static void main(String[] args) {

		testGet();
		//testPost() ;
		//testPut();
	}
	
	public static void testGet()
	{
		Client c = Client.create();
		WebResource r = c.resource("http://localhost:8080/footballer/api/mobile/v1/arena-service/findById/1/333c72e3-91c4-442f-8f6f-c5727ef77aa2/100000/e7afdd55291a3e3796a6d3d83e70d6dd");
		System.out.println("findArenaById ==>> " + r.get(String.class));
	}
	
    public static void testPost() {
        String url = "http://localhost:8080/footballer/api/mobile/v1/field-service/create/100000/e7afdd55291a3e3796a6d3d83e70d6dd";
        String input = "{\"name\":\"Field2\",\"ownerUserId\":\"100\",\"number\":\"10\",\"type\":\"5v5\",\"price\":\"350\",\"conditions\":\"fake grass\",\"pic\":\"sdfsdfas\",\"timeTemplateId\":\"10\"}";

        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);

        String output = response.getEntity(String.class);
        System.out.println("post response Output from Server =>" + output);

    }
    
    
    public static void testPut() {
        String url = "http://localhost:8080/footballer/api/mobile/v1/field-service/update/100000/e7afdd55291a3e3796a6d3d83e70d6dd";
        String input = "{\"name\":\"Field2Update\",\"ownerUserId\":\"100\",\"number\":\"10\",\"type\":\"5v5\",\"price\":\"350\",\"conditions\":\"fake grass\",\"pic\":\"sdfsdfas\",\"timeTemplateId\":\"10\"}";

        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, input);

        String output = response.getEntity(String.class);
        System.out.println("put response Output from Server =>" + output);

    }
}
*/