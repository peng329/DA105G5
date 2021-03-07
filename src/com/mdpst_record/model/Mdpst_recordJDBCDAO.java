package com.mdpst_record.model;

import java.util.*;

import com.mdst_record.model.Mdst_recordVO;

import java.sql.*;

public class Mdpst_recordJDBCDAO implements Mdpst_recordDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String password = "DA105G5";

	private static final String INSERT_STMT = "INSERT into MDPST_RECORD(MEM_NO, DP_NO, TRAC_TIME)VALUES (?,?,?)";
	private static final String GET_ALL_STMT = "SELECT mem_no,dp_no,to_char(trac_time,'yyyy-mm-dd hh24:mi:ss') trac_time FROM mdpst_record ORDER BY mem_no";
	private static final String GET_ONE_STMT = "SELECT mem_no,dp_no,to_char(trac_time,'yyyy-mm-dd hh24:mi:ss') trac_time FROM mdpst_record WHERE mem_no= ? and dp_no=? ";

	private static final String DELETE = "DELETE FROM mdpst_record WHERE mem_no =? and dp_no=?";
	private static final String UPDATE = "UPDATE mdpst_record set trac_time=? WHERE mem_no =? and dp_no=?";
	
	//用一位會員查找他的所有文章追蹤
	private static final String GET_ALL_BY_MEM_STMT = "SELECT mem_no,dp_no,to_char(trac_time,'yyyy-mm-dd hh24:mi:ss') trac_time FROM mdpst_record where mem_no=? ORDER BY mem_no";
	
	@Override
	public void insert(Mdpst_recordVO mdpst_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mdpst_recordVO.getMem_no());
			pstmt.setString(2, mdpst_recordVO.getDp_no());
			pstmt.setTimestamp(3, mdpst_recordVO.getTrac_time());

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
	public void update(Mdpst_recordVO mdpst_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setTimestamp(1, mdpst_recordVO.getTrac_time());
			pstmt.setString(2, mdpst_recordVO.getMem_no());
			pstmt.setString(3, mdpst_recordVO.getDp_no());

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
	public void delete(String mem_no, String dp_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mem_no);
			pstmt.setString(2, dp_no);

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
	public Mdpst_recordVO findByPrimaryKey(String mem_no, String dp_no) {
		Mdpst_recordVO mdpst_recordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, mem_no);
			pstmt.setString(2, dp_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mdpst_recordVO = new Mdpst_recordVO();
				mdpst_recordVO.setMem_no(rs.getString("mem_no"));
				mdpst_recordVO.setDp_no(rs.getString("dp_no"));
				mdpst_recordVO.setTrac_time(rs.getTimestamp("trac_time"));
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
		return mdpst_recordVO;
	}

	@Override
	public List<Mdpst_recordVO> getAll() {
		List<Mdpst_recordVO> list = new ArrayList<>();
		Mdpst_recordVO mdpst_recordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mdpst_recordVO = new Mdpst_recordVO();
				mdpst_recordVO.setMem_no(rs.getString("mem_no"));
				mdpst_recordVO.setDp_no(rs.getString("dp_no"));
				mdpst_recordVO.setTrac_time(rs.getTimestamp("trac_time"));
				list.add(mdpst_recordVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public List<Mdpst_recordVO> getMdpstrsByMem_no (String mem_no){
		List<Mdpst_recordVO> list = new ArrayList<>();
		Mdpst_recordVO mdpst_recordVO = null;
		Connection con = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ALL_BY_MEM_STMT);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mdpst_recordVO = new Mdpst_recordVO();
				mdpst_recordVO.setMem_no(rs.getString("mem_no"));
				mdpst_recordVO.setDp_no(rs.getString("dp_no"));
				mdpst_recordVO.setTrac_time(rs.getTimestamp("trac_time"));
				list.add(mdpst_recordVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
		Mdpst_recordJDBCDAO dao = new Mdpst_recordJDBCDAO();

//		// 新增
//		Mdpst_recordVO mdpstVO1 = new Mdpst_recordVO();
//		mdpstVO1.setMem_no("M000004");
//		mdpstVO1.setDp_no("DP000003");
//		mdpstVO1.setTrac_time(Timestamp.valueOf("2020-01-31 23:43:56"));
//		dao.insert(mdpstVO1);
//		System.out.println("新增OK!");

//		// 修改
//		Mdpst_recordVO mdpstVO2 = new Mdpst_recordVO();
//		mdpstVO2.setMem_no("M000007");
//		mdpstVO2.setDp_no("LTWNNS011-006");
//		mdpstVO2.setTrac_time(Timestamp.valueOf("2020-02-01 08:30:52"));
//		dao.update(mdpstVO2);
//		System.out.println("修改OK!");

		// 刪除
//		dao.delete("M000007", "LTWNNS011-006");
//		System.out.println("刪除OK!");

		// 查詢一件
//		Mdpst_recordVO mdpstVO3 = dao.findByPrimaryKey("M000002", "LTWNNS011-001");
//		System.out.print(mdpstVO3.getMem_no() + ",");
//		System.out.print(mdpstVO3.getDp_no() + ",");
//		System.out.println(mdpstVO3.getTrac_time());
//		System.out.println("------------------");

		// 查詢全部
//		List<Mdpst_recordVO> list = dao.getAll();
//		for (Mdpst_recordVO mdpst : list) {
//			System.out.print(mdpst.getMem_no() + ",");
//			System.out.print(mdpst.getDp_no() + ",");
//			System.out.println(mdpst.getTrac_time());
//			System.out.println();
//		}
		
		//查詢一個會員的所有潛點追蹤
		List<Mdpst_recordVO> list2 = dao.getMdpstrsByMem_no("M000001");
		for (Mdpst_recordVO mdpst : list2) {
			System.out.print(mdpst.getMem_no() + ",");
			System.out.print(mdpst.getDp_no() + ",");
			System.out.println(mdpst.getTrac_time());
			System.out.println();
		}
	}

}
