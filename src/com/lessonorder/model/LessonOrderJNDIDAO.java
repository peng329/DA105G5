package com.lessonorder.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class LessonOrderJNDIDAO implements LessonOrderDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DA105G5");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO LESSON_ORDER VALUES('L'||to_char(sysdate,'yyyymmdd')||'-'||LPAD(to_char(LESSONOD_SEQ.NEXTVAL), 3, '0'),?,?,?,?,?,?,?,?)";

	private static final String DELETE_LES_ORDER = "DELETE FROM LESSON_ORDER WHERE LES_O_NO = ? ";

	private static final String UPDATE = "UPDATE LESSON_ORDER SET MEM_NO = ?, LES_NO = ?,DS_NO = ?, MEM_NAME = ?, LES_NAME = ?,"
			+ " COST = ?, COACH = ?, LO_STATE = ? WHERE LES_O_NO = ?";

	private static final String GET_ALL_STMT = "SELECT * FROM LESSON_ORDER";
	private static final String GET_ONE_ORDER = "SELECT * FROM LESSON_ORDER WHERE LES_O_NO = ?";
	private static final String GET_ORDER_BY_MEMNO = "SELECT * FROM LESSON_ORDER WHERE MEM_NO = ?";
	private static final String GET_ORDER_BY_LESNO = "SELECT * FROM LESSON_ORDER WHERE LES_NO = ?";
	private static final String GET_ORDER_BY_DSNO = "SELECT * FROM LESSON_ORDER WHERE DS_NO = ?";
	
	private static final String GET_MEM_NO_BY_LESNO = "SELECT MEM_NO FROM LESSON_ORDER WHERE LES_NO = ?";
	
	@Override
	public void insert(LessonOrderVO lessonOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, lessonOrderVO.getMem_no());
			pstmt.setString(2, lessonOrderVO.getLes_no());
			pstmt.setString(3, lessonOrderVO.getDs_no());
			pstmt.setString(4, lessonOrderVO.getMem_name());
			pstmt.setString(5, lessonOrderVO.getLes_name());
			pstmt.setInt(6, lessonOrderVO.getCost());
			pstmt.setString(7, lessonOrderVO.getCoach());
			pstmt.setString(8, lessonOrderVO.getLo_state());
			
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
	public void update(LessonOrderVO lessonOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, lessonOrderVO.getMem_no());
			pstmt.setString(2, lessonOrderVO.getLes_no());
			pstmt.setString(3, lessonOrderVO.getDs_no());
			pstmt.setString(4, lessonOrderVO.getMem_name());
			pstmt.setString(5, lessonOrderVO.getLes_name());
			pstmt.setInt(6, lessonOrderVO.getCost());
			pstmt.setString(7, lessonOrderVO.getCoach());
			pstmt.setString(8, lessonOrderVO.getLo_state());
			pstmt.setString(9, lessonOrderVO.getLes_o_no());
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
	public void delete(String les_o_no) {
		int updateCount_order = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {			
			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_LES_ORDER);
			pstmt.setString(1, les_o_no);
			updateCount_order = pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除訂單編號:" + les_o_no + ":" + updateCount_order + "筆");
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
	public LessonOrderVO findByPrimaryKey(String les_o_no) {
		LessonOrderVO lessonOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_ORDER);
			
			pstmt.setString(1, les_o_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				lessonOrderVO = new LessonOrderVO();
				lessonOrderVO.setLes_o_no(rs.getString("les_o_no"));
				lessonOrderVO.setMem_no(rs.getString("mem_no"));
				lessonOrderVO.setLes_no(rs.getString("les_no"));
				lessonOrderVO.setDs_no(rs.getString("ds_no"));
				lessonOrderVO.setMem_name(rs.getString("mem_name"));
				lessonOrderVO.setLes_name(rs.getString("les_name"));
				lessonOrderVO.setCost(rs.getInt("cost"));
				lessonOrderVO.setCoach(rs.getString("coach"));
				lessonOrderVO.setLo_state(rs.getString("lo_state"));
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
		return lessonOrderVO;
	}

	@Override
	public List<LessonOrderVO> findByMem_no(String mem_no) {
		List<LessonOrderVO> list = new ArrayList<LessonOrderVO>();
		LessonOrderVO lessonOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDER_BY_MEMNO);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				lessonOrderVO = new LessonOrderVO();
				lessonOrderVO.setLes_o_no(rs.getString("les_o_no"));
				lessonOrderVO.setMem_no(rs.getString("mem_no"));
				lessonOrderVO.setLes_no(rs.getString("les_no"));
				lessonOrderVO.setDs_no(rs.getString("ds_no"));
				lessonOrderVO.setMem_name(rs.getString("mem_name"));
				lessonOrderVO.setLes_name(rs.getString("les_name"));
				lessonOrderVO.setCost(rs.getInt("cost"));
				lessonOrderVO.setCoach(rs.getString("coach"));
				lessonOrderVO.setLo_state(rs.getString("lo_state"));
				list.add(lessonOrderVO);
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
	public List<LessonOrderVO> findByDs_no(String ds_no) {
		List<LessonOrderVO> list = new ArrayList<LessonOrderVO>();
		LessonOrderVO lessonOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDER_BY_DSNO);
			pstmt.setString(1, ds_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				lessonOrderVO = new LessonOrderVO();
				lessonOrderVO.setLes_o_no(rs.getString("les_o_no"));
				lessonOrderVO.setMem_no(rs.getString("mem_no"));
				lessonOrderVO.setLes_no(rs.getString("les_no"));
				lessonOrderVO.setDs_no(rs.getString("ds_no"));
				lessonOrderVO.setMem_name(rs.getString("mem_name"));
				lessonOrderVO.setLes_name(rs.getString("les_name"));
				lessonOrderVO.setCost(rs.getInt("cost"));
				lessonOrderVO.setCoach(rs.getString("coach"));
				lessonOrderVO.setLo_state(rs.getString("lo_state"));
				list.add(lessonOrderVO);
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
	public List<LessonOrderVO> findByLes_no(String les_no) {
		List<LessonOrderVO> list = new ArrayList<LessonOrderVO>();
		LessonOrderVO lessonOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDER_BY_LESNO);
			pstmt.setString(1, les_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				lessonOrderVO = new LessonOrderVO();
				lessonOrderVO.setLes_o_no(rs.getString("les_o_no"));
				lessonOrderVO.setMem_no(rs.getString("mem_no"));
				lessonOrderVO.setLes_no(rs.getString("les_no"));
				lessonOrderVO.setDs_no(rs.getString("ds_no"));
				lessonOrderVO.setMem_name(rs.getString("mem_name"));
				lessonOrderVO.setLes_name(rs.getString("les_name"));
				lessonOrderVO.setCost(rs.getInt("cost"));
				lessonOrderVO.setCoach(rs.getString("coach"));
				lessonOrderVO.setLo_state(rs.getString("lo_state"));
				list.add(lessonOrderVO);
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
	public List<LessonOrderVO> getAll() {
		List<LessonOrderVO> list = new ArrayList<LessonOrderVO>();
		LessonOrderVO lessonOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				lessonOrderVO = new LessonOrderVO();
				lessonOrderVO.setLes_o_no(rs.getString("les_o_no"));
				lessonOrderVO.setMem_no(rs.getString("mem_no"));
				lessonOrderVO.setLes_no(rs.getString("les_no"));
				lessonOrderVO.setDs_no(rs.getString("ds_no"));
				lessonOrderVO.setMem_name(rs.getString("mem_name"));
				lessonOrderVO.setLes_name(rs.getString("les_name"));
				lessonOrderVO.setCost(rs.getInt("cost"));
				lessonOrderVO.setCoach(rs.getString("coach"));
				lessonOrderVO.setLo_state(rs.getString("lo_state"));
				list.add(lessonOrderVO);
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
	public List<String> findmem_noByLes_no(String les_no) {
		List<String> list = new ArrayList<String>();
		LessonOrderVO lessonOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEM_NO_BY_LESNO);
			pstmt.setString(1, les_no);
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				lessonOrderVO = new LessonOrderVO();
				String mem_no = (rs.getString("mem_no"));
				list.add(mem_no);
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
