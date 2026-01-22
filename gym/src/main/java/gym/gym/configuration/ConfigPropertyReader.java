package gym.gym.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@Data
@Configuration
@EnableAsync
//@PropertySource("file:application.properties")
public class ConfigPropertyReader {

    @Value("${moobitek.datasource.username}")
    private String username;
    @Value("${moobitek.datasource.password}")
    private String password;
    @Value("${moobitek.datasource.databaseName}")
    private String databaseName;
    @Value("${moobitek.datasource.url}")
    private String db_url;
    @Value("${moobitek.datasource.driver-class-name}")
    private String driver_class_name;
    @Value("${moobitek.datasource.extra-params}")
    private String driver_params;



    @Value("${moobitek.datasource.maximum-pool-size}")
    private int maxPoolSize;
    @Value("${moobitek.datasource.connection-timeout}")
    private int connectionTimeout;
    @Value("${moobitek.datasource.idle-timeout}")
    private int idleTimeout;
    @Value("${moobitek.datasource.minimum-idle}")
    private int minimumIdle;
    @Value("${moobitek.datasource.keepalive-time}")
    private int keepAliveTime;
    @Value("${moobitek.datasource.max-lifetime}")
    private int maxLifetime;
    @Value("${moobitek.datasource.auto-commit}")
    private boolean autoCommit;
    @Value("${moobitek.datasource.pool-name}")
    private String mainPoolName;
    @Value("${moobitek.datasource.pool-name}")
    private String backendPoolName;

    @Value("${moobitek.member.minimum-age}")
    private int ageLimit;
}
