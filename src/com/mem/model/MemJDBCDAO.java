package com.mem.model;

import java.util.*;
import java.sql.*;

public class MemJDBCDAO implements MemDAO_interface{
    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    String userid = "DA105G5";
    String passwd = "DA105G5";

    private static final String INSERT_STMT = 
        "Insert into MEM (MEM_NO, MEM_ID, MEM_PSW, MEM_NAME, MEM_SEX, MEM_BD, MEM_MAIL, MEM_PHONE, MEM_ADD, MEM_PIC ,REG_TIME, MEM_REP_NO, MEM_STATE) VALUES ('M'||LPAD(to_char(mem_seq.NEXTVAL), 6, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_STMT = 
            "SELECT MEM_NO, MEM_ID, MEM_PSW, MEM_NAME, MEM_SEX, to_char(MEM_BD,'yyyy-mm-dd')MEM_BD, MEM_MAIL, MEM_PHONE, MEM_ADD, MEM_PIC ,to_char(REG_TIME,'yyyy-mm-dd hh24:mi:ss')REG_TIME, MEM_REP_NO, MEM_STATE FROM MEM order by MEM_NO";
    
    private static final String GET_ONE_STMT = 
        "SELECT * FROM MEM where MEM_NO = ?";
    
    //用帳號查找一位會員
    private static final String GET_ONE_MEM_ID_STMT = 
            "SELECT * FROM MEM where MEM_ID = ?";
    
    
    private static final String DELETE = 
        "DELETE FROM MEM where MEM_NO = ?";
    private static final String UPDATE = 
        "UPDATE MEM set MEM_PSW=?, MEM_NAME=?, MEM_SEX=?, MEM_BD=?, MEM_MAIL=?, MEM_PHONE=?, MEM_ADD=?, MEM_PIC=?, MEM_REP_NO=?, MEM_STATE=? where MEM_NO = ?";
    
    //更改會員狀態
    private static final String UPDATE_MEM_STATE = 
            "UPDATE MEM set MEM_STATE=? where MEM_ID = ?";
    
    
    
    @Override
    public void insert(MemVO memVO) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;
        
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(INSERT_STMT);
            
            pstmt.setString(1, memVO.getMem_id());
            pstmt.setString(2, memVO.getMem_psw());
            pstmt.setString(3, memVO.getMem_name());
            pstmt.setInt(4, memVO.getMem_sex());
            pstmt.setDate(5, memVO.getMem_bd());
            pstmt.setString(6, memVO.getMem_mail());
            pstmt.setString(7, memVO.getMem_phone());
            pstmt.setString(8, memVO.getMem_add());
            pstmt.setBytes(9, memVO.getMem_pic());
            pstmt.setTimestamp(10, memVO.getReg_time());
            pstmt.setInt(11, memVO.getMem_rep_no());
            pstmt.setString(12, memVO.getMem_state());
            
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
    public void update(MemVO memVO) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;
        
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(UPDATE);
            

            pstmt.setString(1, memVO.getMem_psw());
            pstmt.setString(2, memVO.getMem_name());
            pstmt.setInt(3, memVO.getMem_sex());
            pstmt.setDate(4, memVO.getMem_bd());
            pstmt.setString(5, memVO.getMem_mail());
            pstmt.setString(6, memVO.getMem_phone());
            pstmt.setString(7, memVO.getMem_add());
            pstmt.setBytes(8, memVO.getMem_pic());
            pstmt.setInt(9, memVO.getMem_rep_no());
            pstmt.setString(10, memVO.getMem_state());
            pstmt.setString(11, memVO.getMem_no());
            
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
    public void updateMemState(String mem_id ,String stateNum) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;
        
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(UPDATE_MEM_STATE);
            
            String mem_state = "待驗證";
            
            if("2".equals(stateNum)) {
            	mem_state = "通過";
            }else if("3".equals(stateNum)) {
            	mem_state = "停權";
            }else {
            	mem_state = "待驗證";
            }
            
            
            pstmt.setString(1, mem_state);
            pstmt.setString(2, mem_id);
   
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
    public void delete(String mem_no) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(DELETE);

