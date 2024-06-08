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
    private int codigo = 0;

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

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

    public void postApi(String url, String user, String firstName, String lastName, String email, String password, String phone, String carne) {
        codigo = 0;
        HttpClient client = HttpClient.newHttpClient();

        String json = "{ \"user\": \"" + user + "\", \"firstName\": \"" + firstName + "\", \"lastName\": \"" + lastName + "\", \"email\": \"" + email + "\", \"password\": \"" + password + "\", \"phone\": \"" + phone + "\", \"carne\": \"" + carne + "\" }";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> response.statusCode())
                .thenAccept(response -> setCodigo(response))
                .join();
    }
}
