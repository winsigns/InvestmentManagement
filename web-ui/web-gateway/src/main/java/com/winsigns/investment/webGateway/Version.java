package com.winsigns.investment.webGateway;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by jmx on 3/10/17.
 */
@Component
public class Version {
  @Value("${app.version}")
  private String version;

  @PostConstruct
  public void showVersion(){
    Logger log = Logger.getLogger(Version.class.getName());
    log.info(String.format("######VERSION######: %s", version));
  }
}
