package univ.lecture.riotapi.controller;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import univ.lecture.riotapi.Calculator;
import univ.lecture.riotapi.model.Summoner;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * Created by tchi on 2017. 4. 1..
 */
@RestController
@RequestMapping("/api/v1")
@Log4j
public class RiotApiController {
    @Autowired
    private RestTemplate restTemplate;//RestTemplate 객체 생성

    @Value("${riot.api.endpoint}")
    private String riotApiEndpoint;

    @Value("${riot.api.key}")
    private String riotApiKey;

    @RequestMapping(value = "/calc/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody Summoner querySummoner(@RequestBody String ecuation) throws UnsupportedEncodingException {
        final String url = riotApiEndpoint;

//        Calculator cal = new Calculator();
//
//        Date dt = new Date();
//        
//        int teamId = 7;
//        long now = dt.getTime();
//        double result = cal.calculate(ecuation);
//        
//        Summoner summoner = new Summoner(teamId, now, result);
        Calculator cal = new Calculator();
        
        String request = "{"
        + "\"teamId\":\"7\","
        + "\"now\":"+System.currentTimeMillis()
        + "\"cal\":"+cal.calculate(equation)
        + "}";

       
       String response = restTemplate.postForObject(url, request, String.class);
       Map<String, Object> parsedMap = new JacksonJsonParser().parseMap(response);
       

       Map<String, Object> summonerDetail = (Map<String, Object>) parsedMap.values().toArray()[0];
       int teamId = (Integer)summonerDetail.get("teamId");
       int now = (Integer)summonerDetail.get("now");
       double result = (double)summonerDetail.get("result");
//       summonerDetail=(Map<String,Object>)parsedMap.values().toArray()[0];
//       parsedMap = new JacksonJsonParser().parseMap(request);
       
       
       
       Summoner summoner = new Summoner(teamId, now, result);

       return summoner;
        return summoner;
    }
}
