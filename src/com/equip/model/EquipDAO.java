package com.equip.model;

import java.util.*;
import java.io.InputStream;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.eqpic.model.EqpicDAO;
import com.eqpic.model.EqpicVO;
import com.toolbox.CompositeQuery_Equip;

public class EquipDAO implements EquipDAO_interface {

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

	private static final String INSERT_STMT = "INSERT INTO EQUIP (EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE) VALUES (('EP'||LPAD(TO_CHAR(EP_SEQ.NEXTVAL),4,'0')),?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ONE_STMT = "SELECT EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE FROM EQUIP WHERE EP_SEQ = ?";
	private static final String GET_ONE_DENO = "SELECT EPC_NO,EP_NO,EP_NAME,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE FROM EQUIP WHERE DS_NO=? AND EP_NO=?";
	private static final String GET_ALL_DSEQ = "SELECT EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE FROM EQUIP WHERE DS_NO = ?ORDER BY EP_SEQ";
	private static final String GET_ALL_DSEQ_FOR_SHOP = "SELECT EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE FROM EQUIP WHERE DS_NO = ? AND EP_STATE='良好' AND EPS_STATE='上架' ORDER BY EP_SEQ";
	private static final String GET_ALL_STMT = "SELECT EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE FROM EQUIP ORDER BY DS_NO,EP_NAME";
	private static final String GET_ALL_STMT2 = "SELECT DS_NO,EP_NAME,EP_SIZE,COUNT(*) AS COUNT FROM EQUIP WHERE EPR_STATE='在店' GROUP BY DS_NO,EP_NAME,EP_SIZE ORDER BY DS_NO";
	private static final String GET_ALL_STMT3 = "SELECT EP_SEQ FROM EQUIP WHERE DS_NO=? AND EP_NAME=? AND EP_SIZE=?";
	//	private static final String GET_ALL_STMT = "select ds_no,ep_name,ep_size,count(*) as count from EQUIP group by ds_no,ep_name,ep_size ORDER BY ds_no";
	private static final String DELETE = "DELETE FROM EQUIP WHERE EP_SEQ = ?";
	private static final String UPDATE = "UPDATE EQUIP SET EPC_NO=?,DS_NO=?,DS_NAME=?,EP_NAME=?,EP_NO=?,EP_SIZE=?,EP_PRIZ=?,EP_RP=?,EP_STATE=?,EPR_STATE=?,EPS_STATE=? WHERE EP_SEQ = ?";
	private static final String UPDATE_BY_ORDER = "UPDATE EQUIP SET EPR_STATE='待取',EPS_STATE='下架' WHERE EP_SEQ = ?";
	private static final String UPDATE_BY_RETURN = "UPDATE EQUIP SET EP_STATE='待查驗',EPR_STATE='在店',EPS_STATE='下架' WHERE EP_SEQ = ?";

	@Override
	public void insertWithEpic(EquipVO equipVO, List<EqpicVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();

			con.setAutoCommit(false);

			String cols[] = {"EP_SEQ","DS_NO"};
			// 僅綁一個值可無須使用[]
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			pstmt.setString(1, equipVO.getEpc_no());
			pstmt.setString(2, equipVO.getDs_no());
			pstmt.setString(3, equipVO.getDs_name());
			pstmt.setString(4, equipVO.getEp_name());
			pstmt.setString(5, equipVO.getEp_no());
			pstmt.setString(6, equipVO.getEp_size());
			pstmt.setInt(7, equipVO.getEp_priz());
			pstmt.setInt(8, equipVO.getEp_rp());
			pstmt.setString(9, equipVO.getEp_state());
			pstmt.setString(10, equipVO.getEpr_state());
			pstmt.setString(11, equipVO.getEps_state());

			pstmt.executeUpdate();

			String next_ep_seq = null;
			String next_ds_no =equipVO.getDs_no();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_ep_seq = rs.getString(1);
				//next_ds_no=rs.getString(2);
				//由於ds_no並非sequence，因此並不需要藉由rs.getString取得資料
				System.out.println("取得裝備流水號");
			} else {
				System.out.println("未取得裝備流水號");
			}
			rs.close();

			System.out.println("list.size()="+list.size());
			EqpicDAO dao = new EqpicDAO();
			for (EqpicVO aEpic : list) {
				aEpic.setDs_no(next_ds_no);
				aEpic.setEp_seq(next_ep_seq);			
				dao.insertByEquip(aEpic, con);
			}

