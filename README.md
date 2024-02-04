# Tableur

SAÉ DEV 3.2 : Petit tableur<br>
À rendre le 14 janvier 2024 avant 23h59

Ce projet a été réalisé dans le cadre de mes études avec deux autres élèves.
Le but de ce projet est de créer un tableur de 9 cases sur 9 cases pouvant contenir des formules préfixes comprenant des opérandes(+,-,*,/), des nombres et des références à d'autres cellules (A2, C8,...). 
Si une formule n'est pas calculable (division par 0, formule non calculable, référence à une cellule vide, référence circulaire des cellules,...), une erreur adaptée s'affichera telle que "!SYNTAX" pour une formule infixe (1+1).

Un exemple concret de formule calculable est : <br>
  \* / 10 2 * 2 5<br>
Qui est équivalent à :<br>
(10/2) * (2*5) = 50

## Instructions

Avant tout, il faut récupérer chacun des fichiers et dossiers de ce projet dans le même ordre qu'il est présenté sur le git.

Pour se faire, il est possible de télécharger l'entièreté du projet en cliquant sur l'icône vert "<> Code" en haut de cette page, puis "Download ZIP" dans "Local".

Pour lancer le programme, il suffit d'effectuer les deux commandes suivantes dans un terminal ayant un interpréteur java (VsCode par exemple) et en se trouvant dans le terminal, dans le répertoire qui contient le makefile :
```
$ make
```
```
$ java -jar Tableur.java
```

## Feedback

Si vous avez des retours, contactez moi à cette adresse : nolan.toussaint77@gmail.com

## Authors

- [@Nolan](https://github.com/Naturalhg/)
- [@Lucile](https://dwarves.iut-fbleau.fr/gitiut/pereiral)
- [@Firmin](https://dwarves.iut-fbleau.fr/gitiut/ndacleud)

## License

### [GNU GPL License](LICENSE)
