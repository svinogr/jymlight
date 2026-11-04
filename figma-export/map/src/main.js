import './style.css'

// Расстановка фреймов (координаты верхнего левого угла, px на канве 3500×1250)
const NODES = [
  { id: '01', file: '01-my-programs',       title: 'Мои программы',            group: 'main',      x: 80,   y: 140 },
  { id: '04', file: '04-cycle-detail',      title: 'Детали цикла',             group: 'main',      x: 360,  y: 140 },
  { id: '06', file: '06-workout-detail',    title: 'Детали тренировки',        group: 'main',      x: 640,  y: 140 },
  { id: '08', file: '08-sets-detail',       title: 'Подходы (мои)',            group: 'main',      x: 920,  y: 140 },
  { id: '10', file: '10-workout-review',    title: 'Просмотр тренировки',      group: 'review',    x: 1200, y: 140 },
  { id: '11', file: '11-exercise-review',   title: 'Просмотр упражнения',      group: 'review',    x: 1480, y: 140 },
  { id: '02', file: '02-templates',         title: 'Шаблоны',                  group: 'templates', x: 1900, y: 140 },
  { id: '05', file: '05-default-cycle-detail',    title: 'Детали шаблона цикла',     group: 'templates', x: 2180, y: 140 },
  { id: '07', file: '07-default-workout-detail',  title: 'Детали шаблона тренировки', group: 'templates', x: 2460, y: 140 },
  { id: '09', file: '09-default-sets',      title: 'Подходы (шаблон)',         group: 'templates', x: 2740, y: 140 },
  { id: '03', file: '03-settings',          title: 'Настройки',                group: 'settings',  x: 3100, y: 140 },
  { id: '12', file: '12-cycle-create-edit', title: 'Создать/править цикл',     group: 'main',      x: 80,   y: 700 },
  { id: '13', file: '13-workout-create',    title: 'Создать тренировку',       group: 'main',      x: 360,  y: 700 },
  { id: '18', file: '18-exercise-choose',   title: 'Выбор упражнения',         group: 'main',      x: 640,  y: 700 },
  { id: '14', file: '14-workout-edit',      title: 'Правка тренировки',        group: 'main',      x: 920,  y: 700 },
  { id: '16', file: '16-set-create',        title: 'Создать подход',           group: 'main',      x: 1200, y: 700 },
  { id: '17', file: '17-set-edit',          title: 'Правка подхода',           group: 'main',      x: 1480, y: 700 },
  { id: '15', file: '15-sound-timer',       title: 'Интервал нагрузки',        group: 'review',    x: 1760, y: 700 },
  { id: '19', file: '19-backup-choose',     title: 'Экспорт (выбор)',          group: 'settings',  x: 3100, y: 700 },
  { id: '20', file: '20-restore-choose',    title: 'Импорт (выбор)',           group: 'settings',  x: 3380, y: 700 },
]

const GROUP_COLOR = {
  main: '#82B4E8',
  templates: '#7ED6A5',
  review: '#C89BE8',
  settings: '#E8C07A',
  copy: '#A9B7C6',
}

