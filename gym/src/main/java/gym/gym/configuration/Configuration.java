package gym.gym.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
@MapperScan(basePackages = "gym.gym.mapper", sqlSessionTemplateRef = "clientSqlSessionTemplate")
@EnableTransactionManagement
public class Configuration {

    private final ConfigPropertyReader config;

    @Autowired
    public Configuration(ConfigPropertyReader cnf) {
        this.config = cnf;
    }

    @Bean(name = "clientApi")
    public HikariDataSource dataSourcebackendBetting() {
        return setDs();
    }

    @Bean(name = "clientApiSqlSessionFactory")
    public SqlSessionFactory playerApiSqlSessionFactory(@Qualifier("clientApi") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("clientApi") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "clientSqlSessionTemplate")
    public SqlSessionTemplate playerSqlSessionTemplate(@Qualifier("clientApiSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    private HikariDataSource setDs(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(config.getDriver_class_name());
        hikariDataSource.setJdbcUrl(config.getDb_url() + config.getDatabaseName()+config.getDriver_params());
        hikariDataSource.setUsername(config.getUsername());
        hikariDataSource.setPassword(config.getPassword());
        return hikariDataSource;
    }
}