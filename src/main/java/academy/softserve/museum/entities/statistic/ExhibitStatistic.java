package academy.softserve.museum.entities.statistic;

import java.util.Map;

public class ExhibitStatistic {
    private Map<String, Integer> materialStatistic;
    private Map<String, Integer> techniqueStatistic;

    public ExhibitStatistic(Map<String, Integer> materialStatistic, Map<String, Integer> techniqueStatistic) {
        this.materialStatistic = materialStatistic;
        this.techniqueStatistic = techniqueStatistic;
    }

    public ExhibitStatistic() {
    }

    public Map<String, Integer> getMaterialStatistic() {
        return materialStatistic;
    }

    public void setMaterialStatistic(Map<String, Integer> materialStatistic) {
        this.materialStatistic = materialStatistic;
    }

    public Map<String, Integer> getTechniqueStatistic() {
        return techniqueStatistic;
    }

    public void setTechniqueStatistic(Map<String, Integer> techniqueStatistic) {
        this.techniqueStatistic = techniqueStatistic;
    }
}
