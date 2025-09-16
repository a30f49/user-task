package com.jfeat.am.module.task.services.domain.dao;

import com.jfeat.am.module.task.services.persistence.model.TaskFollower;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * Created by Code Generator on 2017-11-21
 */
public interface QueryTaskFollowerDao extends BaseMapper<TaskFollower> {
    List<TaskFollower> findTaskfollowerPage(Page<TaskFollower> page, TaskFollower taskFollower);
}