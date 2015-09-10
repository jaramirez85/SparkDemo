package edu.javier.bl;

import edu.javier.model.Person;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by JavierAlonso on 09/09/2015.
 */
public class PersonServiceImpl implements PersonService {

    private ConcurrentMap<Integer, Person> persons = new ConcurrentHashMap<>();
    private static final AtomicInteger idGenerator = new AtomicInteger(1);


    @Override
    public void create(Person p) {
        p.setId(idGenerator.getAndIncrement());
        persons.put(p.getId(), p);
    }

    @Override
    public void edit(Person p) {
        persons.putIfAbsent(p.getId(),p);
    }

    @Override
    public Optional<Person> findById(int id) {
        return Optional.ofNullable(persons.get(id));
    }

    @Override
    public List<Person> getAll() {
        return Collections.unmodifiableList(persons.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList()));
    }

    @Override
    public void delete(int id) {
        persons.remove(id);
    }
}
