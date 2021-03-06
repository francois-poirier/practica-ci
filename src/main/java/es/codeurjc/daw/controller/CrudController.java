package es.codeurjc.daw.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.codeurjc.daw.controller.dto.BaseDto;
import es.codeurjc.daw.service.CrudService;
import io.swagger.annotations.ApiOperation;

public abstract class CrudController<T extends BaseDto> {

    private CrudService<T> service;

    public CrudController(CrudService<T> crudService){
        this.service = crudService;
    }

    @GetMapping("/")
    @ApiOperation(value = "List all")
    public ResponseEntity<List<T>> getAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get by Id")
    public ResponseEntity<T> getById(@PathVariable Long id){
        Optional<T> optionalT = service.findById(id);

        return optionalT.map(T ->
                new ResponseEntity<>(T, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    @ApiOperation(value = "Create a new one")
    public ResponseEntity<T> save(@RequestBody T body){
        return new ResponseEntity<>(service.save(body), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete by Id")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<T> optional = service.findById(id);

        return optional.map(T ->
                new ResponseEntity<>("Object with the id " + id + " was deleted.", HttpStatus.NO_CONTENT))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update by Id")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody T body){
        Optional<T> optional = service.findById(id);
        optional.ifPresent(n -> service.update(id, body));

        return optional.map(n ->
                new ResponseEntity<>("Object with id " + id + " was updated.", HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND));
    }
}
