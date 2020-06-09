# Objectif
1. Dockeriser cette application JavaEE qui tourne sous wildfly 
2. Déployer l'image docker sur AWS ElasticBeanStalk.

# Prérequis
1. Tester l'application en local : http://localhost:18080/POC_PI_AWS-web/index.jsf ==> elle devra afficher votre IP publique et votre localisation géographique
2. Installer Docker sur votre machine
3. Avoir un compte sur DockerHub

# Environnement de travail
```
docker --version
```
Docker version 18.09.4, build d14af54
```
java -version
```
java version "1.8.0_181"
Java(TM) SE Runtime Environment (build 1.8.0_181-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.181-b13, mixed mode)


# Générer le livrable sous forme ear
```
clean package
```
On va utiliser ce chemin par la suite /Path_to/POC_PI_AWS-ear.ear


# Créer une image docker qui contient l'application
Dockerfile:
```
#https://hub.docker.com/r/jboss/wildfly
FROM jboss/wildfly:9.0.0.Final
COPY POC_PI_AWS-ear.ear /opt/jboss/wildfly/standalone/deployments/
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
```

1. Il faut copier le ear a coté du Dockerfile
2. puis, aller au dossier qui contient le Dockerfile.
3. Construire l'image
```
docker build -t wildfly-poc-aws .
```
4. Vérifier l'image
```
docker images
```


# Tester l'image Docker en local :
Lancer le container
```
docker run -it --name wildfly-poc-aws-container -p 8080:8080 wildfly-poc-aws
```
Et maintenant, il faut tester l'application en local : http://localhost:18080/POC_PI_AWS-web/index.jsf


# Mettre l'image sur DockerHub
Si vous n'avez jamais fait un push sur DockerHub, il faut faire :
```
docker login
```
Lister les images
```
docker images
```
Pour pouvoir faire le push il faut que le nom de l'image commence par votre username sur DockerHub (c'est le nom du repository par défaut)
 ```
 docker tag wildfly-poc-aws yaich/wildfly-poc-aws
 ```
Faire le push sur DockerHub
```
docker push yaich/wildfly-poc-aws
```

# Déployer l'image sur AWS ElasticBeanStalk
1. Créer le fichier Dockerrun.aws.json 
```
{
  "AWSEBDockerrunVersion": "1",
  "Image": {
    "Name": "yaich/wildfly-poc-aws",
    "Update": "true"
  },
  "Ports": [
    {
      "ContainerPort": "8080"
    }
  ]
}
```

2. Aller à la console AWS, au service AWS ElasticBeanStalk
3. Créer une nouvelle application
4. Créer un environnement associé a cette application
5. Choisir WebServer environement
6. puis, choisir Preconfigured platform et selectionner Docker.
7. Attendre quelques minutes et vérifier Health = Ok
8. Appuyer sur le bouton "Upload and Deploy" et choisir le fichier qu'on a crée dans la première étape : Dockerrun.aws.json 
9. Puis tester : http://IP_publique/POC_PI_AWS-web/index.jsf

## ET C'est fini !!! 

## Références :
- https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/single-container-docker.html#single-container-docker.setup
- https://docs.docker.com/
- https://hub.docker.com/r/jboss/wildfly
- https://dzone.com/refcardz/java-containerization?chapter=1






