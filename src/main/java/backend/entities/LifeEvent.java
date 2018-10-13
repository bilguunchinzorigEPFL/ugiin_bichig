package backend.entities;

import java.io.Serializable;
import java.util.Date;

public class LifeEvent implements Serializable {
    public String title;
    public Date startDate;
    public Date endDate;
    public String description;
    public String status;
    public String filePath;
}
