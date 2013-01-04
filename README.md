omf-contrib
========

The OMF contribs is a collection of add-on libraries to the excellent [Object Manager](https://github.com/ilikeorangutans/omf) written by [ilikeorangutans](https://github.com/ilikeorangutans).

Modules
----------

* DAO.  A Generic DAO implementation.  The generic DAO implementation also includes:
* HTTP.  A simple implementation of the Open-Session-In-View pattern for Object Manager DAO
* jcr2pojo. A simple command-line application which can read a JCR and produce appropriate DAO and POJO objects from it to be used with OM DAO.
* jcrcache. A simple wrapper around an arbitrary PersistenceDelegate which caches values.  It is intended to be used in the situation that the JCR is being accessed over RMI, which is very slow.

DAO
----------

The generic DAO includes:

* DAO [interface](https://github.com/ilikeorangutans/omf-contrib/blob/master/dao/src/main/java/org/om/dao/genericdao/GenericDAO.java) and [implementation](https://github.com/ilikeorangutans/omf-contrib/blob/master/dao/src/main/java/org/om/dao/genericdao/impl/GenericDAOImpl.java)
* An [@Transactional](https://github.com/ilikeorangutans/omf-contrib/blob/master/dao/src/main/java/org/om/dao/annotation/transactional/Transactional.java) annotation allowing JCR-transactional operations to be carried out over OMF-managed objects.
* Support to [configure](https://github.com/ilikeorangutans/omf-contrib/blob/master/dao/src/main/java/org/om/dao/config/ObjectManagerConfiguration.java) the DAO via [Properties Files](https://github.com/ilikeorangutans/omf-contrib/blob/master/dao/src/main/java/org/om/dao/config/impl/PropertiesFileObjectManagerConfiguration.java) and [JNDI](https://github.com/ilikeorangutans/omf-contrib/blob/master/dao/src/main/java/org/om/dao/config/impl/JNDIObjectManagerConfiguration.java)

Licensing
----------

OMF is licensed under the Apache 2.0 license

How to use the DAO
----------

Define your POJO

``` @Entity
    public class MyPojo {
      @Id
    	private String id;
    	@Property
    	private double rate;
    	@Property
    	private int count;
    
    	public int getCount() {
    		return count;
    	}
    
    	public String getId() {
    		return id;
    	}
    
    	public double getRate() {
    		return rate;
    	}
    
    	public void setCount(int count) {
    		this.count = count;
    	}
    
    	public void setId(String id) {
    		this.id = id;
    	}
    
    	public void setRate(double rate) {
    		this.rate = rate;
    	}
    }
```
Define your DAO

```

    public class MyPojoDAO extends GenericDAOImpl<MyPojo> implements GenericDAO<MyPojo> {
      /**
    	 * ctor
    	 */
    	public MyPojoDAO() {
    		super(MyPojo.class);
    	}
    }

```

Use your DAO

```
    MyPojoDAO dao = new MyPojoDAO();
    /*
     * make an object
     */
    final MyPojo myPojo = new MyPojo();
    myPojo.setCount(12);
    myPojo.setId("tge");
    myPojo.setRate(13.3);
    /*
     * save the object
     */
    dao.save(myPojo);
    /*
     * get the object back
     */
    MyPojo retrievedPojo = dao.get(myPojo.getId());
    System.out.println(retrievedPojo.getId());
```

Defining Transactions over DAOs
----------

To combine multiple DAO operations into a transaction, simply annotate the method of the service that uses the DAOs. All of the DAO operations in the method will run inside a JCR transaction and the transaction will be rolled back if the annotated method throws an exception.

```
    public class MyServiceImpl implements MyService {
      @Inject()
    	private MyPojoDAO mypojodao1;
    
      @Inject()
      private MyPojoDAO mypojodao2;
          
    	@Transactional()
    	public void doStuff() {
    		mypojodao1.save(object1);
        mypojodao1.save(object2);

    	}
    }

```

OMF Open-Session-In-View
----------

The HTTP module of omf-contribs includes a simple HTTP filter that implements the Open-Session-In-View pattern.  To use the filter, simply include it in your web.xml.

```
    <filter>
       <filter-name>OMFSessionFilter</filter-name>
       <filter-class>org.om.http.filter.ObjectManagerSessionRequestFilter</filter-class>
    </filter>
    <filter-mapping>
       <filter-name>OMFSessionFilter</filter-name>
       <url-pattern>/*</url-pattern>
    </filter-mapping>

```

The OMF session will be available as the HTTP session variable "ObjectManagerSession".  There is a working example of using this filter with Struts, [here](https://github.com/ilikeorangutans/omf-contrib/tree/master/examples/strutsexample)

Generating DAO objects from an existing JCR
----------

In cases where OM is being used to map an existing JCR, jcr2pojo can be used to generate fully annotated POJOs which map the existing JCR structure.  An alternative is to use objectmanager-maven-plugin, a maven mojo which wraps jcr2pojo.  An example configuration looks like this:

```
      <plugin>
  			<groupId>org.objectmapper</groupId>
				<artifactId>objectmapper-maven-plugin</artifactId>
				<configuration>
					<url>http://localhost:8080/rmi</url>
					<username>admin</username>
					<password>admin</password>
					<workspace></workspace>
					<namespace>com.khubla</namespace>
					<jcrRoot></jcrRoot>
					<targetDir>target/om/</targetDir>
				</configuration>
			</plugin>
      
```

This configuration will read the repository structure of the JCR at "http://localhost:8080/rmi" and produce POJOs to "/target/om".







