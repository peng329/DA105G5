package com.agp_list.model;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.act_list.model.Act_List_VO;




public class Agp_List_DAO implements Agp_List_DAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DA105G5");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 	
			"INSERT INTO agp_list (ACT_LIST_NO,MEM_NO,MEM_LIC,ACT_NUM,MEM_LIC_PIC,AGP_STATE)"+
			"VALUES (?,?,?,?,?,?)";
		private static final String GET_ALL_STMT = 
			"SELECT * FROM agp_list order by ACT_LIST_NO,MEM_NO";
		private static final String GET_ONE_STMT =
			"SELECT *FROM agp_list where ACT_LIST_NO = ? and MEM_NO = ?";
		private static final String DELETE = 
			"DELETE FROM agp_list where ACT_LIST_NO = ? and MEM_NO = ?";
		private static final String UPDATE = 
			"UPDATE agp_list set MEM_LIC = ?,ACT_NUM = ?,MEM_LIC_PIC=?"
			+ ",AGP_STATE = ? where ACT_LIST_NO = ? and MEM_NO = ?";
		
		private static final String GET_ONE_JOINGROUP =
				"SELECT *FROM agp_list where MEM_NO=? and AGP_STATE=?";
		
		private static final String GET_MEN_STATE =
				"SELECT *FROM agp_list where AGP_STATE=?";
		
	@Override
	public void insert(Agp_List_VO agp_list_vo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, agp_list_vo.getAct_list_no());
			pstmt.setString(2, agp_list_vo.getMem_no());			
			pstmt.setString(3, agp_list_vo.getMem_lic());
			pstmt.setInt(4, agp_list_vo.getAct_num());
			pstmt.setBytes(5, agp_list_vo.getMem_lic_pic());
			pstmt.setString(6, agp_list_vo.getAgp_state());
			

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void update(Agp_List_VO agp_list_vo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			
			pstmt.setString(1, agp_list_vo.getMem_lic());
			pstmt.setInt(2, agp_list_vo.getAct_num());
			pstmt.setBytes(3, agp_list_vo.getMem_lic_pic());
			pstmt.setString(4, agp_list_vo.getAgp_state());
			pstmt.setString(5, agp_list_vo.getAct_list_no());
			pstmt.setString(6, agp_list_vo.getMem_no());
			
			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void delete(String agp_list_no,String mem_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, agp_list_no);
			pstmt.setString(2, mem_no);
			
			pstmt.executeUpdate();

			// Handle any driver errors
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
	public Agp_List_VO findByPrimaryKey(String agp_list_no,String mem_no) {
		
		Agp_List_VO agp_list_vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, agp_list_no);
			pstmt.setString(2, mem_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				agp_list_vo = new Agp_List_VO();
				agp_list_vo.setAct_list_no(rs.getString("act_list_no"));
				agp_list_vo.setMem_no(rs.getString("mem_no"));
				agp_list_vo.setMem_lic(rs.getString("mem_lic"));
				agp_list_vo.setAct_num(rs.getInt("act_num"));
				agp_list_vo.setMem_lic_pic(rs.getBytes("mem_lic_pic"));
				agp_list_vo.setAgp_state(rs.getString("agp_state"));

			}

			// Handle any driver errors
		}catch (SQLException se) {
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
		return agp_list_vo;
	}
	@Override
	public List<Agp_List_VO> getAll() {
		List<Agp_List_VO> list = new ArrayList<Agp_List_VO>();
		Agp_List_VO agp_list_vo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				agp_list_vo = new Agp_List_VO();
				agp_list_vo.setAct_list_no(rs.getString("act_list_no"));
				agp_list_vo.setMem_no(rs.getString("mem_no"));
				agp_list_vo.setMem_lic(rs.getString("mem_lic"));
				agp_list_vo.setAct_num(rs.getInt("act_num"));
				agp_list_vo.setMem_lic_pic(rs.getBytes("mem_lic_pic"));
				agp_list_vo.setAgp_state(rs.getString("agp_state"));
				list.add(agp_list_vo); // Store the row in the list
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
		return list;
	}
	@Override
	public Set<Agp_List_VO> joingroup(String mem_no, String agp_state) {
		
		Set<Agp_List_VO> set = new HashSet<Agp_List_VO>();
		
		
		Agp_List_VO agp_list_vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_JOINGROUP);

			pstmt.setString(1, mem_no);
			pstmt.setString(2, agp_state);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				agp_list_vo = new Agp_List_VO();
				agp_list_vo.setAct_list_no(rs.getString("act_list_no"));
				agp_list_vo.setMem_no(rs.getString("mem_no"));
				agp_list_vo.setMem_lic(rs.getString("mem_lic"));
				agp_list_vo.setAct_num(rs.getInt("act_num"));
				agp_list_vo.setMem_lic_pic(rs.getBytes("mem_lic_pic"));
				agp_list_vo.setAgp_state(rs.getString("agp_state"));
				
				set.add(agp_list_vo);

			}

			// Handle any driver errors
		}catch (SQLException se) {
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
		return set;
	}
	@Override
	public Set<Agp_List_VO> findMemState(String agp_state) {
		Set<Agp_List_VO> set = new HashSet<Agp_List_VO>();
		
		Agp_List_VO agp_list_vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEN_STATE);

			pstmt.setString(1, agp_state);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				agp_list_vo = new Agp_List_VO();
				agp_list_vo.setAct_list_no(rs.getString("act_list_no"));
				agp_list_vo.setMem_no(rs.getString("mem_no"));
				agp_list_vo.setMem_lic(rs.getString("mem_lic"));
				agp_list_vo.setAct_num(rs.getInt("act_num"));
				agp_list_vo.setMem_lic_pic(rs.getBytes("mem_lic_pic"));
				agp_list_vo.setAgp_state(rs.getString("agp_state"));
				
				set.add(agp_list_vo);

			}

			// Handle any driver errors
		}catch (SQLException se) {
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
		return set;
	}
	
		
