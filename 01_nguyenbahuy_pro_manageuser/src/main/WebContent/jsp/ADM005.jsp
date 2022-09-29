
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
     <%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
     <%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="./css/style.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="./js/eventJs.js"></script>
<title>ユーザ管理</title>
<style type="">
	td.multiline {
	max-width: 550px;
  	word-wrap:break-word;
	}
}
</style>
</head>
<body>
	<!-- Begin vung header -->	
		<jsp:include page="header.jsp" ></jsp:include>
<!-- End vung header -->	

<!-- Begin vung input-->	
	<form action="deleteUser.do" method="post" name="inputform" id="inputform">	
	<input type = "hidden" name = "userId" value = "${userInfor.userId }">
	<table  class="tbl_input" border="0" width="75%"  cellpadding="0" cellspacing="0" >
		
		<tr>
			<th align="left">
				<div style="padding-left:100px;">
					情報確認					
				</div>
				<div style="padding-left:100px;">&nbsp;</div>
			</th>			
		</tr>				
		<tr>
			<td align="left" >
				<div style="padding-left:100px;">
					<table border="1" width="70%" class="tbl_input" cellpadding="4" cellspacing="0" >
					<tr>
						<td class="lbl_left">アカウント名:</td>
						<td align="left" class="multiline"><c:out value="${userInfor.loginName}"></c:out></td>
					</tr>
					<tr>
						<td class="lbl_left">グループ:</td>
						<td align="left" class="multiline"><c:out value="${userInfor.groupName}"></c:out> </td>
					</tr>
					<tr>
						<td class="lbl_left">氏名:</td>
						<td align="left" class="multiline"><c:out value="${userInfor.fullName}"></c:out></td>
					</tr>	
					<tr>
						<td class="lbl_left">カタカナ氏名:</td>
						<td align="left" class="multiline"><c:out value="${userInfor.fullNameKana}"></c:out></td>
					</tr>
					<tr>
						<td class="lbl_left">生年月日:</td>
						<td align="left" ><c:out value="${fn:replace(userInfor.birthday, '-', '/')}"></c:out></td>
					</tr>				
					<tr>
						<td class="lbl_left">メールアドレス:</td>
						<td align="left" class="multiline"><c:out value="${userInfor.email}"></c:out></td>
					</tr>
					<tr>
						<td class="lbl_left">電話番号:</td>
						<td align="left"><c:out value="${userInfor.tel}"></c:out></td>
					</tr>	
					<tr>
						<th colspan = "2"><a href = "#" onclick="showHide()" >日本語能力</a></th>
					</tr>
					<tr id="trinhdoJP" class="dis-play-none">
						<td class="lbl_left">資格:</td>
						<td align="left" class="multiline"><c:out value="${userInfor.nameLevel}"></c:out></td>
					</tr>
					<tr id="selectboxStartDate" class="dis-play-none">
						<td class="lbl_left">資格交付日:</td>
						<td align="left"><c:out value="${fn:replace(userInfor.startDate, '-', '/')}"></c:out></td>
					</tr>
					<tr id="selectboxEndDate" class="dis-play-none">
						<td class="lbl_left">失効日:</td>
						<td align="left"><c:out value="${fn:replace(userInfor.endDate, '-', '/')}"></c:out></td>
					</tr>	
					<tr id="total" class="dis-play-none">
						<td class="lbl_left">点数:</td>
								<c:choose>
								    <c:when test="${userInfor.total == 0}">
								        <td align="left"></td>	
								    </c:when>    
								    <c:otherwise>
								       <td align="left"><c:out value="${userInfor.total}"></c:out></td>
								    </c:otherwise>
								</c:choose>
					</tr>													
				</table>
				</div>				
			</td>		
		</tr>
	</table>
	<div style="padding-left:100px;">&nbsp;</div>
		<!-- Begin vung button -->
	<div style="padding-left:100px;"></div>
	<table border="0" cellpadding="4" cellspacing="0" width="300px">	
		<tr>
			<th width="200px" align="center">&nbsp;</th>
			<td>
				<a href="editUserInput.do?type=edit&userId=${userInfor.userId}"><input class="btn" type="button" value="編集" /></a>			
			</td>
			<td>	 
				<button class="btn" type="button" onclick="displayAlert()">削除</button>
			</td>
			<td>
				<a href="listUser.do?type=back_ADM002"><input class="btn" type="button" value="戻る" /></a>						
			</td>
		</tr>		
	</table>
	<!-- End vung button -->	
</form>
<fmt:setBundle basename="properties.Msg" />

<script>
function displayAlert(){
    if(confirm("<fmt:message key="MSG004" />")){
    	document.getElementById("inputform").method = "post";
        document.getElementById("inputform").action = "deleteUser.do";
        document.getElementById("inputform").submit();
    }else{
        return;
    }
}

function showHide(){
    document.getElementById("trinhdoJP").classList.toggle("dis-play-none")
    document.getElementById("selectboxStartDate").classList.toggle("dis-play-none")
    document.getElementById("selectboxEndDate").classList.toggle("dis-play-none")
    document.getElementById("total").classList.toggle("dis-play-none")
}

</script>
<!-- End vung input -->

<!-- Begin vung footer -->
	<jsp:include page="footer.jsp"></jsp:include>
<!-- End vung footer -->
</body>

</html>