package edu.javier.bl;

import edu.javier.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by JavierAlonso on 09/09/2015.
 */
public interface PersonService {

    void create(Person p);
    void edit(Person p);
    Optional<Person> findById(int id);
    List<Person> getAll();
    void delete(int id);

}
