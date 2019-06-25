//package com.chauncy.web.controller.quartz;
//
//import com.chauncy.data.domain.po.quartz.QuartzJobPO;
//import com.chauncy.quartz.com.chauncy.user.service.QuartzJobService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * @Author huangwancheng
// * @create 2019-05-17 09:42
// */
//@RestController
//@RequestMapping("/quartz")
//public class QuartzController {
//
//    private static final Logger log = LoggerFactory.getLogger(QuartzController.class);
//
//    @Autowired
//    private QuartzJobService jobTaskService;
//
//    /**
//     * 获取定时任务列表
//     *
//     * @return
//     */
//    @PostMapping("/list")
//    public List<QuartzJobPO> list() {
//        List<QuartzJobPO> list = jobTaskService.selectJobList(null);
//        return list;
//    }
//
//    /**
//     * 删除定时任务(批量)
//     *
//     * @param ids
//     * @return
//     */
//    @PostMapping("/remove")
//    public void remove(String ids) {
//        try {
//            jobTaskService.deleteJobByIds(ids);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("失败：" + e.getMessage());
//        }
//    }
//
//    /**
//     * 定时任务详情
//     *
//     * @param jobId
//     * @param mmap
//     * @return
//     */
//    @GetMapping("/detail/{jobId}")
//    public String detail(@PathVariable("jobId") Long jobId, ModelMap mmap) {
//        // 待优化逻辑代码
//        mmap.put("name", "job");
//        mmap.put("job", jobTaskService.selectJobById(jobId));
//        return null;
//    }
//
//    /**
//     * 任务调度立即执行一次
//     */
//    @PostMapping("/run")
//    public void run(QuartzJobPO job) {
//        jobTaskService.run(job);
//    }
//
//}
//
//
