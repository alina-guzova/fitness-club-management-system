-- MySQL dump 10.13  Distrib 8.4.4, for Win64 (x86_64)
--
-- Host: localhost    Database: fitness_club
-- ------------------------------------------------------
-- Server version	8.4.4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authorities` (
  `username` varchar(15) DEFAULT NULL,
  `authority` varchar(25) DEFAULT NULL,
  KEY `username` (`username`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` VALUES ('alina','ROLE_ADMIN'),('olga','ROLE_ADMIN'),('leon','ROLE_CLIENT'),('leon','ROLE_CLIENT'),('vika','ROLE_CLIENT'),('igor','ROLE_CLIENT'),('nikolai','ROLE_CLIENT'),('taniya','ROLE_CLIENT'),('sergey','ROLE_CLIENT'),('maria','ROLE_CLIENT'),('vik','ROLE_CLIENT'),('egor','ROLE_CLIENT'),('eg','ROLE_CLIENT'),('g','ROLE_CLIENT'),('igor1','ROLE_CLIENT'),('igor2','ROLE_CLIENT'),('igor3','ROLE_CLIENT'),('igor4','ROLE_CLIENT'),('igor5','ROLE_CLIENT'),('lera','ROLE_CLIENT'),('igorlaptev','ROLE_CLIENT');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_name` varchar(30) NOT NULL,
  `client_email` varchar(50) DEFAULT NULL,
  `client_phone` varchar(13) NOT NULL,
  `registration_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_clients_phone` (`client_phone`),
  UNIQUE KEY `uq_clients_email` (`client_email`),
  CONSTRAINT `chk_clients_phone_format` CHECK ((`client_phone` like _utf8mb4'+375%'))
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (2,'Алина Гузова','vik.mr@gmail.com','+375291272841','2025-08-09 02:52:12','vika'),(5,'Сидоров Игорь','sidorov@gmail.com','+375291241412','2025-08-09 02:52:12','igor'),(7,'Николай Морозов','moroz.nikolay@mail.ru','+375291705614','2025-08-09 02:52:12','nikolai'),(8,'Татьяна Кравцова','kravtsova.tatyana@gmail.com','+375291112240','2025-08-09 02:52:12','taniya'),(9,'Сергей Волков','volkov.sergey@yandex.ru','+375291112241','2025-08-09 02:52:12','sergey'),(12,'Мария Смирнова','smirnova.mari@gmail.com','+375291112245','2025-09-01 01:00:14','maria'),(18,'Егор Земченко','egor.igor@gmail.com','+375291113237','2025-09-01 22:14:50','egor'),(21,'Игорь Сидоров',NULL,'+375291212','2025-09-02 05:29:43','igor1'),(23,'Игорь Сидоров',NULL,'+37529121','2025-09-02 05:39:46','igor3'),(37,'Игорь Лаптев','laptev.igor@mail.ru','+375293456789','2025-10-02 19:59:41','igorlaptev');
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients_subscriptions`
--

DROP TABLE IF EXISTS `clients_subscriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients_subscriptions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_id` int NOT NULL,
  `subscription_id` int NOT NULL,
  `activation_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_clients_subscriptions_subscription_id` (`subscription_id`),
  KEY `fk_clients_subscriptions_client_id` (`client_id`),
  CONSTRAINT `fk_clients_subscriptions_client_id` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_clients_subscriptions_subscription_id` FOREIGN KEY (`subscription_id`) REFERENCES `subscriptions` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_subscriptions`
--

