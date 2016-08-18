import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnection {
	public static Statement getExecutableStatement() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "");
			return con.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// TODO to be logged
			System.out.println("Could not load JDBC Connector\n");
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO to be logged
			System.out.println("Could not connect to the SQL server");
		}
		return null;
	}
}
