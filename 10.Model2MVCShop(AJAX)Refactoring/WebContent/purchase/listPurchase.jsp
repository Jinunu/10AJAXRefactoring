
<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>���� �����ȸ</title>
<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">

function fncGetPurchaseList(currentPage) {
	
	//document.getElementById("currentPage").value = currentPage;
	$("#currentPage").val(currentPage)
	$("form").attr("method","POST").attr("action","/purchase/listPurchase").submit();
   	//document.detailForm.submit();		
}

$(function() {
	$(".ct_list_pop td:nth-child(1)").on("click", function() {
		//alert($(this).find("input[name='tranNo']").val())
		self.location = "/purchase/getPurchase?tranNo="+$(this).find("input[name='tranNo']").val();
	});
	
	$(".ct_list_pop td:nth-child(3)").on("click", function() {
		//alert($(this).find("input[name='buyerId']").val())
		self.location = "/user/getUser?userId="+$(this).find("input[name='buyerId']").val();
	});
	
	$("#���ǵ���").css('color','blue').on("click", function() {
		alert($(this).find("input[name='tranCode']").val()+":"+$(this).find("input[name='prodNo']").val())
		self.location = "/purchase/updateTranCode?tranCode="+$(this).find("input[name='tranCode']").val()+"&prodNo="+$(this).find("input[name='prodNo']").val();
	});
	
});
</script>
</head>
<body bgcolor="#ffffff" text="#000000">
<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" >

<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="93%" class="ct_ttl01">���� �����ȸ</td>
</tr>
</table>
</td>
<td width="12" height="37"><img src="/images/ct_ttl_img03.gif" width="12" height="37"></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
<tr>
<td colspan="11">��ü ${resultPage.totalCount } �Ǽ�, ����  ${resultPage.currentPage } ������</td>
</tr>
<tr>
<td class="ct_list_b" width="100">No</td>
<td class="ct_line02"></td>
<td class="ct_list_b" width="150">ȸ��ID</td>
<td class="ct_line02"></td>
<td class="ct_list_b" width="150">ȸ����</td>
<td class="ct_line02"></td>
<td class="ct_list_b">��ȭ��ȣ</td>
<td class="ct_line02"></td>
<td class="ct_list_b">�����Ȳ</td>
<td class="ct_line02"></td>
<td class="ct_list_b">��������</td>
</tr>
<tr>
<td colspan="11" bgcolor="808285" height="1"></td>
</tr>
<%-- 
	<% 	
		int no=list.size();
		System.out.println(no);
		for(int i=0; i<list.size(); i++) {
			PurchaseVO vo = (PurchaseVO)list.get(i);
	%>
	--%>
<c:set var="i" value="0" />
<c:forEach var="purchase" items="${list}">
<c:set var="i" value="${i+1 }"/>
<tr class="ct_list_pop">
<td align="center" >
${i }
<%-- <a href="/purchase/getPurchase?tranNo=${purchase.tranNo}">${i }</a> --%>
<input type="hidden" name="tranNo" value="${purchase.tranNo}">
</td>
<td></td>
<td align="left">
${purchase.buyer.userId}
<!-- <a href="/user/getUser?userId=${purchase.buyer.userId}">${purchase.buyer.userId}</a> -->
<input type="hidden" name="buyerId" value="${purchase.buyer.userId}">
</td>
<td></td>
<td align="left">${user.userName}</td>
<td></td>
<td align="left">${purchase.receiverPhone}</td>
<td></td>
<c:choose>
<c:when test="${ !empty purchase.tranCode && purchase.tranCode =='0  ' }">
<td align="left">���� ���ſϷ� ���� �Դϴ�.</td>
<td></td>
</c:when>

<c:when test="${ !empty purchase.tranCode && purchase.tranCode =='1  ' }">
<td align="left">�������� �����Դϴ�. </td>
<td></td>
</c:when>

<c:when test="${ !empty purchase.tranCode && purchase.tranCode == '2  ' }">
<td align="left">�����ۿϷ���� �Դϴ�.</td>
<td></td>
</c:when>

</c:choose>

<c:if test="${ !empty purchase.tranCode && purchase.tranCode eq '1  ' }">
<td align="left" id="���ǵ���">
<%-- <a href="/purchase/updateTranCode?tranCode=${purchase.tranCode}&prodNo=${purchase.purchaseProd.prodNo}">���ǵ���</a> --%>
<input type="hidden" name="tranCode" value="${purchase.tranCode}">
<input type="hidden" name="prodNo" value="${purchase.purchaseProd.prodNo}">
���ǵ���
</td>
</c:if>

</tr>
<tr>
<td colspan="11" bgcolor="D6D7D6" height="1"></td>
</tr>
</c:forEach>

</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
<tr>
<td align="center">

<input type="hidden" id="currentPage" name="currentPage" value=""/>
	
	<!-- jsp include ���� EL�� import�� ��� �ؼ� pageNavigator�� �������̽�ȭ ��Ŵ .. ���� � page�� ������ value(fnc)�� �ٲ��ָ� �� ��밡�� -->
	<c:set var="fnc" value="fncGetPurchaseList" scope="request" />
	
	<c:import var="pageNavi" url="/common/pageNavigator.jsp" scope="request"/>
	${pageNavi}
</td>
</tr>
</table>
<!-- ������ Navigator �� -->
</form>
</div>
</body>
</html>