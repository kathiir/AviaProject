package service.internal.impl;

import avia.models.CityModel;
import avia.models.RecentCityModel;
import avia.repositories.CityRepository;
import avia.repositories.RecentCityRepository;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import service.internal.CityService;
import service.mapper.CityMapper;
import service.models.RecentCity;
import service.models.city.City;
import service.models.city.AnswerModelCity;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class CityServiceImpl implements CityService {

    @Value("${x-rapidapi-key}")
    private String rapid;
    @Value("${x-rapidapi-host}")
    private String host;

    private final RecentCityRepository recentCityRepository;
    private final CityMapper cityMapper;
    private OkHttpClient client = new OkHttpClient();
    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(RecentCityRepository recentCityRepository, CityMapper cityMapper, CityRepository cityRepository) {
        this.recentCityRepository = recentCityRepository;
        this.cityMapper = cityMapper;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<RecentCity> getRecentCities(String userId) {
        List<RecentCityModel> cityModels = recentCityRepository.findAllByUserId(userId);
        return cityMapper.toListRecentCity(cityModels);
    }

    @Override
    public List<City> searchPlaceByName(String name) throws IOException {
        Request request = new Request.Builder()
                .url("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/RU/RUB/ru/?query=" + name)
                .get()
                .addHeader("x-rapidapi-key", rapid)
                .addHeader("x-rapidapi-host", host)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String body = Objects.requireNonNull(response.body()).string();
            AnswerModelCity list = new Gson().fromJson(body, AnswerModelCity.class);
            return list.getPlaces();
        }
        return null;
    }

    @Override
    public Integer addRecentCity(RecentCity recentCity) {
        Integer idCity = addCity(recentCity.getCity());
        RecentCityModel model = cityMapper.toRecentCityModel(recentCity);
        model.getCity().setId(idCity);
        try {
            recentCityRepository.save(model);
            return recentCityRepository.save(model).getId();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Integer addCity(City city) {
        CityModel model = cityRepository.findFirstByPlaceIdAndCountryNameAndCityIdAndPlaceName(city.getPlaceId(), city.getCountryName(), city.getCityId(), city.getPlaceName());
        if (model != null) {
            return model.getId();
        }
        CityModel cityModel = cityMapper.toCityModel(city);
        CityModel saved = cityRepository.save(cityModel);
        return saved.getId();
    }
}
