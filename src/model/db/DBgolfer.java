package model.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.UserInfo.GolfClub;
import model.UserInfo.UserGolfer;

public class DBgolfer {
	
	//Gets a Golfer out of the DB
	public static UserGolfer getGolfer(String email, String pw){
		String sql = " SELECT * FROM Golfer WHERE (Email = '" + email +"') AND (Pword = '"+ pw + "');";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql,con);
		try {
			while (res.next()) {
				UserGolfer u = new UserGolfer(res.getString("Name"),res.getString("Pword"),
						res.getString("Email"),new ArrayList<GolfClub>());
				u.setStats(res.getDouble("AveScore"),res.getInt("NOG"),res.getDouble("Handicap"));
				return u;
			}
		} catch (SQLException e) {
			//TODO
			e.printStackTrace();
		} finally {
			DBcommands.disconnect(con);
		}
		return null;
	}
	
	//Inserts a new Golfer into the DB
	public static boolean insertGolfer(UserGolfer u){
		String sql = "INSERT INTO Golfer VALUES ('"+u.getEmail()+"', '"+u.getUserName()+"', '"+ u.getPWord()+"', "+
						u.getAveNineScore()+", "+u.getNumGames()+", "+u.getHandicap()+");";
		return DBcommands.insertSQL(sql);
	}
	
	//Updates an Existing Golfer in the DB
	public static boolean updateGolfer(UserGolfer u){
		String sql = "UPDATE Golfer SET Name = '"+u.getUserName()+"' ,Pword = '"+u.getPWord()+"' ,AveScore = "+
				u.getAveNineScore()+" ,NOG = "+u.getNumGames()+" ,Handicap = "+ u.getHandicap() +
				" WHERE Email = '"+u.getEmail()+"';";
		return DBcommands.insertSQL(sql);
	}
	
	//Gets a Users Clubs out of the DB in adds them into his clubs list
	public static void getGolfersClubs(UserGolfer u){
		//Get the users clubs
		String sql = "SELECT * FROM GolfClubs WHERE Email = '"+u.getEmail()+"';";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql,con);
		try {
			while(res.next()){
				u.addClub(new GolfClub(res.getInt("gcID"),res.getString("Type"),res.getString("Brand"),res.getString("Model"),
						res.getDouble("MaxDis"),res.getDouble("AveDis"),res.getInt("NOH")));
			}
		} catch (Exception e) {
			//TODO
			e.printStackTrace();
		} finally {
			DBcommands.disconnect(con);
		}
	}
	
	//Adds a golf club into the GolfClubs table
	public static boolean insertGolfClub(UserGolfer u, GolfClub gc){
		gc.setID(newID());
		String sql = "INSERT INTO GolfClubs VALUES ("+gc.getID()+", '"+u.getEmail()+"', '"+ gc.getBrand()+"', '"+
						gc.getType()+"', '"+gc.getModel()+"', "+gc.getMaxD()+", "+gc.getAveD()+", "+gc.getNumHits()+");";
		return DBcommands.insertSQL(sql);
	}
	
	//Updates a golf clubs info
	public static boolean updateGolfClub(GolfClub gc){
		String sql = "UPDATE GolfClubs SET Brand = '"+gc.getBrand()+"' ,Type = '"+gc.getType()+"' ,Model = '"+gc.getModel()+
				"' ,AveDis = "+gc.getAveD()+" ,MaxDis = "+ gc.getMaxD() +" ,NOH = "+ gc.getNumHits() +
				" WHERE gcID = "+gc.getID()+";";
		return DBcommands.insertSQL(sql);
	}
	
	//Makes a new ID 1 int higher than the current highest. Note:MAX(gcID) didn't work
	public static int newID() {
		String sql = "SELECT gcID FROM GolfClubs ORDER BY gcID DESC;";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql,con);
		try {
			while (res.next()) {
				// get highest ID num
				return res.getInt("gcID")+1;
			}
		} catch (SQLException e) {
			//TODO
			e.printStackTrace();
		} finally {
			DBcommands.disconnect(con);
		}
		return 1;
	}

}
