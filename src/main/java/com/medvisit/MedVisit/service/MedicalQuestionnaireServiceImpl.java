package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.MedicalQuestionnaireDto;
import com.medvisit.MedVisit.entity.Client;
import com.medvisit.MedVisit.entity.MedicalQuestionnaire;
import com.medvisit.MedVisit.repository.ClientRepository;
import com.medvisit.MedVisit.repository.MedicalQuestionnaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalQuestionnaireServiceImpl implements MedicalQuestionnaireService {

    private final MedicalQuestionnaireRepository questionnaireRepository;
    private final ClientRepository clientRepository;

    @Override
    public MedicalQuestionnaireDto create(MedicalQuestionnaireDto dto) {
        MedicalQuestionnaire questionnaire = new MedicalQuestionnaire();
        copyDtoToEntity(dto, questionnaire);
        questionnaire.setCreatedAt(LocalDateTime.now());
        questionnaire.setUpdatedAt(LocalDateTime.now());
        return toDto(questionnaireRepository.save(questionnaire));
    }

    @Override
    public MedicalQuestionnaireDto update(Long id, MedicalQuestionnaireDto dto) {
        MedicalQuestionnaire questionnaire = questionnaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Questionnaire not found"));
        copyDtoToEntity(dto, questionnaire);
        questionnaire.setUpdatedAt(LocalDateTime.now());
        return toDto(questionnaireRepository.save(questionnaire));
    }

    @Override
    public MedicalQuestionnaireDto getById(Long id) {
        return questionnaireRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Questionnaire not found"));
    }

    @Override
    public MedicalQuestionnaireDto getByClientId(Long clientId) {
        return questionnaireRepository.findByClientClientId(clientId)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public List<MedicalQuestionnaireDto> getAll() {
        return questionnaireRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        questionnaireRepository.deleteById(id);
    }

    private void copyDtoToEntity(MedicalQuestionnaireDto dto, MedicalQuestionnaire questionnaire) {
        questionnaire.setFullName(dto.getFullName());
        questionnaire.setDateOfBirth(dto.getDateOfBirth());
        questionnaire.setGender(dto.getGender());
        questionnaire.setPhone(dto.getPhone());
        questionnaire.setEmail(dto.getEmail());
        questionnaire.setAddress(dto.getAddress());
        questionnaire.setOccupation(dto.getOccupation());
        questionnaire.setMaritalStatus(dto.getMaritalStatus());
        
        questionnaire.setEmergencyContactName(dto.getEmergencyContactName());
        questionnaire.setEmergencyContactPhone(dto.getEmergencyContactPhone());
        questionnaire.setEmergencyContactRelation(dto.getEmergencyContactRelation());
        
        questionnaire.setChronicDiseases(dto.getChronicDiseases());
        questionnaire.setPreviousSurgeries(dto.getPreviousSurgeries());
        questionnaire.setAllergies(dto.getAllergies());
        questionnaire.setCurrentMedications(dto.getCurrentMedications());
        questionnaire.setFamilyMedicalHistory(dto.getFamilyMedicalHistory());
        
        questionnaire.setSmokes(dto.getSmokes());
        questionnaire.setDrinksAlcohol(dto.getDrinksAlcohol());
        questionnaire.setAlcoholFrequency(dto.getAlcoholFrequency());
        questionnaire.setExercises(dto.getExercises());
        questionnaire.setExerciseFrequency(dto.getExerciseFrequency());
        questionnaire.setDietType(dto.getDietType());
        
        questionnaire.setCurrentSymptoms(dto.getCurrentSymptoms());
        questionnaire.setMainComplaints(dto.getMainComplaints());
        questionnaire.setHeight(dto.getHeight());
        questionnaire.setWeight(dto.getWeight());
        questionnaire.setBloodType(dto.getBloodType());
        
        questionnaire.setInsuranceProvider(dto.getInsuranceProvider());
        questionnaire.setInsuranceNumber(dto.getInsuranceNumber());
        questionnaire.setInsuranceExpiryDate(dto.getInsuranceExpiryDate());
        
        questionnaire.setAdditionalNotes(dto.getAdditionalNotes());
        questionnaire.setLastUpdatedBy(dto.getLastUpdatedBy());
        
        if (dto.getClientId() != null && questionnaire.getClient() == null) {
            Client client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found"));
            questionnaire.setClient(client);
        }
    }

    private MedicalQuestionnaireDto toDto(MedicalQuestionnaire questionnaire) {
        MedicalQuestionnaireDto dto = new MedicalQuestionnaireDto();
        dto.setQuestionnaireId(questionnaire.getQuestionnaireId());
        dto.setClientId(questionnaire.getClient().getClientId());
        dto.setFullName(questionnaire.getFullName());
        dto.setDateOfBirth(questionnaire.getDateOfBirth());
        dto.setGender(questionnaire.getGender());
        dto.setPhone(questionnaire.getPhone());
        dto.setEmail(questionnaire.getEmail());
        dto.setAddress(questionnaire.getAddress());
        dto.setOccupation(questionnaire.getOccupation());
        dto.setMaritalStatus(questionnaire.getMaritalStatus());
        
        dto.setEmergencyContactName(questionnaire.getEmergencyContactName());
        dto.setEmergencyContactPhone(questionnaire.getEmergencyContactPhone());
        dto.setEmergencyContactRelation(questionnaire.getEmergencyContactRelation());
        
        dto.setChronicDiseases(questionnaire.getChronicDiseases());
        dto.setPreviousSurgeries(questionnaire.getPreviousSurgeries());
        dto.setAllergies(questionnaire.getAllergies());
        dto.setCurrentMedications(questionnaire.getCurrentMedications());
        dto.setFamilyMedicalHistory(questionnaire.getFamilyMedicalHistory());
        
        dto.setSmokes(questionnaire.getSmokes());
        dto.setDrinksAlcohol(questionnaire.getDrinksAlcohol());
        dto.setAlcoholFrequency(questionnaire.getAlcoholFrequency());
        dto.setExercises(questionnaire.getExercises());
        dto.setExerciseFrequency(questionnaire.getExerciseFrequency());
        dto.setDietType(questionnaire.getDietType());
        
        dto.setCurrentSymptoms(questionnaire.getCurrentSymptoms());
        dto.setMainComplaints(questionnaire.getMainComplaints());
        dto.setHeight(questionnaire.getHeight());
        dto.setWeight(questionnaire.getWeight());
        dto.setBloodType(questionnaire.getBloodType());
        
        dto.setInsuranceProvider(questionnaire.getInsuranceProvider());
        dto.setInsuranceNumber(questionnaire.getInsuranceNumber());
        dto.setInsuranceExpiryDate(questionnaire.getInsuranceExpiryDate());
        
        dto.setAdditionalNotes(questionnaire.getAdditionalNotes());
        dto.setCreatedAt(questionnaire.getCreatedAt());
        dto.setUpdatedAt(questionnaire.getUpdatedAt());
        dto.setLastUpdatedBy(questionnaire.getLastUpdatedBy());
        
        return dto;
    }
}
