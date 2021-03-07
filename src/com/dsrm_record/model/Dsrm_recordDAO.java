package com.dsrm_record.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Dsrm_record;

public class Dsrm_recordDAO implements Dsrm_recordDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DA105G5");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO DSRM_RECORD VALUES ('DSR'||LPAD(to_char(RDSRNO_SEQ.NEXTVAL), 3, '0'),?,?,?,?)";

	private static final String DELETE_DSRM_RECORD = "DELETE FROM DSRM_RECORD WHERE RDSR_NO = ?";

	private static final String UPDATE = "UPDATE DSRM_RECORD SET DS_NO = ?, MEM_NO = ?, REP_CONTENT = ? ,REP_STATE = ? WHERE RDSR_NO = ?";

	private static final String GET_ALL_RECORD = "SELECT * FROM DSRM_RECORD";
	private static final String GET_ONE_RECORD = "SELECT * FROM DSRM_RECORD WHERE RDSR_NO = ?";
	private static final String GET_RECORD_BY_DSNO = "SELECT * FROM DSRM_RECORD WHERE DS_NO = ?";
	private static final String GET_RECORD_BY_MEMNO = "SELECT * FROM DSRM_RECORD WHERE MEM_NO = ?";

	@Override
	public void insert(Dsrm_recordVO dsrm_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, dsrm_recordVO.getDs_no());
			pstmt.setString(2, dsrm_recordVO.getMem_no());
			pstmt.setString(3, dsrm_recordVO.getRep_content());
			pstmt.setString(4, dsrm_recordVO.getRep_state());

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
	public void update(Dsrm_recordVO dsrm_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, dsrm_recordVO.getDs_no());
			pstmt.setString(2, dsrm_recordVO.getMem_no());
			pstmt.setString(3, dsrm_recordVO.getRep_content());
			pstmt.setString(4, dsrm_recordVO.getRep_state());
			pstmt.setString(5, dsrm_recordVO.getRdsr_no());
			
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
	public void delete(String rdsr_no) {
		int updateCount_record = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			
			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_DSRM_RECORD);
			pstmt.setString(1, rdsr_no);
			updateCount_record = pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除紀錄編號:" + rdsr_no + ":" + updateCount_record + "筆");
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
	public Dsrm_recordVO findByPrimaryKey(String rdsr_no) {
		Dsrm_recordVO dsrm_recordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_RECORD);
			
			pstmt.setString(1, rdsr_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dsrm_recordVO = new Dsrm_recordVO();
				dsrm_recordVO.setRdsr_no(rs.getString("rdsr_no"));
				dsrm_recordVO.setDs_no(rs.getString("ds_no"));
				dsrm_recordVO.setMem_no(rs.getString("mem_no"));
				dsrm_recordVO.setRep_content(rs.getString("rep_content"));
				dsrm_recordVO.setRep_state(rs.getString("rep_state"));
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
		return dsrm_recordVO;
	}

	@Override
	public List<Dsrm_recordVO> findByDiveshopNo(String ds_no) {
		List<Dsrm_recordVO> list = new ArrayList<Dsrm_recordVO>();
		Dsrm_recordVO dsrm_recordVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RECORD_BY_DSNO);
			pstmt.setString(1, ds_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dsrm_recordVO = new Dsrm_recordVO();
				dsrm_recordVO.setRdsr_no(rs.getString("rdsr_no"));
				dsrm_recordVO.setDs_no(rs.getString("ds_no"));
				dsrm_recordVO.setMem_no(rs.getString("mem_no"));
				dsrm_recordVO.setRep_content(rs.getString("rep_content"));
				dsrm_recordVO.setRep_state(rs.getString("rep_state"));
				list.add(dsrm_recordVO);
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
	public List<Dsrm_recordVO> findByByMemNo(String mem_no) {
		List<Dsrm_recordVO> list = new ArrayList<Dsrm_recordVO>();
		Dsrm_recordVO dsrm_recordVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RECORD_BY_MEMNO);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dsrm_recordVO = new Dsrm_recordVO();
				dsrm_recordVO.setRdsr_no(rs.getString("rdsr_no"));
				dsrm_recordVO.setDs_no(rs.getString("ds_no"));
				dsrm_recordVO.setMem_no(rs.getString("mem_no"));
				dsrm_recordVO.setRep_content(rs.getString("rep_content"));
				dsrm_recordVO.setRep_state(rs.getString("rep_state"));
				list.add(dsrm_recordVO);
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
	public List<Dsrm_recordVO> getAll(Map<String, String[]> map) {
		List<Dsrm_recordVO> list = new ArrayList<Dsrm_recordVO>();
		Dsrm_recordVO dsrm_recordVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			String finalSQL = "select * from dsrm_record "
			          + jdbcUtil_CompositeQuery_Dsrm_record.get_WhereCondition(map)
			          + "order by rdsr_no";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("●●finalSQL = " + finalSQL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dsrm_recordVO = new Dsrm_recordVO();
				dsrm_recordVO.setRdsr_no(rs.getString("rdsr_no"));
				dsrm_recordVO.setDs_no(rs.getString("ds_no"));
				dsrm_recordVO.setMem_no(rs.getString("mem_no"));
				dsrm_recordVO.setRep_content(rs.getString("rep_content"));
				dsrm_recordVO.setRep_state(rs.getString("rep_state"));
				list.add(dsrm_recordVO);
			}
		}catch (SQLException se) {
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
	public List<Dsrm_recordVO> getAll() {
		List<Dsrm_recordVO> list = new ArrayList<Dsrm_recordVO>();
		Dsrm_recordVO dsrm_recordVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_RECORD);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dsrm_recordVO = new Dsrm_recordVO();
				dsrm_recordVO.setRdsr_no(rs.getString("rdsr_no"));
				dsrm_recordVO.setDs_no(rs.getString("ds_no"));
				dsrm_recordVO.setMem_no(rs.getString("mem_no"));
				dsrm_recordVO.setRep_content(rs.getString("rep_content"));
				dsrm_recordVO.setRep_state(rs.getString("rep_state"));
				list.add(dsrm_recordVO);
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
