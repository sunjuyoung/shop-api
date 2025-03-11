package com.project.shop.hotproduct.data;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

public class DataInitializer {

    String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzb2NpYWwiOmZhbHNlLCJuaWNrbmFtZSI6InRlc3QxMjMzIiwiaWQiOjMsInJvbGVOYW1lcyI6WyJDVVNUT01FUiJdLCJlbWFpbCI6InRlc3RAdGVzdC5jb20iLCJpYXQiOjE3NDAyOTE1MDQsImV4cCI6MTc0MDMzNDcwNH0.SCsHoRHwVnZrn5Oc3_SB_P384bgg5iADwIB6F4x_M7I";
    RestClient restClient = RestClient.builder()
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
            .baseUrl("http://localhost:9090")
            .build();
}
