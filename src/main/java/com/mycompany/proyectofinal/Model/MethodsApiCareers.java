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
public class MethodsApiCareers {

    ArrayList<CareerModel> careerList;
    private int codigo = 0;
    private Long select;
    private String[] userTemp;

    public String getUserTemp(int field) {
        switch (field) {
            case 0 -> {
                return userTemp[0];
            }
            case 1 -> {
                return userTemp[1];
            }
            case 2 -> {
                return userTemp[2];
            }
            case 3 -> {
                return userTemp[3];
            }
            case 4 -> {
                return userTemp[4];
            }

        }
        return "";
    }

    public void setUserTemp(String[] userTemp) {
        this.userTemp = userTemp;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public Long getSelect() {
        return select;
    }

    public void setSelect(Long delete) {
        this.select = delete;
    }

    public MethodsApiCareers() {
        careerList = new ArrayList();
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

    private ArrayList<CareerModel> convertToArrayList(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        careerList = null;
        try {
            careerList = objectMapper.readValue(responseBody, new TypeReference<ArrayList<CareerModel>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return careerList;
    }

    public void postApi(String url, String careerCode, String description, String workingMarket, String name, String professionalProfile) {
        codigo = 0;
        HttpClient client = HttpClient.newHttpClient();

        String json = "{ \"careerCode\": \"" + careerCode + "\", \"description\": \"" + description + "\", \"workingMarket\": \"" + workingMarket + "\", \"name\": \"" + name + "\", \"professionalProfile\": \"" + professionalProfile + "\" }";

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

    public void patchApi(String url, Long id, String careerCode, String description, String workingMarket, String name, String professionalProfile) {
        codigo = 0;
        HttpClient client = HttpClient.newHttpClient();

        String json = "{ \"id\": \"" + id + "\", \"careerCode\": \"" + careerCode + "\", \"description\": \"" + description + "\", \"workingMarket\": \"" + workingMarket + "\", \"name\": \"" + name + "\", \"professionalProfile\": \"" + professionalProfile + "\" }";

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

    public void deleteApiData(String url) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> response.statusCode())
                .thenAccept(response -> setCodigo(response))
                .exceptionally(e -> {
                    System.err.println("Error occurred: " + e.getMessage());
                    return null;
                })
                .join();
    }

    public String[][] getMatrix() {
        String[][] matrixRecord = new String[this.careerList.size()][CareerModel.HEADER_CAREER.length];
        for (int i = 0; i < matrixRecord.length; i++) {
            for (int j = 0; j < matrixRecord[0].length; j++) {
                matrixRecord[i][j] = this.careerList.get(i).getData(j);
            }
        }
        return matrixRecord;
    }

    public CareerModel find(String name) {
        for (CareerModel career : careerList) {
            if (career.getName().equalsIgnoreCase(name)) {
                return career;
            }
        }
        return null;
    }
}
