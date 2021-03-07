package com.eqpic.model;

import java.util.*;
import java.sql.*;
import java.io.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EqpicDAO implements EqpicDAO_interface {

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

	private static final String INSERT_STMT = "INSERT INTO EQPIC (EPIC_SEQ,DS_NO,EP_SEQ,EPIC) VALUES (('EPP'||LPAD(TO_CHAR(EPIC_SEQ.NEXTVAL),3,'0')),?,?,?)";
	private static final String GET_DS_ALL = "SELECT EPIC_SEQ,DS_NO,EP_SEQ,EPIC FROM EQPIC WHERE DS_NO = ?";
	private static final String GET_EP_ALL = "SELECT EPIC_SEQ,DS_NO,EP_SEQ,EPIC FROM EQPIC WHERE EP_SEQ=?";
	private static final String GET_ONE_EPIC = "SELECT EPIC_SEQ,DS_NO,EP_SEQ,EPIC FROM EQPIC WHERE EPIC_SEQ = ?";
	private static final String GET_EPIC_SEQ_BY_EP_SEQ = "SELECT EPIC_SEQ FROM EQPIC WHERE EP_SEQ =?";
	private static final String DELETE = "DELETE FROM EQPIC WHERE EPIC_SEQ = ?";
	private static final String UPDATE = "UPDATE EQPIC SET EP_SEQ=?,EPIC=? WHERE EPIC_SEQ =?";

	@Override
	public void insert(EqpicVO eqpicVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, eqpicVO.getDs_no());
			pstmt.setString(2, eqpicVO.getEp_seq());
			pstmt.setBytes(3, eqpicVO.getEpic());
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

	@Override
	public void update(EqpicVO eqpicVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, eqpicVO.getEp_seq());
			pstmt.setBytes(2, eqpicVO.getEpic());
			pstmt.setString(3, eqpicVO.getEpic_seq());
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
	public void delete(String epic_seq) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, epic_seq);

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
	public EqpicVO findByPrimaryKey(String epic_seq) {
		EqpicVO eqpicVO=null;
		Connection con=null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
	
		try {

			con = ds.getConnection();
			pstmt=con.prepareStatement(GET_ONE_EPIC);
			
			pstmt.setString(1, epic_seq);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				eqpicVO=new EqpicVO();
				eqpicVO.setEpic_seq(rs.getString("epic_seq"));
				eqpicVO.setDs_no(rs.getString("ds_no"));
				eqpicVO.setEp_seq(rs.getString("ep_seq"));
				eqpicVO.setEpic(rs.getBytes("epic"));
			}
			
		}catch(SQLException se){
			throw new RuntimeException("A database error occured" + se.getMessage());
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				}catch(SQLException se) {
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
		return eqpicVO;
	}
	
	@Override
	public void insertByEquip(EqpicVO eqpicVO , Connection con) {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, eqpicVO.getDs_no());
			pstmt.setString(2, eqpicVO.getEp_seq());
			pstmt.setBytes(3, eqpicVO.getEpic());
			pstmt.executeUpdate();
		}catch(SQLException se) {
			if(con!=null) {
				try {
					System.err.print(" Transaction is being ");
					System.err.print(" rolled back from Epic ");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public List<EqpicVO> findByDsAll(String ds_no) {

		List<EqpicVO> list = new ArrayList<EqpicVO>();
		EqpicVO eqpicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_DS_ALL);

			pstmt.setString(1, ds_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// epicVO 也稱為 Domain objects
				eqpicVO = new EqpicVO();
				eqpicVO.setEpic_seq(rs.getString("epic_seq"));
				eqpicVO.setDs_no(rs.getString("ds_no"));
				eqpicVO.setEp_seq(rs.getString("ep_seq"));
				eqpicVO.setEpic(rs.getBytes("epic"));
				list.add(eqpicVO);
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

	@Override
	public List<EqpicVO> findByAEp_seq_All_Epic(String ep_seq) {

		List<EqpicVO> list = new ArrayList<EqpicVO>();
		EqpicVO eqpicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_EP_ALL);

			pstmt.setString(1, ep_seq);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// epicVO 也稱為 Domain objects
				eqpicVO = new EqpicVO();
				eqpicVO.setEpic_seq(rs.getString("epic_seq"));
				eqpicVO.setEp_seq(rs.getString("ep_seq"));
				eqpicVO.setDs_no(rs.getString("ds_no"));
				eqpicVO.setEpic(rs.getBytes("epic"));
				list.add(eqpicVO);

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

	@Override
	public List<String> AEp_seq_All_Epic_seq(String ep_seq){
		
		List<String> list = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_EPIC_SEQ_BY_EP_SEQ );
			
			pstmt.setString(1, ep_seq);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String epic_seq=rs.getString("epic_seq");
				list.add(epic_seq);
			}
			
			// Handle any driver errors
		}catch (SQLException se) {
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