/*package com.lynda.javaintegration.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.lynda.javaintegration.db.tables.ActorManager;

import java.sql.PreparedStatement;
import java.util.Scanner;

public class MainDraft
    {
	public static void main(String[] args) throws SQLException, IOException
	    {
		String limit;
		String sql = "Select * from actor2 where actor_id <= ? ";
		
		try (Scanner in = new Scanner(System.in))
		    {
			limit = in.nextLine();
		    }

		try (Connection conn = ConnectDB.getConnection(DBType.MYSQL))
		    {
			PreparedStatement pstat = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);

//			pstat.setMaxRows(20);
			pstat.setString(1, limit);

			ResultSet rs = pstat.executeQuery();

			rs.last();
			System.out.println("Number of rows: " + rs.getRow());
			rs.beforeFirst();

			ActorManager.displayData(rs);

		    } catch (SQLException sqle)
		    {
			// System.err.println(sqle);
			ConnectDB.processException(sqle);
		    } catch (Exception e) {
			System.err.println(e);
		    }
	    }

    }
*/