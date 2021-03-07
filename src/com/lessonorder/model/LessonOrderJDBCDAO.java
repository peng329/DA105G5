package com.lessonorder.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.lesson.model.LessonVO;

public class LessonOrderJDBCDAO implements LessonOrderDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String passwd = "DA105G5";

	private static final String INSERT_STMT = "INSERT INTO LESSON_ORDER VALUES('L'||to_char(sysdate,'yyyymmdd')||'-'||LPAD(to_char(LESSONOD_SEQ.NEXTVAL), 3, '0'),?,?,?,?,?,?,?,?)";

	private static final String DELETE_LES_ORDER = "DELETE FROM LESSON_ORDER WHERE LES_O_NO = ? ";

	private static final String UPDATE = "UPDATE LESSON_ORDER SET MEM_NO = ?, LES_NO = ?,DS_NO = ?, MEM_NAME = ?, LES_NAME = ?,"
			+ " COST = ?, COACH = ?, LO_STATE = ? WHERE LES_O_NO = ?";

	private static final String GET_ALL_STMT = "SELECT * FROM LESSON_ORDER";
	private static final String GET_ONE_ORDER = "SELECT * FROM LESSON_ORDER WHERE LES_O_NO = ?";
	private static final String GET_ORDER_BY_MEMNO = "SELECT * FROM LESSON_ORDER WHERE MEM_NO = ?";
	private static final String GET_ORDER_BY_LESNO = "SELECT * FROM LESSON_ORDER WHERE LES_NO = ?";
	private static final String GET_ORDER_BY_DSNO = "SELECT * FROM LESSON_ORDER WHERE DS_NO = ?";
	
	private static final String GET_MEM_NO_BY_LESNO = "SELECT MEM_NO FROM LESSON_ORDER WHERE LES_NO = ?";

	
	@Override
	public void insert(LessonOrderVO lessonOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, lessonOrderVO.getMem_no());
			pstmt.setString(2, lessonOrderVO.getLes_no());
			pstmt.setString(3, lessonOrderVO.getDs_no());
			pstmt.setString(4, lessonOrderVO.getMem_name());
			pstmt.setString(5, lessonOrderVO.getLes_name());
			pstmt.setInt(6, lessonOrderVO.getCost());
			pstmt.setString(7, lessonOrderVO.getCoach());
			pstmt.setString(8, lessonOrderVO.getLo_state());

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
	public void update(LessonOrderVO lessonOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, lessonOrderVO.getMem_no());
			pstmt.setString(2, lessonOrderVO.getLes_no());
			pstmt.setString(3, lessonOrderVO.getDs_no());
			pstmt.setString(4, lessonOrderVO.getMem_name());
			pstmt.setString(5, lessonOrderVO.getLes_name());
			pstmt.setInt(6, lessonOrderVO.getCost());
			pstmt.setString(7, lessonOrderVO.getCoach());
			pstmt.setString(8, lessonOrderVO.getLo_state());
			pstmt.setString(9, lessonOrderVO.getLes_o_no());
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
	public void delete(String les_o_no) {
		int updateCount_order = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_LES_ORDER);
			pstmt.setString(1, les_o_no);
			updateCount_order = pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除訂單編號:" + les_o_no + ":" + updateCount_order + "筆");
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
	public LessonOrderVO findByPrimaryKey(String les_o_no) {
		LessonOrderVO lessonOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_ORDER);
			
			pstmt.setString(1, les_o_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				lessonOrderVO = new LessonOrderVO();
				lessonOrderVO.setLes_o_no(rs.getString("les_o_no"));
				lessonOrderVO.setMem_no(rs.getString("mem_no"));
				lessonOrderVO.setLes_no(rs.getString("les_no"));
				lessonOrderVO.setDs_no(rs.getString("ds_no"));
				lessonOrderVO.setMem_name(rs.getString("mem_name"));
				lessonOrderVO.setLes_name(rs.getString("les_name"));
				lessonOrderVO.setCost(rs.getInt("cost"));
				lessonOrderVO.setCoach(rs.getString("coach"));
				lessonOrderVO.setLo_state(rs.getString("lo_state"));
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
		
		return lessonOrderVO;
	}

	@Override
	public List<LessonOrderVO> findByDs_no(String ds_no) {
		List<LessonOrderVO> list = new ArrayList<LessonOrderVO>();
		LessonOrderVO lessonOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ORDER_BY_DSNO);
			pstmt.setString(1, ds_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				lessonOrderVO = new LessonOrderVO();
				lessonOrderVO.setLes_o_no(rs.getString("les_o_no"));
				lessonOrderVO.setMem_no(rs.getString("mem_no"));
				lessonOrderVO.setLes_no(rs.getString("les_no"));
				lessonOrderVO.setDs_no(rs.getString("ds_no"));
				lessonOrderVO.setMem_name(rs.getString("mem_name"));
				lessonOrderVO.setLes_name(rs.getString("les_name"));
				lessonOrderVO.setCost(rs.getInt("cost"));
				lessonOrderVO.setCoach(rs.getString("coach"));
				lessonOrderVO.setLo_state(rs.getString("lo_state"));
				list.add(lessonOrderVO);
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
	public List<LessonOrderVO> findByMem_no(String mem_no) {
		List<LessonOrderVO> list = new ArrayList<LessonOrderVO>();
		LessonOrderVO lessonOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ORDER_BY_MEMNO);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				lessonOrderVO = new LessonOrderVO();
				lessonOrderVO.setLes_o_no(rs.getString("les_o_no"));
				lessonOrderVO.setMem_no(rs.getString("mem_no"));
				lessonOrderVO.setLes_no(rs.getString("les_no"));
				lessonOrderVO.setDs_no(rs.getString("ds_no"));
				lessonOrderVO.setMem_name(rs.getString("mem_name"));
				lessonOrderVO.setLes_name(rs.getString("les_name"));
				lessonOrderVO.setCost(rs.getInt("cost"));
				lessonOrderVO.setCoach(rs.getString("coach"));
				lessonOrderVO.setLo_state(rs.getString("lo_state"));
				list.add(lessonOrderVO);
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
	public List<LessonOrderVO> findByLes_no(String les_no) {
		List<LessonOrderVO> list = new ArrayList<LessonOrderVO>();
		LessonOrderVO lessonOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ORDER_BY_LESNO);
			pstmt.setString(1, les_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				lessonOrderVO = new LessonOrderVO();
				lessonOrderVO.setLes_o_no(rs.getString("les_o_no"));
				lessonOrderVO.setMem_no(rs.getString("mem_no"));
				lessonOrderVO.setLes_no(rs.getString("les_no"));
				lessonOrderVO.setDs_no(rs.getString("ds_no"));
				lessonOrderVO.setMem_name(rs.getString("mem_name"));
				lessonOrderVO.setLes_name(rs.getString("les_name"));
				lessonOrderVO.setCost(rs.getInt("cost"));
				lessonOrderVO.setCoach(rs.getString("coach"));
				lessonOrderVO.setLo_state(rs.getString("lo_state"));
				list.add(lessonOrderVO);
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
	public List<LessonOrderVO> getAll() {
		List<LessonOrderVO> list = new ArrayList<LessonOrderVO>();
		LessonOrderVO lessonOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				lessonOrderVO = new LessonOrderVO();
				lessonOrderVO.setLes_o_no(rs.getString("les_o_no"));
				lessonOrderVO.setMem_no(rs.getString("mem_no"));
				lessonOrderVO.setLes_no(rs.getString("les_no"));
				lessonOrderVO.setDs_no(rs.getString("ds_no"));
				lessonOrderVO.setMem_name(rs.getString("mem_name"));
				lessonOrderVO.setLes_name(rs.getString("les_name"));
				lessonOrderVO.setCost(rs.getInt("cost"));
				lessonOrderVO.setCoach(rs.getString("coach"));
				lessonOrderVO.setLo_state(rs.getString("lo_state"));
				list.add(lessonOrderVO);
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
	public List<String> findmem_noByLes_no(String les_no) {
		List<String> list = new ArrayList<String>();
		LessonOrderVO lessonOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_MEM_NO_BY_LESNO);
			pstmt.setString(1, les_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				lessonOrderVO = new LessonOrderVO();
				String mem_no =(rs.getString("mem_no"));
				
				list.add(mem_no);
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
	


	
	
	public static void main(String[] args) {
		
		LessonOrderJDBCDAO dao = new LessonOrderJDBCDAO();
		//insert
//		LessonOrderVO lessonOrderVO1 = new LessonOrderVO();
//		lessonOrderVO1.setMem_no("M000002");
//		lessonOrderVO1.setLes_no("LE0005");
//		lessonOrderVO1.setDs_no("DS0001");
//		lessonOrderVO1.setMem_name("seafood");
//		lessonOrderVO1.setLes_name("潛水長");
//		lessonOrderVO1.setCost(2000);
//		lessonOrderVO1.setCoach("DAVID");
//		lessonOrderVO1.setLo_state("未付款");
//		dao.insert(lessonOrderVO1);
		
		//update
//		LessonOrderVO lessonOrderVO2 = new LessonOrderVO();
//		lessonOrderVO2.setMem_no("M000002");
//		lessonOrderVO2.setLes_no("LE0005");
//		lessonOrderVO2.setDs_no("DS0002");
//		lessonOrderVO2.setMem_name("seafood");
//		lessonOrderVO2.setLes_name("潛水長");
//		lessonOrderVO2.setCost(200000);
//		lessonOrderVO2.setCoach("DAVID2");
//		lessonOrderVO2.setLo_state("未付款");
//		lessonOrderVO2.setLes_o_no("L20200303-004");
//		dao.update(lessonOrderVO2);
		
		//delete
//		dao.delete("L20200303-004");
		
		//查詢訂單(PK)
//		LessonOrderVO lessonOrderVO3 = dao.findByPrimaryKey("L20200303-003");
//		System.out.print(lessonOrderVO3.getLes_o_no() + ",");
//		System.out.print(lessonOrderVO3.getMem_no() + ",");
//		System.out.print(lessonOrderVO3.getLes_no() + ",");
//		System.out.print(lessonOrderVO3.getDs_no() + ",");
//		System.out.print(lessonOrderVO3.getMem_name() + ",");
//		System.out.print(lessonOrderVO3.getLes_name() + ",");
//		System.out.print(lessonOrderVO3.getCost() + ",");
//		System.out.print(lessonOrderVO3.getCoach() + ",");
//		System.out.print(lessonOrderVO3.getLo_state());
//		System.out.println();
		
		//查詢訂單(會員編號)
//		List<LessonOrderVO> list = dao.findByMem_no("M000001");
//		for(LessonOrderVO aorder : list) {
//			System.out.print(aorder.getLes_o_no() + ",");
//			System.out.print(aorder.getMem_no() + ",");
//			System.out.print(aorder.getLes_no() + ",");
//			System.out.print(aorder.getDs_no() + ",");
//			System.out.print(aorder.getMem_name() + ",");
//			System.out.print(aorder.getLes_name() + ",");
//			System.out.print(aorder.getCost() + ",");
//			System.out.print(aorder.getCoach() + ",");
//			System.out.print(aorder.getLo_state());
//			System.out.println();
//		}
		
		//查詢訂單(課程編號)
//		List<LessonOrderVO> list = dao.findByLes_no("LE0003");
//		for(LessonOrderVO aorder : list) {
//			System.out.print(aorder.getLes_o_no() + ",");
//			System.out.print(aorder.getMem_no() + ",");
//			System.out.print(aorder.getLes_no() + ",");
//			System.out.print(aorder.getDs_no() + ",");
//			System.out.print(aorder.getMem_name() + ",");
//			System.out.print(aorder.getLes_name() + ",");
//			System.out.print(aorder.getCost() + ",");
//			System.out.print(aorder.getCoach() + ",");
//			System.out.print(aorder.getLo_state());
//			System.out.println();
//		}
		
//		List<LessonOrderVO> list = dao.findByDs_no("DS0001");
//		for(LessonOrderVO aorder : list) {
//			System.out.print(aorder.getLes_o_no() + ",");
//			System.out.print(aorder.getMem_no() + ",");
//			System.out.print(aorder.getLes_no() + ",");
//			System.out.print(aorder.getDs_no() + ",");
//			System.out.print(aorder.getMem_name() + ",");
//			System.out.print(aorder.getLes_name() + ",");
//			System.out.print(aorder.getCost() + ",");
//			System.out.print(aorder.getCoach() + ",");
//			System.out.print(aorder.getLo_state());
//			System.out.println();
//		}
		
//		List<LessonOrderVO> list = dao.findmem_noByLes_no("LE0001");
//		for(LessonOrderVO aorder : list) {
//			System.out.print(aorder.getMem_no() + ",");
//			System.out.println();
//		}
		
		//查詢所有訂單
//		List<LessonOrderVO> list = dao.getAll();
//		for(LessonOrderVO aorder : list) {
//			System.out.print(aorder.getLes_o_no() + ",");
//			System.out.print(aorder.getMem_no() + ",");
//			System.out.print(aorder.getLes_no() + ",");
//			System.out.print(aorder.getDs_no() + ",");
//			System.out.print(aorder.getMem_name() + ",");
//			System.out.print(aorder.getLes_name() + ",");
//			System.out.print(aorder.getCost() + ",");
//			System.out.print(aorder.getCoach() + ",");
//			System.out.print(aorder.getLo_state());
//			System.out.println();
//		}
	}

	
}
