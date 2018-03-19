package model.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.CourseInfo.TempGolfCourse;
import model.GolfScore.GolfScore;
import model.GolfScore.GolfScore18Hole;
import model.GolfScore.GolfScore9Hole;
import model.GolfScore.PlayerCard;
import model.GolfScore.ScoreCard;
import model.UserInfo.UserGolfer;
import model.UserInfo.UserTemp;

public class DBgolfscore {

	// Inserts a golf score into the database
	public static void insertScore(GolfScore gs) {
		//INSERT into Scores table
		gs.setID(newID());
		String sqlGS = " INSERT INTO Scores VALUES";
		// Only will need TempCourse in DB if its in a ScoreCard
		if (gs.getCourse().getClass() == TempGolfCourse.class) {
			// DBgolfcourse.insertCourse(gs.getCourse());
		}
		if(gs.getClass() == GolfScore9Hole.class){
			sqlGS += " (" + gs.getID() + ", " + gs.getCourse().getID() + ", " + gs.getReadableDate() + ", " + "9, "
				+ boolTOint(((GolfScore9Hole) gs).isFront()) + ");";
		} else {
			sqlGS += " (" + gs.getID() + ", " + gs.getCourse().getID() + ", " + gs.getReadableDate() + ", " + "18, 1);";
		}
		DBcommands.insertSQL(sqlGS);
		
		//INSERT into UserScores table
		for(int i = 0; i < gs.getUsers().size(); i++){
			if(gs.getUsers().get(i).getClass() != UserTemp.class){
				sqlGS = " INSERT INTO UserScores VALUES ("+((UserGolfer) gs.getUsers().get(i)).getEmail()+", "+gs.getID() +");";
				DBcommands.insertSQL(sqlGS);
			}
		}
		
		//INSERT into ScoreCard table
		for(int i = 0; i<gs.getScoreCard().getPlayerCards().size(); i++){
			sqlGS = " INSERT INTO ScoreCard VALUES ("+gs.getID()+", "+gs.getScoreCard().getPlayerCards().get(i).getUName()+", "
					+gs.getScoreCard().getPlayerCards().get(i).formatScores()+", "+gs.getScoreCard().getPlayerCards().get(i).getTeeName() +");";
			DBcommands.insertSQL(sqlGS);
		}
	}

	// Returns a list of Golf scores that link to an email account
	public static List<GolfScore> getUserScores(String email) {
		String sql = " SELECT * FROM Scores INNER JOIN ScoreCard ON Scores.ScoreID=ScoreCard.ScoreID INNER JOIN "+
					 "UserScores ON UserScores.ScoreID = Scores.ScoreID WHERE UserScores.Email = " + email+ ";";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql,con);
		try {
			List<GolfScore> lScores = new ArrayList<GolfScore>();
			while (res.next()) {
				List<PlayerCard> pcList = new ArrayList<PlayerCard>();
				pcList.add(new PlayerCard(res.getString("UName"), res.getString("scores"), res.getString("tName")));
				Integer indx = IDcheck(res, lScores);
				// If ScoreID already exists in lScores than add player card
				if (indx != null) {
					lScores.get(indx).getScoreCard().addPlayerCard(
							new PlayerCard(res.getString("UName"), res.getString("scores"), res.getString("tName")));
				} else if (res.getInt("GameSize") == 9) {
					GolfScore gs = new GolfScore9Hole(res.getInt("ScoreID"),res.getInt("courseID"), res.getString("Date"),
							new ScoreCard(pcList), dbBoolTest(res.getInt("")));
					lScores.add(gs);
				} else {
					GolfScore gs = new GolfScore18Hole(res.getInt("ScoreID"),res.getInt("courseID"), res.getString("Date"),
							new ScoreCard(pcList));
					lScores.add(gs);
				}
			}
			return lScores;
		} catch (SQLException e) {
			//TODO
			e.printStackTrace();
		} finally {
			DBcommands.disconnect(con);
		}
		return null;
	}

	// Returns a specific games golf score
	public static GolfScore getScore(int scoreID) {
		String sql = " SELECT * FROM Scores INNER JOIN ScoreCard ON Scores.ScoreID=ScoreCard.ScoreID"
				+ " WHERE Scores.ScoreID = " + scoreID + ";";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql,con);
		try {
			List<PlayerCard> pcList = new ArrayList<PlayerCard>();
			ResultSet rs = res;
			while (res.next()) {
				pcList.add(new PlayerCard(res.getString("UName"), res.getString("scores"), res.getString("tName")));
			}
			if (res.getInt("GameSize") == 9) {
				return new GolfScore9Hole(rs.getInt("ScoreID"),rs.getInt("courseID"), rs.getString("Date"), new ScoreCard(pcList),
						dbBoolTest(rs.getInt("")));
			} else {
				return new GolfScore18Hole(rs.getInt("ScoreID"),rs.getInt("courseID"), rs.getString("Date"), new ScoreCard(pcList));
			}
		} catch (SQLException e) {
			//TODO
			e.printStackTrace();
		} finally {
			DBcommands.disconnect(con);
		}
		return null;
	}

	// if value a 1 than true else false
	private static boolean dbBoolTest(int val) {
		if (val == 1)
			return true;
		else
			return false;
	}

	// returns the index of an existing scoreID
	private static Integer IDcheck(ResultSet res, List<GolfScore> lgs) throws SQLException {
		for (int i = 0; i < lgs.size(); i++) {
			if (res.getInt("ScoreID") == lgs.get(i).getID())
				return i;
		}
		return null;
	}

	// turns a boolean to int for database
	protected static int boolTOint(boolean bool) {
		if (bool)
			return 1;
		else
			return 0;
	}

	protected static boolean intTObool(int i) {
		if (i == 0)
			return false;
		else
			return true;
	}

	//Makes a new ID one higher than the previous high. Note: MAX(CourseID) did not work
	private static int newID() {
		String sql = "SELECT ScoreID FROM Scores ORDER BY ScoreID DESC;";
		Connection con = DBcommands.connect();
		ResultSet res = DBcommands.querySQL(sql,con);
		try {
			while (res.next()) {
				// get highest ID num
				return res.getInt("ScoreID")+1;
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
