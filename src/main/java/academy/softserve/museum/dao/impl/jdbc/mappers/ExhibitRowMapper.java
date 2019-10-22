package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.ExhibitType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class used for creating Exhibit from ResultSet.
 */
public class ExhibitRowMapper implements RowMapper<Exhibit> {

    /**
     * Method for creating Exhibit from ResultSet.
     *
     * @param resultSet object with needed data for
     *                  creating Exhibit.
     * @return Exhibit created from ResultSet.
     */
    @Override
    public Exhibit mapRow(ResultSet resultSet) {
        Exhibit exhibit = new Exhibit();

        try{
            exhibit.setId(resultSet.getLong("exhibit_id"));
            exhibit.setMaterial(resultSet.getString("exhibit_material"));
            exhibit.setTechnique(resultSet.getString("exhibit_technique"));
            exhibit.setType(ExhibitType.valueOf(resultSet.getString("exhibit_type")));
            exhibit.setName((resultSet.getString("exhibit_name")));

            return exhibit;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
