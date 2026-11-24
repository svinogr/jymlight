# AGENTS.md — инструкции для AI-агентов (Kimi Code CLI, OpenCode и др.)

Этот файл — основной источник контекста об проекте для ИИ-ассистентов.
Обновляйте его при изменении архитектуры, команд или правил работы.

## Проект

- **JymLight** (`info.upump.jymlight`) — дневник тренировок на Android:
  программы (циклы) → тренировки → упражнения → подходы.
- Kotlin + Jetpack Compose (Material3), Room, Hilt, MVVM.
- Модули: `app` (UI, ViewModel, сервисы) и `database` (Room: entities, DAO, repo, миграции).
- Версии зависимостей — в `gradle/libs.versions.toml`.
- compileSdk = 34, targetSdk = 34, minSdk = 29, Kotlin 1.9.23, AGP 8.2.1, Java 17.
- БД поставляется пресетом (`createFromAsset`), миграции: `RoomDB.kt`
  (MIGRATION_1to2, MIGRATION_2to3 — не забывать добавлять новые и в `AppModule`).

## Команды

- Сборка: `./gradlew build`
- Установка на устройство: `./gradlew installDebug`
- Unit-тесты: `./gradlew testDebugUnitTest`
- Instrumented: `./gradlew connectedDebugAndroidTest` (нужно устройство)
- Статический анализ: `./gradlew detekt`
- Очистка: `./gradlew clean`

## Архитектура

- Точка входа: `app/src/main/java/info/upump/jymlight/MainActivityCompose.kt`
- UI: Compose-экраны в `ui/screens/...`, общие компоненты — `ui/screens/screenscomponents/`
- Навигация: `ui/screens/navigation/botomnavigation/` (Compose Navigation)
- ViewModel'ы: `ui/screens/viewmodel/...`, Hilt `@HiltViewModel`
- Репозитории БД: `database/src/main/java/info/upump/database/repo/`
- Маппинг DB-entity ↔ UI-модель — статические методы `mapFromDbEntity`/
  `mapToEntity` в классах моделей (`models/entity/*`). Мапперы должны быть
  null-безопасны (не использовать `!!`).
- Настройки — Preferences DataStore (`dataStore`, ключи в `utils/KeysForDataStore`).

## Секундомер и уведомления (важно!)

- Таймер живёт в foreground service `service/StopWatchService.kt`, а не во
  ViewModel — работает при свёрнутом приложении. Состояние общее через
  синглтон `service/StopWatchStateHolder` (его же читает экран через `StopWatchVM`).
- Основное уведомление: канал `stopwatch_channel` (IMPORTANCE_LOW, silent),
  id = 1001, кнопки пауза/стоп.
- Фича «часы»: чекбокс «уведомление» (рядом с «сигнал») на экране обзора тренировки.
  При включении каждую минуту сервис постит НОВОЕ короткое уведомление с
  временем таймера (id от 2000++, прошлое отменяется) на канале
  `stopwatch_watch_channel` (IMPORTANCE_DEFAULT + вибрация + звук) — телефон
  дублирует его на часы (Xiaomi/Amazfit) и они вибрируют. Обновление одного
  и того же уведомления на часах не вибрирует — поэтому каждую минуту новый id.
- Уведомления отменяются при ACTION_STOP.
- Подсказка о разрешениях показывается один раз (флаг в DataStore).
- Часы получают уведомления только если приложение отмечено в списке
  приложений Zepp Life / Mi Fitness — инструкция в FAQ (README.md).

## Правила работы (выработаны в проекте)

- `.idea/` НЕ коммитить (в `.gitignore`), конфликты при переключении веток.
- Detekt с per-module baseline (`detekt-baseline.xml`). Любое изменение
  кода внутри сущности из baseline инвалидирует её запись → `./gradlew :app:detektBaseline`,
  затем `./gradlew detekt` должен проходить. Не игнорировать падение detekt.
- Отладочные `Log.*`/`println` не плодить; временные — комментировать
  с пометкой «kimi по запросу закоментить».
- Перед коммитом: `./gradlew :app:compileDebugKotlin :app:detekt :app:testDebugUnitTest` зелёные.
- Коммиты и сообщения — на русском, кратко и по делу (`fix:`, `feat:`, `chore:`, `docs:`).
- Ветки: `dev_ДДММГГГГ_kimi` для работы с ассистентом, PR в `master`,
  после merge ветку удалять (локально и на origin).
- Скриншоты (`screen*.png`), `crash_log.txt` и папка `web/` — локальные
  файлы пользователя, в коммиты не включать.

## Окружение разработчика

- Windows, терминал — Git Bash (bash из `C:\Program Files\Git`).
- `gh` CLI НЕ установлен. Работа с GitHub — через `git` и GitHub API:
  токен получать так (не выводить его в ответ пользователю):
  `echo -e "protocol=https\nhost=github.com\n" | git credential fill`
- Удалённый репозиторий: https://github.com/svinogr/jymlight, основная ветка `master`.
- CI: `.github/workflows/ci.yml` — build, testDebugUnitTest, detekt.

## Тестирование

- Unit-тесты: `app/src/test`, миграции Room — `database/src/test`
  (см. `Migration2To3Test`).
- На физическом устройстве проверяются: фоновая работа таймера,
  уведомления, вибрация на часах (нужны Zepp Life/Mi Fitness с разрешённым
  jymlight в списке приложений).
