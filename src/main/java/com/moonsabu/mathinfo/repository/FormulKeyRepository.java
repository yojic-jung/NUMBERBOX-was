package com.moonsabu.mathinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moonsabu.mathinfo.entity.FormulKey;

public interface FormulKeyRepository  extends JpaRepository <FormulKey, Integer> {

	List<FormulKey> findByClassification(String classification);
}	
