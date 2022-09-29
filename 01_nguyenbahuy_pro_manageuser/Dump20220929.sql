-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: 01_nguyenbahuy_manageruser
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `mst_group`
--

DROP TABLE IF EXISTS `mst_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_group` (
  `GROUP_ID` int NOT NULL,
  `GROUP_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_group`
--

LOCK TABLES `mst_group` WRITE;
/*!40000 ALTER TABLE `mst_group` DISABLE KEYS */;
INSERT INTO `mst_group` VALUES (1,'Phòng phát triển số 1'),(2,'Phòng phát triển số 2'),(3,'Phòng phát triển số 3'),(4,'Phòng phát triển số 4');
/*!40000 ALTER TABLE `mst_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_japan`
--

DROP TABLE IF EXISTS `mst_japan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_japan` (
  `CODE_LEVEL` varchar(15) NOT NULL,
  `NAME_LEVEL` varchar(255) NOT NULL,
  PRIMARY KEY (`CODE_LEVEL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_japan`
--

LOCK TABLES `mst_japan` WRITE;
/*!40000 ALTER TABLE `mst_japan` DISABLE KEYS */;
INSERT INTO `mst_japan` VALUES ('N1','Trình độ tiếng nhật cấp 1'),('N2','Trình độ tiếng nhật cấp 2'),('N3','Trình độ tiếng nhật cấp 3'),('N4','Trình độ tiếng nhật cấp 4'),('N5','Trình độ tiếng nhật cấp 5');
/*!40000 ALTER TABLE `mst_japan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_detail_user_japan`
--

DROP TABLE IF EXISTS `tbl_detail_user_japan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_detail_user_japan` (
  `DETAIL_USER_JAPAN_ID` int NOT NULL AUTO_INCREMENT,
  `USER_ID` int NOT NULL,
  `CODE_LEVEL` varchar(15) NOT NULL,
  `START_DATE` date NOT NULL,
  `END_DATE` date NOT NULL,
  `TOTAL` int NOT NULL,
  PRIMARY KEY (`DETAIL_USER_JAPAN_ID`),
  KEY `USER_ID` (`USER_ID`),
  KEY `CODE_LEVEL` (`CODE_LEVEL`),
  CONSTRAINT `tbl_detail_user_japan_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `tbl_user` (`USER_ID`),
  CONSTRAINT `tbl_detail_user_japan_ibfk_2` FOREIGN KEY (`CODE_LEVEL`) REFERENCES `mst_japan` (`CODE_LEVEL`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_detail_user_japan`
--

LOCK TABLES `tbl_detail_user_japan` WRITE;
/*!40000 ALTER TABLE `tbl_detail_user_japan` DISABLE KEYS */;
INSERT INTO `tbl_detail_user_japan` VALUES (1,1,'N5','2005-07-08','2006-07-08',90),(2,4,'N1','2005-05-20','2006-05-20',100),(3,2,'N5','2005-03-08','2006-07-02',80),(4,3,'N1','2005-04-20','2006-05-20',120),(5,5,'N5','2005-06-08','2006-06-08',50),(6,6,'N1','2005-03-02','2006-03-20',60),(7,25,'N1','2020-06-08','2023-06-08',123),(9,27,'N1','2022-06-09','2023-06-09',12322),(11,29,'N2','2002-06-09','2022-06-09',159),(13,26,'N5','2022-06-24','2023-06-24',112),(14,35,'N1','2022-06-28','2023-06-28',12),(15,41,'N1','2022-06-28','2023-06-28',12);
/*!40000 ALTER TABLE `tbl_detail_user_japan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user`
--

DROP TABLE IF EXISTS `tbl_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_user` (
  `USER_ID` int NOT NULL AUTO_INCREMENT,
  `GROUP_ID` int DEFAULT NULL,
  `LOGIN_NAME` varchar(15) NOT NULL,
  `PASSWORD` varchar(50) NOT NULL,
  `FULL_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FULL_NAME_KANA` varchar(255) NOT NULL,
  `EMAIL` varchar(255) NOT NULL,
  `TEL` varchar(15) NOT NULL,
  `BIRTHDAY` date NOT NULL,
  `RULE` int NOT NULL,
  `SALT` varchar(255) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  KEY `GROUP_ID` (`GROUP_ID`),
  CONSTRAINT `tbl_user_ibfk_1` FOREIGN KEY (`GROUP_ID`) REFERENCES `mst_group` (`GROUP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user`
--

LOCK TABLES `tbl_user` WRITE;
/*!40000 ALTER TABLE `tbl_user` DISABLE KEYS */;
INSERT INTO `tbl_user` VALUES (1,1,'ntmhuong','123456','Nguyễn Thị Mai Hương','Hương Kana','huong@gmail.com','123456789','1983-07-08',0,'abcxyz1'),(2,1,'hieudt','123456','Đoàn Trọng Hiếu','Hiếu Kana','hieu@gmali.com	','987456123','1983-08-08',1,'abcxyz2'),(3,2,'	longth','123456','Trần Hoàng Long','Long Kana','long@gmail.com	','654123789','1983-09-08',1,'abcxyz3'),(4,2,'	dungdv	','123456','Đỗ Văn Dũng','Dũng Kana','dung@gmail.com','789123456','0983-10-08',1,'abcxyz4'),(5,3,'phuongnv','123456','Nguyễn Việt Phương','Phương Kana	','phuong@gmail.com','456987213','1983-11-08',1,'abcxyz5'),(6,2,'nthuy','123456','Nguyễn van Huy','huy Kana','huy@gmail.com','234569871','1983-07-07',0,'abcxyz6'),(7,1,'hoangnt','123456','Nguyễn Trọng hoàng','hoàng Kana','hoang@gmali.com	','236598741','1983-11-08',1,'abcxyz2'),(8,1,'nthung','123456','Nguyễn van hung','hung Kana','hung@gmail.com','52698663','1983-07-15',0,'abcxyz6'),(9,1,'thuctv','123456','trần văn thức','thức Kana','thuc@gmali.com	','563214789','1983-12-08',1,'abcxyz2'),(10,2,'	thienvv','123456','trần văn thiện','thiện Kana','thien@gmail.com	','567894123','1983-12-09',1,'abcxyz3'),(11,3,'hung11','786582d2dcd7358057f89b74cc8c7f5de24e635f','nguyễn bá hung','hung Kana','hung@gmail.com','256336363','2000-11-02',0,'abcxyz0'),(12,2,'hang12','abc','nguyen thị hang','hang kana','hang@gmail.com','256988888','2002-12-14',1,'abcxxxx'),(13,1,'long111','aaaaaccc','nguyen thanh long','long kana','long@gmail.com','125836932','1999-10-22',1,'abcxyzsss'),(14,1,'huong11','abcss','nguyen thi huong','huonga kana','huon11@gmail.com','222222333','1999-05-23',1,'abcxyzzz'),(15,3,'taiabc','abcxxx','nguyen van tai','taidaica kana','taidaica@gmail.com','159753852','2003-06-18',1,'abcsssxx'),(16,2,'huong','xyzsss','nguyen thi ha','huong kana','huongaaa@gmail.com','258369174','2006-11-16',1,'abcxesd'),(17,2,'hass','xsesxx','nguyen thu hun','hana kana','hana@gmail.com','268138256','2001-11-20',1,'abcxyxx'),(18,3,'thoan','aaaasc','trinh thi thoan','thoan kana','thoan@gmail.com','259368146','2002-03-23',1,'xyiskmf'),(19,1,'hue','abchuy','nguyen thi huee','hue kana','hueee@gmail.com','789236514','2008-07-09',1,'abcyxxx'),(20,2,'huyyyy12','1234','nksyfhs','adahsd','gfgas','1351561','2000-11-12',1,'abc'),(22,1,'huy1236789','8c14b5dbb7ac4f6a33348652c0248c2a7a4b14af','nguyennguyenbahuy','','huynguyen7119@gmail.com','0975-1825-9512','2004-06-08',1,'2022-06-08T05:19:41.700559100'),(23,1,'hffjg43456','e0dda55398d6393bb2480f08da5d2ae9bef59b01','nguyễn bá huy','ホイ','huynguygdfdf9@gmail.com','0975-1825-9512','2005-06-08',1,'20220608T154359764165300'),(25,1,'hu64623aaaaa','99468e61848c6f181e54381b01b535b4f0f249a6','nguyễn bá huy','ホイ','huynguy64341199@gmail.com','0975-1825-9512','2004-06-08',1,'20220608T154717572819400'),(26,1,'huyfsdf456','69fb832f5bda8ffebd8f36ca05cef54305b68419','nguyễn bá huy','ホイ','huyngdf1199@gmail.com','0975-1825-9512','2004-06-09',1,'20220609T165433174016200'),(27,1,'huyadgdfgdaaaaa','c44a1cf594ec0d79c48feb8bb78cda46ba1cf45c','nguyễn bá huy','ホイ','huyngugdfg1199@gmail.com','0975-1825-9512','2004-06-09',1,'20220609T165557258674600'),(28,1,'huyagdf456','2262e9ea906f802cbffcd320349cc152472b0ab3','nguyễn bá huy','ホイ','huyggs199@gmail.com','0975-1825-9512','2004-06-09',1,'20220609T165832362175900'),(29,1,'huyagsfsd56','fb93d8ca2e7c6a9599687bf4c2369068c1cad912','nguyễn bá huy','ホイ','huyngudsfsd9@gmail.com','0975-1825-9512','2003-06-09',1,'20220609T165940401454100'),(33,2,'huyadqw','adasd','nguyenbahuyhuy','ホイ','huynghfgf99@gmail.com','0975-1864-9432','2015-10-13',1,'20220613T164044814010500'),(34,3,'huyhf','fdfsdfs','huy adak','ホイ','huyngfdfd9@gmail.com','0975-1864-9432','2015-10-13',1,'20220613T16404481432105003'),(35,1,'hunghuy','fghfs','huy adha','ホイ','huyngfdfd9@gmail.com','0975-1582-9432','2012-10-13',1,'20220613T164044814gfdg105003'),(36,2,'fgdgd','gdfgdfgfd','wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww','ホイ','huyngfdfd9@gmail.com','0975-1582-9432','2015-10-13',1,'20220613T164044814gfdg105003'),(37,3,'fgdgddfds','gdfgdfgfd','huyfsa%%','ホイ','huyngfdfd9@gmail.com','0975-1582-9432','2015-10-13',1,'20220613T164044814gfdg105003'),(38,1,'fgdgddf159','gdfgdfgfd','huythoána_','ホイ','huyngfdfd9@gmail.com','0975-1582-9432','2015-10-13',1,'20220613T164044814gfdg105003'),(41,1,'huythien123','e1f9a6544967e391b5403617e1ca8a8a7d3ce27b','nguyễn thien huy','ホイ','huythien1171199@gmail.com','0975-1825-9512','2005-06-28',1,'20220627T174937851814600');
/*!40000 ALTER TABLE `tbl_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-29 22:10:59
