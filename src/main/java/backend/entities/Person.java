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
@Table(value = "people")
public class Person implements Serializable {
    @PrimaryKey
    public UUID id;
    @Indexed
    public String registerId;
    public String name;
    public UUID fatherId;
    public UUID motherId;
    public Set<UUID> childrenId;
    public String status; //married,single,dead
    public String description; //public short description about person
    public String history; //detailed history of person
    public Date birthday;
    public Set<UUID> events;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getFatherId() {
        return fatherId;
    }

    public void setFatherId(UUID fatherId) {
        this.fatherId = fatherId;
    }

    public UUID getMotherId() {
        return motherId;
    }

    public void setMotherId(UUID motherId) {
        this.motherId = motherId;
    }

    public Set<UUID> getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(Set<UUID> childrenId) {
        this.childrenId = childrenId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Set<UUID> getEvents() {
        return events;
    }

        public void setEvents(Set<UUID> events) {
        this.events = events;
    }
}
