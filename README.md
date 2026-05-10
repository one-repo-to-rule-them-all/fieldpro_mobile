# FieldPro Mobile

Native Android application for the [FieldPro](https://github.com/one-repo-to-rule-them-all/fieldpro) field service SaaS platform. Built with Kotlin + Jetpack Compose, designed for field workers, supervisors, and tenant admins operating in low-connectivity environments.

> **Status:** Phase 0 — Bootstrap. First green CI build is the exit criteria for this phase.

---

## Stack

| Layer | Choice |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose (Material 3) |
| Architecture | MVVM + Clean Architecture (multi-module) |
| DI | Hilt |
| Networking | Retrofit + OkHttp + kotlinx-serialization |
| Local DB | Room |
| Token storage | DataStore (Android Keystore-backed AES-GCM) |
| Async | Coroutines + Flow |
| Navigation | Navigation Compose (type-safe, 2.8+) |
| Background work | WorkManager |
| Image loading | Coil |
| Crash / Perf | Firebase Crashlytics + Performance |
| Push | FCM (Phase 5 — backend-dependent) |
| Build | Gradle (Kotlin DSL) + KSP + version catalog |
| CI/CD | GitHub Actions + Firebase App Distribution + Play Store |

---

## Prerequisites

- **JDK 17** (Temurin recommended)
- **Android Studio Ladybug+** (2024.2+)
- **Android SDK Platform 34**, **Build-Tools 34.0.0**, NDK not required
- **Gradle 8.10+** (installed locally for first-time wrapper generation only)

---

## First-Time Setup

```bash
# 1. Clone
git clone https://github.com/one-repo-to-rule-them-all/fieldpro_mobile.git
cd fieldpro_mobile

# 2. Generate the Gradle wrapper jar (one-time, requires gradle on PATH)
gradle wrapper --gradle-version 8.10 --distribution-type bin

# 3. Sync and build
./gradlew assembleDebug

# 4. Install on emulator (10.0.2.2 → host loopback for backend)
./gradlew :app:installDebug
```

Then start the backend per its README and log in with one of the demo accounts.

**Demo logins (Employee role primary target)**

| Email | Password |
|---|---|
| carlos@demo.fieldpro.app | Employee123! |

---

## Project Structure

```
app/                       Main application module (NavHost, Application, MainActivity)
core/
  common/                  Shared utilities (ApiResult, dispatchers)
  designsystem/            Material 3 theme + FieldPro components
  domain/                  Use cases, domain models (pure Kotlin)
  network/                 Retrofit + OkHttp + generated OpenAPI DTOs
  database/                Room DB + DAOs + entities
  datastore/               Encrypted token storage + SessionManager
  sync/                    WorkManager workers + outbox dispatcher
  notifications/           FCM service + channels + deep-link routing
  location/                FusedLocationProvider wrapper + geofence helpers
  testing/                 Test fixtures + MockWebServer helpers
feature/
  auth/                    Login + biometric unlock
  workorders/              My Jobs list + Job Detail + check-in/out + tasks
  profile/                 Profile + logout + settings + sync diagnostics
  media/                   (Phase 4) Camera + S3 upload queue
  supervisor/              (Phase 5) Crew monitoring + approvals
```

Phase 0 ships only `:app`, `:core:common`, `:core:designsystem`. Subsequent modules land per phase per the [implementation plan](#phased-plan-summary).

---

## Phased Plan Summary

| Phase | Scope | Exit Criteria |
|---|---|---|
| 0 — Bootstrap | Multi-module skeleton, version catalog, Hilt, Compose, ktlint+detekt, PR CI | Green `pr.yml` build with installable debug APK |
| 1 — Auth | `:core:network` + `:core:datastore` + `:feature:auth` (login + biometric) | Log in against local backend; silent 401 refresh works |
| 2 — Work Orders MVP | Room + Paging 3 + outbox + GPS check-in/out + tasks | Airplane-mode test passes |
| 3 — Polish | Pull-to-refresh, error UI, dark mode, a11y, Crashlytics, internal Firebase distribution | TalkBack walkthrough passes |
| 4 — Photos & Time | CameraX + S3 presigned + clock in/out | Offline-then-reconnect photo upload works |
| 5 — Supervisor + Push | Role-gated tabs, FCM, deep links | Test push deep-links to WO detail |
| 6 — Inventory & Reporting | Blocked on backend inventory routes + ARQ worker | TBD |

Full plan archived at `~/.claude/plans/let-beginning-by-reading-bright-goblet.md`.

---

## Commands

```bash
# Build & install debug APK
./gradlew :app:installDebug

# Run unit tests
./gradlew testDebugUnitTest

# Lint suite
./gradlew ktlintCheck detekt lintDebug

# Full PR check (mirrors CI)
./gradlew ktlintCheck detekt lintDebug testDebugUnitTest assembleDebug

# Release AAB (requires signing config)
./gradlew :app:bundleRelease
```

---

## Backend

This app speaks to the FieldPro FastAPI backend. For local development, point `EXPO_PUBLIC_API_URL` equivalent (`FIELDPRO_API_BASE_URL` in `local.properties`) to one of:

| Target | URL |
|---|---|
| Android emulator → host loopback | `http://10.0.2.2:8000` |
| Physical device on LAN | `http://<your-machine-ip>:8000` |
| Production | `https://api.fieldpro.app` (placeholder) |

Mobile authenticates via `POST /api/v1/auth/login/?client=mobile` — the `?client=mobile` query param is mandatory; it tells the backend to return `refresh_token` in the JSON body (mobile cannot use cookies).

---

## Contributing

- Branch from `main` with `feature/<short-desc>` or `fix/<short-desc>`
- PRs must pass `ktlintCheck`, `detekt`, `lintDebug`, `testDebugUnitTest`, `assembleDebug`
- Conventional commits preferred: `feat:`, `fix:`, `chore:`, `refactor:`, `test:`, `docs:`
- Squash-merge to `main` once green
