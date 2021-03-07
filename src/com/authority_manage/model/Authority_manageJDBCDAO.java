package com.authority_manage.model;

import java.util.*;
import java.sql.*;

public class Authority_manageJDBCDAO implements Authority_manageDAO_interface{
    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    String userid = "DA105G5";
    String passwd = "DA105G5";
    
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
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(INSERT_STMT);
            
            pstmt.setString(1, authority_manageVO.getWm_no());
            pstmt.setString(2, authority_manageVO.getFc_no());  
            
            pstmt.executeUpdate();
        
        }catch (ClassNotFoundException e) {
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
    public void delete(String wm_no, String fc_no) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(DELETE);

            pstmt.setString(1, wm_no);
            pstmt.setString(2, fc_no);

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
    public Authority_manageVO findByPrimaryKey(String wm_no, String fc_no) {
        // TODO Auto-generated method stub
        Authority_manageVO authority_manageVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, wm_no);
            pstmt.setString(2, fc_no);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                authority_manageVO = new Authority_manageVO();
                authority_manageVO.setWm_no(rs.getString("wm_no"));
                authority_manageVO.setFc_no(rs.getString("fc_no"));
 
 
                
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

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                authority_manageVO = new Authority_manageVO();
                authority_manageVO.setWm_no(rs.getString("wm_no"));
                authority_manageVO.setFc_no(rs.getString("fc_no"));
                
                list.add(authority_manageVO); // Store the row in the list
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
    public List<Authority_manageVO> getAuthority_manageVOsByWm_no(String wm_no) {
        // TODO Auto-generated method stub
        List<Authority_manageVO> list = new ArrayList<Authority_manageVO>();
        Authority_manageVO authority_manageVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_WM_ALL_STMT);
            pstmt.setString(1, wm_no);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                authority_manageVO = new Authority_manageVO();
                authority_manageVO.setWm_no(rs.getString("wm_no"));
                authority_manageVO.setFc_no(rs.getString("fc_no"));
                
                list.add(authority_manageVO); // Store the row in the list
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
    
    public static void main(String[] args) {

        Authority_manageJDBCDAO dao = new Authority_manageJDBCDAO();

        // 新增
//        Authority_manageVO  authority_manageVO1 = new Authority_manageVO();
//        authority_manageVO1.setWm_no("A02");
//        authority_manageVO1.setFc_no("F03");
//        dao.insert(authority_manageVO1);


       //沒有修改


        // 刪除
//        dao.delete("A01","F06");

        // 查詢
        Authority_manageVO  authority_manageVO2 = dao.findByPrimaryKey("A01","F02");
        System.out.print(authority_manageVO2.getWm_no() + ",");
        System.out.print(authority_manageVO2.getFc_no());
        System.out.println();

        System.out.println("---------------------");
        


        // 查詢
        List<Authority_manageVO> list = dao.getAll();
        for (Authority_manageVO aAuthority_manage : list) {
            
            System.out.print(aAuthority_manage.getWm_no() + ",");
            System.out.print(aAuthority_manage.getFc_no());
            System.out.println();
        }
    }

}
