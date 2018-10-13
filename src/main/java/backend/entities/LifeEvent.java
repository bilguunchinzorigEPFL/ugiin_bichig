package backend.entities;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Table(value="life_events")
public class LifeEvent implements Serializable {
    @PrimaryKey
    public UUID id;
    public String title;
    public Date startDate;
    public Date endDate;
    public String description;
    public String status;
    public String filePath;
    public Set<UUID> people;
}
