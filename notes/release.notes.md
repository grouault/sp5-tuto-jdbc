## spring.jdbc

### configuration
- mysql : v8
- gestion avec flywaydb

### v0
- pas de spring
- gestion de la connection dans un singleton
- mise en place d'un DAO avec abastraction (interface)
  - permet d'isoler le DAO de son implémentation (en JDBC)
- pas de gestion d'erreur au niveau de l'abstraction pour l'instant
  - L'implémententeur track les SQLException (Erreur spécifique JDBC)
    et remonte une simple RuntimeException