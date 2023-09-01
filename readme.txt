1. Afisare lista cu servicii valabile pentru fiecare specialitate cu preturi, se pot selecta mai multe servicii.
2. Afisare liste de medici cu imagini si punctaje pe fiecare medic.
3. Afisare ore la care se poate realiza programarea.
4. Inregistrare programare.
5. Algoritm de calcul punctaj/medic
6. Interfata de feedback al pacientului pentru a da pucntaj medicului.
7. Adaugare telefon/pacient cu SMS eventual
8. Modificare autentificare in Oauth2 pt ca utilizatorii sa se poata conecta cu contul de Gmail sau alti furnizori de Oauth
9. Modificare date cont
10. Implementare istoric in care pacientul sa poata observa lista programarilor anterioare si sa poata vizualiza scrisoarea medicala.
11. Aranjare dpdv. estetic
12. Testare pe diverse browsere
13. Implementare "responsive", sa vedem daca site-ul se icadreaza bine in telefoanle mobile.
14. Manual si instruire

ng serve --host 0.0.0.0 --ssl  --ssl-cert "C:\Users\victor\Documents\GitHub\Appointments\ssl\server.crt" --ssl-key "C:\Users\victor\Documents\GitHub\Appointments\ssl\server.key" --disable-host-check
ng serve --ssl false --host 0.0.0.0

Ca sa porneasca solutia de programari au nevoie de:
- dispozitivul de aici: https://www.tme.eu/ro/details/mod-usb3g/kituri-pentru-transmisia-de-date/olimex/
- cartela care sa fie montata in dispozitiv, preferabil cu abonament 
- un server ubuntu ultima versiune in care sa monteze dispozitivul
- un certificat digital pentru criptarea traficului https pentru numele de domeniu stabilit de ei, au deja domeniul www.spitalul-radauti.ro si pot stabili ei numele subdomeniului, exemplu: programari.spitalul-radauti.ro.
  certificatul trebuie reinnoit anual.
- rezolvare de nume pentru programari.spitalul-radauti.ro si www.programari.spitalul-radauti.ro catre ip-ul extern al masinii linux pe care au sa o pregateasca
- pe site-ul lor trebie sa puna un link catre programari
- user si parola SSH pt serverul Linux, accesibil doar din LAN, sa fie accesibil de la 192.168.0.30(serverul Caido)
- port forward pe un 2 porturi pe care vor ei sa mearga programarile(catre sereverul linux), ambele vor fi cu https(443 si 444 de exemplu, nu conteaza, depinde ce au ei liber). Un port pt frontend si unul pt backend.
Astia sunt pasi pe care ei trebuie sa-i faca inainte de a publica solutia
