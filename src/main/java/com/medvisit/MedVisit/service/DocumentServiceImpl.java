package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.DocumentDto;
import com.medvisit.MedVisit.entity.Document;
import com.medvisit.MedVisit.entity.User;
import com.medvisit.MedVisit.entity.Visit;
import com.medvisit.MedVisit.repository.DocumentRepository;
import com.medvisit.MedVisit.repository.UserRepository;
import com.medvisit.MedVisit.repository.VisitRepository;
import com.medvisit.MedVisit.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final VisitRepository visitRepository;
    private final UserRepository userRepository;

    @Override
    public DocumentDto create(DocumentDto dto) {
        Document document = new Document();
        copyDtoToEntity(dto, document);
        document.setCreatedAt(dto.getCreatedAt());
        return toDto(documentRepository.save(document));
    }

    @Override
    public DocumentDto getById(Long id) {
        return documentRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Document not found"));
    }

    @Override
    public List<DocumentDto> getAll() {
        return documentRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DocumentDto update(Long id, DocumentDto dto) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        copyDtoToEntity(dto, document);
        return toDto(documentRepository.save(document));
    }

    @Override
    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    private void copyDtoToEntity(DocumentDto dto, Document document) {
        document.setTitle(dto.getTitle());
        document.setContent(dto.getContent());

        Visit visit = visitRepository.findById(dto.getVisitId())
                .orElseThrow(() -> new RuntimeException("Visit not found"));
        document.setVisit(visit);

        User user = userRepository.findById(dto.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("User not found"));
        document.setCreatedBy(user);
    }

    private DocumentDto toDto(Document document) {
        return new DocumentDto(
                document.getDocumentId(),
                document.getTitle(),
                document.getContent(),
                document.getVisit().getVisitId(),
                document.getCreatedBy().getUserId(),
                document.getCreatedAt()
        );
    }
}
