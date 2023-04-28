package com.magmutual.resource.service.Impl;


import com.magmutual.resource.expeption.InvalidRequestException;
import com.magmutual.resource.model.SearchResourceRequestBody;
import com.magmutual.resource.repo.ResourceRepository;
import com.magmutual.resource.repo.model.User;
import com.magmutual.resource.service.ResourceService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    ResourceRepository repository;

//private static final String CSV_FILE_PATH = "/Users/natsnetg/Desktop/MagMutual/UserInformation.csv";
    List<User> users = new ArrayList<>();
    @Override

    public List<User> searchResources(SearchResourceRequestBody searchResourceRequestBody) {
        List<User> users = new ArrayList<>();
        try {
            if (searchResourceRequestBody.getId() != null) {
               users.add(repository.getById(searchResourceRequestBody.getId()));
            }
            else if(searchResourceRequestBody.getEmail() != null){
                users.addAll(repository.getResourceByEmail(searchResourceRequestBody.getEmail()));
            }
            else if(searchResourceRequestBody.getProfession() != null){
                users.addAll(repository.getResourcesByProfession(searchResourceRequestBody.getProfession()));
            }
            else if(searchResourceRequestBody.getFirstName() != null){
                users.addAll(repository.getResourceByFirstName(searchResourceRequestBody.getFirstName()));
            }

            else if(searchResourceRequestBody.getLastName()!= null){
                users.addAll(repository.getResourceByLastName(searchResourceRequestBody.getLastName()));
            }else{
                throw new InvalidRequestException("input value fields are not valid ");
            }

        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User getById(Long id) {

        try {
            return repository.getById(id);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
