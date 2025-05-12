package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.TagDto;
import com.medvisit.MedVisit.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public TagDto create(@RequestBody TagDto dto) {
        return tagService.create(dto);
    }

    @GetMapping("/{id}")
    public TagDto getById(@PathVariable Long id) {
        return tagService.getById(id);
    }

    @GetMapping("/name/{name}")
    public TagDto getByName(@PathVariable String name) {
        return tagService.getByName(name);
    }

    @GetMapping
    public List<TagDto> getAll() {
        return tagService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        tagService.delete(id);
    }
}
