--------last updated 20200129 by 鎮仰
-- select * from v$version;
CLEAR SCREEN;
--------------------------------------------------------
--  CHANGE DATE FORMAT
--------------------------------------------------------
ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD';

--------------------------------------------------------
--  CHANGE TIMESTAMP FORMAT 
--------------------------------------------------------
alter session set NLS_TIMESTAMP_FORMAT = 'yyyy-mm-dd hh24:mi:ss:ff';

---------------1.會員------------------------------
CREATE TABLE MEM(
MEM_NO VARCHAR2(7) PRIMARY KEY NOT NULL,
MEM_ID VARCHAR2(20) NOT NULL,
MEM_PSW VARCHAR2(20) NOT NULL,
MEM_NAME VARCHAR2(40) NOT NULL,
MEM_SEX NUMBER(1) NOT NULL,
MEM_BD DATE NOT NULL,
MEM_MAIL VARCHAR2(30) NOT NULL,
MEM_PHONE VARCHAR2(15) NOT NULL,
MEM_ADD VARCHAR2(100),
MEM_PIC BLOB,
REG_TIME TIMESTAMP NOT NULL,
MEM_REP_NO NUMBER(2) NOT NULL,
MEM_STATE VARCHAR2(20) NOT NULL
);

CREATE SEQUENCE mem_seq
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

Insert into MEM 
(MEM_NO, MEM_ID, MEM_PSW, MEM_NAME, MEM_SEX, MEM_BD, MEM_MAIL, MEM_PHONE, MEM_ADD, MEM_PIC ,REG_TIME, MEM_REP_NO, MEM_STATE) 
values ('M'||LPAD(to_char(mem_seq.NEXTVAL), 6, '0'), 'David', '123456', 'seafood', 1, to_date('1985-01-01', 'yyyy-mm-dd'), 'seafood@gmail.com', '0912123123', '桃園市中壢區中大路300號', EMPTY_BLOB(), to_timeStamp('2020-02-21 21:02:44', 'yyyy-mm-dd hh24:mi:ss:ff'), 0, '通過');

Insert into MEM 
(MEM_NO, MEM_ID, MEM_PSW, MEM_NAME, MEM_SEX, MEM_BD, MEM_MAIL, MEM_PHONE, MEM_ADD, MEM_PIC ,REG_TIME, MEM_REP_NO, MEM_STATE) 
values ('M'||LPAD(to_char(mem_seq.NEXTVAL), 6, '0'), 'Philip', '66666', '王大明', 1, to_date('1991-08-01', 'yyyy-mm-dd'), 'bbbyyy123@gmail.com', '0912345678', '新竹市東區食品路41之6號', EMPTY_BLOB(), to_date('2020-02-28 06:02:04', 'yyyy-mm-dd hh24:mi:ss'), 0, '通過');

Insert into MEM 
(MEM_NO, MEM_ID, MEM_PSW, MEM_NAME, MEM_SEX, MEM_BD, MEM_MAIL, MEM_PHONE, MEM_ADD, MEM_PIC ,REG_TIME, MEM_REP_NO, MEM_STATE) 
values ('M'||LPAD(to_char(mem_seq.NEXTVAL), 6, '0'), 'Candy', 'aaa000101', '陳小美', 2, to_date('1995-01-25', 'yyyy-mm-dd'), 'candy0125@yahoo.com.tw', '0966100100', '台北市士林區中山北路10號', EMPTY_BLOB(), to_date('2020-03-21 16:02:40', 'yyyy-mm-dd hh24:mi:ss'), 0, '通過');

Insert into MEM 
(MEM_NO, MEM_ID, MEM_PSW, MEM_NAME, MEM_SEX, MEM_BD, MEM_MAIL, MEM_PHONE, MEM_ADD, MEM_PIC ,REG_TIME, MEM_REP_NO, MEM_STATE) 
values ('M'||LPAD(to_char(mem_seq.NEXTVAL), 6, '0'), 'asssss7', '989898', '林一郎', 1, to_date('1986-10-19', 'yyyy-mm-dd'), 'ass8888@gmail.com', '0988222333', '高雄市前鎮區東十五街15號', EMPTY_BLOB(), to_date('2020-03-23 21:02:44', 'yyyy-mm-dd hh24:mi:ss'), 0, '通過');

Insert into MEM 
(MEM_NO, MEM_ID, MEM_PSW, MEM_NAME, MEM_SEX, MEM_BD, MEM_MAIL, MEM_PHONE, MEM_ADD, MEM_PIC ,REG_TIME, MEM_REP_NO, MEM_STATE) 
values ('M'||LPAD(to_char(mem_seq.NEXTVAL), 6, '0'), 'Denny200', '7799000qaz', '鄭光光', 1, to_date('1985-09-19', 'yyyy-mm-dd'), 'lightlight@gmail.com', '0921555555', '新北市三重區中央北路66號', EMPTY_BLOB(), to_date('2020-05-11 13:23:24', 'yyyy-mm-dd hh24:mi:ss'), 0, '通過');

Insert into MEM 
(MEM_NO, MEM_ID, MEM_PSW, MEM_NAME, MEM_SEX, MEM_BD, MEM_MAIL, MEM_PHONE, MEM_ADD, MEM_PIC ,REG_TIME, MEM_REP_NO, MEM_STATE) 
values ('M'||LPAD(to_char(mem_seq.NEXTVAL), 6, '0'), 'lili1314', '1030220', '顏莉莉', 2, to_date('1996-5-12', 'yyyy-mm-dd'), 'lilili1314@gmail.com', '0966116116', '台南市南區大林路20號', EMPTY_BLOB(), to_date('2020-06-03 17:29:04', 'yyyy-mm-dd hh24:mi:ss'), 1, '通過');

Insert into MEM 
(MEM_NO, MEM_ID, MEM_PSW, MEM_NAME, MEM_SEX, MEM_BD, MEM_MAIL, MEM_PHONE, MEM_ADD, MEM_PIC ,REG_TIME, MEM_REP_NO, MEM_STATE) 
values ('M'||LPAD(to_char(mem_seq.NEXTVAL), 6, '0'), 'stan9999', 'qweasd111', '李丹丹', 1, to_date('1987-4-05', 'yyyy-mm-dd'), 'staaaan000@gmail.com', '0972002002', '桃園市桃園區三民路100號', EMPTY_BLOB(), to_date('2020-09-28 09:39:01', 'yyyy-mm-dd hh24:mi:ss'), 0, '通過');

Insert into MEM 
(MEM_NO, MEM_ID, MEM_PSW, MEM_NAME, MEM_SEX, MEM_BD, MEM_MAIL, MEM_PHONE, MEM_ADD, MEM_PIC ,REG_TIME, MEM_REP_NO, MEM_STATE) 
values ('M'||LPAD(to_char(mem_seq.NEXTVAL), 6, '0'), 'nancy13', '66543', '袁禎禎', 2, to_date('1992-10-13', 'yyyy-mm-dd'), 'nancy1013@gmail.com', '0988222333', '新竹縣竹北市中山一街12號', EMPTY_BLOB(), to_date('2020-10-17 14:02:44', 'yyyy-mm-dd hh24:mi:ss'), 4, '停權');

commit;
----------------------2.好友----------------------------------------------
CREATE TABLE FRIEND(
MEM_NO_A VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL ,
MEM_NO_B VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
constraint PK_FRIEND PRIMARY KEY(MEM_NO_A,MEM_NO_B),
FRI_STATE VARCHAR2(20) NOT NULL
);

Insert into FRIEND 
(MEM_NO_A, MEM_NO_B, FRI_STATE) 
values ('M000001', 'M000002','待通過');

Insert into FRIEND 
(MEM_NO_A, MEM_NO_B, FRI_STATE) 
values ('M000001', 'M000003','通過');

Insert into FRIEND 
(MEM_NO_A, MEM_NO_B, FRI_STATE) 
values ('M000001', 'M000004','不通過');

Insert into FRIEND 
(MEM_NO_A, MEM_NO_B, FRI_STATE) 
values ('M000002', 'M000003','通過');

Insert into FRIEND 
(MEM_NO_A, MEM_NO_B, FRI_STATE) 
values ('M000002', 'M000004','通過');
COMMIT;
----------------------3.管理員-------------------------------------------
CREATE TABLE WEBMASTER(
    WM_NO VARCHAR2(3) PRIMARY KEY NOT NULL,
    WM_NAME VARCHAR2(20) NOT NULL,
    WM_SEX NUMBER(1) NOT NULL,
    WM_ID VARCHAR2(20) NOT NULL,
    WM_PSW VARCHAR2(20) NOT NULL,
    WM_MAIL VARCHAR2(20) NOT NULL,
    OB_DATE DATE NOT NULL,
    DD_DATE DATE,
    REG_TIME TIMESTAMP NOT NULL
);
CREATE SEQUENCE WEBMASTER_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;


INSERT INTO WEBMASTER
(WM_NO, WM_NAME, WM_SEX, WM_ID, WM_PSW, WM_MAIL, OB_DATE, DD_DATE,  REG_TIME ) VALUES
('A'||LPAD(to_char(WEBMASTER_SEQ.NEXTVAL),2,'0'),'Peter', 1, 'Peter1', '123456', 'peter1xxx@gmail.com', 
to_date('2020-02-20', 'yyyy-mm-dd'),null, to_date('2020-02-21 21:02:44', 'yyyy-mm-dd hh24:mi:ss'));


