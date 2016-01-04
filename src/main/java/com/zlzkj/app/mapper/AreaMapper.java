package com.zlzkj.app.mapper;

import com.zlzkj.app.model.Area;

public interface AreaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Area record);

    Area selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Area record);
}