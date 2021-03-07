package com.dsrm_record.model;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import com.lessonorder.model.LessonOrderVO;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Dsrm_record;

public class Dsrm_recordJDBCDAO implements Dsrm_recordDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String passwd = "DA105G5";
	
	private static final String INSERT_STMT = "INSERT INTO DSRM_RECORD VALUES ('DSR'||LPAD(to_char(RDSRNO_SEQ.NEXTVAL), 3, '0'),?,?,?,?)";
	
	private static final String DELETE_DSRM_RECORD = "DELETE FROM DSRM_RECORD WHERE RDSR_NO = ?";
	
	private static final String UPDATE = "UPDATE DSRM_RECORD SET DS_NO = ?, MEM_NO = ?, REP_CONTENT = ? ,REP_STATE = ? WHERE RDSR_NO = ?";
	
	private static final String GET_ALL_RECORD = "SELECT * FROM DSRM_RECORD";
	private static final String GET_ONE_RECORD = "SELECT * FROM DSRM_RECORD WHERE RDSR_NO = ?";
	private static final String GET_RECORD_BY_DSNO = "SELECT * FROM DSRM_RECORD WHERE DS_NO = ?";
	private static final String GET_RECORD_BY_MEMNO = "SELECT * FROM DSRM_RECORD WHERE MEM_NO = ?";

	@Override
	public void insert(Dsrm_recordVO dsrm_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, dsrm_recordVO.getDs_no());
			pstmt.setString(2, dsrm_recordVO.getMem_no());
			pstmt.setString(3, dsrm_recordVO.getRep_content());
			pstmt.setString(4, dsrm_recordVO.getRep_state());

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
	public void update(Dsrm_recordVO dsrm_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, dsrm_recordVO.getDs_no());
			pstmt.setString(2, dsrm_recordVO.getMem_no());
			pstmt.setString(3, dsrm_recordVO.getRep_content());
			pstmt.setString(4, dsrm_recordVO.getRep_state());
			pstmt.setString(5, dsrm_recordVO.getRdsr_no());
			
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
	public void delete(String rdsr_no) {
		int updateCount_record = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_DSRM_RECORD);
			pstmt.setString(1, rdsr_no);
			updateCount_record = pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除紀錄編號:" + rdsr_no + ":" + updateCount_record + "筆");
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
	public Dsrm_recordVO findByPrimaryKey(String rdsr_no) {
		Dsrm_recordVO dsrm_recordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_RECORD);
			
			pstmt.setString(1, rdsr_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dsrm_recordVO = new Dsrm_recordVO();
				dsrm_recordVO.setRdsr_no(rs.getString("rdsr_no"));
				dsrm_recordVO.setDs_no(rs.getString("ds_no"));
				dsrm_recordVO.setMem_no(rs.getString("mem_no"));
				dsrm_recordVO.setRep_content(rs.getString("rep_content"));
				dsrm_recordVO.setRep_state(rs.getString("rep_state"));
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
		return dsrm_recordVO;
	}

	@Override
	public List<Dsrm_recordVO> findByDiveshopNo(String ds_no) {
		List<Dsrm_recordVO> list = new ArrayList();
		Dsrm_recordVO dsrm_recordVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_RECORD_BY_DSNO);
			pstmt.setString(1, ds_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dsrm_recordVO = new Dsrm_recordVO();
				dsrm_recordVO.setRdsr_no(rs.getString("rdsr_no"));
				dsrm_recordVO.setDs_no(rs.getString("ds_no"));
				dsrm_recordVO.setMem_no(rs.getString("mem_no"));
				dsrm_recordVO.setRep_content(rs.getString("rep_content"));
				dsrm_recordVO.setRep_state(rs.getString("rep_state"));
				list.add(dsrm_recordVO);
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
	public List<Dsrm_recordVO> findByByMemNo(String mem_no) {
		List<Dsrm_recordVO> list = new ArrayList();
		Dsrm_recordVO dsrm_recordVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_RECORD_BY_MEMNO);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dsrm_recordVO = new Dsrm_recordVO();
				dsrm_recordVO.setRdsr_no(rs.getString("rdsr_no"));
				dsrm_recordVO.setDs_no(rs.getString("ds_no"));
				dsrm_recordVO.setMem_no(rs.getString("mem_no"));
				dsrm_recordVO.setRep_content(rs.getString("rep_content"));
				dsrm_recordVO.setRep_state(rs.getString("rep_state"));
				list.add(dsrm_recordVO);
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
	public List<Dsrm_recordVO> getAll(Map<String, String[]> map) {
		List<Dsrm_recordVO> list = new ArrayList<Dsrm_recordVO>();
		Dsrm_recordVO dsrm_recordVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			String finalSQL = "select * from dsrm_record "
			          + jdbcUtil_CompositeQuery_Dsrm_record.get_WhereCondition(map)
			          + "order by rdsr_no";
			pstmt = con.prepareStatement(finalSQL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dsrm_recordVO = new Dsrm_recordVO();
				dsrm_recordVO.setRdsr_no(rs.getString("rdsr_no"));
				dsrm_recordVO.setDs_no(rs.getString("ds_no"));
				dsrm_recordVO.setMem_no(rs.getString("mem_no"));
				dsrm_recordVO.setRep_content(rs.getString("rep_content"));
				dsrm_recordVO.setRep_state(rs.getString("rep_state"));
				list.add(dsrm_recordVO);
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
	public List<Dsrm_recordVO> getAll() {
		List<Dsrm_recordVO> list = new ArrayList<Dsrm_recordVO>();
		Dsrm_recordVO dsrm_recordVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_RECORD);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dsrm_recordVO = new Dsrm_recordVO();
				dsrm_recordVO.setRdsr_no(rs.getString("rdsr_no"));
				dsrm_recordVO.setDs_no(rs.getString("ds_no"));
				dsrm_recordVO.setMem_no(rs.getString("mem_no"));
				dsrm_recordVO.setRep_content(rs.getString("rep_content"));
				dsrm_recordVO.setRep_state(rs.getString("rep_state"));
				list.add(dsrm_recordVO);
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
		
		Dsrm_recordJDBCDAO dao = new Dsrm_recordJDBCDAO();
		//insert
//		Dsrm_recordVO dsrm_recordVO1 = new Dsrm_recordVO();
//		dsrm_recordVO1.setDs_no("DS0003");
//		dsrm_recordVO1.setMem_no("M000002");
//		dsrm_recordVO1.setRep_content("檢舉內容6");
//		dsrm_recordVO1.setRep_state("審核通過");
//		dao.insert(dsrm_recordVO1);
		
		//update
		Dsrm_recordVO dsrm_recordVO2 = new Dsrm_recordVO();
		dsrm_recordVO2.setDs_no("DS0005");
		dsrm_recordVO2.setMem_no("M000002");
		dsrm_recordVO2.setRep_content("updatecontent");
		dsrm_recordVO2.setRep_state("未審核");
		dsrm_recordVO2.setRdsr_no("DSR007");
		dao.update(dsrm_recordVO2);
		
		//delete
//		dao.delete("DSR008");
		
		//查詢紀錄(PK)
//		Dsrm_recordVO dsrm_recordVO3 = dao.findByPrimaryKey("DSR007");
//			System.out.print(dsrm_recordVO3.getRdsr_no() + ",");
//			System.out.print(dsrm_recordVO3.getDs_no() + ",");	
//			System.out.print(dsrm_recordVO3.getMem_no() + ",");	
//			System.out.print(dsrm_recordVO3.getRep_content() + ",");	
//			System.out.print(dsrm_recordVO3.getRep_state() + ",");
//			System.out.println();
		
		//查詢紀錄(潛店)
//		Set<Dsrm_recordVO> set = dao.findByDiveshopNo("DS0001");
//		for(Dsrm_recordVO arecord : set) {
//			System.out.print(arecord.getRdsr_no() + ",");
//			System.out.print(arecord.getDs_no() + ",");	
//			System.out.print(arecord.getMem_no() + ",");	
//			System.out.print(arecord.getRep_content() + ",");	
//			System.out.print(arecord.getRep_state() + ",");
//			System.out.println();
//		}
		//查詢紀錄(會員)
//		Set<Dsrm_recordVO> set = dao.findByByMemNo("M000001");
//		for(Dsrm_recordVO arecord : set) {
//			System.out.print(arecord.getRdsr_no() + ",");
//			System.out.print(arecord.getDs_no() + ",");	
//			System.out.print(arecord.getMem_no() + ",");	
//			System.out.print(arecord.getRep_content() + ",");	
//			System.out.print(arecord.getRep_state() + ",");
//			System.out.println();
//		}
		
		//全部紀錄
		List<Dsrm_recordVO> list = dao.getAll();
		for(Dsrm_recordVO arecord :list) {
			System.out.print(arecord.getRdsr_no() + ",");
			System.out.print(arecord.getDs_no() + ",");	
			System.out.print(arecord.getMem_no() + ",");	
			System.out.print(arecord.getRep_content() + ",");	
			System.out.print(arecord.getRep_state() + ",");
			System.out.println();
		}
	}

	
}
