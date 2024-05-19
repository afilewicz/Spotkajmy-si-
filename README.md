# Spotkajmy się

## Instalacja

Aby użyć biblioteki Gson, wykonaj następujące kroki:

    Otwórz projekt w IntelliJ IDEA.
    Otwórz ustawienia struktury projektu:
        Przejdź do File > Project Structure... > Global Libraries.
        Kliknij + > From Maven.
    Dodaj Gson z repozytorium Maven:
        W polu wyszukiwania wpisz com.google.code.gson:gson:2.10.1.
        Kliknij OK, aby pobrać i dodać bibliotekę do projektu.

## Opis
Algorytm na podstawie kalendarzy dwóch osób oraz oczekiwanej długości spotkania przedstawia propozycję możliwych terminów spotkań.

Program przyjmuje na wejściu:
- czas twania spotkania
- kalendarz (w formacie json):
  - godziny pracy (początek, koniec)
  - lista spotkań (początek, koniec)

Przykładowe dane znajdują się w pliku JsonData

Wynikiem programu są zakresy, w których można organizować spotkania (o długości niemniejszej niż podany czas spotkania)

## Biblioteki zewnętrzne:
gson - do odczytywania danych w formacie JSON
