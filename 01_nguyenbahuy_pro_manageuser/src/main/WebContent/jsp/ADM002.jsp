
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <%@ page session="true" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="./css/style.css" rel="stylesheet" type="text/css" />

<title>ユーザ管理</title>
</head>
<style>
.sizeSelectBox{
	max-width: 75px;
}
</style>
<body>
	<!-- Begin vung header -->	
	<jsp:include page="header.jsp"></jsp:include>
	<!-- End vung header -->	

<!-- Begin vung dieu kien tim kiem -->	
<form action="listUser.do?type=search" method="get" name="mainform">
	<input type = "hidden" name = "type" value = "search">
	<table  class="tbl_input" border="0" width="90%"  cellpadding="0" cellspacing="0">		
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				会員名称で会員を検索します。検索条件無しの場合は全て表示されます。 
			</td>
		</tr>
		<tr>
			<td width="100%">
				<table class="tbl_input" cellpadding="4" cellspacing="0" >
					<tr>
						<td class="lbl_left">氏名:</td>
						<td align="left">
						<input class="txBox" type="text" name="full_name" 
						 value="${full_name.length() > 20 ? full_name.substring(0,17).concat('...') : full_name}"
							size="20" onfocus="this.style.borderColor='#0066ff';
							onblur="this.style.borderColor='#aaaaaa';" />
						</td>
						<td></td>
					</tr>
					<tr>
						<td class="lbl_left">グループ:</td>
						<td align="left" width="80px">
							
						<select name="groupId" class = "sizeSelectBox" >
						
								<option value = "0" >全て</option>
			   		 			<c:forEach var="mstGroup" items="${listMstGroup}">
                    			<option value="${mstGroup.ID}" ${mstGroup.ID == groupId ? 'selected = "selected"' : ''}>	
                    			<c:choose>
								    <c:when test="${mstGroup.groupName.length() > 20}">
								        ${mstGroup.groupName.substring(0,17).concat("...")}
								    </c:when>    
								    <c:otherwise>
								        ${mstGroup.groupName}
								    </c:otherwise>
								</c:choose>
                    			
                    			</option>
                    			</c:forEach>
						</select>					
						</td>
						<td align="left">
							<input class="btn" type="submit" value="検索" />
							<a href="addUserInput.do?type=add"><input class="btn" type="button" value="新規追加" /></a>
							
						</td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
	<!-- End vung dieu kien tim kiem -->
	<c:choose>
    <c:when test="${listUser.size() > 0}">
