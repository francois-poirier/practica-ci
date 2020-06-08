package es.codeurjc.daw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.daw.controller.dto.JobDto;
import es.codeurjc.daw.service.JobService;

@RestController
@RequestMapping("/jobs")
public class JobController extends CrudController<JobDto> {

    public JobController(JobService jobService) {
        super(jobService);
    }
}
