package com.func.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class FuncDAO implements FuncDAO_interface{
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
        	con = ds.getConnection();
            pstmt = con.prepareStatement(INSERT_STMT);
            
            pstmt.setString(1, funcVO.getFc_name());
            
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
    public void update(FuncVO funcVO) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;
        
        
        try {
        	con = ds.getConnection();
            pstmt = con.prepareStatement(UPDATE);
            
            pstmt.setString(1, funcVO.getFc_name());
            pstmt.setString(2, funcVO.getFc_no());
            
            
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
    public void delete(String fc_no) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

        	con = ds.getConnection();
            pstmt = con.prepareStatement(DELETE);

            pstmt.setString(1, fc_no);

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
    public FuncVO findByPrimaryKey(String fc_no) {
        // TODO Auto-generated method stub
        FuncVO funcVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

        	con = ds.getConnection();
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, fc_no);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                funcVO = new FuncVO();
                funcVO.setFc_no(rs.getString("fc_no"));
                funcVO.setFc_name(rs.getString("fc_name"));
 
                
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

        	con = ds.getConnection();
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                funcVO = new FuncVO();
                funcVO.setFc_no(rs.getString("fc_no"));
                funcVO.setFc_name(rs.getString("fc_name"));

                list.add(funcVO); // Store the row in the list
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

