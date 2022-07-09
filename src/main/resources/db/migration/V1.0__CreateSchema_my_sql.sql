CREATE DATABASE  IF NOT EXISTS `entreprise` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `entreprise`;

--
-- Table structure for table `utilisateur`
--
DROP TABLE IF EXISTS `vehicule`;
CREATE TABLE `vehicule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_immatriculation` date DEFAULT NULL,
  `immatriculation` varchar(10) NOT NULL,
  `couleur` varchar(120) NOT NULL,
  `marque` varchar(120) DEFAULT NULL,
  `modele` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;




