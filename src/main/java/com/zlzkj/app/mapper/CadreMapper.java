package com.zlzkj.app.mapper;

import com.zlzkj.app.model.Cadre;

public interface CadreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cadre record);

    Cadre selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Cadre record);
}