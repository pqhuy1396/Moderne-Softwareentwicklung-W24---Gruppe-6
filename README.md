# Moderne-Softwareentwicklung-W24-Gruppe-6 
# Übung 2 : CI/CD für Spring Boot Projekt mit Gradle
Dieses Projekt nutzt eine umfassende CI/CD-Pipeline, die mit GitHub Actions automatisierte Schritte für das Bauen, Testen, Analysieren und Bereitstellen des Projekts ausführt. Die Pipeline stellt sicher, dass die Codequalität und Sicherheit überprüft und Änderungen automatisch auf Firebase Hosting bereitgestellt werden.

## Überblick über den CI/CD-Workflow

Die CI/CD-Pipeline wird ausgelöst durch:
- Pushes zum `master`-Branch
- Pull-Requests zum `master`-Branch

### Aufbau des Workflows

Der Workflow ist in zwei Hauptjobs gegliedert:
1. **Build und Analyse**
2. **Deployment**

### 1. Build und Analyse Job

Dieser Job umfasst folgende Schritte, um sicherzustellen, dass der Code kompiliert, getestet und analysiert wird:
- **Checkout Code**: Klont das Repository.
- **Set up JDK 17**: Konfiguriert das Java Development Kit (JDK) Version 17 mit der Temurin-Distribution.
- **Install Gradle**: Installiert das Build-Tool Gradle, Version `8.10.2`.
- **Run Tests**: Führt `gradle clean test` aus, um alle Unit-Tests zu starten.
- **Make Gradlew Executable**: Macht das `gradlew`-Skript ausführbar.
- **Build with Gradle**: Kompiliert das Projekt.
- **Cache SonarCloud Pakete**: Speichert Abhängigkeiten für SonarCloud, um die Analyse zu beschleunigen.
- **Cache Gradle Pakete**: Speichert Gradle-Abhängigkeiten, um die Build-Zeiten zu verkürzen.
- **SonarCloud Analyse**: Analysiert den Code mit SonarCloud auf Codequalität und Sicherheitslücken. Erfordert das `SONAR_TOKEN` als Geheimnis zur Authentifizierung bei SonarCloud.

### 2. Deployment-Job

Dieser Job wird ausgeführt, nachdem der Build-Job erfolgreich abgeschlossen wurde, und umfasst folgende Schritte:
- **Checkout Code**: Klont das Repository erneut, um sicherzustellen, dass der neueste Code verfügbar ist.
- **Set up JDK 17**: Bereitet das JDK für die Bereitstellung vor.
- **Install Firebase CLI**: Installiert die Firebase CLI für Bereitstellungsaufgaben.
- **Deploy to Firebase**: Stellt das Projekt auf Firebase Hosting bereit. Erfordert das `FIREBASE_TOKEN` als Geheimnis zur Authentifizierung bei Firebase.

### Umgebungsvariablen und Geheimnisse

- **SONAR_TOKEN**: Wird für die Authentifizierung bei SonarCloud für die Codeanalyse verwendet.
- **FIREBASE_TOKEN**: Wird für die Bereitstellung auf Firebase Hosting verwendet.

### Badges : (https://github.com/pqhuy1396/Moderne-Softwareentwicklung-W24---Gruppe-6/actions)
### Pipeline-Ausgabe

Protokolle für jeden Job und jeden Schritt sind im GitHub Actions-Tab des Repositories sichtbar. Die Ergebnisse der Tests und SonarCloud-Analyse sind dort einsehbar, und jede erfolgreiche Bereitstellung kann im Firebase Hosting überprüft werden.

### Einrichtung der Umgebung

1. Fügen Sie die erforderlichen Geheimnisse im GitHub-Repository hinzu:
   - `SONAR_TOKEN` für die Authentifizierung bei SonarCloud.
   - `FIREBASE_TOKEN` für die Authentifizierung bei der Firebase CLI.
2. Stellen Sie sicher, dass die Firebase-Konfiguration (`firebase.json`) korrekt für die Bereitstellung auf Hosting eingerichtet ist.

### Zusammenfassung

Diese CI/CD-Pipeline ermöglicht automatisierte Tests, Analysen und Deployment und verbessert die Codequalität, beschleunigt den Feedback-Prozess und gewährleistet reibungslose Veröffentlichungen. Mithilfe von GitHub Actions, Firebase Hosting und SonarCloud ist das Projekt strukturiert für kontinuierliche Integration und kontinuierliche Bereitstellung.

# Git Handout


## Inhalt

