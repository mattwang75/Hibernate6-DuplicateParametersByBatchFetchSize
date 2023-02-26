### Hibernate6-DuplicateParametersByBatchFetchSize

Just do "mvn test" and you should be able to see the test that reproduces the problem.  Pay attention to the log output:

**2023-02-26 02:25:59,495 DEBUG [main] org.hibernate.SQL: select o1_0.id_encounter,o1_0.id,o1_0.note from observation o1_0 where o1_0.id_encounter in(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)**

You will see every JDBC positional parameter ? is bound to the same id value, for a total of 50 times, which is really unnecessary.  We are just dealing with a single entity object here to load its one-to-many collection.

If you remove or comment out this line from src/main/resources/application.properties file, then it behaves properly:

**# spring.jpa.properties.hibernate.default_batch_fetch_size=50**

**2023-02-26 12:11:12,383 DEBUG [main] org.hibernate.SQL: select o1_0.id_encounter,o1_0.id,o1_0.note from observation o1_0 where o1_0.id_encounter=?**

**default_batch_fetch_size** has its useful places to optimize query performance by reducing database roundtrips (such as when doing **FetchType.SELECT**), but not in this occasion.  This seems a regression since it behaves properly in Hibernate 5.
