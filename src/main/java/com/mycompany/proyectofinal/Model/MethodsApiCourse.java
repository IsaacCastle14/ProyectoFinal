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
public class MethodsApiCourse {

    ArrayList<CourseModel> courseList;
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
            case 5 -> {
                return userTemp[5];
            }
            case 6 -> {
                return userTemp[6];
            }
            case 7 -> {
                return userTemp[7];
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

    public MethodsApiCourse() {
        courseList = new ArrayList();
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

    private ArrayList<CourseModel> convertToArrayList(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        courseList = null;
        try {
            courseList = objectMapper.readValue(responseBody, new TypeReference<ArrayList<CourseModel>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public void postApi(String url, String description, String name, String blockBelonging,  String teachingHours, String modality, String independentWorkHours, String creditQuantity, String initials ) {
        codigo = 0;
        HttpClient client = HttpClient.newHttpClient();

        String json = "{ \"description\": \"" + description + "\", \"name\": \"" + name + "\", \"blockBelonging\": \"" + blockBelonging + "\", \"teachingHours\": \"" + teachingHours + "\", \"modality\": \"" + modality + "\", \"independentWorkHours\": \"" + independentWorkHours + "\", \"creditQuantity\": \"" + creditQuantity + "\", \"initials\": \"" + initials + "\" }";

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

    public void patchApi(String url, Long id, String description, String name, String blockBelonging,  String teachingHours, String modality, String independentWorkHours, String creditQuantity, String initials) {
        codigo = 0;
        HttpClient client = HttpClient.newHttpClient();

        String json = "{ \"id\": \"" + id + "\", \"description\": \"" + description + "\", \"name\": \"" + name + "\", \"blockBelonging\": \"" + blockBelonging + "\", \"teachingHours\": \"" + teachingHours + "\", \"modality\": \"" + modality + "\", \"independentWorkHours\": \"" + independentWorkHours + "\", \"creditQuantity\": \"" + creditQuantity + "\", \"initials\": \"" + initials + "\" }";

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
        String[][] matrixRecord = new String[this.courseList.size()][CourseModel.HEADER_COURSE.length];
        for (int i = 0; i < matrixRecord.length; i++) {
            for (int j = 0; j < matrixRecord[0].length; j++) {
                matrixRecord[i][j] = this.courseList.get(i).getData(j);
            }
        }
        return matrixRecord;
    }

    public CourseModel find(String name) {
        for (CourseModel course : courseList) {
            if (course.getName().equalsIgnoreCase(name)) {
                return course;
            }
        }
        return null;
    }
}
