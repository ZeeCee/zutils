package zutilities.jdbcbeanmodel.db.tables;

import java.sql.*;

import zutilities.jdbcbeanmodel.db.ConnectDB;
import zutilities.jdbcbeanmodel.db.DBType;
import zutilities.jdbcbeanmodel.db.beans.RestList;


public class RestListManager
{
	public static void displayData()
	    {
		String sql = "select * from rest_list ";

		try (Connection conn = ConnectDB.getConnection(DBType.MYSQL))
		    {
			PreparedStatement stat = conn.prepareStatement(sql);
			ResultSet rsAll = stat.executeQuery();
			System.out.println("Display Table 'rest_list': ");

			while (rsAll.next())
			    {
				int restID = rsAll.getObject("rest_id", Integer.class);
				String restName = rsAll.getObject("rest_name", String.class);
				Double restRating = rsAll.getObject("rest_avg_rating", Double.class);
				int catID = rsAll.getObject("cat_id", Integer.class);

				System.out.println(restID + " - " + restName + ", " + restRating + " - " + catID);
			    }

		    } catch (SQLException sqle)
		    {
			ConnectDB.processException(sqle);
		    } catch (Exception e)
		    {
			e.printStackTrace();
		    }

	    }

	public static RestList getRow(int restID)
	    {

		String sql = "select * from rest_list where rest_id = ?";
		RestList bean = new RestList();

		try (Connection conn = ConnectDB.getConnection(DBType.MYSQL))
		    {
			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, Integer.toString(restID));
			ResultSet rsRow = stat.executeQuery();

			bean.setRestID(rsRow.getInt("rest_id"));
			bean.setRestName(rsRow.getString("rest_name"));
			bean.setRestRating(rsRow.getDouble("rest_avg_rating"));
			bean.setCatID(rsRow.getInt("cat_id"));

		    } catch (SQLException sqle)
		    {
			ConnectDB.processException(sqle);
			return null;
		    } catch (Exception e)
		    {
			e.printStackTrace();
		    }
		
		return bean;
	    }

	public static boolean insert(RestList bean)
	    {
		// TODO

		String sql = "insert into rest_list (rest_name, cat_id) values (? , ? )";
		ResultSet keys = null;

		try (Connection conn = ConnectDB.getConnection(DBType.MYSQL))
		    {
			PreparedStatement stat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stat.setString(1, bean.getRestName());
			stat.setInt(2, bean.getCatID());

			int success = stat.executeUpdate();

			if (success == 1)
			    {
				keys = stat.getGeneratedKeys();
				// Notice: the cursor start at beforeFirst().
				keys.next();
				int newKey = keys.getInt(1);
				bean.setRestID(newKey);
			    } else
			    {
				System.out.println("No row affected.");
			    }

		    } catch (SQLException sqle)
		    {
			ConnectDB.processException(sqle);
			return false;
		    } catch (Exception e)
		    {
			e.printStackTrace();
		    }

		return true;
	    }

    }

