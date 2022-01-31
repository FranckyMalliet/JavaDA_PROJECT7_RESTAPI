package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class RatingService implements IRatingService {

    private final static Logger logger = LoggerFactory.getLogger(RatingService.class);
    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository){
        this.ratingRepository = ratingRepository;
    }

    /**
     * Method using crudRepository, saving Rating to the database
     * @param rating
     * @return a Rating in the database
     */

    public Rating addNewRatingToDatabase(Rating rating){
        logger.info("Adding a new Rating to database");
        return ratingRepository.save(rating);
    }

    /**
     * @return all Ratings from database
     */

    public List<Rating> findAll(){
        logger.info("Retrieving all Ratings from database");
        return ratingRepository.findAll();
    }

    /**
     * @param id
     * @return a Rating by his id
     */

    public Rating findById(Integer id){
        logger.info("Retrieving Rating with id number " + id + " from database");
        return ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id " + id));
    }

    /**
     * Method using crudRepository, updating a Rating in the database
     * @param rating
     */

    public void update(Rating rating){
        logger.info("Updating Rating with id number " + rating.getId());
        ratingRepository.save(rating);
    }

    /**
     * Method using crudRepository, deleting a Rating by his id in the database
     * @param id
     */

    public void deleteById(Integer id){
        logger.info("Deleting rating with id number " + id);
        ratingRepository.deleteById(id);
    }
}
