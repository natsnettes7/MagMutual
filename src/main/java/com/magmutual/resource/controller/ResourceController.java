package com.magmutual.resource.controller;

import com.magmutual.resource.model.SearchResourceRequestBody;
import com.magmutual.resource.repo.ResourceRepository;
import com.magmutual.resource.repo.model.User;
import com.magmutual.resource.service.ResourceService;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    ResourceRepository repository;
    @Autowired
    ResourceService resourceServiceImpl;

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getResourceById(@PathVariable("id") Long id) {
        LOGGER.info("Employee find: id={}", id);
        try {
            return new ResponseEntity<>(resourceServiceImpl.getById(id), HttpStatus.OK);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/searchbycreateddate")
    public ResponseEntity<List<User>> getResourcesByCreatedDateRange(@RequestParam(value="fromDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate, @RequestParam(value = "toDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate  toDate) {

        return new ResponseEntity<>(repository.getByCreatedDateRange(fromDate, toDate), HttpStatus.OK);
    }

    @GetMapping("/profession/{profession}")
    public ResponseEntity<List<User>> getResourcesByProfession(@PathVariable String profession) {
        LOGGER.info("Employee find: profession={}", profession);
        return new ResponseEntity<>(repository.getResourcesByProfession(profession), HttpStatus.OK);

    }


    @PostMapping ("/search")
    public ResponseEntity<List<User>> searchResources(@RequestBody SearchResourceRequestBody searchResourceRequestBody) {

        return new ResponseEntity<>(resourceServiceImpl.searchResources(searchResourceRequestBody), HttpStatus.OK);
    }
}
