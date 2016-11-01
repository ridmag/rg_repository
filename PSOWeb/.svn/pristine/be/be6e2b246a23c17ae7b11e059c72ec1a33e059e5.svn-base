<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%
	session.invalidate();
%>
<html>
<head>
<title>Login</title>
<link rel="stylesheet" type="text/css" href="css/login_styles.css" />
<style type="text/css">
#loginError {
	display: none;
}
</style>
<script src="js/jquery-1.8.2.js" type="text/javascript"></script>
<script type="text/javascript">
	function login() {
		
		if(window.crypto.logout)
			window.crypto.logout();
		// alert('test');

		//var http = new XMLHttpRequest();
		//logout();
		var user = document.getElementById('j_username').value;
		var pass = document.getElementById('j_password').value;
		$.ajax({
			url : 'logout.jsp',
			type : 'POST',
			username : user,
			password : pass,
			error : function() {
				//alert('logged out')
				var user = document.getElementById('j_username').value;
				var pass = document.getElementById('j_password').value;
				//alert(user);
				jQuery.ajax({
					url : 'Home.jsf',
					type : 'POST',
					username : user,
					password : pass,
					success : function() {
							document.location = 'Home.jsf';
					},
					error:function(){
					//	alert('test');
						$('#loginError').css('display','block');

					}
				});
			}
		});
		//alert('done');

		
	}
	function logout() {
		var http = new XMLHttpRequest();
		document.execCommand('ClearAuthenticationCache', false);
		http.open('get', 'logout.jsp', false, '111', '222');
		http.send('');
		if (http.status == 200) {
			alert("logged off");
		} else {
			//$('#loginError').html('test');
			alert("error logoff" + http.status);
		}
	}
</script>
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
							<span id="loginError"> <font color="red"> Your
									login attempt was not successful. Please try again.<br /> </font> </span>

							<form name="f" action="<c:url value='j_spring_security_check'/>"
								onsubmit="return login()" method="POST">
								<table width="100%">
									<tr>
										<td>User Name:</td>
										<td><input id="j_username" type='text' name='j_username'
											value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>' />
										</td>
									</tr>
									<tr>
										<td>Password:</td>
										<td><input id="j_password" type='password'
											name='j_password'></td>
									</tr>
									<tr>
										<td>Remember:</td>
										<td><input type="checkbox" onkeypress=""
											name="_spring_security_remember_me"> Remember my
											password for two weeks</td>
									</tr>
									<tr>
										<td></td>
										<td><input type="button" value="Login"
											class="CommandButton" tabindex="0" onclick="login()"/></td>
									</tr>
								</table>
							</form>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
