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

import com.google.gson.Gson;

import springfox.documentation.spring.web.json.Json;
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
//    public @ResponseBody String querySummoner(@RequestBody String equation) throws UnsupportedEncodingException {
    public @ResponseBody Summoner querySummoner(@RequestBody String equation) throws UnsupportedEncodingException {
    	final String url = riotApiEndpoint;

	       Calculator cal = new Calculator();
	       Date dt = new Date();
	        
	       int teamId = 7;
	       long now = dt.getTime();
	       double result = cal.calculate(equation);
	        
//	       Summoner summoner = new Summoner(teamId, now, result,null);
	
	       Gson gson = new Gson();
	       
	      // String request = gson.toJson(summoner);
	       String request = gson.toJson(teamId);
	       request += gson.toJson(now);
	       request += gson.toJson(result);
	       String msg = restTemplate.postForObject(url, request, String.class);
	       Summoner summoner = new Summoner(teamId, now, result,msg);
//	       teamId = gson.fromJson("teamId", Integer.class);
//	       now = gson.fromJson("now", Long.class);
//	       result = gson.fromJson("result", Double.class);
	       
	  //     Summoner summoner2 = new Summoner(teamId, now, result, msg);
        //return string;
	       return summoner;
    }
}
