package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;


public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	}
    /**
    @param : campul care vreau sa il extrag din tabela
	 @return : stringul care reprezinta query-ul de selectie
     */
	private String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " =?");
		System.out.println(sb.toString());
		return sb.toString();
	}
	/**
	 @param : gol
	 @return : stringul care reprezinta query-ul de stergere
	 */
	private String createDeleteQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE (id =?)");
		return sb.toString();
	}
	/**
	 @param : gol
	 @return : stringul care reprezinta query-ul de insertie
	 */
	private String createInsertQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(type.getSimpleName());
		sb.append(" (");
		int i = 0;
		for (Field f : type.getDeclaredFields()){
			if (i != type.getDeclaredFields().length - 1) {
				sb.append(f.getName() + ", ");
			} else {
				sb.append(f.getName() + ") ");
			}
			i++;
		}
		sb.append("VALUES (");
		for (int j = 0; j < i; j++){
			sb.append("?");
			if ( j != i-1 ){
				sb.append(", ");
			}
			else{
				sb.append(")");
			}
		}
		return sb.toString();
	}
	/**
	 @param : gol
	 @return : stringul care reprezinta query-ul de update a tabelei
	 */
private String UpdateQuery(String field,int id)
{
	StringBuilder sb = new StringBuilder();
	sb.append("UPDATE ");
	sb.append(type.getSimpleName());
	sb.append(" SET "+field);
	sb.append(" =? WHERE id= "+ id);
	return sb.toString();


}

	public List<T> findAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();

			return createObjects(resultSet);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findALL " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}
	/**
	 @param  id-ul obiectului(client,produs sau comanda) pe care dorim sa il gasim
	 @return : obiectul gasit sau null daca nu a fost gasit

	 */
	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			System.out.println(resultSet.toString());
			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}
	/**
	 @param resultSet: resultSet obiectul gasit de functia find by id
	 @return :  instanta noua creeata
	 @throws IllegalAccessException daca s-a parsat un argument ilegal metodelor apelate
	 */
	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		Constructor[] ctors = type.getDeclaredConstructors();
		Constructor ctor = null;
		System.out.println(ctors.length);
		for (int i = 0; i < ctors.length; i++) {
			ctor = ctors[i];
			System.out.println(ctor.toString());
			System.out.println(ctors[i].toString());
			if (ctor.getGenericParameterTypes().length == 0)
				break;
		}
		try {
			while (resultSet.next()) {
				assert ctor != null;
				ctor.setAccessible(true);
				T instance = (T) ctor.newInstance();
				for (Field field : type.getDeclaredFields()) {
					String fieldName = field.getName();
					Object value = resultSet.getObject(fieldName);
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 @param  t: obiectul in care se insereaza
	 @return : id-ul obiectului in care s-a inserat
	 */
	public int insert(T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createInsertQuery();
		int i=0;
		int insertedID=-1;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			for(Field f : t.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				PropertyDescriptor propertyDescriptor = new PropertyDescriptor(f.getName(), t.getClass());
				Method method = propertyDescriptor.getReadMethod();
				Type type = propertyDescriptor.getPropertyType();
				if (type.equals(int.class)) {
					int val = (Integer) method.invoke(t);
					statement.setInt(i+1, val);
				} else if (type.equals(double.class)) {
					double val = (double) method.invoke(t);
					statement.setDouble(i+1, val);
				} else if (type.equals(String.class)) {
					String s = (String) method.invoke(t);
					statement.setString(i+1, s);
				}
				i++;
			}
			System.out.println(statement.toString());
			statement.executeUpdate();

			//resultSet=statement.getResultSet();



			resultSet = statement.getGeneratedKeys();
			if(resultSet.next()){
				insertedID = resultSet.getInt(1);
			}
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor("id", t.getClass());
			Method method = propertyDescriptor.getWriteMethod();
			method.invoke(t, insertedID);

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:insertObjT " + e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return insertedID;
	}
	/**
	 @param t,fieldname,o,id : obiectul care urmeaza sa fie actualizat,numele campului de actualizat,valoarea cu care trebuie sa fie actualizat si id-ul obiectului
	 @return : obiectul actualizat
	 */
	public T update(T t,String fieldname, Object o,int id) {
		System.out.println("Am intrat in update");
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = UpdateQuery(fieldname,id);
		int i=0;
		int insertedID=-1;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				//f.setAccessible(true);
				PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldname, t.getClass());
				Type type = propertyDescriptor.getPropertyType();

				if (type.equals(int.class)) {

					statement.setInt(1,(Integer)o);
				} else if (type.equals(String.class)) {
					statement.setString(1, (String)o);
				}
			System.out.println(statement.toString());
			statement.executeUpdate();



		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:insertObjT " + e.getMessage());
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return t;

	}
	/**
	 @param id : id-ul obiectului care trebuie sa fie sters
	 */
	public void delete(int id){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createDeleteQuery();

		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);

			statement.setInt(1,id);

			System.out.println(statement.toString());
			statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
	}
}
