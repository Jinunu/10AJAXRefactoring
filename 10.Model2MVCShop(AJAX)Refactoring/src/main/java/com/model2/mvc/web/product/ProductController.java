package com.model2.mvc.web.product;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.portlet.multipart.MultipartActionRequest;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


//==> ȸ������ Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method ���� ����
		
	public ProductController(){
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
	

	/*@RequestMapping("/addProduct.do")
	public String addProduct( @ModelAttribute("produt") Product product ) throws Exception {

		System.out.println("/addProduct.do");
		//Business Logic
		System.out.println(product.getManuDate());
		String manuDate= product.getManuDate().replace("-", "");
		product.setManuDate(manuDate);
		System.out.println(product.getManuDate());
		productService.addProduct(product);
		
		return "forward:/listProduct.do?menu=manage";
	}
	*/
	@RequestMapping(value = "addProduct", method = RequestMethod.POST)
	public ModelAndView addProduct( @ModelAttribute("product") Product product ,@RequestParam("file1") MultipartFile file) throws Exception {

		System.out.println("/product/addProduct : POST");
		//Business Logic
		
		
		
		
		
		ModelAndView modelAndView = new ModelAndView();
		  product.setManuDate(product.getManuDate().replace("-", ""));
		  
		  
		  
		  if(!file.getOriginalFilename().isEmpty()) {
				file.transferTo(new File("C:\\Users\\admin\\git\\10AJAXRefactoring\\10.Model2MVCShop(AJAX)Refactoring\\WebContent\\images\\uploadFiles",file.getOriginalFilename()));
				product.setFileName(file.getOriginalFilename());
			}else {
				
			}
		  productService.addProduct(product);
		 
		modelAndView.setViewName("forward:/product/listProduct?menu=manage");
		
		//return "forward:/listProduct.do?menu=manage";
		return modelAndView;
	}
	
	@RequestMapping(value = "addProduct", method = RequestMethod.GET)
	public ModelAndView addProduct() throws Exception {

		System.out.println("/product/addProduct : GET");
		//Business Logic
		
		
		
		
		
		ModelAndView modelAndView = new ModelAndView();
		  
				
		 
		modelAndView.setViewName("redirect:/product/addProductView.jsp");
		
		//return "forward:/listProduct.do?menu=manage";
		return modelAndView;
	}
	
	
	
	@RequestMapping(value = "getProduct" , method = RequestMethod.GET)
	public ModelAndView getProduct( @RequestParam("prodNo") int prodNo , HttpSession session ) throws Exception {
		
		System.out.println("/product//getProduct : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product", product);
		
		modelAndView.setViewName("forward:/product/readProduct.jsp");
		
		if(session.getAttribute("menu").equals("manage")) {
			modelAndView.setViewName("forward:/product/updateProductView.jsp");
			//return "forward:/product/updateProductView.jsp";
		}
		
		return modelAndView;
	}
	
	
	@RequestMapping(value = "updateProduct", method = RequestMethod.POST)
	public ModelAndView updateProduct( @ModelAttribute("product") Product product , @RequestParam("file1") MultipartFile file) throws Exception{

		System.out.println("/product/updateProduct : POST");
		//Business Logic
		
		
		if(!file.getOriginalFilename().isEmpty()) {
			file.transferTo(new File("C:\\Users\\admin\\git\\10AJAXRefactoring\\10.Model2MVCShop(AJAX)Refactoring\\WebContent\\images\\uploadFiles",file.getOriginalFilename()));
			product.setFileName(file.getOriginalFilename());
		}else {
			
		}
		productService.updateProduct(product);;
		int prodNo = product.getProdNo();
		product = productService.getProduct(prodNo);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product", product);
		modelAndView.setViewName("forward:/product/readProduct.jsp");
		
		
		return modelAndView;
		
	}
	
	@RequestMapping( value = "listProduct" ) //GET�̶� ����Ʈ ��� �Ѵ� �޾ƾߵ� 
	public ModelAndView listProduct( @RequestParam("menu") String menu ,@ModelAttribute("search") Search search , HttpSession session) throws Exception{
		
		System.out.println("/product/listProduc : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		System.out.println("��ġŰ����� ?"+search.getSearchKeyword());
		System.out.println("����:"+search.getEndRowNum()+"��ŸƮ:"+search.getStartRowNum());
		// Business logic ����
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		session.setAttribute("menu", menu);
		
		ModelAndView modelAndView = new ModelAndView();
		// Model �� View ����
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		modelAndView.setViewName("forward:/product/listProduct.jsp");
		//return "forward:/product/listProduct.jsp";
		
		return modelAndView;
	}
}