#!/usr/bin/env python3
"""
excalidraw_svg_edit.py — decode, edit, and re-encode .excalidraw.svg files.

The Excalidraw IDE extension stores the diagram JSON as:
  base64( JSON({ version, encoding:"bstring", compressed:true, encoded:<bstring> }) )
where <bstring> is a zlib-compressed byte stream encoded as a string where
each character's ordinal equals the original byte value (latin-1 style).

Usage:
  # Decode: extract the Excalidraw JSON from an SVG and write to a .json file
  python excalidraw_svg_edit.py decode path/to/file.excalidraw.svg

  # Encode: take a modified .json file and write it back into the SVG
  python excalidraw_svg_edit.py encode path/to/file.excalidraw.svg path/to/file.json
"""

import sys
import re
import json
import zlib
import base64


PAYLOAD_RE = re.compile(
    r'<!-- payload-start -->(.*?)<!-- payload-end -->',
    re.DOTALL
)


def decode_payload(b64_payload: str) -> dict:
    """Decode a base64 Excalidraw payload string to a Python dict."""
    raw_bytes = base64.b64decode(b64_payload.strip())

    # The outer wrapper is JSON encoded as latin-1 (bstring uses raw byte values)
    outer = json.loads(raw_bytes.decode('latin-1'))

    if outer.get('encoding') != 'bstring' or not outer.get('compressed'):
        raise ValueError(f"Unexpected encoding: {outer.get('encoding')}, compressed={outer.get('compressed')}")

    # bstring: each char's ordinal is the byte value
    compressed = bytes(ord(c) & 0xFF for c in outer['encoded'])

    raw_json = zlib.decompress(compressed)
    return json.loads(raw_json.decode('utf-8'))


def encode_payload(excalidraw_data: dict) -> str:
    """Encode a Python dict back to a base64 Excalidraw payload string."""
    raw_json = json.dumps(excalidraw_data, ensure_ascii=False).encode('utf-8')

    compressed = zlib.compress(raw_json)

    # bstring: convert each byte to a char with that ordinal
    bstring_encoded = ''.join(chr(b) for b in compressed)

    outer = {
        "version": "1",
        "encoding": "bstring",
        "compressed": True,
        "encoded": bstring_encoded
    }

    # Encode outer JSON as latin-1 bytes then base64
    outer_bytes = json.dumps(outer, ensure_ascii=False).encode('latin-1')
    return base64.b64encode(outer_bytes).decode('ascii')


def decode_svg(svg_path: str) -> dict:
    """Extract and decode the Excalidraw JSON from an .excalidraw.svg file."""
    with open(svg_path, 'r', encoding='utf-8') as f:
        svg_content = f.read()

    match = PAYLOAD_RE.search(svg_content)
    if not match:
        raise ValueError(f"No Excalidraw payload found in {svg_path}")

    return decode_payload(match.group(1))


def encode_svg(svg_path: str, excalidraw_data: dict, output_path: str = None):
    """Re-encode modified Excalidraw JSON back into an .excalidraw.svg file."""
    with open(svg_path, 'r', encoding='utf-8') as f:
        svg_content = f.read()

    match = PAYLOAD_RE.search(svg_content)
    if not match:
        raise ValueError(f"No Excalidraw payload found in {svg_path}")

    new_payload = encode_payload(excalidraw_data)
    new_svg = PAYLOAD_RE.sub(
        f'<!-- payload-start -->{new_payload}<!-- payload-end -->',
        svg_content
    )

    out_path = output_path or svg_path
    with open(out_path, 'w', encoding='utf-8') as f:
        f.write(new_svg)

    print(f"Written to {out_path}")


def cmd_decode(svg_path: str):
    import os, tempfile
    data = decode_svg(svg_path)
    # Write to /tmp to avoid polluting the attachments directory
    basename = os.path.basename(svg_path).replace('.excalidraw.svg', '.excalidraw.json')
    out_path = os.path.join(tempfile.gettempdir(), basename)
    with open(out_path, 'w', encoding='utf-8') as f:
        json.dump(data, f, indent=2, ensure_ascii=False)
    print(f"Decoded {len(data.get('elements', []))} elements → {out_path}")
    return out_path


def cmd_encode(svg_path: str, json_path: str):
    with open(json_path, 'r', encoding='utf-8') as f:
        data = json.load(f)
    encode_svg(svg_path, data)


if __name__ == '__main__':
    if len(sys.argv) < 3:
        print(__doc__)
        sys.exit(1)

    command = sys.argv[1]
    if command == 'decode':
        cmd_decode(sys.argv[2])
    elif command == 'encode':
        if len(sys.argv) < 4:
            print("encode requires: svg_path json_path")
            sys.exit(1)
        cmd_encode(sys.argv[2], sys.argv[3])
    else:
        print(f"Unknown command: {command}")
        print(__doc__)
        sys.exit(1)
