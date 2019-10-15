package inc.softserve.services;

import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.dao.interfaces.VisaDao;
import inc.softserve.dto.UsrDto;
import inc.softserve.dto.VisaDto;
import inc.softserve.entities.Usr;
import inc.softserve.entities.Visa;
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
    private final UsrDao usrDao;
    private final VisaDao visaDao;
    private final CountryDao countryDao;

    public UsrRegisterImpl(UsrDao usrDao, VisaDao visaDao, CountryDao countryDao) {
        this.usrDao = usrDao;
        this.visaDao = visaDao;
        this.countryDao = countryDao;
    }

    @Override
    public String register(UsrDto usrDto, VisaDto visaDto) {

        if (exists(usrDto)){
            return "An account with such an email already exists!";
        }
        if (! isEmailValid(usrDto.getEmail())){
            return "Given email is not valid!";
        }
        if (! isPhoneNumberValid(usrDto.getPhoneNumber())){
            return "Given phone number is not valid!";
        }
        if (passwordCheck(usrDto.getPassword()) != null){
            return "Given password is not valid!";
        }
        String salt = saltGen.get();
        String resultPass = salt + usrDto.getPassword();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(resultPass.getBytes(StandardCharsets.US_ASCII));
            usrDto.setPassword(new String(hash, StandardCharsets.US_ASCII));
            usrDto.setPassword(null);
        } catch (NoSuchAlgorithmException e) {
            //TODO - add logging
            throw new RuntimeException(e);
        }
        usrDao.save(convertDtoToUser(usrDto));
        visaDao.save(convertDtoToVisa(visaDto));
        return "The account is created successfully.";
    }

    private Usr convertDtoToUser(UsrDto usrDto){
        Usr user = new Usr();
        user.setFirstName(usrDto.getFirstName());
        user.setLastName(usrDto.getLastName());
        user.setBirthDate(usrDto.getBirthDate());
        user.setEmail(usrDto.getEmail());
        user.setPasswordHash(usrDto.getPassword());
        user.setPhoneNumber(usrDto.getPhoneNumber());
        user.setRole(Usr.Role.CLIENT);
        return user;
    }

    private Visa convertDtoToVisa(VisaDto visaDto){
        Visa visa = new Visa();
        visa.setVisaNumber(visaDto.getVisaNumber());
        visa.setIssued(visaDto.getIssued());
        visa.setExpirationDate(visaDto.getExpirationDate());
        visa.setCountry(countryDao
                .findByCountryName(visaDto.getCountry())
                .orElse(null)
        );
        return  visa;

    }


    private boolean exists(UsrDto usrDto){
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
        Optional<Usr> user = usrDao.findByEmail(email);
        if (user.isPresent()) {
            if(isValidPassword(user.get(), password)){
                return user.get();

            }
        }
        return user.orElse(null);
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
