----最後更新時間：20200129 18:10 by 鎮仰
----SELECT 'DROP TABLE ',table_name,'" CASCADE CONSTRAINTS;' FROM user_tables;
----SELECT 'DROP SEQUENCE',sequence_name,';' FROM user_sequences;

CLEAR SCREEN;

----1.DROP SEQUENCE先--共25個
DROP SEQUENCE	WEBMASTER_SEQ	;
DROP SEQUENCE	FUNC_SEQ	;
DROP SEQUENCE	WEBPIC_SEQ	;
DROP SEQUENCE	FORUM_SEQ	;
DROP SEQUENCE	DSNO_SEQ	;
DROP SEQUENCE	MEM_SEQ		;
DROP SEQUENCE	LESNO_SEQ	;
DROP SEQUENCE	LESSONOD_SEQ	;
DROP SEQUENCE	DPIC_SEQ	;
DROP SEQUENCE	LPIC_SEQ	;
DROP SEQUENCE	EPIC_SEQ	;
DROP SEQUENCE	DSC_SEQ	;
DROP SEQUENCE	RDSRNO_SEQ	;
DROP SEQUENCE 	MRDSNO_SEQ	;
DROP SEQUENCE 	RLRNO_SEQ	;
DROP SEQUENCE	DP_SEQ		;
DROP SEQUENCE	MPO_SEQ		;
DROP SEQUENCE	POCMT_SEQ	;
DROP SEQUENCE	RARNO_SEQ	;
DROP SEQUENCE	ACT_SEQ 	;
DROP SEQUENCE	AGP_SEQ 	;
DROP SEQUENCE	GP_SEQ	;
DROP SEQUENCE	EP_SEQ	;
DROP SEQUENCE	RO_SEQ	;
DROP SEQUENCE   LOC_SEQ;

----2.緊佐咧 DROP TABLE CONSTRAINT共34張;
DROP TABLE "GP_EPT" CASCADE CONSTRAINTS;
DROP TABLE "MEM" CASCADE CONSTRAINTS;
DROP TABLE "FRIEND" CASCADE CONSTRAINTS;
DROP TABLE "WEBMASTER" CASCADE CONSTRAINTS;
DROP TABLE "AUTHORITY_MANAGE" CASCADE CONSTRAINTS;
DROP TABLE "WEBPIC" CASCADE CONSTRAINTS;
DROP TABLE "MRL_RECORD" CASCADE CONSTRAINTS;
DROP TABLE "DIVESHOP" CASCADE CONSTRAINTS;
DROP TABLE "DSRM_RECORD" CASCADE CONSTRAINTS;
DROP TABLE "MRDS_RECORDS" CASCADE CONSTRAINTS;
DROP TABLE "LESSON" CASCADE CONSTRAINTS;
DROP TABLE "LESSON_ORDER" CASCADE CONSTRAINTS;
DROP TABLE "DSPIC" CASCADE CONSTRAINTS;
DROP TABLE "LESPIC" CASCADE CONSTRAINTS;
DROP TABLE "DS_SCOM" CASCADE CONSTRAINTS;
DROP TABLE "R_ORDER" CASCADE CONSTRAINTS;
DROP TABLE "EQUIPC" CASCADE CONSTRAINTS;
DROP TABLE "EQUIP" CASCADE CONSTRAINTS;
DROP TABLE "RO_DETAIL" CASCADE CONSTRAINTS;
DROP TABLE "EQPIC" CASCADE CONSTRAINTS;
DROP TABLE "FORUM" CASCADE CONSTRAINTS;
DROP TABLE "MEM_POST" CASCADE CONSTRAINTS;
DROP TABLE "MAT_RECORD" CASCADE CONSTRAINTS;
DROP TABLE "MRA_RECORD" CASCADE CONSTRAINTS;
DROP TABLE "POST_COMMENT" CASCADE CONSTRAINTS;
DROP TABLE "MDST_RECORD" CASCADE CONSTRAINTS;
DROP TABLE "LOCALE" CASCADE CONSTRAINTS;
DROP TABLE "DIVE_POINT" CASCADE CONSTRAINTS;
DROP TABLE "MDPST_RECORD" CASCADE CONSTRAINTS;
DROP TABLE "MEM_CHAT" CASCADE CONSTRAINTS;
DROP TABLE "SHOP_CHAT" CASCADE CONSTRAINTS;
DROP TABLE "ACT_LIST" CASCADE CONSTRAINTS;
DROP TABLE "AGP_LIST" CASCADE CONSTRAINTS;
DROP TABLE "FUNC" CASCADE CONSTRAINTS;

