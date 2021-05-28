-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  lun. 22 juin 2020 à 18:17
-- Version du serveur :  5.7.26
-- Version de PHP :  7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `huntkingdom`
--

-- --------------------------------------------------------

--
-- Structure de la table `animal`
--

DROP TABLE IF EXISTS `animal`;
CREATE TABLE IF NOT EXISTS `animal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `categorie` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` longtext COLLATE utf8_unicode_ci NOT NULL,
  `image_animal` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `debutSaison` int(11) NOT NULL,
  `finSaison` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `animal`
--

INSERT INTO `animal` (`id`, `categorie`, `nom`, `description`, `image_animal`, `debutSaison`, `finSaison`) VALUES
(1, 'Hunting', 'blackbear', 'Little hunted on our territory your success rate is close to 100%. Take advantage of our baited sites which are visited by this greedy mammal. A hunting camera takes a picture of the bears visiting the sites. If you wish, we will supervise you so that your hunt is up to your expectations.', 'ours_noir.jpg', 5, 3),
(3, 'Hunting', 'SMALL GAME', 'In the fall, it is possible to hunt the Canadian tetra, the ruffed grouse and the hare on our territory. Several trails are available. What a great way to enjoy the beautiful fall days.', 'smallgeme.jpg', 9, 7),
(4, 'Fishing', 'lieujaune', 't is a fish of cold and temperate seas, it is found in the northeast of the Atlantic, from Scandinavia to the Bay of Biscay see at the level of Spain, in the North Sea and in the Western Baltic Sea. In the east of Iceland it is mainly the pollack that we will find, while past the Bay of Biscay, it is especially the pollack that walks there.', 'lieujaune.jpg', 4, 5);

-- --------------------------------------------------------

--
-- Structure de la table `commandes`
--

