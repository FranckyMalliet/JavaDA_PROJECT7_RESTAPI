package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;
import java.util.Optional;

public interface ICurvePoint {

    public CurvePoint addNewCurvePointToDatabase(CurvePoint curvePoint);
    public List<CurvePoint> findAll();
    public CurvePoint findById(Integer id);
    public void updateCurvePoint(CurvePoint curvePoint);
    public void deleteCurvePoint(Integer id);
}
