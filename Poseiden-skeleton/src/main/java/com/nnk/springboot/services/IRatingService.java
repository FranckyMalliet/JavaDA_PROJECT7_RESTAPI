package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface IRatingService {

    public Rating addNewRatingToDatabase(Rating rating);
    public List<Rating> findAll();
    public Rating findById(Integer id);
    public void update(Rating rating);
    public void deleteById(Integer id);
}