</form>
	<!-- Begin vung hien thi danh sach user -->
	<table class="tbl_list" border="1" cellpadding="4" cellspacing="0" width="80%">
		
		<tr class="tr2">
			<th align="center" width="20px">
				ID
			</th>
			<th align="left">
				氏名 <c:choose>
				    <c:when test="${sortByFullName == 'ASC' }">
				         <a href = "listUser.do?type=sort&sortType=full_name&sortByFullName=DESC&full_name=${full_name}&groupId=${groupId}"> ▲▽</a>
				        <br />
				    </c:when>
				    <c:when test="${sortByFullName == 'DESC' }">
				     	 <a href = "listUser.do?type=sort&sortType=full_name&sortByFullName=ASC&full_name=${full_name}&groupId=${groupId}"> △▼</a>
				        <br />
				    </c:when>  
					</c:choose>
			
			</th>
			<th align="left">
				生年月日
			</th>
			<th align="left">
				グループ
			</th>
			<th align="left">
				メールアドレス
			</th>
			<th align="left" width="70px">
				電話番号
			</th>
			
			<th align="left">
				日本語能力  <c:choose>
				    <c:when test="${sortByCodeLevel == 'ASC' }">
				         <a href = "listUser.do?type=sort&sortType=code_level&sortByCodeLevel=DESC&full_name=${full_name}&groupId=${groupId}"> ▲▽</a>
				        <br />
				    </c:when>    
				    <c:when test="${sortByCodeLevel == 'DESC' }">
				     	 <a href = "listUser.do?type=sort&sortType=code_level&sortByCodeLevel=ASC&full_name=${full_name}&groupId=${groupId}"> △▼</a>
				        <br />
				    </c:when>  
					</c:choose>
			</th>
			<th align="left">
				失効日 <c:choose>
				    <c:when test="${sortByEndDate == 'ASC' }">
				         <a href = "listUser.do?type=sort&sortType=end_date&sortByEndDate=DESC&full_name=${full_name}&groupId=${groupId}"> ▲▽</a>
				        <br />
				    </c:when>    
				    <c:when test="${sortByEndDate == 'DESC' }">
				     	 <a href = "listUser.do?type=sort&sortType=end_date&sortByEndDate=ASC&full_name=${full_name}&groupId=${groupId}"> △▼</a>
				        <br />
				    </c:when>  
					</c:choose>
			</th>
			<th align="left">
				点数
			</th>
		</tr>
		
	
      <c:forEach var="userInfor" items= "${listUser}">
    <tr>
        <td align="right">
            <a href="DetailUser.do?userId=${userInfor.userId}"><c:out value="${userInfor.userId}" /></a>
        </td>
        <td >
        <c:choose>
		<c:when test="${userInfor.fullName.length() > 20}">
		        ${userInfor.fullName.substring(0,17).concat("...")}
		    </c:when>    
		    <c:otherwise>
		        <c:out value=" ${userInfor.fullName}" />
		    </c:otherwise>
		</c:choose>
            
        </td>
        <td align="center">
            <c:out value="${fn:replace(userInfor.birthday,'-','/')}"/>
        </td>
        <td>
         <c:choose>
		<c:when test="${userInfor.groupName.length() > 20}">
		        ${userInfor.groupName.substring(0,17).concat("...")}
		    </c:when>    
		    <c:otherwise>
		        <c:out value="${userInfor.groupName}" />
		    </c:otherwise>
		</c:choose>
            
        </td>
        <td>
            <c:choose>
		<c:when test="${userInfor.email.length() > 20}">
		        ${userInfor.email.substring(0,17).concat("...")}
		    </c:when>    
		    <c:otherwise>
		        <c:out value="${userInfor.email}" />
		    </c:otherwise>
		</c:choose>
        </td>
        <td>
            <c:out value="${userInfor.tel}" />
        </td>
        <td>
            <c:out value="${userInfor.nameLevel}" />
        </td>
        <td align="center">
            <c:out value="${fn:replace(userInfor.endDate,'-','/')}" />
        </td>
              <td align="right">
          <c:choose>
			 <c:when test="${userInfor.total == '0' }">
				<c:out value=""></c:out>
			</c:when>    
			<c:otherwise>
				 <c:out value="${userInfor.total }"></c:out>
			</c:otherwise>
		</c:choose>
        	</td>
    </tr>
    </c:forEach>
    </c:when>    
    <c:otherwise>
     	<h2 style="color:red; font-size:30px;">${Msg005}</h2>
    </c:otherwise>
</c:choose>
	
		
	</table>
	<!-- End vung hien thi danh sach user -->

	<!-- Begin vung paging -->
	<table>
		<tr>
			<td class = "lbl_paging">
			
			<c:set var = "size" value = "${listPaging.size()}"/>
			
			<c:if test="${currentGroup > 1 }">
				<a href = "listUser.do?type=paging&currentPage=${listPaging[0] - 3}&full_name=${full_name}&groupId=${groupId}"> &lt;&lt; </a>&nbsp;
			</c:if>
			
			<c:forEach var="page" items= "${listPaging}">
				<a href="listUser.do?type=paging&currentPage=${page}&full_name=${full_name}&groupId=${groupId}"><c:out value="${page}" /></a>
			</c:forEach>
			
			<c:if test="${totalPage > listPaging[size - 1] }">
				<a href = "listUser.do?type=paging&currentPage=${listPaging[size - 1] + 1}&full_name=${full_name}&groupId=${groupId}"> &gt;&gt; </a>
			</c:if>
			 
			</td>
		</tr>
	</table>
	<!-- End vung paging -->

	<!-- Begin vung footer -->
	<jsp:include page="footer.jsp" ></jsp:include>
	<!-- End vung footer -->

</body>

</html>