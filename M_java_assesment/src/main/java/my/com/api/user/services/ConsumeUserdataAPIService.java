package my.com.api.user.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.api.user.model.UserData;
import my.com.api.user.repositories.UserRepository;







@Component
public class ConsumeUserdataAPIService implements CommandLineRunner{


	Logger log = LoggerFactory.getLogger(this.getClass());



	@Autowired
	private UserRepository userRepository;

	@Value("${user.publicAPI.url}")
	String api_url;

	@Value("${user.publicAPI.appid}")
	String api_Id;




	@Override
	public void run(String... args) throws Exception {
		log.info("Simulator v1.0: Country API Consume Service");

		getUserDetails();


	}








	@SuppressWarnings("unchecked")
	private void getUserDetails() throws IllegalAccessException, InvocationTargetException
	{

		try {
		List<Map> list = null;
		List<UserData> listObjects = new ArrayList<UserData>();
		UserData userObj = null;
		ObjectMapper mapper = new ObjectMapper();
		RestTemplate restTemplate = new RestTemplate();
		//String url = "https://dummyapi.io/data/api/user?page=1&limit=10";
		//String result = restTemplate.getForObject(uri, String.class);
		//System.out.println(result);
		HttpHeaders headers = new HttpHeaders();
		headers.add("app-id",
				api_Id);
		//headers.add("x-rapidapi-host", "restcountries-v1.p.rapidapi.co");

		HttpEntity<String> entity = new HttpEntity<>("body", headers);

		ResponseEntity<Map> response=restTemplate.exchange(api_url, HttpMethod.GET,entity, Map.class); 

		
		if (response != null && response.getStatusCode().value() == 200) {
		    list = (List<Map>) response.getBody().get("data"); // this depends on the response
		    for (Map item : list) { // we iterate for each one of the items of the list transforming it
		        
		    	userObj = mapper.convertValue(item, UserData.class);
		         if (userObj!= null) {
		        	 userRepository.save(userObj);
				}else 
				{
					 System.out.println("User is null");
				}
		         
		        listObjects.add(userObj);
		    }
		    
		}
		}catch (Exception e) {
			log.error("Error occured in the getUserDetails"+e.fillInStackTrace());
		}

	}


}
