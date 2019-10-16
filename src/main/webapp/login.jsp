<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="./css/registration.css">
    <title>Login Page</title>
</head>
<body>
<div class="width30">
    <form action="login" method="post">

        <%--        <tr>--%>
        <%--            <td>Email:</td>--%>
        <%--            <td><input type="email" name="email" /></td>--%>
        <%--        <tr>--%>
        <%--            <td>Password:</td>--%>
        <%--            <td><input type="password" name="password" /></td>--%>
        <%--        <tr>--%>
        <%--            <td></td>--%>
        <%--            <td><input type="submit" value="login" /></td>--%>
        <%--        </tr>--%>
            <div class="form-line">
                <label for="email"><b>Email</b></label>
                <input type="text" placeholder="Enter Email" name="email" required>
            </div>

            <div class="form-line">
                <label for="psw"><b>Password</b></label>
                <input type="password" placeholder="Enter Password" name="password" required>
            </div>
        <button type="submit" class="registerbtn">Register</button>


    </form>
</div>
</body>
</html>