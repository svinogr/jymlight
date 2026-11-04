# -*- coding: utf-8 -*-
"""Генератор SVG-макетов экранов приложения UPump Light (jymlight).

Тема приложения (всегда тёмная, см. Theme.kt):
  primary/background = #05559B, onPrimary = #FFFFFF,
  primaryContainer (accent) = #5584AF, outlineVariant = #CF6679
"""
import os

OUT = os.path.join(os.path.dirname(os.path.abspath(__file__)), "screens")
os.makedirs(OUT, exist_ok=True)

PRIMARY = "#05559B"
PRIMARY_DARK = "#0A4C88"   # карточки/панели на фоне primary
ROW_ALT = "#0B589E"        # чередование строк
ACCENT = "#5584AF"
WHITE = "#FFFFFF"
ERROR = "#CF6679"
LBLUE = "#9CC3E5"          # вторичный текст/лейблы
IMG1, IMG2 = "#0E63AC", "#042E54"
FONT = "Roboto, 'Segoe UI', Arial, sans-serif"

W, H = 360, 780


# ---------- примитивы ----------

def esc(t):
    return (t.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;"))


def rect(x, y, w, h, fill, rx=0, stroke=None, sw=1, opacity=None):
    s = f'<rect x="{x}" y="{y}" width="{w}" height="{h}"'
    if rx:
        s += f' rx="{rx}"'
    s += f' fill="{fill}"'
    if stroke:
        s += f' stroke="{stroke}" stroke-width="{sw}"'
    if opacity is not None:
        s += f' opacity="{opacity}"'
    return s + "/>"


def line(x1, y1, x2, y2, stroke, sw=1, opacity=None, dash=None):
    s = f'<line x1="{x1}" y1="{y1}" x2="{x2}" y2="{y2}" stroke="{stroke}" stroke-width="{sw}"'
    if opacity is not None:
        s += f' opacity="{opacity}"'
    if dash:
        s += f' stroke-dasharray="{dash}"'
    return s + "/>"


def text(x, y, s, size=14, fill=WHITE, weight=400, anchor="start", opacity=None, family=FONT, spacing=None):
    a = (f'<text x="{x}" y="{y}" font-family="{family}" font-size="{size}" '
         f'fill="{fill}" font-weight="{weight}" text-anchor="{anchor}"')
    if opacity is not None:
        a += f' opacity="{opacity}"'
    if spacing:
        a += f' letter-spacing="{spacing}"'
    return a + f'>{esc(s)}</text>'


# ---------- иконки (белые контурные, бокс 24) ----------

_ICONS = {
    "back": '<path d="M15 5 L8 12 L15 19" fill="none" stroke="#fff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>',
    "info": '<circle cx="12" cy="12" r="9" fill="none" stroke="#fff" stroke-width="1.6"/><line x1="12" y1="11" x2="12" y2="16.2" stroke="#fff" stroke-width="1.8" stroke-linecap="round"/><circle cx="12" cy="8" r="1.15" fill="#fff"/>',
    "add": '<path d="M12 5.5 V18.5 M5.5 12 H18.5" stroke="#fff" stroke-width="2" stroke-linecap="round"/>',
    "save": '<rect x="5" y="5" width="14" height="14" rx="2" fill="none" stroke="#fff" stroke-width="1.6"/><path d="M8.5 5 V9.5 H15.5 V5 M8.5 19 V13.5 H15.5 V19" fill="none" stroke="#fff" stroke-width="1.4"/>',
    "edit": '<path d="M15.8 4.6 l3.6 3.6 L8.6 19 l-4.2 1.2 1.2-4.2 Z" fill="none" stroke="#fff" stroke-width="1.6" stroke-linejoin="round"/>',
    "delete": '<path d="M5 7 H19 M9.5 7 V5 h5 v2 M7 7 l0.8 13 h8.4 L17 7" fill="none" stroke="#fff" stroke-width="1.6" stroke-linecap="round" stroke-linejoin="round"/><line x1="10.3" y1="10.5" x2="10.6" y2="16.5" stroke="#fff" stroke-width="1.3"/><line x1="13.7" y1="10.5" x2="13.4" y2="16.5" stroke="#fff" stroke-width="1.3"/>',
    "comment": '<path d="M4.5 6.5 h15 v10 h-9 l-3.5 3.5 v-3.5 h-2.5 Z" fill="none" stroke="#fff" stroke-width="1.5" stroke-linejoin="round"/><circle cx="9" cy="11.5" r="0.9" fill="#fff"/><circle cx="12" cy="11.5" r="0.9" fill="#fff"/><circle cx="15" cy="11.5" r="0.9" fill="#fff"/>',
    "copy": '<rect x="8.5" y="8.5" width="10" height="10" rx="1.5" fill="none" stroke="#fff" stroke-width="1.5"/><path d="M5.5 15.5 V5.5 H15.5" fill="none" stroke="#fff" stroke-width="1.5"/>',
    "eye": '<path d="M2.8 12 C6.2 6.8, 17.8 6.8, 21.2 12 C17.8 17.2, 6.2 17.2, 2.8 12 Z" fill="none" stroke="#fff" stroke-width="1.5"/><circle cx="12" cy="12" r="2.6" fill="none" stroke="#fff" stroke-width="1.5"/>',
    "send": '<path d="M3 11 L21 3.5 L14.5 20.5 L11.5 13.5 Z" fill="none" stroke="#fff" stroke-width="1.5" stroke-linejoin="round"/><line x1="21" y1="3.5" x2="11.5" y2="13.5" stroke="#fff" stroke-width="1.3"/>',
    "download": '<path d="M12 4 V13 M8.2 9.6 L12 13.4 L15.8 9.6" fill="none" stroke="#fff" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"/><path d="M5 16.5 V19.5 H19 V16.5" fill="none" stroke="#fff" stroke-width="1.8" stroke-linecap="round"/>',
    "check": '<path d="M5 12.5 l4.5 4.5 L19 7.5" fill="none" stroke="#fff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>',
    "camera": '<rect x="3" y="7" width="18" height="13" rx="2.2" fill="none" stroke="#fff" stroke-width="1.5"/><circle cx="12" cy="13.2" r="3.4" fill="none" stroke="#fff" stroke-width="1.5"/><path d="M8.5 7 L10 4.8 H14 L15.5 7" fill="none" stroke="#fff" stroke-width="1.5" stroke-linejoin="round"/>',
    "programs": '<rect x="6" y="4.5" width="12" height="15.5" rx="2" fill="none" stroke="#fff" stroke-width="1.6"/><line x1="9" y1="9" x2="15" y2="9" stroke="#fff" stroke-width="1.4" stroke-linecap="round"/><line x1="9" y1="12.5" x2="15" y2="12.5" stroke="#fff" stroke-width="1.4" stroke-linecap="round"/><line x1="9" y1="16" x2="13" y2="16" stroke="#fff" stroke-width="1.4" stroke-linecap="round"/>',
    "templates": '<rect x="4.5" y="4.5" width="15" height="6.5" rx="1.6" fill="none" stroke="#fff" stroke-width="1.6"/><rect x="4.5" y="13" width="15" height="6.5" rx="1.6" fill="none" stroke="#fff" stroke-width="1.6"/>',
    "settings": '<circle cx="12" cy="12" r="3.4" fill="none" stroke="#fff" stroke-width="1.6"/><path d="M12 2.8 v2.6 M12 18.6 v2.6 M2.8 12 h2.6 M18.6 12 h2.6 M5.5 5.5 l1.8 1.8 M16.7 16.7 l1.8 1.8 M18.5 5.5 l-1.8 1.8 M7.3 16.7 l-1.8 1.8" stroke="#fff" stroke-width="1.5" stroke-linecap="round"/>',
    "play": '<path d="M8.5 5.5 L18 12 L8.5 18.5 Z" fill="#fff"/>',
    "stop": '<rect x="6.5" y="6.5" width="11" height="11" rx="1.5" fill="#fff"/>',
    "sound": '<path d="M4 9.5 v5 h3.2 L12 18.6 V5.4 L7.2 9.5 Z" fill="#fff"/><path d="M15 9.2 a4.3 4.3 0 0 1 0 5.6 M17.6 7 a8 8 0 0 1 0 10" fill="none" stroke="#fff" stroke-width="1.5" stroke-linecap="round"/>',
}


def icon(name, x, y, size=24, opacity=1.0):
    return (f'<g transform="translate({x},{y}) scale({size / 24})" opacity="{opacity}">'
            f'{_ICONS[name]}</g>')


# ---------- каркас экрана ----------

def status_bar():
    s = rect(0, 0, W, 24, PRIMARY)
    s += text(14, 16.5, "12:00", 12, WHITE, 500)
    # сигнал
    for i, h in enumerate((4, 6.5, 9, 11.5)):
        s += rect(296 + i * 5.2, 17 - h, 3.4, h, WHITE, rx=1)
    # батарея
    s += f'<rect x="322" y="8.5" width="20" height="10" rx="2.5" fill="none" stroke="{WHITE}" stroke-width="1.2"/>'
    s += rect(324.5, 11, 12, 5, WHITE, rx=1)
    s += rect(343.5, 11.5, 2, 4, WHITE, rx=1)
    return s


def top_bar(title, actions=(), back=False):
    s = rect(0, 24, W, 56, PRIMARY)
    x = 16
    if back:
        s += icon("back", 12, 42, 22)
        x = 48
    s += text(x, 60, title, 20, WHITE, 600)
    ax = 344
    for a in reversed(actions):
        s += icon(a, ax - 24, 40, 24)
        ax -= 44
    return s


def bottom_nav(active=-1):
    s = rect(0, H - 56, W, 56, PRIMARY)
    s += line(0, H - 56, W, H - 56, WHITE, 1, opacity=0.25)
    items = (("programs", 60), ("templates", 180), ("settings", 300))
    for i, (ic, cx) in enumerate(items):
        op = 1.0 if i == active else 0.55
        s += icon(ic, cx - 13, H - 56 + 15, 26, opacity=op)
        if i == active:
            s += rect(cx - 14, H - 8, 28, 3, WHITE, rx=1.5)
    return s


def fab(lines, icon_name="add", bottom=H - 56 - 16):
    w = max(len(l) for l in lines) * 7.0 + 24 + 18 + 16
    h = 48
    x = W - 16 - w
    y = bottom - h
    s = rect(x + 1.5, y + 2.5, w, h, "#000000", rx=8, opacity=0.28)
    s += rect(x, y, w, h, PRIMARY, rx=8, stroke=WHITE, sw=0.75, opacity=1)
    s += rect(x, y, w, h, "none", rx=8, stroke=WHITE, sw=0.8)
    s += icon(icon_name, x + 12, y + h / 2 - 9, 18)
    ty = y + h / 2 - (len(lines) - 1) * 8 + 5
    for l in lines:
        s += text(x + 36 + (w - 48) / 2, ty, l, 13, WHITE, 500, anchor="middle")
        ty += 16
    return s


def img_block(y, h, label=None, day=None, camera=False, title=None, picker=False):
    s = f'<rect x="0" y="{y}" width="{W}" height="{h}" fill="url(#imggrad)"/>'
    # пиктограмма-горы
    cx, cy = W / 2, y + h / 2 + 8
    s += (f'<g opacity="0.4" stroke="{WHITE}" stroke-width="2" fill="none" stroke-linejoin="round">'
          f'<path d="M{cx - 56} {cy + 26} L{cx - 22} {cy - 14} L{cx - 2} {cy + 10} L{cx + 16} {cy - 8} L{cx + 56} {cy + 26}"/>'
          f'<circle cx="{cx + 30}" cy="{cy - 24}" r="6"/></g>')
    if label:
        s += text(16, y + 20, label, 10.5, WHITE, 400, opacity=0.65)
    if day:
        s += text(16, y + 30, day, 17, WHITE, 600)
    if title:
        s += rect(0, y + h - 34, W, 34, "#000000", opacity=0.30)
        s += text(16, y + h - 11, title, 16, WHITE, 600)
    if camera:
        s += rect(312, y + 10, 38, 38, "#000000", rx=19, opacity=0.35)
        s += icon("camera", 319, y + 17, 24)
    if picker:
        s += icon("camera", 16, y + 12, 22)
    return s


def chips_row(y, chips, x=8):
    s = ""
    cx = x
    for ic, lbl in chips:
        w = len(lbl) * 6.3 + 40
        s += rect(cx, y, w, 32, "#000000", rx=16, opacity=0.22)
        s += rect(cx, y, w, 32, "none", rx=16, stroke=WHITE, sw=1)
        s += icon(ic, cx + 9, y + 9, 14)
        s += text(cx + 28, y + 21, lbl, 12, WHITE, 500)
        cx += w + 8
    return s


def list_row(y, title_s, subtitle=None, right=None, plus=False):
    s = rect(0, y, W, 66, PRIMARY)
    s += rect(8, y + 8, 50, 50, ACCENT, rx=8, opacity=0.85)
    s += icon("programs", 21, y + 21, 24, opacity=0.5)
    s += text(68, y + 28, title_s, 16, WHITE, 500)
    if subtitle:
        s += text(68, y + 48, subtitle, 12, LBLUE)
    if right:
        s += text(352, y + 48, right, 12, LBLUE, anchor="end")
    if plus:
        s += icon("add", 328, y + 21, 24)
    s += line(0, y + 66, W, y + 66, WHITE, 1, opacity=0.14)
    return s


def date_card(y, start="01.11.2026", finish="01.02.2027"):
    s = rect(0, y, W, 54, PRIMARY_DARK)
    s += text(16, y + 20, "Дата начала", 11, LBLUE)
    s += text(16, y + 40, start, 15, WHITE, 600)
    s += line(W / 2, y + 8, W / 2, y + 46, WHITE, 1, opacity=0.3)
    s += text(W / 2 + 16, y + 20, "Дата окончания", 11, LBLUE)
    s += text(W / 2 + 16, y + 40, finish, 15, WHITE, 600)
    s += line(0, y + 54, W, y + 54, WHITE, 1, opacity=0.14)
    return s


def table_header(y):
    s = rect(0, y, W, 36, ROW_ALT)
    s += text(18, y + 23, "№", 12.5, WHITE, 600)
    s += text(108, y + 23, "Вес", 12.5, WHITE, 600, anchor="end")
    s += text(209, y + 23, "Пред. вес", 12, WHITE, 600, anchor="end")
    s += text(324, y + 23, "Повторения", 12.5, WHITE, 600, anchor="end")
    s += line(0, y + 36, W, y + 36, WHITE, 1, opacity=0.25)
    return s


def set_row(y, n, w_, pw, reps, alt=False):
    if alt:
        s = rect(0, y, W, 42, WHITE, opacity=0.05)
    else:
        s = ""
    s += text(18, y + 27, str(n), 14, WHITE)
    s += text(108, y + 27, w_, 14, WHITE, anchor="end")
    s += text(209, y + 27, pw, 13, LBLUE, anchor="end")
    s += text(324, y + 27, reps, 14, WHITE, anchor="end")
    s += line(0, y + 42, W, y + 42, WHITE, 0.75, opacity=0.12)
    return s


def picker_card(y, label, value, sub=None):
    h = 168
    s = rect(12, y, W - 24, h, PRIMARY_DARK, rx=10, stroke=WHITE, sw=0.5, opacity=1)
    s += rect(12, y, W - 24, h, "none", rx=10, stroke=WHITE, sw=0.5, opacity=0.45)
    s += text(24, y + 24, label, 12.5, LBLUE)
    cy = y + 96
    s += rect(28, cy - 26, W - 56, 52, PRIMARY, rx=8, stroke=WHITE, sw=0.9)
    s += text(W / 2 - 24, cy - 32, "▲", 10, WHITE, anchor="middle", opacity=0.7)
    s += text(W / 2 + 24, cy - 32, "▲", 10, WHITE, anchor="middle", opacity=0.7)
    s += text(W / 2 - 24, cy + 40, "▼", 10, WHITE, anchor="middle", opacity=0.7)
    s += text(W / 2 + 24, cy + 40, "▼", 10, WHITE, anchor="middle", opacity=0.7)
    s += text(W / 2 - 24, cy + 8, str(value), 26, WHITE, 600, anchor="middle")
    if sub:
        s += text(24, y + h - 14, sub, 10.5, LBLUE, opacity=0.8)
    return s


def field(y, label, value, hint=False):
    s = rect(0, y, W, 62, PRIMARY)
    s += text(16, y + 22, label, 12, LBLUE)
    s += text(16, y + 46, value, 16, WHITE if not hint else LBLUE, 400 if hint else 500,
              opacity=None if not hint else 0.75)
    s += line(16, y + 56, W - 16, y + 56, WHITE, 1, opacity=0.5)
    return s


def comment_box(y, h, lines_txt):
    s = rect(0, y, W, h, PRIMARY)
    s += text(16, y + 22, "Коментарий", 12, LBLUE)
    s += rect(12, y + 32, W - 24, h - 44, PRIMARY_DARK, rx=8, stroke=WHITE, sw=0.5, opacity=1)
    s += rect(12, y + 32, W - 24, h - 44, "none", rx=8, stroke=WHITE, sw=0.5, opacity=0.4)
    ty = y + 56
    for l in lines_txt:
        s += text(24, ty, l, 13, WHITE, opacity=0.9)
        ty += 20
    return s


def checkbox_row(y, title_s, checked=False):
    s = rect(0, y, W, 56, PRIMARY)
    s += rect(16, y + 17, 22, 22, "none", rx=4, stroke=WHITE, sw=1.5)
    if checked:
        s += rect(16, y + 17, 22, 22, ACCENT, rx=4)
        s += icon("check", 18.5, y + 19.5, 17)
    s += text(52, y + 34, title_s, 16, WHITE, 500)
    s += line(0, y + 56, W, y + 56, WHITE, 1, opacity=0.14)
    return s


def button_row(y, ic, title_s):
    s = rect(0, y, W, 56, PRIMARY)
    s += icon(ic, 16, y + 16, 24)
    s += text(56, y + 34, title_s, 16, WHITE, 500)
    s += line(0, y + 56, W, y + 56, WHITE, 1, opacity=0.14)
    return s


def wrap(sid, name, body, nav=-1, fab_s=None):
    defs = (
        "<defs>"
        f'<linearGradient id="imggrad" x1="0" y1="0" x2="1" y2="1">'
        f'<stop offset="0" stop-color="{IMG1}"/><stop offset="1" stop-color="{IMG2}"/>'
        "</linearGradient>"
        "</defs>"
    )
    s = f'<svg xmlns="http://www.w3.org/2000/svg" width="720" height="1560" viewBox="0 0 {W} {H}">'
    s += f"<title>{esc(name)}</title>"
    s += defs
    s += rect(0, 0, W, H, PRIMARY)
    s += rect(0, 0, W, H, "none", rx=0)
    s += status_bar()
    s += body
    if fab_s:
        s += fab_s
    s += bottom_nav(nav)
    s += "</svg>"
    with open(os.path.join(OUT, f"{sid}.svg"), "w", encoding="utf-8") as f:
        f.write(s)
    print("write", sid)


# ---------- экраны ----------

def s01():
    b = top_bar("Мои программы")
    b += list_row(88, "Базовый круг", right="до 15.12.2026")
    b += list_row(154, "Сила №2", right="до 01.02.2027")
    b += list_row(220, "На рельеф", right="до 20.12.2026")
    b += list_row(286, "Восстановление", right="до 05.01.2027")
    wrap("01-my-programs", "Мои программы (список циклов)", b, nav=0,
         fab_s=fab(["Добавить", "программу"]))


def s02():
    b = top_bar("Шаблоны")
    b += list_row(88, "Для начинающих", subtitle="предустановленая")
    b += list_row(154, "База 5×5", subtitle="предустановленая")
    b += list_row(220, "Круговая тренировка", subtitle="предустановленая")
    b += list_row(286, "Сплит 3 дня", subtitle="предустановленая")
    wrap("02-templates", "Шаблоны (список готовых циклов)", b, nav=1)


def s03():
    b = top_bar("Настройки")
    b += text(16, 116, "Действия с базой данных", 14, WHITE, 600)
    b += button_row(132, "send", "Экспорт базы на Email")
    b += button_row(188, "download", "Импорт базы")
    wrap("03-settings", "Настройки (профиль)", b, nav=2)


def s04():
    b = top_bar("Базовый круг", back=True)
    b += img_block(80, 200, label="изображение программы")
    b += chips_row(240, [("delete", "очистить"), ("edit", "редактровать"), ("comment", "коментарий")])
    b += date_card(280)
    b += list_row(334, "Тренировка А", right="понедельник")
    b += list_row(400, "Тренировка Б", right="среда")
    b += list_row(466, "Тренировка В", right="пятница")
    b += list_row(532, "Тренировка Г", right="суббота")
    wrap("04-cycle-detail", "Детали цикла", b, fab_s=fab(["Добавить", "тренировку"]))


def s05():
    b = top_bar("Для начинающих", back=True)
    b += img_block(80, 200, label="изображение программы")
    b += chips_row(240, [("copy", "скопировать"), ("comment", "коментарий")])
    b += date_card(280)
    b += list_row(334, "Вводная тренировка")
    b += list_row(400, "Full body")
    b += list_row(466, "Ноги + пресс")
    wrap("05-default-cycle-detail", "Детали шаблона цикла", b)


def s06():
    b = top_bar("Тренировка А", back=True)
    b += img_block(80, 200, day="понедельник")
    b += chips_row(240, [("delete", "очистить"), ("eye", "просмотр"),
                         ("comment", "коментарий"), ("edit", "редактровать")])
    b += date_card(280)
    b += list_row(334, "Приседания", subtitle="квадрицепсы")
    b += list_row(400, "Жим лёжа", subtitle="грудные")
    b += list_row(466, "Тяга штанги", subtitle="спина")
    b += list_row(532, "Планка", subtitle="пресс")
    wrap("06-workout-detail", "Детали тренировки", b, fab_s=fab(["Добавить", "тренировку"]))


def s07():
    b = top_bar("Full body база", back=True)
    b += img_block(80, 200, day="среда")
    b += chips_row(240, [("comment", "коментарий")])
    b += date_card(280)
    b += list_row(334, "Приседания", subtitle="квадрицепсы")
    b += list_row(400, "Отжимания", subtitle="грудные")
    b += list_row(466, "Подтягивания", subtitle="спина")
    wrap("07-default-workout-detail", "Детали шаблона тренировки", b)


def s08():
    b = top_bar("Подходы", back=True)
    b += img_block(80, 200, label="изображение упражнения")
    b += chips_row(240, [("delete", "Удалить подходы")], x=170)
    b += table_header(280)
    rows = [("1", "60.00", "55.00", "10"), ("2", "60.00", "55.00", "10"),
            ("3", "60.00", "55.00", "8"), ("4", "55.00", "50.00", "10"),
            ("5", "55.00", "50.00", "8")]
    y = 316
    for i, r in enumerate(rows):
        b += set_row(y, *r, alt=i % 2 == 1)
        y += 42
    wrap("08-sets-detail", "Подходы упражнения (мои)", b, fab_s=fab(["добавить", "подход"]))


def s09():
    b = top_bar("Подходы", back=True)
    b += img_block(80, 200, label="изображение упражнения")
    b += table_header(280)
    rows = [("1", "20.00", "—", "12"), ("2", "20.00", "—", "12"),
            ("3", "20.00", "—", "10")]
    y = 316
    for i, r in enumerate(rows):
        b += set_row(y, *r, alt=i % 2 == 1)
        y += 42
    wrap("09-default-sets", "Подходы упражнения (шаблон)", b)


def s10():
    b = top_bar("Тренировка А", actions=["info"], back=True)
    # список упражнений на просмотр
    b += rect(0, 80, W, 292, PRIMARY)
    items = [("Приседания", "5 подходов"), ("Жим лёжа", "4 подхода"), ("Тяга штанги", "4 подхода")]
    y = 92
    for t, m in items:
        b += list_row(y, t, subtitle=m)
        y += 66
    b += text(16, y + 14, "⋮  свайп вправо — выполнено", 11, LBLUE, opacity=0.8)
    # панель звукового таймера
    y0 = 396
    b += rect(8, y0, W - 16, 118, PRIMARY_DARK, rx=10, stroke=WHITE, sw=0.5)
    b += rect(8, y0, W - 16, 118, "none", rx=10, stroke=WHITE, sw=0.5, opacity=0.45)
    b += icon("sound", 20, y0 + 14, 20)
    b += text(48, y0 + 29, "Звуковой таймер", 13, WHITE, 600)
    b += text(20, y0 + 66, "00:45", 24, WHITE, 600)
    b += text(120, y0 + 66, "старт 10с · финиш 60с", 12, LBLUE)
    b += rect(20, y0 + 78, 96, 28, PRIMARY, rx=14, stroke=WHITE, sw=0.8)
    b += icon("play", 30, y0 + 85, 13)
    b += text(50, y0 + 96, "старт", 12, WHITE, 500)
    b += rect(130, y0 + 78, 96, 28, PRIMARY, rx=14, stroke=WHITE, sw=0.8)
    b += icon("stop", 141, y0 + 86, 12)
    b += text(160, y0 + 96, "стоп", 12, WHITE, 500)
    b += rect(240, y0 + 78, 96, 28, PRIMARY, rx=14, stroke=WHITE, sw=0.8)
    b += icon("edit", 252, y0 + 86, 12)
    b += text(272, y0 + 96, "изменить", 12, WHITE, 500)
    # панель секундомера
    y1 = 530
    b += rect(8, y1, W - 16, 178, PRIMARY_DARK, rx=10, stroke=WHITE, sw=0.5)
    b += rect(8, y1, W - 16, 178, "none", rx=10, stroke=WHITE, sw=0.5, opacity=0.45)
    b += text(W / 2, y1 + 52, "00:12:34", 34, WHITE, 600, anchor="middle")
    labels = [("START", "play", 20), ("PAUSE", "stop", 132), ("STOP", "delete", 244)]
    for lbl, ic, bx in labels:
        b += rect(bx, y1 + 72, 96, 34, PRIMARY, rx=17, stroke=WHITE, sw=0.9)
        s_icon = {"START": "play", "PAUSE": "stop", "STOP": "delete"}[lbl]
        b += icon(s_icon, bx + 12, y1 + 82, 14)
        b += text(bx + 32, y1 + 94, lbl, 12, WHITE, 600)
    b += rect(20, y1 + 126, 20, 20, ACCENT, rx=4)
    b += icon("check", 22, y1 + 128, 16)
    b += text(48, y1 + 141, "сигнал каждую минуту", 13, WHITE)
    wrap("10-workout-review", "Просмотр тренировки (секундомер)", b)


def s11():
    b = top_bar("Тяга штанги", back=True)
    b += img_block(80, 200, label="изображение упражнения")
    b += rect(0, 280, W, 56, PRIMARY_DARK)
    b += text(16, 304, "Группа мышц", 12, LBLUE)
    b += text(16, 326, "Спина", 16, WHITE, 600)
    b += line(0, 336, W, 336, WHITE, 1, opacity=0.14)
    b += rect(0, 336, W, 380, PRIMARY)
    b += text(16, 362, "Описание", 12, LBLUE)
    txt = ["В наклоне, спина прямая,","хват чуть шире плеч. Тяните","штангу к поясу, локти","направлены вверх и назад.","Контролируйте негативную","фазу — опускайте медленно."]
    ty = 390
    for l in txt:
        b += text(16, ty, l, 13.5, WHITE, opacity=0.92)
        ty += 22
    wrap("11-exercise-review", "Просмотр упражнения", b)


def s12():
    b = top_bar("Создать новую программу", back=True)
    b += img_block(80, 200, label="нажмите, чтобы выбрать изображение", title="Новая программа", picker=True)
    b += field(280, "Название программы", "Новая программа")
    b += field(342, "Дата начала", "01.11.2026")
    b += field(404, "Дата окончания", "01.02.2027")
    b += comment_box(466, 250, ["План на зимний сезон: база","и постепенное увеличение","рабочих весов."])
    wrap("12-cycle-create-edit", "Создание/редактирование цикла", b,
         fab_s=fab(["Сохранить"], icon_name="save"))


def s13():
    b = top_bar("Добавить тренировку", back=True)
    b += img_block(80, 200, label="изображение дня", title="Новая тренировка")
    b += field(280, "День недели", "понедельник  ▾")
    b += checkbox_row(342, "Четная неделя", checked=False)
    b += field(398, "Дата начала", "01.11.2026")
    b += field(460, "Дата окончания", "01.02.2027")
    b += field(522, "Название тренировки", "Новая тренировка")
    b += comment_box(584, 132, ["Коментарий к тренировке"])
    wrap("13-workout-create", "Создание тренировки", b,
         fab_s=fab(["Сохранить"], icon_name="save"))


def s14():
    b = top_bar("Редактирование", back=True)
    b += img_block(80, 200, label="изображение дня", title="Тренировка А")
    b += field(280, "День недели", "понедельник  ▾")
    b += checkbox_row(342, "Четная неделя", checked=True)
    b += field(398, "Дата начала", "01.11.2026")
    b += field(460, "Дата окончания", "01.02.2027")
    b += field(522, "Название тренировки", "Тренировка А")
    b += comment_box(584, 132, ["Акцент на базовые движения"])
    wrap("14-workout-edit", "Редактирование тренировки", b,
         fab_s=fab(["Сохранить"], icon_name="save"))


def s15():
    b = top_bar("Создайте интервал нагрузки", actions=["info"], back=True)
    b += picker_card(92, "Задержка в секундах для первого сигнала", "10")
    b += picker_card(276, "Граница для второго сигнала", "60")
    b += text(16, 470, "ⓘ  интервал подаёт два звуковых сигнала", 11, LBLUE, opacity=0.8)
    wrap("15-sound-timer", "Настройка звукового таймера", b,
         fab_s=fab(["Сохранить"], icon_name="save"))


def s16():
    b = top_bar("Создать новый подход", back=True)
    b += picker_card(92, "Вес", "50.00", sub="шаг 1.25 · диапазон 0–200")
    b += picker_card(276, "Количество повторений", "10", sub="диапазон 0–100")
    b += picker_card(460, "Количество добавляемых подходов", "3", sub="диапазон 0–200")
    wrap("16-set-create", "Создание подхода", b,
         fab_s=fab(["Сохранить"], icon_name="save"))


def s17():
    b = top_bar("Изменить подход", back=True)
    b += picker_card(92, "Вес", "60.00", sub="шаг 1.25 · диапазон 0–200")
    b += picker_card(276, "Количество повторений", "10", sub="диапазон 0–100")
    wrap("17-set-edit", "Редактирование подхода", b,
         fab_s=fab(["Сохранить"], icon_name="save"))


def s18():
    b = top_bar("Выберите упражнение", back=True)
    b += img_block(80, 140)
    muscles = ["Плечи", "Грудные", "Трапеции", "Шея", "Трицепс", "Спина"]
    x = 8
    for i, m in enumerate(muscles):
        w = len(m) * 6.0 + 34
        sel = (m == "Спина")
        b += rect(x, 184, w, 28, ACCENT if sel else "none", rx=14,
                  stroke=WHITE, sw=1, opacity=0.95 if sel else 1)
        b += text(x + w / 2, 202, m, 11.5, WHITE, 500, anchor="middle")
        x += w + 6
    b += rect(316, 184, 36, 28, PRIMARY, opacity=0.0)
    y = 228
    for t, sub in [("Тяга штанги", "спина"), ("Подтягивания", "спина"), ("Тяга блока", "спина")]:
        b += list_row(y, t, subtitle=sub, plus=True)
        y += 66
    b += list_row(y, "Гиперэкстензия", subtitle="спина", plus=True)
    wrap("18-exercise-choose", "Выбор упражнения из шаблонов", b)


def s19():
    b = top_bar("Выберите программы", back=True)
    b += checkbox_row(88, "Базовый круг", checked=True)
    b += checkbox_row(144, "Сила №2", checked=False)
    b += checkbox_row(200, "На рельеф", checked=True)
    b += text(16, 280, "выбранные программы будут отправлены", 11, LBLUE, opacity=0.8)
    b += text(16, 296, "на email как JSON-бекап", 11, LBLUE, opacity=0.8)
    wrap("19-backup-choose", "Выбор программ для экспорта", b,
         fab_s=fab(["Сохранить?"], icon_name="send"))


def s20():
    b = top_bar("Выберите программы", back=True)
    b += checkbox_row(88, "Базовый круг (бэкап 01.11)", checked=True)
    b += checkbox_row(144, "Старая программа", checked=False)
    b += text(16, 216, "прочитано из файла restore.json", 11, LBLUE, opacity=0.8)
    wrap("20-restore-choose", "Выбор программ для импорта", b,
         fab_s=fab(["Сохранить?"], icon_name="download"))


for fn in (s01, s02, s03, s04, s05, s06, s07, s08, s09, s10,
           s11, s12, s13, s14, s15, s16, s17, s18, s19, s20):
    fn()

print("done:", len([f for f in os.listdir(OUT) if f.endswith('.svg')]), "svg in", OUT)
