package com.magmutual.resource.service;


import com.magmutual.resource.model.SearchResourceRequestBody;
import com.magmutual.resource.repo.model.User;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ResourceService {

    public List<User> searchResources(SearchResourceRequestBody searchResourceRequestBody);
    public User getById(Long id) throws CsvValidationException, IOException;
}
