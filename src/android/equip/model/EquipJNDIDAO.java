package android.equip.model;
import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class EquipJNDIDAO implements EquipDAO_interface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();/*p139*/
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DA105G5");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO EQUIP (EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE) VALUES (('EP'||LPAD(TO_CHAR(EP_SEQ.NEXTVAL),4,'0')),?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ONE_STMT = "SELECT EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE FROM EQUIP WHERE EP_SEQ = ?";
	private static final String GET_ONE_DENO = "SELECT EPC_NO,EP_NO,EP_NAME,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE FROM EQUIP WHERE DS_NO=? AND EP_NO=?";
	private static final String GET_ALL_DSEQ = "SELECT EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE FROM EQUIP WHERE DS_NO = ? ORDER BY EP_SEQ";
	private static final String GET_ALL_STMT = "SELECT EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE FROM EQUIP ORDER BY EP_SEQ";
	private static final String GET_ALL_STMT2 = "select ds_no,ep_name,ep_size,count(*) as count from EQUIP group by ds_no,ep_name,ep_size ORDER BY ds_no";
	private static final String GET_ALL_STMT3 = "SELECT EP_SEQ FROM EQUIP WHERE DS_NO=? AND EP_NAME=? AND EP_SIZE=?";
	private static final String DELETE = "DELETE FROM EQUIP WHERE EP_SEQ = ?";
	private static final String UPDATE = "UPDATE EQUIP SET EPC_NO=?,DS_NO=?,DS_NAME=?,EP_NAME=?,EP_NO=?,EP_SIZE=?,EP_PRIZ=?,EP_RP=?,EP_STATE=?,EPR_STATE=?,EPS_STATE=? WHERE EP_SEQ = ?";

	
	@Override
	public EquipVO findByDsEpNo(String ds_no, String ep_no) {
		EquipVO equipVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_DENO);

			pstmt.setString(1, ds_no);
			pstmt.setString(2, ep_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				equipVO = new EquipVO();
				equipVO.setEpc_no(rs.getString("epc_no"));
				equipVO.setEp_no(rs.getString("ep_no"));
				equipVO.setEp_name(rs.getString("ep_name"));
				equipVO.setEp_size(rs.getString("ep_size"));
				equipVO.setEp_priz(rs.getInt("ep_priz"));
				equipVO.setEp_rp(rs.getInt("ep_rp"));
				equipVO.setEp_state(rs.getString("ep_state"));
				equipVO.setEpr_state(rs.getString("epr_state"));
				equipVO.setEps_state(rs.getString("eps_state"));

			}

		} catch (SQLException se) {
			throw new RuntimeException("Could't load database driver." + se.getMessage());
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

		return equipVO;
	}
	
	@Override
	public EquipVO findByPrimaryKey(String ep_seq) {

		EquipVO equipVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1,ep_seq);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				equipVO = new EquipVO();
				equipVO.setEp_seq(rs.getString("ep_seq"));
				equipVO.setEpc_no(rs.getString("epc_no"));
				equipVO.setDs_no(rs.getString("ds_no"));
				equipVO.setDs_name(rs.getString("ds_name"));
				equipVO.setEp_name(rs.getString("ep_name"));
				equipVO.setEp_no(rs.getString("ep_no"));
				equipVO.setEp_size(rs.getString("ep_size"));
				equipVO.setEp_priz(rs.getInt("ep_priz"));
				equipVO.setEp_rp(rs.getInt("ep_rp"));
				equipVO.setEp_state(rs.getString("ep_state"));
				equipVO.setEpr_state(rs.getString("epr_state"));
				equipVO.setEps_state(rs.getString("eps_state"));

			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return equipVO;
	}

	
	
	
}