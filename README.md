# Custom ORM Frameword
## Project Description
A custom object relational mapping (ORM) framework, written in Java, which allows for a simplified and SQL-free interaction with a relational data source. Low-level JDBC is completely abstracted away from the developer, allowing them to easily query and persist data to a data source. Makes heavy use of the Java Reflection API in order to support any entity objects as defined by the developer.
## Technologies Used
- Java 8
- PostgreSql
- DBeaver
- AWS RDS
- Apache Maven
- Git SCM (on GitHub)

## Features
List of features ready:
- Able to perform simple CRUD operations (Insert, Select, Update, Delete)
- JDBC logic abstracted away by the custom ORM
- Dynamic creation of tables by custom ORM on Insert

To-do List:
- Dynamic configuration of database connection using properties file
- Update GET methods to be able to retrieve more than one element
- Add support for basic transaction management

## Getting Started
How to setup this code:
1. Download the most recent version .jar file, which can be found in the target folder.
2. Add the Maven dependency that follows, editing the systemPath according to where you saved the file to:
      
            <dependency>
                  <groupId>com.revature</groupId>
                  <artifactId>Project01</artifactId>
                  <version>2.0</version>
                  <scope>system</scope>
                  <systemPath>${project.basedir}/Project01-2.0.jar</systemPath>
              </dependency>
3. Set system environment variables of "db_url", "db_username", and "db_password" to your database's url, username, and password, respectively.
4. The Custom ORM framework is now ready for use
## Usage
How to use this code:
- All classes that will be used for persistance objects must have proper formatting and annotations:

      - The class must be annotated with @Table(tableName = "your_table_name"
      - There must be a field that is an int with the annotation @PrimaryKey(columnName="your_primary_key") to signify your primary key in the table
      - The rest of the fields that will be persisted into the database must be annotated with @Column(columnName="your_column_name") to signify your other columns
      - There must be at least one field with the @Column annotation
      - There must be a no-args constructor
- The constructor for the ORM is as follows, where Model is the class that is being persisted into the database (You will need to declare a new ORM object for each model class you wish to persist):

            ORM<Model> ormModel = new ORM<>(Model.class);
- There are 4 ORM methods that can be used:
      
      .insert(T obj) - the input for this method is the object you wish to insert into the database. This method returns the int primary key.
      
      .select(Class<T> clazz) - the input for this method is the model class. This method returns a select object, which can then be used to specify the where clause.
                                  There is currently no support for selecting more than one row from a model.
                   
                    .where(int id) - this method finds row where primary key is equal to given id. This method returns the fully constructed model
                    
                    .where(String col, Object val) - this method finds row where a specified column has a value equal to val. This method returns the fully constructed model
      
      .update(T obj) - the input for this method is the object you wish to update in the database, where the primary key has a match in the database with another entry.
                      This method returns a boolean stating whether it was successful.
      
      .delete(int id) - the input for this method is the primary key integer id for the row that you wish to delete. This method returns a boolean stating whether it was successful.
## Contributors
- [ ] Donald Rowell

