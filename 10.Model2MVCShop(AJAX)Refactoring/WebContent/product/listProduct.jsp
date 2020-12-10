<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>

<title>��ǰ �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>

<script type="text/javascript">
//�˻� / page �ΰ��� ��� ��� Form ������ ���� JavaScrpt �̿�  
function fncGetProductList(currentPage) {
	//document.getElementById("currentPage").value = currentPage;
	$("input[name='currentPage']").val(currentPage);
   	//document.detailForm.submit();		
   	$('form').attr('method',"POST").attr("action","/product/listProduct").submit();
}

$(function() {
	$("td.ct_btn01:contains('�˻�')").on("click", function() {
		fncGetProductList(1);
	});
	$("#searchKeyword").on("keydown", function(event) {
		//alert(event.keyCode);
		if(event.keyCode =='13'){
			fncGetProductList(1);
		}
		
	})
	
	$( ".ct_list_pop td:nth-child(3) img" ).on("mouseover" , function() {
		/* alert("����")
		alert($(this).attr("id")) */
		var prodNo = $(this).attr("id");
		$.ajax( 
				{
					url : "/product/json/getProduct/"+prodNo ,
					method : "GET" ,
					dataType : "json" ,
					headers : {
						"Accept" : "application/json",
						"Content-Type" : "application/json"
					},
					success : function(JSONData , status) {

						//Debug...
						//alert(status);
						//Debug...
						//alert("JSONData : \n"+JSONData);
						//alert(JSONData.userId)
						//var link = "<a href=\"/user/getUser?userId="+userId+"\"\">����</a>";
						var displayValue = "<h3>"
													+"��ǰ�� : "+JSONData.prodName+"<br/>"
													+"��	 �� : "+JSONData.price+"<br/>"
													+"�������� : "+JSONData.manuDate+"<br/>"
													+"</h3>";
						//Debug...									\
						//alert(displayValue);<div id=\""+"update\""+">����</div>"+
						$("h3").remove();
						$( "#"+prodNo+"view" ).html(displayValue);
					}
			});
		/* self.location ="/product/getProduct?prodNo="+prodNo; */
}); 

	$( "#����ϱ�" ).css('color','red').on("click" , function() {
		self.location ="/purchase/updateTranCode?tranCode="+$(this).find("input[name='proTranCode']").val()+"&prodNo="+$(this).find("input[name='prodNo']").val()
});
	
});

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">
														
<form name="detailForm" >

