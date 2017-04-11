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
    
// @RequestMapping(value = "/summoner/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public Summoner querySummoner(@PathVariable("name") String summonerName) throws UnsupportedEncodingException {
//    	/*final String url = riotApiEndpoint + "/summoner/by-name/" +
//                summonerName +
//                "?api_key=" +
//                riotApiKey;
//    	
//       String response = restTemplate.getForObject(url, String.class);//(url, 반환될 데이터 타입), 메서드는 GET을 수행하고 HTTP 응답을 원하는 객체 타입으로 변환해서 반환한다.
//    	//int response = restTemplate.getForObject(url, Integer.class);
//    	response = Integer.toString(C.calculate(summonerName));
//        Map<String, Object> parsedMap = new JacksonJsonParser().parseMap(response);
//
//        parsedMap.forEach((key, value) -> log.info(String.format("key [%s] type [%s] value [%s]", key, value.getClass(), value)));
//
//        Map<String, Object> summonerDetail = (Map<String, Object>) parsedMap.values().toArray()[0];
//        String queriedName = (String)summonerDetail.get("name");
//        int queriedLevel = (Integer)summonerDetail.get("summonerLevel");*/
//    	final String url = riotApiEndpoint;
//     	Calculator C = new Calculator();
//    	Summoner summoner = new Summoner(queriedName, queriedLevel);
//
//        return summoner;
//    }
    @RequestMapping(value = "/calc/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody Summoner querySummoner(@RequestBody String ecuation) throws UnsupportedEncodingException {
        final String url = riotApiEndpoint;

        Calculator cal = new Calculator();

        Date dt = new Date();
        
        int teamId = 7;
        long now = dt.getTime();
        double result = cal.calculate(ecuation);
        
        Summoner summoner = new Summoner(teamId, now, result);

        return summoner;
    }
}
