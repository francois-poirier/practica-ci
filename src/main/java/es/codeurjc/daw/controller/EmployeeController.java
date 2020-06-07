package es.codeurjc.daw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.daw.controller.dto.EmployeeDto;
import es.codeurjc.daw.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController extends CrudController<EmployeeDto> {

    public EmployeeController(EmployeeService employeeService) {
        super(employeeService);
    }
}
