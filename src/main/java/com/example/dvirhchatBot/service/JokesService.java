package com.example.dvirhchatBot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class JokesService {

    OkHttpClient client = new OkHttpClient().newBuilder().build();

    @Autowired
    ObjectMapper om;

    public String getJokesId(String keyword) throws IOException {
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.chucknorris.io/jokes/search?query=" + keyword)
                .method("GET", null)
                .addHeader("authority", "www.yahoo.com")
                .addHeader("accept", "*/*")
                .addHeader("accept-language", "en-US,en;q=0.9")
                .addHeader("cookie", "Your-Cookie-Values-Here")
                .addHeader("if-none-match", "W/\"15-umwiS4rIitQ2ijB3W71Mutm+O6U\"")
                .addHeader("referer", "https://www.yahoo.com/news/weather/israel/tel-aviv/giv'atayim-1967398")
                .addHeader("sec-ch-ua", "\"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Linux\"")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                .addHeader("x-webp", "1")
                .build();
        Response response = client.newCall(request).execute();
        String responseStr = response.body().string();
        JokesResponse response2 = om.readValue(responseStr, JokesResponse.class);
        return response2.getResult().get(0).getValue();
    }

    static class JokesResponse {
        List<JokesResponseObject> result;

        public List<JokesResponseObject> getResult() {
            return result;
        }
    }

    static class JokesResponseObject {
        String value;

        public String getValue() {
            return value;
        }
    }
}
