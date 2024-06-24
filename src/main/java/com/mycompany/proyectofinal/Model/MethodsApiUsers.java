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
    private String[] rowSelected;
    private Long select;
    private int codigo = 0;

    public ArrayList<UserModel> getUserList() {
        return userList;
    }

    public Long getSelect() {
        return select;
    }

    public void setSelect(Long select) {
        this.select = select;
    }

    public String[] getRowSelected() {
        return rowSelected;
    }

    public void setRowSelected(String[] rowSelected) {
        this.rowSelected = rowSelected;
    }

    public UserModel getUserTemp() {
        return userTemp;
    }

    public void setUserTemp(UserModel userTemp) {
        this.userTemp = userTemp;
    }

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
                        setUserTemp(userSearch);
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

    public void postApi(String url) {
        codigo = 0;
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(""))
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
        System.out.println("JSON enviado: " + json);

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

    public String[][] getMatrix() {
        String[][] matrixRecord = new String[this.userList.size()][UserModel.HEADER_STUDENTS.length];
        for (int i = 0; i < matrixRecord.length; i++) {
            for (int j = 0; j < matrixRecord[0].length; j++) {
                if (this.userList.get(i).getListaPerfil().isEmpty()) {
                    matrixRecord[i][j] = this.userList.get(i).getData(j);
                }
            }
        }
        return matrixRecord;
    }

    public String[][] getMatrixWIthoutFilters() {
        String[][] matrixRecord = new String[this.userList.size()][UserModel.HEADER_STUDENTS.length];
        for (int i = 0; i < matrixRecord.length; i++) {
            for (int j = 0; j < matrixRecord[0].length; j++) {
                //   if (this.userList.get(i).getCarrera() == null) {
                matrixRecord[i][j] = this.userList.get(i).getData(j);
                //   }
            }
        }
        return matrixRecord;
    }

    public String[][] getMatrixReportes(List<CareerModel> careerList) {
        String[][] matrixRecord = new String[this.userList.size()][UserModel.HEADER_Reports.length];

        for (int i = 0; i < this.userList.size(); i++) {
            UserModel userModel = this.userList.get(i);
            CareerModel careerModel = careerList.get(0);
            if (i < careerList.size()) {
                careerModel = careerList.get(i);
            }

            matrixRecord[i][0] = userModel.getUser();

            if (userModel.getListaPerfil().size() > 0 && userModel.getListaPerfil().get(0).getTypeUser() != null && !userModel.getListaPerfil().get(0).getTypeUser().equals("")) {
                matrixRecord[i][1] = userModel.getListaPerfil().get(0).getTypeUser();
            } else {
                matrixRecord[i][1] = "No contiene";
            }

            if (userModel.getListaPerfil().size() > 1 && userModel.getListaPerfil().get(1).getTypeUser() != null && !userModel.getListaPerfil().get(1).getTypeUser().equals("")) {
                matrixRecord[i][2] = userModel.getListaPerfil().get(1).getTypeUser();
            } else {
                matrixRecord[i][2] = "No contiene";
            }

            if (careerModel.getUsuarios().size() > i && careerModel.getUsuarios().get(i).getUser() != null && careerModel.getUsuarios().get(i).getId()!= null) {
                matrixRecord[i][3] = careerModel.getUsuarios().get(i).getId().toString();
            } else {
                matrixRecord[i][3] = "No contiene";
            }
        }
        return matrixRecord;
    }           
    

    public UserModel find(String userP) {
        for (UserModel user : userList) {
            if (user.getUser().equalsIgnoreCase(userP)) {
                return user;
            }
        }
        return null;
    }
}
