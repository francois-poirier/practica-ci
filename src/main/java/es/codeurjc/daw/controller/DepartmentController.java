package es.codeurjc.daw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.daw.controller.dto.DepartmentDto;
import es.codeurjc.daw.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentController extends CrudController<DepartmentDto> {

    public DepartmentController(DepartmentService departmentService) {
        super(departmentService);
    }
}
