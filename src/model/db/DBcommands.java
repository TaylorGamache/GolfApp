package model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class DBcommands {
	//location where database is present in the system
	private static String url = "jdbc:sqlite:C:/Users/Taylor/workspace/Golfapp-Desktop/GolfAppDB.db";
	
	//Connect to the db and return the connection
	protected static Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
        	Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	//For running a query into the database
	protected static ResultSet querySQL(String sql, Connection conn){
        try {
        	Statement stmt = conn.createStatement();
        	stmt.setQueryTimeout(30);
			stmt.execute(sql);
			return stmt.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	//For updating or inserting new data
	protected static boolean insertSQL(String sql){
		Connection conn = connect();
        try {
        	Statement stmt = conn.createStatement();
        	stmt.setQueryTimeout(30);
			stmt.execute(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect(conn);
		}
	}
	
	//close the connection
	protected static void disconnect(Connection con){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO
			e.printStackTrace();
		}
	}
	
	public static void createDB(){
		String q = "CREATE TABLE IF NOT EXISTS Golfer (\n"
				+ " Email TEXT PRIMARY KEY, \n"
				+ "	Name TEXT,\n"
                + "	Pword VARCHAR(20),\n"
                + "	AveScore DOUBLE(3,2),\n"
                + "	NOG Integer,\n"
                + "	Handicap DOUBLE(3,2)\n"
                + ");";
		String q1 = "CREATE TABLE IF NOT EXISTS TeeInfo (\n"
                + "	TeeID Integer PRIMARY KEY,\n"
                + "	CourseID Integer,\n"
				+ " tName TEXT, \n"
                + "	Color TEXT,\n"
                + "	Slope Integer,\n"
                + "	Rating DOUBLE(3,2),\n"
                + "	Hide INT(1) \n"
                + ");";
		String q2 = "CREATE TABLE IF NOT EXISTS Scores (\n"
                + "	ScoreID Integer PRIMARY KEY,\n"
                + "	CourseID Integer,\n"
                + "	Date TEXT,\n"
                + "	GameSize INT(2),\n"
                + "	Front INT(1) \n"
                + ");";
		String q3 = "CREATE TABLE IF NOT EXISTS ScoreCard (\n"
				+ "	ScoreID Integer,\n"
				+ " UserName TEXT, \n"
				+ "	Scores TEXT, \n"
				+ "	tName TEXT\n"
                + ");";
		String q4 = "CREATE TABLE IF NOT EXISTS HoleInfo (\n"
                + "	TeeID Integer,\n"
				+ " HoleNum INT(2), \n"
                + "	Distance Integer,\n"
                + "	Handicap INT(2),\n"
                + "	Par INT(1),\n"
                + "	GreenLoc Double,\n"
                + " Hide INT(1)\n"
                + ");";
		String q5 = "CREATE TABLE IF NOT EXISTS GolfCourse (\n"
				+ "	CourseID Integer PRIMARY KEY,\n"
				+ " cName TEXT, \n"
                + "	Location TEXT,\n"
                + "	Size INT(2) \n"
                + ");";
		String q6 = "CREATE TABLE IF NOT EXISTS ClubHouse (\n"
                + "	Email TEXT PRIMARY KEY,\n"
				+ " chName TEXT, \n"
				+ " Pword TEXT, \n"
                + "	Phone TEXT,\n"
                + "	Link TEXT \n"
                + ");";
		String q7 = "CREATE TABLE IF NOT EXISTS GolfClubs (\n"
                + "	gcID Integer PRIMARY KEY,\n"
				+ " Email TEXT,\n"
				+ " Brand TEXT, \n"
                + "	Type TEXT,\n"
                + "	Model TEXT,\n"
                + "	MaxDis Integer,\n"
                + "	AveDis DOUBLE(3,2),\n"
                + "	NOH Integer \n"
                + ");";
		String q8 = "CREATE TABLE IF NOT EXISTS UserScores (\n"
				+ " Email TEXT, \n"
				+ " ScoreID Integer \n"
				+ ");";
		String q9 = "CREATE TABLE IF NOT EXISTS ClubHouseCourses (\n"
				+ " Email TEXT, \n"
				+ " CourseID Integer \n"
				+ ");";
		//String q = "INSERT INTO Golfer\n VALUES('mache','tgamache2016@gmail.com','GAMtay1022',47.33,3,25.14)";
		System.out.println(DBcommands.insertSQL(q));
		System.out.println(DBcommands.insertSQL(q1));
		System.out.println(DBcommands.insertSQL(q2));
		System.out.println(DBcommands.insertSQL(q3));
		System.out.println(DBcommands.insertSQL(q4));
		System.out.println(DBcommands.insertSQL(q5));
		System.out.println(DBcommands.insertSQL(q6));
		System.out.println(DBcommands.insertSQL(q7));
		System.out.println(DBcommands.insertSQL(q8));
		System.out.println(DBcommands.insertSQL(q9));
	}
	
	
}
