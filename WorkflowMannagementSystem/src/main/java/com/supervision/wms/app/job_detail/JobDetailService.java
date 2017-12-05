/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.app.job_detail;

import com.supervision.wms.app.job.JobRepository;
import com.supervision.wms.app.job.JobService;
import com.supervision.wms.app.job.model.Job;
import com.supervision.wms.app.job_detail.model.JobDetail;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Thilina Kalum
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class JobDetailService {

    @Autowired
    private JobDetailRepository jobDetailRepository;

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobService jobService;

    public List<JobDetail> getAllJobDetails() {
        return jobDetailRepository.findAll();
    }

    public List<JobDetail> getAllJobsDetailByjob(int indexNo) {
        return jobDetailRepository.findByJob(indexNo);
    }

    public JobDetail saveJobDetail(JobDetail jobDetail) {
        JobDetail jobdetail = jobDetailRepository.save(jobDetail);
        Job job = jobRepository.findOne(jobDetail.getJob());
        List<JobDetail> jobDetailList = jobDetailRepository.getAllJobsByJobNoAndStatus(jobDetail.getJob());
        System.out.println(jobDetailList.size());
        if (jobDetailList.isEmpty()) {
            System.out.println("++++++++++++++++++++++++++");
            System.out.println("EMTY");
            System.out.println("++++++++++++++++++++++++++");
            job.setStatus("FINISH");
            job.setUser(1);
            jobService.saveJobs(job);
        }
        return jobdetail;
    }

    public List<JobDetail> getAllJobsDetailByEmployee(int user) {
        return jobDetailRepository.getAllJobsDetailByEmployeeAndNew(user);
    }

    public List<JobDetail> getAllJobsDetailByEmployeeAndRunning(int user) {
        return jobDetailRepository.getAllJobsDetailByEmployeeAndRunning(user);
    }

    public List<JobDetail> getAllJobsDetailByEmployeeAndFinish(int user) {
        return jobDetailRepository.getAllJobsDetailByEmployeeAndFinish(user);
    }

    public void deleteJobDetail(Integer indexNo) {
        jobDetailRepository.delete(indexNo);
    }

}
