package backend.dao;

import backend.entities.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by beku on 10/10/2018.
 */

public interface PersonRepository extends CrudRepository<Person,UUID> {

}
