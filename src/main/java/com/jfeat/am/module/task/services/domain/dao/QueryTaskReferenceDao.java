package com.jfeat.am.module.task.services.domain.dao;

import com.jfeat.am.module.task.services.persistence.model.TaskReference;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * Created by Code Generator on 2017-11-21
 */
public interface QueryTaskReferenceDao extends BaseMapper<TaskReference> {
    List<TaskReference> findTaskreferencePage(Page<TaskReference> page, TaskReference taskreference);
}