package com.webmaster.controller;


public class PassRandom {

    public static void main(String[] args) {
        PassRandom q1 = new PassRandom();
        
    }
    
    public String genAuthCode() {
        char c = 0;
        int temp = 0;
        
        String passCode ="";
        while(passCode.length() != 8) { //當字串長度達到8個才能跳出迴圈
            
           temp = (int)(Math.random()*3); 
           //先隨機跑0,1,2，來隨機進入下一次隨機。分3次是因為數字、大寫英文、小寫英文的Unicode碼數字不連續
           switch(temp) {
               case 0:
                   c = (char)(int)(Math.random()*10 + 48); //48～57是數字Unicode碼的十進位
                   break;
               case 1:
                   c = (char)(int)(Math.random()*26 + 65); //65～90為大寫英文
                   break;
               case 2:
                   c = (char)(int)(Math.random()*26 + 97); //97～122為小寫英文
                   break;      
           }
           passCode += c; //字串串接字元為字串
        }
//        System.out.println("本次隨機產生驗證碼為：");
//        System.out.println(passCode);
        
        return passCode;
    }

}
