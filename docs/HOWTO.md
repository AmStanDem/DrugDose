# Guide per Sviluppatori - DrugDose

In questa sezione troverai guide pratiche per estendere o manutenere l'applicazione.

## ➕ Aggiungere un nuovo Farmaco
Il database è popolato tramite il file `app/src/main/assets/drugs.json`. Per aggiungere un farmaco:
1. Apri il file JSON.
2. Aggiungi un nuovo oggetto seguendo questo schema:
```json
{
  "id": "nome_farmaco_001",
  "name": "Nome Farmaco",
  "indication": "Indicazione clinica",
  "formulaType": "PER_KG", // O FIXED, PER_BSA, WEIGHT_RANGE
  "unitDose": 10.0,
  "unit": "mg",
  "minAgeYears": 6, // Opzionale
  "alerts": ["Avviso 1", "Avviso 2"]
}
```
3. Reinstalla l'app o pulisci i dati per forzare il ricaricamento del database.

## 🎨 Cambiare l'Identità Visiva (Colori)
Se desideri modificare i colori del tema "Medical Blue":
1. Vai in `it.uninsubria.drugdose.ui.theme.Color.kt`.
2. Modifica i valori HEX dei colori primari (es. `MedicalBlue40`).
3. Il tema Material 3 ricalcolerà automaticamente i colori dei contenitori e dei testi per mantenere l'accessibilità.

## 📖 Generare la Documentazione Tecnica
L'app utilizza **Dokka** per generare documentazione navigabile dal codice sorgente:
1. Apri il terminale nella root del progetto.
2. Esegui:
```bash
./gradlew :app:dokkaGenerateHtml
```
3. Troverai i file HTML in `app/build/dokka/html/index.html`.

## 🧪 Eseguire i Test Unitari
Per verificare che la logica di calcolo sia corretta dopo una modifica:
1. Esegui il comando:
```bash
./gradlew test
```
2. I risultati confermeranno se i vincoli di sicurezza (età/peso) sono ancora integri.
