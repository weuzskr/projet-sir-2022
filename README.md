# Projet GL SIR 2022

## Introduction 
Projet de développement logiciel utilisant l'intégration continue (CI), la livraison continue (CD), Sonar, Jenkins, Docker et Kubernetes, en se basant sur une application Spring Boot existante sur GitHub avec 60% de couverture de code

## Description du projet
Faire un Fork du repo de l'application Spring Boot existante sur GitHub et configurer un environnement de développement en utilisant Docker et Kubernetes.
lien du projet :  

Configurez Jenkins pour exécuter des tests automatisés et des analyses de code à chaque commit dans le dépôt Git. Utilisez Sonar pour analyser la qualité du code et détecter les problèmes potentiels, tels que les bogues et les vulnérabilités de sécurité. L’image générée devra être envoyée à l’aide de jenkins sur le docker Hub

Développez de nouvelles fonctionnalités pour l'application en suivant une approche de livraison continue, en effectuant de petites livraisons fréquentes plutôt que de grandes livraisons moins fréquentes. Utilisez Jenkins et Kubernetes pour déployer automatiquement l'application dans un environnement de test à chaque fois qu'elle est mise à jour. 

Nb: vous pouvez utiliser votre environnement local ou utiliser le cloud( AWS, ...).

Assurez-vous que la couverture de code de l'application atteint au moins 80% en ajoutant de nouveaux tests automatisés pour couvrir les nouvelles fonctionnalités développées et celles existantes.

Testez l'application dans l'environnement de test en utilisant des tests automatisés et des tests manuels. Si l'application passe les tests, utilisez Jenkins et Kubernetes pour envoyer automatiquement l'image de l'application sur le docker hub et déployer automatiquement l'application dans un environnement.

Générez un rapport décrivant les différentes étapes du projet ( fichier docker, jenkins, deployment, ...) y compris les analyses de code et les tests effectués, ainsi que les résultats obtenus. Le rapport devrait également inclure des informations sur les améliorations apportées à l'application et sur les problèmes rencontrés et comment ils ont été résolus.

Cet exemple de projet vous montre comment utiliser différentes technologies pour automatiser le processus de développement et de déploiement logiciel, en utilisant une application Spring Boot existante comme point de départ. Vous pouvez également utiliser ces technologies pour améliorer la qualité du code et atteindre des objectifs de couverture de code spécifiques.

## Livrables
- Rapport sous format PDF
- Lien github du projet 
- Lien docker hub pour voir l’image envoyée

#### email: sambndongo@gmail.com
#### subject: PROJET-SIR-2022

## Spécificité sur le projet
Le travail sera effectué sous forme de groupe de 4 étudiants.

La date limite de livraison sera le 08 Janvier 2023 à 00h.

NB: Ce délai dépassé des pénalités sur la note s’appliquera. 

## Credentials à mettre : 
- Sonar : admin/admin123
- dockerhub: projetsir2022/ProjetSir2022
    - repository : le groupe+numéro ( exemple: groupe1)

## Notation:
- Completer les tests et la couverture : 5pts
- CI/CD avec Jenkins- Sonar - Docker - Kubernetes :  5pts
- Rédaction document 10pts 


Notes : 
Pour les utiisateurs de Mac si vous avez un souci avec les commande dockers sur jenkins voir ici https://harshityadav95.medium.com/how-to-setup-docker-in-jenkins-on-mac-c45fe02f91c5

Bonne chance
