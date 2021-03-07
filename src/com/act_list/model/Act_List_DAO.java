package com.act_list.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

import com.gp_ept.model.Gp_Ept_JDBCDAO;
import com.gp_ept.model.Gp_Ept_VO;

public class Act_List_DAO implements Act_List_DAO_interface{
	
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
			"INSERT INTO act_list (ACT_LIST_NO,MEM_NO,DP_NO,START_DATE,DUAL_DATE,ACTION_DATE,"
			+ "ACT_STATE,LOCALE,ACT_MAX,ACT_MIN,GP_PIC,GP_INFO,GP_DAYS,ACT_LIST_NAME)"+
			"VALUES ('ACT_LIST_'||LPAD(to_char(act_seq.NEXTVAL), 4, '0'),?,?,?,?,?,?,?,?,?,?,?,?,?)";
		private static final String GET_ALL_STMT = 
			"SELECT ACT_LIST_NO,MEM_NO,DP_NO,to_char(START_DATE,'yyyy-mm-dd') START_DATE"
			+ ",to_char(DUAL_DATE,'yyyy-mm-dd') DUAL_DATE,"
			+ "to_char(ACTION_DATE,'yyyy-mm-dd') ACTION_DATE,ACT_STATE,LOCALE,ACT_MAX,ACT_MIN,"
			+ "GP_PIC,GP_INFO,GP_DAYS,ACT_LIST_NAME FROM act_list order by ACT_LIST_NO";
		private static final String GET_ONE_STMT =
				"SELECT *FROM act_list where ACT_LIST_NO = ?";
		private static final String GET_Gp_ByAct_STMT =
				"SELECT * FROM GP_EPT where GP_EPT_NO = ? order by ACT_LIST_NO";
		
		private static final String DELETE = 
			"DELETE FROM act_list where ACT_LIST_NO = ?";
		private static final String UPDATE = 
			"UPDATE act_list set MEM_NO = ?,DP_NO = ?, START_DATE = ?"
			+", DUAL_DATE = ?,ACTION_DATE = ?,ACT_STATE = ?,LOCALE = ?,ACT_MAX = ?,"
			+"ACT_MIN = ?,GP_PIC = ?,GP_INFO = ?,GP_DAYS = ?,ACT_LIST_NAME=? where ACT_LIST_NO = ?";
		
		private static final String DELETE_ACT = 
				"DELETE FROM act_list where ACT_LIST_NO = ?";
		private static final String DELETE_GP = 
				"DELETE FROM gp_ept where ACT_LIST_NO = ?";
		
		private static final String GET_ONE_FREEGROUP =
				"SELECT *FROM act_list where MEM_NO=? and ACT_STATE=?";
		
		private static final String GET_ONE_MEM =
				"SELECT *FROM act_list where MEM_NO = ?";
		
