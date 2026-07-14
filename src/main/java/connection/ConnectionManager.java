package connection;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {

	private static Connection con;

	public static Connection getConnection() {

		try {

			String databaseUrl = System.getenv("DATABASE_URL");

			// Running on Heroku (PostgreSQL)

			URI dbUri = new URI(databaseUrl);

			String username = dbUri.getUserInfo().split(":")[0];
			String password = dbUri.getUserInfo().split(":")[1];

			String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();

			Class.forName("org.postgresql.Driver");

			con = DriverManager.getConnection(jdbcUrl, username, password);

			System.out.println("Connected to Heroku PostgreSQL");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}
}