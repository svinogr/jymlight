# Развёртка экранов UPump Light (jymlight)

SVG-макеты всех экранов приложения и интерактивная карта навигации, генерируемые из кода Android-приложения (`app/src/main/java/info/upump/jymlight`).

- `screens/` — 20 SVG-экранов (готовы к импорту в Figma: перетащить файлы на холст → редактируемые векторные слои)
- `generate_screens.py` — генератор макетов
- `map/` — интерактивная карта навигации (Vite-приложение)

## Тема

Приложение всегда использует тёмную схему (см. `ui/theme/Theme.kt`):

| Роль | Цвет |
|---|---|
| primary / background | `#05559B` |
| primaryContainer (accent) | `#5584AF` |
| onPrimary / текст | `#FFFFFF` |
| outlineVariant (ошибки) | `#CF6679` |

Все видимые строки берутся из `app/src/main/res/values/strings.xml`.

## Быстрый старт

```bash
# из корня проекта jymlight
python figma-export/generate_screens.py        # пересоздать все SVG
cp -r figma-export/screens figma-export/map/public/   # обновить карту

cd figma-export/map
npm install          # один раз
npm run dev          # карта на http://localhost:7100
```

Быстрая визуальная проверка одного экрана без браузера (headless Edge):

```bash
"C:/Program Files (x86)/Microsoft/Edge/Application/msedge.exe" --headless --disable-gpu \
  --screenshot=check.png --window-size=720,1560 \
  "file:///C:/Users/user/StudioProjects/jymlight/figma-export/screens/04-cycle-detail.svg"
```

## Устройство generate_screens.py

Скрипт без сторонних зависимостей, сверху вниз:

1. **Константы темы** — цвета (`PRIMARY`, `ACCENT`, ...). Меняются разом для всех экранов.
2. **Примитивы** — `rect`, `line`, `text`; библиотека иконок `icon("edit", x, y, size)` (словарь `_ICONS`, контуры 24×24, белые).
3. **Каркас** — `status_bar()`, `top_bar(title, actions, back)`, `bottom_nav(active)`, `fab(lines)` — общие блоки всех экранов.
4. **Компоненты** — переиспользуемые куски UI.
5. **Экраны** — функции `s01()`…`s20()`; каждая завершается вызовом `wrap("NN-имя-файла", "Название", body, ...)`, который пишет SVG.
6. Внизу — кортеж `for fn in (s01, s02, ...)` со списком генерируемых экранов.

### Координатная система

Экран — `360 × 780` единиц (= dp Android), ось Y вниз, начало слева сверху:

- 0–24 — статус-бар
- 24–80 — top bar
- 80–724 — контент (строится сверху вниз, отслеживая `y`)
- 724–780 — нижняя навигация

SVG пишется с `width="720" height="1560"` и `viewBox="0 0 360 780"` — в Figma импортируется в 2×.

### Компоненты

| Функция | Рисует | Пример |
|---|---|---|
| `img_block(y, h, day=..., title=..., picker=True)` | плейсхолдер изображения | `img_block(80, 200, day="понедельник")` |
| `chips_row(y, [(icon, текст)])` | ряд чипов действий | `chips_row(240, [("delete", "очистить")])` |
| `list_row(y, "Название", subtitle=, right=)` | строка списка 66dp; `right=` — текст у правого края | `list_row(334, "Тренировка А", right="среда")` |
| `date_card(y)` | «Дата начала / Дата окончания» | `date_card(280)` |
| `table_header(y)` + `set_row(y, №, вес, предВес, повт)` | таблица подходов | `table_header(280)` |
| `picker_card(y, "Вес", "50.00")` | number-picker карточка | `picker_card(92, "Вес", "50.00")` |
| `field(y, "Название", "Значение")` | текстовое поле с подчёркиванием | `field(280, "Название программы", "Новая программа")` |
| `comment_box(y, h, ["строка"])` | многострочный комментарий | `comment_box(466, 250, ["текст"])` |
| `checkbox_row(y, "Текст", checked=True)` | строка с чекбоксом | `checkbox_row(88, "Базовый круг", True)` |
| `button_row(y, "send", "Экспорт базы")` | кнопка-строка с иконкой | `button_row(132, "send", "Экспорт базы на Email")` |

