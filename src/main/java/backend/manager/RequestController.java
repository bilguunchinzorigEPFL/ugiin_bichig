package backend.manager;

import backend.entities.LifeEvent;
import backend.entities.Person;
import backend.others.Command;
import backend.others.FamilyTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by beku on 10/8/2018.
 */
@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;

    //Data creation function
    //Create user
    @RequestMapping(value="/data/people",method=RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createPerson(@RequestBody Person person){
        requestService.addPerson(person);
    }
    //Update user
    @RequestMapping(value="/data/people/{id}",method=RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updatePerson(@PathVariable("id") String id,@RequestBody Person person){
        person.id=UUID.fromString(id);
        requestService.updatePerson(person);
    }
    //Create lifeEvent
    @RequestMapping(value="/data/events/",method=RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createEvent(@RequestBody LifeEvent event){
        requestService.addEvent(event);
    }
    //Update lifeEvent
    @RequestMapping(value="/data/events/{id}",method=RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateEvent(@PathVariable("id") String id,@RequestBody LifeEvent event){
        event.id=UUID.fromString(id);
        requestService.updateEvent(event);
    }

    //Data extraction function
    //simple get methods
    @RequestMapping(value="/data/people",method=RequestMethod.GET)
    public Collection<Person> getAllPeople(){
            return requestService.getAllPeople();
    }

    @RequestMapping(value="/data/people/{id}",method = RequestMethod.GET)
    public Person getPersonById(@PathVariable("id") String id){
        return requestService.getPersonById(UUID.fromString(id));
    }

    @RequestMapping(value="/data/events",method=RequestMethod.GET)
    public Collection<LifeEvent> getAllEvents(){
        return requestService.getAllEvents();
    }

    @RequestMapping(value="/data/events/{id}",method = RequestMethod.GET)
    public LifeEvent getEventById(@PathVariable("id") String id){
        return requestService.getEventById(UUID.fromString(id));
    }

    @RequestMapping(value = "/data/command",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public String command(@RequestBody Command cmd){
        return requestService.execute(cmd);
    }

    @RequestMapping(value="/data/familytree",method = RequestMethod.GET)
    public FamilyTree getFamilyTree(@RequestParam(value="target") String id,
                                    @RequestParam(value="desLevel") int desLevel,
                                    @RequestParam(value="ancLevel") int ancLevel){
        return requestService.getTree(UUID.fromString(id),desLevel,ancLevel);
    }
}
