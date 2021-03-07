package android.lesson.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class LessonJNDIDAO implements LessonDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DA105G5");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = " INSERT INTO LESSON VALUES ('LE'||LPAD(to_char(LESNO_SEQ.NEXTVAL), 4, '0'),"
			+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String DELETE_LES = "DELETE FROM LESSON WHERE LES_NO = ? ";

	private static final String GET_ONE_LESSON = "SELECT * FROM LESSON WHERE LES_NO = ?";
	private static final String GET_LESSON_BY_COACH = "SELECT * FROM LESSON WHERE COACH = ? ORDER BY LES_NO";
	private static final String GET_LESSON_BY_DIVESHOP = "SELECT * FROM LESSON WHERE DS_NO = ?";
	private static final String GET_LESSON_BY_LESSONNAME = "SELECT * FROM LESSON WHERE LES_NAME = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM LESSON";
    

	private static final String UPDATE = "UPDATE LESSON SET LES_NAME = ?,LES_INFO = ?,COACH = ?,COST = ?,DAYS = ?,"
			+ "SIGNUP_STARTDATE = ?,SIGNUP_ENDDATE = ?,LES_STATE = ?,LES_MAX = ?,LES_LIMIT = ?, LES_STARTDATE =?, "
			+ "LES_ENDDATE =?, LESS_STATE = ? WHERE LES_NO = ? AND DS_NAME = ?AND DS_NO = ?";

	
	
	@Override
	public LessonVO findByPrimaryKey(String les_no) {

		LessonVO lessonVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_LESSON);

			pstmt.setString(1, les_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				lessonVO = new LessonVO();
				lessonVO.setLes_no(rs.getString("les_no"));
				lessonVO.setLes_name(rs.getString("les_name"));
				lessonVO.setDs_no(rs.getString("ds_no"));
				lessonVO.setDs_name(rs.getString("ds_name"));
				lessonVO.setLes_info(rs.getString("les_info"));
				lessonVO.setCoach(rs.getString("coach"));
				lessonVO.setCost(rs.getInt("cost"));
				lessonVO.setDays(rs.getInt("days"));
				lessonVO.setSignup_startdate(rs.getDate("signup_startdate"));
				lessonVO.setSignup_enddate(rs.getDate("signup_enddate"));
				lessonVO.setLes_state(rs.getString("les_state"));
				lessonVO.setLes_max(rs.getInt("les_max"));
				lessonVO.setLes_limit(rs.getInt("les_limit"));
				lessonVO.setLes_startdate(rs.getDate("les_startdate"));
				lessonVO.setLes_enddate(rs.getDate("les_enddate"));
				lessonVO.setLess_state(rs.getString("less_state"));

			}

		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return lessonVO;
	}

	@Override
	public List<LessonVO> findByCoach(String coach) {
		List<LessonVO> lessonList = new ArrayList<LessonVO>();
		LessonVO lessonVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LESSON_BY_COACH);
			pstmt.setString(1, coach);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				lessonVO = new LessonVO();
				lessonVO.setLes_no(rs.getString("les_no"));
				lessonVO.setLes_name(rs.getString("les_name"));
				lessonVO.setDs_no(rs.getString("ds_no"));
				lessonVO.setDs_name(rs.getString("ds_name"));
				lessonVO.setLes_info(rs.getString("les_info"));
				lessonVO.setCoach(rs.getString("coach"));
				lessonVO.setCost(rs.getInt("cost"));
				lessonVO.setDays(rs.getInt("days"));
				lessonVO.setSignup_startdate(rs.getDate("signup_startdate"));
				lessonVO.setSignup_enddate(rs.getDate("signup_enddate"));
				lessonVO.setLes_state(rs.getString("les_state"));
				lessonVO.setLes_max(rs.getInt("les_max"));
				lessonVO.setLes_limit(rs.getInt("les_limit"));
				lessonVO.setLes_startdate(rs.getDate("les_startdate"));
				lessonVO.setLes_enddate(rs.getDate("les_enddate"));
				lessonVO.setLess_state(rs.getString("less_state"));
				lessonList.add(lessonVO);
			}

		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return lessonList;
	}

	@Override
	public List<LessonVO> findByShop(String ds_no) {
		List<LessonVO> lessonList = new ArrayList<LessonVO>();
		LessonVO lessonVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LESSON_BY_DIVESHOP);

			pstmt.setString(1, ds_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				lessonVO = new LessonVO();
				lessonVO.setLes_no(rs.getString("les_no"));
				lessonVO.setLes_name(rs.getString("les_name"));
				lessonVO.setDs_no(rs.getString("ds_no"));
				lessonVO.setDs_name(rs.getString("ds_name"));
				lessonVO.setLes_info(rs.getString("les_info"));
				lessonVO.setCoach(rs.getString("coach"));
				lessonVO.setCost(rs.getInt("cost"));
				lessonVO.setDays(rs.getInt("days"));
				lessonVO.setSignup_startdate(rs.getDate("signup_startdate"));
				lessonVO.setSignup_enddate(rs.getDate("signup_enddate"));
				lessonVO.setLes_state(rs.getString("les_state"));
				lessonVO.setLes_max(rs.getInt("les_max"));
				lessonVO.setLes_limit(rs.getInt("les_limit"));
				lessonVO.setLes_startdate(rs.getDate("les_startdate"));
				lessonVO.setLes_enddate(rs.getDate("les_enddate"));
				lessonVO.setLess_state(rs.getString("less_state"));
				lessonList.add(lessonVO);

			}

		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return lessonList;
	}

	@Override
	public List<LessonVO> findByLessonName(String les_name) {
		List<LessonVO> lessonList = new ArrayList<LessonVO>();
		LessonVO lessonVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LESSON_BY_LESSONNAME);

			pstmt.setString(1, les_name);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				lessonVO = new LessonVO();
				lessonVO.setLes_no(rs.getString("les_no"));
				lessonVO.setLes_name(rs.getString("les_name"));
				lessonVO.setDs_no(rs.getString("ds_no"));
				lessonVO.setDs_name(rs.getString("ds_name"));
				lessonVO.setLes_info(rs.getString("les_info"));
				lessonVO.setCoach(rs.getString("coach"));
				lessonVO.setCost(rs.getInt("cost"));
				lessonVO.setDays(rs.getInt("days"));
				lessonVO.setSignup_startdate(rs.getDate("signup_startdate"));
				lessonVO.setSignup_enddate(rs.getDate("signup_enddate"));
				lessonVO.setLes_state(rs.getString("les_state"));
				lessonVO.setLes_max(rs.getInt("les_max"));
				lessonVO.setLes_limit(rs.getInt("les_limit"));
				lessonVO.setLes_startdate(rs.getDate("les_startdate"));
				lessonVO.setLes_enddate(rs.getDate("les_enddate"));
				lessonVO.setLess_state(rs.getString("less_state"));
				lessonList.add(lessonVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return lessonList;
	}
	
	
	@Override
	public List<LessonVO> getAll() {
		List<LessonVO> list = new ArrayList<LessonVO>();
		LessonVO lessonVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				lessonVO = new LessonVO();
				lessonVO.setLes_no(rs.getString("les_no"));
				lessonVO.setLes_name(rs.getString("les_name"));
				lessonVO.setDs_no(rs.getString("ds_no"));
				lessonVO.setDs_name(rs.getString("ds_name"));
				lessonVO.setLes_info(rs.getString("les_info"));
				lessonVO.setCoach(rs.getString("coach"));
				lessonVO.setCost(rs.getInt("cost"));
				lessonVO.setDays(rs.getInt("days"));
				lessonVO.setSignup_startdate(rs.getDate("signup_startdate"));
				lessonVO.setSignup_enddate(rs.getDate("signup_enddate"));
				lessonVO.setLes_state(rs.getString("les_state"));
				lessonVO.setLes_max(rs.getInt("les_max"));
				lessonVO.setLes_limit(rs.getInt("les_limit"));
				lessonVO.setLes_startdate(rs.getDate("les_startdate"));
				lessonVO.setLes_enddate(rs.getDate("les_enddate"));
				lessonVO.setLess_state(rs.getString("less_state"));
				list.add(lessonVO);
			}

		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	
}

