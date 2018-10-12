package backend.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by beku on 10/8/2018.
 */
@Table
public class Person implements Serializable {
    @PrimaryKey
    private UUID id;
    private String name;
    private UUID fatherID;
    private UUID motherID;
    private String about;
    private Date birthday;
    private String registerID;

    public Person() {
    }

    public Person(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getFatherID() {
        return fatherID;
    }

    public void setFatherID(UUID fatherID) {
        this.fatherID = fatherID;
    }

    public UUID getMotherID() {
        return motherID;
    }

    public void setMotherID(UUID motherID) {
        this.motherID = motherID;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRegisterID() {
        return registerID;
    }

    public void setRegisterID(String registerID) {
        this.registerID = registerID;
    }

}
