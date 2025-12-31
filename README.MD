# Android Client

Android application for interacting with the IR Bridge (ESP32).  
The app connects to the device via BLE, receives captured IR frames, allows previewing and validating them, and saves them as remotes and commands.

## Purpose

- Connect to the IR Bridge over Bluetooth Low Energy
- Receive IR frames as an array of durations (`IntArray`)
- Visualize and validate captured commands
- Store commands in a local database
- Re-transmit IR commands via the connected device

## Architecture

The project is designed with a focus on modularity and testability:

- **UI** — Jetpack Compose
- **State management** — ViewModel + Flow
- **DI** — Koin
- **BLE** — custom BLE client with subscription to a notify characteristic
- **Persistence** — Room
- **Serialization** — Kotlinx Serialization (for command export/import)

## Main Modules

- `ble` — BLE connectivity, MTU handling, packet reception
- `ir` — IR frame models, normalization and duration rounding
- `remotes` — remotes and commands domain logic
- `ui` — Compose screens and UI components
- `data` — repositories and local storage

## IR Data Format

An IR frame is represented as an array of durations (in microseconds):

```kotlin
IntArray
```
Before saving, duration normalization (rounding to the nearest step) may be applied to improve repeatability and stability.

## Requirements
- Android 9.0+ (API 28+)
- BLE support
- - Permissions:
- - BLUETOOTH
- - BLUETOOTH_ADMIN
- - BLUETOOTH_CONNECT
- - BLUETOOTH_SCAN
- - ACCESS_COARSE_LOCATION
- - ACCESS_FINE_LOCATION
- - TRANSMIT_IR

## Build and Run

- Open the project in Android Studio
- Sync Gradle
- Run on a physical device with BLE support
- Power on the IR Bridge and wait for the connection

## Limitations and Assumptions

The application does not store IR codes on the ESP32 side — the device acts solely as a capture/emit bridge

The transport protocol is a notify packet stream and does not guarantee delivery outside an active connection

IR command repeatability depends on ESP32 timing accuracy and the chosen rounding step

## Status

The project is under active development.
The API and internal structure are subject to change.

## Links
[ESP32-C3 firmware](https://github.com/valerii-fr/ESP32-C3_IR_BLE_receiver)