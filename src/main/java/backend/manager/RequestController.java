package backend.manager;

import backend.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by beku on 10/8/2018.
 */
@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;

    @RequestMapping(value="/data",method=RequestMethod.GET)
    public Collection<Person> getAllPeople(){
        return requestService.getAllPeople();
    }

    @RequestMapping(value="/data/{id}",method = RequestMethod.GET)
    public Person getPersonById(@PathVariable("id") String id){
        return requestService.getPersonById(UUID.fromString(id));
    }

    @RequestMapping(value="/data/name/{name}",method = RequestMethod.GET)
    public ArrayList<Person> getPersonByName(@PathVariable("name") String name){
        return requestService.findPersonByName(name);
    }

    @RequestMapping(value="/data",method=RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addPerson(@RequestBody Person person){
        requestService.addPerson(person);
    }

    @RequestMapping(value="/data/{id}",method=RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updatePerson(@PathVariable("id") String id,@RequestBody Person person){
        person.setId(UUID.fromString(id));
        requestService.updatePerson(person);
    }

}
