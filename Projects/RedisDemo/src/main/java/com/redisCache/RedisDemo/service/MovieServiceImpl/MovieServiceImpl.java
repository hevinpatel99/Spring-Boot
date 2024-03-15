package com.redisCache.RedisDemo.service.MovieServiceImpl;


import com.redisCache.RedisDemo.dto.MovieDTO;
import com.redisCache.RedisDemo.exception.CustomException;
import com.redisCache.RedisDemo.model.Movie;
import com.redisCache.RedisDemo.repository.MovieDao;
import com.redisCache.RedisDemo.repository.MovieRepository;
import com.redisCache.RedisDemo.response.ApiResponse;
import com.redisCache.RedisDemo.service.MovieService;
import com.redisCache.RedisDemo.util.BaseMethods;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    private final MovieRepository movieRepository;


    private final MovieDao movieDao;

    public MovieServiceImpl(MovieRepository movieRepository,MovieDao movieDao) {
        this.movieRepository = movieRepository;
        this.movieDao = movieDao;
    }

    @Override
    public ResponseEntity<?> insertMovie(MovieDTO movieDTO, HttpServletRequest request) throws CustomException {
        logger.info(" --- insertMovie --- ");
        List<Movie> movieList = movieRepository.searchByMovie(movieDTO.getMovie());
        if(BaseMethods.validateListNotNullOrNotEmpty(movieList)) {
            if (movieList.get(0).getMovie().equalsIgnoreCase(movieDTO.getMovie())) {
                throw new CustomException("movie is already Exists!");
            }
        }else {
            Movie movie = movieRepository.save(saveMovie(movieDTO));
            return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.CREATED, movie, "Movie Inserted SuccessFully", request.getRequestURI()));

        }

        return  ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.NOT_FOUND, null, "Movie Not Inserted SuccessFully", request.getRequestURI()));

    }

    @Override
//    @Cacheable(value = "cachedMovies")
    public ResponseEntity<?> getAllMovie(int page, int size, HttpServletRequest request) {
        logger.info(" --- getAllMovie --- : Page : " + page + " || Size : " + size);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Movie> movieList = movieRepository.findAll(pageable);
        if (!movieList.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, movieList, "All Movie get successFully", request.getRequestURI()));
        } else {
            return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.NOT_FOUND, null, "Not Found", request.getRequestURI()));
        }
    }

    @Override
//    @Caching(evict = { @CacheEvict(value = "cachedMovies", key = "#movieName",allEntries = true)})
    public ResponseEntity<?> removeMovie(String movieName, HttpServletRequest request) {
        logger.info(" --- removeMovie --- : MovieName : " + movieName);
        List<Movie> movieList = movieRepository.searchByMovie(movieName);
        if (movieList != null && !movieList.isEmpty()) {
            Movie movie = movieList.get(0);
            if (movie.isStatus()) {
                movie.setStatus(Boolean.FALSE);
            }
            movie = movieRepository.save(movie);
            return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, movie, "Movie InActive Successfully", request.getRequestURI()));
        }
        return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.BAD_REQUEST, null, "Unsuccessfully", request.getRequestURI()));
    }

    @Override
//    @Caching(evict = { @CacheEvict(value = "cachedMovies", allEntries = true), }, put = {
//            @CachePut(value = "cachedMovies", key = "#movieName") })
    public ResponseEntity<?> updateMovie(MovieDTO movieDTO, String movieName, HttpServletRequest request) {
        logger.info(" --- updateMovie --- : MovieName : " + movieName);
        if (movieDTO != null) {
            List<Movie> movieList = movieRepository.searchByMovie(movieName);
            if (movieList != null && !movieList.isEmpty()) {
                logger.info("Get Movie : " + movieList.get(0));
                Movie movie = updateMovie(movieList.get(0), movieDTO);
                movie.setUpdatedAt(new Date());
                movie = this.movieRepository.save(movie);
                return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, movie, "Movie updated Successfully", request.getRequestURI()));
            }
        }
        return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.BAD_REQUEST, null, "Movie Not updated", request.getRequestURI()));
    }

    @Override
    public ResponseEntity<?> getMovieBySearchBy(String searchContent, String searchText, HttpServletRequest request) {
        logger.info(" --- getMovieBySearchBy --- : SearchContent :  " + searchText + " || searchContent :" + searchText);
        if (searchContent != null && !searchContent.isEmpty() && searchText != null && !searchText.isEmpty()) {
            List<Document> movieList = movieDao.searchByContentAndText(Movie.class, searchContent, searchText);
//            List<Movie> movieList = searchByContentAndText(searchContent, searchText);
            if (BaseMethods.validateListNotNullOrNotEmpty(movieList)) {
                return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, movieList, "Movie get Successfully", request.getRequestURI()));
            }
        }
        return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.BAD_REQUEST, null, "Data Not fond", request.getRequestURI()));
    }