INSERT INTO WEBMASTER
(WM_NO, WM_NAME, WM_SEX, WM_ID, WM_PSW, WM_MAIL, OB_DATE, DD_DATE,  REG_TIME ) VALUES
('A'||LPAD(to_char(WEBMASTER_SEQ.NEXTVAL),2,'0'),'王大蠻', 1, 'daman', 'aaa001001', 'dadaan@gmail.com', 
to_date('2020-08-20', 'yyyy-mm-dd'),null, to_date('2020-08-21 13:04:44', 'yyyy-mm-dd hh24:mi:ss'));
commit;
---------------4.權限功能---------------------------
CREATE TABLE FUNC(
    FC_NO VARCHAR2(3) PRIMARY KEY NOT NULL,
    FC_NAME VARCHAR2(40)
);
CREATE SEQUENCE FUNC_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;


INSERT INTO FUNC
(FC_NO, FC_NAME) VALUES
('F'||LPAD(to_char(FUNC_SEQ.NEXTVAL),2,'0'),'權限管理');

INSERT INTO FUNC
(FC_NO, FC_NAME) VALUES
('F'||LPAD(to_char(FUNC_SEQ.NEXTVAL),2,'0'),'會員管理');

INSERT INTO FUNC
(FC_NO, FC_NAME) VALUES
('F'||LPAD(to_char(FUNC_SEQ.NEXTVAL),2,'0'),'潛店管理');

INSERT INTO FUNC
(FC_NO, FC_NAME) VALUES
('F'||LPAD(to_char(FUNC_SEQ.NEXTVAL),2,'0'),'潛點管理');

INSERT INTO FUNC
(FC_NO, FC_NAME) VALUES
('F'||LPAD(to_char(FUNC_SEQ.NEXTVAL),2,'0'),'檢舉管理');

INSERT INTO FUNC
(FC_NO, FC_NAME) VALUES
('F'||LPAD(to_char(FUNC_SEQ.NEXTVAL),2,'0'),'網站圖片管理');

commit;
----------------5.管理員權限------------------------------------
CREATE TABLE AUTHORITY_MANAGE(
     WM_NO VARCHAR2(3) REFERENCES WEBMASTER(WM_NO) NOT NULL,
     FC_NO VARCHAR2(3) REFERENCES FUNC(FC_NO) NOT NULL, 
     CONSTRAINT PK_AUTHORITY_MANAGE PRIMARY KEY(WM_NO,FC_NO)
);
Insert into AUTHORITY_MANAGE 
(WM_NO, FC_NO) 
values ('A01', 'F01');

Insert into AUTHORITY_MANAGE 
(WM_NO, FC_NO) 
values ('A01', 'F02');

Insert into AUTHORITY_MANAGE 
(WM_NO, FC_NO) 
values ('A01', 'F03');

Insert into AUTHORITY_MANAGE 
(WM_NO, FC_NO) 
values ('A01', 'F04');

Insert into AUTHORITY_MANAGE 
(WM_NO, FC_NO) 
values ('A01', 'F05');

Insert into AUTHORITY_MANAGE 
(WM_NO, FC_NO) 
values ('A01', 'F06');
commit;
-------------------6.網站圖片------------------------
CREATE TABLE WEBPIC(
    WEBPIC_NO VARCHAR2(6) PRIMARY KEY NOT NULL,
    PIC_PURPOSE VARCHAR2(60),
    WEB_PIC BLOB
);

CREATE SEQUENCE WEBPIC_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT INTO WEBPIC
(WEBPIC_NO, PIC_PURPOSE, WEB_PIC) VALUES
('WP'||LPAD(to_char(WEBPIC_SEQ.NEXTVAL),4,'0'),'網站LOGO1', EMPTY_BLOB());

INSERT INTO WEBPIC
(WEBPIC_NO, PIC_PURPOSE, WEB_PIC) VALUES
('WP'||LPAD(to_char(WEBPIC_SEQ.NEXTVAL),4,'0'),'網站LOGO2', EMPTY_BLOB());

INSERT INTO WEBPIC
(WEBPIC_NO, PIC_PURPOSE, WEB_PIC) VALUES
('WP'||LPAD(to_char(WEBPIC_SEQ.NEXTVAL),4,'0'), '首頁上方動態圖片1' , EMPTY_BLOB());

INSERT INTO WEBPIC
(WEBPIC_NO, PIC_PURPOSE, WEB_PIC) VALUES
('WP'||LPAD(to_char(WEBPIC_SEQ.NEXTVAL),4,'0'), '首頁上方動態圖片2', EMPTY_BLOB());

INSERT INTO WEBPIC
(WEBPIC_NO, PIC_PURPOSE, WEB_PIC) VALUES
('WP'||LPAD(to_char(WEBPIC_SEQ.NEXTVAL),4,'0'), '首頁上方動態圖片3', EMPTY_BLOB());

