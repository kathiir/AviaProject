package service.mapper;

import avia.models.FavoriteFlightModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import service.models.FavoriteFlight;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface FavoriteFlightMapper {
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "flightModel", source = "flight")
    FavoriteFlightModel toFavoriteFlightModel(FavoriteFlight purchase);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "flight", source = "flightModel")
    FavoriteFlight toFavoriteFlight(FavoriteFlightModel purchase);

    default List<FavoriteFlight> toListFavoriteFlight(List<FavoriteFlightModel> list) {
        return list
                .stream()
                .map(this::toFavoriteFlight)
                .collect(Collectors.toList());
    }
}
