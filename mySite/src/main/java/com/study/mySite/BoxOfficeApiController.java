package com.study.mySite;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BoxOfficeApiController {
	
	@GetMapping("/api/boxoffice")
	public ResponseEntity<String> boxOffice(@RequestParam("targetDt") String targetDt){
		System.out.println("api 호출됨 : ");
	 
	try {
		String apikey="7f15d8077a11311704a6b9142f74f4e8";
		String url = "https://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key="+apikey +"&targetDt="+targetDt;
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(url,String.class);
		return ResponseEntity.ok(result);
	} catch (Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(500).body("영화 open api 호출 실패");
		}
	}
}
