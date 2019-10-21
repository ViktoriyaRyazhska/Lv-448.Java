package academy.softserve.museum.entities.mappers;

import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.dto.ExcursionStatsDto;
import academy.softserve.museum.entities.statistic.ExcursionStatistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcursionStatsDtoMapper {
    public static List<ExcursionStatsDto> getExcursionStats(ExcursionStatistic statistic) {
        List<ExcursionStatsDto> excursionStats = new ArrayList<>();
        Map<Excursion, Integer> statisticsMap = statistic.getExcursionCountMap();
        for(Map.Entry<Excursion, Integer> stat:statisticsMap.entrySet()){
           excursionStats.add(new ExcursionStatsDto(stat.getKey().getName(), stat.getValue()));
        }
        return excursionStats;
    }
}
