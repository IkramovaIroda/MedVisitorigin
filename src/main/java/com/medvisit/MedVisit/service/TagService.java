package com.medvisit.MedVisit.service;

import com.medvisit.MedVisit.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto create(TagDto dto);
    TagDto getById(Long id);
    TagDto getByName(String name);
    List<TagDto> getAll();
    void delete(Long id);
}
