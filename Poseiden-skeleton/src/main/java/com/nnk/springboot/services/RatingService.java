package com.nnk.springboot.services;

import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService implements IRatingService {

    @Autowired
    private RatingRepository ratingRepository;
}