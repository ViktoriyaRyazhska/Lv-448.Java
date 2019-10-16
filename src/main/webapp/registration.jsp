<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Page</title>
</head>
<body>
<H2>Registration</H2>
<form action="registration" method="post" name="registrform">
    <table>
        <tr>
            <td>First Name:</td>
            <td><input type="text" name="firstname" /></td>
        </tr>
            <td>Last Name:</td>
            <td><input type="text" name="lastname" /></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><input type="text" name="email" /></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" /></td>
        </tr>
        <tr>
            <td>Date Birthday YYYY/MM/DD:</td>
            <td><input type="text" name="date" /></td>
        </tr>
        <tr>
            <td>Phone number:</td>
            <td><input type="text" name="phone" /></td>
        </tr>
        <tr>
            <td>Do you have VISA:</td>
            <td><select name="nubexSelect" size="2">
                <option>YES</option>
                <option>NO</option>
            </select></td>
        </tr>
        <tr>
            <td>Number Visa:</td>
            <td><input type="text" name="numbervisa" /></td>
        </tr>
            <td>When Start:</td>
            <td><input type="text" name="start" /></td>
        </tr>
        <tr>
            <td>When end :</td>
            <td><input type="text" name="end" /></td>
        </tr>
        <tr>
            <td>Country:</td>
            <td><input type="text" name="country" /></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="registration" /></td>
        </tr>
    </table>
</form>
</body>
</html>