COMMIT;
-----------------7.會員檢舉團主紀錄--------------------------------
CREATE TABLE MRL_RECORD(
    RLR_NO VARCHAR2(7) PRIMARY KEY NOT NULL,
    MEM_NO VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    LEADER_NO VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    REP_TIME TIMESTAMP NOT NULL,
    REP_CONTENT VARCHAR2(300) NOT NULL,
    REP_STATE VARCHAR2(20) NOT NULL
);
CREATE SEQUENCE RLRNO_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;
INSERT INTO MRL_RECORD VALUES('RL'||LPAD(to_char(RLRNO_SEQ.NEXTVAL),5,'0'),'M000001','M000002', to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')),'開團日當天放鴿子','通過');
commit;
-------------------8.潛店----------------------
CREATE TABLE DIVESHOP(
    DS_NO VARCHAR2(6) PRIMARY KEY NULL,
    DS_NAME VARCHAR2(40) NOT NULL,
    BRCID NUMBER(8) NOT NULL,
    TEL VARCHAR2(20) NOT NULL,
    ADDRESS VARCHAR2(90) NOT NULL,
    DSACCOUNT VARCHAR2(20)NOT NULL,
    DSPAW VARCHAR2(20) NOT NULL,
    DSMAIL VARCHAR2(40) NOT NULL,
    DSINFO CLOB NOT NULL,
    DS_LNG VARCHAR2(40) NOT NULL,
    DS_LAT VARCHAR2(40) NOT NULL,
    DS_STATE VARCHAR2(20) NOT NULL,
    DS_ASCORE NUMBER(5),
    DS_REP_NO NUMBER(2)
);

CREATE SEQUENCE DSNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
    
 INSERT INTO DIVESHOP VALUES ('DS'||LPAD(to_char(DSNO_SEQ.NEXTVAL), 4, '0'),'潛水貨艙','12345678','0226533885','台北市八德路4段','divehouse','123456','divehouse@yyy.com','NEREUS 源自希臘神中的海神，作為我們潛水集團的代表品牌，我們不僅僅提供潛水的專業訓練、完整多樣的潛水相關商品以及完整合法有保障的潛水旅遊，更致力於海洋教育及保育活動，讓越來越多的人可以認識海洋喜歡大海學會潛水！','0,0','審核通過',0,0);
    INSERT INTO DIVESHOP VALUES ('DS'||LPAD(to_char(DSNO_SEQ.NEXTVAL), 4, '0'),'潛立方','55432158','0423552208','台中市西屯區河西路69號','divecube','123456','hotel@divecube.com.tw','體驗城市船艙住宿潛立方不僅擁有亞洲最深的深潛池，館內設計別出心裁。從挑高的大廳，到陽光灑落的舒適餐廳，再到潛水商店及客房設計。處處皆以船艙概念做為設計基礎，將每間客房打造成船艙氛圍。全館房型皆為上下鋪設計(單人房除外)，讓旅客做完神秘潛水的沉船探險後，接續這樣的氛圍感受，進入船艙式的住宿體驗。','0,0','審核通過',80,0);
    INSERT INTO DIVESHOP VALUES ('DS'||LPAD(to_char(DSNO_SEQ.NEXTVAL), 4, '0'),'舒服潛水','87651245','088867897','屏東縣恆春鎮大光里大光路89號','cozidive','123456','service@cozidive.com','墾丁地區地處熱帶地區且三面臨海，海洋珊瑚群體繁多，各種海洋生物生態豐富，一直以來是台灣的潛水度假勝地。舒服潛水座落在墾丁國家公園大光社區中心，緊鄰後壁湖海洋生態保護區，便於前往後壁湖潛水，生活機能也較便利，並提供墾丁/恆春地區免費接送服務，讓您假期間盡享休閒娛樂。我們每年採購全新潛水裝備，您不需要擔心有老舊的潛水裝備問題，貼心的使用廂型車輛帶顧客潛水，免除風吹日曬雨淋與貨車後斗顛簸之苦，在舒服潛水，您只需要放空發懶，養好精神再跳下海裡吐泡泡，跟魚兒一起在珊瑚叢裡玩耍，來到舒服潛水就像一個大家庭，在這裡，交到一位好朋友，或認識來自世界各地的潛水員，或是選擇寧靜。','0,0','審核通過',0,0);
    INSERT INTO DIVESHOP VALUES ('DS'||LPAD(to_char(DSNO_SEQ.NEXTVAL), 4, '0'),'藍莎潛水中心','24568132','089671888','台灣綠島鄉公館村72號','bluesafari','123456','info@blue-safari.com.tw','藍莎潛水中心是由一群摯愛海洋的人所組成，同時也是綠島第一家具有PADI國際認證的潛水中心；由於對海洋的熱愛，藍莎潛水中心在2006年6月在這座綠色島嶼上誕生。在這70多坪的空間，我們不但提供休閒潛水觀光，也提供專業技術性潛水課程和教 練課程等， 透過全職的 PADI 潛水教練，提供一系列的 PADI 潛水課程， 更提供 各類型持續進修課程予符合資格的潛水員，包括潛水教練培訓課程、 緊急第一反應 課程，高氧潛水及休閒技術性潛水課程等。','0,0','審核通過',0,0);
    INSERT INTO DIVESHOP VALUES ('DS'||LPAD(to_char(DSNO_SEQ.NEXTVAL), 4, '0'),'資策會潛水中心','88651375','034257387','桃園市中壢區中大路300號','da105g5','123456','service@tibame.com','潛店資訊','0,0','審核通過',00,0);
commit;
-----------------9.潛店檢舉會員紀錄----------------------
CREATE TABLE DSRM_RECORD(
    RDSR_NO VARCHAR2(6) PRIMARY KEY NOT NULL,
    DS_NO VARCHAR2(6) REFERENCES DIVESHOP(DS_NO) NOT NULL,
    MEM_NO VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    REP_TIME TIMESTAMP NOT NULL,
    REP_CONTENT VARCHAR2(300) NOT NULL,
    REP_STATE VARCHAR2(20) NOT NULL
);
CREATE SEQUENCE RDSRNO_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT INTO DSRM_RECORD VALUES ('DSR'||LPAD(to_char(RDSRNO_SEQ.NEXTVAL), 3, '0'),'DS0001','M000001',TO_DATE('2020-01-20','YYYY-MM-DD'),'檢舉內容1','審核中');
INSERT INTO DSRM_RECORD VALUES ('DSR'||LPAD(to_char(RDSRNO_SEQ.NEXTVAL), 3, '0'),'DS0002','M000001',TO_DATE('2020-01-20','YYYY-MM-DD'),'檢舉內容2','審核中');
INSERT INTO DSRM_RECORD VALUES ('DSR'||LPAD(to_char(RDSRNO_SEQ.NEXTVAL), 3, '0'),'DS0003','M000001',TO_DATE('2020-01-20','YYYY-MM-DD'),'檢舉內容3','審核中');
INSERT INTO DSRM_RECORD VALUES ('DSR'||LPAD(to_char(RDSRNO_SEQ.NEXTVAL), 3, '0'),'DS0001','M000001',TO_DATE('2020-01-20','YYYY-MM-DD'),'檢舉內容4','審核中');
INSERT INTO DSRM_RECORD VALUES ('DSR'||LPAD(to_char(RDSRNO_SEQ.NEXTVAL), 3, '0'),'DS0001','M000001',TO_DATE('2020-01-20','YYYY-MM-DD'),'檢舉內容5','審核中');

----------------10.會員檢舉潛店紀錄----------------------
CREATE TABLE MRDS_RECORDS(
    MRDS_NO VARCHAR2(7) PRIMARY KEY NOT NULL,
    MEM_NO VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    DS_NO VARCHAR2(6) REFERENCES DIVESHOP(DS_NO) NOT NULL,
    REP_TIME TIMESTAMP NOT NULL,
    REP_CONTENT VARCHAR2(300) NOT NULL,
    REP_STATE VARCHAR2(20) NOT NULL
);
CREATE SEQUENCE MRDSNO_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT INTO MRDS_RECORDS VALUES ('RD'||LPAD(to_char(MRDSNO_SEQ.NEXTVAL),5,'0'),'M000003','DS0003',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')),'教練長太醜','待審核');
INSERT INTO MRDS_RECORDS VALUES ('RD'||LPAD(to_char(MRDSNO_SEQ.NEXTVAL),5,'0'),'M000006','DS0001',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')),'這不是肯德基','不通過');
COMMIT;
-----------------課程會員檢舉潛店紀錄----------------------
CREATE TABLE LESSON(
    LES_NO VARCHAR2(6) PRIMARY KEY NOT NULL,
    DS_NO VARCHAR2(6) REFERENCES DIVESHOP(DS_NO) NOT NULL,
    DS_NAME VARCHAR2(20) NOT NULL,
    LES_NAME VARCHAR(20) NOT NULL,
    LES_INFO CLOB NOT NULL,
    COACH VARCHAR2(20) NOT NULL,
    COST NUMBER(6) NOT NULL,
    DAYS NUMBER(3) NOT NULL,
    SIGNUP_STARTDATE DATE NOT NULL, 
    SIGNUP_ENDDATE DATE NOT NULL,
    LES_STATE VARCHAR2(20) NOT NULL,
    LES_MAX NUMBER(3) NOT NULL,
    LES_LIMIT NUMBER(3) NOT NULL,
    LES_STARTDATE DATE NOT NULL,
    LES_ENDDATE DATE NOT NULL,
    LESS_STATE VARCHAR2(20) NOT NULL
);

CREATE SEQUENCE LESNO_SEQ
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
    
    INSERT INTO LESSON VALUES ('LE'||LPAD(to_char(LESNO_SEQ.NEXTVAL), 4, '0'),'DS0001','潛水貨艙','OW','潛水入門中最受歡迎的證照，全球已有數百萬人取得PADI開放水域潛水員證照。完成開放水域潛水員訓練，代表俱備基本的潛水知識與技巧，可憑證照至世界各地租借裝備、氣瓶或聘請潛導，進行潛水觀光。訓練期間，最大潛水深度為18公尺，取得證照之後建議不要超過休閒潛水的極限40公尺，也建議經常練習累積潛水經驗，或參與進階課程增加技巧熟練度。','DAVID',19000,5,TO_DATE('2020-3-1','YYYY-MM-DD'),TO_DATE('2020-3-25','YYYY-MM-DD'),'開放報名',8,2,TO_DATE('2020-3-27','YYYY-MM-DD'),TO_DATE('2020-3-30','YYYY-MM-DD'),'上架');
    INSERT INTO LESSON VALUES ('LE'||LPAD(to_char(LESNO_SEQ.NEXTVAL), 4, '0'),'DS0001','潛水貨艙','AOW','想出國/離島旅遊，卻擔心無法參加深潛夜潛，錯過美麗的潛點？那麼應該在台灣完成進階潛水的訓練，而不是到國外才摸索適應喔。進階潛水課程會更加扎實的訓練水底導航能力、如何用電腦錶瞭解潛水計劃，以及深潛、夜潛、船潛等多項潛水專長。用最有效率的方式訓練潛水技巧，省去自己摸索的時間，加深對潛水活動的認知。','PETER',17000,4,TO_DATE('2020-3-15','YYYY-MM-DD'),TO_DATE('2020-3-20','YYYY-MM-DD'),'開放報名',8,2,TO_DATE('2020-3-22','YYYY-MM-DD'),TO_DATE('2020-3-25','YYYY-MM-DD'),'上架');
    INSERT INTO LESSON VALUES ('LE'||LPAD(to_char(LESNO_SEQ.NEXTVAL), 4, '0'),'DS0001','潛水貨艙','FUNDIVE','這是國外對持證潛水員在課堂結束後參與的導潛活動所慣用的說法，跟一般上課下水的「訓練潛水」最大不同的地方，就是在 FunDive 的過程當中不會再訓練額外的潛水技巧，潛水員可以用放鬆的心情來慢慢享受潛水的整個過程，並且透過這種方式，還能夠讓潛水員不斷累積實際的潛水經驗，變成厲害的潛水員喔！','EVIS',9000,1,TO_DATE('2020-03-02','YYYY-MM-DD'),TO_DATE('2020-03-07','YYYY-MM-DD'),'報名中',8,2,TO_DATE('2020-03-13','YYYY-MM-DD'),TO_DATE('2020-03-14','YYYY-MM-DD'),'上架');
    INSERT INTO LESSON VALUES ('LE'||LPAD(to_char(LESNO_SEQ.NEXTVAL), 4, '0'),'DS0001','潛水貨艙','救援潛水員','急救與水域救援的觀念，人人都應該俱備。除了能協助潛伴解除問題，自救更是救援潛水員十分重要的觀念。完整的救援潛水訓練及透過模擬情節練習，讓你在遇到危急狀況時，能冷靜應對並作出正確判斷。這門課程是建立在你已經學過的潛水技巧之上，以學會如何避免問題發生與問題發生後如何處理為基礎，做進一步的延伸。','DAVID',15000,5,TO_DATE('2020-3-10','YYYY-MM-DD'),TO_DATE('2020-03-17','YYYY-MM-DD'),'報名中',8,2,TO_DATE('2020-3-19','YYYY-MM-DD'),TO_DATE('2020-3-23','YYYY-MM-DD'),'上架');
    INSERT INTO LESSON VALUES ('LE'||LPAD(to_char(LESNO_SEQ.NEXTVAL), 4, '0'),'DS0001','潛水貨艙','潛水長','PADI潛水長是邁向潛水領導階級的第一步，無論是想朝潛水教練發展或單純的自我實現，都非常歡迎來參與潛水長養成計劃。潛水長課程採知識、技術訓練與實務操作三方面進行，可以給你更專業及實用的訓練。上完潛水長後你將能帶領潛水旅遊、潛水簡報、評估浪況、了解風險管理，做最安全的潛水規劃，如果對潛水教學有興趣的你，可以考慮繼續朝教練班前進，到達教練之後你可以做潛水教學，發證照，邁進專業!','DAVID',20000,10,TO_DATE('2020-02-28','YYYY-MM-DD'),TO_DATE('2020-03-10','YYYY-MM-DD'),'報名中',8,2,TO_DATE('2020-03-13','YYYY-MM-DD'),TO_DATE('2020-3-16','YYYY-MM-DD'),'上架');
    INSERT INTO LESSON VALUES ('LE'||LPAD(to_char(LESNO_SEQ.NEXTVAL), 4, '0'),'DS0003','舒服潛水','OW','潛水入門中最受歡迎的證照，全球已有數百萬人取得PADI開放水域潛水員證照。完成開放水域潛水員訓練，代表俱備基本的潛水知識與技巧，可憑證照至世界各地租借裝備、氣瓶或聘請潛導，進行潛水觀光。訓練期間，最大潛水深度為18公尺，取得證照之後建議不要超過休閒潛水的極限40公尺，也建議經常練習累積潛水經驗，或參與進階課程增加技巧熟練度。','DAVID',19000,5,TO_DATE('2020-3-1','YYYY-MM-DD'),TO_DATE('2020-3-25','YYYY-MM-DD'),'開放報名',8,2,TO_DATE('2020-3-27','YYYY-MM-DD'),TO_DATE('2020-3-30','YYYY-MM-DD'),'上架');
    INSERT INTO LESSON VALUES ('LE'||LPAD(to_char(LESNO_SEQ.NEXTVAL), 4, '0'),'DS0003','舒服潛水','AOW','想出國/離島旅遊，卻擔心無法參加深潛夜潛，錯過美麗的潛點？那麼應該在台灣完成進階潛水的訓練，而不是到國外才摸索適應喔。進階潛水課程會更加扎實的訓練水底導航能力、如何用電腦錶瞭解潛水計劃，以及深潛、夜潛、船潛等多項潛水專長。用最有效率的方式訓練潛水技巧，省去自己摸索的時間，加深對潛水活動的認知。','PETER',17000,4,TO_DATE('2020-3-15','YYYY-MM-DD'),TO_DATE('2020-3-20','YYYY-MM-DD'),'開放報名',8,2,TO_DATE('2020-3-22','YYYY-MM-DD'),TO_DATE('2020-3-25','YYYY-MM-DD'),'上架');
    INSERT INTO LESSON VALUES ('LE'||LPAD(to_char(LESNO_SEQ.NEXTVAL), 4, '0'),'DS0003','舒服潛水','FUNDIVE','這是國外對持證潛水員在課堂結束後參與的導潛活動所慣用的說法，跟一般上課下水的「訓練潛水」最大不同的地方，就是在 FunDive 的過程當中不會再訓練額外的潛水技巧，潛水員可以用放鬆的心情來慢慢享受潛水的整個過程，並且透過這種方式，還能夠讓潛水員不斷累積實際的潛水經驗，變成厲害的潛水員喔！','EVIS',9000,1,TO_DATE('2020-03-02','YYYY-MM-DD'),TO_DATE('2020-03-07','YYYY-MM-DD'),'報名中',8,2,TO_DATE('2020-03-13','YYYY-MM-DD'),TO_DATE('2020-03-14','YYYY-MM-DD'),'上架');
    INSERT INTO LESSON VALUES ('LE'||LPAD(to_char(LESNO_SEQ.NEXTVAL), 4, '0'),'DS0003','舒服潛水','救援潛水員','急救與水域救援的觀念，人人都應該俱備。除了能協助潛伴解除問題，自救更是救援潛水員十分重要的觀念。完整的救援潛水訓練及透過模擬情節練習，讓你在遇到危急狀況時，能冷靜應對並作出正確判斷。這門課程是建立在你已經學過的潛水技巧之上，以學會如何避免問題發生與問題發生後如何處理為基礎，做進一步的延伸。','DAVID',15000,5,TO_DATE('2020-3-10','YYYY-MM-DD'),TO_DATE('2020-03-17','YYYY-MM-DD'),'報名中',8,2,TO_DATE('2020-3-19','YYYY-MM-DD'),TO_DATE('2020-3-23','YYYY-MM-DD'),'上架');
    INSERT INTO LESSON VALUES ('LE'||LPAD(to_char(LESNO_SEQ.NEXTVAL), 4, '0'),'DS0003','舒服潛水','潛水長','PADI潛水長是邁向潛水領導階級的第一步，無論是想朝潛水教練發展或單純的自我實現，都非常歡迎來參與潛水長養成計劃。潛水長課程採知識、技術訓練與實務操作三方面進行，可以給你更專業及實用的訓練。上完潛水長後你將能帶領潛水旅遊、潛水簡報、評估浪況、了解風險管理，做最安全的潛水規劃，如果對潛水教學有興趣的你，可以考慮繼續朝教練班前進，到達教練之後你可以做潛水教學，發證照，邁進專業!','DAVID',20000,10,TO_DATE('2020-02-28','YYYY-MM-DD'),TO_DATE('2020-03-10','YYYY-MM-DD'),'報名中',8,2,TO_DATE('2020-03-13','YYYY-MM-DD'),TO_DATE('2020-3-16','YYYY-MM-DD'),'上架');
    
commit;
-------------------課程訂單----------------------
CREATE TABLE LESSON_ORDER(
    LES_O_NO VARCHAR2(13) PRIMARY KEY NOT NULL,
    MEM_NO VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    LES_NO VARCHAR2(6) REFERENCES LESSON(LES_NO) NOT NULL,
    MEM_NAME VARCHAR2(20) NOT NULL,
    LES_NAME VARCHAR2(20) NOT NULL,
    COST NUMBER(6) NOT NULL,
    COACH VARCHAR2(20) NOT NULL,
    LO_STATE VARCHAR2(20) NOT NULL
);

CREATE SEQUENCE LESSONOD_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;
    
INSERT INTO LESSON_ORDER
VALUES ('L'||to_char(sysdate,'yyyymmdd')||'-'||LPAD(to_char(LESSONOD_SEQ.NEXTVAL), 3, '0'),'M000001','LE0004','seafood','救援潛水員',15000,'DAVID','已付款');
INSERT INTO LESSON_ORDER
VALUES ('L'||to_char(sysdate,'yyyymmdd')||'-'||LPAD(to_char(LESSONOD_SEQ.NEXTVAL), 3, '0'),'M000001','LE0003','seafood','FUNDIVE',9000,'EVIS','已付款');
INSERT INTO LESSON_ORDER
VALUES ('L'||to_char(sysdate,'yyyymmdd')||'-'||LPAD(to_char(LESSONOD_SEQ.NEXTVAL), 3, '0'),'M000001','LE0005','seafood','潛水長',20000,'DAVID','已付款');
commit;
-------------------潛店圖片----------------------
CREATE TABLE DSPIC(
    DPIC_SEQ VARCHAR2(6) PRIMARY KEY NOT NULL,
    DS_NO VARCHAR2(6) REFERENCES DIVESHOP(DS_NO),
    DPIC BLOB NOT NULL,
    DPIC_NAME VARCHAR2(20) NOT NULL
);
CREATE SEQUENCE DPIC_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT INTO DSPIC VALUES ('DSP'||LPAD(to_char(DPIC_SEQ.NEXTVAL), 3, '0'),'DS0001',EMPTY_BLOB(),'潛店照片1');
INSERT INTO DSPIC VALUES ('DSP'||LPAD(to_char(DPIC_SEQ.NEXTVAL), 3, '0'),'DS0001',EMPTY_BLOB(),'潛店照片2');
INSERT INTO DSPIC VALUES ('DSP'||LPAD(to_char(DPIC_SEQ.NEXTVAL), 3, '0'),'DS0001',EMPTY_BLOB(),'潛店照片3');
INSERT INTO DSPIC VALUES ('DSP'||LPAD(to_char(DPIC_SEQ.NEXTVAL), 3, '0'),'DS0001',EMPTY_BLOB(),'潛店照片4');
INSERT INTO DSPIC VALUES ('DSP'||LPAD(to_char(DPIC_SEQ.NEXTVAL), 3, '0'),'DS0001',EMPTY_BLOB(),'潛店照片5');
commit;
-------------------課程圖片----------------------
CREATE TABLE LESPIC(
    LPIC_SEQ VARCHAR2(6) PRIMARY KEY NOT NULL,
    LES_NO VARCHAR2(6) REFERENCES LESSON(LES_NO) NOT NULL,
    LPIC BLOB NOT NULL,
    LPIC_NAME VARCHAR2(20) NOT NULL
);
CREATE SEQUENCE LPIC_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT INTO LESPIC VALUES ('LSP'||LPAD(to_char(LPIC_SEQ.NEXTVAL), 3, '0'),'LE0001',EMPTY_BLOB(),'課程照片');
INSERT INTO LESPIC VALUES ('LSP'||LPAD(to_char(LPIC_SEQ.NEXTVAL), 3, '0'),'LE0002',EMPTY_BLOB(),'課程照片');
INSERT INTO LESPIC VALUES ('LSP'||LPAD(to_char(LPIC_SEQ.NEXTVAL), 3, '0'),'LE0003',EMPTY_BLOB(),'課程照片');
INSERT INTO LESPIC VALUES ('LSP'||LPAD(to_char(LPIC_SEQ.NEXTVAL), 3, '0'),'LE0004',EMPTY_BLOB(),'課程照片');
INSERT INTO LESPIC VALUES ('LSP'||LPAD(to_char(LPIC_SEQ.NEXTVAL), 3, '0'),'LE0005',EMPTY_BLOB(),'課程照片');
COMMIT;
------------------潛店評分-------------
CREATE TABLE DS_SCOM(
    DSC_SEQ VARCHAR2(6) PRIMARY KEY NOT NULL,
    MEM_NO VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    DS_NO VARCHAR2(6) REFERENCES DIVESHOP(DS_NO) NOT NULL,
    DS_SCORE NUMBER(5),
    DS_COM CLOB
);

CREATE SEQUENCE DSC_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT INTO DS_SCOM VALUES ('DCM'||LPAD(to_char(DSC_SEQ.NEXTVAL), 3, '0'),'M000001','DS0001',5,EMPTY_CLOB());
INSERT INTO DS_SCOM VALUES ('DCM'||LPAD(to_char(DSC_SEQ.NEXTVAL), 3, '0'),'M000002','DS0001',6,EMPTY_CLOB());
INSERT INTO DS_SCOM VALUES ('DCM'||LPAD(to_char(DSC_SEQ.NEXTVAL), 3, '0'),'M000003','DS0001',7,EMPTY_CLOB());

COMMIT;

COMMIT;
------------------租賃訂單--------------
CREATE TABLE R_ORDER(
    RO_NO VARCHAR2(13) PRIMARY KEY NOT NULL,
    DS_NO VARCHAR2(6) REFERENCES DIVESHOP(DS_NO) NOT NULL,
    MEM_NO VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    TEPC NUMBER(3) NOT NULL,
    TPRIZ NUMBER(10) NOT NULL,
    OP_STATE VARCHAR2(20) NOT NULL,
    RS_DATE DATE NOT NULL,
    RD_DATE DATE NOT NULL,
    RR_DATE DATE,
    FFINE NUMBER(10),
    O_STATE VARCHAR2(20) NOT NULL,
    O_NOTE VARCHAR2(300)
);
CREATE SEQUENCE RO_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT INTO R_ORDER(RO_NO,DS_NO,MEM_NO,TEPC,TPRIZ,OP_STATE,RS_DATE,RD_DATE,O_STATE)
VALUES(('O'||to_char(SYSDATE,'yyyymmdd')||'-'||LPAD(to_char(RO_SEQ.NEXTVAL),3,'0')),'DS0001','M000001',2,1400,'未付款',TO_DATE(SYSDATE,'YYYY-MM-DD'),TO_DATE(SYSDATE,'YYYY-MM-DD'),'未歸還');

INSERT INTO R_ORDER(RO_NO,DS_NO,MEM_NO,TEPC,TPRIZ,OP_STATE,RS_DATE,RD_DATE,O_STATE)
VALUES(('O'||to_char(SYSDATE,'yyyymmdd')||'-'||LPAD(to_char(RO_SEQ.NEXTVAL),3,'0')),'DS0001','M000002',3,5000,'未付款',TO_DATE(SYSDATE,'YYYY-MM-DD'),TO_DATE(SYSDATE,'YYYY-MM-DD'),'未歸還');

COMMIT;
------------------裝備分類------------------
CREATE TABLE EQUIPC(
    EPC_NO VARCHAR2(20) PRIMARY KEY NOT NULL,
    EPC_NAME VARCHAR2(20) NOT NULL
);
INSERT INTO EQUIPC (EPC_NO,EPC_NAME) VALUES('EQAH','蜂鳴器');
INSERT INTO EQUIPC (EPC_NO,EPC_NAME) VALUES('EQB','定位轉盤');
INSERT INTO EQUIPC (EPC_NO,EPC_NAME) VALUES('EQBCD','浮力調整背心');
INSERT INTO EQUIPC (EPC_NO,EPC_NAME) VALUES('EQDB','潛水鞋');
INSERT INTO EQUIPC (EPC_NO,EPC_NAME) VALUES('EQDK','潛水刀');
INSERT INTO EQUIPC (EPC_NO,EPC_NAME) VALUES('EQDT','潛水表');
INSERT INTO EQUIPC (EPC_NO,EPC_NAME) VALUES('EQDW','防寒衣');
INSERT INTO EQUIPC (EPC_NO,EPC_NAME) VALUES('EQF','蛙鞋');
INSERT INTO EQUIPC (EPC_NO,EPC_NAME) VALUES('EQM','面鏡');
INSERT INTO EQUIPC (EPC_NO,EPC_NAME) VALUES('EQSG','魚槍');
INSERT INTO EQUIPC (EPC_NO,EPC_NAME) VALUES('EQSGS','潛水手套');
INSERT INTO EQUIPC (EPC_NO,EPC_NAME) VALUES('EQST','氣瓶');
INSERT INTO EQUIPC (EPC_NO,EPC_NAME) VALUES('EQQRB','快卸扣');
INSERT INTO EQUIPC (EPC_NO,EPC_NAME) VALUES('EQUR','水中通訊器');
INSERT INTO EQUIPC (EPC_NO,EPC_NAME) VALUES('EQW','鉛塊');
COMMIT;
------------------裝備------------------
CREATE TABLE EQUIP(
    EP_SEQ VARCHAR2(6) PRIMARY KEY NOT NULL,
    EPC_NO VARCHAR2(5) REFERENCES EQUIPC(EPC_NO) NOT NULL,
    DS_NO VARCHAR2(6) REFERENCES DIVESHOP(DS_NO) NOT NULL,
    DS_NAME VARCHAR2(20) NOT NULL,
    EP_NAME VARCHAR2(20) NOT NULL,
    EP_NO VARCHAR2(20) NOT NULL,
    EP_SIZE VARCHAR2(20) NOT NULL,
    EP_PRIZ NUMBER(10) NOT NULL,
    EP_RP NUMBER(10) NOT NULL,
    EP_STATE VARCHAR2(20) NOT NULL,
    EPR_STATE VARCHAR2(20) NOT NULL,
    EPS_STATE VARCHAR2(20) NOT NULL
);
CREATE SEQUENCE EP_SEQ
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOCYCLE
NOCACHE;
-----------------------------------------手套---------------------------------------------------------
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQSGS','DS0001','潛水貨艙','GA手套','G0001','S',2000,300,'良好','在店','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQSGS','DS0001','潛水貨艙','GC手套','G0002','M',2500,350,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQSGS','DS0001','潛水貨艙','GD手套','G0003','L',3500,400,'良好','在店','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQSGS','DS0001','潛水貨艙','GE手套','G0004','M',4000,450,'良好','在店','上架');

-----------------------------------------乾式防寒衣--------------------------------------------------------
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQDW','DS0001','潛水貨艙','DA防寒衣','DC0001','S',7000,800,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQDW','DS0001','潛水貨艙','DA防寒衣','DC0002','M',7000,800,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQDW','DS0001','潛水貨艙','DC防寒衣','DC0003','M',8000,1000,'修繕中','在店','下架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQDW','DS0001','潛水貨艙','DD防寒衣','DC0004','L',9000,1200,'損壞','在店','下架');

-----------------------------------------濕式防寒衣--------------------------------------------------------
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQDW','DS0001','潛水貨艙','WB防寒衣','WC0001','S',10000,1800,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQDW','DS0001','潛水貨艙','WD防寒衣','WC0002','M',12000,2000,'修繕中','在店','下架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQDW','DS0001','潛水貨艙','WC防寒衣','WC0003','M',10000,1800,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQDW','DS0001','潛水貨艙','WA防寒衣','WC0004','L',9000,800,'良好','出租','上架');

-----------------------------------------面鏡--------------------------------------------------------
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQM','DS0001','潛水貨艙','A面鏡','MA0001','F',2000,300,'損壞','在店','下架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQM','DS0001','潛水貨艙','A面鏡','MA0002','F',2000,300,'修繕中','在店','下架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQM','DS0001','潛水貨艙','A面鏡','MA0003','F',2000,300,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQM','DS0001','潛水貨艙','B面鏡','MB0004','F',2500,500,'良好','出租','上架');

-----------------------------------------潛水鞋--------------------------------------------------------
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQDB','DS0001','潛水貨艙','A潛水鞋','DBA0001','M',2500,500,'修繕中','在店','下架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQDB','DS0001','潛水貨艙','A潛水鞋','DBA0002','M',2500,500,'修繕中','在店','下架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQDB','DS0001','潛水貨艙','B潛水鞋','DBB0003','L',3000,800,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQDB','DS0001','潛水貨艙','B潛水鞋','DBB0004','L',3000,800,'良好','出租','上架');

