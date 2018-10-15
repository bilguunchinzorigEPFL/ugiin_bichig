package backend.dao;

import backend.entities.LifeEvent;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LifeEventRepository extends CrudRepository<LifeEvent, UUID> {
}
