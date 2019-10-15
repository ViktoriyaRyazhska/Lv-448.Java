package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.ExhibitType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExhibitRowMapper implements RowMapper<Exhibit> {

    @Override
    public Exhibit mapRow(ResultSet resultSet) {
        Exhibit exhibit = new Exhibit();

        try{
            exhibit.setId(resultSet.getLong("id"));
            exhibit.setMaterial(resultSet.getString("material"));
            exhibit.setTechnique(resultSet.getString("techic"));
            exhibit.setType(ExhibitType.valueOf(resultSet.getString("type")));
            exhibit.setName((resultSet.getString("name")));

            return exhibit;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
