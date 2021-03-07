package com.webmaster.model;

import java.util.*;

import com.authority_manage.model.Authority_manageVO;

import java.sql.*;

public class WebmasterDAO implements WebmasterDAO_interface{
    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    String userid = "DA105G5";
    String passwd = "DA105G5";

    private static final String INSERT_STMT = 
        "Insert into WEBMASTER (WM_NO, WM_NAME, WM_SEX, WM_ID, WM_PSW, WM_MAIL, OB_DATE, DD_DATE,  REG_TIME) VALUES ('A'||LPAD(to_char(WEBMASTER_SEQ.NEXTVAL),2,'0'), ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_STMT = 
        "SELECT * FROM WEBMASTER order by WM_NO";
    private static final String GET_ONE_STMT = 
        "SELECT * FROM WEBMASTER where WM_NO = ?";
    
    //用帳號查找一位員工
    private static final String GET_ONE_WM_ID_STMT = 
            "SELECT * FROM WEBMASTER where WM_ID = ?";
    
    private static final String GET_AMS_BYWM_NO_STMT = "SELECT * FROM AUTHORITY_MANAGE where WM_NO= ? order by Fc_NO";
    
    private static final String DELETE_AMS = "DELETE FROM AUTHORITY_MANAGE where WM_NO = ?";
    private static final String DELETE_WM = 
        "DELETE FROM WEBMASTER where WM_NO = ?";
    
    private static final String UPDATE = 
        "UPDATE WEBMASTER set WM_NAME=?, WM_SEX=?, WM_PSW=?, WM_MAIL=?, OB_DATE=?, DD_DATE=? where WM_NO = ?";

    
    
    @Override
    public String insert(WebmasterVO webmasterVO) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;
        String key = "";
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            
            String cols[] = { "WM_NO" };
            
            pstmt = con.prepareStatement(INSERT_STMT, cols);
            
            pstmt.setString(1, webmasterVO.getWm_name());
            pstmt.setInt(2, webmasterVO.getWm_sex());
            pstmt.setString(3, webmasterVO.getWm_id());
            pstmt.setString(4, webmasterVO.getWm_psw());
            pstmt.setString(5, webmasterVO.getWm_mail());
            pstmt.setDate(6, webmasterVO.getOb_date());
            pstmt.setDate(7, webmasterVO.getDd_date());
            pstmt.setTimestamp(8, webmasterVO.getReg_time());

            pstmt.executeUpdate();
            
    		//掘取對應的自增主鍵值
    		ResultSet rsKeys = pstmt.getGeneratedKeys();
    		ResultSetMetaData rsmd = rsKeys.getMetaData();
    		int columnCount = rsmd.getColumnCount();
    		