<input type="hidden"  name="menu" value=${menu} />

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
				
				<c:if test="${menu eq 'manage' }">
			<%-- 	<%if(menu.equals("manage")){ %>--%>
					<td width="93%" class="ct_ttl01">��ǰ ����</td>
				</c:if>
			
			
		<%--<%}else{ %>--%>
		<c:if test="${ !(menu eq 'manage') }">
			<td width="93%" class="ct_ttl01">��ǰ �����ȸ</td>
	</c:if>
				</tr>
			</table>
		</td>
		
		<%--<%} %>--%>
		
		
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
	
		<td align="right">
		<select name="searchCondition" class="ct_input_g" style="width:80px">
			<%-- 	<option value="0" <%= (searchCondition.equals("0") ? "selected" : "")%>>��ǰ��ȣ</option>
					<option value="1" <%= (searchCondition.equals("1") ? "selected" : "")%>>��ǰ��</option>--%>
					<option value="0" ${! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>��ǰ��ȣ</option>
					<option value="1" ${search.searchCondition==1 ? "selected" : "" }>��ǰ��</option>
					<option value="2" ${search.searchCondition==2 ? "selected" : "" }>�������ݼ�</option>
					<option value="3" ${search.searchCondition==3 ? "selected" : "" }>�������ݼ�</option>
			</select>
			
			<input 	type="text" name="searchKeyword"  id="searchKeyword" value="${! empty search.searchKeyword ? search.searchKeyword : ""}" 
							class="ct_input_g" style="width:200px; height:19px"  >
							</td>
			<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23"><img src="/images/ct_btnbg01.gif" width="17" height="23"></td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<!--  <button id="search" onclick="javascript:fncGetProductList('1');">�˻�</button>-->
						�˻�
					</td>
							
							
							
							
							
							
							
						
		
	
					
						
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >��ü ${resultPage.totalCount } �Ǽ�, ����  ${resultPage.currentPage } ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" >No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" >��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" >����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" >�����</td>	
		<td class="ct_list_b" >�������</td>
		<td class="ct_line02"></td>
		
			
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	 
	<%--<% 	
		int no=list.size();
		System.out.println(no);
		for(int i=0; i<list.size(); i++) {
			ProductVO vo = (ProductVO)list.get(i);
	--%>
	
	<c:set var="i" value="0" />
	<c:forEach var="product" items="${list}">
	<c:set var="i" value="${i+1 }"/>
	<tr class="ct_list_pop">
			<td align="center">${ i }
		<td></td>
		<td align="left">
			<!--  <a href="/product/getProduct?prodNo=${product.prodNo }&menu=${menu}">${product.prodName}</a>-->
		<a href="/product/getProduct?prodNo=${product.prodNo }&menu=${menu}">
		<c:if test="${!empty product.fileName}">
		<img id="${product.prodNo}"  src="/images/uploadFiles/${product.fileName}" width="100" height="100"  />
		</c:if>
		<c:if test="${ empty product.fileName}">
		<img id="${product.prodNo}" src="http://placehold.it/100X100" />
		</c:if>
		</a>
		${product.prodName}
		
		</td>
		<td></td>
		<td align="left">${product.price}</td>
		<td></td>
		<td align="left">${product.regDate}
		</td>
		
		<c:if test="${! (menu eq 'manage') }">
		<c:if test="${product.proTranCode==null}">
		<%--<%if(vo.getProTranCode()==null){ --%>		
		<td align="left">�Ǹ���
		</c:if>
		<c:if test="${!(product.proTranCode==null)}">
		<td align="left">������.
		</c:if>
		</c:if>
		
		<c:if test="${ menu eq 'manage' }">
		<c:if test="${product.proTranCode==null}">
		<td align="left">�Ǹ���
		</c:if>
		<c:if test="${product.proTranCode=='0  '}">
		<td align="left"> 
		<!--  <a href="/purchase/updateTranCode?tranCode=${product.proTranCode}&prodNo=${product.prodNo}">����ϱ�</a>-->
		<div align="letf" >���ſϷ�</div> 
		<div align="letf" id="����ϱ�"><input type="hidden" name="prodNo" value="${product.prodNo }">
		<input type="hidden" name="proTranCode" value="${product.proTranCode }">
		(����ϱ�)</div> 
		</c:if>
		<c:if test="${product.proTranCode=='1  '}">
			<td align="left">�����
		</td>	
		</c:if>
		<c:if test="${product.proTranCode=='2  '}">
			<td align="left">��ۿϷ�
		</td>	
		<td></td>
		</c:if>
		</c:if>
		
		<%--<%}else{ --%>
		</td>	
		<%--<%} --%>
	</tr>
	<tr>
	<!-- 	<td colspan="11" bgcolor="D6D7D6" height="1"></td> -->
	<td id="${product.prodNo}view" colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</tr>
	
	<%--<% } --%>
	</c:forEach>
</table>

	
	

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
	<td align="center">
	<input type="hidden" id="currentPage" name="currentPage" value=""/>
	
	<!-- jsp include ���� EL�� import�� ��� �ؼ� pageNavigator�� �������̽�ȭ ��Ŵ .. ���� � page�� ������ value(fnc)�� �ٲ��ָ� �� ��밡�� -->
	<c:set var="fnc" value="fncGetProductList" scope="request" />
	
	<c:import var="pageNavi" url="/common/pageNavigator.jsp" scope="request"/>
	${pageNavi}
	
	
		
    	</td>
	</tr>
</table>
<!--  ������ Navigator �� -->
</form>
</div>

</body>
</html>
