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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

/**
 *
 * @author Marco
 */
public class MethodsApiUsers {

    ArrayList<UserModel> userList;
    private UserModel userTemp;

    public UserModel getUserTemp() {
        return userTemp;
    }

    public void setUserTemp(UserModel userTemp) {
        this.userTemp = userTemp;
    }

    private int codigo = 0;

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public MethodsApiUsers() {
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
                        userTemp = userSearch;
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

    public void patchApi(String url, Long id, String user, String firstName, String lastName, String email, String password, String phone, String carne, List<PerfilModel> listaPerfil) {
        codigo = 0;
        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode listaPerfilJson = objectMapper.valueToTree(listaPerfil);

        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("id", id);
        jsonNode.put("user", user);
        jsonNode.put("firstName", firstName);
        jsonNode.put("lastName", lastName);
        jsonNode.put("email", email);
        jsonNode.put("password", password);
        jsonNode.put("phone", phone);
        jsonNode.put("carne", carne);
        jsonNode.putArray("listaPerfil").addAll(listaPerfilJson);

        String json = jsonNode.toString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> response.statusCode())
                .thenAccept(response -> setCodigo(response))
                .join();
    }
}
