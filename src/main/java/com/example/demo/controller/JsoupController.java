package com.example.demo.controller;

import java.util.Objects;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsoupController {
	//썸네일 링크	
	@GetMapping("/thumbnail")
	public String thumbnail() {
		final String inflearnUrl = "https://www.inflearn.com/courses/it-programming";
		Connection conn = Jsoup.connect(inflearnUrl);
		StringBuilder sb = new StringBuilder();
		try {
			Document document = conn.get();
			Elements imgUrlElements = document.getElementsByClass("swiper-lazy");
			for (Element element : imgUrlElements) {
				sb.append(element.attr("abs:src")+"<br>");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	// 강의 제목
	@GetMapping("/title")
	public String courseTitle() {
		final String inflearnUrl = "https://www.inflearn.com/courses/it-programming";
		Connection conn = Jsoup.connect(inflearnUrl);
		StringBuilder sb = new StringBuilder();
		try {		
			Document document = conn.get();
			Elements titleUrlElements = document.select("div.card-content>div.course_title");
			Elements instructorElements = document.getElementsByClass("instructor");
			for (Element element : titleUrlElements) {
				sb.append(element.text()+"<br>");
			}
			
			for (Element element : instructorElements) {
				sb.append(element);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	@GetMapping("/price")
	public String coursePrice() {
		final String inflearnUrl = "https://www.inflearn.com/courses/it-programming";
		Connection conn = Jsoup.connect(inflearnUrl);
		StringBuilder sb = new StringBuilder();
		try {		
			Document document = conn.get();
			Elements priceUrlElements = document.select("div.card-content>div.price");
			for (Element element : priceUrlElements) {
				String price = element.text();
				String realPrice = getRealPrice(price);
				String salePrice = getSalePrice(price);
				
				int nrealPrice = toInt(realPrice);
				int nsalePrice = toInt(salePrice);
						
				sb.append("가격: "+ nrealPrice + "&nbsp;");
				if(nrealPrice!=nsalePrice)
					sb.append("할인가격: " + nsalePrice);
				sb.append("<br>");
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	private String getRealPrice(String price) {
		return price.split("")[0];
	}
	
	private String getSalePrice(String price) {
		String[] prices = price.split("");
		return prices.length == 1? prices[0]:prices[1];
	}
	
	private int toInt(String str) {
		str = str.replaceAll("\\₩", "");
		str = str.replaceAll(",", "");
		return Integer.parseInt(str);
	}
	
	//강의 링크 & 평점
	@GetMapping("/link")
	public String course_link() {
		final String inflearnUrl = "https://www.inflearn.com/courses/it-programming";
		Connection conn = Jsoup.connect(inflearnUrl);
		StringBuilder sb = new StringBuilder();
		try {
			Document document = conn.get();
			Elements linkElements = document.select("a.course_card_front");
			for (Element element : linkElements) {
				final String innerUrl = element.attr("abs:href");
					Connection innerConn = Jsoup.connect(innerUrl);
					Document innerDoc = innerConn.get();
					//Element ratingElement = innerDoc.selectFirst("div.dashboard-star__num");
					Element ratingElement = innerDoc.select("div.dashboard-star__num").get(0);
					double rating = Objects.isNull(ratingElement)? 0.0:Double.parseDouble(ratingElement.text());
					rating = rating*100/100.0;
				sb.append(element.attr("abs:href")+", 평점: "+rating +"<br>");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	//강의자, 강의 부가설명, 기술스택
	@GetMapping("/etc")
	public String etc() {
		final String inflearnUrl = "https://www.inflearn.com/courses/it-programming";
		Connection conn = Jsoup.connect(inflearnUrl);
		StringBuilder sb = new StringBuilder();
		
		try {
			Document document = conn.get();
			Elements instructorElements = document.getElementsByClass("instructor");
			Elements descriptionElements = document.select("p.course_description");
			Elements skillElements = document.select("div.course_skills>span");
			
			for (int i=0; i<instructorElements.size();i++) {
				String instructor = instructorElements.get(i).text();
				String description = descriptionElements.get(i).text();
				String skills = skillElements.get(i).text().replace("\\s","");
				
				sb.append("강의자 : " + instructor + "<br>" );
				sb.append("강의 부가설명 : " + description + "<br>" );
				sb.append("기술스택 : " + skills + "<br><br>" );
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
}