            pstmt.setString(1, mem_no);

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
    public MemVO findByPrimaryKey(String mem_no) {
        // TODO Auto-generated method stub
        MemVO memVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, mem_no);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                memVO = new MemVO();
                memVO.setMem_no(rs.getString("mem_no"));
                memVO.setMem_id(rs.getString("mem_id"));
                memVO.setMem_psw(rs.getString("mem_psw"));
                memVO.setMem_name(rs.getString("mem_name"));
                memVO.setMem_sex(rs.getInt("mem_sex"));
                memVO.setMem_bd(rs.getDate("mem_bd"));
                memVO.setMem_mail(rs.getString("mem_mail"));
                memVO.setMem_phone(rs.getString("mem_phone"));
                memVO.setMem_add(rs.getString("mem_add"));
                memVO.setMem_pic(rs.getBytes("mem_pic"));
                memVO.setReg_time(rs.getTimestamp("reg_time"));
                memVO.setMem_rep_no(rs.getInt("mem_rep_no"));
                memVO.setMem_state(rs.getString("mem_state"));
                
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
        return memVO;
    }
    
    //用帳號查找一位會員
    @Override
    public MemVO findByMem_id(String mem_id) {
        // TODO Auto-generated method stub
        MemVO memVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ONE_MEM_ID_STMT);

            pstmt.setString(1, mem_id);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                memVO = new MemVO();
                memVO.setMem_no(rs.getString("mem_no"));
                memVO.setMem_id(rs.getString("mem_id"));
                memVO.setMem_psw(rs.getString("mem_psw"));
                memVO.setMem_name(rs.getString("mem_name"));
                memVO.setMem_sex(rs.getInt("mem_sex"));
                memVO.setMem_bd(rs.getDate("mem_bd"));
                memVO.setMem_mail(rs.getString("mem_mail"));
                memVO.setMem_phone(rs.getString("mem_phone"));
                memVO.setMem_add(rs.getString("mem_add"));
                memVO.setMem_pic(rs.getBytes("mem_pic"));
                memVO.setReg_time(rs.getTimestamp("reg_time"));
                memVO.setMem_rep_no(rs.getInt("mem_rep_no"));
                memVO.setMem_state(rs.getString("mem_state"));
                
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
        return memVO;
    }
    

    @Override
    public List<MemVO> getAll() {
        // TODO Auto-generated method stub
        List<MemVO> list = new ArrayList<MemVO>();
        MemVO memVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                memVO = new MemVO();
                memVO.setMem_no(rs.getString("mem_no"));
                memVO.setMem_id(rs.getString("mem_id"));
                memVO.setMem_psw(rs.getString("mem_psw"));
                memVO.setMem_name(rs.getString("mem_name"));
                memVO.setMem_sex(rs.getInt("mem_sex"));
                memVO.setMem_bd(rs.getDate("mem_bd"));
                memVO.setMem_mail(rs.getString("mem_mail"));
                memVO.setMem_phone(rs.getString("mem_phone"));
                memVO.setMem_add(rs.getString("mem_add"));
                memVO.setMem_pic(rs.getBytes("mem_pic"));
                memVO.setReg_time(rs.getTimestamp("reg_time"));
                memVO.setMem_rep_no(rs.getInt("mem_rep_no"));
                memVO.setMem_state(rs.getString("mem_state"));
                list.add(memVO); // Store the row in the list
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

        MemJDBCDAO dao = new MemJDBCDAO();

        // 新增
        MemVO memVO1 = new MemVO();
        memVO1.setMem_id("Luck");
        memVO1.setMem_psw("123456");
        memVO1.setMem_name("劉華");
        memVO1.setMem_sex(1);
        memVO1.setMem_bd(java.sql.Date.valueOf("2005-01-01"));
        memVO1.setMem_mail("luckxxx@gmail.com");
        memVO1.setMem_phone("0912111111");
        memVO1.setMem_add("台南市xx路xx段xx號");
        memVO1.setMem_pic(new byte[1]);
        memVO1.setReg_time(java.sql.Timestamp.valueOf("2020-02-21 21:02:44"));
        memVO1.setMem_rep_no(0);
        memVO1.setMem_state("通過");
        dao.insert(memVO1);
        

//      // 修改
        MemVO memVO2 = new MemVO();
        memVO2.setMem_no("M000010");
        memVO2.setMem_psw("123456");
        memVO2.setMem_name("劉華華");
        memVO2.setMem_sex(1);
        memVO2.setMem_bd(java.sql.Date.valueOf("2007-01-01"));
        memVO2.setMem_mail("luckxxx@gmail.com");
        memVO2.setMem_phone("0912111111");
        memVO2.setMem_add("台南市xx路xx段xx號");
        memVO2.setMem_pic(new byte[0]);
        memVO2.setMem_rep_no(1);
        memVO2.setMem_state("通過");
        dao.update(memVO2);
        

//      // 刪除
//      dao.delete("M000011");
//
//      // 查詢
//      MemVO memVO3 = dao.findByPrimaryKey("M000003");
//      System.out.print(memVO3.getMem_no() + ",");
//      System.out.print(memVO3.getMem_id() + ",");
//      System.out.print(memVO3.getMem_psw() + ",");
//      System.out.print(memVO3.getMem_name() + ",");
//      System.out.print(memVO3.getMem_sex() + ",");
//      System.out.print(memVO3.getMem_bd() + ",");
//      System.out.print(memVO3.getMem_mail() + ",");
//      System.out.print(memVO3.getMem_phone() + ",");
//      System.out.print(memVO3.getMem_add() + ",");
//      System.out.print(memVO3.getMem_pic() + ",");
//      System.out.print(memVO3.getReg_time() + ",");
//      System.out.print(memVO3.getMem_rep_no() + ",");
//      System.out.print(memVO3.getMem_state());
//      System.out.println();
//      System.out.println("---------------------");

        // 查詢
        List<MemVO> list = dao.getAll();
        for (MemVO aMem : list) {
            
            System.out.print(aMem.getMem_no() + ",");
            System.out.print(aMem.getMem_id() + ",");
            System.out.print(aMem.getMem_psw() + ",");
            System.out.print(aMem.getMem_name() + ",");
            System.out.print(aMem.getMem_sex() + ",");
            System.out.print(aMem.getMem_bd() + ",");
            System.out.print(aMem.getMem_mail() + ",");
            System.out.print(aMem.getMem_phone() + ",");
            System.out.print(aMem.getMem_add() + ",");
            System.out.print(aMem.getMem_pic() + ",");
            System.out.print(aMem.getReg_time() + ",");
            System.out.print(aMem.getMem_rep_no() + ",");
            System.out.print(aMem.getMem_state());
            System.out.println();
        }
    }

}
