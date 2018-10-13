package backend.entities;

import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Created by beku on 10/8/2018.
 */
//@Table(value = "people")
public class Person implements Serializable {
    //@PrimaryKey
    public UUID id;
    public String name;
    public UUID fatherId;
    public UUID motherId;
    public Set<UUID> childrenId;
    public String status; //married,single,dead
    public String description; //public short description about person
    public String history; //detailed history of person
    public Date birthday;
//    @Indexed
//    public String registerId;
    //public Set<LifeEvent> events;
}
