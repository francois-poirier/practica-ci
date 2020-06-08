package es.codeurjc.daw.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Job")
public class JobDto extends BaseDto {

    @ApiModelProperty(value = "jobTitle")
	private String jobTitle;
    @ApiModelProperty(value = "minSalary")
    private Long minSalary;
    @ApiModelProperty(value = "maxSalary")
	private Long maxSalary;
}
