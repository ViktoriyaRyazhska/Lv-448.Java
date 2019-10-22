package service;

import dao.implemetsDao.AddressDao;
import dao.interfaceDao.AddressDaoInterface;
import database.DaoFactory;
import entities.Address;

public class AddressService {
    private AddressDaoInterface addressDao;

    public AddressService() {
        this.addressDao = DaoFactory.addressDao();
    }

    public void createAddress(Address address) {
        addressDao.save(address);
    }

    public boolean updateAddress(Address address) {
        addressDao.update(address);
        if (addressDao.findById(address.getId()).isPresent()) {
            addressDao.update(address);
            return true;
        } else {
            return false;
        }
    }

    public Address findAddressById(Long id) {
        return addressDao.findById(id).get();
    }


}
