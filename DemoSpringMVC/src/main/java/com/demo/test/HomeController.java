package com.demo.test;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	RestHighLevelClient x;
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! TEsting The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value="/search", method= RequestMethod.GET)
	public String  resultname(Model model, @RequestParam("searchfor") String txt ) {
		
		String[] include = new String[] {"*"};
		String[] exclude = new String[] {"@timestamp","@version"};
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		
		searchSourceBuilder.query(QueryBuilders.wildcardQuery("_all", txt));
		searchSourceBuilder.fetchSource(include, exclude);
		
		SearchRequest searchRequest= new SearchRequest("bottle");
		//searchRequest.types("books");
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse searchResponse = null;
		try {
			searchResponse = x.search(searchRequest);
		} catch (IOException e) {
			e.printStackTrace();
			
			model.addAttribute("resultvalue1", "Error While Connecting Elastic Server... ! Please Verify Connection" );
			return "searchresult"; 
		}
		
		 SearchHit[] searchHits = searchResponse.getHits().getHits();
		    StringBuilder builder = new StringBuilder();
		    int length = searchHits.length;
		    builder.append("[");
		    for (int i = 0; i < length; i++) {
		        if (i == length - 1) {
		            builder.append(searchHits[i].getSourceAsString());
		        } else {
		            builder.append(searchHits[i].getSourceAsString());
		            builder.append(",");
		        }
		    }
		    builder.append("]");
		         String result= builder.toString();
		         
		         System.out.println(searchResponse.getTookInMillis()+"REsult from String Builder-----   "+ result);
		
		model.addAttribute("resultvalue", result );
		
		
		return "searchresult";
		
	}
	
}
