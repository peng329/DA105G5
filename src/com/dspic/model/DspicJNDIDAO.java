package com.dspic.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DspicJNDIDAO implements DspicDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DA105G5");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO DSPIC VALUES ('DSP'||LPAD(to_char(DPIC_SEQ.NEXTVAL), 3, '0'),?,?)";

	private static final String DELETE_DSPIC = "DELETE FROM DSPIC WHERE DPIC_SEQ = ?";

	private static final String UPDATE = "UPDATE DSPIC SET DS_NO = ?, DPIC = ? WHERE DPIC_SEQ = ?";

	private static final String GET_ALL_DSPIC = "SELECT * FROM DSPIC";
	private static final String GET_ONE_DSPIC = "SELECT * FROM DSPIC WHERE DPIC_SEQ = ?";
	private static final String GET_DSPIC_BY_DSNO = "SELECT * FROM DSPIC WHERE DS_NO = ?";

	@Override
	public void insert(DspicVO dspicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, dspicVO.getDs_no());
			pstmt.setBytes(2, dspicVO.getDpic());

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
	public void inert2(DspicVO dspicVO, Connection con) {
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, dspicVO.getDs_no());
			pstmt.setBytes(2, dspicVO.getDpic());

			pstmt.executeUpdate();
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-dspic");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
		}
		
	}

	@Override
	public void update(DspicVO dspicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, dspicVO.getDs_no());
			pstmt.setBytes(2, dspicVO.getDpic());
			pstmt.setString(4, dspicVO.getDpic_seq());

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
	public void delete(String dpic_seq) {

		int updateCount_record = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_DSPIC);
			pstmt.setString(1, dpic_seq);
			updateCount_record = pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除紀錄編號:" + dpic_seq + ":" + updateCount_record + "筆");
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
	public DspicVO findByPrimaryKey(String dpic_seq) {
		DspicVO dspicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_DSPIC);

			pstmt.setString(1, dpic_seq);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				dspicVO = new DspicVO();
				dspicVO.setDpic_seq(rs.getString("dpic_seq"));
				dspicVO.setDs_no(rs.getString("ds_no"));
				dspicVO.setDpic(rs.getBytes("dpic"));
			}
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
		return dspicVO;
	}

	@Override
	public List<DspicVO> findByDs_no(String ds_no) {
		List<DspicVO> list = new LinkedList<DspicVO>();
		DspicVO dspicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_DSPIC_BY_DSNO);
			pstmt.setString(1, ds_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dspicVO = new DspicVO();
				dspicVO.setDpic_seq(rs.getString("dpic_seq"));
				dspicVO.setDs_no(rs.getString("ds_no"));
				dspicVO.setDpic(rs.getBytes("dpic"));
				list.add(dspicVO);
			}
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
		return list;
	}

	@Override
	public List<DspicVO> getAll() {
		List<DspicVO> list = new ArrayList<DspicVO>();
		DspicVO dspicVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_DSPIC);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dspicVO = new DspicVO();
				dspicVO.setDpic_seq(rs.getString("dpic_seq"));
				dspicVO.setDs_no(rs.getString("ds_no"));
				dspicVO.setDpic(rs.getBytes("dpic"));
				list.add(dspicVO);
			} 
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
		return list;
	}

}
