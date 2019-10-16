package inc.softserve.services;

import inc.softserve.dao.implementations.CountryDaoJdbc;
import inc.softserve.dao.implementations.UsrDaoJdbc;
import inc.softserve.dao.implementations.VisaDaoJdbc;
import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.dao.interfaces.VisaDao;
import inc.softserve.datebase.ConnectDb;
import inc.softserve.dto.UsrDto;
import inc.softserve.dto.VisaDto;
import inc.softserve.entities.Usr;
import inc.softserve.entities.Visa;
import inc.softserve.security.JavaNativeSaltGen;
import inc.softserve.security.SaltGen;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UsrRegisterImpl implements UsrRegisterService {

    private SaltGen saltGen = new JavaNativeSaltGen(); // TODO - impl a singleton container or make static
    private UsrDao usrDao;
    private VisaDao visaDao;
    private CountryDao countryDao;
    Connection conn;

    public UsrRegisterImpl() {
        conn = ConnectDb.connectBase();
        this.usrDao = new UsrDaoJdbc(conn);
        this.visaDao = new VisaDaoJdbc(conn, usrDao, countryDao);
        this.countryDao = new CountryDaoJdbc(conn);
    }


    @Override
    public String register(UsrDto usrDto, VisaDto visaDto) {

        if (exists(usrDto)) {
            return "An account with such an email already exists!";
        }
        if (!isEmailValid(usrDto.getEmail())) {
            return "Given email is not valid!";
        }
        if (!isPhoneNumberValid(usrDto.getPhoneNumber())) {
            return "Given phone number is not valid!";
        }
        if (passwordCheck(usrDto.getPassword()) != null) {
            return "Given password is not valid!";
        }
//        try {
//            conn.setAutoCommit(false);
            Usr us = usrDao.save(convertDtoToUser(usrDto));
            visaDao.save(convertDtoToVisa(visaDto, us));
            return "The account is created successfully.";
//        } catch (SQLException e) {
//            try {
//                conn.setAutoCommit(true);
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//            return "Oops, something happened";
//        }
    }

    private String generateHashPassword(UsrDto usrDto, String salt){
        String resultPass = salt + usrDto.getPassword();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(resultPass.getBytes(StandardCharsets.US_ASCII));
            return (new String(hash, StandardCharsets.US_ASCII));

        } catch (NoSuchAlgorithmException e) {
            //TODO - add logging
            throw new RuntimeException(e);
        }

    }

    private Usr convertDtoToUser(UsrDto usrDto){
        Usr user = new Usr();
        String salt = saltGen.get();
        user.setFirstName(usrDto.getFirstName());
        user.setLastName(usrDto.getLastName());
        user.setBirthDate(usrDto.getBirthDate());
        user.setEmail(usrDto.getEmail());
        user.setPasswordHash(generateHashPassword(usrDto, salt));
        user.setSalt(salt);
        user.setPhoneNumber(usrDto.getPhoneNumber());
        user.setRole(Usr.Role.CLIENT);
        return user;
    }

    private Visa convertDtoToVisa(VisaDto visaDto, Usr user){
        Visa visa = new Visa();
        visa.setVisaNumber(visaDto.getVisaNumber());
        visa.setIssued(visaDto.getIssued());
        visa.setExpirationDate(visaDto.getExpirationDate());
        visa.setCountry(countryDao
                .findByCountryName(visaDto.getCountry())
                .orElseThrow()
        );
        visa.setUsr(user);
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

    public Map<String, String> validateData(String email, String password){
        Map<String, String> messages = new HashMap<>();
        if(email.isBlank() || password.isBlank()){
            System.out.println("Given email is not valid!");
            messages.put("email", "Enter login and password");
        }
        if(!isEmailValid(email)){
            System.out.println("Given email is not valid!1");
            messages.put("email", "Given email is not valid!1");
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

    public UsrDto initUsrDto(String firstName, String lastName, String email, String phone, String date, String password){
        UsrDto user = new UsrDto();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setPassword(password);
        user.setBirthDate(generateDate(date));
        return user;
    }
    private LocalDate generateDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(date, formatter);
    }

    public VisaDto initVisaDto(String country, String ExpirationDate, String issued, String visaNumber){
        VisaDto visa = new VisaDto();
        visa.setCountry(country);
        visa.setExpirationDate(generateDate(ExpirationDate));
        visa.setIssued(generateDate(issued));
        visa.setVisaNumber(visaNumber);
        return visa;
    }

    private boolean isValidDate(String date) {
        return date.matches("^\\d{4}/\\d{2}/\\d{2}$");



    }

}
