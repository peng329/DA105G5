package android.lespic.model;

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

	private static final String GET_ALL_LESPIC = "SELECT * FROM LESPIC";
	private static final String GET_ONE_LESPIC = "SELECT * FROM LESPIC WHERE LPIC_SEQ = ?";
	private static final String GET_BY_LESNO = "SELECT * FROM LESPIC WHERE LES_NO = ?";
    private static final String GET_LPIC_BY_LESNO ="SELECT LPIC FROM LESPIC WHERE LES_NO = ?";
	
	

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
		List<LespicVO> lespicList = new ArrayList<LespicVO>();
		LespicVO lespicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_LESNO);
			pstmt.setString(1, les_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				lespicVO = new LespicVO();
				lespicVO.setLpic_seq(rs.getString("lpic_seq"));
				lespicVO.setLes_no(rs.getString("les_no"));
				lespicList.add(lespicVO);
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
		return lespicList;
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
			
			while(rs.next()) {
				lespicVO = new LespicVO();
				lespicVO.setLpic_seq(rs.getString("lpic_seq"));
				lespicVO.setLes_no(rs.getString("les_no"));
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
	public byte[] getLespic(String les_no) {
		byte[] lespic = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try {
            con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LPIC_BY_LESNO);
			pstmt.setString(1, les_no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				lespic = rs.getBytes("lpic");
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
		return lespic;
	}


}

