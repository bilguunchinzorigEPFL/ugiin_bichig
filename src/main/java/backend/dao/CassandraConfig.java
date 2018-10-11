package backend.dao;

import backend.model.Person;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by beku on 10/10/2018.
 */
@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {
    private Properties p;

    public CassandraConfig(){
        p=new Properties();
        try {
            p.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification=CreateKeyspaceSpecification
                .createKeyspace(p.getProperty("db_keyspace"))
                .ifNotExists();
        return Arrays.asList(specification);
    }

    @Override
    protected String getContactPoints() {
        return p.getProperty("db_ip");
    }

    @Override
    protected int getPort() {
        return Integer.valueOf(p.getProperty("db_port"));
    }

    @Override
    protected String getKeyspaceName() {
        return p.getProperty("db_keyspace");
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] {"backend.model"};
    }
}
