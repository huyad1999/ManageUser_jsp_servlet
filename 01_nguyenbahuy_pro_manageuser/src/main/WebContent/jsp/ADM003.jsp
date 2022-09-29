
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="./css/style.css" rel="stylesheet" type="text/css" />
<script type ="text/javascript" src = "./js/eventJs.js"></script>
<title>ユーザ管理</title>
<style >
.select_box {
	max-width: 200px;
}
</style>
	
</head>
<body>
	<!-- Begin vung header -->	
		<jsp:include page="header.jsp" ></jsp:include>

<!-- End vung header -->	

<!-- Begin vung input-->	
	
	<form action="${userInfor.userId > 0?'editUserValidate.do':'AddUserValidate.do' }" method="post" name="inputform" >	
		<input type="hidden" name="checkADM003" value="check" />
		<input type="hidden" name="type" value="validate" />
		<input type="hidden" name="userId" value="${userInfor.userId }" />
	<table  class="tbl_input"   border="0" width="75%"  cellpadding="0" cellspacing="0" />

		<tr>
			<th align="left">
				<div style="padding-left:100px;">
					会員情報編集
				</div>
			</th>			
		</tr>		
		<tr>
			<td class="errMsg">
				
					<c:forEach var = "error" items = "${listError }">
						<div style="padding-left:120px;color: red ;" >
						${error}
						</div>
					</c:forEach>
				
			</td>
		</tr>
		<tr>
			<td align="left" >
				<div style="padding-left:100px;">
					<table border="0" width="100%" class="tbl_input" cellpadding="4" cellspacing="0" >				
					
					<tr>
						<td class="lbl_left"><font color = "red">*</font> アカウント名:</td>
						<td align="left">
							<c:if test="${userInfor.userId > 0 }">
								<input class="txBox" type="text" name="loginName" value="<c:out value="${userInfor.loginName}"></c:out>"
								size="15" onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" readonly/>
							</c:if>
							<c:if test="${userInfor.userId == 0 }">
								<input class="txBox" type="text" name="loginName" value="<c:out value="${userInfor.loginName}"></c:out>"
								size="15" onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" />
							</c:if>	
							
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font> グループ:</td>
						<td align="left">						
							<select class="select_box" name="groupId">
								<option value="0">選択してください</option>								
								<c:forEach var="mstGroup" items="${listMstGroup}">
                    			<option value="${mstGroup.ID}" ${mstGroup.ID == userInfor.groupId ? 'selected = "selected"' : ''}>  ${mstGroup.groupName}</option>
                    			
                    			</c:forEach>
							</select>							
							
							<span>&nbsp;&nbsp;&nbsp;</span>
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font> 氏名:</td>
						<td align="left">
						<input class="txBox" type="text" name="fullName" value="<c:out value="${userInfor.fullName }"></c:out>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>
					<tr>
						<td class="lbl_left ">カタカナ氏名:</td>
						<td align="left">
						<input class="txBox" type="text" name="fullNameKana" value="<c:out value="${userInfor.fullNameKana }"></c:out>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>	
					<tr>
						<td class="lbl_left"><font color = "red">*</font> 生年月日:</td>
						<td align="left">
						<select name="yearBirthday" >
								<c:forEach var="yearBirthday" items="${listYear}">
                    					<option value="${yearBirthday}" ${yearBirthday == userInfor.yearBirthday ? 'selected = "selected"' : ''}>  ${yearBirthday}</option>
                    			</c:forEach>
						</select>年
							<select name="monthBirthday">
									<c:forEach var="monthBirthday" items="${listMonth}">
                    					<option value="${monthBirthday}" ${monthBirthday == userInfor.monthBirthday ? 'selected = "selected"' : ''}>  ${monthBirthday}</option>
                    				</c:forEach>
							</select>月
							<select name="dayBirthday">
									<c:forEach var="dayBirthday" items="${listDay}">
                    					<option value="${dayBirthday}" ${dayBirthday == userInfor.dayBirthday ? 'selected = "selected"' : ''}> ${dayBirthday}</option>
                    				</c:forEach>
							</select>日							
						</td>
					</tr>				
					<tr>
						<td class="lbl_left"><font color = "red">*</font> メールアドレス:</td>
						<td align="left">
							<input class="txBox" type="text" name="email" value="<c:out value="${userInfor.email }"></c:out>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font>電話番号:</td>
						<td align="left">
						<input class="txBox" type="text" name="tel"  value="<c:out value="${userInfor.tel }"></c:out>"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />						
						</td>
					</tr>
					<tr>
						<td class="lbl_left" <c:if test="${userInfor.userId != 0 }">hidden</c:if>><font color = "red">*</font> パスワード:</td>
						<td align="left">
								<input <c:if test="${userInfor.userId != 0 }">hidden</c:if>
								class="txBox" type="password" name="password" value="<c:out value="${userInfor.password}"></c:out>"
								size="30" onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';"/>							
					</tr>
					<tr>
						<td class="lbl_left" <c:if test="${userInfor.userId != 0 }">hidden</c:if>>パスワード（確認）:</td>
						<td align="left">
								<input <c:if test="${userInfor.userId != 0 }">hidden</c:if>
								class="txBox" type="password" name=passwordComfirm value="<c:out value="${userInfor.passwordComfirm}"></c:out>"
								size="30" onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" />		
					</tr>
					<tr>
						<th align="left" colspan = "2" >							
								<a href = "#" onclick="showHide()">日本語能力</a>
						</th>			
					</tr>
					<tr id="trinhdoJP" class="dis-play-none">
						<td class="lbl_left">資格:</td>
						<td align="left">
							<select class="select_box" name="codeLevel">
								<option value="0">選択してください</option>								
								<c:forEach var="mstJapan" items="${listMstJapan}">
                    			<option value="${mstJapan.codeLevel}" ${mstJapan.codeLevel == userInfor.codeLevel ? 'selected = "selected"' : ''} >${mstJapan.nameLevel}</option>
                    			</c:forEach>
							</select>							
						</td>
					</tr>
					<tr id="selectboxStartDate" class="dis-play-none">
						<td class="lbl_left">資格交付日: </td>
						<td align="left">
								<select name="yearStart"> 
									<c:forEach var="yearStart" items="${listYear}">
                    					<option value="${yearStart}" ${yearStart == userInfor.yearStart ? 'selected = "selected"' : ''}> ${yearStart}</option>
                    				</c:forEach>
								</select>年
								<select name="monthStart">
									<c:forEach var="monthStart" items="${listMonth}">
                    				<option value="${monthStart}" ${monthStart == userInfor.monthStart ? 'selected = "selected"' : ''}>  ${monthStart}</option>
                    				</c:forEach>
								</select>月
								<select name="dayStart">
									<c:forEach var="dayStart" items="${listDay}">
                    				<option value="${dayStart}" ${dayStart == userInfor.dayStart ? 'selected = "selected"' : ''}>  ${dayStart}</option>
                    				</c:forEach>
								</select>日							
						</td>
					</tr >
					<tr id="selectboxEndDate" class="dis-play-none" >
						<td class="lbl_left">失効日: </td>
						<td align="left">
							<select name="yearEnd">
									<c:forEach var="yearEnd" items="${listYearEndDate}">
                    				<option value="${yearEnd}" ${yearEnd == userInfor.yearEnd ? 'selected = "selected"' : ''}>  ${yearEnd}</option>
                    				</c:forEach>
								</select>年
								
								<select name="monthEnd">
									<c:forEach var="monthEnd" items="${listMonth}">${option } 
                    				<option value="${monthEnd}" ${monthEnd == userInfor.monthEnd ? 'selected = "selected"' : ''}>  ${monthEnd}</option>
                    				</c:forEach>
								</select>月
								<select name="dayEnd">
									<c:forEach var="dayEnd" items="${listDay}">
                    				<option value="${dayEnd}" ${dayEnd == userInfor.dayEnd ? 'selected = "selected"' : ''}>  ${dayEnd}</option>
                    				</c:forEach>
								</select>日							
						</td>
					</tr>
					<tr id="total" class="dis-play-none" >
						<td class="lbl_left">点数: </td>
						<td align="left">
							
								<c:choose>
								    <c:when test="${userInfor.total == '0' }">
								        <input class="txBox" type="text" name="total" value=""
								size="5" onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" maxlength="10"/>	
								    </c:when>    
								    <c:otherwise>
								       <input class="txBox" type="text" name="total" value="<c:out value="${userInfor.total }"></c:out>"
								size="5" onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" maxlength="10"/>
								    </c:otherwise>
								</c:choose>

						</td>
					</tr>									
				</table>
				</div>				
			</td>		
		</tr>
	</table>
	<div style="padding-left:100px;">&nbsp;</div>
		<!-- Begin vung button -->
	<div style="padding-left:45px;"></div>
	<table border="0" cellpadding="4" cellspacing="0" width="300px">	
		<tr>
			<th width="200px" align="center">&nbsp;</th>
				<td>
					<input class="btn" type="submit" value="確認" />					
				</td>	
				<td>
					<a href="listUser.do?type=back_ADM002"><input class="btn" type="button" value="戻る" /></a>						
				</td>
		</tr>		
	</table>
	<!-- End vung button -->	
</form>
<script>
function showHide(){
    document.getElementById("trinhdoJP").classList.toggle("dis-play-none")
    document.getElementById("selectboxStartDate").classList.toggle("dis-play-none")
    document.getElementById("selectboxEndDate").classList.toggle("dis-play-none")
    document.getElementById("total").classList.toggle("dis-play-none")
}
</script>
<!-- End vung input -->

<!-- Begin vung footer -->
	<jsp:include page="footer.jsp" ></jsp:include>
<!-- End vung footer -->
</body>

</html>