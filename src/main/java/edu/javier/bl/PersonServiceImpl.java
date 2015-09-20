package edu.javier.bl;

import edu.javier.model.Person;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by JavierAlonso on 09/09/2015.
 */
public class PersonServiceImpl implements PersonService {

    private Map<UUID, Person> persons = Collections.synchronizedMap(new LinkedHashMap<>());


    @Override
    public UUID create(Person p) {
        UUID id = UUID.randomUUID();
        p.setId(id);
        persons.put(p.getId(), p);
        return id;
    }

    @Override
    public void edit(Person p) {
        persons.putIfAbsent(p.getId(),p);
    }

    @Override
    public Optional<Person> findById(UUID id) {
        return Optional.ofNullable(persons.get(id));
    }

    @Override
    public List<Person> getAll() {
        return Collections.unmodifiableList(persons.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList()));
    }

    @Override
    public void delete(UUID id) {
        persons.remove(id);
    }
}
