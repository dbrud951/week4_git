package univ.lecture.riotapi.controller;

import lombok.extern.log4j.Log4j;
import net.minidev.json.parser.JSONParser;

import org.json.JSONObject;
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

    Calculator cal = new Calculator();

    
    class Data{
    	int teamId = 7;
    	long now = System.currentTimeMillis();
    	double result;
    	
    	public int getTeamId(){
    		return teamId;
    	}
    	
    	public long getNow(){
    		return now;
    	}
    	
    	public void setResult(String exp){
    		this.result = cal.calculate(exp);
    	}
    	
    	public double getResult(){
    		return result;
    	}
    	
    }
    
    @RequestMapping(value = "/calc/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String querySummoner(@RequestBody String equation) throws UnsupportedEncodingException {
        final String url = riotApiEndpoint;

        Calculator cal = new Calculator();

        Data dt = new Data();
        
        dt.setResult(equation);
        
        int teamId = dt.getTeamId();
        long now = dt.getNow();
        double result = dt.getResult();
        
       
        JSONObject j = new JSONObject();
        j.put("teamId", teamId);
        j.put("now", now);
        j.put("result", result);
        
//        Json js = new Json(url);
        
//        Data resultData = restTemplate.postForObject(url, dt, String.class);
        
        String sendMsg = j.toString();
        @SuppressWarnings("deprecation")
		JSONParser parser = new JSONParser();
       // String response = restTemplate.postForObject(url, dt, String.class);
        JSONObject jsonObj = (JSONObject)parser.parse(sendMsg);
        teamId = (int) jsonObj.get("teamId");
        now = (long) jsonObj.get("now");
        result = (double) jsonObj.get("result");
        
        String summoner = new Summoner(teamId, now, result);

        return summoner;
    }
}
