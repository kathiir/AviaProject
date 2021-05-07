package com.example.aviaapplication.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.aviaapplication.api.models.City;
import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.api.models.Passenger;
import com.example.aviaapplication.api.models.RecentFlight;
import com.example.aviaapplication.api.models.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Api {

    private List<Flight> flights;
    private Map<Long, List<Flight>> favoriteFlights;
    private List<RecentFlight> recentFlights;
    private Map<Long, Set<Flight>> recentViewed;
    private Map<Long, Set<City>> recentCities;
    private List<Passenger> passengers;
    private List<User> users;
    private List<City> cities;

    private boolean isLoggedIn = false;

    private static Api api;

    public static Api getInstance(){


        if(api == null)
            api = new Api();

        return api;
    }

    private Api(){
        cities = new ArrayList<>();
        cities.addAll(Arrays.asList(
                new City(1L, "Воронеж", "VOZ"),
                new City(2L, "Москва", "DME"),
                new City(3L, "Анаа", "AAA"),
                new City(4L, "Аугсбург", "AGB"),
                new City(5L, "Париж", "CDG"),
                new City(6L, "Лондон", "LCY"),
                new City(7L, "Вашингтон", "DCA"),
                new City(8L, "Берлин", "BER"),
                new City(9L, "Стокгольм", "ARN"),
                new City(10L, "Санкт-Петербург", "LED")));

        users = new ArrayList<>();

        users.add(new User(1L, "Владимир Беспалов", null));

        passengers = new ArrayList<>();
        recentCities = new TreeMap<>();
        recentFlights = new ArrayList<>();
        favoriteFlights = new TreeMap<>();
        recentViewed = new TreeMap<>();

        flights = new ArrayList<>();

        DateFormat format = new SimpleDateFormat("MM dd, yyyy HH:mm:ss");

        try {
            flights.addAll(Arrays.asList(
                    new Flight(1L, format.parse("05 08, 2021 07:00:00"), format.parse("05 08, 2021 08:10:00"), "Домодедово", "Чертовицкое", "DME", "VOZ", 5948, 4198),
                    new Flight(2L, format.parse("05 08, 2021 17:20:00"), format.parse("05 08, 2021 18:30:00"), "Домодедово", "Чертовицкое", "DME", "VOZ", 8698, 6448),
                    new Flight(3L, format.parse("05 01, 2021 10:10:00"), format.parse("05 01, 2021 11:30:00"),  "Чертовицкое","Домодедово",  "VOZ","DME", 7921, 5892)
            ));
            for (int i = 0; i < 2; i++) {
                flights.get(i).setArrivalCity(cities.get(1));
                flights.get(i).setDepCity(cities.get(0));
            }


            recentFlights.add(new RecentFlight(1L, 1L, flights.get(2), 2));


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Flight getFlightById(Long id){
        Flight res = flights.stream().filter(elem -> elem.getFlightId().equals(id)).findFirst().get();
        return res;
    }

    public List<Flight> getAllFlights(){
        return flights;
    }

    public Long getCurrentUserId(){
        return isLoggedIn ? 1L : null;
    }

    public void login(){
        isLoggedIn = true;
    }

    public void logout(){
        isLoggedIn = false;
    }

    public boolean isLoggedIn(){
        return isLoggedIn;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<RecentFlight> getRecentFlights(){
        List<RecentFlight> res = recentFlights.stream().filter(item -> item.getUserId().equals(1L)).collect(Collectors.toList());
        return isLoggedIn ?  res : new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean isFavorite(Long userId, Long flightId){
        if (favoriteFlights.containsKey(userId))
            return favoriteFlights.get(userId).stream().anyMatch(item -> item.getFlightId().equals(flightId));
        return false;
    }

    public void addFavorite(Long userId, Flight flight){
        if (favoriteFlights.containsKey(userId))
            favoriteFlights.get(userId).add(flight);
        else
            favoriteFlights.put(userId, new ArrayList<>(Arrays.asList(flight)));
    }

    public void removeFavorite(Long userId, Flight flight){
        if (favoriteFlights.containsKey(userId))
            favoriteFlights.get(userId).remove(flight);
    }

    public List<Flight> getFavoriteFlights(Long userId){
        if (isLoggedIn && favoriteFlights.containsKey(userId))
            return favoriteFlights.get(userId);

        return new ArrayList<>();
    }

    public void addToRecentViewed(Long userId, Flight flight){
        if (recentViewed.containsKey(userId))
            recentViewed.get(userId).add(flight);
        else
            recentViewed.put(userId, new HashSet<>(Arrays.asList(flight)));
    }

    public List<Flight> getViewedList(Long userId){
        if (recentViewed.containsKey(userId))
            return new ArrayList<>(recentViewed.get(userId));
        return new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Flight> find(Long from, Long to, Long fromCity, Long toCity){
        List<Flight> res = flights.stream().filter(item ->
                item.getDepartureDate().getTime() >= from &&
                        item.getDepartureDate().getTime() <= to &&
                        item.getDepCity().getId().equals(fromCity) &&
                        item.getArrivalCity().getId().equals(toCity)).collect(Collectors.toList());
        return res;
    }

    public List<City> getAllCities(){
        return cities;
    }

    public List<City> getRecentCities(Long userId){
        if (recentCities.containsKey(userId))
            return new ArrayList<>(recentCities.get(userId));

        return new ArrayList<>();
    }

    public void addRecentCity(Long userId, City city){
        if(recentCities.containsKey(userId))
            recentCities.get(userId).add(city);
        else
            recentCities.put(userId, new HashSet<>(Arrays.asList(city)));
    }
}