			con.commit();
			con.setAutoCommit(true);
		}catch (SQLException se) {
			if (con != null) {
				try {
					System.err.print("Transaction is being");
					System.err.print("rolled back from Equip");
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
	}

	@Override
	public void update(EquipVO equipVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, equipVO.getEpc_no());
			pstmt.setString(2, equipVO.getDs_no());
			pstmt.setString(3, equipVO.getDs_name());
			pstmt.setString(4, equipVO.getEp_name());
			pstmt.setString(5, equipVO.getEp_no());
			pstmt.setString(6, equipVO.getEp_size());
			pstmt.setInt(7, equipVO.getEp_priz());
			pstmt.setInt(8, equipVO.getEp_rp());
			pstmt.setString(9, equipVO.getEp_state());
			pstmt.setString(10, equipVO.getEpr_state());
			pstmt.setString(11, equipVO.getEps_state());
			pstmt.setString(12, equipVO.getEp_seq());

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
	public void updateByOrder(String ep_seq) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_BY_ORDER);
			
			pstmt.setString(1, ep_seq);
			
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
	public void updateByReturn(String ep_seq) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_BY_RETURN);
			
			pstmt.setString(1, ep_seq);
			
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
	public void delete(String ep_seq) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ep_seq);

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
	public EquipVO findByPrimaryKey(String ep_seq) {

		EquipVO equipVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ep_seq);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// epcVO 也稱為 Domain objects
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
		return equipVO;
	}

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
	public List<EquipVO> getDSAll(String ds_no) {
		List<EquipVO> list = new ArrayList<EquipVO>();
		EquipVO equipVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_DSEQ);
			
			pstmt.setString(1,ds_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// EquipVO 也稱為 Domain objects
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
				list.add(equipVO); // Store the row in the list
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
	
	public List<EquipVO> getDSAllFORSHOP(String ds_no) {
		List<EquipVO> list = new ArrayList<EquipVO>();
		EquipVO equipVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_DSEQ_FOR_SHOP);
			
			pstmt.setString(1,ds_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// EquipVO 也稱為 Domain objects
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
				list.add(equipVO); // Store the row in the list
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
	
	
//	@Override
//	public List<EquipVO> getDSECAll(String ds_no, String epc_no) {
//		List<EquipVO> list = new ArrayList<EquipVO>();
//		EquipVO equipVO = null;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			con = ds.getConnection();		
//			pstmt = con.prepareStatement(GET_ALL_DECNO);
//
//			pstmt.setString(1, ds_no);
//			pstmt.setString(2, epc_no);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				// EquipVO 也稱為 Domain objects
//				equipVO = new EquipVO();
//				equipVO.setEp_seq(rs.getString("ep_seq"));
//				equipVO.setEp_name(rs.getString("ep_name"));
//				equipVO.setEp_no(rs.getString("ep_no"));
//				equipVO.setEp_size(rs.getString("ep_size"));
//				equipVO.setEp_priz(rs.getInt("ep_priz"));
//				equipVO.setEp_rp(rs.getInt("ep_rp"));
//				equipVO.setEp_state(rs.getString("ep_state"));
//				equipVO.setEpr_state(rs.getString("epr_state"));
//				equipVO.setEps_state(rs.getString("eps_state"));
//				list.add(equipVO); // Store the row in the list
//			}
//			// Handle any driver errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return list;
//	}

	@Override
	public List<EquipVO> getAll() {
		List<EquipVO> list = new ArrayList<EquipVO>();
		EquipVO equipVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// epcVO 也稱為 Domain objects
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

				list.add(equipVO); // Store the row in the list
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
	public List<EquipVO> getAll(Map<String,String[]> map) {
		List<EquipVO> list = new ArrayList<EquipVO>();
		EquipVO equipVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			String finalSQL="select * from equip "+CompositeQuery_Equip.get_whereCondition(map)+" order by ep_seq";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("finalSQL = " + finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// epcVO 也稱為 Domain objects
				equipVO = new EquipVO();
				equipVO.setEpc_no(rs.getString("epc_no"));
				equipVO.setDs_name(rs.getString("ds_name"));
				equipVO.setEp_name(rs.getString("ep_name"));
				equipVO.setEp_rp(rs.getInt("ep_rp"));
				list.add(equipVO); // Store the row in the list
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
	public List<String> getAllGroupBy() {
		List<String> list = new ArrayList<String>();
		EquipVO equipVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT2);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getString("count")); // Store the row in the list
			}

			// Handle any driver errors
		}  catch (SQLException se) {
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
	public List<String> getAllByDA_NO$EP_NAME(String ds_no,String ep_name,String ep_size) {
		List<String> list = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT3);

			pstmt.setString(1, ds_no);
			pstmt.setString(2, ep_name);
			pstmt.setString(3, ep_size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getString("EP_SEQ")); // Store the row in the list
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

		return list;
	}
	
}