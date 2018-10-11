package backend.dao;

import backend.model.Person;
import org.springframework.data.repository.CrudRepository;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by beku on 10/10/2018.
 */

public interface PersonDao extends CrudRepository<Person,UUID> {
    public ArrayList<Person> findByName(String name);
}
