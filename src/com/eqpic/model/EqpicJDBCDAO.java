package com.eqpic.model;

import java.util.*;

import com.equip.model.EquipVO;

import java.sql.*;
import java.io.*;

public class EqpicJDBCDAO implements EqpicDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String passwd = "DA105G5";

	private static final String INSERT_STMT = "INSERT INTO EQPIC (EPIC_SEQ,DS_NO,EP_SEQ,EPIC) VALUES (('EPP'||LPAD(TO_CHAR(EPIC_SEQ.NEXTVAL),3,'0')),?,?,?)";
	private static final String GET_DS_ALL = "SELECT EPIC_SEQ,DS_NO,EP_SEQ,EPIC FROM EQPIC WHERE DS_NO = ?";
	private static final String GET_EP_ALL = "SELECT EPIC_SEQ,DS_NO,EP_SEQ,EPIC FROM EQPIC WHERE EP_SEQ=?";
	private static final String GET_ONE_EPIC = "SELECT EPIC_SEQ,DS_NO,EP_SEQ,EPIC FROM EQPIC WHERE EPIC_SEQ = ?";
	private static final String GET_EPIC_SEQ_BY_EP_SEQ = "SELECT EPIC_SEQ FROM EQPIC WHERE EP_SEQ =?";
	private static final String DELETE = "DELETE FROM EQPIC WHERE EPIC_SEQ = ?";
	private static final String UPDATE = "UPDATE EQPIC set EP_SEQ=?,EPIC=? WHERE EPIC_SEQ =?";
	//	private static final String GET_ADS_EPIC  = "SELECT DISTINCT EP_SEQ FROM EQPIC WHERE DS_NO = ? ORDER BY EP_SEQ";
	
	@Override
	public void insert(EqpicVO eqpicVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, eqpicVO.getDs_no());
			pstmt.setString(2, eqpicVO.getEp_seq());
			pstmt.setBytes(3, eqpicVO.getEpic());
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
	public void update(EqpicVO eqpicVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, eqpicVO.getEp_seq());
			pstmt.setBytes(2, eqpicVO.getEpic());
			pstmt.setString(3, eqpicVO.getEpic_seq());
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
	public void delete(String epic_seq) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, epic_seq);
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
	public EqpicVO findByPrimaryKey(String epic_seq) {
		EqpicVO eqpicVO=null;
		Connection con=null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
	
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt=con.prepareStatement(GET_ONE_EPIC);
			
			pstmt.setString(1, epic_seq);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				eqpicVO=new EqpicVO();
				eqpicVO.setDs_no(rs.getString("ds_no"));
				eqpicVO.setEp_seq(rs.getString("ep_seq"));
				eqpicVO.setEpic(rs.getBytes("epic"));
			}
			
		}catch(ClassNotFoundException e){
			throw new RuntimeException("Couldn't load database driver"+e.getMessage());
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
	public void insertByEquip(EqpicVO eqpicVO , Connection con) {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, eqpicVO.getDs_no());
			pstmt.setString(2, eqpicVO.getEp_seq());
			pstmt.setBytes(3, eqpicVO.getEpic());
System.out.println("------------------------------------------");
System.out.println(eqpicVO.getDs_no());
System.out.println(eqpicVO.getEp_seq());
System.out.println(eqpicVO.getEpic().length);
System.out.println("------------------------------------------");
			pstmt.executeUpdate();
		}catch(SQLException se) {
			if(con!=null) {
				try {
					System.err.print(" Transaction is being ");
					System.err.print(" rolled back from Epic ");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public List<EqpicVO> findByDsAll(String ds_no) {

		List<EqpicVO> list = new ArrayList<EqpicVO>();
		EqpicVO eqpicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_DS_ALL);

			pstmt.setString(1, ds_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// EpcVO 也稱為 Domain objects
				eqpicVO = new EqpicVO();
				eqpicVO.setEpic_seq(rs.getString("epic_seq"));
				eqpicVO.setDs_no(rs.getString("ds_no"));
				eqpicVO.setEp_seq(rs.getString("ep_seq"));
				eqpicVO.setEpic(rs.getBytes("epic"));
				list.add(eqpicVO);

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
	public List<EqpicVO> findByAEp_seq_All_Epic(String ep_seq) {

		List<EqpicVO> list = new ArrayList<EqpicVO>();
		EqpicVO eqpicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_EP_ALL);

			pstmt.setString(1, ep_seq);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// EpcVO 也稱為 Domain objects
				eqpicVO = new EqpicVO();
				eqpicVO.setEpic_seq(rs.getString("epic_seq"));
				eqpicVO.setEp_seq(rs.getString("ep_seq"));
				eqpicVO.setDs_no(rs.getString("ds_no"));
				eqpicVO.setEpic(rs.getBytes("epic"));
				list.add(eqpicVO);

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
	public List<String> AEp_seq_All_Epic_seq(String ep_seq){
		
		List<String> list = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_EPIC_SEQ_BY_EP_SEQ );
			
			pstmt.setString(1, ep_seq);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String epic_seq=rs.getString("epic_seq");
				list.add(epic_seq);
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

	public static void main(String[] args) throws IOException {

		EqpicJDBCDAO dao = new EqpicJDBCDAO();

		// 新增
//		EqpicVO epcVO1 = new EqpicVO();
//		epcVO1.setDs_no("DS0001");
//		epcVO1.setEp_seq("EP0001");
//		byte[] b=null;
//		try {
//		FileInputStream in = new FileInputStream("C:\\Users\\frede\\Desktop\\資策會紀錄\\專題紀錄\\裝備圖片\\BCD\\BCD2.jpg");
//		b = new byte[in.available()];
//		in.read(b);
//		in.close();
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//		epcVO1.setEpic(b);
//		dao.insert(epcVO1);

		// 刪除
//		dao.delete("EPP006");

		// 修改
//		EqpicVO epcVO2 = new EqpicVO();
//		epcVO2.setEp_seq("EP0001");
//		byte[] b2=null;
//		try {
//		FileInputStream in = new FileInputStream("C:\\Users\\frede\\Desktop\\資策會紀錄\\專題紀錄\\裝備圖片\\BCD\\BCD4.jpg");
//		b2 = new byte[in.available()];
//		in.read(b2);
//		in.close();
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//		epcVO2.setEpic(b2);
//		epcVO2.setEpic_seq("EPP001");
//		dao.update(epcVO2);

		// 查詢單張
//		EqpicVO eqpicVO3 = dao.findByPrimaryKey("EPP001");
//		System.out.print(eqpicVO3.getEp_seq() + ",");
//		System.out.print(eqpicVO3.getEpic() + ",");
//		System.out.println("---------------------");
		
//		
//		 查詢 DsAll
//		List<EqpicVO> listDA = dao.findByDsAll("DS0001");
//		for (EqpicVO aEpic : listDA) {
//			System.out.print(aEpic.getEp_seq() + ",");
//			System.out.print(aEpic.getEpic() + ",");
//			System.out.println();
//		}
//		List<String> listseq = dao.AEp_seq_All_Epic_seq("EP0001");
//		for (String aEpic : listseq) {
//			System.out.print(aEpic);
//			System.out.println();
//		}
//		
		
		 //查詢 EPAll
//		List<EqpicVO> listEP = dao.findByAEp_seq_All_Epic("EP0001");
//		for (EqpicVO aEpic : listEP) {
//			System.out.print(aEpic.getEp_seq() + ",");
//			System.out.print(aEpic.getEpic() + ",");
//			System.out.println();
//		}

		// 查詢DSSEF
//		List<EqpicVO> listDSE = dao.findByDsSE("DS0001", "EP0001");
//		for (EqpicVO aEpic : listDSE) {
//			System.out.print(aEpic.getEpic_name() + ",");
//			System.out.print(aEpic.getEpic() + ",");
//			System.out.println();
//		}

		// 查詢find_EP_SEQ_ByDS_NO
//		List<String> test = dao.findByDsSE("DS0001", "EP0001");
//		for (EqpicVO aEpic : listDSE) {
//			System.out.print(aEpic.getEpic_name() + ",");
//			System.out.print(aEpic.getEpic() + ",");
//			System.out.println();
//		}
//		
		
	}

}