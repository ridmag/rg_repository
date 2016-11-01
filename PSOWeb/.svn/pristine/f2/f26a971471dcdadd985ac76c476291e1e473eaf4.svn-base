<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>

<html>
<head>
<title>Login</title>
<%session.invalidate(); %>
<link rel="stylesheet" type="text/css" href="css/login_styles.css" />
<style type="text/css"></style>
</head>

<body onload="document.f.j_username.focus();">
	<table id="wrapper">
		<tr>
			<td align="center" valign="middle">

				<table border="0" cellspacing="0" cellpadding="8" width="400"
					class="Dialog">
					<tr>
						<td class="DialogTitle">CCPSO User Login</td>
					</tr>
					<tr>
						<td><br /> <%-- this form-login-page form is also used as the form-error-page to ask for a login again. --%>
							<c:if test="${not empty param.login_error}">
								<font color="red"> Your login attempt was not successful.
									Please try again.<%-- <br /> Reason: <c:out
										value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.  --%></font>
							</c:if>

							<form name="f" action="<c:url value='j_spring_security_check'/>"
								method="POST">
								<table width="100%">
									<tr>
										<td>User Name:</td>
										<td><input id="j_username" type='text' name='j_username' required="required"
											value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>' />
										</td>
									</tr>
									<tr>
										<td>Password:</td>
										<td><input id="j_password" type='password'
											name='j_password' required="required">
										</td>
									</tr>
									<tr>
										<td>Remember:</td>
										<td><input type="checkbox"
											name="_spring_security_remember_me"> Remember my
											password for two weeks</td>
									</tr>
									<tr>
										<td></td>
										<td><input id="submit" name="submit" type="submit"
											value="Login" class="CommandButton">
										</td>
									</tr>
								</table>
							</form></td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>
