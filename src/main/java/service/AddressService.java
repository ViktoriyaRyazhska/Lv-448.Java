package service;

import dao.interfaceDao.AddressDaoInterface;
import database.DaoFactory;
import entities.Address;

public class AddressService {

    private AddressDaoInterface addressDao;

    /**
     * Default constructor
     */
    public AddressService() {
        this.addressDao = DaoFactory.addressDao();
    }

    /**
     * Method for creating an address in database
     *
     * @param address address that must be created
     */
    public void createAddress(Address address) {
        addressDao.save(address);
    }

    /**
     * Method for updating address in database
     *
     * @param address address for updating
     * @return
     */
    public boolean updateAddress(Address address) {
        addressDao.update(address);
        if (addressDao.findById(address.getId()).isPresent()) {
            addressDao.update(address);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for finding an address by its id
     *
     * @param id address id
     * @return Address object if it is present in database
     */
    public Address findAddressById(Long id) {
        return addressDao.findById(id).get();
    }

}
