package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

//==> ȸ������ Controller
@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {

	/// Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	// setter Method ���� ����

	public PurchaseController() {
		System.out.println(this.getClass());
	}

	// ==> classpath:config/common.properties , classpath:config/commonservice.xml
	// ���� �Ұ�
	// ==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	// @Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	// @Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	//view�� GET���� ������ �� ���ְ� 
	@RequestMapping(value = "addPurchase" , method = RequestMethod.GET)
	public ModelAndView addPurchaseView(@RequestParam("prodNo") int prodNo) throws Exception{
		
		System.out.println("/purchase/addPurchase : GET");
		
		//businessLogic ����
		Product product = productService.getProduct(prodNo);
		
		//model view ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product", product);
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "addPurchase" , method = RequestMethod.POST)
	public ModelAndView addPurchase(@RequestParam("prodNo") int prodNo, @ModelAttribute("purchase") Purchase purchase,HttpSession session) throws Exception{
		
		System.out.println("/purchase//addPurchase : POST");
		
		//businessLogic ����
		//view���� product�� ���ǿ� ��°� ������ �ƴϸ� prodNo�� �޾Ƽ� product�� ������ ���°� ������ ??
		System.out.println("��ǰ���� :"+purchase);
		Product product = productService.getProduct(prodNo);
		purchase.setBuyer((User)session.getAttribute("user"));
		purchase.setPurchaseProd(product);
		purchase.setTranCode("0");
		purchaseService.addPurchase(purchase);
		//model view ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/purchase/listPurchase");
		System.out.println("/purchase//addPurchase : POST �� ");
		return modelAndView;
	}
	
	@RequestMapping(value = "getPurchase")
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo) throws Exception{
		
		System.out.println("/purchase/getPurchase : GET");
		
		//businessLogic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("�������̸��� ?"+purchase.getReceiverName());
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("forward:/purchase/getPurchaseView.jsp");
		System.out.println("/purchase/getPurchase : GET �� ");
		return modelAndView;
		
	}
	//view = get
	@RequestMapping(value = "updatePurchase", method = RequestMethod.GET)
	public ModelAndView updatePurchase(@RequestParam("tranNo") int tranNo) throws Exception{
		
		System.out.println("/purchase/updatePurchase : GET");

		
		//businessLogic
		ModelAndView modelAndView = new ModelAndView();
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		modelAndView.addObject("purchase", purchase);
		
		modelAndView.setViewName("forward:/purchase/updatePurchaseView.jsp");
		System.out.println("/purchase/updatePurchase : GET �� ");
		return modelAndView;
		
		
	}
	
	@RequestMapping(value = "updatePurchase", method = RequestMethod.POST)
	public ModelAndView updatePurchase(@ModelAttribute("purchase") Purchase purchase ) throws Exception{
		
		System.out.println("/purchase/updatePurchase : POST");

		
		//businessLogic
		ModelAndView modelAndView = new ModelAndView();
		
		purchaseService.updatePurcahse(purchase);
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("forward:/purchase/getPurchase");//why tranNo�� ���̸� numberFromatException�� �߻��ұ� ??
		System.out.println("/purchase/updatePurchase : POST �� ");
		return modelAndView;
		
		
	}

	@RequestMapping(value = "listPurchase")
	public ModelAndView listPurchase(@ModelAttribute("search") Search search, HttpSession session) throws Exception {

		System.out.println("/purchase/listPurchase.do GET / POST����");

		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);

		User buyer = (User) session.getAttribute("user");

		Map<String, Object> map = purchaseService.getPurchaseList(search, buyer.getUserId());
		//List<Object> list = (List<Object>) map.get("list");

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		System.out.println("/purchase/listPurchase POST / GET ��");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "updateTranCode", method = RequestMethod.GET)
	public ModelAndView updateTranCode(@RequestParam("tranCode") String tranCode, 
										@RequestParam("prodNo") int prodNo, HttpSession session) throws Exception{
		
		
		System.out.println("/purchase/updateTranCode GET");
		Purchase purchase = new Purchase();
		Product product = new Product();
		product.setProdNo(prodNo);
		purchase.setTranCode(tranCode);
		purchase.setPurchaseProd(product);
		
		purchaseService.updateTranCode(purchase);
		ModelAndView modelAndView = new ModelAndView();
		
		User user = (User)session.getAttribute("user");
		
		if( user.getRole().trim().equals("user")) {
			System.out.println("������Գ� ?");
			modelAndView.setViewName("forward:/purchase/listPurchase"); 
		}else {
			
			modelAndView.setViewName("forward:/product/listProduct?menu=manage"); 

		}
		
		
		
		
		System.out.println("/purchase/updateTranCode GET ��");
		
		return modelAndView;
		
	}

}