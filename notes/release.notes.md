## spring.jdbc

### configuration
- mysql : v8
- gestion avec flywaydb

### version initiale
- pas de spring
- gestion de la connection dans un singleton
- mise en place d'un DAO avec abastraction (interface)
  - permet d'isoler le DAO de son implémentation (en JDBC)
- pas de gestion d'erreur au niveau de l'abstraction pour l'instant
  - L'implémenteur track les SQLException (Erreur spécifique JDBC)
    et remonte une simple RuntimeException


### mise en place spring + ordre des tests
- mise en place de Spring : (data-context.xml / database.properties)
  - configuration dataSource
  - injection dataSource dans le DAO Véhicule
- finalisation des tests
  - mise en place de l'ordre des tests
  - truncate sur véhicule

### requete Select - Update
- mise en place des requetes update
- mise en place des requetes select

### spring - défnition jdbcTemplate
- injection jdbcTemplate dans le DAO