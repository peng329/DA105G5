package com.mat_record.model;

import java.util.*;
import java.sql.*;

public class Mat_recordJDBCDAO implements Mat_recordDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String password = "DA105G5";

	private static final String INSERT_STMT = "INSERT into MAT_RECORD(MEM_NO, MPO_NO, TRAC_TIME)VALUES (?,?,?)";
	private static final String GET_ALL_STMT = "SELECT mem_no,mpo_no,to_char(trac_time,'yyyy-mm-dd hh24:mi:ss') trac_time FROM mat_record ORDER BY mem_no";
	private static final String GET_ONE_STMT = "SELECT mem_no,mpo_no,to_char(trac_time,'yyyy-mm-dd hh24:mi:ss') trac_time FROM mat_record WHERE mem_no= ? and mpo_no=? ";

	private static final String DELETE = "DELETE FROM mat_record WHERE mem_no =? and mpo_no=?";
	private static final String UPDATE = "UPDATE mat_record set trac_time=? WHERE mem_no =? and mpo_no=?";
	
	//用一位會員查找他的所有文章追蹤
	private static final String GET_ALL_BY_MEM_STMT = "SELECT mem_no,mpo_no,to_char(trac_time,'yyyy-mm-dd hh24:mi:ss') trac_time FROM mat_record WHERE mem_no= ? ORDER BY mem_no";

	@Override
	public void insert(Mat_recordVO mat_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mat_recordVO.getMem_no());
			pstmt.setString(2, mat_recordVO.getMpo_no());
			pstmt.setTimestamp(3, mat_recordVO.getTrac_time());

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
	public void update(Mat_recordVO mat_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setTimestamp(1, mat_recordVO.getTrac_time());
			pstmt.setString(2, mat_recordVO.getMem_no());
			pstmt.setString(3, mat_recordVO.getMpo_no());

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
	public void delete(String mem_no, String mpo_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mem_no);
			pstmt.setString(2, mpo_no);

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
	public Mat_recordVO findByPrimaryKey(String mem_no, String mpo_no) {
		Mat_recordVO mat_recordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, mem_no);
			pstmt.setString(2, mpo_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mat_recordVO = new Mat_recordVO();
				mat_recordVO.setMem_no(rs.getString("mem_no"));
				mat_recordVO.setMpo_no(rs.getString("mpo_no"));
				mat_recordVO.setTrac_time(rs.getTimestamp("trac_time"));
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
		return mat_recordVO;
	}

	@Override
	public List<Mat_recordVO> getAll() {
		List<Mat_recordVO> list = new ArrayList<>();
		Mat_recordVO mat_recordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mat_recordVO = new Mat_recordVO();
				mat_recordVO.setMem_no(rs.getString("mem_no"));
				mat_recordVO.setMpo_no(rs.getString("mpo_no"));
				mat_recordVO.setTrac_time(rs.getTimestamp("trac_time"));
				list.add(mat_recordVO);
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
	public List<Mat_recordVO> getMatrsByMem_no(String mem_no) {
		List<Mat_recordVO> list = new ArrayList<>();
		Mat_recordVO mat_recordVO = null;
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
				mat_recordVO = new Mat_recordVO();
				mat_recordVO.setMem_no(rs.getString("mem_no"));
				mat_recordVO.setMpo_no(rs.getString("mpo_no"));
				mat_recordVO.setTrac_time(rs.getTimestamp("trac_time"));
				list.add(mat_recordVO);
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
		Mat_recordJDBCDAO dao = new Mat_recordJDBCDAO();

//		// 新增
//		Mat_recordVO matVO1 = new Mat_recordVO();
//		matVO1.setMem_no("M000007");
//		matVO1.setMpo_no("MP000005");
//		matVO1.setTrac_time(Timestamp.valueOf("2020-02-01 13:53:09"));
//		dao.insert(matVO1);
//		System.out.println("新增OK!");

//		// 修改
//		Mat_recordVO matVO2 = new Mat_recordVO();
//		matVO2.setMem_no("M000007");
//		matVO2.setMpo_no("MP000005");
//		matVO2.setTrac_time(Timestamp.valueOf("2020-02-02 09:39:12"));
//		dao.update(matVO2);
//		System.out.println("修改OK!");

		// 刪除
		dao.delete("M000007", "MP000005");
		System.out.println("刪除OK!");

		// 查詢一件
		Mat_recordVO matVO3 = dao.findByPrimaryKey("M000002", "MP000001");
		System.out.print(matVO3.getMem_no() + ",");
		System.out.print(matVO3.getMpo_no() + ",");
		System.out.println(matVO3.getTrac_time());
		System.out.println("------------------");

		// 查詢全部
		List<Mat_recordVO> list = dao.getAll();
		for (Mat_recordVO mat : list) {
			System.out.print(mat.getMem_no() + ",");
			System.out.print(mat.getMpo_no() + ",");
			System.out.println(mat.getTrac_time());
			System.out.println();
		}
	}

}
