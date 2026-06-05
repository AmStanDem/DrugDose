/**
 * Contiene le entità fondamentali del dominio per l'applicazione DrugDose.
 *
 * Questo package definisce il "cuore" logico del sistema, rappresentando i dati
 * del paziente e le specifiche dei farmaci. Tutte le classi sono progettate per essere
 * immutabili e indipendenti da framework esterni, garantendo la massima testabilità
 * e rispetto dei principi della Clean Architecture.
 *
 * Le entità principali sono:
 * - [Patient]: Rappresenta i dati biometrici per il calcolo.
 * - [Drug]: Definisce le regole di dosaggio e i vincoli clinici.
 * - [FormulaType]: Definisce le strategie di calcolo supportate.
 */
package it.uninsubria.drugdose.domain.model
