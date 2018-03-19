package model.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import model.CourseInfo.GolfCourse;
import model.CourseInfo.HoleInfo;
import model.CourseInfo.TeeInfo;

public class DBgolfcourse {

	//gets a Golf Course out of db 
	public static GolfCourse getGolfCourse(int courseID) {
		String sql = " SELECT * FROM GolfCourse WHERE GolfCourse.CourseID = "+ courseID + ";";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql,con);
		try {
			while (res.next()) {
				return new GolfCourse(res.getInt("CourseID"),res.getString("cName"),res.getString("Location"),res.getInt("Size"),loadTeeInfo(courseID));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBcommands.disconnect(con);
		}
		return null;
	}

	// Returns a list of golf courses that are linked to a club house
	public static List<GolfCourse> getGolfClubHouse(String chEmail) {
		String sql = " SELECT * FROM GolfCourse INNER JOIN ClubHouseCourses ON ClubHouseCourses.CourseID=GolfCourse.CourseID"
				+ " WHERE ClubHouseCourses.Email = " + chEmail + ";";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql,con);
		List<GolfCourse> gcList = new ArrayList<GolfCourse>();
		try {
			while (res.next()) {
				gcList.add(new GolfCourse(res.getInt("CourseID"), res.getString("cName"), res.getString("Location"),
						res.getInt("Size"), loadTeeInfo(res.getInt("CourseID"))));
			}
		} catch (SQLException e) {
			// TODO
			e.printStackTrace();
		} finally {
			DBcommands.disconnect(con);
		}
		return gcList;
	}
	
	// Returns a list of all the golf courses in the database that contain the
	// query and that state input
	public static List<GolfCourse> queryGolfCourses(String uQuery, String state) {
		String sql = " SELECT * FROM GolfCourse INNER JOIN ClubHouseCourses ON ClubHouseCourses.CourseID=GolfCourse.CourseID;";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql,con);
		List<GolfCourse> gcList = new ArrayList<GolfCourse>();
		try {
			while (res.next()) {
				if( ( res.getString("cName").contains(uQuery) || res.getString("Location").contains(uQuery) ) && res.getString("Location").contains(state) ){
					gcList.add(new GolfCourse(res.getInt("CourseID"), res.getString("cName"), res.getString("Location"),
						res.getInt("Size"), getTeeInfo(res.getInt("CourseID"))));
				}
			}
		} catch (SQLException e) {
			// TODO
			e.printStackTrace();
		} finally {
			DBcommands.disconnect(con);
		}
		return gcList;
	}

	//Inserts a new Golf Course into the DB
	public static boolean insertGolfCourse(GolfCourse gc){
		gc.setID(newCourseID());
		String sql = "INSERT INTO GolfCourse VALUES ("+gc.getID()+", '"+gc.getCourseName()+"', '"+ gc.getLocation()+"', "+
				gc.getNumHoles()+");";
		return DBcommands.insertSQL(sql);
	}

	//Updates an existing Golf Course in the DB
	public static boolean updateGolfCourse(GolfCourse gc){
		String sql = "UPDATE Golfer SET cName = '"+gc.getCourseName()+"' ,Location = '"+gc.getLocation()+"' ,Size = "+ gc.getNumHoles()+
				" WHERE CourseID = "+gc.getID()+";";
		return DBcommands.insertSQL(sql);
	}
	
	public static List<TeeInfo> getAllTeeInfo(){
		String sql = " SELECT * FROM TeeInfo;";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql,con);
		List<TeeInfo> tiList = new ArrayList<TeeInfo>();
		try {
			while (res.next()) {
				tiList.add(new TeeInfo(res.getInt("TeeID"),res.getString("tName"),toColor(res.getString("Color")),
						res.getInt("Slope"),res.getDouble("Rating"),getHoleInfo(res.getInt("TeeID")),intTObool(res.getInt("Hide"))) );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBcommands.disconnect(con);
		}
		return tiList;
	}

	// Load all Tees out of the db linked to a course TODO sql not returning data
	public static List<TeeInfo> loadTeeInfo(int courseID) {
		List<TeeInfo> tees = new ArrayList<TeeInfo>();
		String sql = "SELECT * FROM TeeInfo INNER JOIN HoleInfo ON HoleInfo.TeeID = TeeInfo.TeeID"
				+ " WHERE TeeInfo.CourseID = " + courseID + " ORDER BY TeeInfo.tName" + ";";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql,con);
		try {
			if(res.getFetchSize() == 0){
				return tees;
			}
			TeeInfo curTee = new TeeInfo(res.getString("TeeInfo.tName"), toColor(res.getString("TeeInfo.Color")), res.getInt("TeeInfo.Slope"),
					res.getDouble("TeeInfo.Rating"), new ArrayList<HoleInfo>());
			while (res.next()) {
				if (res.getString("TeeInfo.tName").equals(curTee.getTeeName())) {
					curTee.addHole(new HoleInfo(res.getInt("HoleInfo.HoleNumber"), res.getInt("HoleInfo.Distance"),
							res.getInt("HoleInfo.Handicap"), res.getInt("HoleInfo.Par"), res.getDouble("HoleInfo.GreenLoc"),intTObool(res.getInt("HoleInfo.Hide"))));
				} else {
					tees.add(curTee);
					curTee = new TeeInfo(res.getString("TeeInfo.tName"), toColor(res.getString("TeeInfo.Color")), res.getInt("TeeInfo.Slope"),
							res.getDouble("TeeInfo.Rating"), new ArrayList<HoleInfo>());
					curTee.addHole(new HoleInfo(res.getInt("HoleInfo.HoleNumber"), res.getInt("HoleInfo.Distance"),
							res.getInt("HoleInfo.Handicap"), res.getInt("HoleInfo.Par"), res.getDouble("HoleInfo.GreenLoc"),intTObool(res.getInt("HoleInfo.Hide"))));
				}

			}
			tees.add(curTee);
		} catch (SQLException e) {
			// TODO
			e.printStackTrace();
		} finally {
			DBcommands.disconnect(con);
		}
		return tees;
	}
	
	//Gets TeeInfo from the DB that all have the same CourseID
	public static List<TeeInfo> getTeeInfo(int courseID){
		String sql = " SELECT * FROM TeeInfo WHERE CourseID = "+ courseID + ";";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql,con);
		List<TeeInfo> tiList = new ArrayList<TeeInfo>();
		try {
			while (res.next()) {
				tiList.add(new TeeInfo(res.getInt("TeeID"),res.getString("tName"),toColor(res.getString("Color")),
						res.getInt("Slope"),res.getDouble("Rating"),getHoleInfo(res.getInt("TeeID")),intTObool(res.getInt("Hide"))) );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBcommands.disconnect(con);
		}
		return tiList;
	}
	
	//For inserting a new TeeInfo into DB
	public static boolean insertTeeInfo(GolfCourse gc,TeeInfo ti){
		ti.setID(newTeeID());
		String sql = "INSERT INTO TeeInfo VALUES ("+ti.getID()+", "+gc.getID()+", '"+ ti.getTeeName()+"', '"+
				ti.getTeeColor()+"', "+ti.getSlope()+", "+ti.getRating()+", "+boolTOint(ti.showTee())+");";
		return DBcommands.insertSQL(sql);
	}
	
	//Updates an Existing TeeInfo in the DB
	public static boolean updateTeeInfo(TeeInfo ti){
		String sql = "UPDATE HoleInfo SET tNAme = '"+ti.getTeeName()+"' ,Color = '"+ti.getTeeColor()+"' ,Slope = "+
				ti.getSlope()+" ,Rating = "+ti.getRating()+" ,Hide = "+boolTOint(ti.showTee())+
				" WHERE TeeID = "+ti.getID() +";";
		return DBcommands.insertSQL(sql);
	}
	
	public static List<HoleInfo> getAllHoleInfo(){
		String sql = " SELECT * FROM HoleInfo;";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql,con);
		List<HoleInfo> tiList = new ArrayList<HoleInfo>();
		try {
			while (res.next()) {
				tiList.add(new HoleInfo(res.getInt("HoleNum"), res.getInt("Distance"), res.getInt("Handicap"),
						res.getInt("Par"), res.getDouble("GreenLoc"), intTObool(res.getInt("Hide"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBcommands.disconnect(con);
		}
		return tiList;
	}
	
	// Gets HoleInfo from the DB that all have the same TeeID
	public static List<HoleInfo> getHoleInfo(int teeID) {
		String sql = " SELECT * FROM HoleInfo WHERE TeeID = " + teeID + ";";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql, con);
		List<HoleInfo> hiList = new ArrayList<HoleInfo>();
		try {
			while (res.next()) {
				hiList.add(new HoleInfo(res.getInt("HoleNum"), res.getInt("Distance"), res.getInt("Handicap"),
						res.getInt("Par"), res.getDouble("GreenLoc"), intTObool(res.getInt("Hide"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBcommands.disconnect(con);
		}
		return hiList;
	}
	
	//Inserts a new HoleInfo into the DB
	public static boolean insertHoleInfo(TeeInfo ti, HoleInfo hi){
		String sql = "INSERT INTO HoleInfo VALUES ("+ti.getID()+", "+hi.getHoleNum()+", "+ hi.getDistance()+", "+
				hi.getHandicap()+", "+hi.getPAR()+", "+hi.getGreenLoc()+", "+boolTOint(hi.getHide())+");";
		return DBcommands.insertSQL(sql);
	}
	
	//Updates an existing HoleInfo in the DB
	public static boolean updateHoleInfo(TeeInfo ti, HoleInfo hi){
		String sql = "UPDATE HoleInfo SET Distance = "+hi.getDistance()+" ,Handicap = "+hi.getHandicap()+" ,Par = "+
				hi.getPAR()+" ,GreenLoc = "+hi.getGreenLoc()+" ,Hide = "+boolTOint(hi.getHide())+
				" WHERE TeeID = "+ti.getID()+" AND HoleNum = "+ hi.getHoleNum() +";";
		return DBcommands.insertSQL(sql);
	}

	//Makes a new unused CourseID. Note: MAX(CourseID) did not work
	private static int newCourseID() {
		String sql = "SELECT CourseID FROM GolfCourse ORDER BY CourseID DESC;";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql,con);
		try {
			while (res.next()) {
				// get highest ID num
				return res.getInt("CourseID")+1;
			}
		} catch (SQLException e) {
			// TODO
			e.printStackTrace();
		} finally {
			DBcommands.disconnect(con);
		}
		return 1;
	}
	
	//Makes a new unused TeeID. Note: MAX(TeeID) did not work
	private static int newTeeID() {
		String sql = "SELECT TeeID FROM TeeInfo ORDER BY TeeID DESC;";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql,con);
		try {
			while (res.next()) {
				// get highest ID num
				return res.getInt("TeeID")+1;
			}
		} catch (SQLException e) {
			// TODO
			e.printStackTrace();
		} finally {
			DBcommands.disconnect(con);
		}
		return 1;
	}
	
	//Converts int to bool
	private static boolean intTObool(int i) {
		if (i == 0)
			return false;
		else
			return true;
	}
	//Converts bool to int
	private static int boolTOint(boolean b) {
		if (b)
			return 1;
		else
			return 0;
	}
	
	//Turns a String into a Color
	private static Color toColor(String col) {
		return Color.valueOf(col);
	}

}
