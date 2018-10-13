package backend.dao;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;

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

    @Bean
    public Config hazelcastConfig(){
        return new Config()
                .setInstanceName("hz-inst")
                .addMapConfig(
                        new MapConfig()
                                .setName("sexy")
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(10)
                );
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
        return new String[] {"backend.entities"};
    }
}
