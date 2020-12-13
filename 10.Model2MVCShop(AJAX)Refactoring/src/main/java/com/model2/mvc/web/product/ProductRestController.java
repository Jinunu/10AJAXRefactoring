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


//==> ȸ������ Controller
@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method ���� ����
		
	public ProductRestController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
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
		
		map.put("message", "��ǰ����� �Ϸ� �Ǿ����ϴ�.");
		
		
		return map;
	}
	
	
	@RequestMapping(value = "json/getProduct/{prodNo}" , method = RequestMethod.GET)
	public Product getProduct( @PathVariable int prodNo  ) throws Exception {
		
		System.out.println("/product//getProduct : GET");
		//Business Logic
		// Model �� View ����
		
		//����ó���� ��� ���� ?? �޴��� ��ġ���� �Ŵ������� ??? ?? ������Ʈ ���߿� �Ǿ �����߰ڳ� 
		//�׸��� jsp�� ���� �ʿ���� data�� �����ָ� �Ǵϱ� menu�� �ʿ䰡 ���°žƴϿ� ?? 
		
		
		return productService.getProduct(prodNo);
	}
	@RequestMapping( value="json/getProductList" )
	public Map getProductList( @RequestBody(required = false ) Search search) throws Exception{
		
		System.out.println("/product/json/getProductList : GET ,POST");
		if(search.getCurrentPage()==0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		System.out.println("��ġ�� ?? :"+search);
		
		if(search.getSearchCondition()==null ||search.getSearchCondition().equals("null") ) {
			search.setSearchCondition("");
		}
		if (  search.getSearchKeyword()==null || search.getSearchKeyword().equals("null")) {
			search.setSearchKeyword("");
		}
		System.out.println(search);
		
		
		Map map = new HashMap<String, Object>();
		
		map =productService.getProductList(search); //map���� ��ܿ´� List<User> user =(User)map.get("list") user[0],user[1],user[2]
		 List list = new ArrayList();
		 list = (List<Product>) map.get("list");
		 System.out.println("����Ʈ�� ? :"+list.toString());
		 Map returnMap = new HashMap<String, Object>();
		 
		 returnMap.put("list", map.get("list"));
		 System.out.println("����ƮƮƮ"+returnMap.get("list"));
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
	

	
