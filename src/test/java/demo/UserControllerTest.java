package demo;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

	private final String URL = "http://localhost:8099/demo/users/";
	
	@Test
	public void testFindById() throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(URL + 1);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		
//		assertThat(response.getStatusLine().getStatusCode(), is(HttpStatus.OK) );
		
		HttpEntity entity = response.getEntity();
		System.out.println(entity.toString());
	}
}
