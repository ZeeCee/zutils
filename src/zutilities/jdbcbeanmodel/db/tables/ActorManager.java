package zutilities.jdbcbeanmodel.db.tables;


import java.sql.*;

import zutilities.jdbcbeanmodel.db.ConnectDB;
import zutilities.jdbcbeanmodel.db.DBType;
import zutilities.jdbcbeanmodel.db.beans.Actor;

public class ActorManager
    {
	public static void displayData()
	    {
		String sql = "select * from actor2 ";

		try (Connection conn = ConnectDB.getConnection(DBType.MYSQL))
		    {
			PreparedStatement stat = conn.prepareStatement(sql);
			ResultSet rsAll = stat.executeQuery();
			System.out.println("Display Table Actor: ");

			while (rsAll.next())
			    {
				// int actorID = rs.getInt("actor_id");
				// String firstName =
				// rs.getString("first_name");
				// String lastName = rs.getString("last_name");

				int actorID = rsAll.getObject("actor_id", Integer.class);
				String firstName = rsAll.getObject("first_name", String.class);
				String lastName = rsAll.getObject("last_name", String.class);
				Timestamp tStamp = rsAll.getObject("last_update", Timestamp.class);

				System.out.println(actorID + " - " + initcap(firstName) + ", " + initcap(lastName) + " //- " + tStamp);
			    }

		    } catch (SQLException sqle)
		    {
			ConnectDB.processException(sqle);
		    } catch (Exception e)
		    {
			e.printStackTrace();
		    }

	    }

	public static Actor getRow(int actorId)
	    {

		String sql = "select * from actor2 where actor_id = ?";
		Actor bean = new Actor();

		try (Connection conn = ConnectDB.getConnection(DBType.MYSQL))
		    {
			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, Integer.toString(actorId));
			ResultSet rsRow = stat.executeQuery();

			bean.setActor_id(rsRow.getInt("actor_id"));
			bean.setFirst_name(rsRow.getString("first_name"));
			bean.setLast_name(rsRow.getString("last_name"));
			bean.setLast_update(rsRow.getTimestamp("last_update"));

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

	public static boolean insert(Actor bean)
	    {
		// TODO

		String sql = "insert into actor2 (first_name, last_name) values (? , ? )";
		ResultSet keys = null;

		try (Connection conn = ConnectDB.getConnection(DBType.MYSQL))
		    {
			PreparedStatement stat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stat.setString(1, bean.getFirst_name());
			stat.setString(2, bean.getLast_name());

			int success = stat.executeUpdate();

			if (success == 1)
			    {
				keys = stat.getGeneratedKeys();
				// Notice: the cursor start at beforeFirst().
				keys.next();
				int newKey = keys.getInt(1);
				bean.setActor_id(newKey);
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

	private static String initcap(String line)
	    {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1).toLowerCase();
	    }

    }
