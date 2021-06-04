package u314;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Main {
    public static void main(String[] args) {
        String url = "http://91.241.64.178:7081/api/users/";
        RestTemplate restTemplate = new RestTemplate();
        User user = new User(3, "James", "Brown", 88);
        ObjectMapper mapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String code = "";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String jsessionid = response.getHeaders().get(HttpHeaders.SET_COOKIE).get(0);

            headers.add(HttpHeaders.COOKIE, jsessionid);
            HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(user), headers);
            response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            code += response.getBody();
            System.out.println("1-я часть кода: " + response.getBody());

            user.setName("Thomas");
            user.setLastName("Shelby");
            entity = new HttpEntity<>(mapper.writeValueAsString(user), headers);
            response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
            code += response.getBody();
            System.out.println("2-я часть кода: " + response.getBody());

            entity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url + user.getId(), HttpMethod.DELETE, entity, String.class);
            code += response.getBody();
            System.out.println("3-я часть кода: " + response.getBody());
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Код: " + code);
    }
}
