package android.eqpic.model;

import java.util.*;
import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EqpicJNDIDAO implements EqpicDAO_interface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();/* p139 */
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DA105G5");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO EQPIC (EPIC_SEQ,DS_NO,EP_SEQ,EPIC) VALUES (('EPP'||LPAD(TO_CHAR(EPIC_SEQ.NEXTVAL),3,'0')),?,?,?)";
	private static final String GET_DS_ALL = "SELECT * FROM EQPIC WHERE DS_NO = ?";
	private static final String GET_EP_ALL = "SELECT * FROM EQPIC WHERE EP_SEQ=?";
	private static final String GET_ONE_EPIC = "SELECT * FROM EQPIC WHERE EPIC_SEQ = ?";
	private static final String GET_PIC_BY_FK = "SELECT EPIC FROM EQPIC WHERE DS_NO = ? AND EP_SEQ=? ";
	private static final String DELETE = "DELETE FROM EQPIC WHERE EPIC_SEQ = ?";
	private static final String UPDATE = "UPDATE EQPIC set EP_SEQ=?,EPIC=? WHERE EPIC_SEQ =?";

	

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
				// empVo 也稱為 Domain objects
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
	
	public byte[] getEqpic(String ds_no,String ep_seq) {
		byte[] eqpic = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con= ds.getConnection();
			pstmt = con.prepareStatement(GET_PIC_BY_FK);
			
			pstmt.setString(1, ds_no);
			pstmt.setString(2, ep_seq);
			rs= pstmt.executeQuery();
			
			if(rs.next()) {
				eqpic = rs.getBytes(1);
			}
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
		return eqpic;
	}
	

}
