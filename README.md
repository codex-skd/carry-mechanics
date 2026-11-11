# Carry Mechanics

Carry Mechanics lets you pick up, carry, and place blocks (with their block entities) and entities using only your empty hands, for Minecraft 1.20.1 (Forge).

> This mod is a fork of [Carry On](https://www.curseforge.com/minecraft/mc-mods/carry-on) by Tschipp and PurpliciousCow, licensed under the GNU LGPL v3. Gameplay dynamics differ from Carry On (crouch-to-pick-up behaviour, grab rules, carry conditions, rendering). Not affiliated with or endorsed by the Carry On authors.

## Status

Beta (`0.0.0-beta.1`). Port to Minecraft 1.20.1 / MinecraftForge 47.4.23 (Java 17) of the 1.21.1 build (v1.0.1), with full feature parity. Player carry data lives in a Forge capability synced to the carrier and every tracking client; networking uses a Forge `SimpleChannel`; an in-game config screen is available from the Mods menu.

## Requirements

| Component | Version |
|---|---|
| Minecraft | 1.20.1 |
| Forge | 47.4.23+ |
| Java | 17+ |
| Dependencies | None (optional: GameStages for script conditions; First Person Model is supported) |

## Installation

1. Install [Forge](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.20.1.html) for Minecraft 1.20.1.
2. Download the mod jar and place it in your `mods/` folder (client and server).

## Configuration

In `config/carry_mechanics-common.toml` (section `[blocks]`):

- `allowTwoTallBlocks` (boolean, default `true`): allow carrying 2-block-tall vertical pairs.
- `allowLargeBlocks` (boolean, default `false`): allow carrying large blocks (visual shape wider/taller than 1.0 without a vertical pair, or horizontal multi-part pairs like beds). Double chests and fence/wall collision-only height are not treated as large.
- `allowedBlocks` (list of strings): block ids (`"modid:name"`) or whole mods (`"modid:*"`) that are always permitted (overrides `allowTwoTallBlocks`, `allowLargeBlocks`, and datapack blacklist). Does not bypass other safety checks (e.g. unbreakable/locked).
- `blockedBlocks` (list of strings): same syntax, never carried. `blockedBlocks` wins over `allowedBlocks`.

Block permission order: `blockedBlocks` -> `allowedBlocks` -> existing tag/whitelist logic -> two-tall rule -> large rule.

## Commands

Permission level 2:

- `/carrymechanics blacklist add|remove <block>` — edits `blockedBlocks`.
- `/carrymechanics whitelist add|remove <block>` — edits `allowedBlocks`.
- `/carrymechanics blacklist list` / `/carrymechanics whitelist list` — show entries.

`<block>` accepts a block id (`modid:name`) or a namespace wildcard (`modid:*`). Changes are saved immediately and persist across restarts.

## Datapack tags

Blacklists/whitelists are block and entity type tags under the `carry_mechanics` namespace. On 1.20.1 they live in `data/carry_mechanics/tags/blocks/` and `data/carry_mechanics/tags/entity_types/`.

## License

GNU LGPL v3.0 — see [LICENSE](LICENSE). Fork of Carry On (Tschipp, PurpliciousCow); the original copyright is retained and the full corresponding source is published in this repository, as the LGPL requires.
