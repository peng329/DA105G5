package android.rorder.model;



import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ROrderJNDIDAO implements ROrderDAO_interface {

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

	private static final String INSERT_STMT = "INSERT INTO R_ORDER (RO_NO,DS_NO,MEM_NO,TEPC,TPRIZ,OP_STATE,RS_DATE,RD_DATE,O_STATE,O_NOTE) VALUES (('O'||TO_CHAR(SYSDATE,'YYYYMMDD')||'-'||LPAD(TO_CHAR(RO_SEQ.NEXTVAL),3,'0')),?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_DSRO = "SELECT * FROM R_ORDER WHERE DS_NO = ? ORDER BY RO_NO DESC";
	private static final String GET_ALL_MERO = "SELECT * FROM R_ORDER WHERE MEM_NO = ? ORDER BY RO_NO DESC";
	private static final String GET_RO_BY_PK = "SELECT * FROM R_ORDER WHERE RO_NO = ?";
	private static final String GET_ONE_MERO = "SELECT * FROM R_ORDER WHERE MEM_NO=? AND RO_NO = ?";
	private static final String DELETE = "DELETE FROM R_ORDER WHERE RO_NO = ?";
	private static final String UPDATE = "UPDATE R_ORDER SET TEPC=?,TPRIZ=?,OP_STATE=?,RS_DATE=?,RD_DATE=?,RR_DATE=?,FFINE=?,O_STATE=?,O_NOTE=? WHERE RO_NO = ?";

	
	@Override
	public boolean delete(String ro_no) {

		Connection con = null;
		PreparedStatement pstmt = null;
        boolean isDeleted = false; 
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1,ro_no);

			int count = pstmt.executeUpdate();
			isDeleted = count > 0 ? true : false;
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
        return isDeleted;
	}
	@Override
	public ROrderVO findOneRo(String ro_no) {
        ROrderVO roVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RO_BY_PK);
			pstmt.setString(1,ro_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				roVO = new ROrderVO();
				roVO.setRo_no(rs.getString("ro_no"));
				roVO.setMem_no(rs.getString("mem_no"));
				roVO.setDs_no(rs.getString("ds_no"));
				roVO.setTepc(rs.getInt("tepc"));
				roVO.setTpriz(rs.getInt("tpriz"));
				roVO.setOp_state(rs.getString("op_state"));
				roVO.setRs_date(rs.getDate("rs_date"));
				roVO.setRd_date(rs.getDate("rd_date"));
				roVO.setRr_date(rs.getDate("rr_date"));
				roVO.setFfine(rs.getInt("ffine"));
				roVO.setO_state(rs.getString("o_state"));
				roVO.setO_note(rs.getString("o_note"));
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
		return roVO;
	}
	@Override
	public ROrderVO findAMemRo(String mem_no,String ro_no) {

		ROrderVO roVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_MERO);

			pstmt.setString(1,mem_no);
			pstmt.setString(2,ro_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				roVO = new ROrderVO();
				roVO.setMem_no(rs.getString("mem_no"));
				roVO.setRo_no(rs.getString("ro_no"));
				roVO.setDs_no(rs.getString("ds_no"));
				roVO.setTepc(rs.getInt("tepc"));
				roVO.setTpriz(rs.getInt("tpriz"));
				roVO.setOp_state(rs.getString("op_state"));
				roVO.setRs_date(rs.getDate("rs_date"));
				roVO.setRd_date(rs.getDate("rd_date"));
				roVO.setRr_date(rs.getDate("rr_date"));
				roVO.setFfine(rs.getInt("ffine"));
				roVO.setO_state(rs.getString("o_state"));
				roVO.setO_note(rs.getString("o_note"));

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
		return roVO;
	}

	@Override
	public List<ROrderVO> getAllRoByAMem(String mem_no) {
		List<ROrderVO> list = new ArrayList<ROrderVO>();
		ROrderVO roVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_MERO);
			
			pstmt.setString(1,mem_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// roVO 也稱為 Domain objects
				roVO = new ROrderVO();
				roVO.setRo_no(rs.getString("ro_no"));
				roVO.setDs_no(rs.getString("ds_no"));
				roVO.setMem_no(rs.getString("mem_no"));
				roVO.setTepc(rs.getInt("tepc"));
				roVO.setTpriz(rs.getInt("tpriz"));
				roVO.setOp_state(rs.getString("op_state"));
				roVO.setRs_date(rs.getDate("rs_date"));
				roVO.setRd_date(rs.getDate("rd_date"));
				roVO.setRr_date(rs.getDate("rr_date"));
				roVO.setFfine(rs.getInt("ffine"));
				roVO.setO_state(rs.getString("o_state"));
				roVO.setO_note(rs.getString("o_note"));

				list.add(roVO); // Store the row in the list
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
	public List<ROrderVO> getAllDsRo(String ds_no) {
		List<ROrderVO> list = new ArrayList<ROrderVO>();
		ROrderVO roVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_DSRO);
			
			pstmt.setString(1,ds_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				roVO = new ROrderVO();
				roVO = new ROrderVO();
				roVO.setRo_no(rs.getString("ro_no"));
				roVO.setDs_no(rs.getString("ds_no"));
				roVO.setMem_no(rs.getString("mem_no"));
				roVO.setTepc(rs.getInt("tepc"));
				roVO.setTpriz(rs.getInt("tpriz"));
				roVO.setOp_state(rs.getString("op_state"));
				roVO.setRs_date(rs.getDate("rs_date"));
				roVO.setRd_date(rs.getDate("rd_date"));
				roVO.setRr_date(rs.getDate("rr_date"));
				roVO.setFfine(rs.getInt("ffine"));
				roVO.setO_state(rs.getString("o_state"));
				roVO.setO_note(rs.getString("o_note"));

				list.add(roVO); // Store the row in the list
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
	public boolean update(ROrderVO rorderVO) {
        Connection con = null;
		PreparedStatement pstmt = null;
		boolean isUpdate = false;
		try {
            con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1,rorderVO.getTepc());
			pstmt.setInt(2,rorderVO.getTpriz());
			pstmt.setString(3,rorderVO.getOp_state());
			pstmt.setDate(4,rorderVO.getRs_date());
			pstmt.setDate(5, rorderVO.getRd_date());
			pstmt.setDate(6, rorderVO.getRr_date());
			pstmt.setInt(7, rorderVO.getFfine());
			pstmt.setString(8, rorderVO.getO_state());
			pstmt.setString(9, rorderVO.getO_note());
			pstmt.setString(10,rorderVO.getRo_no());
			pstmt.executeUpdate();

			int count = pstmt.executeUpdate();
			isUpdate = count > 0 ? true : false;
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
		return isUpdate;
    }
}