-----------------------------------------潛水刀--------------------------------------------------------
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQDK','DS0001','潛水貨艙','A潛水刀','DKA0001','M',4000,1000,'良好','在店','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQDK','DS0001','潛水貨艙','A潛水刀','DKB0002','M',4500,1500,'良好','在店','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQDK','DS0001','潛水貨艙','B潛水刀','DKB0003','L',4500,1500,'良好','在店','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQDK','DS0001','潛水貨艙','C潛水刀','DKC0004','L',5000,2000,'良好','在店','上架');

-----------------------------------------氣瓶--------------------------------------------------------
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQST','DS0001','潛水貨艙','A氣瓶','STA0001','M',3000,600,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQST','DS0001','潛水貨艙','B氣瓶','STB0002','M',3500,700,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQST','DS0001','潛水貨艙','B氣瓶','STB0003','L',3500,700,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQST','DS0001','潛水貨艙','C氣瓶','STC0004','L',4000,800,'良好','出租','上架');

-----------------------------------------BCD--------------------------------------------------------
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQBCD','DS0001','潛水貨艙','BCDA','BCDA01','M',9000,2000,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQBCD','DS0001','潛水貨艙','BCDB','BCDB02','M',13000,3500,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQBCD','DS0001','潛水貨艙','BCDB','BCDB03','L',13000,3500,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQBCD','DS0001','潛水貨艙','BCDD','BCDD04','L',15000,4000,'良好','出租','上架');

