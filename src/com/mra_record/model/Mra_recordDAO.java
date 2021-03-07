package com.mra_record.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Timestamp;

public class Mra_recordDAO implements Mra_recordDAO_interface {
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

	private static final String INSERT = "INSERT INTO MRA_RECORD(rar_no, mem_no, mpo_no, rep_time, rep_content, rep_state)VALUES('RA'||LPAD(to_char(RARNO_SEQ.NEXTVAL),5,'0'),?,?,?,?,?)";
	private static final String GET_ONE_STMT = "SELECT  rar_no, mem_no, mpo_no, to_char(rep_time,'yyyy-mm-dd hh24:mi:ss') rep_time, rep_content, rep_state FROM MRA_RECORD WHERE rar_no=?";
	private static final String GET_ALL_STMT = "SELECT  rar_no, mem_no, mpo_no, to_char(rep_time,'yyyy-mm-dd hh24:mi:ss') rep_time, rep_content, rep_state FROM MRA_RECORD ORDER BY rar_no";
	private static final String UPDATE = "UPDATE MRA_RECORD SET mem_no=?, mpo_no=?, rep_time=?, rep_content=?, rep_state=? WHERE rar_no=? ";
	private static final String DELETE = "DELETE FROM MRA_RECORD WHERE rar_no=?";

	@Override
	public void insert(Mra_recordVO mra_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);

			pstmt.setString(1, mra_recordVO.getMem_no());
			pstmt.setString(2, mra_recordVO.getMpo_no());
			pstmt.setTimestamp(3, mra_recordVO.getRep_time());
			pstmt.setString(4, mra_recordVO.getRep_content());
			pstmt.setString(5, mra_recordVO.getRep_state());

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
	public void update(Mra_recordVO mra_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mra_recordVO.getMem_no());
			pstmt.setString(2, mra_recordVO.getMpo_no());
			pstmt.setTimestamp(3, mra_recordVO.getRep_time());
			pstmt.setString(4, mra_recordVO.getRep_content());
			pstmt.setString(5, mra_recordVO.getRep_state());
            pstmt.setString(6, mra_recordVO.getRar_no());
			
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
	public void delete(String rar_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, rar_no);

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
	public Mra_recordVO findByPrimaryKey(String rar_no) {
		Mra_recordVO mra_recordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, rar_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				mra_recordVO= new Mra_recordVO();
				mra_recordVO.setRar_no(rs.getString("rar_no"));
				mra_recordVO.setMem_no(rs.getString("mem_no"));
				mra_recordVO.setMpo_no(rs.getString("mpo_no"));
				mra_recordVO.setRep_time(rs.getTimestamp("rep_time"));
				mra_recordVO.setRep_content(rs.getString("rep_content"));
				mra_recordVO.setRep_state(rs.getString("rep_state"));
			}

			// Handle any SQL errors
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

		return mra_recordVO;
	}

	@Override
	public List<Mra_recordVO> getAll() {
		List<Mra_recordVO> list = new ArrayList<Mra_recordVO>();
		Mra_recordVO mra_recordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mra_recordVO = new Mra_recordVO();
				mra_recordVO.setRar_no(rs.getString("rar_no"));
				mra_recordVO.setMem_no(rs.getString("mem_no"));
				mra_recordVO.setMpo_no(rs.getString("mpo_no"));
				mra_recordVO.setRep_time(rs.getTimestamp("rep_time"));
				mra_recordVO.setRep_content(rs.getString("rep_content"));
				mra_recordVO.setRep_state(rs.getString("rep_state"));

				list.add(mra_recordVO);
			}

			// Handle any SQL errors
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
