package com.devskiller.movies;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MoviesAnalyzer {


    private List<Movie> movies;
    private List<User> users;

    public MoviesAnalyzer(List<Movie> movies, List<User> users) {
        this.movies = movies;
        this.users = users;
    }

    public List<String> topWatchlistedMoviesAmongFriends(int userId) {

        List<String> friendsWatchlist = new ArrayList<>();

        try {
            for (User user : users) {
                if (user.getUserId() == userId) {
                    for (int i = 0; i < user.getFriends().size(); i++) {
                        for (Movie movie : movies) {
                            if (movie.getWatchlist().contains(user.getFriends().get(i))) {
                                friendsWatchlist.add(movie.getTitle());
                            }
                        }
                    }
                }
            }

            Map<String, Long> frequencyMap =
                    friendsWatchlist
                            .stream()
                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            Map<String, Long> sortedMap =
                    frequencyMap
                            .entrySet()
                            .stream()
                            .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                                    .thenComparing(Map.Entry.comparingByKey()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (e1, e2) -> e1, LinkedHashMap::new));

            return sortedMap
                    .entrySet()
                    .stream()
                    .map(Map.Entry::getKey)
                    .limit(4)
                    .collect(Collectors.toList());

        } catch (NullPointerException e) {
            for (Movie movie : movies) {
                friendsWatchlist.add(movie.getTitle());
            }
            return friendsWatchlist;
        }
    }
}
