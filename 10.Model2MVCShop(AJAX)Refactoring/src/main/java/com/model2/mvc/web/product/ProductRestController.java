package com.model2.mvc.web.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;


//==> 회원관리 Controller
@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductRestController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping(value = "json/addProduct" , method = RequestMethod.POST)
	public Map addProduct( @RequestBody Product product  ) throws Exception {
		
		System.out.println("/product/addProduct : Post");
		
		productService.addProduct(product);
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("message", "상품등록이 완료 되었습니다.");
		
		
		return map;
	}
	
	
	@RequestMapping(value = "json/getProduct/{prodNo}" , method = RequestMethod.GET)
	public Product getProduct( @PathVariable int prodNo  ) throws Exception {
		
		System.out.println("/product//getProduct : GET");
		//Business Logic
		// Model 과 View 연결
		
		//세션처리는 어떻게 하지 ?? 메뉴가 서치인지 매니저인지 ??? ?? 리퀘스트 스콥에 실어서 보내야겠네 
		//그리고 jsp를 보낼 필요없이 data만 보내주면 되니까 menu도 필요가 없는거아니여 ?? 
		
		
		return productService.getProduct(prodNo);
	}
	@RequestMapping( value="json/getProductList" )
	public Map getProductList( @RequestBody(required = false ) JSONObject jsonObject) throws Exception{
		
		System.out.println("/product/json/getProductList : GET ,POST");
		System.out.println("Not toString : "+jsonObject.get("search"));
		System.out.println(jsonObject.get("search").toString());
		
		
		/*
		 * Search search1 = (Search)(jsonObject.get("search"));
		 * System.out.println(search1);
		 */
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println("jssss"+jsonObject.get("search")); // jsobject get하면 오브젝트가온다
		String jsonValue = objectMapper.writeValueAsString(jsonObject.get("search"));// mapper.writeValueAsString을 하면 오브젝트를 json String으로 바꾸어준다
		System.out.println("jsonValue :: '"+jsonValue+"'");
		
		Search search = objectMapper.readValue(jsonValue, Search.class );//제이슨형태{key : value, key : value}를 바인딩해준다.
		System.out.println("서치는"+search);
		
		System.out.println(search);
		
		/*if(search.getSearchCondition().equals("null") || search.getSearchCondition()==null) {
			search.setSearchCondition("");
		}
		if (search.getSearchKeyword().equals("null") || search.getSearchKeyword()==null) {
			search.setSearchKeyword("");
		}	*/	
		Map map = new HashMap<String, Object>();
		
		map =productService.getProductList(search); //map으로 담겨온다 List<User> user =(User)map.get("list") user[0],user[1],user[2]
		 List list = new ArrayList();
		 list = (List<Product>) map.get("list");
		 System.out.println("리스트는 ? :"+list.toString());
		 Map returnMap = new HashMap<String, Object>();
		 
		 returnMap.put("list", map.get("list"));
		 System.out.println("리스트트트"+returnMap.get("list"));
		//Business Logic
		return returnMap;
		}
	
	@RequestMapping(value =  "json/updateProduct")
	public Product updateProduct(@RequestBody Product product) throws Exception{
		
		System.out.println("json/updateProduct : POST" );
		
		productService.updateProduct(product);
		
		
		return productService.getProduct(product.getProdNo()) ;
	}
		
	}
	

	
