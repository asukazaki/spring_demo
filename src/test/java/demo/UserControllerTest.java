package demo;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.nio.charset.StandardCharsets;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

	private final String URL = "http://localhost:8099/demo/users/";
	
	@Test
	public void testFindById() throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(URL + 1);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		
		assertThat(response.getStatusLine().getStatusCode(), is(200) );
		
		String responseBody = EntityUtils.toString(response.getEntity());
		responseBody = responseBody.substring(1, responseBody.length()-1);
		ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(responseBody, User.class);
		System.out.println(user.getUserName());
	}
}
