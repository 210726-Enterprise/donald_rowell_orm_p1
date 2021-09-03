# Custom ORM Frameword
## Project Description
A custom object relational mapping (ORM) framework, written in Java, which allows for a simplified and SQL-free interaction with a relational data source. Low-level JDBC is completely abstracted away from the developer, allowing them to easily query and persist data to a data source. Makes heavy use of the Java Reflection API in order to support any entity objects as defined by the developer.
## Technologies Used
- [ ] Java 8
- [ ] PostgreSql
- [ ] DBeaver
- [ ] AWS RDS
- [ ] Apache Maven
- [ ] Git SCM (on GitHub)

## Features
List of features ready:
- [ ] Able to perform simple CRUD operations (Insert, Select, Update, Delete)
- [ ] JDBC logic abstracted away by the custom ORM
- [ ] Dynamic creation of tables by custom ORM on Insert

To-do List:
- [ ] Dynamic configuration of database connection
- [ ] Update GET methods to be able to retrieve more than one element
- [ ] Add support for basic transaction management

## Getting Started
How to setup this code

## Usage
How to use this code

## Contributors
- [ ] Donald Rowell


This ORM requires that the models that will be persisted into a database must have annotations that cover the following:
      
      The class must have a "Table" annotation with an argument of tableName = "Your table name".
      
      There must be an integer primary key field in the class with the annotation "PrimaryKey(columnName = "Your primary key column name")"
      
      There must be at least one additional column field with the annotation "Column(columnName = "Your column name")"

  
  The ORM must be initialized for each model type that you are using, using the constructor and passing in the class of the model.
  
  There are 4 ORM methods that can be used:
      
      .insert(T obj) - the input for this method is the object you wish to insert into the database. This method returns the int primary key.
      
      .select(Class<T> clazz) - the input for this method is the model class. This method returns a select object, which can then be used to specify the where clause.
                                  There is currently no support for selecting more than one row from a model.
                   
                    .where(int id) - this method finds row where primary key is equal to given id. This method returns the fully constructed model
                    
                    .where(String col, Object val) - this method finds row where a specified column has a value equal to val. This method returns the fully constructed model
      
      .update(T obj) - the input for this method is the object you wish to update in the database, where the primary key has a match in the database with another entry.
                      This method returns a boolean stating whether it was successful.
      
      .delete(int id) - the input for this method is the primary key integer id for the row that you wish to delete. This method returns a boolean stating whether it was successful.
