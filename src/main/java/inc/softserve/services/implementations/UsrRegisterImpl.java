package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.dao.interfaces.VisaDao;
import inc.softserve.dto.UsrDto;
import inc.softserve.dto.VisaDto;
import inc.softserve.entities.Usr;
import inc.softserve.entities.Visa;
import inc.softserve.security.SaltGen;
import inc.softserve.services.intefaces.UsrRegisterService;
import inc.softserve.utils.rethrowing_lambdas.RethrowingLambdas;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UsrRegisterImpl implements UsrRegisterService {

    private SaltGen saltGen;
    private UsrDao usrDao;
    private VisaDao visaDao;
    private CountryDao countryDao;
    private Connection conn;

    public UsrRegisterImpl(SaltGen saltGen, UsrDao usrDao, VisaDao visaDao, CountryDao countryDao, Connection conn) {
        this.saltGen = saltGen;
        this.usrDao = usrDao;
        this.visaDao = visaDao;
        this.countryDao = countryDao;
        this.conn = conn;
    }

    @Override
    public Map<String, String> register(UsrDto usrDto, VisaDto visaDto) {
        Map<String, String> error = new HashMap<>();
        if (!isEmailValid(usrDto.getEmail())) {
            error.put("email", "Given email is not valid!");
            return error;
        }
        if (exists(usrDto)) {
            error.put("empty", "An account with such an email already exists!");
            return error;
        }

        if (!isPhoneNumberValid(usrDto.getPhoneNumber())) {
            error.put("phone", "Given phone number is not valid!");
            return error;
        }
        if (isValidDate(usrDto.getBirthDate()) ) {
            error.put("date", "Date is not valid!");
            return error;
        }
        try {
            conn.setAutoCommit(false);
            Usr usr = usrDao.save(convertDtoToUser(usrDto));
            if (visaDto != null) {
                visaDao.save(convertDtoToVisa(visaDto, usr));
            }
            conn.commit();
            conn.setAutoCommit(true);
            return error;
        } catch (SQLException e) {
            RethrowingLambdas.runnable(() -> {
                conn.rollback();
                conn.setAutoCommit(true);
            });
            error.put("Oops", "Oops, something happened");
            return error;
        }
    }

    private String generateHashPassword(UsrDto usrDto, String salt){
        String resultPass = salt + usrDto.getPassword();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(resultPass.getBytes(StandardCharsets.US_ASCII));
            return (new String(hash, StandardCharsets.US_ASCII));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private Usr convertDtoToUser(UsrDto usrDto){
        Usr user = new Usr();
        String salt = saltGen.get();
        user.setFirstName(usrDto.getFirstName());
        user.setLastName(usrDto.getLastName());
        user.setBirthDate(generateDate(usrDto.getBirthDate()));
        user.setEmail(usrDto.getEmail());
        user.setPasswordHash(generateHashPassword(usrDto, salt));
        user.setSalt(salt);
        user.setPhoneNumber(usrDto.getPhoneNumber());
        user.setRole(Usr.Role.CLIENT);
        return user;
    }

    private Visa convertDtoToVisa(VisaDto visaDto, Usr user){
        Visa visa = new Visa();
        if (visaDto != null ) {
            visa.setVisaNumber(visaDto.getVisaNumber());
            visa.setIssued(visaDto.getIssued());
            visa.setExpirationDate(visaDto.getExpirationDate());
            visa.setCountry(countryDao
                    .findByCountryName(visaDto.getCountry())
                    .orElseThrow()
            );
            visa.setUsr(user);
        }else{
            return  visa;
        }
        return  visa;
    }

    private boolean exists(UsrDto usrDto){
        return usrDao.findByEmail(usrDto.getEmail()).isPresent();
    }

    private boolean isEmailValid(String email){
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?" +
                "(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        return email.matches(regex);
    }

    private boolean isPhoneNumberValid(String phoneNumber){
        return phoneNumber.matches("^[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$");
    }

    @Override
    public Usr login(String email, String password) {
        Optional<Usr> user = usrDao.findByEmail(email);
        if (user.isPresent()) {
            if(isValidPassword(user.get(), password)){
                return user.get();
            }else {
                throw new RuntimeException("Password incorrect!");
            }
        }else {
            return user.orElse(null);
        }
    }

    public Map<String, String> validateData(String email, String password){
        Map<String, String> messages = new HashMap<>();
        if(email.isBlank() || password.isBlank()){
            messages.put("email", "Enter login and password");
        }
        if(!isEmailValid(email)){
            messages.put("email", "Given email is not valid!");
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
            throw new RuntimeException(e);
        }
        byte[] hash = messageDigest.digest(userPass.getBytes(StandardCharsets.US_ASCII));
        String userPassHash = new String(hash, StandardCharsets.US_ASCII);
        return userPassHash.equals(passHash);
    }

    @Override
    public UsrDto initUsrDto(String firstName, String lastName, String email, String phone, String date, String password){
        UsrDto user = new UsrDto();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setPassword(password);
        user.setBirthDate(date);
        return user;
    }

    public LocalDate generateDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    @Override
    public VisaDto initVisaDto(String country, String ExpirationDate, String issued, String visaNumber){
        if (country.isEmpty()) {
            return null;
        } else {
            VisaDto visa = new VisaDto();
            visa.setCountry(country);
            visa.setExpirationDate(generateDate(ExpirationDate));
            visa.setIssued(generateDate(issued));
            visa.setVisaNumber(visaNumber);
            return visa;
        }
    }

    private boolean isValidDate(String date) {
        return date.matches("^\\d{4}/\\d{2}/\\d{2}$");
    }
}