		@Override
		public void insert(Act_List_VO act_list_vo) {
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, act_list_vo.getMem_no());
				pstmt.setString(2, act_list_vo.getDp_no());
				pstmt.setDate(3, act_list_vo.getStart_date());
				pstmt.setDate(4, act_list_vo.getDual_date());
				pstmt.setDate(5, act_list_vo.getAction_date());
				pstmt.setString(6, act_list_vo.getAct_state());
				pstmt.setString(7, act_list_vo.getLocale());
				pstmt.setInt(8, act_list_vo.getAct_max());			
				pstmt.setInt(9, act_list_vo.getAct_min());
				pstmt.setBytes(10, act_list_vo.getGp_pic());
				pstmt.setString(11, act_list_vo.getGp_info());
				pstmt.setInt(12, act_list_vo.getGp_days());
				pstmt.setString(13, act_list_vo.getAct_list_name());

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
		public void update(Act_List_VO act_list_vo) {
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {


				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);

				
				pstmt.setString(1, act_list_vo.getMem_no());
				pstmt.setString(2, act_list_vo.getDp_no());
				pstmt.setDate(3, act_list_vo.getStart_date());
				pstmt.setDate(4, act_list_vo.getDual_date());
				pstmt.setDate(5, act_list_vo.getAction_date());
				pstmt.setString(6, act_list_vo.getAct_state());
				pstmt.setString(7, act_list_vo.getLocale());
				pstmt.setInt(8, act_list_vo.getAct_max());
				pstmt.setInt(9, act_list_vo.getAct_min());				
				pstmt.setBytes(10, act_list_vo.getGp_pic());
				pstmt.setString(11, act_list_vo.getGp_info());
				pstmt.setInt(12, act_list_vo.getGp_days());
				pstmt.setString(13, act_list_vo.getAct_list_name());
				pstmt.setString(14, act_list_vo.getAct_list_no());
				
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
		public void delete(String act_list_no) {
			int updateCount_EMPs = 0;
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {


				con = ds.getConnection();
				
				// 1●設定於 pstm.executeUpdate()之前
				con.setAutoCommit(false);
				
				// 先刪除員工
				pstmt = con.prepareStatement(DELETE_GP);
				pstmt.setString(1,act_list_no);
				updateCount_EMPs = pstmt.executeUpdate();
				// 再刪除部門
				pstmt = con.prepareStatement(DELETE_ACT);
				pstmt.setString(1, act_list_no);
				
				pstmt.executeUpdate();
				// 2●設定於 pstm.executeUpdate()之後
				con.commit();
				con.setAutoCommit(true);
				System.out.println("刪除揪團編號" + act_list_no + "時,共有員工" + updateCount_EMPs
									+ "人同時被刪除");
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
		public Act_List_VO findByPrimaryKey(String act_list_no) {
			
			Act_List_VO act_list_vo = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {


				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setString(1, act_list_no);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// Act_List_VO 也稱為 Domain objects
					act_list_vo = new Act_List_VO();
					act_list_vo.setAct_list_no(rs.getString("act_list_no"));
					act_list_vo.setMem_no(rs.getString("mem_no"));
					act_list_vo.setDp_no(rs.getString("dp_no"));
					act_list_vo.setStart_date(rs.getDate("start_date"));
					act_list_vo.setDual_date(rs.getDate("dual_date"));
					act_list_vo.setAction_date(rs.getDate("action_date"));
					act_list_vo.setAct_state(rs.getString("act_state"));
					act_list_vo.setLocale(rs.getString("locale"));
					act_list_vo.setAct_max(rs.getInt("act_max"));
					act_list_vo.setAct_min(rs.getInt("act_min"));
					act_list_vo.setGp_pic(rs.getBytes("gp_pic"));
					act_list_vo.setGp_info(rs.getString("gp_info"));
					act_list_vo.setGp_days(rs.getInt("gp_days"));
					act_list_vo.setAct_list_name(rs.getString("act_list_name"));
				}

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
			return act_list_vo;	
		}
		@Override
		public List<Act_List_VO> getAll() {
			List<Act_List_VO> list = new ArrayList<Act_List_VO>();
			Act_List_VO act_list_vo = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {


				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// Act_List_VO 也稱為 Domain objects
					act_list_vo = new Act_List_VO();
					act_list_vo.setAct_list_no(rs.getString("act_list_no"));
					act_list_vo.setMem_no(rs.getString("mem_no"));
					act_list_vo.setDp_no(rs.getString("dp_no"));
					act_list_vo.setStart_date(rs.getDate("start_date"));
					act_list_vo.setDual_date(rs.getDate("dual_date"));
					act_list_vo.setAction_date(rs.getDate("action_date"));
					act_list_vo.setAct_state(rs.getString("act_state"));
					act_list_vo.setLocale(rs.getString("locale"));
					act_list_vo.setAct_max(rs.getInt("act_max"));
					act_list_vo.setAct_min(rs.getInt("act_min"));
					act_list_vo.setGp_pic(rs.getBytes("gp_pic"));
					act_list_vo.setGp_info(rs.getString("gp_info"));
					act_list_vo.setGp_days(rs.getInt("gp_days"));
					act_list_vo.setAct_list_name(rs.getString("act_list_name"));
					list.add(act_list_vo); // Store the row in the list
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
		public Set<Gp_Ept_VO> getGpEptByActListNo(String act_list_no) {
			Set<Gp_Ept_VO> set = new HashSet<Gp_Ept_VO>();
			Gp_Ept_VO gp_ept_vo = null;
		
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setString(1,act_list_no);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVo 也稱為 Domain objects
					gp_ept_vo = new Gp_Ept_VO();
					gp_ept_vo.setGp_ept_no(rs.getString("gp_ept_no"));
					gp_ept_vo.setAct_list_no(rs.getString("act_list_no"));
					gp_ept_vo.setMem_no(rs.getString("mem_no"));
					gp_ept_vo.setEpc_no(rs.getString("epc_no"));
					gp_ept_vo.setGp_t_num(rs.getInt("gp_t_num"));
					
					set.add(gp_ept_vo);
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
			return set;
		}
		
		@Override
		public void insertWithGpEpt(Act_List_VO act_list_vo, List<Gp_Ept_VO> list) {
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {

				con = ds.getConnection();

				
				// 1●設定於 pstm.executeUpdate()之前
	    		con.setAutoCommit(false);
				
	    		// 先新增部門
				String cols[] = {"ACT_LIST_NO"};
				pstmt = con.prepareStatement(INSERT_STMT,cols);

				pstmt.setString(1, act_list_vo.getMem_no());
				pstmt.setString(2, act_list_vo.getDp_no());
				pstmt.setDate(3, act_list_vo.getStart_date());
				pstmt.setDate(4, act_list_vo.getDual_date());
				pstmt.setDate(5, act_list_vo.getAction_date());
				pstmt.setString(6, act_list_vo.getAct_state());
				pstmt.setString(7, act_list_vo.getLocale());
				pstmt.setInt(8, act_list_vo.getAct_max());			
				pstmt.setInt(9, act_list_vo.getAct_min());
				pstmt.setBytes(10, act_list_vo.getGp_pic());
				pstmt.setString(11, act_list_vo.getGp_info());
				pstmt.setInt(12, act_list_vo.getGp_days());
				pstmt.setString(13, act_list_vo.getAct_list_name());

				
			
				pstmt.executeUpdate();
				
				
				//掘取對應的自增主鍵值
				String next_act_list_no = null;
				ResultSet rs = pstmt.getGeneratedKeys();
				System.out.println(pstmt);
				System.out.println(rs);
				if (rs.next()) {
					next_act_list_no = rs.getString(1);
					System.out.println("自增主鍵值= " + next_act_list_no +"(剛新增成功的部門編號)");
				} else {
					System.out.println("未取得自增主鍵值");
				}
				rs.close();
				
				// 再同時新增員工
				Gp_Ept_JDBCDAO dao = new Gp_Ept_JDBCDAO();
				System.out.println("list.size()-A="+list.size());
				for (Gp_Ept_VO aEmp : list) {
					aEmp.setAct_list_no(new String(next_act_list_no)) ;
					dao.insert2(aEmp,con);
				}

				// 2●設定於 pstm.executeUpdate()之後
				con.commit();
				con.setAutoCommit(true);
				System.out.println("list.size()-B="+list.size());
				System.out.println("新增部門編號" + next_act_list_no + "時,共有員工" + list.size()
						+ "人同時被新增");
				
				// Handle any driver errors
			}catch (SQLException se) {
				if (con != null) {
					try {
						// 3●設定於當有exception發生時之catch區塊內
						System.err.print("Transaction is being ");
						System.err.println("rolled back-由-act_list");
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
		public Set<Act_List_VO> freegroup(String mem_no, String act_state) {
			Set<Act_List_VO> set = new HashSet<Act_List_VO>();
			Act_List_VO act_list_vo = null;
		
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_FREEGROUP);

				pstmt.setString(1,mem_no);
				pstmt.setString(2,act_state);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVo 也稱為 Domain objects
					act_list_vo = new Act_List_VO();
					act_list_vo.setAct_list_no(rs.getString("act_list_no"));
					act_list_vo.setMem_no(rs.getString("mem_no"));
					act_list_vo.setDp_no(rs.getString("dp_no"));
					act_list_vo.setStart_date(rs.getDate("start_date"));
					act_list_vo.setDual_date(rs.getDate("dual_date"));
					act_list_vo.setAction_date(rs.getDate("action_date"));
					act_list_vo.setAct_state(rs.getString("act_state"));
					act_list_vo.setLocale(rs.getString("locale"));
					act_list_vo.setAct_max(rs.getInt("act_max"));
					act_list_vo.setAct_min(rs.getInt("act_min"));
					act_list_vo.setGp_pic(rs.getBytes("gp_pic"));
					act_list_vo.setGp_info(rs.getString("gp_info"));
					act_list_vo.setGp_days(rs.getInt("gp_days"));
					act_list_vo.setAct_list_name(rs.getString("act_list_name"));
					
					set.add(act_list_vo);
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
			return set;
		}
		@Override
		public Set<Act_List_VO> group(String mem_no) {
			Set<Act_List_VO> set = new HashSet<Act_List_VO>();
			Act_List_VO act_list_vo = null;
		
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_MEM);

				pstmt.setString(1,mem_no);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVo 也稱為 Domain objects
					act_list_vo = new Act_List_VO();
					act_list_vo.setAct_list_no(rs.getString("act_list_no"));
					act_list_vo.setMem_no(rs.getString("mem_no"));
					act_list_vo.setDp_no(rs.getString("dp_no"));
					act_list_vo.setStart_date(rs.getDate("start_date"));
					act_list_vo.setDual_date(rs.getDate("dual_date"));
					act_list_vo.setAction_date(rs.getDate("action_date"));
					act_list_vo.setAct_state(rs.getString("act_state"));
					act_list_vo.setLocale(rs.getString("locale"));
					act_list_vo.setAct_max(rs.getInt("act_max"));
					act_list_vo.setAct_min(rs.getInt("act_min"));
					act_list_vo.setGp_pic(rs.getBytes("gp_pic"));
					act_list_vo.setGp_info(rs.getString("gp_info"));
					act_list_vo.setGp_days(rs.getInt("gp_days"));
					act_list_vo.setAct_list_name(rs.getString("act_list_name"));
					
					set.add(act_list_vo);
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
			return set;
		}
		



	
     
		
		
		
	
}
