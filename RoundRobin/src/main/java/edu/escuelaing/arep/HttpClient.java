package edu.escuelaing.arep;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class HttpClient {
    private String[] ports = {":8001",":8002",":8003"};
    private int nServer = 0;
    private String url = "http://192.168.99.100";

    public String getMessage() throws UnirestException {
        HttpResponse<String> apiResponse = Unirest.get(url+ports[nServer]+"/messages").asString();
        System.out.println("Petición GET de "+url+ports[nServer]);
        return apiResponse.getBody();
    }

    public String postMessage(String message) throws UnirestException {
        HttpResponse<String> apiResponse = Unirest.post(url+ports[nServer]+"/messages")
                .body(message)
                .asString();
        System.out.println("Petición POST de "+url+ports[nServer]);
        return apiResponse.getBody();
    }

    public void changeNumberServer(){
        nServer=(nServer + 1) % ports.length;
    }
}
