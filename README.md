# Helpdesk Intelligent — priorisation automatique & temps réel

Application web de gestion de tickets d'assistance (PFA, FSM) : création de tickets, **catégorisation et priorisation automatiques**, assignation au technicien le moins chargé, **notifications en temps réel** (WebSocket) et tableau de bord statistique.

## Fonctionnalités
- Authentification JWT, 3 rôles : `USER`, `TECHNICIEN`, `ADMIN`
- Tickets : création, commentaires, pièces jointes, historique, statuts (`OUVERT`, `EN_COURS`, `RESOLU`…)
- **Catégorisation automatique** : moteur à règles NLP simplifié (mots-clés FR sur titre + description) → `AICategorizationEngine`
- **Priorité & SLA** : score calculé (urgence dans la description + catégorie) → `LOW / MEDIUM / HIGH / CRITICAL` avec échéance SLA → `PriorityEngine`
- **Assignation automatique** : technicien de la bonne spécialité avec le moins de tickets ouverts → `AssignmentEngine`
- Notifications temps réel (STOMP/SockJS) + e-mails (SMTP)
- Gestion des utilisateurs, statistiques par catégorie / priorité / technicien
- API documentée avec Swagger (`/swagger-ui.html`)

## Stack
| Couche | Technologies |
|---|---|
| Backend | Java 17, Spring Boot 3.4, Spring Security + JWT, Spring Data JPA, WebSocket, Mail, springdoc-openapi |
| Base de données | PostgreSQL |
| Frontend | React 18, Vite, React Router, Axios, STOMP/SockJS |

## Installation

### 1. Base de données
```sql
CREATE DATABASE helpdesk_db;
CREATE USER helpdesk_user WITH PASSWORD '<votre_mot_de_passe>';
GRANT ALL PRIVILEGES ON DATABASE helpdesk_db TO helpdesk_user;
```

### 2. Backend
```bash
cd backend
cp .env.example .env        # puis remplir les valeurs (jamais commité)
export $(grep -v '^#' .env | sed 's/ *#.*//' | xargs)
./mvnw spring-boot:run      # ou : mvn spring-boot:run
```
Variables : `DB_PASSWORD`, `JWT_SECRET` (`openssl rand -base64 48`), `MAIL_USERNAME`, `MAIL_PASSWORD` (compte [Mailtrap](https://mailtrap.io) de test). Voir `backend/.env.example`.

Comptes de démonstration (optionnel) : lancer avec le profil `dev` et `DEMO_PASSWORD` défini :
```bash
export DEMO_PASSWORD='<mot_de_passe_demo>'
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
Crée `admin@helpdesk.com` (ADMIN) et `tech@helpdesk.com` (TECHNICIEN, réseau).

### 3. Frontend
```bash
cd frontend
cp .env.example .env        # VITE_API_BASE_URL=http://localhost:8080
npm install
npm run dev                 # http://localhost:5173
```

### 4. Tests
```bash
cd backend && mvn test
```

## Structure
```
backend/   API Spring Boot (config, controller, service, security, entity, repository)
frontend/  Application React (pages, context, composants de notification)
```

## Limites connues
- La « IA » est un moteur à règles (mots-clés), pas un modèle d'apprentissage ; l'interface `AICategorizationEngine` permet de le remplacer par un modèle ML/LLM.

## Auteur
Projet de fin d'année — Faculté des Sciences de Monastir (GLSI).