//    private List<Movie> searchByContentAndText(String searchContent, String searchText) {
//        List<Movie> movieList = new ArrayList<>();
//        if (searchContent.equalsIgnoreCase("actor")) {
//            movieList = movieRepository.searchByActor(searchText);
//        } else if (searchContent.equalsIgnoreCase("actress")) {
//            movieList = movieRepository.searchByActress(searchText);
//        } else if (searchContent.equalsIgnoreCase("movie")) {
//            movieList = movieRepository.searchByMovie(searchText);
//        } else if (searchContent.equalsIgnoreCase("releaseYear")) {
//            movieList = movieRepository.searchByReleaseYear(Integer.parseInt(searchText));
//        } else {
//            System.out.println("Not found contain !!");
//        }
//        return movieList;
//
//    }

    @Override
//    @Cacheable(value = "cachedMovies",unless = "#result.toString() != null")
    public ResponseEntity<?> getAllMovieWithStatusTrue(int page, int size, HttpServletRequest request) {
        logger.info(" --- getAllMovieWithStatusTrue --- : Page : " + page + " || Size : " + size);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "movie"));
        Page<Movie> movieList = movieRepository.findAllAndStatusTrue(pageable);
        if (!movieList.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, movieList, "All Movie get successFully", request.getRequestURI()));
        } else {
            return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.NOT_FOUND, null, "Not Found", request.getRequestURI()));
        }
    }


    @Override
    public ResponseEntity<?> getDataByMultiSearch(String searchContent1, String searchText1, String searchContent2, String searchText2, HttpServletRequest request) {
        logger.info(" --- getDataByMultiSearch --- : SearchContent1 : " + searchContent1 + " || SearchText1 : " + searchText1 + " || SearchContent2 : " + searchContent2 + " || SearchText2 : " + searchText2);
        List<Document> movieList = movieDao.searchByMultiContentAndText(Movie.class, searchContent1, searchText1, searchContent2, searchText2);
//            List<Movie> movieList = searchByContentAndText(searchContent, searchText);
        if (BaseMethods.validateListNotNullOrNotEmpty(movieList)) {
            return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, movieList, "Movie get Successfully", request.getRequestURI()));
        }
        return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.BAD_REQUEST, null, "Data Not fond", request.getRequestURI()));
    }

    @Override
//    @Caching(evict = { @CacheEvict(value = "cachedMovies", allEntries = true), }, put = {
//            @CachePut(value = "cachedMovies", key = "#movieName") })
    public ResponseEntity<?> activeMovie(String movieName, HttpServletRequest request) {
        logger.info(" --- activeMovie --- : MovieName : " + movieName);
        List<Movie> movieList = movieRepository.searchByMovie(movieName);
        if (movieList != null && !movieList.isEmpty()) {
            Movie movie = movieList.get(0);
            if (movie != null && !movie.isStatus()) {
                movie.setUpdatedAt(new Date());
                movie.setStatus(Boolean.TRUE);
                movie = this.movieRepository.save(movie);
                return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, movie, "Movie activated Successfully", request.getRequestURI()));
            }
        }
        return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.BAD_REQUEST, null, "Data Not fond", request.getRequestURI()));
    }

    @Override
    public ResponseEntity<?> getAllMovie(HttpServletRequest request) {
        try {
            logger.info(" --- getAllMovie --- ");
            List<Movie> movieList = movieRepository.findAll();
            if (!movieList.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, movieList, "All Movie get successFully", request.getRequestURI()));
            } else {
                return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.NOT_FOUND, null, "Not Found", request.getRequestURI()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getMovieByMovieName(String movieName, HttpServletRequest request) {
        List<Movie> movieList = movieRepository.searchByMovie(movieName);
        if (movieList != null && !movieList.isEmpty()) {
            Movie movie = movieList.get(0);
                return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, movie, "Movie get Successfully", request.getRequestURI()));
        }
        return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.BAD_REQUEST, null, "Data Not fond", request.getRequestURI()));
    }


    public Movie saveMovie(MovieDTO movieDTO) {
        logger.info(" --- saveMovie --- ");
        return Movie.builder().movie(movieDTO.getMovie()).actor(movieDTO.getActor()).actress(movieDTO.getActress())
                .releaseYear(movieDTO.getReleaseYear()).status(Boolean.TRUE).build();
    }

    public Movie updateMovie(Movie movie, MovieDTO movieDTO) {
        logger.info(" --- updateMovie --- ");
        movie.setMovie(movieDTO.getMovie());
        movie.setActor(movieDTO.getActor());
        movie.setActress(movieDTO.getActress());
        movie.setReleaseYear(movieDTO.getReleaseYear());
        movie.setStatus(movieDTO.isStatus());
        return movie;
    }
}
