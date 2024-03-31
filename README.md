# tema2
BOGDAN ELENA CATALINA
331 CC

    Pentru realizarea acestui proiect a fost necesara actualizarea claselor
    MyHost si MyDispatcher, in care am explicitat metodele abstracte ale 
    claselor parinte.

    Planificarea algoritmilor de distribuire a taskurilor catre noduri se
    realizeaza in clasa MyDispatcher, unde se diferentiaza 4 tipuri de algoritmi:
        ROUNTD_ROBIN - trimite taskuri pe rand de la primul la ultimul nod
        SHORTEST_QUEUE - alege nodul cu cele mai putine taskuri in coada si in
                        executie cu ajutorul metodei getQueueSize()
        SIZE_INTERVAL_TASK_ASSIGNMENT - distribuie taskurile in functie de tipul
                        acestora catre nodurile specifice
        LEAST WORK LEFT - trimite taskuri nodului care termina executia cel mai
                        curand, cu ajutorul metodei getWorkLeft()
    
    Pentru a tine in evidenta taskurile si a realiza o sincronizare automata 
    este folosita o coada blocanta de prioritati, ce se construieste pe baza 
    unui comparator : aseaza in coada taskurile descrescator in functie de
    prioritati si crescator dupa start time in cazul in care au prioritati egale.

    Metoda addTask asigura adaugarea in coada si setarea timpului ramas de parcurs
    pentru fiecare task ca fiind durata totala a acestuia.
    In metoda run se creeaza o bucla infinita in functie de constanta runnable, 
    variabila de tip bool, setata true, ce este marcata ca fiind falsa in shutdown.

    Sunt extrase taskuri din coada si executate in functie de caracteristicele
    acestora : preemptibilitate. Daca un task nu este preemptibil atunci executarea
    lui nu poate fi intrerupta, asa ca Threadul asteapta periodic cat timp nu s-a 
    consumat tot timpul necesar finalizarii.

    Pe de alta parte, daca are setat acest camp ca fiind true, dupa fiecare incheiere
    a perioadei de sleep este verificat daca a fost adaugat in coada un task cu 
    prioritate mai mare decat a celui curent, caz in care se este pusa pe pauza 
    executia lui, este adaugat in coada si incepe executia taskului de prioritate
    superioara.

    