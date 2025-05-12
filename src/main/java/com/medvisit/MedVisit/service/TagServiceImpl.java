package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.TagDto;
import com.medvisit.MedVisit.entity.Tag;
import com.medvisit.MedVisit.repository.TagRepository;
import com.medvisit.MedVisit.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public TagDto create(TagDto dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setDescription(dto.getDescription());
        tag.setCreatedAt(LocalDateTime.now());
        return toDto(tagRepository.save(tag));
    }

    @Override
    public TagDto getById(Long id) {
        return tagRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    @Override
    public TagDto getByName(String name) {
        return tagRepository.findByName(name)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    @Override
    public List<TagDto> getAll() {
        return tagRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }

    private TagDto toDto(Tag tag) {
        return new TagDto(
                tag.getTagId(),
                tag.getName(),
                tag.getDescription(),
                tag.getCreatedAt()
        );
    }
}
