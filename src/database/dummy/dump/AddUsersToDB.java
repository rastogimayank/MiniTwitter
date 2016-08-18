package database.dummy.dump;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddUsersToDB {
	private String filePath;
	private BufferedReader fileReader;
	private Statement statement;
	
	public AddUsersToDB(String filePath) {
		this.filePath = filePath;
		try {
			fileReader = new BufferedReader(new FileReader(this.filePath));
			statement = SQLConnection.getExecutableStatement();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not connect to the file");
			e.printStackTrace();
		}
	}
	
	public void processFile() {
		Integer user_id = 1;
		String user;
		try {
			user = fileReader.readLine();
			while (user != null) {
				String query = "select * from authentication where name=\"" + user + "\"";
				try {
						ResultSet rs = statement.executeQuery(query);
						if (!rs.next()) {
							query = "insert into authentication values(" + 
							"\"" + user + "@mail.com\"," +				// Email
							"\"" + user + "\"," + 						// Name
							"\"" + user + "\"," +						// Password
							"\"" + user + "\"," +						// Handle
							user_id + ")";								// User_ID
							// TODO to be logged
							System.out.println(statement.execute(query));
							user_id++;
						}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				user = fileReader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		AddUsersToDB addUsersToDB = new AddUsersToDB("usernames.txt");
		addUsersToDB.processFile();
	}
	
}
