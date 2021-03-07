package com.mdpst_record.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mdst_record.model.Mdst_recordVO;



public class Mdpst_recordDAO implements Mdpst_recordDAO_interface {
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mdpst_recordVO.getMem_no());
			pstmt.setString(2, mdpst_recordVO.getDp_no());
			pstmt.setTimestamp(3, mdpst_recordVO.getTrac_time());

			pstmt.executeUpdate();
;
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setTimestamp(1, mdpst_recordVO.getTrac_time());
			pstmt.setString(2, mdpst_recordVO.getMem_no());
			pstmt.setString(3, mdpst_recordVO.getDp_no());

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
	public void delete(String mem_no, String dp_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mem_no);
			pstmt.setString(2, dp_no);

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
	public Mdpst_recordVO findByPrimaryKey(String mem_no, String dp_no) {
		Mdpst_recordVO mdpst_recordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mdpst_recordVO = new Mdpst_recordVO();
				mdpst_recordVO.setMem_no(rs.getString("mem_no"));
				mdpst_recordVO.setDp_no(rs.getString("dp_no"));
				mdpst_recordVO.setTrac_time(rs.getTimestamp("trac_time"));
				list.add(mdpst_recordVO);
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
	public List<Mdpst_recordVO> getMdpstrsByMem_no (String mem_no){
		List<Mdpst_recordVO> list = new ArrayList<>();
		Mdpst_recordVO mdpst_recordVO = null;
		Connection con = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