-----------------------------------------鉛塊--------------------------------------------------------
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQW','DS0001','潛水貨艙','鉛塊1','WA0001','1',800,100,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQW','DS0001','潛水貨艙','鉛塊2','WB0002','2',1000,200,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQW','DS0001','潛水貨艙','鉛塊2','WB0003','2',1000,200,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQW','DS0001','潛水貨艙','鉛塊3','WCC0004','3',1200,250,'良好','出租','上架');

-----------------------------------------定位盤--------------------------------------------------------
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQB','DS0001','潛水貨艙','A定位轉盤','BA0001','F',3000,500,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQB','DS0001','潛水貨艙','B定位轉盤','BB0002','F',4000,800,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQB','DS0001','潛水貨艙','B定位轉盤','BB0003','F',4000,800,'良好','出租','上架');
INSERT INTO EQUIP(EP_SEQ,EPC_NO,DS_NO,DS_NAME,EP_NAME,EP_NO,EP_SIZE,EP_PRIZ,EP_RP,EP_STATE,EPR_STATE,EPS_STATE)
VALUES(('EP'||LPAD(to_char(EP_SEQ.NEXTVAL),4,'0')),'EQB','DS0001','潛水貨艙','C定位轉盤','BC0004','F',5000,800,'良好','出租','上架');

