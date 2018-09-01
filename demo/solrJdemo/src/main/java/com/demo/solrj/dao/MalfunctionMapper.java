package com.demo.solrj.dao;

import com.demo.solrj.domain.Malfunction;

public interface MalfunctionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Malfunction record);

    int insertSelective(Malfunction record);

    Malfunction selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Malfunction record);

    int updateByPrimaryKey(Malfunction record);
}