## Типовые задачи

### Поменять текст или список на экране

Редактируете соответствующую `sNN()`:

```python
def s04():
    b = top_bar("Базовый круг", back=True)
    b += img_block(80, 200, label="изображение программы")
    b += chips_row(240, [("delete", "очистить"), ("edit", "редактровать"), ("comment", "коментарий")])
    b += date_card(280)
    b += list_row(334, "Тренировка А", right="понедельник")  # <- здесь
    ...
```

### Добавить новый экран

1. Скопируйте ближайшую по структуре функцию `sNN()` под новым именем.
2. Имя файла — по схеме `NN-название` (номер = позиция на карте).
3. Добавьте функцию в кортеж внизу файла (`for fn in (s01, s02, ...)`).
4. Чтобы экран появился на карте навигации — добавьте его в `NODES` и `EDGES` в `map/src/main.js` (сетка: шаг 280 по X, 560 по Y; телефон 162×351).

### Добавить иконку

Вставьте path-контур в словарь `_ICONS` (формат 24×24, белый, `stroke="#fff"`), затем используйте по имени в чипах, кнопках, top bar.

## Карта навигации (`map/`)

Vite-приложение: `npm run dev` → http://localhost:7100.

- Панорама — drag, масштаб — колесо, клик по экрану — крупный просмотр.
- `src/main.js` — список экранов `NODES` (координаты, группа цвета) и переходов `EDGES` (полилинии в координатах канвы 3500×1250); `GROUP_COLOR` — цвета потоков.
- SVG для карты копируются в `map/public/screens/` — не забудьте синхронизировать после регенерации.

## Список экранов

| № | Файл | Экран | Источник |
|---|---|---|---|
| 01 | 01-my-programs | Мои программы | `MyStartScreen.kt` |
| 02 | 02-templates | Шаблоны | `DefailtStartScreen.kt` |
| 03 | 03-settings | Настройки | `ProfileScreen.kt` |
| 04 | 04-cycle-detail | Детали цикла | `CycleDetailScreenM3.kt` |
| 05 | 05-default-cycle-detail | Детали шаблона цикла | `DefaultCycleDetailScreenM3.kt` |
| 06 | 06-workout-detail | Детали тренировки | `WorkoutDetailScreenM3.kt` |
| 07 | 07-default-workout-detail | Детали шаблона тренировки | `DefaultWorkoutDetailScreenM3.kt` |
| 08 | 08-sets-detail | Подходы (мои) | `MyExerciseDetailScreen.kt` |
| 09 | 09-default-sets | Подходы (шаблон) | `DefaultExerciseDetailScreen.kt` |
| 10 | 10-workout-review | Просмотр тренировки | `WorkoutReviewScreen.kt` |
| 11 | 11-exercise-review | Просмотр упражнения | `ExersiceReviewScreen.kt` |
| 12 | 12-cycle-create-edit | Создать/править цикл | `CreateEditeCycleScreen.kt` |
| 13 | 13-workout-create | Создать тренировку | `CreateWorkoutScreen.kt` |
| 14 | 14-workout-edit | Правка тренировки | `EditeWorkoutScreen.kt` |
| 15 | 15-sound-timer | Интервал нагрузки | `EditSoundTimerScreen.kt` |
| 16 | 16-set-create | Создать подход | `SetsCreateScreen.kt` |
| 17 | 17-set-edit | Правка подхода | `SetEditeScreen.kt` |
| 18 | 18-exercise-choose | Выбор упражнения | `ExerciseChooseScreen.kt` |
| 19 | 19-backup-choose | Экспорт (выбор) | `ProfileChooseBackupScreen.kt` |
| 20 | 20-restore-choose | Импорт (выбор) | `ProfileChooseRestoreScreen.kt` |
