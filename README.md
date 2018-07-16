# MyERP

[![Build Status](https://travis-ci.org/diarraSokhna/Projet5_MyErp.svg?branch=master)](https://travis-ci.org/diarraSokhna/Projet5_MyErp/)
[![Code Coverage](https://codecov.io/github/diarraSokhna/Projet5_MyErp/coverage.svg)](https://codecov.io/gh/diarraSokhna/Projet5_MyErp)

## Organisation du répertoire

*   `doc` : documentation
*   `docker` : répertoire relatifs aux conteneurs _docker_ utiles pour le projet
    *   `dev` : environnement de développement
*   `src` : code source de l'application

## Environnement de développement

Les composants nécessaires lors du développement sont disponibles via des conteneurs _docker_.
L'environnement de développement est assemblé grâce à _docker-compose_
(cf docker/dev/docker-compose.yml).

Il comporte :

*   une base de données _PostgreSQL_ contenant un jeu de données de démo (`postgresql://127.0.0.1:9032/db_myerp`)

### Lancement

    cd docker/dev
    docker-compose up


### Arrêt

    cd docker/dev
    docker-compose stop


### Remise à zero

    cd docker/dev
    docker-compose stop
    docker-compose rm -v
    docker-compose up

	
## Modifications et Corrections

*   Dans l'entité `EcritureComptable`, modificationde la méthode `getTotalCredit()` qui appelle la méthode `getDebit()` au lieu de `getCredit()` et par la même occasion correction du test `isEquilibree()` qui appeler la methode `isEquilibree()` du bean `EcritureComptable` qui lui appeler `getTotalCredit()`
*   Dans la classe `ComptabiliteManagerImpl`, ajout la ligne `this.checkEcritureComptable(pEcritureComptable)` dans la methode `updateEcritureComptable()` en haut afin de vérifier que la référence de l'écriture comptable respecte les règles de comptabilité
*   Dans la classe `SpringRegistry` de la couche business, modification de la variable `CONTEXT_APPLI_LOCATION` afin d'adapter le chemin d'accès au fichier `bootstrapContext.xml` qui est un conteneur Spring IoC, dans lequel on importe le `businessContext.xml`, `consumerContext.xml` et le `datasourceContext.xml` qui va redéfinir le bean `dataSourceMYERP` pour les tests


## Ajouts

*   Mise à jour de postegreSQL 9.4 vers 10.4 afin de pouvoir faire de `insertUpdate`
*   Ajout de la dépendance vers Cobertura dans le POM parent afin de prendre en compte la couverture du code
*   Dans le bean `SequenceEcritureComptable`, ajpout de l'attribut journalCode, son getter et setter
*   Dans le consumer, ajout du RowMapper `SequenceEcritureComptableRM`
*   Dans l'interface `ComptabiliteDao`, ajout de la méthode `getSequenceByCodeAndAnneeCourante()`, implémentation de celle-ci dans `ComptabiliteDaoImpl` et définition de la requête correspondante `SQLgetSequenceByCodeAndAnneeCourante` dans le fichier `sqlContext.xml`
*   Dans l'interface `ComptabiliteDao`, ajout de la méthode `insertOrUpdateSequenceEcritureComptable()`, implémentation de celle-ci dans `ComptabiliteDaoImpl` et définition de la requête correspondante `SQLinsertOrUpdateSequenceEcritureComptable` dans le fichier `sqlContext.xml`
*   Dans l'interface `ComptabiliteManager`, ajout de la méthode `insertOrUpdateSequenceEcritureComptable()`, implémentation de celle-ci dans `ComptabiliteManagerImpl`
*   Dans la classe `ComptabiliteManagerImpl`, implémentation de la méthode `addReference()` 
*   Dans la classe `ComptabiliteManagerImplTest`, test de la méthode `addReference()`
*   Dans la classe `ComptabiliteManagerImpl`, implémentation de la régle de comptabilité 5
*   Configuration des tests d'intégration de la couche consumer dans le dossier `test-consumer`
*   Ajout des fichiers database.properties and src/test-consumer/resources et src/test-business/resources contenant les informations de connection à la base de données
*   Ajout de test dans le module consumer, business et modele...

## Travis CI et CodeCov

Création du fichier de configuration `.travis.yml` de l'environnement d'intégration continue avec travis CI dans la racine du projet
Un lien vers le build dans travis CI et vers la couverture dans Codecov.
	
