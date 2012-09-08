package eu.interedition.server;

import eu.interedition.collatex.graph.GraphFactory;
import eu.interedition.collatex.simple.SimpleTokenMapper;
import eu.interedition.collatex.simple.SimpleWitnessMapper;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.impl.transaction.SpringTransactionManager;
import org.neo4j.kernel.impl.transaction.UserTransactionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import java.io.File;
import java.io.IOException;

/**
 * @author <a href="http://gregor.middell.net/" title="Homepage">Gregor Middell</a>
 */
@Configuration
public class DataSourceConfiguration {
  private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfiguration.class);

  @Autowired
  private Environment environment;

  @Bean
  public PlatformTransactionManager transactionManager() throws Exception {
    final EmbeddedGraphDatabase graphDatabase = graphDatabase();
    return new JtaTransactionManager(new UserTransactionImpl(graphDatabase), new SpringTransactionManager(graphDatabase));
  }

  @Bean
  public GraphFactory graphFactory() throws IOException {
    return new GraphFactory(graphDatabase(), new SimpleWitnessMapper(), new SimpleTokenMapper());
  }

  @Bean(destroyMethod = "shutdown")
  public EmbeddedGraphDatabase graphDatabase() throws IOException {
    final File graphDirectory = new File(environment.getRequiredProperty("interedition.data", File.class), "graphs");
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("Starting graph database in {}", graphDirectory);
    }
    return new EmbeddedGraphDatabase(graphDirectory.getCanonicalPath());
  }
}