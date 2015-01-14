package zutilities.jdbcbeanmodel.db;

import java.io.IOException;
import java.sql.SQLException;
import zutilities.jdbcbeanmodel.db.beans.RestList;
import zutilities.jdbcbeanmodel.db.tables.RestListManager;

public class Main
    {

	public static void main(String[] args) throws SQLException, IOException
	    {
		// TODO Auto-generated method stub
		RestListManager.displayData();

		RestList bean = new RestList();

		bean.setRestName("Burger King");
		bean.setCatID(0);

		boolean success = RestListManager.insert(bean);

		if (success)
		    {
			// PK Auto-Increment; returned from stat.getGeneratedKeys()
			// Q: to retrieve DEFAULT value of inserted row,
			// Current_timestamp?
			System.out.println("New row inserted: " +  bean.getRestID() + " , " + bean.getRestName()
				+ " , " + bean.getCatID() );
		    }
	    }

    }
