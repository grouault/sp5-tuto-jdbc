## spring.jdbc

### transaction : par programmation avec l'API du gestionnaire de Transaction
- mise en place de JdbcTemplate et de JdbcDaoSupport
  - plus de gestion de la connexion
  - plus de SqlException
- Transaction : gérer manuellement avec le gestionnaire de transaction
- IMPORTANT : mise en place du logger sur
  <Logger name="org.springframework.jdbc" level="debug" />
  <Logger name="org.springframework.jdbc.datasource.DataSourceTransactionManager" level="debug" />

### transaction : jdbc avec mise en place du rollback
- désactivation de la gestion automatique des transactions
- mise en place du commit / rollback

### transaction : initialisation
- mise en place projet BookShop
- ajout d'une nouvelle base de données
- main sans transaction : en mode autocommit
  - dans le main, une exception est levée et pas de rollback opéré
  
### exception : ajout test insertion en erreur
- test en erreur
- main : exécution en erreur
- dataSource: configuration Pool

### spring - NamedParameterJdbcDaoSupport
- chaque dao hérite de la classe Spring : NamedParameterJdbcDaoSupport
- utilisation de getNamedParameterJdbcTemplate() pour les paramètres nommés

### spring - jdbcDaoSupport et getJdbcTemplate
- injection de la dataSource dans les DAOs
- chaque dao hérite de la classe Spring : jdbcDaoSupport
- utilisation de la classe getJdbcTemplate()

### spring - défnition jdbcTemplate
- injection jdbcTemplate dans le DAO

### requete Select - Update
- mise en place des requetes update
- mise en place des requetes select

### mise en place spring + ordre des tests
- mise en place de Spring : (data-context.xml / database.properties)
  - configuration dataSource
  - injection dataSource dans le DAO Véhicule
- finalisation des tests
  - mise en place de l'ordre des tests
  - truncate sur véhicule

### version initiale
- pas de spring
- gestion de la connection dans un singleton
- mise en place d'un DAO avec abastraction (interface)
  - permet d'isoler le DAO de son implémentation (en JDBC)
- pas de gestion d'erreur au niveau de l'abstraction pour l'instant
  - L'implémenteur track les SQLException (Erreur spécifique JDBC)
    et remonte une simple RuntimeException

### configuration
- mysql : v8
- gestion avec flywaydb









