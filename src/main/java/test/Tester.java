package test;

import backend.entities.LifeEvent;
import backend.entities.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Tester {

    public static String convert(InputStream inputStream) throws Exception {
        try (Scanner scanner = new Scanner(inputStream)) {
            return scanner.useDelimiter("\\A").next();
        }
    }

    private static String sendRequest(String suburl,String method, String values) {
        try {
            HttpPut request=new HttpPut("http://127.0.0.1:8000/data/"+suburl);
            request.setEntity(new StringEntity(values));
            request.setHeader("Content-type", "application/json");
            HttpResponse response=HttpClientBuilder.create().build().execute(request);
            return convert(response.getEntity().getContent());
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static <T> void updateTable(String suburl,T[] objects) throws Exception{
        ObjectMapper mapper=new ObjectMapper();
        for (T object : objects) {
            sendRequest(suburl,"PUT",
                    mapper.writer().writeValueAsString(object));
        }
    }

    //Maybe add timedelays
    private static <T> ArrayList<T> populateTableRandom(int amount, Class object){
        PodamFactory factory=new PodamFactoryImpl();
        ArrayList<T> list=new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            list.add((T)factory.manufacturePojo(object));
        }
        return list;
    }

    private static Set<UUID> subSet(List<UUID> set, UUID exclude, int max){
        Random random=new Random();
        set.remove(exclude);
        Collections.shuffle(set);
        return new HashSet<>(
                set.subList(0,
                        random.nextInt(max)));
    }
    private static UUID randomElem(List<UUID> set, UUID exclude){
        Random random=new Random();
        List<UUID> tmp=new ArrayList<>(set);
        tmp.remove(exclude);
        return tmp.get(random.nextInt(set.size()-1));
    }

    private static void populateDB(int numPerson, int numEvents, int maxEvents) throws Exception {
        //populate Tables
        ArrayList<Person> people= populateTableRandom(numPerson, Person.class);
        ArrayList<LifeEvent> events= populateTableRandom(numPerson, LifeEvent.class);
        List<UUID> peopleIDs=new ArrayList<>();
        for (Person person : people) { peopleIDs.add(person.id); }
        List<UUID> eventIDs=new ArrayList<>();
        for (LifeEvent event : events) { eventIDs.add(event.id); }

        //Connections
        HashMap<UUID, Set<UUID>> childrenSets=new HashMap<>();
        for (Person person : people) {
            Set<UUID> tmp=new HashSet<>();
            childrenSets.put(person.id,tmp);
        }
        HashMap<UUID, Set<UUID>> eventSets=new HashMap<>();
        for (LifeEvent event : events) {
            Set<UUID> tmp=new HashSet<>();
            eventSets.put(event.id,tmp);
        }
        for (int i = 0; i < people.size(); i++) {
            UUID personid=people.get(i).getId();

            UUID tmp=randomElem(peopleIDs,personid);
            childrenSets.get(tmp).add(personid);
            people.get(i).setFatherId(tmp);

            tmp=randomElem(peopleIDs,personid);
            childrenSets.get(tmp).add(personid);
            people.get(i).setMotherId(tmp);

            Set<UUID> tmpEvents=subSet(eventIDs,personid,maxEvents);
            for (UUID event : tmpEvents) {
                eventSets.get(event).add(personid);
            }
            people.get(i).setEvents(tmpEvents);
        }

        for (int i = 0; i < people.size(); i++) {
            people.get(i).setChildrenId(childrenSets.get(
                    people.get(i).getId()
            ));
        }
        for (int i = 0; i < events.size(); i++) {
            events.get(i).setPeople(eventSets.get(
                    events.get(i).getId()
            ));
        }

        updateTable("people/",people.toArray());
        updateTable("events/",events.toArray());
    }

    public static void main(String[] args){
        try {
            populateDB(100,200,15);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
