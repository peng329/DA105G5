package com.mra_record.model;

import java.util.*;
import java.sql.*;
import java.sql.Timestamp;

public class Mra_recordJDBCDAO implements Mra_recordDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String password = "DA105G5";

	private static final String INSERT = "INSERT INTO MRA_RECORD(rar_no, mem_no, mpo_no, rep_time, rep_content, rep_state)VALUES('RA'||LPAD(to_char(RARNO_SEQ.NEXTVAL),5,'0'),?,?,?,?,?)";
	private static final String GET_ONE_STMT = "SELECT  rar_no, mem_no, mpo_no, to_char(rep_time,'yyyy-mm-dd hh24:mi:ss') rep_time, rep_content, rep_state FROM MRA_RECORD WHERE rar_no=?";
	private static final String GET_ALL_STMT = "SELECT  rar_no, mem_no, mpo_no, to_char(rep_time,'yyyy-mm-dd hh24:mi:ss') rep_time, rep_content, rep_state FROM MRA_RECORD ORDER BY rar_no";
	private static final String UPDATE = "UPDATE MRA_RECORD SET mem_no=?, mpo_no=?, rep_time=?, rep_content=?, rep_state=? WHERE rar_no=? ";
	private static final String DELETE = "DELETE FROM MRA_RECORD WHERE rar_no=?";

	@Override
	public void insert(Mra_recordVO mra_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(INSERT);

			pstmt.setString(1, mra_recordVO.getMem_no());
			pstmt.setString(2, mra_recordVO.getMpo_no());
			pstmt.setTimestamp(3, mra_recordVO.getRep_time());
			pstmt.setString(4, mra_recordVO.getRep_content());
			pstmt.setString(5, mra_recordVO.getRep_state());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public void update(Mra_recordVO mra_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mra_recordVO.getMem_no());
			pstmt.setString(2, mra_recordVO.getMpo_no());
			pstmt.setTimestamp(3, mra_recordVO.getRep_time());
			pstmt.setString(4, mra_recordVO.getRep_content());
			pstmt.setString(5, mra_recordVO.getRep_state());
            pstmt.setString(6, mra_recordVO.getRar_no());
			
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public void delete(String rar_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, rar_no);

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public Mra_recordVO findByPrimaryKey(String rar_no) {
		Mra_recordVO mra_recordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, rar_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				mra_recordVO= new Mra_recordVO();
				mra_recordVO.setRar_no(rs.getString("rar_no"));
				mra_recordVO.setMem_no(rs.getString("mem_no"));
				mra_recordVO.setMpo_no(rs.getString("mpo_no"));
				mra_recordVO.setRep_time(rs.getTimestamp("rep_time"));
				mra_recordVO.setRep_content(rs.getString("rep_content"));
				mra_recordVO.setRep_state(rs.getString("rep_state"));
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

		return mra_recordVO;
	}

	@Override
	public List<Mra_recordVO> getAll() {
		List<Mra_recordVO> list = new ArrayList<Mra_recordVO>();
		Mra_recordVO mra_recordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mra_recordVO = new Mra_recordVO();
				mra_recordVO.setRar_no(rs.getString("rar_no"));
				mra_recordVO.setMem_no(rs.getString("mem_no"));
				mra_recordVO.setMpo_no(rs.getString("mpo_no"));
				mra_recordVO.setRep_time(rs.getTimestamp("rep_time"));
				mra_recordVO.setRep_content(rs.getString("rep_content"));
				mra_recordVO.setRep_state(rs.getString("rep_state"));

				list.add(mra_recordVO);
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
       Mra_recordJDBCDAO dao = new Mra_recordJDBCDAO();
       
       //新增
//       Mra_recordVO mraVO1 = new Mra_recordVO();
//       mraVO1.setMem_no("M000001");
//       mraVO1.setMpo_no("MP200129-008");
//       mraVO1.setRep_time(Timestamp.valueOf("2020-01-30 23:41:04"));
//       mraVO1.setRep_content("內容空洞毫無意義！");
//       mraVO1.setRep_state("待審核");
//	   dao.insert(mraVO1);
//	   System.out.println("新增OK!");
	   
	   //修改
//	   Mra_recordVO mraVO2 = new Mra_recordVO();
//	   mraVO2.setRar_no("RA00003");
//	   mraVO2.setMem_no("M000001");
//	   mraVO2.setMpo_no("MP200129-008");
//       mraVO2.setRep_time(Timestamp.valueOf("2020-01-31 09:23:47"));
//       mraVO2.setRep_content("內容空洞毫無意義！");
//	   mraVO2.setRep_state("通過");
//	   dao.update(mraVO2);
//	   System.out.println("修改OK!");
	   
//	   //刪除
	   dao.delete("RA00003");
	   System.out.println("刪除OK!");
	   //查詢一件
	   Mra_recordVO mraVO3 = dao.findByPrimaryKey("RA00001");
	   
	   System.out.print(mraVO3.getRar_no()+",");
	   System.out.print(mraVO3.getMem_no()+",");
	   System.out.print(mraVO3.getMpo_no()+",");
	   System.out.print(mraVO3.getRep_time()+",");
	   System.out.print(mraVO3.getRep_content()+",");
	   System.out.println(mraVO3.getRep_state());
	   System.out.println("---------------------");
	   
	   //查詢全部
	   List<Mra_recordVO> list = dao.getAll();
	   for(Mra_recordVO mra_recordVOs:list) {
		   System.out.print(mra_recordVOs.getRar_no()+",");
		   System.out.print(mra_recordVOs.getMem_no()+",");
		   System.out.print(mra_recordVOs.getMpo_no()+",");
		   System.out.print(mra_recordVOs.getRep_time()+",");
		   System.out.print(mra_recordVOs.getRep_content()+",");
		   System.out.println(mra_recordVOs.getRep_state());
		   System.out.println();
	   }
	   
	   
	}

}
