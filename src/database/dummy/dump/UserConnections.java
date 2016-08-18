package database.dummy.dump;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Statement;

public class UserConnections {
	private Integer maxUserID;

	public void setMaxUserID(Integer nrOfUsers) {
		maxUserID = nrOfUsers;
	}
	
	private long getRandomTimeStamp() {
		long current = System.currentTimeMillis();
		return (long) (current * Math.random());
	}
	
	public void insertConnections() {
		for (int user = 1; user <= maxUserID; user++) {
			for (int i = 1; i < 11; i++) {
				Integer follower = (int) ((Math.random() * (maxUserID)));
				if (follower == user) {
					i--;
					continue;
				}
				Statement statement = SQLConnection.getExecutableStatement();
				String query = "insert into connections values(" + follower + "," + user + "," + getRandomTimeStamp() + ")";
				try {
					statement.executeQuery(query);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Couldn't execute " + query);
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		UserConnections userConnections = new UserConnections();
		userConnections.setMaxUserID();
		userConnections.insertConnections();
		
	}
}
