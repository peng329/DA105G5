package com.rodetail.model;

import java.util.*;
import java.sql.*;
import com.equip.model.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class RoDetailDAO implements RoDetailDAO_interface {

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

	private static final String INSERT_STMT = "INSERT INTO RO_DETAIL (RO_NO,EP_SEQ,EP_CRP) VALUES (?,?,?)";
	private static final String GET_AM_RORD = "SELECT RO_NO,EP_SEQ,EP_CRP FROM RO_DETAIL WHERE RO_NO=? ORDER BY EP_SEQ";
	private static final String GET_ONE_STMT = "SELECT RO_NO,EP_SEQ,EP_CRP FROM RO_DETAIL WHERE RO_NO=? AND EP_SEQ = ?";
	private static final String DELETE = "DELETE FROM RO_DETAIL WHERE RO_NO=? AND EP_SEQ = ?";
	private static final String UPDATE = "UPDATE RO_DETAIL SET EP_CRP = ? WHERE RO_NO = ? AND EP_SEQ = ?";

	@Override
	public void insert(RoDetailVO rdVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1,rdVO.getRo_no());
			pstmt.setString(2,rdVO.getEp_seq());
			pstmt.setInt(3,rdVO.getEp_crp ());

			pstmt.executeUpdate();

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

	public void insertByROrder(RoDetailVO rodetailVO,java.sql.Connection con) {

		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1,rodetailVO.getRo_no());
			pstmt.setString(2,rodetailVO.getEp_seq());
			pstmt.setInt(3,rodetailVO.getEp_crp ());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			if(con!=null) {
				try {
					System.err.print(" Transaction is being ");
					System.err.print(" rolled back from ROrder ");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	
	
	
	@Override
	public void update(RoDetailVO rdVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1,rdVO. getEp_crp());
			pstmt.setString(2,rdVO.getRo_no());
			pstmt.setString(3,rdVO.getEp_seq());
			
			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void delete(String ro_no,String ep_seq) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ro_no);
			pstmt.setString(2, ep_seq);

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public RoDetailVO findByPrimaryKey(String ro_no,String ep_seq) {

		RoDetailVO rdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1,ro_no);
			pstmt.setString(2,ep_seq);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// epcVO 也稱為 Domain objects
				rdVO = new RoDetailVO();
				rdVO.setRo_no(rs.getString("ro_no"));
				rdVO.setEp_seq(rs.getString("ep_seq"));
				rdVO.setEp_crp(rs.getInt("ep_crp"));
			}

			// Handle any driver errors
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
		return rdVO;
	}

	@Override
	public List<RoDetailVO> getSameRoRdAll(String ro_no) {
		List<RoDetailVO> list = new ArrayList<RoDetailVO>();
		RoDetailVO rdVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_AM_RORD);
			
			pstmt.setString(1,ro_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// rdVO 也稱為 Domain objects
				rdVO = new RoDetailVO();
				rdVO.setEp_seq(rs.getString("ep_seq"));
				rdVO.setEp_crp(rs.getInt("ep_crp"));
				list.add(rdVO); // Store the row in the list
			}
			// Handle any driver errors
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
}