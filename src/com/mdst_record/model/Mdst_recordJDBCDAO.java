package com.mdst_record.model;

import java.util.*;
import java.sql.*;
import java.sql.Timestamp;
public class Mdst_recordJDBCDAO implements Mdst_recordDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String password = "DA105G5";

	private static final String INSERT_STMT = "INSERT into MDST_RECORD(MEM_NO, DS_NO, TRAC_TIME)VALUES (?,?,?)";
	private static final String GET_ALL_STMT = "SELECT mem_no,ds_no,to_char(trac_time,'yyyy-mm-dd hh24:mi:ss') trac_time FROM mdst_record ORDER BY mem_no";
	private static final String GET_ONE_STMT = "SELECT mem_no,ds_no,to_char(trac_time,'yyyy-mm-dd hh24:mi:ss') trac_time FROM mdst_record WHERE mem_no= ? and ds_no=? ";

	private static final String DELETE = "DELETE FROM mdst_record WHERE mem_no =? and ds_no=?";
	private static final String UPDATE = "UPDATE mdst_record set trac_time=? WHERE mem_no =? and ds_no=?" ;

	//用一位會員查找他的所有文章追蹤
	private static final String GET_ALL_BY_MEM_STMT = "SELECT mem_no,ds_no,to_char(trac_time,'yyyy-mm-dd hh24:mi:ss') trac_time FROM mdst_record where mem_no= ? ORDER BY mem_no";

	@Override
	public void insert(Mdst_recordVO mdst_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mdst_recordVO.getMem_no());
			pstmt.setString(2, mdst_recordVO.getDs_no());
			pstmt.setTimestamp(3, mdst_recordVO.getTrac_time());

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
	public void update(Mdst_recordVO mdst_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(UPDATE);
          
			pstmt.setTimestamp(1, mdst_recordVO.getTrac_time());
			pstmt.setString(2, mdst_recordVO.getMem_no());
			pstmt.setString(3, mdst_recordVO.getDs_no());
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
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(String mem_no, String ds_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mem_no);
			pstmt.setString(2, ds_no);

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
	public Mdst_recordVO findByPrimaryKey(String mem_no, String ds_no) {
		Mdst_recordVO mdst_recordVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mem_no);
            pstmt.setString(2, ds_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mdst_recordVO = new Mdst_recordVO();

				mdst_recordVO.setMem_no(rs.getString("mem_no"));
				mdst_recordVO.setDs_no(rs.getString("ds_no"));
				mdst_recordVO.setTrac_time(rs.getTimestamp("trac_time"));
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return mdst_recordVO;
	}

   @Override
	public List<Mdst_recordVO> getAll() {
		List<Mdst_recordVO> list = new ArrayList<>();
		Mdst_recordVO mdst_recordVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mdst_recordVO = new Mdst_recordVO();
				mdst_recordVO.setMem_no(rs.getString("mem_no"));
				mdst_recordVO.setDs_no(rs.getString("ds_no"));
				mdst_recordVO.setTrac_time(rs.getTimestamp("trac_time"));

				list.add(mdst_recordVO);
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
	public List<Mdst_recordVO> getMdstrsByMem_no(String mem_no) {
		List<Mdst_recordVO> list = new ArrayList<>();
		Mdst_recordVO mdst_recordVO = null;

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
				mdst_recordVO = new Mdst_recordVO();
				mdst_recordVO.setMem_no(rs.getString("mem_no"));
				mdst_recordVO.setDs_no(rs.getString("ds_no"));
				mdst_recordVO.setTrac_time(rs.getTimestamp("trac_time"));

				list.add(mdst_recordVO);
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
	
	

	public static void main(String[]  args) {
		Mdst_recordJDBCDAO dao = new Mdst_recordJDBCDAO();

//		// 新增
//		Mdst_recordVO mdstVO1 = new Mdst_recordVO();
//		mdstVO1.setMem_no("M000001");
//		mdstVO1.setDs_no("DS0002");
//		mdstVO1.setTrac_time(Timestamp.valueOf("2020-01-01 14:43:20"));
//		dao.insert(mdstVO1);
       
		//修改
//		Mdst_recordVO mdstVO2 =new Mdst_recordVO();
//		mdstVO2.setMem_no("M000001");
//		mdstVO2.setDs_no("DS0005");
//		mdstVO2.setTrac_time(java.sql.Timestamp.valueOf("2020-01-28 12:45:09"));
//		dao.update(mdstVO2);
//		System.out.println("修改OK!");
//		//刪除
//		dao.delete("M000001", "DS0005");
//		
		//查詢一件
		Mdst_recordVO mdstVO3 = dao.findByPrimaryKey("M000002","DS0001");
		System.out.print(mdstVO3.getMem_no()+",");
		System.out.print(mdstVO3.getDs_no()+",");
		System.out.println(mdstVO3.getTrac_time());
		System.out.println("-------------------------");
//		
//		//查詢所有
//		List<Mdst_recordVO> list = dao.getAll();
//		for(Mdst_recordVO aMdst : list) {
//			System.out.print(aMdst.getMem_no()+",");
//			System.out.print(aMdst.getDs_no()+",");
//			System.out.println(aMdst.getTrac_time());
//			System.out.println();
//		}
//		
		
	}

}
