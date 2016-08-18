import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

public class DatabaseTry {
    public void dumpInDatabase(Integer id1, Integer id2) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "");
    
	    Statement stmt = (Statement) con.createStatement();
	    String query = "insert into authentication values(\"ma\",\"ma\",\"ma\",\"ma\",1)";
	    String query2 = "select * from authentication where name=\"ma\"";
	    System.out.println(query);
	    ResultSet rs = stmt.executeQuery(query2);
	    System.out.println(rs.next());
	    
//	    rs.next();
//	    System.out.println(rs.getString(1) + "   " + rs.getString(5));
    }
    public static void main(String[] args) {
    	DatabaseTry databaseTry = new DatabaseTry();
    	try {
			databaseTry.dumpInDatabase(1, 2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  	
    }
}