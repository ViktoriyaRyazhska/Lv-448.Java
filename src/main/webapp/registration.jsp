<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="./css/registration.css">
    <title>Registration Page</title>
</head>
<body>
<div class="width30">
<form action="registration" method="post" name="registrform" >
<%--    <table>--%>
<%--        <tr>--%>
<%--            <div class="row"></div>--%>
<%--            <td>First Name:</td>--%>
<%--            <td><input type="text" name="firstname" class="input-group" /></td>--%>
<%--        </tr>--%>
<%--            <td>Last Name:</td>--%>
<%--            <td><input type="text" name="lastname" /></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>Email:</td>--%>
<%--            <td><input type="text" name="email" /></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>Password:</td>--%>
<%--            <td><input type="password" name="password" /></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>Date Birthday YYYY/MM/DD:</td>--%>
<%--            <td><input type="text" name="date" /></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>Phone number:</td>--%>
<%--            <td><input type="text" name="phone" /></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>Do you have VISA:</td>--%>
<%--            <td><select name="nubexSelect" size="2">--%>
<%--                <option>YES</option>--%>
<%--                <option>NO</option>--%>
<%--            </select></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>Number Visa:</td>--%>
<%--            <td><input type="text" name="numbervisa" /></td>--%>
<%--        </tr>--%>
<%--            <td>When Start:</td>--%>
<%--            <td><input type="text" name="start" /></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>When end :</td>--%>
<%--            <td><input type="text" name="end" /></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>Country:</td>--%>
<%--            <td><input type="text" name="country" /></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td></td>--%>
<%--            <td><input type="submit" value="registration" /></td>--%>
<%--        </tr>--%>
<%--    </table>--%>
<%--</form>--%>

    <div class="container">
        <h1>Register</h1>
        <p>Please fill in this form to create an account.</p>
        <hr>
        <div class="form-line">
        <label for="First Name"><b>First Name:</b></label>
        <input type="text" placeholder="Enter First Name:" name="firstname" required>
        </div>
         <div class="form-line">
        <label for="Last Name"><b>Last Name:</b></label>
        <input type="text" placeholder="Enter Last Name:" name="lastname" required>
         </div>
             <div class="form-line">
        <label for="email"><b>Email</b></label>
        <input type="text" placeholder="Enter Email" name="email" required>
             </div>
                 <div class="form-line">
        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" required>
                 </div>
                     <div class="form-line">
        <label for="birthday"><b>Date Birthday YYYY/MM/DD:</b></label>
        <input type="text" placeholder="Date Birthday" name="date" required>
                     </div>
                         <div class="form-line">
        <label for="phone"><b>Phone number:</b></label>
        <input type="text" placeholder="Phone number" name="phone" required>
                         </div>
        <hr>
                                 <div class="form-line">
        <label for="Number Visa"><b>Number Visa: </b></label>
        <input type="text" placeholder="Number Visa" name="numbervisa" required>
                                 </div>
                                     <div class="form-line">
        <label for="When Start"><b>When Start: </b></label>
        <input type="text" placeholder="When Start" name="start" required>
                                     </div>
                                         <div class="form-line">
        <label for="When end"><b>When end: </b></label>
        <input type="text" placeholder="When end" name="end" required>
                                         </div>
    <div class="form-line">
        <label for="Country"><b>Country:</b></label>
        <input type="text" placeholder="Country" name="country" required>
    </div>





        <%--        <tr>--%>
        <%--            <td>Country:</td>--%>
        <%--            <td><input type="text" name="country" /></td>--%>
        <%--        </tr>--%>
        <%--        <tr>--%>
        <%--            <td></td>--%>
        <%--            <td><input type="submit" value="registration" /></td>--%>
        <%--        </tr>--%>

        <p>By creating an account you agree to our <a href="#">Terms & Privacy</a>.</p>
        <button type="submit" class="registerbtn">Register</button>
    </div>

    <div class="container signin">
        <p>Already have an account? <a href="#">Sign in</a>.</p>
    </div>
</form>
    </div>
</body>
</html>

