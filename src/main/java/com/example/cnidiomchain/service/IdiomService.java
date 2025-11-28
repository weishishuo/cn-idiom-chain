package com.example.cnidiomchain.service;

import com.example.cnidiomchain.dto.IdiomDTO;
import com.example.cnidiomchain.entity.Idiom;

import java.util.List;
import java.util.Optional;

public interface IdiomService {

    // 获取所有成语
    List<Idiom> getAllIdioms();

    // 获取所有成语DTO
    List<IdiomDTO> getAllIdiomDTOs();

    // 根据名称获取成语
    Optional<Idiom> getIdiomByName(String name);

    // 根据名称获取成语DTO
    Optional<IdiomDTO> getIdiomDTOByName(String name);

    // 检查成语是否存在
    boolean existsIdiomByName(String name);

    // 根据最后一个字获取成语列表（用于成语接龙）
    List<Idiom> getIdiomsByLastChar(char lastChar);

    // 根据最后一个字获取成语DTO列表（用于成语接龙）
    List<IdiomDTO> getIdiomDTOsByLastChar(char lastChar);

    // 根据第一个字获取成语列表
    List<Idiom> getIdiomsByFirstChar(char firstChar);

    // 根据第一个字获取成语DTO列表
    List<IdiomDTO> getIdiomDTOsByFirstChar(char firstChar);

    // 成语接龙：根据输入成语获取下一个成语
    Optional<Idiom> getNextIdiom(String currentIdiom);

    // 成语接龙：根据输入成语获取下一个成语DTO
    Optional<IdiomDTO> getNextIdiomDTO(String currentIdiom);

    // 批量添加成语
    List<Idiom> addIdioms(List<Idiom> idioms);

    // 批量添加成语DTO
    List<IdiomDTO> addIdiomDTOs(List<IdiomDTO> idiomDTOs);

    // 将成语实体转换为DTO
    IdiomDTO convertToDTO(Idiom idiom);

    // 将成语DTO转换为实体
    Idiom convertToEntity(IdiomDTO idiomDTO);
}
