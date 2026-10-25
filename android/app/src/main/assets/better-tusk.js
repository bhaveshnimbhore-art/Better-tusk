(function () {
  'use strict';
  if (window.top !== window.self) return;
  if (document.getElementById('better-tusk-v3')) return;

  // Official Claude tokens (studied first, then built):
  // canvas #faf9f5, soft #f5f0e8, card #efe9de, cream-strong #e8e0d2,
  // primary #cc785c, active #a9583e, ink #141413, body #3d3d3a,
  // muted #6c6a64, hairline #e6dfd8.
  // Layout: warm cream canvas, soft sidebar, centred chat, plain assistant text,
  // soft user card, rounded composer, coral send.
  var css = [
    ':root{--bt-canvas:#faf9f5;--bt-soft:#f5f0e8;--bt-card:#efe9de;--bt-cream-strong:#e8e0d2;--bt-primary:#cc785c;--bt-active:#a9583e;--bt-ink:#141413;--bt-body:#3d3d3a;--bt-muted:#6c6a64;--bt-hairline:#e6dfd8;}',
    'html,body{background:var(--bt-canvas) !important;color:var(--bt-body) !important;}',
    'body{font-family:system-ui,-apple-system,"Segoe UI",Roboto,Helvetica,Arial,sans-serif !important;}',
    'h1,h2,h3{font-family:Georgia,"Times New Roman",serif !important;color:var(--bt-ink) !important;}',
    'aside,nav,[role="navigation"]{background:var(--bt-soft) !important;border-right:1px solid var(--bt-hairline) !important;}',
    'main,[role="main"]{background:var(--bt-canvas) !important;}',
    'textarea,[contenteditable="true"]{background:#fff !important;border:1px solid var(--bt-hairline) !important;border-radius:24px !important;box-shadow:0 2px 12px rgba(20,20,19,.06) !important;color:var(--bt-ink) !important;}',
    'button{border-radius:999px !important;}',
    'button[aria-label*="Send" i],button[title*="Send" i]{background:var(--bt-primary) !important;color:#fff !important;}',
    '[role="complementary"]{display:none !important;}',
    '.bt-hide{display:none !important;}',
  ].join('\n');
  var style = document.createElement('style');
  style.id = 'better-tusk-v3';
  style.textContent = css;
  (document.head || document.documentElement).appendChild(style);

  // Hide Tusk-visible branding / banner / footer / Sponsored, by text and role, not by class.
  // This runs on the real Tusk page, so chat, composer, model picker and sign-in stay functional.
  function hideTuskVisible() {
    var badTexts = [
      'tuskcentral.ai has moved to new.tusksearch.com',
      'v0.9.78 (Beta)',
      '26 AI models are free and unlimited.',
      '© 2026 Virtual World Computing'
    ];
    var walker = document.createTreeWalker(document.body, NodeFilter.SHOW_TEXT, null);
    var nodes = [];
    var n;
    while ((n = walker.nextNode())) nodes.push(n);
    nodes.forEach(function (t) {
      var txt = (t.nodeValue || '').trim();
      if (!txt) return;
      badTexts.forEach(function (bad) {
        if (txt.indexOf(bad) !== -1 && t.parentElement) {
          // Hide only the banner/footer element, not the whole page.
          var el = t.parentElement;
          if (el && el.textContent && el.textContent.length < 500) el.classList.add('bt-hide');
        }
      });
    });
    // Hide Tusk logo images by accessible name.
    document.querySelectorAll('button[aria-label="Tusk Central AI logo"], img[alt="Tusk Central AI logo"]').forEach(function (el) {
      el.style.visibility = 'hidden';
      el.style.width = '0';
      el.style.height = '0';
    });
    // Hide Sponsored complementary blocks.
    document.querySelectorAll('[role="complementary"]').forEach(function (el) {
      if ((el.getAttribute('aria-label') || '').toLowerCase().indexOf('sponsored') !== -1) el.classList.add('bt-hide');
    });
  }
  if (document.body) hideTuskVisible();
  new MutationObserver(function () { hideTuskVisible(); }).observe(document.documentElement, { childList: true, subtree: true });
  // No extra UI added: no floating button, no drawer, no panel, no story feature.
  // The user sees Claude-style colours and layout on top of Tusk's own chat, which remains the provider.
})();
