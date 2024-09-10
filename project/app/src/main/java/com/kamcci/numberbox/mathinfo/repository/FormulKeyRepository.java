package com.kamcci.numberbox.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamcci.numberbox.mathinfo.entity.FormulKey;

public interface FormulKeyRepository extends JpaRepository<FormulKey, Integer> {

	List<FormulKey> findAllByOrderByFormulOrderAscIdAsc();

	List<FormulKey> findByClassification(String classification);
}
