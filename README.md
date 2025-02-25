# System Rezerwacji Wizyt w Salonie Fryzjerskim

## Opis
Aplikacja bazodanowa do zarządzania rezerwacjami w salonie fryzjerskim. System przechowuje informacje o pracownikach (imię, nazwisko, nr telefonu, dostępne i zajęte terminy, specjalizacja, liczba lat doświadczenia) oraz o klientach (płeć, imię, nazwisko, poprzednie zabiegi, nr telefonu jako unikalny sposób identyfikacji). Klienci mają możliwość wyszukiwania dostępnych terminów i zabiegów na podstawie swoich preferencji, takich jak rodzaj zabiegu, fryzjer, czy data.

## Wymagania funkcjonalne
1. **Wyszukiwanie wolnych terminów** – System umożliwia wyszukiwanie wolnych terminów zabiegów przez klientów z uwzględnieniem pożądanych opcji (rodzaj zabiegu, fryzjer, data).
2. **Rejestracja i logowanie** – System umożliwia klientowi rejestrację i logowanie się do systemu.
3. **Rezerwacja wizyt** – Klient ma możliwość dokonania rezerwacji wizyty.
4. **Historia rezerwacji** – Klient może przeglądać historię swoich poprzednich rezerwacji.

## Wymagania niefunkcjonalne
1. **Blokowanie zarezerwowanych terminów** – System automatycznie blokuje terminy zarezerwowane przez jednego klienta, aby uniemożliwić ich ponowną rezerwację przez innych użytkowników.
2. **Unikalny numer telefonu** – Rejestracja klienta wymaga podania unikalnego numeru telefonu, który pełni rolę identyfikatora w systemie.
3. **Logowanie przed działaniami** – Klient może przystąpić do wyboru czynności wyłącznie po zalogowaniu się lub zarejestrowaniu w systemie. Bez spełnienia tego wymogu dostęp do dalszych działań jest zablokowany.
4. **Rezerwacja po wyszukaniu dostępnych terminów** – Rezerwacja zabiegu jest możliwa tylko po wyszukaniu dostępnych terminów wizyty. Użytkownik musi najpierw sprawdzić dostępne sloty czasowe, aby zapobiec kolizji terminów.
