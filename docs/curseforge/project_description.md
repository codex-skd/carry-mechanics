<h1 align="center">&#9995; Carry Mechanics</h1>

<p align="center"><strong>Pick up, carry, and place blocks and entities with your bare hands.</strong></p>

<p align="center">
<img src="https://img.shields.io/badge/loader-NeoForge-orange?style=plastic&logo=curseforge" alt="NeoForge">
<img src="https://img.shields.io/badge/minecraft-26.2%20%7C%201.21.1-blue?style=plastic" alt="Minecraft 26.2 and 1.21.1">
<img src="https://img.shields.io/badge/side-client%20%2B%20server-brightgreen?style=plastic" alt="Client and Server">
<img src="https://img.shields.io/badge/license-LGPL--3.0-lightgrey?style=plastic" alt="LGPL-3.0">
</p>

<br>

---

<br>

<h2>&#10024; Overview</h2>

<table>
<tr>
<td width="65%">
<p>Carry Mechanics lets you pick up, carry, and place blocks (with their block entities &mdash; chests, furnaces, spawners&hellip;) and entities using only your empty hands. Heavier objects slow you down, you can't jump while carrying, and datapack tags control exactly what is grabbable.</p>

<p>A fork of <a href="https://www.curseforge.com/minecraft/mc-mods/carry-on"><strong>Carry On</strong></a> by <em>Tschipp</em> and <em>PurpliciousCow</em>, rebranded and reworked by <strong>Stalking Dragons</strong>. Distributed under the same license (LGPL-3.0). Gameplay dynamics differ from Carry On &mdash; the crouch-to-pick-up behaviour, grab rules, carry conditions and rendering are modified. Not affiliated with or endorsed by the Carry On authors.</p>
</td>
<td width="35%" align="center">
<a href="https://codex.skdragons.com/" target="_blank"><img src="https://node-files.skdragons.com/uploads/MINECRAFT/Codex/logo_codex_stalking_dragons.png" alt="Codex Stalking Dragons" width="160"></a>
</td>
</tr>
</table>

<br>

<h2>&#127919; Features</h2>

<h3>&#128230; Pick up anything</h3>
<p>Grab blocks with block entities by holding <strong>Sneak</strong> and right-clicking with empty hands. Also works on mobs, animals, and other players.</p>

<h3>&#128205; Place anywhere</h3>
<p>Right-click on any surface to place what you're carrying &mdash; blocks, entities, or players.</p>

<h3>&#128507;&#65039; Entity stacking</h3>
<p>Stack carried entities on top of others like a totem pole. Configurable stack limits and size checks.</p>

<h3>&#127947;&#65039; Weight &amp; slowness</h3>
<p>Heavier objects slow you down and block jumping. Weight depends on object size and NBT.</p>

<h3>&#128203; Whitelist / Blacklist</h3>
<p>Control what can be picked up via datapack tags for blocks, entities and stacking (six tag types), in whitelist or blacklist mode.</p>

<h3>&#128220; Scripting system</h3>
<p>Datapack-powered JSON scripts with conditions (game stage, advancement, XP, gamemode) and custom commands on init / loop / place.</p>

<h3>&#9881;&#65039; Fully configurable</h3>
<p>25+ options: max distance, entity size limits, slowness multipliers, hit prevention, player-pickup toggle and more.</p>

<h3>&#128187; Commands</h3>
<ul>
<li><code>/carrymechanics debug</code> &mdash; show current carry state</li>
<li><code>/carrymechanics clear [player]</code> &mdash; clear carry data</li>
<li><code>/carrymechanics place [player]</code> &mdash; place the carried object</li>
</ul>

<br>

<h2>&#129521; Mod Structure</h2>

<table>
<tr><th align="left">Area</th><th align="left">What it provides</th></tr>
<tr><td><code>carry</code></td><td>The carry-data model, per-player storage and sync, and the pickup / placement logic.</td></tr>
<tr><td><code>pickupcondition</code> / <code>scripting</code></td><td>The datapack-driven pickup conditions and the JSON script engine.</td></tr>
<tr><td><code>client/render</code></td><td>Rendering the carried block / entity on the player and in first person.</td></tr>
<tr><td><code>mixin</code></td><td>The vanilla patches for pickup interaction, movement penalties, inventory locking and the carried-object render layer.</td></tr>
<tr><td><code>compat</code></td><td>Optional GameStages integration for script conditions.</td></tr>
</table>

<br>

<h2>&#128203; Requirements</h2>

<table>
<tr><td><strong>Minecraft / NeoForge / Java</strong></td><td>see <em>Available Versions</em> below</td></tr>
<tr><td><strong>Dependencies</strong></td><td>None</td></tr>
<tr><td><strong>Side</strong></td><td>Client and Server (required on both)</td></tr>
</table>

<br>

<h2>&#128230; Available Versions</h2>

<table>
<tr><th align="left">Minecraft</th><th align="left">NeoForge</th><th align="left">Java</th><th align="left">Latest build</th><th align="left">Status</th></tr>
<tr><td>26.2</td><td>26.2.0.57+</td><td>25</td><td><code>1.1.0</code></td><td>Stable</td></tr>
<tr><td>1.21.1</td><td>21.1.249+</td><td>21</td><td><code>1.0.0</code></td><td>Stable</td></tr>
</table>

<p><em>Both versions share this CurseForge project. Pick the file that matches your Minecraft version.</em></p>

<br>

<h2>&#127918; How to Use</h2>

<ol>
<li>Hold <strong>Sneak</strong> and <strong>right-click</strong> a block or entity with empty hands.</li>
<li>It is now stored in your carry data.</li>
<li><strong>Right-click</strong> any surface to place it; right-click a carried entity on another entity to stack them.</li>
<li>Check your state with <code>/carrymechanics debug</code>.</li>
</ol>

<br>

---

<br>

<h2>&#128591; Credits &amp; License</h2>

<p>Carry Mechanics is a fork of <a href="https://www.curseforge.com/minecraft/mc-mods/carry-on">Carry On</a> by <strong>Tschipp</strong> and <strong>PurpliciousCow</strong>, rebranded and reworked by <strong>Stalking Dragons</strong>.</p>

<p><strong>License:</strong> <strong>GNU LGPL v3.0</strong> (LGPL-3.0-or-later), the same license as Carry On. Under its copyleft terms this fork stays under the LGPL, keeps the original Carry On copyright, and its complete corresponding source is published at <a href="https://gitlab.com/stalking-dragons/minecraft/carry-mechanics">gitlab.com/stalking-dragons/minecraft/carry-mechanics</a>. The full licence text ships in the jar and the repository <code>LICENSE</code> file.</p>

<br>
<br>

<p align="center">
  <a href="https://codex.skdragons.com/" target="_blank">
    <img src="https://node-files.skdragons.com/uploads/MINECRAFT/Codex/logo_codex_stalking_dragons.png" alt="Codex Stalking Dragons" width="200">
  </a>
  <br>
  <a href="https://codex.skdragons.com/">https://codex.skdragons.com/</a>
  <br>
  <em>Codex Stalking Dragons &mdash; Minecraft Modding</em>
</p>
