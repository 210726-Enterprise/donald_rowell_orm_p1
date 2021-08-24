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
