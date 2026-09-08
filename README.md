# Carry Mechanics

Carry Mechanics lets you pick up, carry, and place blocks (with their block entities) and entities using only your empty hands, for Minecraft 1.21.1 (NeoForge).

> This mod is a fork of [Carry On](https://www.curseforge.com/minecraft/mc-mods/carry-on) by Tschipp and PurpliciousCow, licensed under the GNU LGPL v3. Gameplay dynamics differ from Carry On (crouch-to-pick-up behaviour, grab rules, carry conditions, rendering). Not affiliated with or endorsed by the Carry On authors.

## Status

Stable (`1.0.0`). Port to Minecraft 1.21.1 / NeoForge 21.1.249 (Java 21) — the 26.2 fork tree with all 26.2-only Minecraft/NeoForge API reverted to 1.21.1, using upstream Carry On 1.21.1 (v2.2.6) as the API reference. The fork's own dynamics are preserved. `./gradlew build` and `./gradlew runServer` verified; this build has been running server-side in a full modded-server pack.

## Requirements

| Component | Version |
|---|---|
| Minecraft | 1.21.1 |
| NeoForge | 21.1.249+ |
| Java | 21+ |
| Dependencies | None (GameStages optional, for script conditions) |

## Installation

1. Install [NeoForge](https://neoforge.net/) for Minecraft 1.21.1.
2. Download the mod jar and place it in your `mods/` folder (client and server).

## License

GNU LGPL v3.0 — see [LICENSE](LICENSE). Fork of Carry On (Tschipp, PurpliciousCow); the original copyright is retained and the full corresponding source is published in this repository, as the LGPL requires.
