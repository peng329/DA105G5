package com.authority_manage.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Authority_manageDAO implements Authority_manageDAO_interface{
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
    
    private static final String INSERT_STMT = 
        "Insert into AUTHORITY_MANAGE (WM_NO, FC_NO) VALUES (?, ?)";
    private static final String GET_ALL_STMT = 
        "SELECT * FROM AUTHORITY_MANAGE order by WM_NO";
    private static final String GET_ONE_STMT = 
        "SELECT * FROM AUTHORITY_MANAGE where  WM_NO= ? AND FC_NO=?";
    
    private static final String DELETE = 
        "DELETE FROM AUTHORITY_MANAGE where WM_NO= ? AND FC_NO= ?";
    
    private static final String GET_WM_ALL_STMT = 
        "SELECT * FROM AUTHORITY_MANAGE WHERE WM_NO= ? order by FC_NO";
    
    
    @Override
    public void insert(Authority_manageVO authority_manageVO) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;
        
        
        try {
        	con = ds.getConnection();
            pstmt = con.prepareStatement(INSERT_STMT);
            
            pstmt.setString(1, authority_manageVO.getWm_no());
            pstmt.setString(2, authority_manageVO.getFc_no());  
            
            pstmt.executeUpdate();
        
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
    public void delete(String wm_no, String fc_no) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

        	con = ds.getConnection();
            pstmt = con.prepareStatement(DELETE);

            pstmt.setString(1, wm_no);
            pstmt.setString(2, fc_no);

            pstmt.executeUpdate();

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
    public Authority_manageVO findByPrimaryKey(String wm_no, String fc_no) {
        // TODO Auto-generated method stub
        Authority_manageVO authority_manageVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

        	con = ds.getConnection();
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, wm_no);
            pstmt.setString(2, fc_no);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                authority_manageVO = new Authority_manageVO();
                authority_manageVO.setWm_no(rs.getString("wm_no"));
                authority_manageVO.setFc_no(rs.getString("fc_no"));
 
 
                
            }

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
        return authority_manageVO;
    }

    
    @Override
    public List<Authority_manageVO> getAll() {
        // TODO Auto-generated method stub
        List<Authority_manageVO> list = new ArrayList<Authority_manageVO>();
        Authority_manageVO authority_manageVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

        	con = ds.getConnection();
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                authority_manageVO = new Authority_manageVO();
                authority_manageVO.setWm_no(rs.getString("wm_no"));
                authority_manageVO.setFc_no(rs.getString("fc_no"));
                
                list.add(authority_manageVO); // Store the row in the list
            }

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
    public List<Authority_manageVO> getAuthority_manageVOsByWm_no(String wm_no) {
        // TODO Auto-generated method stub
        List<Authority_manageVO> list = new ArrayList<Authority_manageVO>();
        Authority_manageVO authority_manageVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

        	con = ds.getConnection();
            pstmt = con.prepareStatement(GET_WM_ALL_STMT);
            pstmt.setString(1, wm_no);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                authority_manageVO = new Authority_manageVO();
                authority_manageVO.setWm_no(rs.getString("wm_no"));
                authority_manageVO.setFc_no(rs.getString("fc_no"));
                
                list.add(authority_manageVO); // Store the row in the list
            }

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
 

}
