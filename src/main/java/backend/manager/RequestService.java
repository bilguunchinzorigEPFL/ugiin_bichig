package backend.manager;

import backend.dao.PersonDao;
import backend.entities.Person;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by beku on 10/8/2018.
 */
@Service
@CacheConfig(cacheNames = "sexy")
public class RequestService {
    @Autowired
    private CassandraTemplate template;
    @Autowired
    private PersonDao personDao;

    @Cacheable()
    public Collection<Person> getAllPeople(){
        return Lists.newArrayList(personDao.findAll());
    }

    public Person getPersonById(UUID id){
        Person tmp=personDao.findById(id).get();
        return tmp;
    }

    public void addPerson(Person person){
        person.id=UUID.randomUUID();
        personDao.save(person);
    }

    public void updatePerson(Person person){
        template.insert(person);
    }

    public ArrayList<Person> findPersonByName(String name){
        return personDao.findByName(name);
    }
}
