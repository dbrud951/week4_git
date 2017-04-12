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
import univ.lecture.riotapi.model.Model;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * Created by tchi on 2017. 4. 1..
 */
@RestController
@RequestMapping("/api/v1/")
@Log4j
public class RiotApiController {
    @Autowired
    private RestTemplate restTemplate;//RestTemplate 객체 생성

    @Value("${riot.api.endpoint}")
    private String riotApiEndpoint;

    @Value("${riot.api.key}")
    private String riotApiKey;
    
    @RequestMapping(value = "/calc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes={"application/json"})
    @ResponseBody
   public String querySummoner(@RequestBody String equation) throws UnsupportedEncodingException {
        final String url = riotApiEndpoint;                   
       
        Calculator calc=new Calculator();
        
        Date dt = new Date();
        
        int teamId = 7;
        long now = dt.getTime(); 
        double result = calc.calculate(equation);
        
        Model model = new Model(teamId,now,result);                
//        Gson gson = new Gson();
        
        
//        String request = gson.toJson(summoner);
        String mineString = restTemplate.postForObject(url, model, String.class);
//       Summoner summoner2 = new Summoner(teamId,now,result);
       
      return mineString;
    }
}
