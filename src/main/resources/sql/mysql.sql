-- MySQL dump 10.13  Distrib 5.7.30, for osx10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: fast_admin
-- ------------------------------------------------------
-- Server version	5.7.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `QRTZ_BLOB_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_BLOB_TRIGGERS`
--


--
-- Table structure for table `QRTZ_CALENDARS`
--

DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(190) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CALENDARS`
--


--
-- Table structure for table `QRTZ_CRON_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CRON_TRIGGERS`
--

INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('fastAdminScheduler','TASK_1','DEFAULT','0 * * * * ?','Asia/Tokyo');

--
-- Table structure for table `QRTZ_FIRED_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(190) DEFAULT NULL,
  `JOB_GROUP` varchar(190) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_FIRED_TRIGGERS`
--


--
-- Table structure for table `QRTZ_JOB_DETAILS`
--

DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_JOB_DETAILS`
--

INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('fastAdminScheduler','TASK_1','DEFAULT',NULL,'pers.syq.fastadmin.backstage.schedule.ScheduleJob','0','0','0','0',_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0JOB_KEYsr\08pers.syq.fastadmin.backstage.entity.SysScheduleJobEntity\0\0\0\0\0\0\0\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0idt\0Ljava/lang/Long;L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statust\0Ljava/lang/Integer;xpt\0testTasksr\0cn.hutool.core.date.DateTimeµì˚\≈\ƒ\‰\0Z\0mutableL\0firstDayOfWeekt\0\ZLcn/hutool/core/date/Week;L\0timeZonet\0Ljava/util/TimeZone;xr\0java.util.DatehjÅKYt\0\0xpw\0\0{êñ\Ô\ƒx~r\0cn.hutool.core.date.Week\0\0\0\0\0\0\0\0\0\0xr\0java.lang.Enum\0\0\0\0\0\0\0\0\0\0xpt\0MONDAYsr\0\Zsun.util.calendar.ZoneInfo$\—\”\Œ\0qõ\0I\0checksumI\0\ndstSavingsI\0	rawOffsetI\0\rrawOffsetDiffZ\0willGMTOffsetChange[\0offsetst\0[I[\0simpleTimeZoneParamsq\0~\0[\0transitionst\0[Jxr\0java.util.TimeZone1≥\ÈıwD¨°\0L\0IDq\0~\0	xpt\0\nAsia/TokyoWZ\¬\Ô\0\0\0\0\ÓbÄ\0\0\0\0\0ur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\ÓbÄ%Q\0\06\ÓÄpur\0[Jx µ±uì\0\0xp\0\0\0\nˇ\ﬂ\⁄\‡¿\0\0ˇˆ£òX\0!ˇˆ7ru\0\0ˇˆx\…$X\0!ˇˆ¨õ\"\0\0ˇˆ˘5∫ò\0!ˇ˜!\√\œ\0\0ˇ˜n^gò\0!ˇ˜ñ\Ï|\0\0\0ƒè\√\ÿ\0\0t\00 0 12 * * ?sr\0java.lang.Long;ã\‰êÃè#\ﬂ\0J\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0\0\0\0\0t\01111t\0testsr\0java.lang.Integer‚†§˜Åá8\0I\0valuexq\0~\0$\0\0\0\0x\0');

--
-- Table structure for table `QRTZ_LOCKS`
--

DROP TABLE IF EXISTS `QRTZ_LOCKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_LOCKS`
--

INSERT INTO `QRTZ_LOCKS` VALUES ('fastAdminScheduler','TRIGGER_ACCESS');

--
-- Table structure for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_PAUSED_TRIGGER_GRPS`
--


--
-- Table structure for table `QRTZ_SCHEDULER_STATE`
--

DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SCHEDULER_STATE`
--


--
-- Table structure for table `QRTZ_SIMPLE_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPLE_TRIGGERS`
--

INSERT INTO `QRTZ_SIMPLE_TRIGGERS` VALUES ('fastAdminScheduler','MT_16j6li7758dpi','DEFAULT',0,0,0);

--
-- Table structure for table `QRTZ_SIMPROP_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPROP_TRIGGERS`
--


--
-- Table structure for table `QRTZ_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_TRIGGERS`
--

INSERT INTO `QRTZ_TRIGGERS` VALUES ('fastAdminScheduler','MT_16j6li7758dpi','DEFAULT','TASK_1','DEFAULT',NULL,1630380210160,-1,5,'PAUSED','SIMPLE',1630380210160,0,NULL,0,_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0JOB_KEYsr\08pers.syq.fastadmin.backstage.entity.SysScheduleJobEntity\0\0\0\0\0\0\0\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0idt\0Ljava/lang/Long;L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statust\0Ljava/lang/Integer;xpt\0testTasksr\0java.util.DatehjÅKYt\0\0xpw\0\0{êñ\ÔÄxt\00 * * * * ?sr\0java.lang.Long;ã\‰êÃè#\ﬂ\0J\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0\0\0\0\0t\01111t\0testsr\0java.lang.Integer‚†§˜Åá8\0I\0valuexq\0~\0\0\0\0\0x\0'),('fastAdminScheduler','TASK_1','DEFAULT','TASK_1','DEFAULT',NULL,1634276100000,1634276040000,5,'WAITING','CRON',1630218416000,0,NULL,2,_binary '¨\Ì\0sr\0org.quartz.JobDataMapü∞ÉËø©∞\À\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMapÇ\Ë\√˚\≈](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\Ê.≠(v\n\Œ\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0JOB_KEYsr\08pers.syq.fastadmin.backstage.entity.SysScheduleJobEntity\0\0\0\0\0\0\0\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0idt\0Ljava/lang/Long;L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statust\0Ljava/lang/Integer;xpt\0testTasksr\0java.util.DatehjÅKYt\0\0xpw\0\0{êñ\ÔÄxt\00 * * * * ?sr\0java.lang.Long;ã\‰êÃè#\ﬂ\0J\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0\0\0\0\0t\01111t\0testsr\0java.lang.Integer‚†§˜Åá8\0I\0valuexq\0~\0\0\0\0x\0');

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT 'Áî®Êà∑Âêç',
  `operation` varchar(50) DEFAULT NULL COMMENT 'Áî®Êà∑Êìç‰Ωú',
  `method` varchar(200) DEFAULT NULL COMMENT 'ËØ∑Ê±ÇÊñπÊ≥ï',
  `params` varchar(5000) DEFAULT NULL COMMENT 'ËØ∑Ê±ÇÂèÇÊï∞',
  `time` bigint(20) NOT NULL COMMENT 'ÊâßË°åÊó∂Èïø(ÊØ´Áßí)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IPÂú∞ÂùÄ',
  `create_time` datetime DEFAULT NULL COMMENT 'ÂàõÂª∫Êó∂Èó¥',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=294 DEFAULT CHARSET=utf8mb4 COMMENT='Á≥ªÁªüÊó•Âøó';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--


--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT 'Áà∂ËèúÂçïIDÔºå‰∏ÄÁ∫ßËèúÂçï‰∏∫0',
  `name` varchar(50) DEFAULT NULL COMMENT 'ËèúÂçïÂêçÁß∞',
  `path` varchar(200) DEFAULT NULL COMMENT 'ËèúÂçïURL',
  `perms` varchar(500) DEFAULT NULL COMMENT 'ÊéàÊùÉ(Â§ö‰∏™Áî®ÈÄóÂè∑ÂàÜÈöîÔºåÂ¶ÇÔºöuser:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT 'Á±ªÂûã   0ÔºöÁõÆÂΩï   1ÔºöËèúÂçï   2ÔºöÊåâÈíÆ',
  `icon` varchar(50) DEFAULT NULL COMMENT 'ËèúÂçïÂõæÊ†á',
  `order_num` int(11) DEFAULT NULL COMMENT 'ÊéíÂ∫è',
  `hidden` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COMMENT='ËèúÂçïÁÆ°ÁêÜ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

INSERT INTO `sys_menu` VALUES (1,0,'System','/sys',NULL,0,'sys',0,0),(2,1,'User','/sys/user',NULL,1,'user',0,0),(3,1,'Role','/sys/role',NULL,1,'role',1,0),(4,1,'Menu','/sys/menu',NULL,1,'menu',0,0),(5,2,'read',NULL,'sys:user:page,sys:user:get',2,NULL,0,1),(6,2,'create',NULL,'sys:user:save,sys:role:list',2,NULL,0,1),(7,2,'update',NULL,'sys:user:update,sys:role:list,sys:user:user-role',2,NULL,0,1),(8,2,'delete',NULL,'sys:user:delete',2,NULL,0,1),(9,3,'read',NULL,'sys:role:page,sys:role:get',2,NULL,0,1),(10,3,'create',NULL,'sys:role:save,sys:menu:tree',2,NULL,0,1),(11,3,'update',NULL,'sys:role:update,sys:menu:tree,sys:role:role-menu',2,NULL,0,1),(12,3,'delete',NULL,'sys:role:delete',2,NULL,0,1),(13,4,'read',NULL,'sys:menu:tree,sys:menu:get',2,NULL,0,1),(14,4,'create',NULL,'sys:menu:save,sys:menu:tree',2,NULL,0,1),(15,4,'update',NULL,'sys:menu:update,sys:menu:tree',2,NULL,0,1),(16,4,'delete',NULL,'sys:menu:delete',2,NULL,0,1),(17,1,'Log','/sys/log','',1,'log',4,0),(20,17,'read',NULL,'sys:log:page',2,NULL,0,1),(22,17,'delete',NULL,'sys:log:delete',2,NULL,0,1),(23,1,'Job','/sys/job',NULL,1,'timer',1,0),(24,23,'read',NULL,'sys:schedule:page,sys:schedule:get',2,NULL,0,1),(25,23,'create',NULL,'sys:schedule:save',2,NULL,0,1),(26,23,'update',NULL,'sys:schedule:update,sys:schedule:get',2,NULL,0,1),(27,23,'delete',NULL,'sys:schedule:delete',2,NULL,0,1),(28,23,'run',NULL,'sys:schedule:run',2,NULL,0,1),(29,23,'status',NULL,'sys:schedule:pause,sys:schedule:resume',2,NULL,0,1),(31,23,'log',NULL,'sys:schedule:log',2,NULL,0,1);

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT 'ËßíËâ≤ÂêçÁß∞',
  `remark` varchar(100) DEFAULT NULL COMMENT 'Â§áÊ≥®',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT 'ÂàõÂª∫ËÄÖID',
  `create_time` datetime DEFAULT NULL COMMENT 'ÂàõÂª∫Êó∂Èó¥',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='ËßíËâ≤';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

INSERT INTO `sys_role` VALUES (1,'ROLE_ADMIN','admin',1,'2021-08-20 20:14:51');

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT 'ËßíËâ≤ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT 'ËèúÂçïID',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=618 DEFAULT CHARSET=utf8mb4 COMMENT='ËßíËâ≤‰∏éËèúÂçïÂØπÂ∫îÂÖ≥Á≥ª';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

INSERT INTO `sys_role_menu` VALUES (535,1,1),(536,1,2),(537,1,3),(538,1,4),(539,1,5),(540,1,6),(541,1,7),(542,1,8),(543,1,9),(544,1,10),(545,1,11),(546,1,12),(547,1,13),(548,1,14),(549,1,15),(550,1,16),(551,1,17),(552,1,20),(553,1,22),(554,1,23),(555,1,24),(556,1,25),(557,1,26),(558,1,27),(559,1,28),(560,1,29),(561,1,31);

--
-- Table structure for table `sys_schedule_job`
--

DROP TABLE IF EXISTS `sys_schedule_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_schedule_job` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bean_name` varchar(200) NOT NULL COMMENT 'Bean name',
  `params` varchar(2000) DEFAULT NULL COMMENT 'Params',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'Cron expression',
  `status` tinyint(4) DEFAULT NULL COMMENT 'Status',
  `remark` varchar(255) DEFAULT NULL COMMENT 'Remark',
  `create_time` datetime DEFAULT NULL COMMENT 'Create time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='Schedule job';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_schedule_job`
--

INSERT INTO `sys_schedule_job` VALUES (1,'testTask','1111','0 * * * * ?',1,'test','2021-08-29 15:26:56');

--
-- Table structure for table `sys_schedule_job_log`
--

DROP TABLE IF EXISTS `sys_schedule_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_schedule_job_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `job_id` bigint(20) unsigned NOT NULL COMMENT 'Job id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'Bean name',
  `params` varchar(2000) DEFAULT NULL COMMENT 'params',
  `status` tinyint(4) NOT NULL COMMENT 'Status',
  `error` varchar(2000) DEFAULT NULL COMMENT 'Error message',
  `time` int(11) NOT NULL COMMENT 'Time(ms)',
  `create_time` datetime DEFAULT NULL COMMENT 'Create time',
  PRIMARY KEY (`id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=356 DEFAULT CHARSET=utf8mb4 COMMENT='Schedule job log';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_schedule_job_log`
--

INSERT INTO `sys_schedule_job_log` VALUES (350,1,'testTask','1111',0,NULL,0,'2021-10-15 14:29:00'),(351,1,'testTask','1111',0,NULL,1,'2021-10-15 14:30:00'),(352,1,'testTask','1111',0,NULL,0,'2021-10-15 14:31:00'),(353,1,'testTask','1111',0,NULL,0,'2021-10-15 14:32:00'),(354,1,'testTask','1111',0,NULL,1,'2021-10-15 14:33:00'),(355,1,'testTask','1111',0,NULL,1,'2021-10-15 14:34:00');

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT 'Áî®Êà∑Âêç',
  `password` varchar(100) DEFAULT NULL COMMENT 'ÂØÜÁ†Å',
  `email` varchar(100) DEFAULT NULL COMMENT 'ÈÇÆÁÆ±',
  `mobile` varchar(100) DEFAULT NULL COMMENT 'ÊâãÊú∫Âè∑',
  `enable` tinyint(1) unsigned DEFAULT NULL COMMENT 'Áä∂ÊÄÅ  0ÔºöÁ¶ÅÁî®   1ÔºöÊ≠£Â∏∏',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT 'ÂàõÂª∫ËÄÖID',
  `create_time` datetime DEFAULT NULL COMMENT 'ÂàõÂª∫Êó∂Èó¥',
  `avatar` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='Á≥ªÁªüÁî®Êà∑';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

INSERT INTO `sys_user` VALUES (1,'admin1','$2a$10$x.9ZKHDX.o8XHQDylCrgV.1KQQRz2GrXP0bJrxQAcWSn7Zda3G266','root@fast.com','13612345679',1,1,'2011-11-11 11:11:11','@/assets/avatar/cute8.jpg');

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT 'Áî®Êà∑ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT 'ËßíËâ≤ID',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COMMENT='Áî®Êà∑‰∏éËßíËâ≤ÂØπÂ∫îÂÖ≥Á≥ª';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

INSERT INTO `sys_user_role` VALUES (14,1,1);
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-15 14:34:06
