package com.func.model;

import java.util.*;
import java.sql.*;

public class FuncJDBCDAO implements FuncDAO_interface{
    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    String userid = "DA105G5";
    String passwd = "DA105G5";
    
    private static final String INSERT_STMT = 
        "Insert into FUNC (FC_NO, FC_NAME) VALUES ('F'||LPAD(to_char(FUNC_SEQ.NEXTVAL),2,'0'), ?)";
    private static final String GET_ALL_STMT = 
        "SELECT * FROM FUNC order by FC_NO";
    private static final String GET_ONE_STMT = 
        "SELECT * FROM FUNC where FC_NO = ?";
    private static final String DELETE = 
        "DELETE FROM FUNC where FC_NO = ?";
    private static final String UPDATE = 
        "UPDATE FUNC set FC_NAME=? where FC_NO = ?";

            
    
    
    @Override
    public void insert(FuncVO funcVO) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;
        
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(INSERT_STMT);
            
            pstmt.setString(1, funcVO.getFc_name());
            
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
    public void update(FuncVO funcVO) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;
        
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(UPDATE);
            
            pstmt.setString(1, funcVO.getFc_name());
            pstmt.setString(2, funcVO.getFc_no());
            
            
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
    public void delete(String fc_no) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(DELETE);

            pstmt.setString(1, fc_no);

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
    public FuncVO findByPrimaryKey(String fc_no) {
        // TODO Auto-generated method stub
        FuncVO funcVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, fc_no);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                funcVO = new FuncVO();
                funcVO.setFc_no(rs.getString("fc_no"));
                funcVO.setFc_name(rs.getString("fc_name"));
 
                
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
        return funcVO;
    }

    @Override
    public List<FuncVO> getAll() {
        // TODO Auto-generated method stub
        List<FuncVO> list = new ArrayList<FuncVO>();
        FuncVO funcVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                funcVO = new FuncVO();
                funcVO.setFc_no(rs.getString("fc_no"));
                funcVO.setFc_name(rs.getString("fc_name"));

                list.add(funcVO); // Store the row in the list
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

        FuncJDBCDAO dao = new FuncJDBCDAO();

        // 新增
//        FuncVO funcVO1 = new FuncVO();
//        funcVO1.setFc_name("員工管理");
//        dao.insert(funcVO1);


//      // 修改
//        FuncVO funcVO2 = new FuncVO();
//        funcVO2.setFc_no("F07");
//        funcVO2.setFc_name("圖書管理");

//      // 刪除
//        dao.delete("F07");

//      // 查詢
        FuncVO funcVO3 = dao.findByPrimaryKey("F03");
        System.out.print(funcVO3.getFc_no() + ",");
        System.out.print(funcVO3.getFc_name());
        System.out.println();        
        System.out.println("---------------------");

        // 查詢
        List<FuncVO> list = dao.getAll();
        for (FuncVO aFc : list) {            
            System.out.print(aFc.getFc_no() + ",");
            System.out.print(aFc.getFc_name());
            System.out.println();
        }
    }

}

