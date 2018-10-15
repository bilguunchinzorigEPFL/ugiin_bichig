package backend.manager;

import backend.dao.LifeEventRepository;
import backend.dao.PersonRepository;
import backend.entities.LifeEvent;
import backend.entities.Person;
import backend.others.Command;
import backend.others.FamilyTree;
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
    private PersonRepository personRepository;
    @Autowired
    private LifeEventRepository lifeEventDao;

    @Cacheable()
    public Collection<Person> getAllPeople(){
        return Lists.newArrayList(personRepository.findAll());
    }

    public Person getPersonById(UUID id){
        return personRepository.findById(id).get();
    }

    public void addPerson(Person person){
        if(person.getId()==null){
            person.id=UUID.randomUUID();
        }
        personRepository.save(person);
    }

    public void updatePerson(Person person){
        template.insert(person);
    }

    public void addEvent(LifeEvent event) {
        if(event.getId()==null){
            event.id=UUID.randomUUID();
        }
        lifeEventDao.save(event);
    }

    public void updateEvent(LifeEvent event) {
        template.insert(event);
    }

    public Collection<LifeEvent> getAllEvents() {
        return Lists.newArrayList(lifeEventDao.findAll());
    }

    public LifeEvent getEventById(UUID id) {
        return lifeEventDao.findById(id).get();
    }

    public String execute(Command command){
        switch (command.getName()) {
            case "reset":
                lifeEventDao.deleteAll();
                personRepository.deleteAll();
                return "success: Person="+Long.toString(personRepository.count())+
                        ", Event="+Long.toString(lifeEventDao.count())+";";
        }
        return "";
    }

    public FamilyTree getTree(UUID person, int desLevel, int ancLevel){
        FamilyTree tree=new FamilyTree(person,desLevel,ancLevel);
        //Finding descendants
        Set<UUID> currentLevelPeopleID=new HashSet<>();
        currentLevelPeopleID.add(person);
        for (int i = 0; i < desLevel; i++) {
            if(currentLevelPeopleID.isEmpty()){ break; }
            ArrayList<Person> currentLevelPeople=Lists.newArrayList(personRepository.findAllById(currentLevelPeopleID));
            Set<UUID> tmp=new HashSet<>();
            for (Person currentLevelPerson : currentLevelPeople) {
                Set<UUID> jojo=currentLevelPerson.getChildrenId();
                if(jojo!=null){
                    if(!jojo.isEmpty()){
                        tmp.addAll(jojo);
                    }
                }
            }
            tree.getDescendants().get(i).addAll(currentLevelPeople);
            currentLevelPeopleID=tmp;
        }
        //Finding ancestors
        currentLevelPeopleID=new HashSet<>();
        currentLevelPeopleID.add(person);
        for (int i = 0; i < ancLevel; i++) {
            if(currentLevelPeopleID.isEmpty()){ break; }
            ArrayList<Person> currentLevelPeople=Lists.newArrayList(personRepository.findAllById(currentLevelPeopleID));
            Set<UUID> tmp=new HashSet<>();
            for (Person currentLevelPerson : currentLevelPeople) {
                tmp.add(currentLevelPerson.getFatherId());
                tmp.add(currentLevelPerson.getMotherId());
            }
            tree.getAncestors().get(i).addAll(currentLevelPeople);
            currentLevelPeopleID=tmp;
        }
        return tree;
    }
}
