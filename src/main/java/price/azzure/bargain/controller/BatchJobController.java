package price.azzure.bargain.controller;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import price.azzure.bargain.entity.BatchJob;
import price.azzure.bargain.repository.BatchJobRepository;
import price.azzure.bargain.repository.JobDetailsRepository;

import java.util.Optional;

@Component
public class BatchJobController {

    private BatchJobRepository batchJobRepository;
    private JobDetailsRepository jobDetailsRepository;

    public BatchJobController(@NonNull BatchJobRepository batchJobRepository,
                              @NonNull JobDetailsRepository jobDetailsRepository) {
        this.batchJobRepository = batchJobRepository;
        this.jobDetailsRepository = jobDetailsRepository;
    }

    public BatchJob saveBatchJob(@NonNull BatchJob batchJob) {
        this.jobDetailsRepository.save(batchJob.getDetail());
        return this.batchJobRepository.save(batchJob);
    }

    public Optional<BatchJob> getBatchJobById(@NonNull Long id) {
        return this.batchJobRepository.findById(id);
    }
}
