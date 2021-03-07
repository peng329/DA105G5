package com.mrds_record.model;

import java.sql.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Mrds_recordJNDIDAO implements Mrds_recordDAO_interface {
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mrds_recordVO.getMem_no());
			pstmt.setString(2, mrds_recordVO.getDs_no());
			pstmt.setTimestamp(3, mrds_recordVO.getRep_time());
			pstmt.setString(4, mrds_recordVO.getRep_content());
			pstmt.setString(5, mrds_recordVO.getRep_state());

			pstmt.executeUpdate();

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, mrds_recordVO.getMem_no());
			pstmt.setString(2, mrds_recordVO.getDs_no());
			pstmt.setTimestamp(3, mrds_recordVO.getRep_time());
			pstmt.setString(4, mrds_recordVO.getRep_content());
			pstmt.setString(5, mrds_recordVO.getRep_state());
			pstmt.setString(6, mrds_recordVO.getMrds_no());
			pstmt.executeUpdate();

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, mrds_no);
			
			pstmt.executeUpdate();

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
			con = ds.getConnection();
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
			con = ds.getConnection();
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

}
