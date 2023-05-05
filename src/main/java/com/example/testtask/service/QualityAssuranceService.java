package com.example.testtask.service;

import com.example.testtask.dto.QualityAssuranceDto;

import java.util.List;

public interface QualityAssuranceService {

    List<QualityAssuranceDto> getAllQualityAssurances(int page, int size);

    QualityAssuranceDto addQualityAssurance(QualityAssuranceDto qualityAssuranceDto);

    String deleteQualityAssuranceById(long qualityAssuranceId);

    QualityAssuranceDto updateQualityAssurance(long developerId, QualityAssuranceDto qualityAssuranceDto);
}
