package com.gp_ept.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class Gp_Ept_JDBCDAO implements Gp_Ept_DAO_interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String passwd = "DA105G5";
	
	
	private static final String INSERT_STMT = 	
			"INSERT INTO GP_EPT(GP_EPT_NO,ACT_LIST_NO,MEM_NO,EPC_NO,GP_T_NUM,GP_T_SIZE)"+
			"VALUES ('GP_EPT_'||LPAD(to_char(gp_seq.NEXTVAL), 4, '0'),?,?,?,?,?)";
		private static final String GET_ALL_STMT = 
			"SELECT * FROM GP_EPT order by GP_EPT_NO";
		private static final String GET_ONE_STMT =
			"SELECT *FROM GP_EPT where GP_EPT_NO = ?";
		private static final String DELETE = 
			"DELETE FROM GP_EPT where GP_EPT_NO = ?";
		private static final String UPDATE = 
			"UPDATE GP_EPT set ACT_LIST_NO = ?,MEM_NO = ?,EPC_NO = ?,"
			+ "GP_T_NUM = ?,GP_T_SIZE=? where GP_EPT_NO = ?";
		
		private static final String GET_ONE =
				"SELECT *FROM GP_EPT where MEM_NO = ?";
		
	@Override
	public void insert(Gp_Ept_VO gp_ept_vo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1,gp_ept_vo.getAct_list_no());
			pstmt.setString(2,gp_ept_vo.getMem_no());
			pstmt.setString(3,gp_ept_vo.getEpc_no());
			pstmt.setInt(4,gp_ept_vo.getGp_t_num());
			pstmt.setString(5,gp_ept_vo.getGp_t_size());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
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
	public void update(Gp_Ept_VO gp_ept_vo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			
			pstmt.setString(1,gp_ept_vo.getAct_list_no());
			pstmt.setString(2,gp_ept_vo.getMem_no());
			pstmt.setString(3,gp_ept_vo.getEpc_no());
			pstmt.setInt(4,gp_ept_vo.getGp_t_num());
			pstmt.setString(5,gp_ept_vo.getGp_t_size());
			pstmt.setString(6,gp_ept_vo.getGp_ept_no());
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
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
	public void delete(String gp_ept_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1,gp_ept_no);

			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
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
	public Gp_Ept_VO findByPrimaryKey(String gp_ept_no) {
		
		Gp_Ept_VO gp_ept_vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1,gp_ept_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				gp_ept_vo = new Gp_Ept_VO();
				gp_ept_vo.setGp_ept_no(rs.getString("gp_ept_no"));
				gp_ept_vo.setAct_list_no(rs.getString("act_list_no"));
				gp_ept_vo.setMem_no(rs.getString("mem_no"));
				gp_ept_vo.setEpc_no(rs.getString("epc_no"));
				gp_ept_vo.setGp_t_num(rs.getInt("gp_t_num"));
				gp_ept_vo.setGp_t_size(rs.getString("gp_t_size"));


			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
		return gp_ept_vo;
	}
	@Override
	public List<Gp_Ept_VO> getAll() {
		List<Gp_Ept_VO> list = new ArrayList<Gp_Ept_VO>();
		Gp_Ept_VO gp_ept_vo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				gp_ept_vo = new Gp_Ept_VO();
				gp_ept_vo.setGp_ept_no(rs.getString("gp_ept_no"));
				gp_ept_vo.setAct_list_no(rs.getString("act_list_no"));
				gp_ept_vo.setMem_no(rs.getString("mem_no"));
				gp_ept_vo.setEpc_no(rs.getString("epc_no"));
				gp_ept_vo.setGp_t_num(rs.getInt("gp_t_num"));
				gp_ept_vo.setGp_t_size(rs.getString("gp_t_size"));

				list.add(gp_ept_vo); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
		return list;
	}
	
	@Override
	public void insert2(Gp_Ept_VO gp_ept_vo, Connection con) {
		
		PreparedStatement pstmt = null;

		try {

     		pstmt = con.prepareStatement(INSERT_STMT);

     		pstmt.setString(1,gp_ept_vo.getAct_list_no());
			pstmt.setString(2,gp_ept_vo.getMem_no());
			pstmt.setString(3,gp_ept_vo.getEpc_no());
			pstmt.setInt(4,gp_ept_vo.getGp_t_num());
			pstmt.setString(5,gp_ept_vo.getGp_t_size());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-gp_ept");
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
	public void insert3(List<Gp_Ept_VO> list) {
	
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			for(Gp_Ept_VO gp_ept_vo: list) {
			pstmt.setString(1,gp_ept_vo.getAct_list_no());
			pstmt.setString(2,gp_ept_vo.getMem_no());
			pstmt.setString(3,gp_ept_vo.getEpc_no());
			pstmt.setInt(4,gp_ept_vo.getGp_t_num());
			pstmt.setString(5,gp_ept_vo.getGp_t_size());
			}
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
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
	public Gp_Ept_VO findByMe(String mem_no) {
		Gp_Ept_VO gp_ept_vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE);

			pstmt.setString(1,mem_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				gp_ept_vo = new Gp_Ept_VO();
				gp_ept_vo.setGp_ept_no(rs.getString("gp_ept_no"));
				gp_ept_vo.setAct_list_no(rs.getString("act_list_no"));
				gp_ept_vo.setMem_no(rs.getString("mem_no"));
				gp_ept_vo.setEpc_no(rs.getString("epc_no"));
				gp_ept_vo.setGp_t_num(rs.getInt("gp_t_num"));
				gp_ept_vo.setGp_t_size(rs.getString("gp_t_size"));


			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
		return gp_ept_vo;
	}
	public static void main(String[] args) {

		Gp_Ept_JDBCDAO dao = new Gp_Ept_JDBCDAO();

		// 新增
//		Gp_Ept_VO gp_ept_vo1 = new Gp_Ept_VO();
//		gp_ept_vo1 = new GP_EPT_VO();
//		gp_ept_vo1.setGp_ept_no("GP_EPT_0002");
//		gp_ept_vo1.setAct_list_no("ACT_LIST_0001");
//		gp_ept_vo1.setMem_no("M000006");
//		gp_ept_vo1.setEpc_no("EQAH");
//		gp_ept_vo1.setGp_t_num(2);
//		gp_ept_vo1.setGp_t_size("X");
		
//
//		dao.insert(gp_ept_vo1);

		// 修改
//		Gp_Ept gp_ept_vo2 = new Gp_Ept_VO();
//		gp_ept_vo2 = new GP_EPT_VO();
//		gp_ept_vo2.setGp_ept_no("GP_EPT_0002");
//		gp_ept_vo2.setAct_list_no("ACT_LIST_0001");
//		gp_ept_vo2.setMem_no("M000006");
//		gp_ept_vo2.setEpc_no("EQAH");
//		gp_ept_vo2.setGp_t_num(5);
//		gp_ept_vo2.setGp_t_size("X");
//
//		dao.update(gp_ept_vo2);
		// 刪除
//		dao.delete("GP_EPT_0002");

		// 查詢
//		Gp_Ept gp_ept_vo3 = dao.findByPrimaryKey("GP_EPT_0001");
//		System.out.print(gp_ept_vo3.getGp_ept_no() + ",");
//		System.out.print(gp_ept_vo3.getAct_list_no() + ",");
//		System.out.print(gp_ept_vo3.getMem_no() + ",");
//		System.out.print(gp_ept_vo3.getEpc_no() + ",");
//		System.out.print(gp_ept_vo3.getGp_t_num() + ",");
//		System.out.print(gp_ept_vo3.getGp_t_size() + ",");	
//		System.out.println("---------------------");

		// 查詢
//		List<Gp_Ept_VO> list = dao.getAll();
//		for (Gp_Ept_VO aEmp : list) {
//			System.out.print(aEmp.getGp_ept_no() + ",");
//			System.out.print(aEmp.getAct_list_no() + ",");
//			System.out.print(aEmp.getMem_no() + ",");
//			System.out.print(aEmp.getEpc_no() + ",");
//			System.out.print(aEmp.getGp_t_num() + ",");
//			System.out.print(aEmp.getGp_t_size() + ",");
//			System.out.println();
//		}
	}
	
}
