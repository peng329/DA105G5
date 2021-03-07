package com.rorder.model;

import java.util.*;

import com.eqpic.model.EqpicVO;
import com.equip.model.EquipJDBCDAO;
import com.equip.model.EquipVO;
import com.rodetail.model.RoDetailJDBCDAO;
import com.rodetail.model.RoDetailDAO;
import com.rodetail.model.RoDetailVO;

import java.sql.*;
import java.time.LocalDate;  

public class ROrderJDBCDAO implements ROrderDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String passwd = "DA105G5";

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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	public String insertWithRoDetail(ROrderVO roVO,List<RoDetailVO> list) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String next_ro_no=null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
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
			
			RoDetailJDBCDAO rodetaildao = new RoDetailJDBCDAO();
			System.out.println(list.size());
			for(RoDetailVO rodetailVO : list) {
				rodetailVO.setRo_no(next_ro_no);
				rodetaildao.insertByROrder(rodetailVO,con);
			}
			con.commit();
			// Handle any SQL errors
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException see) {
			if(con!=null) {
				try {
					System.err.print(" Transaction is being ");
					System.err.print(" rolled back from ROrder ");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_By_DS);

			pstmt.setString(1,roVO.getOp_state());
			pstmt.setDate(2, roVO.getRr_date());
			pstmt.setInt(3, roVO.getFfine());
			pstmt.setString(4, roVO.getO_state());
			pstmt.setString(5, roVO.getO_note());
			pstmt.setString(6,roVO.getRo_no());

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
	public void updateByMEM(String ro_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_By_MEM);
		
			pstmt.setString(1,ro_no);
			
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
	public void delete(String ro_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1,ro_no);

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
	public ROrderVO findAMemRo(String mem_no,String ro_no) {

		ROrderVO roVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_MERO);
			pstmt.setString(1,mem_no);
			pstmt.setString(2,ro_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// roVO 也稱為 Domain objects
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	
	@Override
	public List<ROrderVO> getAllDsRo(String ds_no) {
		List<ROrderVO> list = new ArrayList<ROrderVO>();
		ROrderVO roVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_DSRO);
			
			pstmt.setString(1,ds_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// roVO 也稱為 Domain objects
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
	
	@Override
	public Set<String> getRR_DATE_IS_NULL() {
		Set<String> set = new HashSet<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String ro_no=null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_RR_DATE_IS_NULL);
			rs = pstmt.executeQuery();			
			
			while (rs.next()) {
				ro_no=rs.getString("ro_no");
				set.add(ro_no); // Store the row in the list
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
		return set;
	}

	public static void main(String[] args) {

		ROrderJDBCDAO dao = new ROrderJDBCDAO();

		// 新增
//		ROrderVO roVO1 = new ROrderVO();
//		roVO1.setDs_no("DS0001");
//		roVO1.setMem_no("M000002");
//		roVO1.setTepc(new Integer(5));
//		roVO1.setTpriz(new Integer(3500));
//		roVO1.setOp_state("已付款");
//		roVO1.setRs_date(java.sql.Date.valueOf(LocalDate.now()));
//		roVO1.setRd_date(java.sql.Date.valueOf("2020-02-05"));
//		roVO1.setO_state("未歸還");
//		roVO1.setO_note("");
//		dao.insert(roVO1);

		// 自增主鍵值測試
		
//		EquipJDBCDAO equipdao = new EquipJDBCDAO();
//		
//		ROrderVO roVO1 = new ROrderVO();
//		roVO1.setDs_no("DS0003");
//		roVO1.setMem_no("M000001");
//		roVO1.setTpriz(equipdao.findByPrimaryKey("EP0077").getEp_rp()+equipdao.findByPrimaryKey("EP0087").getEp_rp()+equipdao.findByPrimaryKey("EP0099").getEp_rp());
//		roVO1.setTepc(new Integer(3));
//		roVO1.setOp_state("已付款");
//		roVO1.setRs_date(java.sql.Date.valueOf(LocalDate.now()));
//		roVO1.setRd_date(java.sql.Date.valueOf("2020-02-05"));
//		roVO1.setO_state("未歸還");
//		roVO1.setO_note("");
//		
//		String ep_seq=equipdao.findByPrimaryKey("EP0077").getEp_seq();
//		Integer ep_rp= equipdao.findByPrimaryKey("EP0077").getEp_rp();
//		
//		String ep_seq1=equipdao.findByPrimaryKey("EP0087").getEp_seq();
//		Integer ep_rp1= equipdao.findByPrimaryKey("EP0087").getEp_rp();
//		
//		String ep_seq2=equipdao.findByPrimaryKey("EP0099").getEp_seq();
//		Integer ep_rp2= equipdao.findByPrimaryKey("EP0099").getEp_rp();
//		
//		List<RoDetailVO> list =new ArrayList<RoDetailVO>();
//		RoDetailVO rdVO=new RoDetailVO();
//		RoDetailVO rdVO1=new RoDetailVO();
//		RoDetailVO rdVO2=new RoDetailVO();
//		
//		rdVO.setEp_seq(ep_seq);
//		rdVO.setEp_crp(ep_rp);
//		rdVO1.setEp_seq(ep_seq1);
//		rdVO1.setEp_crp(ep_rp1);
//		rdVO2.setEp_seq(ep_seq2);
//		rdVO2.setEp_crp(ep_rp2);
//		list.add(rdVO);
//		list.add(rdVO1);
//		list.add(rdVO2);
//		
//		System.out.println(list);
//		
//		dao.insertWithRoDetail(roVO1,list);
	
		// 修改
		ROrderVO roVO2 = new ROrderVO();
		roVO2.setRo_no("O20200308-001");
		roVO2.setOp_state("已付款");
		roVO2.setRr_date(java.sql.Date.valueOf("2020-03-9"));
		roVO2.setO_state("已歸還");
		roVO2.setFfine(new Integer(0));
		roVO2.setO_note("");
		dao.updateByDS(roVO2);
		
		 //查詢某會員之某訂單
//		ROrderVO roVO3= dao.findAMemRo("M000001","O20200306-001");
//		System.out.print(roVO3.getDs_no()+ ",");
//		System.out.print(roVO3.getTepc()+ ",");
//		System.out.print(roVO3.getTpriz()+ ",");
//		System.out.print(roVO3.getOp_state()+ ",");
//		System.out.print(roVO3.getRs_date()+ ",");
//		System.out.print(roVO3.getRd_date()+ ",");
//		System.out.print(roVO3.getRr_date()+ ",");
//		System.out.print(roVO3.getFfine()+ ",");
//		System.out.print(roVO3.getO_state()+ ",");
//		System.out.print(roVO3.getO_note()+ ",");
//		private static final String GET_ONE_MERO = "SELECT "
//				+ "DS_NO,TEPC,TPRIZ,OP_STATE,TO_CHAR(RS_DATE,'YYYY-MM-DD')RS_DATE"
//				+ ",TO_CHAR(RD_DATE,'YYYY-MM-DD')RD_DATE,"
//				+ "TO_CHAR(RR_DATE,'YYYY-MM-DD')RR_DATE,"
//				+ "FFINE,O_STATE,O_NOTE FROM R_ORDER WHERE MEM_NO=? AND RO_NO = ?";

//		System.out.println("---------------------");

		// 查詢潛店所有訂單
//		List<ROrderVO> list = dao.getAllDsRo("DS0001");
//		for (ROrderVO aRo : list) {
//		System.out.print(aRo.getRo_no()+ ",");
//		System.out.print(aRo.getMem_no()+ ",");
//		System.out.print(aRo.getTepc()+ ",");
//		System.out.print(aRo.getTpriz()+ ",");
//		System.out.print(aRo.getOp_state()+ ",");
//		System.out.print(aRo.getRs_date()+ ",");
//		System.out.print(aRo.getRd_date()+ ",");
//		System.out.print(aRo.getRr_date()+ ",");
//		System.out.print(aRo.getFfine()+ ",");
//		System.out.print(aRo.getO_state()+ ",");
//		System.out.print(aRo.getO_note()+ ",");
//		System.out.println();
//		}
		
		
		// 查詢會員所有訂單
//		List<ROrderVO> list = dao.getAllRoByAMem("M000003");
//		for (ROrderVO aRo : list) {
//		System.out.print(aRo.getRo_no()+ ",");
//		System.out.print(aRo.getDs_no()+ ",");
//		System.out.print(aRo.getTepc()+ ",");
//		System.out.print(aRo.getTpriz()+ ",");
//		System.out.print(aRo.getOp_state()+ ",");
//		System.out.print(aRo.getRs_date()+ ",");
//		System.out.print(aRo.getRd_date()+ ",");
//		System.out.print(aRo.getRr_date()+ ",");
//		System.out.print(aRo.getFfine()+ ",");
//		System.out.print(aRo.getO_state()+ ",");
//		System.out.print(aRo.getO_note()+ ",");
//		System.out.println();
//		}
		
//		Set<String> set = dao.getRR_DATE_IS_NULL();
//		for (String aRo : set) {
//			System.out.print(aRo);
//			System.out.println();
//		}
//		
		
		
	}
}