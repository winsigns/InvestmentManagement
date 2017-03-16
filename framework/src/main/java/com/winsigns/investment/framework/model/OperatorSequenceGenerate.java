package com.winsigns.investment.framework.model;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.spring.SpringManager;

@Component
public class OperatorSequenceGenerate implements Configurable, IdentifierGenerator {

  @Override
  public Serializable generate(SessionImplementor session, Object object)
      throws HibernateException {
    return SpringManager.getApplicationContext().getBean(OperatorSequenceIntegration.class)
        .getSequence();
  }

  @Override
  public void configure(Type type, Properties params, ServiceRegistry serviceRegistry)
      throws MappingException {}

}
