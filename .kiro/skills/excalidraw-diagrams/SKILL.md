---
name: excalidraw-diagrams
description: Create and embed Excalidraw diagrams in markdown files. Use when asked to create diagrams, architecture drawings, flowcharts, or visual illustrations for notes in this repo.
version: 1.0.0
tags: [skill, excalidraw, diagrams, svg, markdown]
---

# Excalidraw Diagrams

## Overview

Create Excalidraw diagrams as `.excalidraw.svg` files. The Kiro IDE extension auto-exports them as SVG on re-save, making them directly embeddable in markdown.

## Usage

Use this skill when:
- Asked to create a diagram, chart, or visual illustration
- Designing architecture or system design visuals
- Adding flowcharts or concept maps to notes
- Embedding visuals in any markdown page in this repo

## Core Concepts

### File Extension

Always use `.excalidraw.svg` — not `.excalidraw` alone. The Kiro IDE Excalidraw extension detects this extension and auto-exports the SVG on save, so the file is both editable and directly renderable.

### File Placement

Place diagram files in an `attachments/` subdirectory relative to the markdown file that embeds them:

```
system-design/
├── networking.md
└── attachments/
    └── tcp-handshake.excalidraw.svg
```

### Embedding in Markdown

Use standard markdown image syntax:

```markdown
![TCP Handshake](attachments/tcp-handshake.excalidraw.svg)
```

### Fonts

- **Headings and titles**: Use `Excalidraw` font (`fontFamily: 1` in JSON)
- **Normal text / labels**: Use `comic shanns` font (`fontFamily: 3` in JSON) — it is clearer for body text

## Creating a Diagram File

Write the file as **pure Excalidraw JSON** — the same format as a `.excalidraw` file — just saved with the `.excalidraw.svg` extension. Do NOT write SVG markup. The Kiro IDE extension reads the JSON, renders it, and exports the SVG automatically on save.

Minimal file structure:

```json
{
  "type": "excalidraw",
  "version": 2,
  "source": "https://excalidraw.com",
  "elements": [],
  "appState": {
    "gridSize": null,
    "viewBackgroundColor": "#ffffff"
  },
  "files": {}
}
```

### Font Family Values in JSON

| Value | Font |
|-------|------|
| `1` | Excalidraw (hand-drawn, use for headings/titles) |
| `2` | Normal (sans-serif) |
| `3` | Code / comic shanns (use for body text and labels) |
| `4` | Script |

### Element Types

Common element types in the JSON `elements` array:

- `rectangle` — boxes, containers
- `ellipse` — circles, ovals
- `arrow` — directed connections
- `line` — undirected lines
- `text` — standalone text labels
- `diamond` — decision nodes

### Minimal Element Schema

```json
{
  "id": "unique-id",
  "type": "rectangle",
  "x": 100,
  "y": 100,
  "width": 200,
  "height": 80,
  "angle": 0,
  "strokeColor": "#1e1e1e",
  "backgroundColor": "transparent",
  "fillStyle": "solid",
  "strokeWidth": 2,
  "strokeStyle": "solid",
  "roughness": 1,
  "opacity": 100,
  "groupIds": [],
  "roundness": {"type": 3},
  "isDeleted": false,
  "boundElements": [],
  "updated": 1,
  "link": null,
  "locked": false
}
```

For `text` elements, add:

```json
{
  "type": "text",
  "text": "Label",
  "fontSize": 20,
  "fontFamily": 3,
  "textAlign": "center",
  "verticalAlign": "middle",
  "baseline": 18
}
```

For `arrow` elements, add:

```json
{
  "type": "arrow",
  "startBinding": {"elementId": "source-id", "focus": 0, "gap": 1},
  "endBinding": {"elementId": "target-id", "focus": 0, "gap": 1},
  "points": [[0, 0], [100, 0]]
}
```

## Editing an Existing Diagram

After you re-save a `.excalidraw.svg` in Kiro, the file becomes SVG with the Excalidraw JSON **base64+zlib+bstring encoded** inside `<metadata>`. Use the helper script to decode, modify, and re-encode in a single Python script.

**Workflow — run Python inline via heredoc, no temp files:**

```bash
python3 - <<'EOF'
import sys
sys.path.insert(0, '.kiro/skills/excalidraw-diagrams/scripts')
from excalidraw_svg_edit import decode_svg, encode_svg

svg_path = 'path/to/attachments/name.excalidraw.svg'
data = decode_svg(svg_path)

# Inspect current elements
for e in data['elements']:
    print(e.get('id'), e.get('type'), e.get('text', ''))

# Make changes — add, remove, or modify elements
data['elements'].append({
    "id": "new-element",
    "type": "text",
    "x": 100, "y": 500,
    "width": 200, "height": 25,
    "angle": 0,
    "strokeColor": "#1e1e1e",
    "backgroundColor": "transparent",
    "fillStyle": "solid",
    "strokeWidth": 1,
    "strokeStyle": "solid",
    "roughness": 1,
    "opacity": 100,
    "groupIds": [],
    "roundness": None,
    "isDeleted": False,
    "boundElements": [],
    "updated": 1,
    "link": None,
    "locked": False,
    "text": "New label",
    "fontSize": 16,
    "fontFamily": 3,
    "textAlign": "left",
    "verticalAlign": "top",
    "baseline": 18
})

encode_svg(svg_path, data)
EOF
```

After running, the SVG payload is updated. The user must re-open and re-save the file in Kiro to regenerate the SVG rendering from the new JSON.

## Quick Reference

| Task | Detail |
|------|--------|
| File extension | `.excalidraw.svg` |
| Diagram location | `attachments/` next to the `.md` file |
| Embed syntax | `![alt text](attachments/name.excalidraw.svg)` |
| Title/heading font | `fontFamily: 1` (Excalidraw) |
| Body/label font | `fontFamily: 3` (comic shanns) |
| Stroke color | `#1e1e1e` (dark, hand-drawn look) |
| Roughness | `1` for hand-drawn style |

## Common Mistakes

- **Wrong extension**: Using `.excalidraw` instead of `.excalidraw.svg` — the SVG won't render in markdown
- **Wrong font for labels**: Using `fontFamily: 1` for body text — use `fontFamily: 3` (comic shanns) for clarity
- **Flat file placement**: Putting diagrams next to the markdown file instead of in `attachments/` — clutters the directory
- **Missing `attachments/` directory**: Create it if it doesn't exist before placing the file
