package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

    List<CurvePoint> findAll();
    Optional<CurvePoint> findById(Integer id);
}
