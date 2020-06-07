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
@ApiModel(value = "Department")
public class EmployeeDto extends BaseDto {
	
    @ApiModelProperty(value = "firstName")
	private String firstName;
    @ApiModelProperty(value = "lastName")
	private String lastName;
    @ApiModelProperty(value = "salary")
	private Long salary;
}