    		if (rsKeys.next()) {
    			do {
    				for (int i = 1; i <= columnCount; i++) {
    					key = rsKeys.getString(i);
    					System.out.println("自增主鍵值(i=" + i + ") = " + key +"(剛新增成功的員工編號)");
    					
    				}
    			} while (rsKeys.next());
    		} else {
    			System.out.println("NO KEYS WERE GENERATED.");
    		}
        
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
        return key; 
    }

    @Override
    public void update(WebmasterVO webmasterVO) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;
        
        
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(UPDATE);
            
            pstmt.setString(1, webmasterVO.getWm_name());
            pstmt.setInt(2, webmasterVO.getWm_sex());
            pstmt.setString(3, webmasterVO.getWm_psw());
            pstmt.setString(4, webmasterVO.getWm_mail());
            pstmt.setDate(5, webmasterVO.getOb_date());
            pstmt.setDate(6, webmasterVO.getDd_date());
            pstmt.setString(7, webmasterVO.getWm_no());
            
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
    public void delete(String wm_no) {
    	int updateCount_Ams = 0;
    	
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            
            //1●設定於 pstm.executeUpdate()之前
            con.setAutoCommit(false);
            
            //先刪除權限
            pstmt = con.prepareStatement(DELETE_AMS);
			pstmt.setString(1, wm_no);
            updateCount_Ams = pstmt.executeUpdate();
            
            //再刪除管理員           
            pstmt = con.prepareStatement(DELETE_WM);
            pstmt.setString(1, wm_no); 
			pstmt.executeUpdate();
            
            //2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("管理員刪除時" + wm_no + "時,共有權限" + updateCount_Ams
					+ "筆同時被刪除");
			

            // Handle any driver errors
        } catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
    public WebmasterVO findByPrimaryKey(String wm_no) {
        // TODO Auto-generated method stub
        WebmasterVO webmasterVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1, wm_no);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                // webmasterVo 也稱為 Domain objects
                webmasterVO = new WebmasterVO();
                webmasterVO.setWm_no(rs.getString("wm_no"));
                webmasterVO.setWm_name(rs.getString("wm_name"));
                webmasterVO.setWm_sex(rs.getInt("wm_sex"));
                webmasterVO.setWm_id(rs.getString("wm_id"));
                webmasterVO.setWm_psw(rs.getString("wm_psw"));
                webmasterVO.setWm_mail(rs.getString("wm_mail"));
                webmasterVO.setOb_date(rs.getDate("ob_date"));
                webmasterVO.setDd_date(rs.getDate("dd_date"));
                webmasterVO.setReg_time(rs.getTimestamp("reg_time"));
 
                
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
        return webmasterVO;
    }
    
    
    @Override
    public WebmasterVO findByWm_id(String wm_id) {
        // TODO Auto-generated method stub
        WebmasterVO webmasterVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ONE_WM_ID_STMT);

            pstmt.setString(1, wm_id);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                // webmasterVo 也稱為 Domain objects
                webmasterVO = new WebmasterVO();
                webmasterVO.setWm_no(rs.getString("wm_no"));
                webmasterVO.setWm_name(rs.getString("wm_name"));
                webmasterVO.setWm_sex(rs.getInt("wm_sex"));
                webmasterVO.setWm_id(rs.getString("wm_id"));
                webmasterVO.setWm_psw(rs.getString("wm_psw"));
                webmasterVO.setWm_mail(rs.getString("wm_mail"));
                webmasterVO.setOb_date(rs.getDate("ob_date"));
                webmasterVO.setDd_date(rs.getDate("dd_date"));
                webmasterVO.setReg_time(rs.getTimestamp("reg_time"));
 
                
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
        return webmasterVO;
    }
    
    
    
    

    @Override
    public List<WebmasterVO> getAll() {
        // TODO Auto-generated method stub
        List<WebmasterVO> list = new ArrayList<WebmasterVO>();
        WebmasterVO webmasterVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                
                webmasterVO = new WebmasterVO();
                webmasterVO.setWm_no(rs.getString("wm_no"));
                webmasterVO.setWm_name(rs.getString("wm_name"));
                webmasterVO.setWm_sex(rs.getInt("wm_sex"));
                webmasterVO.setWm_id(rs.getString("wm_id"));
                webmasterVO.setWm_psw(rs.getString("wm_psw"));
                webmasterVO.setWm_mail(rs.getString("wm_mail"));
                webmasterVO.setOb_date(rs.getDate("ob_date"));
                webmasterVO.setDd_date(rs.getDate("dd_date"));
                webmasterVO.setReg_time(rs.getTimestamp("reg_time"));
                list.add(webmasterVO); // Store the row in the list
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
	public Set<Authority_manageVO> getAmsByWm_no(String wm_no) {
		Set<Authority_manageVO> set = new LinkedHashSet<Authority_manageVO>();
		Authority_manageVO authority_manageVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_AMS_BYWM_NO_STMT);
			pstmt.setString(1, wm_no);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				authority_manageVO = new Authority_manageVO();
				authority_manageVO.setWm_no(rs.getString("wm_no"));
				authority_manageVO.setFc_no(rs.getString("fc_no"));

				set.add(authority_manageVO); // Store the row in the vector
			}
	
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
    
    public static void main(String[] args) {

        WebmasterDAO dao = new WebmasterDAO();

        // 新增
        WebmasterVO webmasterVO1 = new WebmasterVO();
        
        webmasterVO1.setWm_name("劉華");
        webmasterVO1.setWm_sex(1);
        webmasterVO1.setWm_id("Luck");
        webmasterVO1.setWm_psw("123456");
        webmasterVO1.setWm_mail("luckxxx@gmail.com");
        webmasterVO1.setOb_date(java.sql.Date.valueOf("2005-01-01"));
        webmasterVO1.setDd_date(java.sql.Date.valueOf("2005-01-01"));
        webmasterVO1.setReg_time(java.sql.Timestamp.valueOf("2005-01-01 11:30:20.0"));
        String key = dao.insert(webmasterVO1);
        System.out.println(key);
        
//      // 修改
//        WebmasterVO webmasterVO2 = new WebmasterVO();
//        webmasterVO2.setWm_no("A03");
//        webmasterVO2.setWm_name("劉華x");
//        webmasterVO2.setWm_sex(1);
//        webmasterVO2.setWm_psw("123456");
//        webmasterVO2.setWm_mail("luck2xxx@gmail.com");
//        webmasterVO2.setOb_date(java.sql.Date.valueOf("2008-01-01"));
//        webmasterVO2.setDd_date(null);
//        dao.update(webmasterVO2);        


//      // 刪除
//        dao.delete("A01");
//
//      // 查詢
//        WebmasterVO masterVO3 = dao.findByPrimaryKey("A02");
//        System.out.print(masterVO3.getWm_no() + ",");
//        System.out.print(masterVO3.getWm_name() + ",");
//        System.out.print(masterVO3.getWm_sex() + ",");
//        System.out.print(masterVO3.getWm_id() + ",");
//        System.out.print(masterVO3.getWm_psw() + ",");
//        System.out.print(masterVO3.getWm_mail() + ",");
//        System.out.print(masterVO3.getOb_date() + ",");
//        System.out.print(masterVO3.getDd_date() + ",");
//        System.out.print(masterVO3.getReg_time());
//        System.out.println();
//        System.out.println("---------------------");

        // 查詢
        List<WebmasterVO> list = dao.getAll();
        for (WebmasterVO aWebmaster : list) {
            
            System.out.print(aWebmaster.getWm_no() + ",");
            System.out.print(aWebmaster.getWm_name() + ",");
            System.out.print(aWebmaster.getWm_sex() + ",");
            System.out.print(aWebmaster.getWm_id() + ",");
            System.out.print(aWebmaster.getWm_psw() + ",");
            System.out.print(aWebmaster.getWm_mail() + ",");
            System.out.print(aWebmaster.getOb_date() + ",");
            System.out.print(aWebmaster.getDd_date() + ",");
            System.out.print(aWebmaster.getReg_time());
            System.out.println();
        }
        
        
        //查詢某管理員的權限
        Set<Authority_manageVO> set = dao.getAmsByWm_no("A01");
		for (Authority_manageVO aAuthority_manage : set) {
			System.out.print(aAuthority_manage.getWm_no() + ",");
			System.out.print(aAuthority_manage.getFc_no());
			System.out.println();
		}
        
    }

}