//	public static void main(String[] args) {
//
//		Agp_List_JDBCDAO dao = new Agp_List_JDBCDAO();
//		byte[] mem_lic_pic =null;
//		BufferedInputStream bin;
//		 try {
//			 bin =new BufferedInputStream(new FileInputStream("C:\\Users\\Java\\Desktop\\123\\4.jpg"));
//			 mem_lic_pic =new byte[bin.available()];
//			 bin.read(mem_lic_pic);
//			 System.out.println(mem_lic_pic);
//			 bin.close();
//		 
//		 } catch (Exception e) {
//			e.printStackTrace();
//		}
		 
		 
		 
		// 新增
//		Agp_List_VO agp_list_vo1 = new Agp_List_VO();
//		agp_list_vo1.setAct_list_no("ACT_LIST_0001");
//		agp_list_vo1.setMem_no("M000006");		
//		agp_list_vo1.setMem_lic("P2-180006");
//		agp_list_vo1.setAct_num(3);
//		agp_list_vo1.setMem_lic_pic(mem_lic_pic);		 
//		agp_list_vo1.setAgp_state("待審核");
//		dao.insert(agp_list_vo1);

		// 修改
//		Agp_List_VO agp_list_vo2 = new Agp_List_VO();
//		agp_list_vo2.setAct_list_no("ACT_LIST_0001");
//		agp_list_vo2.setMem_no("M000006");
//		agp_list_vo2.setMem_lic("P2-180006");
//		agp_list_vo2.setAct_num(5);
//		agp_list_vo2.setMem_lic_pic(mem_lic_pic);
//		agp_list_vo2.setAgp_state("通過");
//		dao.update(agp_list_vo2);

		// 刪除
//		dao.delete("ACT_LIST_0001","M000006");

		// 查詢
//		Agp_List_VO agp_list_vo3 = dao.findByPrimaryKey("ACT_LIST_0001","M000005");
//		System.out.print(agp_list_vo3.getAct_list_no() + ",");
//		System.out.print(agp_list_vo3.getMem_no() + ",");
//		System.out.print(agp_list_vo3.getMem_lic() + ",");
//		System.out.print(agp_list_vo3.getAct_num() + ",");
//		System.out.print(agp_list_vo3.getMem_lic_pic() + ",");
//		System.out.print(agp_list_vo3.getAgp_state() + ",");
//		System.out.println("---------------------");

		// 查詢
//		List<Agp_List_VO> list = dao.getAll();
//		for (Agp_List_VO aEmp : list) {
//			System.out.print(aEmp.getAct_list_no() + ",");
//			System.out.print(aEmp.getMem_no() + ",");
//			System.out.print(aEmp.getMem_lic() + ",");
//			System.out.print(aEmp.getAct_num() + ",");
//			System.out.print(aEmp.getMem_lic_pic() + ",");
//			System.out.print(aEmp.getAgp_state() + ",");
//			System.out.println();
//		}
//	}
	
}
