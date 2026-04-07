package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.MedicalQuestionnaireDto;
import java.util.List;

public interface MedicalQuestionnaireService {
    MedicalQuestionnaireDto create(MedicalQuestionnaireDto dto);
    MedicalQuestionnaireDto update(Long id, MedicalQuestionnaireDto dto);
    MedicalQuestionnaireDto getById(Long id);
    MedicalQuestionnaireDto getByClientId(Long clientId);
    List<MedicalQuestionnaireDto> getAll();
    void delete(Long id);
}
