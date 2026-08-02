<h1 align="center">✋ Carry Mechanics</h1>

<p align="center"><strong>Pick up, carry, and place blocks and entities with your bare hands!</strong></p>

<br>

---

<br>

<h2>✨ Overview</h2>

<p>Carry Mechanics lets you pick up, carry, and place blocks (with their tile entities) and entities using only your empty hands. Inspired by the classic Carry On mod, rewritten from scratch for modern Fabric.</p>

<br>

<h2>🎯 Features</h2>

<h3>📦 Pick up anything</h3>
<p>Grab blocks with tile entities (chests, furnaces, spawners, etc.) by holding <strong>Shift</strong> + right-clicking with empty hands. Also works on mobs, animals, and other players.</p>

<h3>📍 Place anywhere</h3>
<p>Right-click on any surface to place what you&#8217;re carrying — blocks, entities, or players.</p>

<h3>🗼 Entity stacking</h3>
<p>Stack carried entities on top of others like a totem pole. Configurable stack limits and size checks.</p>

<h3>🏋️ Weight &amp; slowness</h3>
<p>Heavier objects slow you down. You cannot jump while carrying. The weight depends on the object size and NBT data.</p>

<h3>📋 Whitelist / Blacklist</h3>
<p>Control what can be picked up via datapack tags for blocks, entities, and stacking (6 tag types). Supports whitelist or blacklist mode.</p>

<h3>📜 Scripting system</h3>
<p>Datapack-powered JSON scripts with conditions (gamestage, advancement, XP, gamemode) and custom commands on init/loop/place.</p>

<h3>⚙️ Fully configurable</h3>
<p>25+ options including max distance, entity size limits, slowness multipliers, hit prevention, player pickup toggle, and more. Config file: <code>config/carry_mechanics.json</code>.</p>

<h3>💻 Commands</h3>
<p><code>/carrymechanics debug</code> · <code>/carrymechanics clear [player]</code> · <code>/carrymechanics place [player]</code></p>

<br>

<h2>📋 Requirements</h2>
<ul>
<li>Minecraft 26.2</li>
<li>Fabric Loader 0.19.3</li>
<li>Fabric API 0.156.0+26.2</li>
</ul>

<br>

<h2>🎮 How to Use</h2>
<ol>
<li>Hold <strong>Shift</strong> (sneak) and <strong>right-click</strong> on a block or entity with empty hands.</li>
<li>The block or entity is now stored in your carry data.</li>
<li><strong>Right-click</strong> on any surface to place it.</li>
<li>For entities, right-click a carried entity on another entity to stack them.</li>
<li>Check your carry state with <code>/carrymechanics debug</code>.</li>
</ol>

<br>

<h2>📋 Tags</h2>
<p>Use datapack tags to control what can be picked up:</p>
<ul>
<li><code>carry_mechanics:block/block_whitelist</code> / <code>block_blacklist</code></li>
<li><code>carry_mechanics:entity_type/entity_whitelist</code> / <code>entity_blacklist</code></li>
<li><code>carry_mechanics:entity_type/stacking_whitelist</code> / <code>stacking_blacklist</code></li>
</ul>

<br>

<h2>🙏 Credits</h2>
<p>Inspired by <strong>Carry On</strong> by <em>Tschipp</em> and <em>PurpliciousCow</em>. Rewritten for Fabric by <strong>Stalking Dragons</strong>.</p>
