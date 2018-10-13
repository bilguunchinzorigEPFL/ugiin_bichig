package test;

import backend.entities.Person;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Tester {

    private String sendRequest(String method, String values) {
        try {
            URL url=new URL("127.0.0.1:8080/data");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(method);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(values);
            writer.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer jsonString = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();
            connection.disconnect();
            return jsonString.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){
        ObjectMapper mapper=new ObjectMapper();
        try {
            Person person=new Person();
            person.name="WTF";
            String hehe=mapper.writer().writeValueAsString(person);
            Person hoho=mapper.readValue(hehe,Person.class);
            System.out.print(hehe);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