DROP TABLE IF EXISTS `commandes`;
CREATE TABLE IF NOT EXISTS `commandes` (
  `produit_id` int(11) NOT NULL,
  `panier_id` int(11) NOT NULL,
  PRIMARY KEY (`produit_id`,`panier_id`),
  KEY `IDX_35D4282CF347EFB` (`produit_id`),
  KEY `IDX_35D4282CF77D927C` (`panier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `comment`
--

DROP TABLE IF EXISTS `comment`;
CREATE TABLE IF NOT EXISTS `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `postedAt` date NOT NULL,
  `checked` int(11) NOT NULL,
  `postId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_9474526CE094D20D` (`postId`),
  KEY `IDX_9474526C64B64DCC` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `comment`
--

INSERT INTO `comment` (`id`, `content`, `postedAt`, `checked`, `postId`, `userId`) VALUES
(1, 'Wow Amazing! I can\'t wait for the season to come', '2020-06-16', 0, 5, 71),
(2, 'Same problem here !!', '2020-06-15', 0, 1, 45),
(3, 'It\'s essential to clean your weapon after using it. Otherwise it will get stuck', '2020-06-03', 0, 1, 44);

-- --------------------------------------------------------

--
-- Structure de la table `competition`
--

DROP TABLE IF EXISTS `competition`;
CREATE TABLE IF NOT EXISTS `competition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `dateDebut` date NOT NULL,
  `dateFin` date NOT NULL,
  `categorie` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `lieu` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `nbParticipants` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `competition`
--

INSERT INTO `competition` (`id`, `nom`, `dateDebut`, `dateFin`, `categorie`, `lieu`, `nbParticipants`) VALUES
(4, 'qsd', '2015-01-01', '2017-01-01', 'Hunting', 'qsd', 3),
(6, 'go12', '2020-06-19', '2020-06-28', 'Hunting', '91, Sidi Bou Zid, Tunisia', 6),
(7, 'CaptureTheFish', '2020-06-18', '2020-06-26', 'Fishing', '31, Kairouan, Tunisia', 8),
(8, 'ram', '2020-06-19', '2020-06-21', 'Hunting', '41, Tataouine, Tunisia', 7),
(9, 'CTF', '2020-06-26', '2020-06-30', 'Fishing', '05, Oued Chaaba, Batna, Algeria', 9);

-- --------------------------------------------------------

--
-- Structure de la table `competition_user`
--

DROP TABLE IF EXISTS `competition_user`;
CREATE TABLE IF NOT EXISTS `competition_user` (
  `competition_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`competition_id`,`user_id`),
  KEY `IDX_83D0485B7B39D312` (`competition_id`),
  KEY `IDX_83D0485BA76ED395` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `entrainement`
--

DROP TABLE IF EXISTS `entrainement`;
CREATE TABLE IF NOT EXISTS `entrainement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `categorie` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `nbHeures` int(11) NOT NULL,
  `dateEnt` date DEFAULT NULL,
  `prix` double NOT NULL,
  `lieu` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `entraineurId` int(11) DEFAULT NULL,
  `animalId` int(11) DEFAULT NULL,
  `produitId` int(11) DEFAULT NULL,
  `accepter` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'encours',
  `likeTrainer` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_A27444E564B64DCC` (`userId`),
  KEY `IDX_A27444E5E9C22C61` (`entraineurId`),
  KEY `IDX_A27444E5458056B5` (`animalId`),
  KEY `IDX_A27444E559C47661` (`produitId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `entrainement`
--

INSERT INTO `entrainement` (`id`, `categorie`, `nbHeures`, `dateEnt`, `prix`, `lieu`, `userId`, `entraineurId`, `animalId`, `produitId`, `accepter`, `likeTrainer`) VALUES
(2, 'fishing', 2, '2020-10-13', 200, 'tozeur', 15, 10, 1, 1, 'oui', 'heart'),
(3, 'Hunting', 3, '2020-05-01', 30, 'tozer', 15, 10, 1, 1, 'oui', 'heart');

-- --------------------------------------------------------

--
-- Structure de la table `fos_user`
--

DROP TABLE IF EXISTS `fos_user`;
CREATE TABLE IF NOT EXISTS `fos_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `username_canonical` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `email_canonical` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `salt` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `confirmation_token` varchar(180) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password_requested_at` datetime DEFAULT NULL,
  `roles` longtext COLLATE utf8_unicode_ci NOT NULL COMMENT '(DC2Type:array)',
  `firstName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `lastName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 NOT NULL,
  `phoneNumber` bigint(20) NOT NULL,
  `picture` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gender` tinyint(1) NOT NULL,
  `contract` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `confirmed` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_957A647992FC23A8` (`username_canonical`),
  UNIQUE KEY `UNIQ_957A6479A0D96FBF` (`email_canonical`),
  UNIQUE KEY `UNIQ_957A6479C05FB297` (`confirmation_token`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `fos_user`
--

INSERT INTO `fos_user` (`id`, `username`, `username_canonical`, `email`, `email_canonical`, `enabled`, `salt`, `password`, `last_login`, `confirmation_token`, `password_requested_at`, `roles`, `firstName`, `lastName`, `address`, `phoneNumber`, `picture`, `gender`, `contract`, `confirmed`) VALUES
(37, 'ram', 'ram', 'ramzi0369@gmail.com', 'ramzi0369@gmail.com', 1, NULL, '$2a$10$sNz8TP/xL95z7WXqT2Dpu.HEYmHdS8GBkP0q35xnsnysEKVI8Ybca', '2020-06-16 21:04:48', NULL, NULL, 'a:1:{i:0;s:10:\"ROLE_ADMIN\";}', 'ram', 'ram', 'العذارة, دوز الجنوبية, قبلي, تونس', 21627768870, '0RYjUsNzMHKupDuJ4z7Y5qEzvUadQM.jpg', 1, '', 1),
(44, 'linda11', 'linda11', 'linda.guesmi@esprit.tn', 'linda.guesmi@esprit.tn', 1, NULL, '$2a$10$Dg.Ut2yyqzkBQZR13aG/M.ASwRTT9.PdievBS75suSZWmW07uSth.', '2020-06-16 21:08:04', NULL, NULL, 'a:1:{i:0;s:11:\"ROLE_CLIENT\";}', 'linda', 'guesmi', 'سرت, Libya / ليبيا', 21811478895, '7Mnkqgqu31ehUyUfyyF0QsxlXndq8Y.jpg', 0, '', 1),
(45, 'tatou1217', 'tatou1217', 'tarek.loukil@esprit.tn', 'tarek.loukil@esprit.tn', 1, NULL, '$2a$10$2ie7z0aRU..q9EIZugBUi.P1.uCCmtC0Snj/S.oB/Eg7ZJaoCExZO', NULL, NULL, NULL, 'a:1:{i:0;s:11:\"ROLE_CLIENT\";}', 'tarek', 'loukil', 'Jarana, Lapai, Niger, Nigeria', 2341147785, 'zN7m3itHnaO9a2Xxs4gHBJBoDP0AS4.jpg', 1, '', 1),
(65, 'raerq', 'raerq', 'walid.haguiga@esprit.tn', 'walid.haguiga@esprit.tn', 1, NULL, '$2a$10$1MZ7A2dAip0B7mU7DuoKA.SFqm5PW7ixK1s2BskRNJMTMBG/EOsP6', NULL, NULL, NULL, 'a:1:{i:0;s:12:\"ROLE_TRAINER\";}', 'raea', 'erqra', 'العوينة الجنوبية, دوز الجنوبية, قبلي, تونس', 2161224587, 'Jys1cC3IzjlUnsscFSUid9bFjBeeaV.jpg', 1, 'Ijq0ZxaY4RskyBd7dhq5Ck83gU04Fe.pdf', 1),
(67, 'toutou', 'toutou', 'mohamedSayed.Tourabi@esprit.tn', 'mohamedSayed.Tourabi@esprit.tn', 1, NULL, '$2a$10$NPnv2bHPXhUEWC5h9g3wW.KFLQPDMbLwD3qpVZD0Nmj/Lv1MNOlkq', NULL, NULL, NULL, 'a:1:{i:0;s:13:\"ROLE_REPAIRER\";}', 'toutou', 'toutou', 'سيدي عمر بوحجلة, بوحجلة المركز, بوحجلة, 3180, تونس', 2165586987, 'VZB1vZCSxOnmtkHqJCRWLgIQEAxWuE.jpg', 1, 'WgH7AJ3zbQuN3XurEeSdoiF4L9BSUq.pdf', 0),
(69, '9roun', '9roun', 'moetezhechmi.groun@esprit.tn', 'moetezhechmi.groun@esprit.tn', 1, NULL, '$2a$10$3xyHmNVS2tVoIw6Be7RRVecUJ0kcAiHsC.fszoGPf6C6QVXfucea6', NULL, NULL, NULL, 'a:1:{i:0;s:11:\"ROLE_CLIENT\";}', 'groun', 'groun', 'صانغو, حسي الجربي, جرجيس, مدنين, 4173, تونس', 21655478898, 'z0y1CbTFJmg4GlBH4k5O7BdoAPYxJI.jpg', 1, '', 1),
(70, 'rammm', 'rammm', 'ramzi30369@gmail.com', 'ramzi30369@gmail.com', 1, NULL, '$2a$10$xM.IRpXhqJb8WKkh9SvonuJp5O0ZiX3RDawpHmyA9dQk0R52GeoYe', NULL, NULL, NULL, 'a:1:{i:0;s:11:\"ROLE_CLIENT\";}', 'ram', 'ram', 'دوز الشرقية, دوز الشمالية, قبلي, تونس', 21627768870, '5kDjhjNULUs1cgxdEVppgsdQc3soZe.jpg', 1, '', 1),
(71, 'rajia1', 'rajia1', 'rajiarihab.nacib@esprit.tn', 'rajiarihab.nacib@esprit.tn', 1, NULL, '$2a$10$gJJ7tCWUHzJN9FGGO1BblORZ8jTMupXXp4WduGvLCtN3NHKN69s6y', '2020-06-19 17:42:46', NULL, NULL, 'a:1:{i:0;s:11:\"ROLE_CLIENT\";}', 'rajiarihab', 'nacib', '0, Via Belvedere Alto, Sommavilla, Lamporecchio, Pistoia, Toscana, 51035, Italia', 39277688700, 'H3KBteGSDt033GamVs2qhGnhE3jvMR.jpg', 1, '', 1),
(92, 'ramzi125', 'ramzi125', 'rahma.chhoud@esprit.tn', 'rahma.chhoud@esprit.tn', 1, NULL, '$2a$10$xM.IRpXhqJb8WKkh9SvonuJp5O0ZiX3RDawpHmyA9dQk0R52GeoYe', NULL, NULL, NULL, 'a:1:{i:0;s:11:\"ROLE_CLIENT\";}', 'Ramzi', 'Ramzi', 'gafsa', 27768870, 'temp19149855779514568..PNG', 1, 'contract', 1);

-- --------------------------------------------------------

--
-- Structure de la table `hebergement`
--

DROP TABLE IF EXISTS `hebergement`;
CREATE TABLE IF NOT EXISTS `hebergement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `prixParJour` double NOT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `adresse` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `nbChambre` int(11) NOT NULL,
  `nbLits` int(11) NOT NULL,
  `capacite` int(11) NOT NULL,
  `description` longtext COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `hebergement`
--

INSERT INTO `hebergement` (`id`, `type`, `prixParJour`, `image`, `adresse`, `nbChambre`, `nbLits`, `capacite`, `description`) VALUES
(1, 'flat', 800, 'C:\\Users\\ASUS1\\Pictures\\hunting house.jpg', '22, Tozeur, Tunisia', 6, 8, 8, 'hello  everyone im happy to announce that ur all gonna fail'),
(10, 'house', 50, 'C:\\Users\\ASUS1\\Pictures\\bear accomo.jpg', '41, Hawmat as Suq, Médenine, Tunisia', 4, 10, 10, 'no electricity no food no water no toilet'),
(22, 'caravane', 80, 'C:\\Users\\ASUS1\\Pictures\\faire-du-camping-en-caravane-2.jpg', 'Cité Larmel, 81, Tabarqah, Jendouba, Tunisia', 5, 10, 10, 'almost fully equiped ; kitchen stuff , bath, and private room with royal couch'),
(25, 'house', 50, 'C:\\wamp64\\www\\HuntKingdom\\web\\front\\assets\\images\\accommodation\\accom1.jpg', '80, Bir Mroua, Nabeul, Tunisia', 4, 8, 8, 'nice small house with great space in the middle of the woods'),
(27, 'flat', 140, 'C:\\Users\\ASUS1\\Pictures\\ebd175ad-97d6-4103-b241-d075ad730a08.f6.jpg', '71, Le Kef, Tunisia', 2, 4, 4, 'plus training field and veranda'),
(28, 'house', 150, 'C:\\Users\\ASUS1\\Pictures\\accom14.jpg', '90, Béja, Tunisia', 6, 8, 8, 'high accommodation well equipped and amazing');

-- --------------------------------------------------------

--
-- Structure de la table `location`
--

DROP TABLE IF EXISTS `location`;
CREATE TABLE IF NOT EXISTS `location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nbJours` int(11) NOT NULL,
  `dateArrivee` date NOT NULL,
  `prixTot` double NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `MoyenDeTransportId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_5E9E89CB64B64DCC` (`userId`),
  KEY `IDX_5E9E89CBE2B56A2E` (`MoyenDeTransportId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `location`
--

INSERT INTO `location` (`id`, `nbJours`, `dateArrivee`, `prixTot`, `userId`, `MoyenDeTransportId`) VALUES
(16, 1, '2020-06-19', 240, 10, 26),
(17, 1, '2020-06-10', 40, 10, 25),
(18, 1, '2020-06-15', 40, 10, 25);

-- --------------------------------------------------------

--
-- Structure de la table `marque`
--

DROP TABLE IF EXISTS `marque`;
CREATE TABLE IF NOT EXISTS `marque` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nomMarque` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `moyen_de_transport`
--

DROP TABLE IF EXISTS `moyen_de_transport`;
CREATE TABLE IF NOT EXISTS `moyen_de_transport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `prixParJour` double NOT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `marque` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `categorie` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `moyen_de_transport`
--

INSERT INTO `moyen_de_transport` (`id`, `type`, `prixParJour`, `image`, `marque`, `categorie`) VALUES
(25, 'battery', 40, 'C:\\Users\\ASUS1\\Pictures\\rodeo bike.jpg', 'Rodeo Fv6', 'Bike'),
(26, 'diezel', 240, 'C:\\Users\\ASUS1\\Pictures\\téléchargement.jpg', 'Ford F-250 Pickup', 'vehicule');

-- --------------------------------------------------------

--
-- Structure de la table `notifiable_entity`
--

DROP TABLE IF EXISTS `notifiable_entity`;
CREATE TABLE IF NOT EXISTS `notifiable_entity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `identifier` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `class` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `notifiable_entity`
--

INSERT INTO `notifiable_entity` (`id`, `identifier`, `class`) VALUES
(1, '10', 'UserBundle\\Entity\\User'),
(2, '5', 'UserBundle\\Entity\\User');

-- --------------------------------------------------------

--
-- Structure de la table `notifiable_notification`
--

DROP TABLE IF EXISTS `notifiable_notification`;
CREATE TABLE IF NOT EXISTS `notifiable_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notification_id` int(11) DEFAULT NULL,
  `notifiable_entity_id` int(11) DEFAULT NULL,
  `seen` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_ADCFE0FAEF1A9D84` (`notification_id`),
  KEY `IDX_ADCFE0FAC3A0A4F8` (`notifiable_entity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `notifiable_notification`
--

INSERT INTO `notifiable_notification` (`id`, `notification_id`, `notifiable_entity_id`, `seen`) VALUES
(1, 1, 1, 0),
(2, 2, 1, 0),
(3, 3, 2, 0),
(4, 4, 2, 0),
(5, 5, 2, 0),
(6, 6, 2, 0),
(7, 7, 2, 0),
(8, 8, 2, 0),
(9, 9, 2, 0),
(10, 10, 2, 0),
(11, 11, 2, 0),
(12, 12, 2, 0),
(13, 13, 2, 0),
(14, 14, 2, 0),
(15, 15, 2, 0),
(16, 16, 2, 0),
(17, 17, 2, 0),
(18, 18, 2, 0),
(19, 19, 2, 0),
(20, 20, 2, 0),
(21, 21, 2, 0);

-- --------------------------------------------------------

--
-- Structure de la table `notification`
--

DROP TABLE IF EXISTS `notification`;
CREATE TABLE IF NOT EXISTS `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `subject` varchar(4000) COLLATE utf8_unicode_ci NOT NULL,
  `message` varchar(4000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `link` varchar(4000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `notification`
--

INSERT INTO `notification` (`id`, `date`, `subject`, `message`, `link`) VALUES
(1, '2020-02-24 01:09:40', 'Hello world !', 'This a notification.', 'http://symfony.com/'),
(2, '2020-02-25 01:00:13', 'Hello world !', 'This a notification.', 'http://symfony.com/'),
(3, '2020-02-25 01:03:44', 'hello', 'this is not', 'http://symfony.com/'),
(4, '2020-02-25 01:04:08', 'hello', 'this is not', 'http://symfony.com/'),
(5, '2020-02-25 01:15:34', 'hello', 'this is not', 'http://symfony.com/'),
(6, '2020-02-25 01:20:25', 'hello', 'condidates are waiting for your answer !', 'http://symfony.com/'),
(7, '2020-02-26 03:32:01', 'hello', 'condidates are waiting for your answer !', 'http://symfony.com/'),
(8, '2020-02-26 03:38:34', 'hello', 'condidates are waiting for your answer !', 'http://symfony.com/'),
(9, '2020-02-26 15:13:57', 'hello', 'condidates are waiting for your answer !', 'http://symfony.com/'),
(10, '2020-02-26 19:42:42', 'hello', 'condidates are waiting for your answer !', 'http://symfony.com/'),
(11, '2020-02-26 19:57:41', 'hello', 'condidates are waiting for your answer !', 'http://symfony.com/'),
(12, '2020-02-26 22:16:14', 'hello', 'condidates are waiting for your answer !', 'http://symfony.com/'),
(13, '2020-02-27 05:32:48', 'hello', 'condidates are waiting for your answer !', 'http://symfony.com/'),
(14, '2020-02-27 06:12:04', 'hello', 'condidates are waiting for your answer !', 'http://symfony.com/'),
(15, '2020-02-27 06:30:41', 'hello', 'condidates are waiting for your answer !', 'http://symfony.com/'),
(16, '2020-02-27 07:12:06', 'hello', 'condidates are waiting for your answer !', 'http://symfony.com/'),
(17, '2020-02-27 07:49:21', 'hello', 'condidates are waiting for your answer !', 'http://symfony.com/'),
(18, '2020-02-29 15:29:40', 'hello', 'condidates are waiting for your answer !', 'http://symfony.com/'),
(19, '2020-02-29 21:57:05', 'hello', 'condidates are waiting for your answer !', 'http://symfony.com/'),
(20, '2020-03-02 16:32:05', 'hello', 'condidates are waiting for your answer !', 'http://symfony.com/'),
(21, '2020-04-05 21:15:00', 'hello', 'condidates are waiting for your answer !', 'http://symfony.com/');

-- --------------------------------------------------------

--
-- Structure de la table `panier`
--

DROP TABLE IF EXISTS `panier`;
CREATE TABLE IF NOT EXISTS `panier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `datePanier` date NOT NULL,
  `prixTotal` double NOT NULL,
  `nbArticles` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `panier`
--

INSERT INTO `panier` (`id`, `datePanier`, `prixTotal`, `nbArticles`) VALUES
(34, '2020-05-24', 2000, 2),
(35, '2020-05-24', 2000, 2),
(36, '2020-05-24', 2000, 2),
(37, '2020-05-24', 2000, 2),
(38, '2020-05-24', 2000, 2),
(39, '2020-05-24', 3000, 2),
(40, '2020-05-24', 190, 54),
(41, '2020-05-24', 90, 57),
(42, '2020-05-24', 20, 55),
(43, '2020-05-24', 105, 3),
(44, '2020-05-24', 70, 2),
(45, '2020-05-24', 1140, 3),
(46, '2020-05-24', 125, 0),
(47, '2020-05-24', 85, 2),
(48, '2020-05-24', 20, 1),
(49, '2020-06-01', 70, 2);

-- --------------------------------------------------------

--
-- Structure de la table `participationcompetition`
--

DROP TABLE IF EXISTS `participationcompetition`;
CREATE TABLE IF NOT EXISTS `participationcompetition` (
  `user_id` int(11) NOT NULL,
  `competition_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`competition_id`),
  KEY `IDX_7405929AA76ED395` (`user_id`),
  KEY `IDX_7405929A7B39D312` (`competition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `piecesdefectueuses`
--

DROP TABLE IF EXISTS `piecesdefectueuses`;
CREATE TABLE IF NOT EXISTS `piecesdefectueuses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `etat` tinyint(1) NOT NULL DEFAULT '0',
  `reserved` tinyint(1) NOT NULL,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `categorie` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_F44FD5EE64B64DCC` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

DROP TABLE IF EXISTS `produit`;
CREATE TABLE IF NOT EXISTS `produit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promotion_id` int(11) DEFAULT NULL,
  `lib_prod` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `prix` double NOT NULL,
  `prixFinale` double NOT NULL,
  `description` longtext COLLATE utf8_unicode_ci NOT NULL,
  `qte_prod` int(11) NOT NULL,
  `date_ajout` date NOT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `marque` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_29A5EC27139DF194` (`promotion_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id`, `promotion_id`, `lib_prod`, `prix`, `prixFinale`, `description`, `qte_prod`, `date_ajout`, `image`, `type`, `marque`) VALUES
(1, NULL, 'aze', 50, 50, 'qsdqsdq', 10, '2020-04-16', 'E:\\roadToInfini\\java\\projet\\FinalGit\\huntkingdom\\src\\Uploads\\pic4.jpeg', 'Fishing', 'decathlon'),
(3, NULL, 'libelle', 20, 20, 'description', 30, '2020-04-16', 'E:\\roadToInfini\\java\\projet\\FinalGit\\huntkingdom\\src\\Uploads\\pic5.jpeg', 'Hunting', 'marque'),
(4, NULL, 'libelle2', 20, 20, 'description', 10, '2020-04-16', 'E:\\roadToInfini\\java\\projet\\FinalGit\\huntkingdom\\src\\Uploads\\pic2.jpeg', 'Hunting', 'marque'),
(5, NULL, 'libelle3', 20, 20, 'description', 10, '2020-04-16', 'E:\\roadToInfini\\java\\projet\\FinalGit\\huntkingdom\\src\\Uploads\\pic3.jpeg', 'Hunting', 'marque'),
(6, NULL, 'a', 20, 20, 'a', 2, '2020-05-22', 'E:\\roadToInfini\\java\\projet\\FinalGit\\huntkingdom\\src\\Uploads\\pic6.jpeg', 'aa', 'Fishing');

-- --------------------------------------------------------

--
-- Structure de la table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
CREATE TABLE IF NOT EXISTS `promotion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taux` double NOT NULL,
  `dateDebut` date NOT NULL,
  `dateFin` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `publication`
--

DROP TABLE IF EXISTS `publication`;
CREATE TABLE IF NOT EXISTS `publication` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` longtext COLLATE utf8_unicode_ci NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `image` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `closed` tinyint(1) NOT NULL,
  `solved` tinyint(1) NOT NULL,
  `views` int(11) NOT NULL,
  `pinned` tinyint(1) NOT NULL,
  `publishedAt` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_AF3C677964B64DCC` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `publication`
--

INSERT INTO `publication` (`id`, `title`, `description`, `userId`, `image`, `closed`, `solved`, `views`, `pinned`, `publishedAt`) VALUES
(1, 'Defected Sniper RAT32', 'I bought a new sniper RAT32 from huntKingdom store last week and after one trip the bullets got stuck. I don\'t know what to do', 71, '9d9fd846e848c45f49d3e9b2ba41434a.jpeg', 0, 0, 6, 0, '2020-02-27 00:00:07'),
(2, 'post admin ramzi', 'post admin ramzi description', 71, 'f62256c1b36fbc64f281faad7c57e85c.jpeg', 0, 1, 8, 0, '2020-02-27 00:14:28'),
(5, 'Announcement', 'Announcement  description', 71, '639f3915314a1cbc97949a3774d8a649.jpeg', 0, 0, 0, 0, '2020-02-27 07:14:07'),
(6, 'azezeaaaaaaaaaaaaaaa', 'zeazaeaeqsdqsdqsdds', 74, '9f3014d2051566f8585879a4cef80b6c.png', 0, 0, 1, 0, '2020-06-22 10:11:13');

-- --------------------------------------------------------

--
-- Structure de la table `publicity`
--

DROP TABLE IF EXISTS `publicity`;
CREATE TABLE IF NOT EXISTS `publicity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateDebut` datetime NOT NULL,
  `dateFin` datetime NOT NULL,
  `compagnie` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `prix` double NOT NULL,
  `description` longtext COLLATE utf8_unicode_ci NOT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `titre` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `publicity`
--

INSERT INTO `publicity` (`id`, `dateDebut`, `dateFin`, `compagnie`, `prix`, `description`, `image`, `titre`) VALUES
(4, '2015-01-01 00:00:00', '2022-01-01 00:00:00', 'sdfhj', 54, 'sdfjlksjdfk', '02a41dcc47671928485ceea5b2f33114.jpeg', 'qsdsdfqkfj');

-- --------------------------------------------------------

--
-- Structure de la table `reclamation`
--

DROP TABLE IF EXISTS `reclamation`;
CREATE TABLE IF NOT EXISTS `reclamation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descriptionRec` longtext COLLATE utf8_unicode_ci NOT NULL,
  `dateRec` date NOT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `userId` int(11) NOT NULL,
  `handled` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_CE60640464B64DCC` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `reclamation`
--

INSERT INTO `reclamation` (`id`, `descriptionRec`, `dateRec`, `title`, `userId`, `handled`) VALUES
(30, 'reclamation long long description 1', '2020-06-10', 'reclamation3', 71, 1),
(31, 'reclamation long long description 1', '2020-06-03', 'reclamation4', 44, 1),
(33, 'reclamation long long description 1', '2020-06-03', 'reclamation6', 45, 1),
(42, 'reclamation long long description 1', '2020-06-11', 'reclamation9', 45, 1),
(45, 'reclamation long long description 1', '2020-06-01', 'reclamation1', 44, 1),
(47, 'reclamation long long description 1', '2020-06-02', 'reclamation14', 45, 1),
(48, 'reclamation long long description 1 updated', '2020-03-01', 'reclamation17', 44, 0),
(49, 'reclamation long long description 1', '2019-06-01', 'reclamation16', 71, 1),
(50, 'reclamation long long description 1 update user', '2020-06-01', 'reclamation17', 45, 0),
(55, 'Inaccessible cart reclamation description updated', '2020-06-12', 'Inaccessible cart u', 49, 0),
(56, 'reclindea11reclindea11reclindea11reclindea11 updated', '2020-06-18', 'reclindea11updated', 44, 0),
(57, 'qsdazeqscx qsddezrdsfg qsdqdqqq description', '2020-06-19', 'title qsd to', 71, 1),
(60, 'raaclamation description descrip', '2020-06-22', 'reclamation12 upd', 71, 0);

-- --------------------------------------------------------

--
-- Structure de la table `reparation`
--

DROP TABLE IF EXISTS `reparation`;
CREATE TABLE IF NOT EXISTS `reparation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateDebut` datetime DEFAULT NULL,
  `dateFin` datetime NOT NULL,
  `prixRep` double NOT NULL,
  `description` longtext COLLATE utf8_unicode_ci NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `Piecesdefectueuses_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_8FDF219D64B64DCC` (`userId`),
  KEY `IDX_8FDF219D48D970ED` (`Piecesdefectueuses_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nbJours` int(11) NOT NULL,
  `dateArrivee` date NOT NULL,
  `prixTot` double NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `HebergementId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_42C8495564B64DCC` (`userId`),
  KEY `IDX_42C84955BC552B2A` (`HebergementId`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `reservation`
--

INSERT INTO `reservation` (`id`, `nbJours`, `dateArrivee`, `prixTot`, `userId`, `HebergementId`) VALUES
(41, 1, '2020-06-12', 800, 10, 1),
(42, 1, '2020-06-13', 50, 10, 10);

-- --------------------------------------------------------

--
-- Structure de la table `trainer`
--

DROP TABLE IF EXISTS `trainer`;
CREATE TABLE IF NOT EXISTS `trainer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `username_canonical` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `email_canonical` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `salt` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `confirmation_token` varchar(180) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password_requested_at` datetime DEFAULT NULL,
  `roles` longtext COLLATE utf8_unicode_ci NOT NULL COMMENT '(DC2Type:array)',
  `firstName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `lastName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `phoneNumber` int(11) NOT NULL,
  `picture` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gender` tinyint(1) NOT NULL,
  `licence` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `contract` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `confirmed` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_C515082092FC23A8` (`username_canonical`),
  UNIQUE KEY `UNIQ_C5150820A0D96FBF` (`email_canonical`),
  UNIQUE KEY `UNIQ_C5150820C05FB297` (`confirmation_token`),
  KEY `IDX_C515082064B64DCC` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `commandes`
--
ALTER TABLE `commandes`
  ADD CONSTRAINT `FK_35D4282CF347EFB` FOREIGN KEY (`produit_id`) REFERENCES `produit` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_35D4282CF77D927C` FOREIGN KEY (`panier_id`) REFERENCES `panier` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `FK_9474526C64B64DCC` FOREIGN KEY (`userId`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_9474526CE094D20D` FOREIGN KEY (`postId`) REFERENCES `publication` (`id`);

--
-- Contraintes pour la table `competition_user`
--
ALTER TABLE `competition_user`
  ADD CONSTRAINT `FK_83D0485B7B39D312` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_83D0485BA76ED395` FOREIGN KEY (`user_id`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `location`
--
ALTER TABLE `location`
  ADD CONSTRAINT `FK_5E9E89CB64B64DCC` FOREIGN KEY (`userId`) REFERENCES `fos_user` (`id`),
  ADD CONSTRAINT `FK_5E9E89CBE2B56A2E` FOREIGN KEY (`MoyenDeTransportId`) REFERENCES `moyen_de_transport` (`id`);

--
-- Contraintes pour la table `notifiable_notification`
--
ALTER TABLE `notifiable_notification`
  ADD CONSTRAINT `FK_ADCFE0FAC3A0A4F8` FOREIGN KEY (`notifiable_entity_id`) REFERENCES `notifiable_entity` (`id`),
  ADD CONSTRAINT `FK_ADCFE0FAEF1A9D84` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`id`);

--
-- Contraintes pour la table `participationcompetition`
--
ALTER TABLE `participationcompetition`
  ADD CONSTRAINT `FK_7405929A7B39D312` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_7405929AA76ED395` FOREIGN KEY (`user_id`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `publication`
--
ALTER TABLE `publication`
  ADD CONSTRAINT `FK_AF3C677964B64DCC` FOREIGN KEY (`userId`) REFERENCES `fos_user` (`id`);

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `FK_42C8495564B64DCC` FOREIGN KEY (`userId`) REFERENCES `fos_user` (`id`),
  ADD CONSTRAINT `FK_42C84955BC552B2A` FOREIGN KEY (`HebergementId`) REFERENCES `hebergement` (`id`);

--
-- Contraintes pour la table `trainer`
--
ALTER TABLE `trainer`
  ADD CONSTRAINT `FK_C515082064B64DCC` FOREIGN KEY (`userId`) REFERENCES `fos_user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
