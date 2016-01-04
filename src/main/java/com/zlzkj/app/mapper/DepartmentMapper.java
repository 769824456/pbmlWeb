package com.zlzkj.app.mapper;

import com.zlzkj.app.model.Department;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Department record);
}