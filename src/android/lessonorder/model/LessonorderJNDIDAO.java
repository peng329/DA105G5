package android.lessonorder.model;



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



public class LessonorderJNDIDAO implements LessonorderDAO_interface {
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

	private static final String UPDATE = "UPDATE LESSON_ORDER SET MEM_NO = ?, LES_NO = ?, MEM_NAME = ?, LES_NAME = ?, COST = ?, "
			+ "COACH = ?, LO_STATE = ? WHERE LES_O_NO = ?";

	private static final String GET_ALL_STMT = "SELECT * FROM LESSON_ORDER ORDER BY LES_O_NO DESC";
	private static final String GET_ONE_ORDER = "SELECT * FROM LESSON_ORDER WHERE LES_O_NO = ?";
	private static final String GET_ORDER_BY_MEMNO = "SELECT * FROM LESSON_ORDER WHERE MEM_NO = ? ORDER BY LES_O_NO DESC";
	private static final String GET_ORDER_BY_LESNO = "SELECT * FROM LESSON_ORDER WHERE LES_NO = ? ORDER BY LES_O_NO DESC";
    private static final String CHECK_MEM_ORDERED = "SELECT MEM_NO FROM LESSON_ORDER WHERE LES_NO = ? AND MEM_NO = ?"; 
	
	
	
	@Override
	public boolean isMemOrdered(String les_no,String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean isMemOrdered = false;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK_MEM_ORDERED);
			pstmt.setString(1, les_no);
			pstmt.setString(2, mem_no);
			ResultSet rs = pstmt.executeQuery();
			isMemOrdered = rs.next();
			return isMemOrdered;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isMemOrdered;
	}
		
	@Override
	public boolean insert(LessonorderVO lessonOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
        boolean isInsert = false;
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
			
			int count = pstmt.executeUpdate();
			isInsert = count>0? true:false;
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
           return isInsert;
	}
	@Override
	public LessonorderVO findByPrimaryKey(String les_o_no) {
		LessonorderVO lessonOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_ORDER);
			
			pstmt.setString(1, les_o_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				lessonOrderVO = new LessonorderVO();
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
	public List<LessonorderVO> findByMem_no(String mem_no) {
		List<LessonorderVO> list = new ArrayList<LessonorderVO>();
		LessonorderVO lessonorderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDER_BY_MEMNO);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				lessonorderVO = new LessonorderVO();
				lessonorderVO.setLes_o_no(rs.getString("les_o_no"));
				lessonorderVO.setMem_no(rs.getString("mem_no"));
				lessonorderVO.setLes_no(rs.getString("les_no"));
				lessonorderVO.setDs_no(rs.getString("ds_no"));
				lessonorderVO.setMem_name(rs.getString("mem_name"));
				lessonorderVO.setLes_name(rs.getString("les_name"));
				lessonorderVO.setCost(rs.getInt("cost"));
				lessonorderVO.setCoach(rs.getString("coach"));
				lessonorderVO.setLo_state(rs.getString("lo_state"));
				list.add(lessonorderVO);
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
	public List<LessonorderVO> findByLes_no(String les_no) {
		List<LessonorderVO> list = new ArrayList<>();
		LessonorderVO lessonorderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDER_BY_LESNO);
			pstmt.setString(1, les_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				lessonorderVO = new LessonorderVO();
				lessonorderVO.setLes_o_no(rs.getString("les_o_no"));
				lessonorderVO.setMem_no(rs.getString("mem_no"));
				lessonorderVO.setLes_no(rs.getString("les_no"));
				lessonorderVO.setDs_no(rs.getString("ds_no"));
				lessonorderVO.setMem_name(rs.getString("mem_name"));
				lessonorderVO.setLes_name(rs.getString("les_name"));
				lessonorderVO.setCost(rs.getInt("cost"));
				lessonorderVO.setCoach(rs.getString("coach"));
				lessonorderVO.setLo_state(rs.getString("lo_state"));
				list.add(lessonorderVO);
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
	public List<LessonorderVO> getAll() {
		List<LessonorderVO> list = new ArrayList<LessonorderVO>();
		LessonorderVO lessonorderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				lessonorderVO = new LessonorderVO();
				lessonorderVO.setLes_o_no(rs.getString("les_o_no"));
				lessonorderVO.setMem_no(rs.getString("mem_no"));
				lessonorderVO.setLes_no(rs.getString("les_no"));
				lessonorderVO.setDs_no(rs.getString("ds_no"));
				lessonorderVO.setMem_name(rs.getString("mem_name"));
				lessonorderVO.setLes_name(rs.getString("les_name"));
				lessonorderVO.setCost(rs.getInt("cost"));
				lessonorderVO.setCoach(rs.getString("coach"));
				lessonorderVO.setLo_state(rs.getString("lo_state"));
				list.add(lessonorderVO);
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

