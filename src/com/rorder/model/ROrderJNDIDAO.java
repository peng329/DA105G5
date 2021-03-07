package com.rorder.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.equip.model.EquipJDBCDAO;
import com.equip.model.EquipVO;
import com.rodetail.model.RoDetailDAO;
import com.rodetail.model.RoDetailJDBCDAO;
import com.rodetail.model.RoDetailVO;

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
	private static final String GET_ALL_DSRO = "SELECT RO_NO,MEM_NO,TEPC,TPRIZ,OP_STATE,TO_CHAR(RS_DATE,'YYYY-MM-DD')RS_DATE,TO_CHAR(RD_DATE,'YYYY-MM-DD')RD_DATE,TO_CHAR(RR_DATE,'YYYY-MM-DD')RR_DATE,FFINE,O_STATE,O_NOTE FROM R_ORDER WHERE DS_NO = ? ORDER BY RO_NO";
	private static final String GET_ALL_MERO = "SELECT RO_NO,DS_NO,TEPC,TPRIZ,OP_STATE,TO_CHAR(RS_DATE,'YYYY-MM-DD')RS_DATE,TO_CHAR(RD_DATE,'YYYY-MM-DD')RD_DATE,TO_CHAR(RR_DATE,'YYYY-MM-DD')RR_DATE,FFINE,O_STATE,O_NOTE FROM R_ORDER WHERE MEM_NO = ? ORDER BY RO_NO";
	private static final String GET_ONE_MERO = "SELECT RO_NO,MEM_NO,DS_NO,TEPC,TPRIZ,OP_STATE,TO_CHAR(RS_DATE,'YYYY-MM-DD')RS_DATE,TO_CHAR(RD_DATE,'YYYY-MM-DD')RD_DATE,TO_CHAR(RR_DATE,'YYYY-MM-DD')RR_DATE,FFINE,O_STATE,O_NOTE FROM R_ORDER WHERE MEM_NO=? AND RO_NO = ?";
	private static final String GET_RR_DATE_IS_NULL = "SELECT R.RO_NO FROM R_ORDER R JOIN RO_DETAIL RO ON R.RO_NO = RO.RO_NO WHERE R.RR_DATE IS NULL";
	private static final String DELETE = "DELETE FROM R_ORDER WHERE RO_NO = ?";
	private static final String UPDATE_By_DS = "UPDATE R_ORDER SET OP_STATE=?,RR_DATE=?,FFINE=?,O_STATE=?,O_NOTE=? WHERE RO_NO = ?";
	private static final String UPDATE_By_MEM = "UPDATE R_ORDER SET OP_STATE='已付款'  WHERE RO_NO = ?";

	@Override
	public void insert(ROrderVO roVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1,roVO.getDs_no());
			pstmt.setString(2,roVO.getMem_no());
			pstmt.setInt(3,roVO.getTepc());
			pstmt.setInt(4,roVO.getTpriz());
			pstmt.setString(5,roVO.getOp_state());
			pstmt.setDate(6,roVO.getRs_date());
			pstmt.setDate(7,roVO.getRd_date());
			pstmt.setString(8,roVO.getO_state());
			pstmt.setString(9,roVO.getO_note());


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
	public String insertWithRoDetail(ROrderVO roVO,List<RoDetailVO> list) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String next_ro_no=null;
		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
			
			String cols[]= {"RO_NO"};
			
			pstmt = con.prepareStatement(INSERT_STMT,cols);
			
			pstmt.setString(1,roVO.getDs_no());
			pstmt.setString(2,roVO.getMem_no());
			pstmt.setInt(3,roVO.getTepc());
			pstmt.setInt(4,roVO.getTpriz());
			pstmt.setString(5,roVO.getOp_state());
			pstmt.setDate(6,roVO.getRs_date());
			pstmt.setDate(7,roVO.getRd_date());
			pstmt.setString(8,roVO.getO_state());
			pstmt.setString(9,roVO.getO_note());
			pstmt.executeUpdate();


			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				next_ro_no = rs.getString(1);
				System.out.println("取得訂單編號");
			}else {
				System.out.println("未取得訂單編號");
			}
			
			rs.close();
			
			RoDetailDAO rodetaildao = new RoDetailDAO();
			for(RoDetailVO rodetailVO : list) {
				rodetailVO.setRo_no(next_ro_no);
				rodetaildao.insertByROrder(rodetailVO,con);
			}

			con.commit();
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					System.err.print("Transaction is being");
					System.err.print("rolled back from ROrder");
					con.rollback();
				} catch (SQLException see) {
					throw new RuntimeException("Rollback error occured" + see.getMessage());
				}
			}
			throw new RuntimeException("A database error occured" + se.getMessage());
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
		return next_ro_no;
	}
	
	@Override
	public void updateByDS(ROrderVO roVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_By_DS);

			pstmt.setString(1,roVO.getOp_state());
			pstmt.setDate(2, roVO.getRr_date());
			pstmt.setInt(3, roVO.getFfine());
			pstmt.setString(4, roVO.getO_state());
			pstmt.setString(5, roVO.getO_note());
			pstmt.setString(6,roVO.getRo_no());

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
	public void updateByMEM(String ro_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_By_MEM);
		
			pstmt.setString(1,ro_no);
			
			pstmt.executeUpdate();
			
			// Handle any driver errors
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
	}
	
	@Override
	public void delete(String ro_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1,ro_no);

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
	public Set<String> getRR_DATE_IS_NULL() {
		Set<String> set = new HashSet<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String ro_no=null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RR_DATE_IS_NULL);
			rs = pstmt.executeQuery();			
			
			while (rs.next()) {
				ro_no=rs.getString("ro_no");
				set.add(ro_no); // Store the row in the list
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
		return set;
	}
	
}