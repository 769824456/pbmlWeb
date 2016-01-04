package com.zlzkj.app.mapper;

import com.zlzkj.app.model.Duties;

public interface DutiesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Duties record);

    Duties selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Duties record);
}