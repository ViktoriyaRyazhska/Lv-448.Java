package dao.interfaceDao;

import entities.Genre;

import java.util.List;

public interface GenreDaoInterface extends Crud<Genre> {
   List<Genre> findAll();

}
