package com.friend.model;

import java.util.*;
import java.sql.*;

public class FriendJDBCDAO implements FriendDAO_interface{
    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    String userid = "DA105G5";
    String passwd = "DA105G5";
    
    private static final String INSERT_STMT = 
        "Insert into FRIEND (MEM_NO_A, MEM_NO_B, FRI_STATE) VALUES (?, ?, ?)";
    private static final String GET_ALL_STMT = 
        "SELECT * FROM FRIEND order by MEM_NO_A";
    private static final String GET_ONE_STMT = 
        "SELECT * FROM FRIEND where  MEM_NO_A= ? AND MEM_NO_B= ?";
    
    //查詢一位會員所有好友表格VO，用or語法，狀態通過的
    private static final String GET_ONE_MEM_STMT = 
        "SELECT * FROM FRIEND where  (MEM_NO_A= ? OR MEM_NO_B= ?) AND FRI_STATE ='通過'";
    
    private static final String DELETE = 
        "DELETE FROM FRIEND where MEM_NO_A= ? AND MEM_NO_B= ?";
    private static final String UPDATE = 
        "UPDATE FRIEND set FRI_STATE=? where MEM_NO_A= ? AND MEM_NO_B= ?";
    
  //查詢一位會員所有被加好友(自己是會員B時)，狀態還不是通過的好友表格VO
    private static final String GET_MEM_B_UNCONFIRM_STMT = 
        "SELECT * FROM FRIEND where MEM_NO_B= ? AND FRI_STATE !='通過'";
    
    
    @Override
    public void insert(FriendVO friendVO) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;
        
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(INSERT_STMT);
            
            pstmt.setString(1, friendVO.getMem_no_a());
            pstmt.setString(2, friendVO.getMem_no_b());
            pstmt.setString(3, friendVO.getFri_state());
            
            
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
    public void update(FriendVO friendVO) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;
        
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(UPDATE);
            
            pstmt.setString(1, friendVO.getFri_state());
            pstmt.setString(2, friendVO.getMem_no_a());
            pstmt.setString(3, friendVO.getMem_no_b());
            
            
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
    public void delete(String mem_no_a, String mem_no_b) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(DELETE);

            pstmt.setString(1, mem_no_a);
            pstmt.setString(2, mem_no_b);

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
    public FriendVO findByPrimaryKey(String mem_no_a, String mem_no_b) {
        // TODO Auto-generated method stub
        FriendVO friendVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, mem_no_a);
            pstmt.setString(2, mem_no_b);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                // webmasterVo 也稱為 Domain objects
                friendVO = new FriendVO();
                friendVO.setMem_no_a(rs.getString("mem_n0_a"));
                friendVO.setMem_no_b(rs.getString("mem_no_b"));
                friendVO.setFri_state(rs.getString("fri_state"));
 
                
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
        return friendVO;
    }
    
    //查詢一位會員所有好友資料
    @Override
    public List<FriendVO> getFriendsByMem_no(String mem_no_a){
        // TODO Auto-generated method stub
        List<FriendVO> list = new ArrayList<FriendVO>();
        FriendVO friendVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ONE_MEM_STMT);
            
            //當mem_no_a == mem_no_a  或者  mem_no_b == mem_no_a 時，都屬於mem_no這位會員的好友
            pstmt.setString(1, mem_no_a);
            pstmt.setString(2, mem_no_a);
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // webmasterVO 也稱為 Domain objects
                friendVO = new FriendVO();
                friendVO.setMem_no_a(rs.getString("mem_no_a"));
                friendVO.setMem_no_b(rs.getString("mem_no_b"));
                friendVO.setFri_state(rs.getString("fri_state"));
                
                list.add(friendVO); // Store the row in the list
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
    public List<FriendVO>  getMem_no_bUnOk(String mem_no_b){
        // TODO Auto-generated method stub
        List<FriendVO> list = new ArrayList<FriendVO>();
        FriendVO friendVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_MEM_B_UNCONFIRM_STMT);
                       
            pstmt.setString(1, mem_no_b);
             
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // webmasterVO 也稱為 Domain objects
                friendVO = new FriendVO();
                friendVO.setMem_no_a(rs.getString("mem_no_a"));
                friendVO.setMem_no_b(rs.getString("mem_no_b"));
                friendVO.setFri_state(rs.getString("fri_state"));
                
                list.add(friendVO); // Store the row in the list
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
    public List<FriendVO> getAll() {
        // TODO Auto-generated method stub
        List<FriendVO> list = new ArrayList<FriendVO>();
        FriendVO friendVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // webmasterVO 也稱為 Domain objects
                friendVO = new FriendVO();
                friendVO.setMem_no_a(rs.getString("mem_no_a"));
                friendVO.setMem_no_b(rs.getString("mem_no_b"));
                friendVO.setFri_state(rs.getString("fri_state"));
                
                list.add(friendVO); // Store the row in the list
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

        FriendJDBCDAO dao = new FriendJDBCDAO();

        // 新增
//        FriendVO friendVO1 = new FriendVO();
//        friendVO1.setMem_no_a("M000003");
//        friendVO1.setMem_no_b("M000004");
//        friendVO1.setFri_state("通過");
//        dao.insert(friendVO1);
        

        // 修改
//        FriendVO friendVO2 = new FriendVO();
//        friendVO2.setMem_no_a("M000003");
//        friendVO2.setMem_no_b("M000004");
//        friendVO2.setFri_state("不通過");
//
//        dao.update(friendVO2);

//      // 刪除
       dao.delete("M000003","M000004");
//
//      // 查詢
//      EmpVO empVO3 = dao.findByPrimaryKey(7001);
//      System.out.print(empVO3.getEmpno() + ",");
//      System.out.print(empVO3.getEname() + ",");
//      System.out.print(empVO3.getJob() + ",");
//      System.out.print(empVO3.getHiredate() + ",");
//      System.out.print(empVO3.getSal() + ",");
//      System.out.print(empVO3.getComm() + ",");
//      System.out.println(empVO3.getDeptno());
//      System.out.println("---------------------");
       
       //查詢一位會員所有好友資料
       List<FriendVO> list1 = dao.getFriendsByMem_no("M000001");
       for (FriendVO aFriend : list1) {
           
           System.out.print(aFriend.getMem_no_a() + ",");
           System.out.print(aFriend.getMem_no_b() + ",");
           System.out.print(aFriend.getFri_state());
           System.out.println();
           
       }
       System.out.println("---------------------");

        // 查詢
        List<FriendVO> list = dao.getAll();
        for (FriendVO aFriend : list) {
            
            System.out.print(aFriend.getMem_no_a() + ",");
            System.out.print(aFriend.getMem_no_b() + ",");
            System.out.print(aFriend.getFri_state());
            System.out.println();
        }
    }

}
