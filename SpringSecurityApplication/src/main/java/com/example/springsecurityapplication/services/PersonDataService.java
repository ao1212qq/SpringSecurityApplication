package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonDataService {
    private final PersonRepository personDataRepository;

    public PersonDataService(PersonRepository personDataRepository) {
        this.personDataRepository = personDataRepository;
    }

    // Данный метод позволяет получить список всех товаров
    public List<Person> getAllPerson(){
        return personDataRepository.findAll();
    }




    public Person getPersonId(int id){
        Optional<Person> optionalPerson = personDataRepository.findById(id);
        return optionalPerson.orElse(null);
    }


}
