package com.mat_record.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Mat_recordDAO implements Mat_recordDAO_interface {
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DA105G5");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mat_recordVO.getMem_no());
			pstmt.setString(2, mat_recordVO.getMpo_no());
			pstmt.setTimestamp(3, mat_recordVO.getTrac_time());

			pstmt.executeUpdate();

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setTimestamp(1, mat_recordVO.getTrac_time());
			pstmt.setString(2, mat_recordVO.getMem_no());
			pstmt.setString(3, mat_recordVO.getMpo_no());

			pstmt.executeUpdate();

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mem_no);
			pstmt.setString(2, mpo_no);

			pstmt.executeUpdate();

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
			con = ds.getConnection();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mat_recordVO = new Mat_recordVO();
				mat_recordVO.setMem_no(rs.getString("mem_no"));
				mat_recordVO.setMpo_no(rs.getString("mpo_no"));
				mat_recordVO.setTrac_time(rs.getTimestamp("trac_time"));
				list.add(mat_recordVO);
			}

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
			con = ds.getConnection();
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



}
