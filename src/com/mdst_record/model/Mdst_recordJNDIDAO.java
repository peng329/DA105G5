package com.mdst_record.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Timestamp;

public class Mdst_recordJNDIDAO implements Mdst_recordDAO_interface {
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mdst_recordVO.getMem_no());
			pstmt.setString(2, mdst_recordVO.getDs_no());
			pstmt.setTimestamp(3, mdst_recordVO.getTrac_time());

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
	public void update(Mdst_recordVO mdst_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
          
			pstmt.setTimestamp(1, mdst_recordVO.getTrac_time());
			pstmt.setString(2, mdst_recordVO.getMem_no());
			pstmt.setString(3, mdst_recordVO.getDs_no());
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mem_no);
			pstmt.setString(2, ds_no);

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
	public Mdst_recordVO findByPrimaryKey(String mem_no, String ds_no) {
		Mdst_recordVO mdst_recordVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mdst_recordVO = new Mdst_recordVO();
				mdst_recordVO.setMem_no(rs.getString("mem_no"));
				mdst_recordVO.setDs_no(rs.getString("ds_no"));
				mdst_recordVO.setTrac_time(rs.getTimestamp("trac_time"));

				list.add(mdst_recordVO);
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
	public List<Mdst_recordVO> getMdstrsByMem_no(String mem_no) {
		List<Mdst_recordVO> list = new ArrayList<>();
		Mdst_recordVO mdst_recordVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