// Полилинии переходов (абсолютные точки канвы)
const EDGES = [
  { from: '01', to: '04', label: 'открыть программу', pts: [[242, 315], [360, 315]] },
  { from: '01', to: '12', label: 'FAB · Добавить программу', pts: [[161, 491], [161, 700]] },
  { from: '04', to: '06', label: 'открыть тренировку', pts: [[522, 315], [640, 315]] },
  { from: '04', to: '13', label: 'FAB · Добавить тренировку', pts: [[441, 491], [441, 700]] },
  { from: '04', to: '12', label: 'чип «редактровать»', pts: [[400, 491], [400, 575], [161, 575], [161, 700]] },
  { from: '06', to: '08', label: 'открыть упражнение', pts: [[802, 315], [920, 315]] },
  { from: '06', to: '18', label: 'FAB · Добавить', pts: [[721, 491], [721, 700]] },
  { from: '06', to: '10', label: 'чип «просмотр»', pts: [[802, 340], [1200, 340]] },
  { from: '06', to: '14', label: 'чип «редактровать»', pts: [[760, 491], [760, 640], [1001, 640], [1001, 700]] },
  { from: '08', to: '16', label: 'FAB · добавить подход', pts: [[1001, 491], [1001, 600], [1281, 600], [1281, 700]] },
  { from: '08', to: '17', label: 'открыть подход', pts: [[1040, 491], [1040, 560], [1561, 560], [1561, 700]] },
  { from: '18', to: '08', label: 'выбрать → детали упражнения', pts: [[802, 881], [860, 881], [860, 470], [1001, 470], [1001, 491]] },
  { from: '10', to: '11', label: 'открыть упражнение', pts: [[1362, 315], [1480, 315]] },
  { from: '10', to: '15', label: 'изменить интервал', pts: [[1320, 491], [1320, 620], [1841, 620], [1841, 700]] },
  { from: '02', to: '05', label: 'открыть', pts: [[2062, 315], [2180, 315]] },
  { from: '05', to: '07', label: 'открыть', pts: [[2342, 315], [2460, 315]] },
  { from: '07', to: '09', label: 'открыть', pts: [[2622, 315], [2740, 315]] },
  { from: '05', to: '01', label: 'скопировать → в «Мои программы»', dash: true, pts: [[2261, 140], [2261, 60], [161, 60], [161, 140]] },
  { from: '03', to: '19', label: 'Экспорт базы на Email', pts: [[3181, 491], [3181, 700]] },
  { from: '03', to: '20', label: 'Импорт базы', pts: [[3240, 491], [3240, 640], [3340, 640], [3340, 881], [3380, 881]] },
]

const BOARD_W = 3500
const BOARD_H = 1250
const PW = 162
const PH = 351

const canvas = document.getElementById('canvas')
canvas.style.width = BOARD_W + 'px'
canvas.style.height = BOARD_H + 'px'

// ---------- рендер стрелок ----------
function arrowHead(pts, color) {
  const [x1, y1] = pts[pts.length - 2]
  const [x2, y2] = pts[pts.length - 1]
  const ang = Math.atan2(y2 - y1, x2 - x1)
  const a = 10, w = 5.5
  const p1 = [x2, y2]
  const p2 = [x2 - a * Math.cos(ang) + w * Math.sin(ang), y2 - a * Math.sin(ang) - w * Math.cos(ang)]
  const p3 = [x2 - a * Math.cos(ang) - w * Math.sin(ang), y2 - a * Math.sin(ang) + w * Math.cos(ang)]
  return `<polygon points="${p1} ${p2} ${p3}" fill="${color}"/>`
}

function polyLength(pts) {
  let L = 0
  for (let i = 1; i < pts.length; i++) L += Math.hypot(pts[i][0] - pts[i-1][0], pts[i][1] - pts[i-1][1])
  return L
}

function pointAt(pts, dist) {
  let left = dist
  for (let i = 1; i < pts.length; i++) {
    const seg = Math.hypot(pts[i][0] - pts[i-1][0], pts[i][1] - pts[i-1][1])
    if (left <= seg) {
      const t = seg === 0 ? 0 : left / seg
      return [pts[i-1][0] + (pts[i][0] - pts[i-1][0]) * t, pts[i-1][1] + (pts[i][1] - pts[i-1][1]) * t]
    }
    left -= seg
  }
  return pts[pts.length - 1]
}

function renderEdges() {
  const svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg')
  svg.setAttribute('id', 'edges')
  svg.setAttribute('width', BOARD_W)
  svg.setAttribute('height', BOARD_H)
  svg.setAttribute('viewBox', `0 0 ${BOARD_W} ${BOARD_H}`)
  for (const e of EDGES) {
    const src = NODES.find(n => n.id === e.from)
    const color = e.dash ? GROUP_COLOR.copy : GROUP_COLOR[src.group]
    const d = 'M ' + e.pts.map(p => p.join(' ')).join(' L ')
    const g = document.createElementNS('http://www.w3.org/2000/svg', 'g')
    g.setAttribute('class', 'edge')
    g.dataset.from = e.from
    g.dataset.to = e.to
    g.style.color = color
    const dashAttr = e.dash ? ' stroke-dasharray="7 6"' : ''
    g.innerHTML = `
      <path class="line" d="${d}" stroke="${color}"${dashAttr}/>
      ${arrowHead(e.pts, color)}
      <path class="hit" d="${d}"/>
      <text x="0" y="0">${e.label}</text>`
    const [mx, my] = pointAt(e.pts, polyLength(e.pts) * 0.5)
    const t = g.querySelector('text')
    t.setAttribute('x', mx + 8)
    t.setAttribute('y', my - 6)
    g.addEventListener('mouseenter', () => highlight(e.from, e.to))
    g.addEventListener('mouseleave', clearHighlight)
    svg.appendChild(g)
  }
  canvas.appendChild(svg)
}

