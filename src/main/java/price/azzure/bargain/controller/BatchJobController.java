package price.azzure.bargain.controller;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import price.azzure.bargain.entity.BatchJob;
import price.azzure.bargain.entity.JobDetail;
import price.azzure.bargain.entity.JobStatus;
import price.azzure.bargain.entity.JobType;
import price.azzure.bargain.repository.BatchJobRepository;
import price.azzure.bargain.repository.JobDetailsRepository;

import java.util.Date;
import java.util.Optional;

@Component
public class BatchJobController {

    private static final BatchJob BATCH_JOB = new BatchJob();
    private static final JobDetail JOB_DETAIL = new JobDetail();

    static {
        JOB_DETAIL.setCpuCount(2);
        JOB_DETAIL.setMemoryCount(3);
        JOB_DETAIL.setDiskCount(4);
        JOB_DETAIL.setType(JobType.HADOOP);

        BATCH_JOB.setStartTime(new Date());
        BATCH_JOB.setPrice(123.23);
        BATCH_JOB.setDeadline(new Date());
        BATCH_JOB.setSuggestDeadline(new Date());
        BATCH_JOB.setSuggestedPrice(122.8);
        BATCH_JOB.setStatus(JobStatus.SUBMITTED);
        BATCH_JOB.setDetail(JOB_DETAIL);
    }

    private BatchJobRepository batchJobRepository;
    private JobDetailsRepository jobDetailsRepository;

    public BatchJobController(@NonNull BatchJobRepository batchJobRepository,
                              @NonNull JobDetailsRepository jobDetailsRepository) {
        this.batchJobRepository = batchJobRepository;
        this.jobDetailsRepository = jobDetailsRepository;

        this.jobDetailsRepository.save(JOB_DETAIL);
        this.batchJobRepository.save(BATCH_JOB);
    }

    public BatchJob saveBatchJob(@NonNull BatchJob batchJob) {
        this.jobDetailsRepository.save(batchJob.getDetail());
        return this.batchJobRepository.save(batchJob);
    }

    public Optional<BatchJob> getBatchJobById(@NonNull Long id) {
        return this.batchJobRepository.findById(id);
    }
}
