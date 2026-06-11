# Riferimento Tecnico - DrugDose

Specifiche tecniche, formule matematiche e strutture dati utilizzate nel progetto.

## 📐 Formule di Calcolo

L'applicazione implementa le seguenti strategie di dosaggio nel file `CalculateDoseUseCase.kt`:

| Strategia (`FormulaType`) | Formula Matematica Applicata | Esempio |
| :--- | :--- | :--- |
| **PER_KG** | $Dose_{tot} = Dose_{unit} \times Peso_{kg}$ | Amoxicillina: 50mg * 10kg = 500mg |
| **PER_BSA** | $Dose_{tot} = Dose_{unit} \times BSA$ | Chemioterapia: 100mg * 1.5m² = 150mg |
| **FIXED** | $Dose_{tot} = Dose_{unit}$ | Cetirizina: 10mg fisso |
| **WEIGHT_RANGE** | Tabella a intervalli (if-else) | Fascia 10-20kg: 50mg |

### Formula BSA (Mosteller)
Per il calcolo della superficie corporea viene utilizzata la formula di Mosteller, lo standard internazionale:
$$BSA (m^2) = \sqrt{\frac{Altezza(cm) \times Peso(kg)}{3600}}$$

---

## 🔒 Vincoli di Sicurezza Clinica

L'app implementa un layer di validazione "fail-fast" che blocca il calcolo se:
1. **Età < MinAge**: Il paziente è più giovane dell'età minima richiesta dal farmaco.
2. **Peso < MinWeight**: Il paziente pesa meno del limite di sicurezza definito nel JSON.

---

## 📦 Schema JSON Assets
Il file `drugs.json` deve seguire questo schema rigoroso:

- `id` (String): Identificativo univoco.
- `name` (String): Nome scientifico.
- `formulaType` (Enum): Una delle strategie elencate sopra.
- `unitDose` (Double): Valore base per il calcolo.
- `unit` (String): mg, μg, ml, etc.
- `minAgeYears` (Int?): Vincolo opzionale.
- `weightRanges` (List?): Richiesto solo se `formulaType` è `WEIGHT_RANGE`.
