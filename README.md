

# Git Handout

## Inhalt
1. [Was ist Git und warum sollte es verwendet werden?](#was-ist-git-und-warum-sollte-es-verwendet-werden)
2. [Grundlegende Git-Befehle](#grundlegende-git-befehle)
3. [Branches und ihre Nutzung](#branches-und-ihre-nutzung)
4. [Umgang mit Merge-Konflikten](#umgang-mit-merge-konflikten)
5. [Git mit IntelliJ/PyCharm verwenden](#git-mit-intellijpycharm-verwenden)
6. [Nützliche Git-Tools und Plattformen](#nützliche-git-tools-und-plattformen)

---

## 1. Was ist Git und warum sollte es verwendet werden?

**Git** ist ein verteiltes Versionskontrollsystem, das von Entwicklern verwendet wird, um den Quellcode von Projekten zu verfolgen und zu verwalten. Es ermöglicht mehreren Entwicklern, gleichzeitig an verschiedenen Teilen eines Projekts zu arbeiten, ohne dabei Dateien oder Änderungen zu überschreiben.

### Vorteile von Git:
- **Versionskontrolle**: Behalten Sie den Überblick über alle Änderungen im Projekt.
- **Zusammenarbeit**: Erlaubt es Teams, parallel zu arbeiten und Änderungen zu integrieren.
- **Verteiltes System**: Jeder Entwickler hat eine vollständige Kopie des Projekts auf seinem Rechner.
- **Rückverfolgbarkeit**: Sie können jederzeit zu einem vorherigen Stand zurückkehren.

---

## 2. Grundlegende Git-Befehle

| Befehl           | Beschreibung |
|------------------|--------------|
| `git init`       | Erstellt ein neues, leeres Git-Repository. |
| `git add <datei>`| Fügt eine Datei zur Staging-Area hinzu. |
| `git commit -m "Nachricht"` | Speichert die Änderungen dauerhaft mit einer Commit-Nachricht. |
| `git push`       | Überträgt die lokalen Commits zum Remote-Repository (z.B. GitHub). |
| `git pull`       | Holt und integriert Änderungen vom Remote-Repository in das lokale Repository. |

---


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
.