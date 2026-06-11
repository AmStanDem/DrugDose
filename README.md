# DrugDose 💊
> Calcolatore di dosaggi clinici professionale con focus sulla sicurezza pediatrica.

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blue.svg)](https://kotlinlang.org)
[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://android.com)
[![Architecture](https://img.shields.io/badge/Architecture-Clean%20%2B%20MVVM-orange.svg)](./docs/ARCHITECTURE.md)

**DrugDose** è un'applicazione Android professionale progettata per supportare il personale sanitario nel calcolo preciso dei dosaggi farmacologici. L'app mette al primo posto la **sicurezza clinica** attraverso un sistema rigido di validazione basato su parametri bioscientifici.

---

## 📚 Documentazione (Framework Diátaxis)
Per una navigazione ottimale tra le informazioni del progetto, scegli il documento più adatto alle tue esigenze:

- 📖 **[Guida all'Uso](./docs/TUTORIAL.md)**: Impara a usare l'app ed eseguire il tuo primo calcolo pediatrico.
- 🛠️ **[Guida per Sviluppatori](./docs/HOWTO.md)**: Istruzioni su come estendere il database farmaci, cambiare il tema o compilare il codice.
- 🏛️ **[Architettura di Sistema](./docs/ARCHITECTURE.md)**: Spiegazione approfondita del design pattern (Clean Architecture), Dependency Injection e gestione dello stato.
- 📋 **[Riferimento Tecnico](./docs/REFERENCE.md)**: Dettagli sulle formule matematiche utilizzate (Mosteller BSA), vincoli di sicurezza e schema dati.

---

## 🚀 Caratteristiche Salienti
- **Validazione Clinica**: Blocco automatico di dosaggi per pazienti sotto l'età o il peso minimo di sicurezza.
- **Supporto Multi-Formula**: Calcoli per Peso (mg/kg), Superficie Corporea (BSA), Dosi Fisse e Tabelle a fasce.
- **UI Material 3**: Interfaccia moderna, accessibile (WCAG AAA) e ottimizzata per scenari clinici ad alta intensità.
- **i18n Ready**: Sistema di internazionalizzazione avanzato (`UiText`) per supporto multilingua totale.

---

## 🛠️ Tecnologie Utilizzate
- **Jetpack Compose**: UI dichiarativa e reattiva.
- **Room Database**: Persistenza locale dei dati clinici.
- **Hilt (Dagger)**: Dependency Injection di livello industriale.
- **Kotlin Flow & Coroutines**: Gestione asincrona dei dati in tempo reale.
- **Dokka**: Generazione di documentazione tecnica automatizzata.

---

## 👨‍💻 Autore
**Thomas Riotto**  
*Università degli Studi dell'Insubria*  
Corso di Programmazione di dispositivi mobili- A.A. 2025/2026

---
> [!IMPORTANT]
> **Disclaimer**: Questa applicazione è un prototipo a scopo didattico. Prima di ogni utilizzo in contesti clinici reali, verificare sempre i dosaggi con i manuali ufficiali (AIFA, BNF, WHO).
