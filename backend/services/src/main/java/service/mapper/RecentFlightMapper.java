package service.mapper;

import avia.models.RecentFlightModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import service.models.RecentFlight;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface RecentFlightMapper {
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "flightModel", source = "flight")
    RecentFlightModel toRecentFlightModel(RecentFlight recentFlight);


    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "flight", source = "flightModel")
    RecentFlight toRecentFlight(RecentFlightModel recentFlight);

    default List<RecentFlight> toListRecentFlight(List<RecentFlightModel> list) {
        return list
                .stream()
                .map(this::toRecentFlight)
                .collect(Collectors.toList());
    }
}
