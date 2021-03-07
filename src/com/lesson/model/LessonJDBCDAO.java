package com.lesson.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import java.util.Map;
import java.util.Set;

import com.dspic.model.DspicVO;
import com.lespic.model.LespicJDBCDAO;
import com.lespic.model.LespicVO;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Lesson;

public class LessonJDBCDAO implements LessonDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String passwd = "DA105G5";

	private static final String INSERT_STMT = " INSERT INTO LESSON VALUES ('LE'||LPAD(to_char(LESNO_SEQ.NEXTVAL), 4, '0'),"
			+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String DELETE_LES = "DELETE FROM LESSON WHERE LES_NO = ? ";

	private static final String GET_ONE_LESSON = "SELECT * FROM LESSON WHERE LES_NO = ?";
	private static final String GET_LESSON_BY_COACH = "SELECT * FROM LESSON WHERE COACH = ? ORDER BY LES_NO";
	private static final String GET_LESSON_BY_DIVESHOP = "SELECT * FROM LESSON WHERE DS_NO = ?";
	private static final String GET_LESSON_BY_LESSONNAME = "SELECT * FROM LESSON WHERE LES_NAME = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM LESSON";
	private static final String GET_LSPIC_SEQ = "SELECT LPIC_SEQ FROM LESPIC WHERE LES_NO =?";

	
	private static final String UPDATE = "UPDATE LESSON SET LES_NAME = ?,LES_INFO = ?,COACH = ?,COST = ?,DAYS = ?,"
			+ "SIGNUP_STARTDATE = ?,SIGNUP_ENDDATE = ?,LES_STATE = ?,LES_MAX = ?,LES_LIMIT = ?, LES_STARTDATE =?, "
			+ "LES_ENDDATE =?, LESS_STATE = ? WHERE LES_NO = ? AND DS_NAME = ?AND DS_NO = ?";

	@Override
	public void insertWithLespic(LessonVO lessonVO, List<LespicVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			try {
				Class.forName(driver);

				con = DriverManager.getConnection(url, userid, passwd);
				
				//新增課程
				String cols[] = {"LES_NO"};
				pstmt = con.prepareStatement(INSERT_STMT,cols);
				pstmt.setString(1, lessonVO.getDs_no());
				pstmt.setString(2, lessonVO.getDs_name());
				pstmt.setString(3, lessonVO.getLes_name());
				pstmt.setString(4, lessonVO.getLes_info());
				pstmt.setString(5, lessonVO.getCoach());
				pstmt.setInt(6, lessonVO.getCost());
				pstmt.setInt(7, lessonVO.getDays());
				pstmt.setDate(8, lessonVO.getSignup_startdate());
				pstmt.setDate(9, lessonVO.getSignup_enddate());
				pstmt.setString(10, lessonVO.getLes_state());
				pstmt.setInt(11, lessonVO.getLes_max());
				pstmt.setInt(12, lessonVO.getLes_limit());
				pstmt.setDate(13, lessonVO.getLes_startdate());
				pstmt.setDate(14, lessonVO.getLes_enddate());
				pstmt.setString(15, lessonVO.getLess_state());
				pstmt.executeUpdate();
				
				//擷取對應的自增值主鍵
				String next_les_no = null;
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next()) {
					next_les_no = rs.getString(1);
					System.out.println("自增主鍵值=" + next_les_no + "(剛新增成功的課程編號)");
				} else {
					System.out.println("未取得自增值主鍵");
				}
				rs.close();
				
				//在同時新增課程照片
				LespicJDBCDAO dao = new LespicJDBCDAO();
				System.out.println("list.size()-A=" + list.size());
				for(LespicVO lespics : list) {
					lespics.setLes_no(next_les_no);
					dao.insert2(lespics, con);
				}
				
				// 設定於pstmt.excuteUpdate()之後
				con.commit();
				con.setAutoCommit(true);
				System.out.println("list.size()-B=" + list.size());
				System.out.println("新增課程編號" + next_les_no + "時,共有" + list.size() + "張照片同時被新增");
		
			}	catch (SQLException se) {
				if (con != null) {
					try {
						// 3●設定於當有exception發生時之catch區塊內
						System.err.print("Transaction is being ");
						System.err.println("rolled back-由-dept");
						con.rollback();
					} catch (SQLException excep) {
						throw new RuntimeException("rollback error occured. " + excep.getMessage());
					}
				}
				throw new RuntimeException("A database error occured. " + se.getMessage());
				// Clean up JDBC resources
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} finally {
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
	}
	
	@Override
	public void insert(LessonVO lessonVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, lessonVO.getDs_no());
			pstmt.setString(2, lessonVO.getDs_name());
			pstmt.setString(3, lessonVO.getLes_name());
			pstmt.setString(4, lessonVO.getLes_info());
			pstmt.setString(5, lessonVO.getCoach());
			pstmt.setInt(6, lessonVO.getCost());
			pstmt.setInt(7, lessonVO.getDays());
			pstmt.setDate(8, lessonVO.getSignup_startdate());
			pstmt.setDate(9, lessonVO.getSignup_enddate());
			pstmt.setString(10, lessonVO.getLes_state());
			pstmt.setInt(11, lessonVO.getLes_max());
			pstmt.setInt(12, lessonVO.getLes_limit());
			pstmt.setDate(13, lessonVO.getLes_startdate());
			pstmt.setDate(14, lessonVO.getLes_enddate());
			pstmt.setString(15, lessonVO.getLess_state());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
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

	}
	
	@Override
	public List<String> getlespic_seqByLess_no(String les_no) {
		List<String> list = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_LSPIC_SEQ);
			pstmt.setString(1, les_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getString("LPIC_SEQ"));
			}
			
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return list;
	}
	
	@Override
	public void update(LessonVO lessonVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, lessonVO.getLes_name());
			pstmt.setString(2, lessonVO.getLes_info());
			pstmt.setString(3, lessonVO.getCoach());
			pstmt.setInt(4, lessonVO.getCost());
			pstmt.setInt(5, lessonVO.getDays());
			pstmt.setDate(6, lessonVO.getSignup_startdate());
			pstmt.setDate(7, lessonVO.getSignup_enddate());
			pstmt.setString(8, lessonVO.getLes_state());
			pstmt.setInt(9, lessonVO.getLes_max());
			pstmt.setInt(10, lessonVO.getLes_limit());
			pstmt.setDate(11, lessonVO.getLes_startdate());
			pstmt.setDate(12, lessonVO.getLes_enddate());
			pstmt.setString(13, lessonVO.getLess_state());
			pstmt.setString(14, lessonVO.getLes_no());
			pstmt.setString(15, lessonVO.getDs_name());
			pstmt.setString(16, lessonVO.getDs_no());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
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
	}

	@Override
	public void delete(String les_no) {
		int updateCount_lesson = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_LES);
			pstmt.setString(1, les_no);
			updateCount_lesson = pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除課程編號:" + les_no + ":" + updateCount_lesson + "筆");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
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

	}

	@Override
	public LessonVO findByPrimaryKey(String les_no) {

		LessonVO lessonVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

		return lessonVO;
	}

	@Override
	public Set<LessonVO> findByCoach(String coach) {
		Set<LessonVO> set = new LinkedHashSet<LessonVO>();
		LessonVO lessonVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
				set.add(lessonVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

		return set;
	}

	@Override
	public List<LessonVO> findByShop(String ds_no) {
		List<LessonVO> list = new ArrayList<LessonVO>();
		LessonVO lessonVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
				list.add(lessonVO);

			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return list;
	}

	@Override
	public Set<LessonVO> findByLessonName(String les_name) {
		Set<LessonVO> set = new LinkedHashSet<LessonVO>();
		LessonVO lessonVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
				set.add(lessonVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return set;
	}

	@Override
	public List<LessonVO> getAll() {
		List<LessonVO> list = new ArrayList<LessonVO>();
		LessonVO lessonVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return list;
	}
	
	@Override
	public List<LessonVO> getAll(Map<String, String[]> map) {
		List<LessonVO> list = new ArrayList<LessonVO>();
		LessonVO lessonVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			String finalSQL = "select * from lesson "
			          + jdbcUtil_CompositeQuery_Lesson.get_WhereCondition(map)
			          + "order by les_no";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("●●finalSQL = " + finalSQL);
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
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return list;
	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();
	
		return baos.toByteArray();
	}
	
	public static void main(String[] args) throws IOException {
		
		LessonJDBCDAO dao = new LessonJDBCDAO();
		// insert
//		LessonVO lessonVO1 = new LessonVO();
//		lessonVO1.setDs_no("DS0002");
//		lessonVO1.setDs_name("testname");
//		lessonVO1.setLes_name("testlesson");
//		lessonVO1.setLes_info("testinfo");
//		lessonVO1.setCoach("Tony");
//		lessonVO1.setCost(500);
//		lessonVO1.setDays(7);
//		lessonVO1.setSignup_startdate(new Date(2020-01-01));
//		lessonVO1.setSignup_enddate(new Date(2020-01-05));
//		//new Date(System.currentTimeMillis()); 當前時間
//		lessonVO1.setLes_state("報名中");
//		lessonVO1.setLes_max(4);
//		lessonVO1.setLes_limit(2);
//		lessonVO1.setLes_startdate(new Date(2020-01-07));
//		lessonVO1.setLes_enddate(new Date(2020-01-14));
//		lessonVO1.setLess_state("OPEN");
//		dao.insert(lessonVO1);
		
		//insetWithLespic
//		LessonVO lessonVO1 = new LessonVO();
//		lessonVO1.setDs_no("DS0002");
//		lessonVO1.setDs_name("testname");
//		lessonVO1.setLes_name("testlesson");
//		lessonVO1.setLes_info("testinfo");
//		lessonVO1.setCoach("Tony");
//		lessonVO1.setCost(500);
//		lessonVO1.setDays(7);
//		lessonVO1.setSignup_startdate(new Date(2020-01-01));
//		lessonVO1.setSignup_enddate(new Date(2020-01-05));
//		//new Date(System.currentTimeMillis()); 當前時間
//		lessonVO1.setLes_state("報名中");
//		lessonVO1.setLes_max(4);
//		lessonVO1.setLes_limit(2);
//		lessonVO1.setLes_startdate(new Date(2020-01-07));
//		lessonVO1.setLes_enddate(new Date(2020-01-14));
//		lessonVO1.setLess_state("OPEN");
//		
//		List<LespicVO> testList = new ArrayList<LespicVO>(); //準備置入圖片
//		LespicVO pic1 = new LespicVO();
//		byte[] lpic = getPictureByteArray("WebContent/image/p15.png");;
//		pic1.setLpic(lpic);
//		
//		
//		LespicVO pic2 = new LespicVO();
//		byte[] lpic2 = getPictureByteArray("WebContent/image/p15.png");;
//		pic2.setLpic(lpic2);
//	
//		
//		testList.add(pic1);
//		testList.add(pic2);
//		
//		dao.insertWithLespic(lessonVO1, testList);
//		
		// update
//		LessonVO lessonVO2 = new LessonVO();
//		
//		lessonVO2.setLes_name("update");
//		lessonVO2.setLes_info("testinfo");
//		lessonVO2.setCoach("Tony2");
//		lessonVO2.setCost(50);
//		lessonVO2.setDays(7);
//		lessonVO2.setSignup_startdate(java.sql.Date.valueOf("2020-01-01"));
//		lessonVO2.setSignup_enddate(java.sql.Date.valueOf("2020-01-10"));
//		lessonVO2.setLes_state("報名中");
//		lessonVO2.setLes_max(4);
//		lessonVO2.setLes_limit(2);
//		lessonVO2.setLes_startdate(java.sql.Date.valueOf("2020-02-10"));
//		lessonVO2.setLes_enddate(java.sql.Date.valueOf("2020-02-15"));
//		lessonVO2.setLess_state("OPEN");
//		lessonVO2.setLes_no("LE0012");
//		lessonVO2.setDs_name("testname");
//		dao.update(lessonVO2);

		// delete
//		dao.delete("LE0012");

		// 查詢課程(PK)
//		LessonVO lessonVO3 = dao.findByPrimaryKey("LE0001");
//		System.out.print(lessonVO3.getLes_name() + ",");
//		System.out.print(lessonVO3.getDs_no() + ",");
//		System.out.print(lessonVO3.getDs_name() + ",");
//		System.out.print(lessonVO3.getLes_info() + ",");
//		System.out.print(lessonVO3.getCoach() + ",");
//		System.out.print(lessonVO3.getCost() + ",");
//		System.out.print(lessonVO3.getDays() + ",");
//		System.out.print(lessonVO3.getSignup_startdate() + ",");
//		System.out.print(lessonVO3.getSignup_enddate() + ",");
//		System.out.print(lessonVO3.getLes_name() + ",");
//		System.out.print(lessonVO3.getLes_state() + ",");
//		System.out.print(lessonVO3.getLes_max() + ",");
//		System.out.print(lessonVO3.getLes_limit() + ",");
//		System.out.print(lessonVO3.getLes_startdate() + ",");
//		System.out.print(lessonVO3.getLes_enddate() + ",");
//		System.out.print(lessonVO3.getLess_state());
//		System.out.println();

		// 查詢課程(教練)
//		Set<LessonVO> set = dao.findByCoach("DAVID");
//		for(LessonVO ales : set) {
//		System.out.print(ales.getLes_no() + ",");
//		System.out.print(ales.getLes_name() + ",");
//		System.out.print(ales.getDs_no() + ",");
//		System.out.print(ales.getDs_name() + ",");
//		System.out.print(ales.getLes_info() + ",");
//		System.out.print(ales.getCoach() + ",");
//		System.out.print(ales.getCost() + ",");
//		System.out.print(ales.getDays() + ",");
//		System.out.print(ales.getSignup_startdate() + ",");
//		System.out.print(ales.getSignup_enddate() + ",");
//		System.out.print(ales.getLes_state() + ",");
//		System.out.print(ales.getLes_max() + ",");
//		System.out.print(ales.getLes_limit() + ",");
//		System.out.print(ales.getLes_startdate() + ",");
//		System.out.print(ales.getLes_enddate() + ",");
//		System.out.print(ales.getLess_state());
//		System.out.println();
//		}

		// 查詢課程(潛店)
		List<LessonVO> list = dao.findByShop("DS0001");
		for (LessonVO ales : list) {
			System.out.print(ales.getLes_no() + ",");
			System.out.print(ales.getLes_name() + ",");
			System.out.print(ales.getDs_no() + ",");
			System.out.print(ales.getDs_name() + ",");
			System.out.print(ales.getLes_info() + ",");
			System.out.print(ales.getCoach() + ",");
			System.out.print(ales.getCost() + ",");
			System.out.print(ales.getDays() + ",");
			System.out.print(ales.getSignup_startdate() + ",");
			System.out.print(ales.getSignup_enddate() + ",");
			System.out.print(ales.getLes_state() + ",");
			System.out.print(ales.getLes_max() + ",");
			System.out.print(ales.getLes_limit() + ",");
			System.out.print(ales.getLes_startdate() + ",");
			System.out.print(ales.getLes_enddate() + ",");
			System.out.print(ales.getLess_state());
			System.out.println();
		}
		// 查詢課程(課程名稱)
//		Set<LessonVO> set = dao.findByLessonName("OW");
//		for (LessonVO ales : set) {
//			System.out.print(ales.getLes_no() + ",");
//			System.out.print(ales.getLes_name() + ",");
//			System.out.print(ales.getDs_no() + ",");
//			System.out.print(ales.getDs_name() + ",");
//			System.out.print(ales.getLes_info() + ",");
//			System.out.print(ales.getCoach() + ",");
//			System.out.print(ales.getCost() + ",");
//			System.out.print(ales.getDays() + ",");
//			System.out.print(ales.getSignup_startdate() + ",");
//			System.out.print(ales.getSignup_enddate() + ",");
//			System.out.print(ales.getLes_state() + ",");
//			System.out.print(ales.getLes_max() + ",");
//			System.out.print(ales.getLes_limit() + ",");
//			System.out.print(ales.getLes_startdate() + ",");
//			System.out.print(ales.getLes_enddate() + ",");
//			System.out.print(ales.getLess_state());
//			System.out.println();
//		}
		//查詢所有課程
//		List<LessonVO> list = dao.getAll();
//		for(LessonVO all : list) {
//			System.out.print(all.getLes_no() + ",");
//			System.out.print(all.getLes_name() + ",");
//			System.out.print(all.getDs_no() + ",");
//			System.out.print(all.getDs_name() + ",");
//			System.out.print(all.getLes_info() + ",");
//			System.out.print(all.getCoach() + ",");
//			System.out.print(all.getCost() + ",");
//			System.out.print(all.getDays() + ",");
//			System.out.print(all.getSignup_startdate() + ",");
//			System.out.print(all.getSignup_enddate() + ",");
//			System.out.print(all.getLes_state() + ",");
//			System.out.print(all.getLes_max() + ",");
//			System.out.print(all.getLes_limit() + ",");
//			System.out.print(all.getLes_startdate() + ",");
//			System.out.print(all.getLes_enddate() + ",");
//			System.out.print(all.getLess_state());
//			System.out.println();
//		}
	}

	

	


}