1. [Was ist Git und warum sollte es verwendet werden?](#was-ist-git-und-warum-sollte-es-verwendet-werden)
2. [Grundlegende Git-Befehle](#grundlegende-git-befehle)
3. [Branches und ihre Nutzung](#branches-und-ihre-nutzung)
4. [Umgang mit Merge-Konflikten](#umgang-mit-merge-konflikten)
5. [Git mit IntelliJ/PyCharm verwenden](#git-mit-intellijpycharm-verwenden)
6. [Nützliche Git-Tools und Plattformen](#nützliche-git-tools-und-plattformen)

---
<!-- erstellt von: Laurin -->

## 1. Was ist Git und warum sollte es verwendet werden? (Laurin)

**Git** ist ein verteiltes Versionskontrollsystem, das von Entwicklern verwendet wird, um den Quellcode von Projekten zu verfolgen und zu verwalten. Es ermöglicht mehreren Entwicklern, gleichzeitig an verschiedenen Teilen eines Projekts zu arbeiten, ohne dabei Dateien oder Änderungen zu überschreiben.

### Vorteile von Git:
- **Versionskontrolle**: Behalten Sie den Überblick über alle Änderungen im Projekt.
- **Zusammenarbeit**: Erlaubt es Teams, parallel zu arbeiten und Änderungen zu integrieren.
- **Verteiltes System**: Jeder Entwickler hat eine vollständige Kopie des Projekts auf seinem Rechner.
- **Rückverfolgbarkeit**: Sie können jederzeit zu einem vorherigen Stand zurückkehren.

---

## 2. Grundlegende Git-Befehle (Laurin)

| Befehl           | Beschreibung |
|------------------|--------------|
| `git init`       | Erstellt ein neues, leeres Git-Repository. |
| `git add <datei>`| Fügt eine Datei zur Staging-Area hinzu. |
| `git commit -m "Nachricht"` | Speichert die Änderungen dauerhaft mit einer Commit-Nachricht. |
| `git push`       | Überträgt die lokalen Commits zum Remote-Repository (z.B. GitHub). |
| `git pull`       | Holt und integriert Änderungen vom Remote-Repository in das lokale Repository. |

---
<!-- erstellt von: Huy -->

## 3. Branches und ihre Nutzung, Umgang mit Merge-Konflikten (Huy)
   
   Branches ermöglichen parallele Entwicklung.
   
   **Befehle**:
   
         Branch erstellen: git branch <branch-name>
   
         Branch wechseln: git checkout <branch-name>
   
         Branch mergen: git merge <branch-name>
   
         Branch löschen: git branch -d <branch-name>
   
## 4. Umgang mit Merge-Konflikten (Huy)

   Konflikte treten auf, wenn zwei Branches denselben Code ändern.

   **Konflikt lösen**:

            Git zeigt Konflikte an.
         
            Datei manuell bearbeiten (Konfliktmarkierungen entfernen).
   
            Änderungen hinzufügen: git add <file>
   
            Committen: git commit

<!-- erstellt von: Rinor -->

## 5. Git mit IntelliJ/PyCharm verwenden (Rinor)

**Git in IntelliJ/PyCharm** erleichtert die Versionskontrolle durch eine grafische Benutzeroberfläche, die es einfach macht, Änderungen zu verfolgen und zu integrieren. Die Integration bietet:
- **Local Repository**: Änderungen werden lokal gespeichert, bevor sie ins Remote-Repository übertragen werden.
- **Remote Repository**: Einbindung von Plattformen wie GitHub, um Code online zu speichern und zu teilen. 
---
<!-- erstellt von: Mergim -->
## 6. Nützliche Git-Tools und Plabormen (Mergim)

    a) GitHub
        -Hosting: Öffentliche und private Repositories.
        -Zusammenarbeit: Tools zur Teamarbeit und Code-Überprüfung.
        -Features: Issue-Tracking, CI/CD-Integration.
    b) GitLab
        -Funktionalität: Ähnlich wie GitHub, aber mit erweiterten Funktionen.
        -Integrierte CI/CD: Direkte Implementierung von CI/CD-Pipelines.
        -DevOps-Tools: Umfassende Unterstützung für den gesamten Softwareentwicklungsprozess.
    c) Bitbucket
        -Entwicklung: Von Atlassian, gute Integration mit Jira und Confluence.
        -Teamarbeit: Tools zur Zusammenarbeit, Code-Reviews und Issue-Tracking.
        -Repository-Hosting: Unterstützung für Git- und Mercurial-Repositories.
