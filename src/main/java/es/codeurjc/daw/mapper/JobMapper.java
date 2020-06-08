package es.codeurjc.daw.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import es.codeurjc.daw.controller.dto.JobDto;
import es.codeurjc.daw.entity.Job;

@Mapper
public interface JobMapper {

    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    JobDto jobToDto(Job job);
    Job dtoToJob(JobDto jobDto);
}
