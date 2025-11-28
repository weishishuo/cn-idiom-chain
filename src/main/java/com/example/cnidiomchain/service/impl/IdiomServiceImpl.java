package com.example.cnidiomchain.service.impl;

import com.example.cnidiomchain.dto.IdiomDTO;
import com.example.cnidiomchain.entity.Idiom;
import com.example.cnidiomchain.repository.IdiomRepository;
import com.example.cnidiomchain.service.IdiomService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class IdiomServiceImpl implements IdiomService {

    private final IdiomRepository idiomRepository;
    private final Random random = new Random();

    public IdiomServiceImpl(IdiomRepository idiomRepository) {
        this.idiomRepository = idiomRepository;
    }

    @Override
    public List<Idiom> getAllIdioms() {
        return idiomRepository.findAll();
    }

    @Override
    public List<IdiomDTO> getAllIdiomDTOs() {
        return getAllIdioms().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Idiom> getIdiomByName(String name) {
        return idiomRepository.findByName(name);
    }

    @Override
    public Optional<IdiomDTO> getIdiomDTOByName(String name) {
        return getIdiomByName(name)
                .map(this::convertToDTO);
    }

    @Override
    public boolean existsIdiomByName(String name) {
        return idiomRepository.existsByName(name);
    }

    @Override
    public List<Idiom> getIdiomsByLastChar(char lastChar) {
        return idiomRepository.findByLastChar(lastChar);
    }

    @Override
    public List<IdiomDTO> getIdiomDTOsByLastChar(char lastChar) {
        return getIdiomsByLastChar(lastChar).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Idiom> getIdiomsByFirstChar(char firstChar) {
        return idiomRepository.findByFirstChar(firstChar);
    }

    @Override
    public List<IdiomDTO> getIdiomDTOsByFirstChar(char firstChar) {
        return getIdiomsByFirstChar(firstChar).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Idiom> getNextIdiom(String currentIdiom) {
        // 验证输入成语是否存在
        Optional<Idiom> current = idiomRepository.findByName(currentIdiom);
        if (!current.isPresent()) {
            return Optional.empty();
        }

        // 获取输入成语的最后一个字
        char lastChar = current.get().getLastChar();

        // 查询以该字开头的所有成语
        List<Idiom> nextIdioms = idiomRepository.findByFirstChar(lastChar);

        // 如果没有找到下一个成语，返回空
        if (nextIdioms.isEmpty()) {
            return Optional.empty();
        }

        // 随机选择一个成语作为下一个接龙
        Idiom nextIdiom = nextIdioms.get(random.nextInt(nextIdioms.size()));

        return Optional.of(nextIdiom);
    }

    @Override
    public Optional<IdiomDTO> getNextIdiomDTO(String currentIdiom) {
        return getNextIdiom(currentIdiom)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public List<Idiom> addIdioms(List<Idiom> idioms) {
        // 过滤掉已存在的成语
        List<Idiom> newIdioms = idioms.stream()
                .filter(idiom -> !idiomRepository.existsByName(idiom.getName()))
                .toList();

        // 保存新成语
        return idiomRepository.saveAll(newIdioms);
    }

    @Override
    @Transactional
    public List<IdiomDTO> addIdiomDTOs(List<IdiomDTO> idiomDTOs) {
        // 将DTO转换为实体
        List<Idiom> idioms = idiomDTOs.stream()
                .map(this::convertToEntity)
                .toList();

        // 添加成语
        List<Idiom> newIdioms = addIdioms(idioms);

        // 将实体转换为DTO并返回
        return newIdioms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public IdiomDTO convertToDTO(Idiom idiom) {
        IdiomDTO idiomDTO = new IdiomDTO();
        BeanUtils.copyProperties(idiom, idiomDTO);
        return idiomDTO;
    }

    @Override
    public Idiom convertToEntity(IdiomDTO idiomDTO) {
        Idiom idiom = new Idiom();
        BeanUtils.copyProperties(idiomDTO, idiom);
        return idiom;
    }
}
