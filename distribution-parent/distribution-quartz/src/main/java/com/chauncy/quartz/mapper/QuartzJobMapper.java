package com.chauncy.quartz.mapper;

import com.chauncy.quartz.domain.po.QuartzJobPO;

import java.util.List;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/26 17:53
 * @Version 1.0
 */
public interface QuartzJobMapper {

    /**
     * 查询调度任务集合
     *
     * @param job
     * @return
     */
    List<QuartzJobPO> selectJobList(QuartzJobPO job);

    /**
     * 查询所有调度任务
     *
     * @return
     */
    List<QuartzJobPO> selectJobAll();

    /**
     * 通过调度ID查询调度任务信息
     *
     * @param jobId
     * @return
     */
    QuartzJobPO selectJobById(Long jobId);

    /**
     * 通过调度ID删除调度任务信息
     *
     * @param jobId
     * @return
     */
    int deleteJobById(Long jobId);

    /**
     * 批量删除调度任务信息
     *
     * @param ids
     * @return
     */
//    int deleteJobLogByIds(Long[] ids);

    /**
     * 修改调度任务信息
     *
     * @param job
     * @return
     */
    int updateJob(QuartzJobPO job);

    /**
     * 新增调度任务信息
     *
     * @param job
     * @return
     */
    int insertJob(QuartzJobPO job);

}

