# Project Variables — Carry Mechanics

<!--
  Este archivo contiene las variables específicas del proyecto para CurseForge.
  Se usa como referencia al subir versiones. No contiene secrets directamente,
  solo referencias a los tokens que están en el gestor de contraseñas.
-->

## CurseForge

| Variable | Valor |
|----------|-------|
| Project ID | `1608286` |
| Upload Token | `ee776b0a-ee95-4850-b554-06be02a8657f` |
| Core API GET | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` |

## Game Versions (IDs)

| Recurso | ID |
|---------|----|
| Minecraft 26.1.2 | `16082` |
| Client | `9638` |
| Server | `9639` |
| NeoForge | `10150` |

## Versiones

| Recurso | Versión |
|---------|---------|
| Minecraft | `26.1.2` |
| NeoForge | `26.1.2.78` |
| Java | `25` |
| Mod loader | `neoforge` |

## Mod

| Variable | Valor |
|----------|-------|
| mod_id | `carry_mechanics` |
| display_name | `Carry Mechanics` |
| mod_group_id | `com.skd.carrymechanics` |
| package_path | `com/skd/carrymechanics` |

## Rama

| Variable | Valor |
|----------|-------|
| branch | `minecraft/26.1.2/neoforge-26.1.2.78/production` |
| tag_prefix | `26.1.2-neoforge-` |

## Token de subida (Python)

```python
# Ejemplo de uso con requests
import requests

UPLOAD_TOKEN = "ee776b0a-ee95-4850-b554-06be02a8657f"
PROJECT_ID = 1608286
headers = {
    "X-Api-Tokens": UPLOAD_TOKEN
}
```