function highlight(a, b) {
  document.querySelectorAll('.edge').forEach(el => {
    const hit = el.dataset.from === a || el.dataset.to === a || el.dataset.from === b || el.dataset.to === b
    el.classList.toggle('hot', hit || (el.dataset.from === a && el.dataset.to === b))
  })
  document.querySelectorAll('.phone').forEach(el => {
    const on = el.dataset.id === a || el.dataset.id === b
    el.style.borderColor = on ? '#ffffff' : ''
  })
}
function clearHighlight() {
  document.querySelectorAll('.edge.hot').forEach(el => el.classList.remove('hot'))
  document.querySelectorAll('.phone').forEach(el => el.style.borderColor = '')
}

// ---------- рендер телефонов ----------
function renderPhones() {
  for (const n of NODES) {
    const div = document.createElement('div')
    div.className = 'phone'
    div.dataset.id = n.id
    div.style.left = n.x + 'px'
    div.style.top = n.y + 'px'
    div.innerHTML = `
      <img src="/screens/${n.file}.svg" alt="${n.title}" draggable="false"/>
      <div class="cap"><b>${n.id}</b> · ${n.title}</div>`
    div.addEventListener('mouseenter', () => highlight(n.id, null))
    div.addEventListener('mouseleave', clearHighlight)
    div.addEventListener('click', () => openModal(n))
    canvas.appendChild(div)
  }
}

// ---------- модалка ----------
const modal = document.getElementById('modal')
const modalImg = document.getElementById('modal-img')
const modalTitle = document.getElementById('modal-title')
function openModal(n) {
  modalImg.src = `/screens/${n.file}.svg`
  modalTitle.textContent = `${n.id} · ${n.title}`
  modal.classList.remove('hidden')
}
document.getElementById('modal-close').addEventListener('click', () => modal.classList.add('hidden'))
modal.addEventListener('click', e => { if (e.target === modal) modal.classList.add('hidden') })
window.addEventListener('keydown', e => { if (e.key === 'Escape') modal.classList.add('hidden') })

// ---------- панорама и масштаб ----------
const viewport = document.getElementById('viewport')
let scale = 0.32, tx = 40, ty = 60

function apply() {
  canvas.style.transform = `translate(${tx}px, ${ty}px) scale(${scale})`
}

viewport.addEventListener('wheel', e => {
  e.preventDefault()
  const k = Math.min(2.5, Math.max(0.15, scale * Math.exp(-e.deltaY * 0.0012)))
  const mx = e.clientX, my = e.clientY
  tx = mx - (mx - tx) * (k / scale)
  ty = my - (my - ty) * (k / scale)
  scale = k
  apply()
}, { passive: false })

let drag = null
viewport.addEventListener('pointerdown', e => {
  if (e.target.closest('.phone') || e.target.closest('#legend') || e.target.closest('#topbar')) return
  drag = { x: e.clientX, y: e.clientY, tx, ty }
  viewport.classList.add('dragging')
  viewport.setPointerCapture(e.pointerId)
})
viewport.addEventListener('pointermove', e => {
  if (!drag) return
  tx = drag.tx + (e.clientX - drag.x)
  ty = drag.ty + (e.clientY - drag.y)
  apply()
})
viewport.addEventListener('pointerup', () => { drag = null; viewport.classList.remove('dragging') })

// стартовое вписывание доски в окно
function fit() {
  const w = window.innerWidth, h = window.innerHeight
  scale = Math.min((w - 60) / BOARD_W, (h - 140) / BOARD_H)
  tx = (w - BOARD_W * scale) / 2
  ty = 90 + (h - 140 - BOARD_H * scale) / 2
  apply()
}
window.addEventListener('resize', fit)

renderEdges()
renderPhones()
fit()
