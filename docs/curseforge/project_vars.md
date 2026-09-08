# Project Variables — Carry Mechanics (1.21.1)

> **Rama 1.21.1**: `game_versions = 9638, 9639, 11779, 10150` (Client, Server, **1.21.1** id `11779`, NeoForge). `release_type = release`. JAR `carry_mechanics-1.21.1-neoforge-21.1.249-<version>.jar`. Tag `1.21.1-neoforge-<version>`. Proyecto CurseForge compartido con la rama 26.2 (`1608286`).

## CurseForge

| Variable | Valor |
|----------|-------|
| Project ID | `1608286` |
| Upload Token | `ee776b0a-ee95-4850-b554-06be02a8657f` |
| Core API GET | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` |

## Variables para script (lectura automática)

project_id = 1608286
api_token = ee776b0a-ee95-4850-b554-06be02a8657f
release_type = release
game_versions = 9638, 9639, 11779, 10150
relations =

El script lee `project_id`, `api_token` y `game_versions` de este archivo, y `mod_id`, `mod_name`,
`minecraft_version`, `mod_version` de `gradle.properties`. Sube el JAR desde `build/libs/` con el
changelog de `docs/curseforge/versions/<version>.md`.

## Nota post-subida (manual)

El API no expone client/server ni la licencia. Tras subir, editar el archivo en la web →
entorno **Client & Server**. Verificar que el selector de licencia del proyecto está en
**GNU Lesser General Public License version 3 (LGPLv3)** — NO en "All Rights Reserved" ni MIT.

## Rama
minecraft/1.21.1/neoforge-21.1.249/production

## Tag
Formato: `<mc-version>-<framework>-<version>` — Ejemplo: `1.21.1-neoforge-0.0.0-beta.1`

## Repo GitLab
https://gitlab.com/stalking-dragons/minecraft/carry-mechanics.git
