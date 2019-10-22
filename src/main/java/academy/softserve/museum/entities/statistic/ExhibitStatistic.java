package academy.softserve.museum.entities.statistic;

import java.util.HashMap;
import java.util.Map;

/**
 * Class, that contains Exhibit statistic
 */
public class ExhibitStatistic {
    /**
     * Map, that contains statistic by exhibit materials
     */
    private Map<String, Integer> materialStatistic;
    /**
     * Map, that contains statistic by exhibit techniques
     */
    private Map<String, Integer> techniqueStatistic;

    /**
     * Constructor, that accepts all parameters
     */
    public ExhibitStatistic(Map<String, Integer> materialStatistic, Map<String, Integer> techniqueStatistic) {
        this.materialStatistic = materialStatistic;
        this.techniqueStatistic = techniqueStatistic;
    }

    /**
     * Constructor, that initialize maps
     */
    public ExhibitStatistic() {
        materialStatistic = new HashMap<>();
        techniqueStatistic = new HashMap<>();
    }

    /**
     * @return material statistic map
     */
    public Map<String, Integer> getMaterialStatistic() {
        return materialStatistic;
    }

    /**
     * @param materialStatistic material statistic map
     */
    public void setMaterialStatistic(Map<String, Integer> materialStatistic) {
        this.materialStatistic = materialStatistic;
    }

    /**
     * @return technique statistic map
     */
    public Map<String, Integer> getTechniqueStatistic() {
        return techniqueStatistic;
    }

    /**
     * @param techniqueStatistic technique statistic map
     */
    public void setTechniqueStatistic(Map<String, Integer> techniqueStatistic) {
        this.techniqueStatistic = techniqueStatistic;
    }
}