COMMIT;
------------------租賃訂單明細------------------
CREATE TABLE RO_DETAIL(
    RO_NO VARCHAR2(13) REFERENCES R_ORDER(RO_NO) NOT NULL,
    EP_SEQ VARCHAR2(6) REFERENCES EQUIP(EP_SEQ) NOT NULL,
    CONSTRAINT PK_RO_DETAIL PRIMARY KEY(RO_NO,EP_SEQ),
    EP_CRP NUMBER(10) NOT NULL
);
INSERT INTO RO_DETAIL (RO_NO,EP_SEQ,EP_CRP)
VALUES('O20200205-001','EP0003',400);
INSERT INTO RO_DETAIL (RO_NO,EP_SEQ,EP_CRP)
VALUES('O20200205-001','EP0021',1000);
INSERT INTO RO_DETAIL (RO_NO,EP_SEQ,EP_CRP)
VALUES('O20200205-002','EP0032',4000);
INSERT INTO RO_DETAIL (RO_NO,EP_SEQ,EP_CRP)
VALUES('O20200205-002','EP0035',200);
INSERT INTO RO_DETAIL (RO_NO,EP_SEQ,EP_CRP)
VALUES('O20200205-002','EP0040',800);
COMMIT;
--------------------裝備圖片------------------
CREATE TABLE EQPIC(
    EPIC_SEQ VARCHAR2(6) PRIMARY KEY NOT NULL,
    DS_NO VARCHAR2(6) REFERENCES DIVESHOP(DS_NO) NOT NULL,
    EP_SEQ VARCHAR2(6) REFERENCES EQUIP(EP_SEQ) NOT NULL,
    EPIC BLOB
);

CREATE SEQUENCE EPIC_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT INTO EQPIC VALUES ('EPP'||LPAD(to_char(EPIC_SEQ.NEXTVAL), 3, '0'),'DS0001','EP0001',EMPTY_BLOB());
INSERT INTO EQPIC VALUES ('EPP'||LPAD(to_char(EPIC_SEQ.NEXTVAL), 3, '0'),'DS0001','EP0001',EMPTY_BLOB());
INSERT INTO EQPIC VALUES ('EPP'||LPAD(to_char(EPIC_SEQ.NEXTVAL), 3, '0'),'DS0001','EP0001',EMPTY_BLOB());