LOCK TABLES `clients_subscriptions` WRITE;
/*!40000 ALTER TABLE `clients_subscriptions` DISABLE KEYS */;
INSERT INTO `clients_subscriptions` VALUES (2,2,1,'2025-08-01'),(7,7,2,'2025-08-08'),(8,8,10,'2025-08-10'),(9,9,1,'2025-08-09'),(11,7,8,'2025-09-28'),(12,7,4,'2025-09-28'),(13,7,5,'2025-09-28'),(14,7,6,NULL);
/*!40000 ALTER TABLE `clients_subscriptions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients_visit_history`
--

DROP TABLE IF EXISTS `clients_visit_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients_visit_history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_id` int NOT NULL,
  `visit_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_clients_visit_history_client_id` (`client_id`),
  CONSTRAINT `fk_clients_visit_history_client_id` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_visit_history`
--

LOCK TABLES `clients_visit_history` WRITE;
/*!40000 ALTER TABLE `clients_visit_history` DISABLE KEYS */;
INSERT INTO `clients_visit_history` VALUES (3,5,'2025-08-11 09:35:00'),(4,2,'2025-08-14 15:10:00'),(5,7,'2025-08-13 13:05:00'),(8,8,'2025-08-17 10:10:00'),(10,9,'2025-08-19 14:35:00');
/*!40000 ALTER TABLE `clients_visit_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specializations`
--

DROP TABLE IF EXISTS `specializations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `specializations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `specialization_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_specializations_name` (`specialization_name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specializations`
--

LOCK TABLES `specializations` WRITE;
/*!40000 ALTER TABLE `specializations` DISABLE KEYS */;
INSERT INTO `specializations` VALUES (5,'Бодибилдинг'),(7,'Боевые искусства'),(2,'Йога'),(4,'Кроссфит'),(3,'Пилатес'),(10,'Реабилитация'),(8,'Стретчинг'),(6,'Танцы'),(9,'Тренировки на выносливость'),(1,'Фитнес');
/*!40000 ALTER TABLE `specializations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_types`
--

DROP TABLE IF EXISTS `status_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status_types` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status_code` varchar(20) NOT NULL,
  `status_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_status_types_status_code` (`status_code`),
  UNIQUE KEY `uq_status_types_status_name` (`status_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_types`
--

LOCK TABLES `status_types` WRITE;
/*!40000 ALTER TABLE `status_types` DISABLE KEYS */;
INSERT INTO `status_types` VALUES (1,'SCHEDULED','Запланировано'),(2,'COMPLETED','Завершено'),(3,'CANCELLED','Отменено'),(4,'NO_SHOW','Не пришёл'),(5,'RESCHEDULED','Перенесено');
/*!40000 ALTER TABLE `status_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscriptions`
--

DROP TABLE IF EXISTS `subscriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscriptions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `subscription_name` varchar(30) NOT NULL,
  `description` text NOT NULL,
  `current_price` decimal(5,2) NOT NULL,
  `duration_days` int NOT NULL,
  `visit_count` int NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_subscriptions_name` (`subscription_name`),
  CONSTRAINT `chk_subscriptions_duration_pos` CHECK ((`duration_days` > 0)),
  CONSTRAINT `chk_subscriptions_price_pos` CHECK ((`current_price` > 0)),
  CONSTRAINT `chk_subscriptions_visit_count_pos` CHECK ((`visit_count` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscriptions`
--

LOCK TABLES `subscriptions` WRITE;
/*!40000 ALTER TABLE `subscriptions` DISABLE KEYS */;
INSERT INTO `subscriptions` VALUES (1,'Базовый','Доступ в тренажёрный зал без ограничений по времени.',49.99,30,30,1),(2,'Премиум','Включает групповые занятия и бассейн.',89.99,30,40,1),(3,'Утренний','Доступ с 6:00 до 12:00 по будням.',39.99,30,25,1),(4,'Выходной','Только суббота и воскресенье.',29.99,30,8,1),(5,'Годовой','Безлимитный доступ на 365 дней.',499.99,365,400,1),(6,'Студенческий','Скидка для студентов, доступ по будням.',34.99,30,30,1),(7,'Семейный','Для двух человек, включает детскую зону.',99.99,30,50,0),(8,'Интенсив','Доступ к персональным тренировкам.',119.99,30,20,1),(9,'Релакс','Только бассейн и сауна.',59.99,30,15,1),(10,'Тестовый','1 неделя пробного доступа.',9.99,7,5,1),(15,'Вечерний Рел','Доступ к сауне, бассейну и зоне отдыха с 18:00 до 23:00. Идеален после рабочего дня.',79.99,30,10,0);
/*!40000 ALTER TABLE `subscriptions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trainers`
--

DROP TABLE IF EXISTS `trainers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trainers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `trainer_name` varchar(30) NOT NULL,
  `trainer_email` varchar(50) DEFAULT NULL,
  `trainer_phone` varchar(13) NOT NULL,
  `trainer_price` decimal(5,2) NOT NULL,
  `specialization_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_trainers_phone` (`trainer_phone`),
  UNIQUE KEY `uq_trainers_email` (`trainer_email`),
  KEY `fk_trainers_specialization_id` (`specialization_id`),
  CONSTRAINT `fk_trainers_specialization_id` FOREIGN KEY (`specialization_id`) REFERENCES `specializations` (`id`) ON DELETE SET NULL,
  CONSTRAINT `chk_trainers_phone_format` CHECK ((`trainer_phone` like _utf8mb4'+375%')),
  CONSTRAINT `chk_trainers_price_pos` CHECK ((`trainer_price` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainers`
--

LOCK TABLES `trainers` WRITE;
/*!40000 ALTER TABLE `trainers` DISABLE KEYS */;
INSERT INTO `trainers` VALUES (1,'Андрей Мельников','melnikov.andrey@mail.ru','+375291112243',25.00,1),(2,'Светлана Орлова','orlova.svetlana@gmail.com','+375291112244',30.00,2),(3,'Максим Гусев','gusev.maxim@yandex.ru','+375291112245',28.50,3),(4,'Ирина Захарова','zakharova.irina@mail.ru','+375291112246',35.00,4),(5,'Владимир Титов','titov.vladimir@gmail.com','+375291112247',40.00,5),(6,'Наталья Егорова','egorova.natalya@yandex.ru','+375291112248',32.00,6),(7,'Павел Соловьёв','solovyov.pavel@mail.ru','+375291112249',38.00,7),(8,'Екатерина Миронова','mironova.ekaterina@gmail.com','+375291112250',27.00,8),(9,'Олег Баранов','baranov.oleg@yandex.ru','+375291112251',29.99,9),(10,'Алина Чернова','chernova.alina@mail.ru','+375291112252',33.33,10);
/*!40000 ALTER TABLE `trainers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trainers_schedules`
--

DROP TABLE IF EXISTS `trainers_schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trainers_schedules` (
  `id` int NOT NULL AUTO_INCREMENT,
  `day` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `trainer_id` int NOT NULL,
  `client_id` int NOT NULL,
  `status_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_trainers_schedules_trainer_id` (`trainer_id`),
  KEY `fk_trainers_schedules_client_id` (`client_id`),
  KEY `fk_trainers_schedules_status_id` (`status_id`),
  CONSTRAINT `fk_trainers_schedules_client_id` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_trainers_schedules_status_id` FOREIGN KEY (`status_id`) REFERENCES `status_types` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_trainers_schedules_trainer_id` FOREIGN KEY (`trainer_id`) REFERENCES `trainers` (`id`) ON DELETE CASCADE,
  CONSTRAINT `chk_ts_valid_time_range` CHECK ((`end_time` > `start_time`))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainers_schedules`
--

LOCK TABLES `trainers_schedules` WRITE;
/*!40000 ALTER TABLE `trainers_schedules` DISABLE KEYS */;
INSERT INTO `trainers_schedules` VALUES (2,'2025-08-11','09:30:00','10:30:00',2,5,2),(4,'2025-08-13','13:00:00','14:00:00',4,7,4),(5,'2025-08-14','15:00:00','16:00:00',5,2,5),(8,'2025-08-17','10:00:00','11:00:00',8,8,3),(10,'2025-08-19','14:30:00','15:30:00',10,9,5);
/*!40000 ALTER TABLE `trainers_schedules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(15) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('alina','$2y$10$32otC59haaSkEY.yUfYTd.Or7X0B8C3V5wYNptJWAOB6VI1HHPYSW',1),('eg','$2a$10$ZM2DQHgX1ZYvbOIxloZeN.DnkPB7/Qu8Y2.lWdLs9I8419m6VP90i',1),('egor','$2a$10$FbjtlEN/CkYUKsv1lv414eoWOjfEbomsFuELFZNTew360PVzt0oCK',1),('g','$2a$10$0Te14fEwbPd9dw2po1Lkoehzta.SI47aMSNT2bLOzenfhpdh65EiS',1),('igor','$2y$10$hmO0ztVkU/zarUIweZ/Z/e7vcmihtA63r9HbpRoNUODZx1yCk//Iy',1),('igor1','$2a$10$izu4E4Ob.AprCKi869Eso.lOgjjL8eavhZ3CughWd0xZ0erqry4Vi',1),('igor2','$2a$10$yZFoU4QArxBDtoHvOXiMVeormtXcbcPtnSIxrdPEIyxW1N5eBSdki',1),('igor3','$2a$10$bhY04w2M3hq8HCJHiQDccOUraDOzmG9KWDy.tUshJi5OlQIxzCu4u',1),('igor4','$2a$10$jnPd2YESel2U6qc0/l1UZearX3Xk3CDVU3Xvx3JwxJklI10B0pNCK',1),('igor5','$2a$10$tMrCiiG7csOoCPWkrQ8uq.5sVvbmx3ZfsJuqLYBNwFMFJyj.YWnAW',1),('igorlaptev','$2a$10$lFHt9.JKYY.H3K4DMKPKL.4.Oi7ZW3P6w4NHG41zHvg0fhz9XqqNa',1),('leon','$2y$10$3c7.FeIQ7kkJD/kL2t13E.aF2ULIpMX15fBuc327Nr.ePAFXo5Wcu',1),('lera','$2a$10$akdeIuexkKa38Qw/Vd6Ij.L398Hm1Dof.cZZAU/Wvl3JUqF9GdzsW',1),('maria','$2y$10$.rsinC21p.Qgx.JG6PdytO9e5zqE4LKLT2X88EC26nP/Iha9myAMS',1),('nikolai','$2y$10$xVk9d2L.aPYLVo67/ZxPFe5NHieX3Ic9lf9nqPtOJx43yJv5DSK6K',1),('olga','$2y$10$efAjebBPVF9SXIxrazSFgeMhWgqZfYFzA4mDT6iJo/IuC9LbIp5Wu',1),('sergey','$2y$10$7kRvLOEpCnSBXm5tq2RjIOq6UeCqhKnWascEzBb/B7.nQM5QfJrZy',1),('taniya','$2y$10$8twW8r4EufVccPeJ5yK09uMxIkF4kOYhnWUs5RiUT/Vd8ytBuUOm6',1),('vik','$2y$10$ZA/6y36ShuDA/ogUFwX27ueRj1Hy9ButnlUHY9UW7ZZN5GbMxD7O6',1),('vika','$2y$10$rxKZan17ZNqDKnQsTmnSjuRTRcDPsrAOz8QWDoqh6qSUAf8UIoXda',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-06  8:22:16
