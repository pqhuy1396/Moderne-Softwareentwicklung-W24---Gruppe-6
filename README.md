# Moderne-Softwareentwicklung-W24---Gruppe-6

Quang Huy Phan:

1. Was ist Git und warum sollte es verwendet werden?

   
      Git ist ein verteiltes Versionskontrollsystem, das verwendet wird, um Änderungen an Dateien, insbesondere Quellcode, zu verfolgen. Es ermöglicht Entwicklern, verschiedene Versionen ihrer Projekte zu verwalten, auf ältere Versionen zurückzugreifen und effizient in Teams zusammenzuarbeiten. Git bietet durch seine verteilte Struktur Flexibilität, Sicherheit und Unabhängigkeit, da jeder Entwickler eine vollständige Kopie des Projekts lokal besitzt. Es ist ideal für die Verwaltung von Code-Änderungen und die Koordination von mehreren Entwicklern in Projekten.


2. Grundlegende Git-Befehle (z. B. git init, git add, git commit, git push)
  
         **git init**: Dieser Befehl wird verwendet, um ein neues Repository zu erstellen.
   
         **git add**: Fügt Änderungen an Dateien zum Staging-Bereich hinzu, sodass sie für den nächsten Commit berücksichtigt werden.

         **git commit**: Speichert die Änderungen, die sich im Staging-Bereich befinden, dauerhaft im Repository.

         **git push**: Überträgt die lokalen Commits in das Remote-Repository (z. B. auf GitHub).


3. Branches und ihre Nutzung, Umgang mit Merge-Konflikten
   
   Branches ermöglichen parallele Entwicklung.
   
   **Befehle**:
   
         Branch erstellen: git branch <branch-name>
   
         Branch wechseln: git checkout <branch-name>
   
         Branch mergen: git merge <branch-name>
   
         Branch löschen: git branch -d <branch-name>
   
   Umgang mit Merge-Konflikten: Konflikte treten auf, wenn zwei Branches denselben Code ändern.

   **Konflikt lösen**:

            Git zeigt Konflikte an.
         
            Datei manuell bearbeiten (Konfliktmarkierungen entfernen).
   
            Änderungen hinzufügen: git add <file>
   
            Committen: git commit

4. Git mit IntelliJ/PyCharm benutzen: Local Repository und Remote Repository

   **Local Repository**: 
            
         Git initialisieren --> gitignore hinzufügen --> Repository klonen -->  Lokale Änderungen tracken und committen

   **Remote Repository**: 

         Remote hinzufügen -->  Wenn du lokal Änderungen gemacht hast und sie ins Remote-Repository hochladen möchtest, gehe zu Git push --> Änderungen vom Remote-Repository holen git pull


