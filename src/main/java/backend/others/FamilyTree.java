package backend.others;

import backend.entities.Person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FamilyTree {
    UUID targetId;
    int desLevel;
    int ancLevel;
    ArrayList<Set<Person>> descendants;
    ArrayList<Set<Person>> ancestors;

    public FamilyTree(UUID targetId, int desLevel, int ancLevel) {
        this.targetId = targetId;
        this.desLevel = desLevel;
        this.ancLevel = ancLevel;
        descendants=new ArrayList<>();
        for (int i = 0; i < desLevel; i++) {
            descendants.add(new HashSet<>());
        }
        ancestors=new ArrayList<>();
        for (int i = 0; i < ancLevel; i++) {
            ancestors.add(new HashSet<>());
        }
    }

    public UUID getTargetId() {
        return targetId;
    }

    public void setTargetId(UUID targetId) {
        this.targetId = targetId;
    }

    public int getDesLevel() {
        return desLevel;
    }

    public void setDesLevel(int desLevel) {
        this.desLevel = desLevel;
    }

    public int getAncLevel() {
        return ancLevel;
    }

    public void setAncLevel(int ancLevel) {
        this.ancLevel = ancLevel;
    }

    public ArrayList<Set<Person>> getDescendants() {
        return descendants;
    }

    public void setDescendants(ArrayList<Set<Person>> descendants) {
        this.descendants = descendants;
    }

    public ArrayList<Set<Person>> getAncestors() {
        return ancestors;
    }

    public void setAncestors(ArrayList<Set<Person>> ancestors) {
        this.ancestors = ancestors;
    }
}
