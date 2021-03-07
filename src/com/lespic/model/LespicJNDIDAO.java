package com.lespic.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class LespicJNDIDAO implements LespicDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DA105G5");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO LESPIC VALUES ('LSP'||LPAD(to_char(LPIC_SEQ.NEXTVAL), 3, '0'),?,?)";

	private static final String DELETE_LESPIC = "DELETE FROM LESPIC WHERE LPIC_SEQ = ?";

	private static final String UPDATE = "UPDATE LESPIC SET LES_NO = ?, LPIC = ? WHERE LPIC_SEQ = ?";

	private static final String GET_ALL_LESPIC = "SELECT * FROM LESPIC";
	private static final String GET_ONE_LESPIC = "SELECT * FROM LESPIC WHERE LPIC_SEQ = ?";
	private static final String GET_LPIC_BY_LESNO = "SELECT * FROM LESPIC WHERE LES_NO = ?";

	@Override
	public void insert2(LespicVO lespicVO, Connection con) {
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, lespicVO.getLes_no());
			pstmt.setBytes(2, lespicVO.getLpic());

			pstmt.executeUpdate();
			// Handle any SQL errors
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
	public void insert(LespicVO lespicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, lespicVO.getLes_no());
			pstmt.setBytes(2, lespicVO.getLpic());

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
	public void update(LespicVO lespicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, lespicVO.getLes_no());
			pstmt.setBytes(2, lespicVO.getLpic());
			pstmt.setString(3, lespicVO.getLpic_seq());

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
	public void delete(String lpic_seq) {
		int updateCount_record = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_LESPIC);
			pstmt.setString(1, lpic_seq);
			updateCount_record = pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除圖片編號:" + lpic_seq + ":" + updateCount_record + "筆");
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
	public LespicVO findByPrimaryKey(String lpic_seq) {
		LespicVO lespicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_LESPIC);

			pstmt.setString(1, lpic_seq);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				lespicVO = new LespicVO();
				lespicVO.setLpic_seq(rs.getString("lpic_seq"));
				lespicVO.setLes_no(rs.getString("les_no"));
				lespicVO.setLpic(rs.getBytes("lpic"));
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
		return lespicVO;
	}

	@Override
	public List<LespicVO> findByLes_no(String les_no) {
		List<LespicVO> list = new ArrayList<LespicVO>();
		LespicVO lespicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LPIC_BY_LESNO);
			pstmt.setString(1, les_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				lespicVO = new LespicVO();
				lespicVO.setLpic_seq(rs.getString("lpic_seq"));
				lespicVO.setLes_no(rs.getString("les_no"));
				lespicVO.setLpic(rs.getBytes("lpic"));
				list.add(lespicVO);
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
	public List<LespicVO> getAll() {
		List<LespicVO> list = new ArrayList<LespicVO>();
		LespicVO lespicVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_LESPIC);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				lespicVO = new LespicVO();
				lespicVO.setLpic_seq(rs.getString("lpic_seq"));
				lespicVO.setLes_no(rs.getString("les_no"));
				lespicVO.setLpic(rs.getBytes("lpic"));
				list.add(lespicVO);
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
