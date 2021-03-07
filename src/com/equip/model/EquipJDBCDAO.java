package com.equip.model;

import java.util.*;

import com.eqpic.model.EqpicVO;
import com.toolbox.CompositeQuery_Equip;
import com.eqpic.model.EqpicJDBCDAO;

import java.io.*;
import java.sql.*;

public class EquipJDBCDAO implements EquipDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String passwd = "DA105G5";

	private static final String INSERT_STMT = "INSERT INTO EQUIP (EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE) VALUES (('EP'||LPAD(TO_CHAR(EP_SEQ.NEXTVAL),4,'0')),?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ONE_STMT = "SELECT EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE FROM EQUIP WHERE EP_SEQ = ?";
	private static final String GET_ONE_DENO = "SELECT EPC_NO,EP_NO,EP_NAME,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE FROM EQUIP WHERE DS_NO=? AND EP_NO=?";
	private static final String GET_ALL_DSEQ = "SELECT EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE FROM EQUIP WHERE DS_NO = ? ORDER BY EP_SEQ";
	private static final String GET_ALL_DSEQ_FOR_SHOP = "SELECT EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE FROM EQUIP WHERE DS_NO = ? AND EPR_STATE='在店' AND EPS_STATE='上架' ORDER BY EP_SEQ";
	private static final String GET_ALL_STMT = "SELECT EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE FROM EQUIP ORDER BY EP_SEQ";
	private static final String GET_ALL_STMT2 ="SELECT DS_NO,EP_NAME,EP_SIZE,COUNT(*) AS COUNT FROM EQUIP WHERE EPR_STATE='在店' GROUP BY DS_NO,EP_NAME,EP_SIZE ORDER BY DS_NO";
	private static final String GET_ALL_STMT3 = "SELECT EP_SEQ FROM EQUIP WHERE DS_NO=? AND EP_NAME=? AND EP_SIZE=?";
	private static final String DELETE = "DELETE FROM EQUIP WHERE EP_SEQ = ?";
	private static final String UPDATE = "UPDATE EQUIP SET EPC_NO=?,DS_NO=?,DS_NAME=?,EP_NAME=?,EP_NO=?,EP_SIZE=?,EP_PRIZ=?,EP_RP=?,EP_STATE=?,EPR_STATE=?,EPS_STATE=? WHERE EP_SEQ = ?";
	private static final String UPDATE_BY_ORDER = "UPDATE EQUIP SET EPR_STATE='待取'  WHERE EP_SEQ = ?";
	private static final String UPDATE_BY_RETURN = "UPDATE EQUIP SET EP_STATE='待查驗',EPR_STATE='在店',EPS_STATE='下架' WHERE EP_SEQ = ?";
	
	@Override
	public void insertWithEpic(EquipVO equipVO, List<EqpicVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

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
			String next_ds_no =null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_ep_seq = rs.getString(1);
				next_ds_no=rs.getString(2);
				//此處的1和2是指鍵入的次序，看起來應該是要和String中的cols排序一致
				System.out.println("新增成功");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();

			EqpicJDBCDAO dao = new EqpicJDBCDAO();
			for (EqpicVO aEpic : list) {
				aEpic.setDs_no(next_ds_no);
				aEpic.setEp_seq(next_ep_seq);
				dao.insertByEquip(aEpic, con);
			}

			con.commit();
			con.setAutoCommit(true);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver" + e.getMessage());
		} catch (SQLException se) {
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public void updateByOrder(String ep_seq) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_BY_ORDER);
			
			pstmt.setString(1, ep_seq);
			
			pstmt.executeUpdate();
			
			// Handle any driver errors
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public void updateByReturn(String ep_seq) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_BY_RETURN);
			
			pstmt.setString(1, ep_seq);
			
			pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public void delete(String ep_seq) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ep_seq);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public EquipVO findByPrimaryKey(String ep_seq) {

		EquipVO equipVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ep_seq);

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
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
		return equipVO;
	}

	@Override
	public EquipVO findByDsEpNo(String ds_no, String ep_no) {
		EquipVO equipVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could't load database driver." + e.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

	public List<EquipVO> getDSAllFORSHOP(String ds_no) {
		List<EquipVO> list = new ArrayList<EquipVO>();
		EquipVO equipVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	
	@Override
	public List<EquipVO> getAll() {
		List<EquipVO> list = new ArrayList<EquipVO>();
		EquipVO equipVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver" + e.getMessage());
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
	public List<String> getAllGroupBy() {
		List<String> list = new ArrayList<String>();
		EquipVO equipVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT2);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getString("count")); // Store the row in the list
			}

			// Handle any driver errors
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver" + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT3);

			pstmt.setString(1, ds_no);
			pstmt.setString(2, ep_name);
			pstmt.setString(3, ep_size);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getString("EP_SEQ")); // Store the row in the list
			}


		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could't load database driver." + e.getMessage());
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

	@Override
	public List<EquipVO> getAll(Map<String,String[]> map) {
		List<EquipVO> list = new ArrayList<EquipVO>();
		EquipVO equipVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver" + e.getMessage());
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


	public static void main(String[] args) {

		EquipJDBCDAO dao = new EquipJDBCDAO();

		// 新增
//		EquipVO epVO1 = new EquipVO();
//		epVO1.setEpc_no("EQB");
//		epVO1.setDs_no("DS0002");
//		epVO1.setDs_name("潛水立方");
//		epVO1.setEp_name("D定位轉盤");
//		epVO1.setEp_no("BD0005");
//		epVO1.setEp_size("F");
//		epVO1.setEp_priz(new Integer(10000));
//		epVO1.setEp_rp(new Integer(1200));
//		epVO1.setEp_state("良好");
//		epVO1.setEpr_state("出租");
//		epVO1.setEps_state("上架");
//		dao.insert(epVO1);

		// 修改
//		EquipVO epVO2 = new EquipVO();
//		epVO2.setEp_seq("EP0041");
//		epVO2.setEpc_no("EQB");
//		epVO2.setDs_no("DS0001");
//		epVO2.setDs_name("潛水貨艙");
//		epVO2.setEp_name("D定位轉盤");
//		epVO2.setEp_no("BD0005");
//		epVO2.setEp_size("F");
//		epVO2.setEp_priz(new Integer(20000));
//		epVO2.setEp_rp(new Integer(2000));
//		epVO2.setEp_state("良好");
//		epVO2.setEpr_state("出租");
//		epVO2.setEps_state("上架");
//		dao.update(epVO2);
		
		// 修改(由訂單)
		dao.updateByOrder("EP0002");

		// 查詢單一(PK)
//		EquipVO epVO3 = dao.findByPrimaryKey("EP0001");
//		System.out.print(epVO3.getEpc_no() + ",");
//		System.out.print(epVO3.getDs_no() + ",");
//		System.out.print(epVO3.getDs_name() + ",");
//		System.out.print(epVO3.getEp_name() + ",");
//		System.out.print(epVO3.getEp_no() + ",");
//		System.out.print(epVO3.getEp_size() + ",");
//		System.out.print(epVO3.getEp_priz() + ",");
//		System.out.print(epVO3.getEp_rp() + ",");
//		System.out.print(epVO3.getEp_state() + ",");
//		System.out.print(epVO3.getEpr_state() + ",");
//		System.out.print(epVO3.getEps_state() + ",");
//		System.out.println("---------------------");

////		// 查詢全
//		List<EquipVO> list1 = dao.getAll();
//		for (EquipVO aEp : list1) {
//			System.out.print(aEp.getEp_seq() + ",");
//			System.out.print(aEp.getEpc_no() + ",");
//			System.out.print(aEp.getDs_no() + ",");
//			System.out.print(aEp.getDs_name() + ",");
//			System.out.print(aEp.getEp_name() + ",");
//			System.out.print(aEp.getEp_no() + ",");
//			System.out.print(aEp.getEp_size() + ",");
//			System.out.print(aEp.getEp_priz() + ",");
//			System.out.print(aEp.getEp_rp() + ",");
//			System.out.print(aEp.getEp_state() + ",");
//			System.out.print(aEp.getEpr_state() + ",");
//			System.out.print(aEp.getEps_state() + ",");
//			System.out.println();
//		}

		List<String> list2 = dao.getAllGroupBy();
		for (String count : list2) {
			System.out.print(count + ",");
			System.out.println();
		}
		
//		List<String> list3 = dao.getAllByDA_NO$EP_NAME("DS0003","B潛水鞋","L");
//		for (String ep_seq : list3) {
//			System.out.print(ep_seq + ",");
//			System.out.println();
//		}
		
//		// 查詢特定分類之潛店庫存狀態
//		List<EquipVO> lis2t = dao.getDSECAll("DS0001","EQSGS");
//		for (EquipVO aEp : list2) {
//			System.out.print(aEp.getEp_no() + ",");
//			System.out.print(aEp.getEp_name() + ",");
//			System.out.print(aEp.getEp_size() + ",");
//			System.out.print(aEp.getEp_priz() + ",");
//			System.out.print(aEp.getEp_rp() + ",");
//			System.out.print(aEp.getEp_state() + ",");
//			System.out.print(aEp.getEpr_state() + ",");
//			System.out.print(aEp.getEps_state() + ",");
//			System.out.println();
//		}
		
		// 查詢特定分類之潛店庫存狀態
//		List<EquipVO> list3 = dao.getDSAll("DS0004");
//		for (EquipVO aEp : list3) {
//			System.out.print(aEp.getEp_seq() + ",");
//			System.out.print(aEp.getEpc_no() + ",");
//			System.out.print(aEp.getDs_no() + ",");
//			System.out.print(aEp.getDs_name() + ",");
//			System.out.print(aEp.getEp_name() + ",");
//			System.out.print(aEp.getEp_no() + ",");
//			System.out.print(aEp.getEp_size() + ",");
//			System.out.print(aEp.getEp_priz() + ",");
//			System.out.print(aEp.getEp_rp() + ",");
//			System.out.print(aEp.getEp_state() + ",");
//			System.out.print(aEp.getEpr_state() + ",");
//			System.out.print(aEp.getEps_state() + ",");
//			System.out.println();
//		}
		
		

		//自增主鍵測試
		
//		EquipVO epVO4 = new EquipVO();
//		epVO4.setEpc_no("EQAH");
//		epVO4.setDs_no("DS0002");
//		epVO4.setDs_name("潛立方");
//		epVO4.setEp_name("D定位轉盤");
//		epVO4.setEp_no("BD0008");
//		epVO4.setEp_size("F");
//		epVO4.setEp_priz(new Integer(10000));
//		epVO4.setEp_rp(new Integer(1200));
//		epVO4.setEp_state("良好");
//		epVO4.setEpr_state("出租");
//		epVO4.setEps_state("上架");
//		
//		List<EqpicVO> testlist =new ArrayList<EqpicVO>();
//		EqpicVO epic1 = new EqpicVO();
//		byte[] b=null;
//		try {
//		FileInputStream in = new FileInputStream("C:\\11.jpg");
//		b = new byte[in.available()];
//		in.read(b);
//		in.close();
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//		epic1.setEpic(b);
//		
//		EqpicVO epic2 = new EqpicVO();
//		byte[] b1=null;
//		try {
//		FileInputStream in1 = new FileInputStream("C:\\2.jpg");
//		b1 = new byte[in1.available()];
//		in1.read(b1);
//		in1.close();
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//		epic2.setEpic(b1);
//		
//		testlist.add(epic1);
//		testlist.add(epic2);
//		
//		dao.insertWithEpic(epVO4,testlist);
	}
}