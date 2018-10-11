package backend.manager;

import backend.dao.PersonDao;
import backend.model.Person;
import com.datastax.driver.core.Session;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by beku on 10/8/2018.
 */
@Service
public class RequestService {

    @Autowired
    private CassandraTemplate template;

    @Autowired
    private PersonDao personDao;

    public Collection<Person> getAllPeople(){
        return Lists.newArrayList(personDao.findAll());
    }

    public Person getPersonById(UUID id){
        Person tmp=personDao.findById(id).get();
        return tmp;
    }

    public void addPerson(Person person){
        person.setId(UUID.randomUUID());
        personDao.save(person);
    }

    public void updatePerson(Person person){
        template.insert(person);
    }

    public ArrayList<Person> findPersonByName(String name){
        return personDao.findByName(name);
    }
}
