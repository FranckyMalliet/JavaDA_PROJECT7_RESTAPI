package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;
import java.util.Optional;

public interface ICurvePoint {

    public CurvePoint addNewCurvePointToDatabase(CurvePoint curvePoint);
    public List<CurvePoint> findAllCurvePointInDatabase();
    public Optional<CurvePoint> findCurvePointById(Integer id);
    public void updateCurvePoint(Integer id);
    public void deleteCurvePoint(Integer id);
}