COMMIT;
------------------討論區------------------
CREATE TABLE FORUM(
    FRM_NO VARCHAR2(4) PRIMARY KEY NOT NULL,
    FRM_NAME VARCHAR2(20) NOT NULL
);
CREATE SEQUENCE FORUM_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT INTO FORUM VALUES('FR07','新手');
INSERT INTO FORUM VALUES('FR08','日誌');
INSERT INTO FORUM VALUES('FR09','環境保護');
commit;
------------------會員文章--------------
CREATE TABLE MEM_POST(
    MPO_NO VARCHAR2(8) PRIMARY KEY NOT NULL,
    MEM_NO VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    FRM_NO VARCHAR2(4) REFERENCES FORUM(FRM_NO) NOT NULL,
    PO_TSTAMP TIMESTAMP NOT NULL,
    STATUS VARCHAR2(8)NOT NULL,
    PO_CONT CLOB NOT NULL,
    RES_NO_LIST CLOB,
    PO_PIC1 BLOB,
    PO_PIC2 BLOB,
    PO_PIC3 BLOB,
    PO_PIC4 BLOB,
    PO_PIC5 BLOB,
    PO_PIC6 BLOB

);
--增加文章狀態status 20200202
CREATE SEQUENCE MPO_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT into	MEM_POST	values	(	'MP'||LPAD(to_char(MPO_SEQ.NEXTVAL),6,'0'),	'M000001',	'FR07',	to_timestamp(to_char(systimestamp, 'yyyymmddhh24miss'),'yyyymmddhh24miss'),'vis',	to_clob('新手版測試文章  by m000001'),	NULL,	NULL,	EMPTY_BLOB(),	NULL,	NULL,	NULL,	NULL	);
INSERT into	MEM_POST	values	(	'MP'||LPAD(to_char(MPO_SEQ.NEXTVAL),6,'0'),	'M000001',	'FR08',	to_timestamp(to_char(systimestamp, 'yyyymmddhh24miss'),'yyyymmddhh24miss'),'vis',	to_clob('日誌版測試文章  by m000001'),	NULL,	NULL,	EMPTY_BLOB(),	NULL,	NULL,	NULL,	NULL	);
INSERT into	MEM_POST	values	(	'MP'||LPAD(to_char(MPO_SEQ.NEXTVAL),6,'0'),	'M000001',	'FR09',	to_timestamp(to_char(systimestamp, 'yyyymmddhh24miss'),'yyyymmddhh24miss'),	'vis',to_clob('環保版測試文章  by m000001'),	NULL,	NULL,	EMPTY_BLOB(),	NULL,	NULL,	NULL,	NULL	);
INSERT into	MEM_POST	values	(	'MP'||LPAD(to_char(MPO_SEQ.NEXTVAL),6,'0'),	'M000002',	'FR07',	to_timestamp(to_char(systimestamp, 'yyyymmddhh24miss'),'yyyymmddhh24miss'),	'vis',to_clob('新手版測試文章  by m000002'),	NULL,	NULL,	EMPTY_BLOB(),	NULL,	NULL,	NULL,	NULL	);
INSERT into	MEM_POST	values	(	'MP'||LPAD(to_char(MPO_SEQ.NEXTVAL),6,'0'),	'M000002',	'FR08',	to_timestamp(to_char(systimestamp, 'yyyymmddhh24miss'),'yyyymmddhh24miss'),	'vis',to_clob('日誌版測試文章  by m000002'),	NULL,	NULL,	EMPTY_BLOB(),	NULL,	NULL,	NULL,	NULL	);
INSERT into	MEM_POST	values	(	'MP'||LPAD(to_char(MPO_SEQ.NEXTVAL),6,'0'),	'M000002',	'FR09',	to_timestamp(to_char(systimestamp, 'yyyymmddhh24miss'),'yyyymmddhh24miss'),	'vis',to_clob('環保版測試文章  by m000002'),	NULL,	NULL,	EMPTY_BLOB(),	NULL,	NULL,	NULL,	NULL	);
INSERT into	MEM_POST	values	(	'MP'||LPAD(to_char(MPO_SEQ.NEXTVAL),6,'0'),	'M000003',	'FR07',	to_timestamp(to_char(systimestamp, 'yyyymmddhh24miss'),'yyyymmddhh24miss'),	'vis',to_clob('新手版測試文章  by m000003'),	NULL,	NULL,	EMPTY_BLOB(),	NULL,	NULL,	NULL,	NULL	);
INSERT into	MEM_POST	values	(	'MP'||LPAD(to_char(MPO_SEQ.NEXTVAL),6,'0'),	'M000003',	'FR07',	to_timestamp(to_char(systimestamp, 'yyyymmddhh24miss'),'yyyymmddhh24miss'),'vis',	to_clob('新手版測試文章  by m000003'),	NULL,	NULL,	EMPTY_BLOB(),	NULL,	NULL,	NULL,	NULL	);
commit;
--------------會員文章追蹤紀錄--------------
CREATE TABLE MAT_RECORD(
    MEM_NO VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    MPO_NO VARCHAR2(8) REFERENCES MEM_POST(MPO_NO) NOT NULL,
    CONSTRAINT PK_MAT_RECORD PRIMARY KEY(MEM_NO,MPO_NO),
    TRAC_TIME TIMESTAMP NOT NULL
);
insert into MAT_RECORD(mem_no,mpo_no,trac_time)values('M000002','MP000001',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')));
insert into MAT_RECORD(mem_no,mpo_no,trac_time)values('M000002','MP000002',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')));
insert into MAT_RECORD(mem_no,mpo_no,trac_time)values('M000002','MP000003',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')));
commit;
--------------會員檢舉文章紀錄--------------
CREATE TABLE MRA_RECORD(
    RAR_NO VARCHAR2(7) PRIMARY KEY NOT NULL,
    MEM_NO VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    MPO_NO VARCHAR2(8) REFERENCES MEM_POST(MPO_NO) NOT NULL,
    REP_TIME TIMESTAMP NOT NULL,
    REP_CONTENT VARCHAR2(300) NOT NULL,
    REP_STATE VARCHAR2(20) NOT NULL
);
CREATE SEQUENCE RARNO_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT INTO MRA_RECORD(RAR_NO, MEM_NO, MPO_NO, REP_TIME, REP_CONTENT, REP_STATE)
VALUES('RA'||LPAD(to_char(RARNO_SEQ.NEXTVAL),5,'0'),'M000003','MP000004',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')),'政治文與標題不符合','通過');

INSERT INTO MRA_RECORD(RAR_NO, MEM_NO, MPO_NO, REP_TIME, REP_CONTENT, REP_STATE)
VALUES('RA'||LPAD(to_char(RARNO_SEQ.NEXTVAL),5,'0'),'M000004','MP000005',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')),'我看不順眼','不通過');
COMMIT;
--------------回覆文章--------------
CREATE TABLE POST_COMMENT(
    RES_NO VARCHAR2(8) PRIMARY KEY NOT NULL,
    MPO_NO VARCHAR2(8) REFERENCES MEM_POST(MPO_NO) NOT NULL,
    MEM_NO VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    RES_TSTAMP TIMESTAMP NOT NULL,
    STATUS  VARCHAR2(8) NOT NULL,
    RES_CONT CLOB NOT NULL
);

CREATE SEQUENCE POCMT_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

----增加第1筆回文
INSERT into	POST_COMMENT values	( 'RE'||LPAD(to_char(POCMT_SEQ.NEXTVAL),6,'0'),
    'MP000001',
    'M000003',
    to_timestamp(to_char(systimestamp,'yyyymmddhh24miss'),'yyyymmddhh24miss'),
    'vis',
    to_clob('對M000001的文章編號MP000001的回覆 by M000003')	);
    ----更新對應文章的回文列表欄位
UPDATE MEM_post set RES_NO_LIST = 
to_clob('RE000001') 
WHERE mpo_no='MP000001';
----增加第2筆回文
INSERT into	POST_COMMENT values	( 'RE'||LPAD(to_char(POCMT_SEQ.NEXTVAL),6,'0'),
    'MP000002',
    'M000003',
    to_timestamp(to_char(systimestamp,'yyyymmddhh24miss'),'yyyymmddhh24miss'),
    'vis',
    to_clob('對M000001的文章編號MP000002的回覆 by M000003')	);
    ----更新對應文章的回文列表欄位
UPDATE MEM_post set RES_NO_LIST = 
to_clob('RE000002') 
WHERE mpo_no='MP000002';
----增加第3筆回文
INSERT into	POST_COMMENT values	( 'RE'||LPAD(to_char(POCMT_SEQ.NEXTVAL),6,'0'),
    'MP000003',
    'M000003',
    to_timestamp(to_char(systimestamp,'yyyymmddhh24miss'),'yyyymmddhh24miss'),
    'vis',
    to_clob('對M000001的文章編號MP000003的回覆 by M000003')	);
    ----更新對應文章的回文列表欄位
UPDATE MEM_post set RES_NO_LIST = 
to_clob('RE000003') 
WHERE mpo_no='MP000003';
----增加第4筆回文
INSERT into	POST_COMMENT values	( 'RE'||LPAD(to_char(POCMT_SEQ.NEXTVAL),6,'0'),
    'MP000004',
    'M000003',
    to_timestamp(to_char(systimestamp,'yyyymmddhh24miss'),'yyyymmddhh24miss'),
    'vis',
    to_clob('對M000002的文章編號MP000004的回覆 by M000003')	);
    ----更新對應文章的回文列表欄位
UPDATE MEM_post set RES_NO_LIST = 
to_clob('RE000004') 
WHERE mpo_no='MP000004';
----增加第5筆回文
INSERT into	POST_COMMENT values	( 'RE'||LPAD(to_char(POCMT_SEQ.NEXTVAL),6,'0'),
    'MP000007',
    'M000003',
    to_timestamp(to_char(systimestamp,'yyyymmddhh24miss'),'yyyymmddhh24miss'),
    'vis',
    to_clob('對M000003的文章編號MP000007的回覆 by M000003')	);
    ----更新對應文章的回文列表欄位
UPDATE MEM_post set RES_NO_LIST = 
to_clob('RE000005') 
WHERE mpo_no='MP000007';
commit;
-----------------會員潛店追蹤紀錄---------------------
CREATE TABLE MDST_RECORD(
    MEM_NO VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    DS_NO VARCHAR2(6) REFERENCES DIVESHOP(DS_NO) NOT NULL,
    CONSTRAINT PK_MDST_RECORD PRIMARY KEY(MEM_NO,DS_NO),
    TRAC_TIME TIMESTAMP NOT NULL
);
insert into MDST_RECORD VALUES ('M000001','DS0005',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')));
insert into MDST_RECORD VALUES ('M000001','DS0003',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')));
insert into MDST_RECORD VALUES ('M000002','DS0002',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')));
insert into MDST_RECORD VALUES ('M000002','DS0001',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')));
insert into MDST_RECORD VALUES ('M000002','DS0003',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')));
insert into MDST_RECORD VALUES ('M000004','DS0005',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')));
insert into MDST_RECORD VALUES ('M000005','DS0005',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')));
insert into MDST_RECORD VALUES ('M000005','DS0001',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')));
insert into MDST_RECORD VALUES ('M000006','DS0005',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')));
insert into MDST_RECORD VALUES ('M000007','DS0004',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')));
insert into MDST_RECORD VALUES ('M000007','DS0001',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')));
COMMIT;
---------------------地區----------------------
CREATE TABLE LOCALE(
    LOC_NO VARCHAR2(9) PRIMARY KEY NOT NULL,
    CTRY VARCHAR2(3) ,
    SUB_REGION VARCHAR2(60) ,
    WEATHER CLOB NOT NULL    
);
--SUB_REGION請按當國氣象海域劃分輸入
--CTRY 按ISO3166-1三位標準碼填入
CREATE SEQUENCE LOC_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','彭佳嶼基隆海面',	'empty_CLOB()'	);
INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','釣魚台海面',	'empty_CLOB()'	);
INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','新竹鹿港沿海',	'empty_CLOB()'	);
INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','鹿港東石沿海',	'empty_CLOB()'	);
INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','東石安平沿海',	'empty_CLOB()'	);
INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','安平高雄沿海',	'empty_CLOB()'	);
INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','高雄枋寮沿海',	'empty_CLOB()'	);
INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','枋寮恆春沿海',	'empty_CLOB()'	);
INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','鵝鑾鼻沿海',	'empty_CLOB()'	);
INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','成功台東沿海',	'empty_CLOB()'	);
INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','台東大武沿海',	'empty_CLOB()'	);
INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','綠島蘭嶼海面',	'empty_CLOB()'	);
INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','花蓮沿海',	'empty_CLOB()'	);
INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','宜蘭蘇澳沿海',	'empty_CLOB()'	);
INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','澎湖海面',	'empty_CLOB()'	);
INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','馬祖海面',	'empty_CLOB()'	);
INSERT into	LOCALE	 (LOC_NO,CTRY,SUB_REGION,	WEATHER	)values	    (	'LOC'||LPAD(TO_CHAR(LOC_SEQ.NEXTVAL),6,0),'TWN','金門海面',	'empty_CLOB()'	);

commit;
-----------------潛點------------------
CREATE TABLE DIVE_POINT(
    DP_NO VARCHAR2(8) PRIMARY KEY NOT NULL,
    LOC_NO VARCHAR2(9) REFERENCES LOCALE(LOC_NO) NOT NULL,
    DP_NAME VARCHAR2(40) NOT NULL,
    DP_LAT NUMBER(20,5) NOT NULL,
    DP_LNG NUMBER(20,5) NOT NULL,
    DP_INFO VARCHAR2(300) NOT NULL,
    DP_PIC1 BLOB NOT NULL,
    DP_PIC2 BLOB ,
    DP_PIC3 BLOB ,
    DP_PIC4 BLOB 
);
CREATE SEQUENCE DP_seq
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT into	DIVE_POINT (DP_NO,	LOC_NO,	DP_NAME,	DP_LAT,	DP_LNG,	DP_INFO,	DP_PIC1	)
	values	(   'DP'||LPAD(to_char(DP_SEQ.NEXTVAL),6,'0'),	'LOC000012',    'Artificial Reefs by Steel',        22.6365,121.48467,      'CMAS ** / AOW',    EMPTY_BLOB());
INSERT into	DIVE_POINT (DP_NO,	LOC_NO,	DP_NAME,	DP_LAT,	DP_LNG,	DP_INFO,	DP_PIC1	)
	values	(	'DP'||LPAD(to_char(DP_SEQ.NEXTVAL),6,'0'),	'LOC000012',	'Artificial Reefs by Wire Pole',	22.65167,	121.4715,	'All divers',	    EMPTY_BLOB());
INSERT into	DIVE_POINT (DP_NO,	LOC_NO,	DP_NAME,	DP_LAT,	DP_LNG,	DP_INFO,	DP_PIC1	)
	values	(	'DP'||LPAD(to_char(DP_SEQ.NEXTVAL),6,'0'),	'LOC000012',	'Big Mushroom',	                    22.65683,	121.472,	'All divers',	    EMPTY_BLOB());
INSERT into	DIVE_POINT (DP_NO,	LOC_NO,	DP_NAME,	DP_LAT,	DP_LNG,	DP_INFO,	DP_PIC1	)
	values	(	'DP'||LPAD(to_char(DP_SEQ.NEXTVAL),6,'0'),	'LOC000012',	'Gateway Rock',	                    22.6825,	121.51183,	'CMAS ** / AOW',	EMPTY_BLOB());
INSERT into	DIVE_POINT (DP_NO,	LOC_NO,	DP_NAME,	DP_LAT,	DP_LNG,	DP_INFO,	DP_PIC1	)
	values	(	'DP'||LPAD(to_char(DP_SEQ.NEXTVAL),6,'0'),	'LOC000012',	'Great White Sand',	                22.639,	    121.49233,  'CMAS * / OW',	    EMPTY_BLOB());
INSERT into	DIVE_POINT (DP_NO,	LOC_NO,	DP_NAME,	DP_LAT,	DP_LNG,	DP_INFO,	DP_PIC1	)
	values	(	'DP'||LPAD(to_char(DP_SEQ.NEXTVAL),6,'0'),	'LOC000012',	'Shih-Lang',	                    22.65467,	121.474,	'All divers',	    EMPTY_BLOB());
INSERT into	DIVE_POINT (DP_NO,	LOC_NO,	DP_NAME,	DP_LAT,	DP_LNG,	DP_INFO,	DP_PIC1	)
	values	(	'DP'||LPAD(to_char(DP_SEQ.NEXTVAL),6,'0'),	'LOC000007',	'TURTLE CAVERN',	                22.35267,	120.3645,	'CMAS ** / AOW',	EMPTY_BLOB());
commit;
---------------會員潛點追蹤紀錄----------------
CREATE TABLE MDPST_RECORD(
    MEM_NO VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    DP_NO  VARCHAR2(13) REFERENCES DIVE_POINT(DP_NO) NOT NULL,
    CONSTRAINT PK_MDPST_RECORD PRIMARY KEY(MEM_NO,DP_NO),
    TRAC_TIME TIMESTAMP NOT NULL
);

Insert into MDPST_RECORD(mem_no,dp_no,trac_time) VALUES ('M000002','DP000001',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')));
Insert into MDPST_RECORD(mem_no,dp_no,trac_time) VALUES ('M000001','DP000003',to_timestamp(to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ss')));
Commit;
--------------會員跟會員聊天-----------------
CREATE TABLE MEM_CHAT(
    MEM_CHT_NO VARCHAR2(10) PRIMARY KEY NOT NULL,
    MEM_NO_A VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    MEM_NO_B VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    INIT_CONT CLOB,
    INIT_UPDATE TIMESTAMP,
    RES_UPDATE TIMESTAMP
);

--------------會員跟潛店聊天-----------------
CREATE TABLE SHOP_CHAT(
    SHOP_CHT_NO VARCHAR2(10) PRIMARY KEY NOT NULL,
    MEM_NO VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    DS_NO VARCHAR2(6) REFERENCES DIVESHOP(DS_NO) NOT NULL,
    MEM_CH_REC CLOB,
    INIT_UPD TIMESTAMP,
    RES_UPD TIMESTAMP
);
COMMIT;
------------------------------------------揪團建表------------------------------------------------------
CREATE TABLE ACT_LIST(
	ACT_LIST_NO VARCHAR2(15) PRIMARY KEY NOT NULL,
	MEM_NO 	VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
	DP_NO 	VARCHAR2(13) REFERENCES DIVE_POINT(DP_NO) NOT NULL,
	START_DATE DATE NOT NULL,
	DUAL_DATE 	DATE NOT NULL,
	ACTION_DATE 	DATE NOT NULL,
   	ACT_STATE 	VARCHAR2(20) NOT NULL,
    LOCALE 	VARCHAR2(100) NOT NULL,
	ACT_MAX 	NUMBER(2) NOT NULL,
	ACT_MIN 	NUMBER(2) NOT NULL,
   	 GP_PIC 	BLOB NOT NULL,
	GP_INFO 	VARCHAR2(500) NOT NULL,
	GP_DAYS 	NUMBER(2) NOT NULL
);

CREATE SEQUENCE act_seq
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT INTO act_list (ACT_LIST_NO,MEM_NO,DP_NO,START_DATE,DUAL_DATE,ACTION_DATE,ACT_STATE,LOCALE,ACT_MAX,ACT_MIN,GP_PIC,GP_INFO,GP_DAYS)
VALUES ('ACT_LIST_'||LPAD(to_char(act_seq.NEXTVAL), 4, '0'),'M000001','DP000001',to_date('2020-02-01', 'yyyy-mm-dd'),to_date('2020-02-03', 'yyyy-mm-dd'),
to_date('2020-02-04', 'yyyy-mm-dd'),'發團中','桃園市中壢區中大路31號',10,2,EMPTY_BLOB(),'很好玩喔!!!!!',5);

INSERT INTO act_list (ACT_LIST_NO,MEM_NO,DP_NO,START_DATE,DUAL_DATE,ACTION_DATE,ACT_STATE,LOCALE,ACT_MAX,ACT_MIN,GP_PIC,GP_INFO,GP_DAYS)
VALUES ('ACT_LIST_'||LPAD(to_char(act_seq.NEXTVAL), 4, '0'),'M000002','DP000002',to_date('2020-02-01', 'yyyy-mm-dd'),to_date('2020-02-05', 'yyyy-mm-dd'),
to_date('2020-02-09', 'yyyy-mm-dd'),'發團中','桃園市中壢區中大路32號',8,2,EMPTY_BLOB(),'玩起來',4);

INSERT INTO act_list (ACT_LIST_NO,MEM_NO,DP_NO,START_DATE,DUAL_DATE,ACTION_DATE,ACT_STATE,LOCALE,ACT_MAX,ACT_MIN,GP_PIC,GP_INFO,GP_DAYS)
VALUES ('ACT_LIST_'||LPAD(to_char(act_seq.NEXTVAL), 4, '0'),'M000003','DP000003',to_date('2020-01-03', 'yyyy-mm-dd'),to_date('2020-01-06', 'yyyy-mm-dd'),
to_date('2020-01-07', 'yyyy-mm-dd'),'已開團','桃園市中壢區中大路33號',10,2,EMPTY_BLOB(),'走起!!!!!',2);

INSERT INTO act_list (ACT_LIST_NO,MEM_NO,DP_NO,START_DATE,DUAL_DATE,ACTION_DATE,ACT_STATE,LOCALE,ACT_MAX,ACT_MIN,GP_PIC,GP_INFO,GP_DAYS)
VALUES ('ACT_LIST_'||LPAD(to_char(act_seq.NEXTVAL), 4, '0'),'M000004','DP000004',to_date('2020-02-01', 'yyyy-mm-dd'),to_date('2020-02-02', 'yyyy-mm-dd'),
to_date('2020-02-03', 'yyyy-mm-dd'),'發團中','桃園市中壢區中大路34號',12,2,EMPTY_BLOB(),'董ㄟ~很好玩喔!!!!!',1);

INSERT INTO act_list (ACT_LIST_NO,MEM_NO,DP_NO,START_DATE,DUAL_DATE,ACTION_DATE,ACT_STATE,LOCALE,ACT_MAX,ACT_MIN,GP_PIC,GP_INFO,GP_DAYS)
VALUES ('ACT_LIST_'||LPAD(to_char(act_seq.NEXTVAL), 4, '0'),'M000005','DP000005',to_date('2020-01-10', 'yyyy-mm-dd'),to_date('2020-01-12', 'yyyy-mm-dd'),
to_date('2020-01-13', 'yyyy-mm-dd'),'已開團','桃園市中壢區中大路35號',6,2,EMPTY_BLOB(),'還不快來玩一玩',3);
COMMIT;
------------------------報名明細------------------------------
CREATE TABLE AGP_LIST(
	ACT_LIST_NO VARCHAR2(15) REFERENCES ACT_LIST(ACT_LIST_NO) NOT NULL,
	MEM_NO VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
	CONSTRAINT PK_AGP_LIST PRIMARY KEY(ACT_LIST_NO,MEM_NO),
	MEM_LIC VARCHAR2(15) NOT NULL,
	ACT_NUM NUMBER(2) NOT NULL,
   	 MEM_LIC_PIC BLOB NOT NULL,
	AGP_STATE VARCHAR2(20) NOT NULL
);
CREATE SEQUENCE agp_seq
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT INTO AGP_LIST(ACT_LIST_NO,MEM_NO,MEM_LIC,ACT_NUM,MEM_LIC_PIC,AGP_STATE)
VALUES ('ACT_LIST_0001','M000001','P2-18'||LPAD(to_char(agp_seq.NEXTVAL), 4, '0')
,1,EMPTY_BLOB(),'待審核');

INSERT INTO AGP_LIST(ACT_LIST_NO,MEM_NO,MEM_LIC,ACT_NUM,MEM_LIC_PIC,AGP_STATE)
VALUES ('ACT_LIST_0001','M000002','P2-18'||LPAD(to_char(agp_seq.NEXTVAL), 4, '0')
,1,EMPTY_BLOB(),'待審核');

INSERT INTO AGP_LIST(ACT_LIST_NO,MEM_NO,MEM_LIC,ACT_NUM,MEM_LIC_PIC,AGP_STATE)
VALUES ('ACT_LIST_0001','M000003','P2-18'||LPAD(to_char(agp_seq.NEXTVAL), 4, '0')
,1,EMPTY_BLOB(),'通過');

INSERT INTO AGP_LIST(ACT_LIST_NO,MEM_NO,MEM_LIC,ACT_NUM,MEM_LIC_PIC,AGP_STATE)
VALUES ('ACT_LIST_0001','M000004','P2-18'||LPAD(to_char(agp_seq.NEXTVAL), 4, '0')
,2,EMPTY_BLOB(),'待審核');

INSERT INTO AGP_LIST(ACT_LIST_NO,MEM_NO,MEM_LIC,ACT_NUM,MEM_LIC_PIC,AGP_STATE)
VALUES ('ACT_LIST_0001','M000005','P2-18'||LPAD(to_char(agp_seq.NEXTVAL), 4, '0')
,1,EMPTY_BLOB(),'不通過');
COMMIT;
-----------------------揪團裝備---------------------
CREATE TABLE GP_EPT(
	GP_EPT_NO VARCHAR2(15) PRIMARY KEY NOT NULL,
	ACT_LIST_NO VARCHAR2(15) REFERENCES ACT_LIST(ACT_LIST_NO) NOT NULL,
	MEM_NO VARCHAR2(7) REFERENCES MEM(MEM_NO) NOT NULL,
    	EPC_NO VARCHAR2(20) REFERENCES EQUIPC(EPC_NO)NOT NULL,
	GP_T_NUM NUMBER(10) NOT NULL
);
COMMIT;
CREATE SEQUENCE gp_seq
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT INTO GP_EPT(GP_EPT_NO,ACT_LIST_NO,MEM_NO,EPC_NO,GP_T_NUM)
VALUES ('GP_EPT_'||LPAD(to_char(gp_seq.NEXTVAL), 4, '0'),'ACT_LIST_0001','M000001','EQAH',1);
COMMIT;


