package service;

import dao.implemetsDao.AddressDao;
import entities.Address;

public class AddressService {
    private AddressDao addressDao;

    public AddressService(AddressDao addressDao) {
        this.addressDao = addressDao;
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
