package com.magmutual.resource.repo;

import com.magmutual.resource.expeption.InvalidRequestException;
import com.magmutual.resource.expeption.UserNotFoundException;
import com.magmutual.resource.repo.model.User;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;


import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ResourceRepository {

    private static final String CSV_FILE_PATH = "src/main/resources/data/UserInformation.csv";


    private static List<User> users = createResource();

    private static List<User> createResource() {
        List<User> users = new ArrayList<>();
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(String.valueOf(CSV_FILE_PATH)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        String[] nextRecord;
        while (true) {
            try {
                if (!((nextRecord = csvReader.readNext()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (CsvValidationException e) {
                throw new RuntimeException(e);
            }
            User user = new User();
            user.setId((long) Integer.parseInt(nextRecord[0]));
            user.setFirstName(nextRecord[1]);
            user.setLastName(nextRecord[2]);
            user.setEmail(nextRecord[3]);
            user.setProfession(nextRecord[4]);
            user.setDateCreated(LocalDate.parse(nextRecord[5]));
            user.setCountry(nextRecord[6]);
            user.setCity(nextRecord[7]);


            users.add(user);
        }
        return users;

    }

    public User getById(Long id) throws CsvValidationException, IOException {

        Optional<List<User>> resources = Optional.ofNullable(users);
        Optional<User> resource = resources.get().stream().filter(a -> a.getId().equals(id)).findFirst();
        if (resource.isPresent()) {
            return resource.get();
        } else {
            throw new UserNotFoundException("user with Id:" + id + "does not exist");
        }
    }

    public List<User> getByCreatedDateRange(LocalDate fromDate, LocalDate toDate) {
        List<User> userList;

            userList = users.stream()
                    .filter(user -> user.getDateCreated().isAfter(null != fromDate ? fromDate.minusDays(1) : LocalDate.MIN)
                            && user.getDateCreated().isBefore(null != toDate ? toDate.plusDays(1) : LocalDate.MAX))
                    .collect(Collectors.toList());
            if(null==userList || userList.size()<1){
                throw new UserNotFoundException("user with Date:" + fromDate + "does not exist");
            }

        return userList;

    }

    public List<User> getResourcesByProfession(String profession) {
        List<User> userList;
        if (profession != null && !StringUtils.isEmptyOrWhitespace(profession)) {


            userList= users.stream()
                    .filter(user -> user.getProfession().equalsIgnoreCase(profession))
                    .collect(Collectors.toList());

            if(null==userList || userList.size()<1){
                throw new UserNotFoundException("user with profession:" + profession + "does not exist");
            }
        }

        else {
            throw new InvalidRequestException("in valid profession filed");
        }

        return userList;
    }

    public List<User> getResourceByEmail(String email) {
        List<User> userList;
        if (email != null && !StringUtils.isEmptyOrWhitespace(email)) {
            userList= users.stream()
                    .filter(user -> user.getEmail().equalsIgnoreCase(email))
                    .collect(Collectors.toList());
            if(null==userList || userList.size()<1){
                throw new UserNotFoundException("user with email:" + email + "does not exist");
            }
        } else {
            throw new InvalidRequestException("in valid email filed");

        }
        return userList;
    }

    public List<User> getResourceByFirstName(String fName) {

        return users.stream()
                .filter(user -> user.getFirstName().equalsIgnoreCase(fName))
                .collect(Collectors.toList());
    }

    public List<User> getResourceByLastName(String lName) {

        return users.stream()
                .filter(user -> user.getLastName().equalsIgnoreCase(lName))
                .collect(Collectors.toList());
    }
}
