# VARIABLES #

# Commandes #

JAVAC_OPTIONS = -encoding UTF-8 -implicit:none -d build -sourcepath src 
JAVA = java
JAR = jar cvfe ${JAR_NOM} ${MAIN} -C build fr 
EXEC_JAR = ${JAVA} -jar
MAIN = fr.iutfbleau.SAE3_2_Nolan_Lucile_Firmin.Main


## Chemins relatifs ##

SRC = src/fr/iutfbleau/SAE3_2_Nolan_Lucile_Firmin
BUILD = build/fr/iutfbleau/SAE3_2_Nolan_Lucile_Firmin
DOC = doc/fr/iutfbleau/SAE3_2_Nolan_Lucile_Firmin


## Noms de fichiers à créer ##

JAR_NOM = Tableur.jar



# BUTS #

## But factice ##

.PHONY: run clean doc all


## But par défaut ##

run: Tableur.jar


## Autres buts ##

doc :
	mkdir doc
	javadoc -d doc ${SRC}/*.java

clean : 
	rm -rf ${BUILD}/*.class
	rm -rf ./res/tmp
	rm -f Tableur.jar



# REGLES DE DEPENDANCE #

## Modeles ##

${BUILD}/CellContent.class ${BUILD}/Tableau.class ${BUILD}/AbstractSyntaxTree.class ${BUILD}/Node.class: \
	${SRC}/Tableau.java \
	${SRC}/CellContent.java \
	${SRC}/Node.java \
	${SRC}/AbstractSyntaxTree.java \
	${BUILD}/File.class
	javac ${JAVAC_OPTIONS} ${SRC}/CellContent.java ${SRC}/Tableau.java ${SRC}/AbstractSyntaxTree.java ${SRC}/Node.java

${BUILD}/File.class: ${SRC}/File.java \
	${BUILD}/Maillon.class
	javac ${JAVAC_OPTIONS} ${SRC}/Tableau.java ${SRC}/File.java
	
${BUILD}/Maillon.class: ${SRC}/Maillon.java
	javac ${JAVAC_OPTIONS} src/fr/iutfbleau/SAE3_2_Nolan_Lucile_Firmin/Maillon.java


## Vues ##

${BUILD}/Fenetre.class ${BUILD}/Entree.class ${BUILD}/Cellule.class ${BUILD}/CellListener.class: \
	${SRC}/CellListener.java \
	${SRC}/Fenetre.java \
	${SRC}/Cellule.java \
	${SRC}/Entree.java \
	${BUILD}/Tableau.class \
	${BUILD}/CellContent.class
	javac ${JAVAC_OPTIONS} ${SRC}/Fenetre.java ${SRC}/Cellule.java ${SRC}/Entree.java ${SRC}/CellListener.java


## Controleurs ##

${BUILD}/Launcher.class: ${SRC}/Launcher.java \
	${BUILD}/Tableau.class \
	${BUILD}/Fenetre.class
	javac ${JAVAC_OPTIONS} ${SRC}/Launcher.java

${BUILD}/EntreeListener.class: ${SRC}/EntreeListener.java \
	${BUILD}/Cellule.class \
	${BUILD}/Entree.class
	javac ${JAVAC_OPTIONS} ${SRC}/EntreeListener.java


## Main ##

${BUILD}/Main.class: ${SRC}/Main.java \
	${BUILD}/Launcher.class
	javac ${JAVAC_OPTIONS} ${SRC}/Main.java


## Créer le JAR exécutable ##

Tableur.jar: \
	${BUILD}/Main.class \
	${BUILD}/AbstractSyntaxTree.class \
	${BUILD}/CellContent.class \
	${BUILD}/CellListener.class \
	${BUILD}/Cellule.class \
	${BUILD}/Launcher.class \
	${BUILD}/Entree.class \
	${BUILD}/EntreeListener.class \
	${BUILD}/Fenetre.class \
	${BUILD}/File.class \
	${BUILD}/Maillon.class \
	${BUILD}/Node.class \
	${BUILD}/Tableau.class
	${JAR}