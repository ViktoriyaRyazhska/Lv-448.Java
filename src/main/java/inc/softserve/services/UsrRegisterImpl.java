package inc.softserve.services;

import inc.softserve.dao.UsrCrudJdbs;
import inc.softserve.entities.Usr;
import inc.softserve.security.JavaNativeSaltGen;
import inc.softserve.security.SaltGen;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UsrRegisterImpl implements UsrRegisterService {

    private final SaltGen saltGen = new JavaNativeSaltGen(); // TODO - impl a singleton container or make static
    private final UsrCrudJdbs usrCrudJdbs;

    public UsrRegisterImpl(UsrCrudJdbs usrCrudJdbs) {
        this.usrCrudJdbs = usrCrudJdbs;
    }

    @Override
    public String register(Usr usr) {
        if (exists(usr)){
            return "An account with such an email already exists!";
        }
        if (! isEmailValid(usr.getEmail())){
            return "Given email is not valid!";
        }
        if (! isPhoneNumberValid(usr.getPhoneNumber())){
            return "Given phone number is not valid!";
        }
        if (passwordCheck(usr.getPassword()) != null){
            return "Given password is not valid!";
        }
        String salt = saltGen.get();
        usr.setRole(Usr.Role.USER);
        String resultPass = salt + usr.getPassword();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(resultPass.getBytes(StandardCharsets.US_ASCII));
            usr.setPasswordHash(new String(hash, StandardCharsets.US_ASCII));
            usr.setPassword(null);
        } catch (NoSuchAlgorithmException e) {
            //TODO - add logging
            throw new RuntimeException(e);
        }
        usrCrudJdbs.save(usr);
        return "The account is created successfully.";
    }

    private boolean exists(Usr usr){
        // usrCrudJdbs.findByEmail(usr.getEmail())
        return false; //TODO - implement find by id method
    }

    private boolean isEmailValid(String email){
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?" +
                "(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        return email.matches(regex);
    }

    private String passwordCheck(String password){
        return null; // TODO
    }

    private boolean isPhoneNumberValid(String phoneNumber){
        return phoneNumber.matches("^[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$");
    }

    public Usr LoginIn(String email, String password) {

        Optional<Usr> user = usrCrudJdbs.findByUniqueField(email);
        if (user.isPresent()) {
            if(isValidPassword(user.get(), password)){
                return user.get();

            }
        }
        return user.get();
    }

    public Map<String, String> validateDate(String email, String password){
        Map<String, String> messages = new HashMap<>();
        if(email.isEmpty() || password.isEmpty()){
            messages.put("email", "Enter login and password");
        }
        if(!isEmailValid(email)){
            messages.put("email", "Given email is not valid!");
        }else{
            messages.put("email", "User with this email not fount!");
        }
        return messages;
    }

    private boolean isValidPassword(Usr user, String password) {
        String salt = user.getSalt();
        String passHash = user.getPasswordHash();
        String userPass = salt + password;
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = messageDigest.digest(userPass.getBytes(StandardCharsets.US_ASCII));
        String userPassHash = new String(hash, StandardCharsets.US_ASCII);
        return userPassHash.equals(passHash);
    }

    public void LoginOut(){

    }

}
