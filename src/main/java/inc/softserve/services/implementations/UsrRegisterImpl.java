package inc.softserve.services.implementations;

import inc.softserve.dao.implementations.CountryDaoJdbc;
import inc.softserve.dao.implementations.UsrDaoJdbc;
import inc.softserve.dao.implementations.VisaDaoJdbc;
import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.dao.interfaces.VisaDao;
import inc.softserve.connectivity.ConnectDb;
import inc.softserve.dto.UsrDto;
import inc.softserve.dto.VisaDto;
import inc.softserve.entities.Usr;
import inc.softserve.entities.Visa;
import inc.softserve.security.JavaNativeSaltGen;
import inc.softserve.security.SaltGen;
import inc.softserve.services.intefaces.UsrRegisterService;
import inc.softserve.utils.rethrowing_lambdas.ThrowingLambdas;
import inc.softserve.utils.rethrowing_lambdas.ThrowingRunnable;
import org.apache.catalina.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    /**
     * Constructor with 5 parameters.
     */

    public UsrRegisterImpl(SaltGen saltGen, UsrDao usrDao, VisaDao visaDao, CountryDao countryDao, Connection conn) {
        this.saltGen = saltGen;
        this.usrDao = usrDao;
        this.visaDao = visaDao;
        this.countryDao = countryDao;
        this.conn = conn;
    }

    /**
     * Method saves new user in database
     *
     * @param usrDto User dto
     * @param visaDto visa dto
     *
     * @return Map with errors.
     */
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
            ThrowingLambdas.runnable(() -> {
                conn.rollback();
                conn.setAutoCommit(true);
            });
            error.put("Oops", "Oops, something happened");
            return error;
        }
    }

    /**
     * Generate hash password.
     *
     * @param usrDto User dto.
     * @param salt Password salt.
     *
     * @return String salt of password.
     */
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

    /**
     * Convert user dto to user entity.
     *
     * @param usrDto Usr dto.
     *
     * @return Usr entity.
     */
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

    /**
     * Convert visa dto to visa entity.
     *
     * @param visaDto User dto.
     * @param user Usr entity.
     *
     * @return Visa entity.
     */
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

    /**
     * Methods check if user exists.
     *
     * @param usrDto User dto.
     *
     * @return True if user exists.
     */
    private boolean exists(UsrDto usrDto){
        return usrDao.findByEmail(usrDto.getEmail()).isPresent();
    }

    /**
     * Methods check is email valid.
     *
     * @param email for check.
     *
     * @return True if email is valid.
     */
    private boolean isEmailValid(String email){
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?" +
                "(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        return email.matches(regex);
    }

    /**
     * Methods check is phone number valid.
     *
     * @param phoneNumber for check.
     *
     * @return True if phone number is valid.
     */
    private boolean isPhoneNumberValid(String phoneNumber){
        return phoneNumber.matches("^[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$");
    }

    /**
     * Methods sing in.
     *
     * @param email for login.
     * @param password for login.
     *
     * @return Usr entity if email and password is true.
     */
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

    /**
     * Method validate data.
     *
     * @param email for login.
     * @param password for login.
     *
     * @return Map with errors.
     */
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

    /**
     * Method validates password.
     *
     * @param user entity.
     * @param password raw entered password.
     *
     * @return True if password validate.
     */
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
