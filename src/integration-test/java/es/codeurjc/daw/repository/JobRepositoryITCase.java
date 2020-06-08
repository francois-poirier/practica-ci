package es.codeurjc.daw.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es.codeurjc.daw.entity.Job;

@DataJpaTest
@DisplayName("Integration Tests of JobRepository with H2 Database")
public class JobRepositoryITCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Get a Job by Id")
    public void givenSingleJob_whenFindById_thenGetJob() {
        //given

        entityManager.persist(singleJob(11L));
        entityManager.flush();
        Job savedJob = getJobResultListSavedInDb().get(0);

        //when
        Optional<Job> optionalJob = jobRepository.findById(savedJob.getId());

        //then
        assertFalse(!optionalJob.isPresent());
        assertEquals("Job title 11", optionalJob.get().getJobTitle());
    }

    @Test
    @DisplayName("Get a list with 3 Jobs")
    public void given3Jobs_whenFindAll_thenGetJobs() {
        //given
        entityManager.persist(singleJob(1L));
        entityManager.persist(singleJob(2L));
        entityManager.persist(singleJob(3L));
        entityManager.flush();

        //when
        Iterable<Job> jobIterable = jobRepository.findAll();

        //then
        List<Job> jobList =
                StreamSupport.stream(jobIterable.spliterator(), false)
                        .collect(Collectors.toList());

        assertFalse(jobList.isEmpty());
        assertEquals(3, jobList.size());
    }

    @Test
    @DisplayName("Get a Job by Id when 2 Jobs are in database")
    public void given2Jobs_whenFindById_thenGetSingleJob() {
        //given
        entityManager.persist(singleJob(1L));
        entityManager.persist(singleJob(2L));
        entityManager.flush();

        Job savedJob = getJobResultListSavedInDb().get(0);

        //when
        Optional<Job> optJob = jobRepository.findById(savedJob.getId());

        //then
        assertTrue(optJob.isPresent());
        assertNotNull(optJob.get().getId());
    }

    @Test
    @DisplayName("Save a Job")
    public void givenSingleJob_whenSave_thenJobIsSaved() {
        //given
        Job job = singleJob(1L);

        //when
        jobRepository.save(job);

        //then
        Job savedJob = getJobResultListSavedInDb().get(0);
        assertNotNull(savedJob.getId());
        assertEquals("Job title 1", savedJob.getJobTitle());
    }

    @Test
    @DisplayName("Delete Job by Id")
    public void given2SavedJobs_whenDeleteById_thenOnlyOneJobInDb() {
        //given
        entityManager.persist(singleJob(1L));
        entityManager.persist(singleJob(2L));
        entityManager.flush();

        Job deletedJob = getJobResultListSavedInDb().get(0);

        //when
        jobRepository.deleteById(deletedJob.getId());

        //then
        List<Job> jobList = getJobResultListSavedInDb();

        assertEquals(1, jobList.size());
        assertFalse(deletedJob.getId().equals(jobList.get(0).getId()));
    }

    private Job singleJob(Long number){
        return Job.builder()
                .jobTitle("Job title " + number)
                .maxSalary(2000*number)
                .minSalary(1000*number)
                .build();
    }

    private List<Job> getJobResultListSavedInDb() {
        return entityManager
                .createNativeQuery("SELECT Job.* FROM Job", Job.class)
                .getResultList();
    }
}
