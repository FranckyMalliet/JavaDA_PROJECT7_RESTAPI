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

@Service
@Transactional(rollbackFor = Exception.class)
public class CurvePointService implements ICurvePointService {

    private final Logger logger = LoggerFactory.getLogger(CurvePointService.class);
    private final CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curvePointRepository){
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * Method using crudRepository, saving CurvePoint to the database
     * Before saving a CurvePoint, add timestamp to it
     * @param curvePoint
     * @return a CurvePoint in the database
     */

    public CurvePoint addNewCurvePointToDatabase(CurvePoint curvePoint){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        curvePoint.setCreationDate(timestamp.from(Instant.now()));

        logger.info("Adding a new CurvePoint to the database");
        return curvePointRepository.save(curvePoint);
    }

    /**
     * @return all CurvePoints from database
     */

    public List<CurvePoint> findAll(){
        logger.info("Retrieving all CurvePoints from database");
        return curvePointRepository.findAll();
    }

    /**
     * @param id
     * @return a CurvePoint by his ID
     */

    public CurvePoint findById(Integer id){
        logger.info("Retrieving CurvePoint with id number " + id + " from database");
        return curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id " +id));
    }

    /**
     * Method using crudRepository, updating a CurvePoint in the database
     * Before updating a CurvePoint, set a timestamp to it
     * @param curvePoint
     */

    public void update(CurvePoint curvePoint){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        curvePoint.setAsOfDate(timestamp.from(Instant.now()));

        logger.info("Updating CurvePoint with id number : " + curvePoint.getCurveId());
        curvePointRepository.save(curvePoint);
    }

    /**
     * Method using crudRepository, deleting a CurvePoint by his id in the database
     * @param id
     */

    public void deleteById(Integer id){
        logger.info("Deleting CurvePoint with id number : " + id);
        curvePointRepository.deleteById(id);
    }
}
