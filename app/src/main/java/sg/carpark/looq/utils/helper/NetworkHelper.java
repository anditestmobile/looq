package sg.carpark.looq.utils.helper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Objects;

import sg.carpark.looq.utils.constants.Constants;

public class NetworkHelper {
    /*GET*/
    public static Object getWebservice(String url, Class<?> responseType) {
        int flag = 0;

        HttpEntity<?> response = null;
//        while (flag == 0) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        String token = (String) Helper.getItemParam(Constants.TOKEN);
        String bearerToken = Constants.BEARER.concat(token);
        requestHeaders.set("Authorization", bearerToken);

        HttpEntity<?> entity = new HttpEntity<>(requestHeaders);
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        } catch (Exception e) {
            if (e.getMessage().equals("401 Unauthorized")) {
            }

        }
//        }
        return Objects.requireNonNull(response).getBody();
    }

    /*POST*/
    public static Object postWebserviceLogin(String url, Class<?> responseType, Object body) {
        int flag = 0;
        ResponseEntity<?> responseEntity = null;
        while (flag == 0) {
            flag = 1;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

            requestHeaders.set("Authorization", Constants.AUTHORIZATION_LOGIN);
            requestHeaders.set("Content-Type", Constants.HTTP_HEADER_CONTENT_TYPE);

            HttpEntity<?> requestEntity = new HttpEntity<>(body, requestHeaders);
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

            try {
                responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
            } catch (Exception e) {
                if (e.getMessage().contains("400")) {
                    Helper.setItemParam(Constants.ERROR_LOGIN, "1");
                }
                e.getMessage();
            }

        }
        return Objects.requireNonNull(responseEntity).getBody();
    }

    public static Object postWebserviceWithBody(String url, Class<?> responseType, Object body) {
        int flag = 0;
        ResponseEntity<?> responseEntity = null;
        while (flag == 0) {
            flag = 1;

            String token = (String) Helper.getItemParam(Constants.TOKEN);
            String bearerToken = Constants.BEARER.concat(token);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            requestHeaders.set("Authorization", bearerToken);
            HttpEntity<?> requestEntity = new HttpEntity<>(body, requestHeaders);
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

            try {
                responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
            } catch (Exception e) {
                if (e.getMessage().equals("401 Unauthorized"))
                    flag = 0;
            }
        }
        return Objects.requireNonNull(responseEntity).getBody();
    }

    public static Object postWebserviceWithBodyWithoutHeaders(String url, Class<?> responseType, Object body) {
        int flag = 0;
        ResponseEntity<?> responseEntity = null;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        HttpEntity<?> requestEntity = new HttpEntity<>(body, requestHeaders);
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
        } catch (Exception e) {
            e.getMessage();
        }

        return responseEntity != null ? responseEntity.getBody() : null;
    }

    /*PUT*/
    public static Object putWebserviceWithBody(String url, Class<?> responseType, Object body) {
        int flag = 0;
        ResponseEntity<?> responseEntity = null;
        while (flag == 0) {
            flag = 1;

            String token = (String) Helper.getItemParam(Constants.TOKEN);
            String bearerToken = Constants.BEARER.concat(token);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            requestHeaders.set("Authorization", bearerToken);
            HttpEntity<?> requestEntity = new HttpEntity<>(body, requestHeaders);
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

            try {
                responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseType);
            } catch (Exception e) {
                if (e.getMessage().equals("401 Unauthorized"))
                    flag = 0;
            }
        }
        return Objects.requireNonNull(responseEntity).getBody();
    }

}
