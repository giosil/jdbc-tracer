package org.dew.jdbc;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

public 
class TObjectFactory implements ObjectFactory 
{
  @Override
  public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment)
      throws Exception 
  {
    if (obj instanceof Reference) {
      Reference ref = (Reference) obj;
      if (ref.getClassName().equals(TDataSource.class.getName())) {
        TDataSource dataSource = new TDataSource();
        dataSource.setURL((String)ref.get("url").getContent());
        dataSource.setUser((String)ref.get("user").getContent());
        dataSource.setPassword((String)ref.get("password").getContent());
        dataSource.setDescription((String)ref.get("description").getContent());
        String loginTimeout = (String)ref.get("loginTimeout").getContent();
        if(loginTimeout != null && loginTimeout.length() > 0) {
          dataSource.setLoginTimeout(Integer.parseInt(loginTimeout));
        }
        return dataSource;
      } 
    }
    return null;
  }
  
}
