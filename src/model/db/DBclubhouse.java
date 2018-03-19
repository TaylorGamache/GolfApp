package model.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.CourseInfo.GolfCourse;
import model.UserInfo.ClubHouseInfo;
import model.UserInfo.UserClubHouse;

public class DBclubhouse {

	// Gets a ClubHouse out of the DB
	public UserClubHouse getClubHouse(String email, String pw) {
		String sql = " SELECT * FROM ClubHouse WHERE (Email = " + email + ") AND (Pword = " + pw + ");";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql,con);
		try {
			while (res.next()) {
				UserClubHouse u = new UserClubHouse(res.getString("Email"), res.getString("Pword"), new ClubHouseInfo(
						res.getString("chName"),res.getString("Phone"),res.getString("Link"),DBgolfcourse.getGolfClubHouse(email)));
				
				return u;
			}
		} catch (SQLException e) {
			// TODO
			e.printStackTrace();
		} finally {
			DBcommands.disconnect(con);
		}
		return null;
	}

	// Inserts a new ClubHouse into the DB
	public boolean insertClubHouse(UserClubHouse u) {
		String sql = "INSERT INTO ClubHouse VALUES (" + u.getEmail() + ", " + u.getUserName() + ", " + u.getPWord() + ", "
				+ u.getChInfo().getPhone() + ", " + u.getChInfo().getLink() + ");";
		return DBcommands.insertSQL(sql);
	}

	// Updates an Existing ClubHouse in the DB
	public boolean updateClubHouse(UserClubHouse u) {
		String sql = "UPDATE ClubHouse SET chName = " + u.getUserName() + " ,Pword = " + u.getPWord() + " ,Phone = "
				+ u.getChInfo().getPhone() + " ,Link = " + u.getChInfo().getLink() + " WHERE Email = " + u.getEmail() + ";";
		return DBcommands.insertSQL(sql);
	}
	
	// Inserts a new relation in ClubHouseCourses in the DB
	public boolean addCourseToClubHouse(UserClubHouse u, GolfCourse gc) {
		String sql = "INSERT INTO ClubHouseCourses VALUES (" + u.getEmail() + ", " + gc.getID()+");";
		return DBcommands.insertSQL(sql);
	}

	// Removes a relation in ClubHouseCourses in the DB
	public boolean removeCourseToClubHouse(UserClubHouse u, GolfCourse gc) {
		String sql = "DELETE FROM ClubHouseCourses WHERE Email ="+ u.getEmail() + " AND CourseID = " + gc.getID()+";";
		return DBcommands.insertSQL(sql);
	}

}
