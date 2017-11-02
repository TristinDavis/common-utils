package com.cweijan.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class DbUtils {

	private static Logger logger = Logger.getLogger(DbUtils.class);

	public static Connection getConnectino(String url,String username, String password) {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}

		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}

		return connection;
	}
	
	public static Connection getLocalConnectino(String username, String password) {

		Connection connection = getConnectino("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8" ,username, password);

		return connection;
	}
	
	/**
	 * 查询转换为实体类
	 * 
	 * @param connection
	 * @param sql
	 * @param beanClass
	 * @return
	 * @throws SQLException
	 */
	public static <T> T select(Connection connection, String sql, Class<T> beanClass) throws SQLException {

		ResultSet resultSet = executeSelectSql(connection, sql);

		return resultSetToBean(resultSet, beanClass);
	}

	/**
	 * 查询转换为实体类list
	 * 
	 * @param connection
	 * @param sql
	 * @param beanClass
	 * @return
	 * @throws SQLException
	 */
	public static <T> List<T> selectToList(Connection connection, String sql, Class<T> beanClass) throws SQLException {

		ResultSet resultSet = executeSelectSql(connection, sql);

		return resultSetToBeanList(resultSet, beanClass);
	}

	public static <T> void insertList(List<T> tList, String tableName, Connection connection) {

		for (T t : tList) {
			insert(t, tableName, connection);
		}

	}

	public static <T> void createTable(Class<T> t, String tableName, String primaryKey, Connection connection) {

		checkParam(t, tableName);
		checkConnectionAlive(connection);

		String sql = "create table " + tableName + "(";

		for (Field field : t.getDeclaredFields()) {

			field.setAccessible(true);
			sql += "`"+field.getName() + "` " + getFieldType(field) + ",";

		}

		sql += "primary key(`" + primaryKey + "`)";
		
		sql += ");";

		executeSql(connection, sql);

	}

	private static String getFieldType(Field field) {

		Class<?> fieldType = field.getType();
		if (fieldType == String.class) {
			return "varchar(100)";
		}

		if (fieldType == Integer.class) {
			return "int";
		}

		if (fieldType == Double.class) {
			return "double";
		}

		if (fieldType == Long.class || fieldType.getName().equals("long")) {
			return "int";
		}

		if (fieldType == Float.class) {
			return "float";
		}

		if (fieldType.isPrimitive()) {
			return fieldType.getName();
		}

		return null;
	}

	/**
	 * 插入java对象到数据库
	 * 
	 * @param t
	 * @param tableName
	 * @param connection
	 */
	public static <T> void insert(T t, String tableName, Connection connection) {

		if (t instanceof List) {
			throw new IllegalArgumentException("not support list Type!");
		}

		checkParam(t, tableName);

		Field[] fields = t.getClass().getDeclaredFields();
		String sql = "";
		String keys = "";
		String values = "";

		Object fieldValue = null;
		for (Field field : fields) {

			field.setAccessible(true);

			try {
				fieldValue = field.get(t);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				logger.error("get fieldValue fail!", e);
			}
			if (fieldValue == null) {
				continue;
			}

			keys += ",`" + field.getName()+"`";
			if (field.getType().getSimpleName().equals("String")) {
				values += ",'" + fieldValue + "'";
				continue;
			}

			values += "," + fieldValue;

		}

		keys = keys.replaceFirst(",", "");
		values = values.replaceFirst(",", "");
		sql = "insert ignore  into " + tableName + "(" + keys + ") values(" + values + ");";
		executeSql(connection, sql);

	}

	private static <T> void checkParam(T t, String tableName) {
		if (t == null || StringUtils.isBlank(tableName)) {
			throw new IllegalArgumentException("param must be not null!");
		}
	}

	public static void main(String[] args) {
		
		System.out.println(new ArrayList<>() instanceof List);
		
	}
	
	public static void executeSql(Connection connection, String sql) {

		checkConnectionAlive(connection);

		try {
			Statement statement = connection.createStatement();
			logger.info("sql : " + sql);
			statement.execute(sql);
		} catch (SQLException e) {
			logger.error("execute sql error!", e);
		}

	}

	private static ResultSet executeSelectSql(Connection connection, String sql) {

		checkConnectionAlive(connection);

		ResultSet resultSet = null;
		try {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			logger.error("execute sql error!", e);
		}

		return resultSet;
	}

	private static void checkConnectionAlive(Connection connection) {
		try {
			if (connection == null || connection.isClosed()) {
				throw new IllegalArgumentException("connection illegal!");
			}
		} catch (SQLException e1) {
			logger.error(e1);
		}
	}

	/**
	 * 将resultSet转为java对象
	 * 
	 * @param resultSet
	 * @param beanClass
	 * @return
	 * @throws SQLException
	 */
	public static <T> T resultSetToBean(ResultSet resultSet, Class<T> beanClass) throws SQLException {

		if (!next(resultSet)) {
			return null;
		}

		Map<String, String> columns = getColumns(resultSet);
		T bean = toJavaBean(resultSet, beanClass, columns);

		return bean;
	}

	/**
	 * 将resultSet转为java对象List
	 * 
	 * @param resultSet
	 * @param beanClass
	 * @return
	 */
	public static <T> List<T> resultSetToBeanList(ResultSet resultSet, Class<T> beanClass) {

		List<T> beanList = new ArrayList<T>();

		Map<String, String> columns = getColumns(resultSet);
		T bean = null;

		while (next(resultSet)) {

			bean = toJavaBean(resultSet, beanClass, columns);
			beanList.add(bean);

		}

		if (beanList.size() == 0) {
			return null;
		}

		return beanList;
	}

	/**
	 * 获取resultSet字段Map key为字段,value为字段类型
	 * 
	 * @param resultSet
	 * @return
	 */
	public static Map<String, String> getColumns(ResultSet resultSet) {

		ResultSetMetaData metaData = null;
		Map<String, String> columns = null;
		String columnLabel = null;

		// 获取resultSet字段类型
		try {
			metaData = resultSet.getMetaData();
			int count = metaData.getColumnCount();
			columns = new HashMap<String, String>();

			for (int i = 1; i <= count; i++) {
				columnLabel = metaData.getColumnLabel(i);
				columns.put(columnLabel, metaData.getColumnTypeName(i));
			}

		} catch (SQLException e) {
			logger.error("get metaData fail!", e);
		}

		return columns;
	}

	/**
	 * 统一处理next异常
	 * 
	 * @param resultSet
	 * @return
	 */
	private static boolean next(ResultSet resultSet) {

		boolean result = false;

		try {
			result = resultSet.next();
		} catch (SQLException e) {
			logger.error("access fail!", e);
		}

		return result;
	}

	/**
	 * 将resultSet一列转为javaBean
	 * 
	 * @param resultSet
	 * @param clazz
	 * @param columns
	 * @return
	 */
	private static <T> T toJavaBean(ResultSet resultSet, Class<T> clazz, Map<String, String> columns) {

		Field[] fields = clazz.getDeclaredFields();
		T t = null;
		try {
			t = clazz.newInstance();
		} catch (InstantiationException e) {
			logger.error("this class have not default constructor!", e);
		} catch (IllegalAccessException e) {
			logger.error("this class not support instance!", e);
		}

		// 为对象进行赋值
		for (Field field : fields) {

			field.setAccessible(true);
			String fieldName = field.getName();
			if (!columns.containsKey(fieldName)) {
				continue;
			}
			String fieldType = columns.get(fieldName);

			try {
				if (fieldType.equals("INT"))
					field.set(t, resultSet.getInt(fieldName));
				else if (fieldType.equals("LONG"))
					field.set(t, resultSet.getLong(fieldName));
				else if (fieldType.equals("VARCHAR"))
					field.set(t, resultSet.getString(fieldName));
				else if (fieldType.equals("FLOAT") || fieldType.equals("DECIMAL"))
					field.set(t, resultSet.getFloat(fieldName));
				else if (fieldType.equals("DOUBLE"))
					field.set(t, resultSet.getDouble(fieldName));
			} catch (Exception e) {
				logger.error("set fieldType occur error!", e);
			}

		}
		return t;
	}

}
