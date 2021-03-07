

import java.sql.*;
import java.io.*;

class PhotoWrite {

    static {
        try {
             Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }
    
    //此程式，用來將資料庫假資料圖片補上，要修改的一共4處地方

    public static void main(String argv[]) {
          Connection con = null;
          PreparedStatement pstmt = null;
          InputStream fin = null;
          String url = "jdbc:oracle:thin:@localhost:1521:XE";
          String userid = "DA105G5";
          String passwd = "DA105G5";
          
          //要修改photos，放圖檔路徑，有多少筆資料，就放多少張圖
          String photos = "C:\\Users\\Lenovo\\Desktop\\memImg";
          //要修改底下的語法，所以假資料先有，再去更換圖片欄位
          String update = "UPDATE MEM SET MEM_PIC=? WHERE MEM_NO=?";
          
          //這是為了編號計次用
          int count = 1;
          
          try {
            con = DriverManager.getConnection(url, userid, passwd);
            File[] photoFiles = new File(photos).listFiles();
            
            for (File f : photoFiles) {
            	fin = new FileInputStream(f);
            	pstmt = con.prepareStatement(update);
            	
            	//下面是編號的動態串接，這裡暫時性是9筆以下，自己可自己修改其他方式
            	String mem_no = "M00000" + String.valueOf(count);
            	
            	
            	pstmt.setString(2, mem_no);
            	pstmt.setBinaryStream(1, fin);
            	pstmt.executeUpdate();
            	count++;
            	
				System.out.println("\n\nUpdate the database... ");
				System.out.println(f.toString());
			}


            fin.close();
            pstmt.close();

          } catch (Exception e) {
                e.printStackTrace();
          } finally {
                try {
                  con.close();
                } catch (SQLException e) {
                }
         }
  }
}