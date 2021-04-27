package u314;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Main {
    public static void main(String[] args) {
        String url = "http://91.241.64.178:7081/api/users/";
        RestTemplate restTemplate = new RestTemplate();
        User user = new User(3, "James", "Brown", 5);
        ObjectMapper mapper = new ObjectMapper();
        String password = "";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String jsessionid = response.getHeaders().get(HttpHeaders.SET_COOKIE).get(0).split("; ")[0];

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.COOKIE, jsessionid);
            HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(user), headers);
            response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            password += response.getBody();

            user.setName("James");
            user.setLastName("Brown");
            entity = new HttpEntity<>(mapper.writeValueAsString(user), headers);
            response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
            password += response.getBody();

            entity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url + user.getId(), HttpMethod.DELETE, entity, String.class);
            password += response.getBody();
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(password);
    }
}
