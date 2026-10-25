# Better Tusk

Claude-style skin for Tusk Central AI, in the style of Better DeepSeek.

**How it works, like Better DeepSeek:**
- This is a browser extension, not a separate app.
- It runs ON https://new.tusksearch.com/ai itself.
- Tusk stays the backend/provider — sign-in, models and chats work on Tusk itself.
- No iframe, no separate chat, no extra UI, no login asked or stored.
- Unofficial, not affiliated with Tusk or Anthropic.

**What v3 does:**
- Hides Tusk branding, the moved-site banner, footer and Sponsored block.
- Restyles the real Tusk chat to Claude's official UI tokens:
  canvas #faf9f5, soft #f5f0e8, card #efe9de, cream-strong #e8e0d2,
  primary #cc785c, active #a9583e, ink #141413, body #3d3d3a, muted #6c6a64, hairline #e6dfd8.
- Keeps Tusk's own sidebar, composer, model picker and sign-in functional, only recoloured.

**Install, like Better DeepSeek's Load unpacked build:**
1. Download and unzip better-tusk-extension.zip.
2. Chrome / Edge: go to Extensions, turn on Developer mode, Load unpacked, select the unzipped folder.
3. Firefox: use the userscript version (better-tusk.user.js) with Tampermonkey, as Firefox Android does not load unpacked Chrome extensions the same way.
4. Open https://new.tusksearch.com/ai — the skin runs automatically.

**Files:**
- manifest.json — Manifest V3, matches new.tusksearch.com and tuskcentral.ai only.
- content.js — the v3 skin, same code as better-tusk.user.js without the userscript header.
- better-tusk.user.js — Tampermonkey / userscript version for Firefox Android.
