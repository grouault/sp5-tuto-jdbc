USE `entreprise`;

--
-- Dumping data for table `utilisateur`
--
LOCK TABLES `vehicule` WRITE;
/*!40000 ALTER TABLE `vehicule` DISABLE KEYS */;
INSERT INTO `vehicule`
    VALUES
    (1,'2015-09-19','708-PO-78','blanche','Peugeot','3008'),
    (2,'2016-09-19','9078-UI-10','blanche','Renault','Clio'),
    (3,'2017-09-19','789-POP-45','blanche','Renault','Clio');
UNLOCK TABLES;