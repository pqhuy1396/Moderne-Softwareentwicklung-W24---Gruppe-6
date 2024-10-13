

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

## 3. Branches und ihre Nutzung

Ein **Branch** in Git ist wie ein paralleler Entwicklungszweig, auf dem Sie unabhängig von anderen Änderungen arbeiten können. Standardmäßig gibt es den `main` oder `master` Branch, aber für Feature-Entwicklungen oder Bugfixes können eigene Branches angelegt werden.

### Branch-Befehle:
- `git branch <name>`: Erstellt einen neuen Branch.
- `git checkout <name>`: Wechselt zu einem Branch.
- `git merge <branch>`: Führt einen Branch in den aktuellen Branch zusammen.

---

## 4. Umgang mit Merge-Konflikten

**Merge-Konflikte** treten auf, wenn Git nicht automatisch entscheiden kann, welche Änderungen übernommen werden sollen. Dies geschieht oft, wenn mehrere Entwickler denselben Teil einer Datei ändern.

### Konfliktbehebungsschritte:
1. Git wird betroffene Dateien als „conflicted“ kennzeichnen.
2. Öffnen Sie die Datei und entscheiden Sie, welche Änderung(en) Sie behalten möchten.
3. Markieren Sie die Konfliktauflösung und führen Sie `git add` und `git commit` aus, um die Änderungen zu speichern.

---

## 5. Git mit IntelliJ/PyCharm verwenden

**Git in IntelliJ/PyCharm** erleichtert die Versionskontrolle durch eine grafische Benutzeroberfläche, die es einfach macht, Änderungen zu verfolgen und zu integrieren. Die Integration bietet:
- **Lokale Repositories**: Änderungen werden lokal gespeichert, bevor sie ins Remote-Repository übertragen werden.
- **Remote-Repositories**: Einbindung von Plattformen wie GitHub, um Code online zu speichern und zu teilen.

---

## 6. Nützliche Git-Tools und Plattformen

Es gibt viele Tools und Plattformen, die mit Git zusammenarbeiten. Einige davon sind:

- **GitHub**: Eine Web-Plattform zur gemeinsamen Nutzung und Verwaltung von Git-Repositories. Es unterstützt Teamzusammenarbeit, Issues-Tracking, und vieles mehr.
- **GitLab**: Ähnlich wie GitHub, bietet aber zusätzliche Funktionen wie integrierte CI/CD-Pipelines.
- **Bitbucket**: Eine weitere Plattform zur Verwaltung von Git-Repositories, besonders beliebt in Unternehmensumgebungen.

---

