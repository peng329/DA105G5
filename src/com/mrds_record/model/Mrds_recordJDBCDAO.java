package com.mrds_record.model;

import java.sql.*;
import java.util.*;

public class Mrds_recordJDBCDAO implements Mrds_recordDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA105G5";
	String password = "DA105G5";

	private static final String INSERT_STMT = "INSERT INTO MRDS_RECORD (mrds_no,mem_no,ds_no,rep_time,rep_content,rep_state) VALUES ('RD'||LPAD(to_char(MRDSNO_SEQ.NEXTVAL),5,'0'), ?, ?, ?, ?, ?)";

	private static final String GET_ALL_STMT = "SELECT mrds_no,mem_no,ds_no,to_char(rep_time,'yyyy-mm-dd hh24:mi:ss') rep_time,rep_content,rep_state FROM MRDS_RECORD order by mrds_no";

	private static final String GET_ONE_STMT = "SELECT mrds_no,mem_no,ds_no,to_char(rep_time,'yyyy-mm-dd hh24:mi:ss') rep_time,rep_content,rep_state FROM MRDS_RECORD where mrds_no=?";

	private static final String DELETE = "DELETE FROM MRDS_RECORD where mrds_no = ?";

	private static final String UPDATE = "UPDATE MRDS_RECORD set mem_no=?, ds_no=?, rep_time=?, rep_content=?, rep_state=? where mrds_no = ? ";

	@Override
	public void insert(Mrds_recordVO mrds_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mrds_recordVO.getMem_no());
			pstmt.setString(2, mrds_recordVO.getDs_no());
			pstmt.setTimestamp(3, mrds_recordVO.getRep_time());
			pstmt.setString(4, mrds_recordVO.getRep_content());
			pstmt.setString(5, mrds_recordVO.getRep_state());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public void update(Mrds_recordVO mrds_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, mrds_recordVO.getMem_no());
			pstmt.setString(2, mrds_recordVO.getDs_no());
			pstmt.setTimestamp(3, mrds_recordVO.getRep_time());
			pstmt.setString(4, mrds_recordVO.getRep_content());
			pstmt.setString(5, mrds_recordVO.getRep_state());
			pstmt.setString(6, mrds_recordVO.getMrds_no());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver."+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database erroe occured."+se.getMessage());
		} finally {
			if(pstmt!= null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con!= null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	@Override
	public void delete(String mrds_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, mrds_no);
			
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver."+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured."+ se.getMessage());
		} finally {
			if(pstmt!= null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con!= null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public Mrds_recordVO findByPrimaryKey(String mrds_no) {
		Mrds_recordVO mrds_recordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, mrds_no);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
			  mrds_recordVO = new Mrds_recordVO();
			  mrds_recordVO.setMrds_no(rs.getString("mrds_no"));
			  mrds_recordVO.setMem_no(rs.getString("mem_no"));
			  mrds_recordVO.setDs_no(rs.getString("ds_no"));
			  mrds_recordVO.setRep_time(rs.getTimestamp("rep_time"));
			  mrds_recordVO.setRep_content(rs.getString("rep_content"));
			  mrds_recordVO.setRep_state(rs.getString("rep_state"));
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver."+e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if(rs!= null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt!= null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con!= null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		
		return mrds_recordVO;
	}
	
	@Override
	public List<Mrds_recordVO> getAll(){
		List<Mrds_recordVO> list = new ArrayList<>();
		Mrds_recordVO mrds_recordVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				mrds_recordVO = new Mrds_recordVO();
				
				mrds_recordVO.setMrds_no(rs.getString("mrds_no")); 
				mrds_recordVO.setMem_no(rs.getString("mem_no"));
				mrds_recordVO.setDs_no(rs.getString("ds_no")); 
				mrds_recordVO.setRep_time(rs.getTimestamp("rep_time")); 
				mrds_recordVO.setRep_content(rs.getString("rep_content"));
				mrds_recordVO.setRep_state(rs.getString("rep_state")); 
				
				list.add(mrds_recordVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver."+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured."+ se.getMessage());
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt!= null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
				   se.printStackTrace(System.err);
				}
			}
			if(con!= null) {
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
		Mrds_recordJDBCDAO dao = new Mrds_recordJDBCDAO();
		
//		//新增
//		Mrds_recordVO mrdsVO1 = new Mrds_recordVO();
//		mrdsVO1.setMem_no("M000002");
//		mrdsVO1.setDs_no("DS0001");
//		mrdsVO1.setRep_time(java.sql.Timestamp.valueOf("2020-01-29 13:30:17"));
//		mrdsVO1.setRep_content("女教練不給約，差評");
//		mrdsVO1.setRep_state("不通過");
//		dao.insert(mrdsVO1);
//		System.out.println("新增OK!");
		//修改
//		Mrds_recordVO mrdsVO2 = new Mrds_recordVO();
//		mrdsVO2.setMrds_no("RD00003");
//		mrdsVO2.setMem_no("M000002");
//		mrdsVO2.setDs_no("DS0001");
//		mrdsVO2.setRep_time(java.sql.Timestamp.valueOf("2020-01-30 04:30:17"));
//		mrdsVO2.setRep_content("女教練不給約，差評");
//		mrdsVO2.setRep_state("待審核");
//		dao.update(mrdsVO2);
//		System.out.println("修改OK!");
		
		//刪除
//		dao.delete("RD00003");
//		System.out.println("刪除OK!");
		//查詢一件
		Mrds_recordVO mrdsVO3 = dao.findByPrimaryKey("RD00001");
		System.out.print(mrdsVO3.getMrds_no()+",");
		System.out.print(mrdsVO3.getMem_no()+",");
		System.out.print(mrdsVO3.getDs_no()+",");
		System.out.print(mrdsVO3.getRep_time()+",");
		System.out.print(mrdsVO3.getRep_content()+",");
		System.out.println(mrdsVO3.getRep_state());
		System.out.println("-------------------------------");
		//查詢全部
		List<Mrds_recordVO> list = dao.getAll();
		
		for(Mrds_recordVO mrdsVO4 : list) {
			System.out.print(mrdsVO4.getMrds_no()+",");
			System.out.print(mrdsVO4.getMem_no()+",");
			System.out.print(mrdsVO4.getDs_no()+",");
			System.out.print(mrdsVO4.getRep_time()+",");
			System.out.print(mrdsVO4.getRep_content()+",");
			System.out.println(mrdsVO4.getRep_state());
			System.out.println("-------------------");
		}
		
	}

}
