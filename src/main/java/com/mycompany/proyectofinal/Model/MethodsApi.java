/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal.Model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 *
 * @author Marco
 */
public class MethodsApi {

    ArrayList<UserModel> userList;

    public MethodsApi() {
        userList = new ArrayList();
    }

    public void getApiData(String url) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> response.body())
                .thenApply(responseBody -> convertToArrayList(responseBody))
                .join();

    }

    private ArrayList<UserModel> convertToArrayList(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        userList = null;
        try {
            userList = objectMapper.readValue(responseBody, new TypeReference<ArrayList<UserModel>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public boolean searchUser(String user, String password) {
        boolean login = false;
        if (userList != null) {
            for (UserModel userSearch : userList) {
                if (userSearch.getUser().equals(user)) {
                    if (userSearch.getPassword().equals(password)) {
                        login = true;
                    }
                }
            }
        }
        return login;
    }
}
