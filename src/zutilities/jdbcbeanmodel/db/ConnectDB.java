package zutilities.jdbcbeanmodel.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class ConnectDB
    {

	// ConnectDB.getConnection(DBType.MYSQL)
	public static Connection getConnection(DBType dbtype) throws IOException, SQLException
	    {
		// load the properties file of DB connection

		Properties props = new Properties();
		try (InputStream in = Files.newInputStream(Paths.get("dbconn.properties")))
		    {
			props.load(in);
		    }

		String url;
		String username = props.getProperty("JDBC.USERNAME");
		String password = props.getProperty("JDBC.PASSWORD");

		switch (dbtype)
		    {
		    case MYSQL:
			url = props.getProperty("JDBC.MYURL");
			break;
		    case SQLITE:
			url = props.getProperty("JDBC.LITEURL");
			break;
		    default:
			url = null;
		    }

		return DriverManager.getConnection(url, username, password);
	    }

	public static void processException(SQLException sqle)
	    {
		System.err.println("Error message: " + sqle.getMessage());
		System.err.println("Error code: " + sqle.getErrorCode());
		System.err.println("SQL state: " + sqle.getSQLState());
	    }
    }
