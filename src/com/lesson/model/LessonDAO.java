package com.lesson.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.lespic.model.LespicJDBCDAO;
import com.lespic.model.LespicVO;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Lesson;

public class LessonDAO implements LessonDAO_interface {
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

	private static final String GET_ALL_LESSON_FROM_SHOPNAME = "SELECT * FROM LESSON WHERE DS_NAME = ?";
	private static final String GET_ONE_LESSON = "SELECT * FROM LESSON WHERE LES_NO = ?";
	private static final String GET_LESSON_BY_COACH = "SELECT * FROM LESSON WHERE COACH = ? ORDER BY LES_NO";
	private static final String GET_LESSON_BY_DIVESHOP = "SELECT * FROM LESSON WHERE DS_NO = ?";
	private static final String GET_LESSON_BY_LESSONNAME = "SELECT * FROM LESSON WHERE LES_NAME = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM LESSON";
	private static final String GET_LSPIC_SEQ = "SELECT LPIC_SEQ FROM LESPIC WHERE LES_NO =?";

	private static final String UPDATE = "UPDATE LESSON SET LES_NAME = ?,LES_INFO = ?,COACH = ?,COST = ?,"
			+ "DAYS = ?,SIGNUP_STARTDATE = ?,SIGNUP_ENDDATE = ?,LES_STATE = ?,LES_MAX = ?,LES_LIMIT = ?, "
			+ "LES_STARTDATE =?, LES_ENDDATE =?, LESS_STATE = ? WHERE LES_NO = ? AND DS_NAME = ?AND DS_NO = ?";

	@Override
	public void insertWithLespic(LessonVO lessonVO, List<LespicVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;

			try {
				
				con = ds.getConnection();
				con.setAutoCommit(false);
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
		
			}catch (SQLException se) {
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

			con = ds.getConnection();
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
	public void update(LessonVO lessonVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
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
			System.out.println(132);
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
			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_LES);
			pstmt.setString(1, les_no);
			updateCount_lesson = pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除課程編號:" + les_no + ":" + updateCount_lesson + "筆");
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
				set.add(lessonVO);
			}

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
				list.add(lessonVO);

			}

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
				set.add(lessonVO);
			}

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
		return list;
	}


	@Override
	public List<String> getlespic_seqByLess_no(String les_no) {
		List<String> list = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LSPIC_SEQ);

			pstmt.setString(1, les_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getString("LPIC_SEQ"));
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
			con = ds.getConnection();
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
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
