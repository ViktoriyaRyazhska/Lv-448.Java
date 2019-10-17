package dao.interfaceDao;

import entities.Address;

import java.util.List;

public interface AddressDaoInterface extends Crud<Address> {

    List<Address> findAll();


}
