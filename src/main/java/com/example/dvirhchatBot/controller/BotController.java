package com.example.dvirhchatBot.controller;

import com.example.dvirhchatBot.service.AmazonService;
import com.example.dvirhchatBot.service.JokesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class BotController {

    @Autowired
    AmazonService amazonService;

    @Autowired
    JokesService jokesService;

    @GetMapping("/bot/amazon")
    public ResponseEntity<?> getProduct(@RequestParam String keyword) throws IOException {
        return new ResponseEntity<>(amazonService.searchProducts(keyword), HttpStatus.OK);
    }

    @GetMapping("/bot/jokes")
    public ResponseEntity<?> getJokes(@RequestParam String keyword) throws IOException {
        return new ResponseEntity<>(jokesService.getJokesId(keyword), HttpStatus.OK);
    }

    @RequestMapping(value = "bot", method = {RequestMethod.POST})
    public ResponseEntity<?> getBotResponse(@RequestBody BotQuery query) throws IOException {
        HashMap<String, String> params = query.getQueryResult().getParameters();
        String res = "Not found";
        if (params.containsKey("joke")) {
            res = jokesService.getJokesId(params.get("joke"));
        } else if (params.containsKey("product")) {
            res = amazonService.searchProducts(params.get("product"));
        }
        return new ResponseEntity<>(BotResponse.of(res), HttpStatus.OK);
    }


    static class BotQuery {
        QueryResult queryResult;

        public QueryResult getQueryResult() {
            return queryResult;
        }
    }

    static class QueryResult {
        HashMap<String, String> parameters;

        public HashMap<String, String> getParameters() {
            return parameters;
        }
    }

    static class BotResponse {
        String fulfillmentText;
        String source = "BOT";

        public String getFulfillmentText() {
            return fulfillmentText;
        }

        public String getSource() {
            return source;
        }

        public static BotResponse of(String fulfillmentText) {
            BotResponse res = new BotResponse();
            res.fulfillmentText = fulfillmentText;
            return res;
        }
    }
}