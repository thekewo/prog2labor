//Forrás: Bátfai Norbert - High Level Programming Languages 2, C++11, Allocators
//Allokátorok - Prezis előadása alapján
//Készítette: Holozsnyák Nándor Kristóf
#include <iostream>
#include <exception>
#include <iostream>
#include <cxxabi.h>
#include <sys/types.h>
#include <sys/shm.h>
#include <sys/stat.h>
#include <vector>
#include <unistd.h>

//fordítani g++ shm.cpp -o shmp -std=c++11
//Arena osztály deklarásláa
class Arena{
	char *p;
	int s;

public:

	char *q;
	int nnn{0};
	Arena(char *p, int s) : p(p), s(s){
		q = p+100;
		
	}
};

	int tomb1[4]={1,2,3,4};
	int (&tomb2)[4]=tomb1;

//CustomAlloc struktúra, ami bármilyen típussal képes dolgozni
template<typename T>
struct CustomAlloc{

	using size_type = size_t;
	using value_type = T;
	using pointer = T*;
	using const_pointer = const T*;
	using reference = T&;
	using const_reference = const T&;
	using difference_type = ptrdiff_t;

	Arena & arena;
	CustomAlloc( Arena & arena): arena (arena){

	}
	//allocate függvényünk ami a nyomkövetési céllel illetve az allokálással kapcsolatos dolgokat intézi számunkra
	pointer allocate (size_type n) {
		++arena.nnn;
		int s;
		char * p = abi::__cxa_demangle (typeid(tomb2).name(),0,0,&s);

		std::cout << "Allocating "
				  << n << " object of "
				  << n*sizeof(tomb2) 
				  << "  bytes. "
				  << typeid(tomb2).name() << " = " << p 
				  << " hívások száma " << arena.nnn
				  << std::endl;
		free (p);
	
		char *q = arena.q;

		std::cout << "q " << static_cast<const void *> (q) << std::endl;

		arena.q += n*sizeof(T);
		//std::cout << "q + n*sizeof(T) " << static_cast<const void *> (q) << std::endl;
		return reinterpret_cast<tomb2*> (q);
	}
	//deallocate ami a deallokálást végzni, itt nálunk csak deklarálni kell a program futásához
	void  deallocate (pointer p, size_type n) {
		p -= n * sizeof ( T );
};

int main()
{
	//osztott memória címen nekünk ez a változónk fogja a már lefoglalt memória ID-jét tartalmazni
	int osztott_memoria;
	//mutató az osztott memória terülenütknara
	char *osztott_memoria_terulet;


	//memória ellenörzés
	//itt kapja meg az osztott_memoria változó az osztott memórájának az ID-jét.
	//manual alapján az shmget függvény:
	// shmget - allocates a System V shared memory segment
	// 3 db argumentauma van az első egy kulcs ami nekünk egy függvény által visszaadott érték
	// 1 - ftok - convert a pathname and a project identifier to a System V IPC key - egyszerűen egy kulcsot generál nekünk egy elérési útból
	// 2 - a lefoglalandó memória mérete... jelen esetben 10mb lesz
	// 3 - flagek, ide jönnek a memória "tulajdonságai" úgymond.
	// IPC_CREAT - egy új szegmenst hoz létre, ha nincs ez a flag használva akkor az shmget megkeresi azt a helyet ami ezzel a kulccsal van párosítva úgymond
	// és ha van a felhasználónak megfelelő joga akkor engedélyezi 
	if( ( osztott_memoria = shmget(ftok(".",10),1024*10*1024,IPC_CREAT|S_IRUSR|S_IWUSR)) == -1)
	{
		perror("shmget");
		exit(EXIT_FAILURE);
	}

	std::cout << "Osztott memoria ID: " << osztott_memoria << std::endl;
	//shmat - shared memory attach -  System V shared memory operations
	// 3 argumentuma van neki is

	// 1 - shmid, hozzárendeli az adott ID-hez tartozó memóriarészt amit az shmget függvénny ad vissza.
	// 2 - shmaddr - a hozzácsatolandó memóriacím, ha NULL akkor a rendszer választja ki a megfelelő még nem használt memórirészt
	// 3 - shmflag - olvasási vagy hozzáférési részre kérjük
	// mivel az shmat egy void* "visszatérésű" ezért kényszerítenünk kell a char* - ra jelen esetben 
	if( ( osztott_memoria_terulet = 
				  (char*) shmat (osztott_memoria,NULL,0) ) < 0 )
	{
		perror("shmat");
		exit(EXIT_FAILURE);
	}
	//std::cout << "Oszott memoria terulete: " << &osztott_memoria_terulet << std::endl;

	//arena néven egy Arena típusú változót hozunk létre aminek átadjuk az osztott_memoria_terulet mutatónkat
	Arena arena {osztott_memoria_terulet,10*1024*1024};
	//ezt már csak úgy hozzáadtam hogy tesztelgethessem.
	Arena arena_1 {osztott_memoria_terulet,10*1024*1024};
	//csak egyszerűen kiíratjuk a memória címét egy castolás után
	std::cout << "oszott " << static_cast<const void *> (osztott_memoria_terulet) << std::endl;
	//ezzel a deklarációval tudjuk majd később meghívnunk az arena változónk allocate függvényét, allocobj alias néven
	CustomAlloc<int> allocobj {arena};
	//itt már nekünk egy double típusú lesz.
	CustomAlloc<double> allocale {arena_1};
	//ezzel a változóval fogunk fork()-olni
	int gyerkmek_pid;
	//egy aliast hozunk létre
	using TP = std::vector<int, CustomAlloc<int> > ;
	//forkolunk
	if((gyerkmek_pid = fork()) == 0) {
		//itt folytatódik a vezérlés a gyerek folyamatban
		//kicsit több változónk van itt jelenleg a placement new függvényünk fog lefutni
		//ezzel elérvén hogy ne új területet foglaljuk a memóriában hanem egy már előre lefoglalt
		//most az osztott_memoria_területen allokáljunk memóriát a következő változónk számára

		TP* v = new (osztott_memoria_terulet) TP {allocobj};
		TP* l = new (osztott_memoria_terulet) TP {allocobj};
		TP* k = new (osztott_memoria_terulet) TP {allocobj};
		//itt fog lefutni az allocate úgymond mert itt rakunk a
		//konténerünkbe elemeket
		v->push_back(42);
		v->push_back(43);
		v->push_back(44);
		v->push_back(45);
		v->push_back(46);
		v->push_back(47);
		v->push_back(48);
		v->push_back(49);
		v->push_back(50);
		v->push_back(51);
		v->push_back(52);
		l->push_back(1);
		k->push_back(100);

		//csak címeket iratunk ki
		std::cout << "v " << static_cast<const void *> (v) << std::endl;
		std::cout << "o " << static_cast<const void *> (osztott_memoria_terulet) << std::endl;
		//itt pedig szépen leválasztjuk az osztott memória szegmensünket
		if( shmdt(osztott_memoria_terulet) == -1) {
			perror("shmdt");
			exit(EXIT_FAILURE);
		}

		exit(EXIT_SUCCESS);
	} 

	else if (gyerkmek_pid > 0) {
		//itt pedig a szülő folyamat
		//egy kis sleep-et belerakunk
		sleep(1);
		//készítünk egy mutatót ami az osztott memória területünkre mutat majd hogy az ott allokált
		//adatainkat ki tudjuk iratni vagy később dolgozni tudjuk velük
		TP *vp = (TP*) osztott_memoria_terulet;
	
		std::cout << "0 " << static_cast<const void *> (vp) << std::endl;
		//az első három elemet itt irattuk ki elsőnek, de most kommentben lesz
		//std::cout << (*vp)[0] << std::endl;
		//std::cout << (*vp)[1] << std::endl;
		//std::cout << (*vp)[2] << std::endl;
		//egy for cikluassal átmegyünk az elemeken

		for(int i : *vp){

			std::cout << &i << " - " << i << std::endl;
		}
		//itt megint ha tudjuk akkor leválasztjuk a szegmenst
		if (shmdt(osztott_memoria_terulet) == -1)
		{
			
			perror("shmdt");
		    exit(EXIT_FAILURE);
		}

	}
	

	return 0;
}

