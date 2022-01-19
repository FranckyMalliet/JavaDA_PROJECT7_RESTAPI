package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CurvePointService implements ICurvePoint {

    private final Logger logger = LoggerFactory.getLogger(CurvePointService.class);
    private final CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curvePointRepository){
        this.curvePointRepository = curvePointRepository;
    }

    public CurvePoint addNewCurvePointToDatabase(CurvePoint curvePoint){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        curvePoint.setCreationDate(timestamp.from(Instant.now()));

        logger.info("Adding a new CurvePoint to the database");
        return curvePointRepository.save(curvePoint);
    }

    public List<CurvePoint> findAll(){
        logger.info("Retrieving all CurvePoints from database");
        return curvePointRepository.findAll();
    }

    public CurvePoint findById(Integer id){
        logger.info("Retrieving a CurvePoint with the id number " + id);
        return curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id " +id));
    }

    public void updateCurvePoint(CurvePoint curvePoint){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        curvePoint.setAsOfDate(timestamp.from(Instant.now()));

        logger.info("Updating CurvePoint with id number : " + curvePoint.getCurveId());
        curvePointRepository.save(curvePoint);
    }

    public void deleteCurvePoint(Integer id){
        logger.info("Deleting CurvePoint with id number : " + id);
        curvePointRepository.deleteById(id);
    }
}
