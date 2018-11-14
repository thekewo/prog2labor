#include <iostream>
 
template<class T>
class Auto_ptr4
{
	T* m_ptr;
public:
	Auto_ptr4(T* ptr = nullptr)
		:m_ptr(ptr)
	{
	}
 
	~Auto_ptr4()
	{
		delete m_ptr;
	}
 
	// M�sol� konstrukor
	// M�lym�sol�s a.m_ptr -> m_ptr
	Auto_ptr4(const Auto_ptr4& a)
	{
		m_ptr = new T;
		*m_ptr = *a.m_ptr;
	}
 
	//  Mozgat� konstruktor
	// "Ellopja" a.m_ptr-t �s �tadja m_ptr-nek
	Auto_ptr4(Auto_ptr4&& a)
		: m_ptr(a.m_ptr)
	{
		a.m_ptr = nullptr; // Ki�r�tj�k az a.m_ptr-t, hogy ne mutasson sehov�,
						   // mindig j�l defini�lt �llapotban kell hagyni
	}
 
	// M�sol� �rt�kad�s
	// M�lym�sol�s a.m_ptr -> m_ptr
	Auto_ptr4& operator=(const Auto_ptr4& a)
	{
		// Felismeri ha saj�t mag�t kellene lem�solni
		if (&a == this)
			return *this;
 
		// Biztosan �res legyen az m_ptr
		delete m_ptr;
 
		// Lem�soljuk a forr�st
		m_ptr = new T;
		*m_ptr = *a.m_ptr;
 
		return *this;
	}
 
	// Mozgat� �rt�kad�s
	// "Ellopja" a.m_ptr-t �s �tadja m_ptr-nek
	Auto_ptr4& operator=(Auto_ptr4&& a)
	{
		// Felismeri ha saj�t mag�t kellene lem�solni
		if (&a == this)
			return *this;
 
		// Biztosan �res legyen az m_ptr
		delete m_ptr;
 
		// Hasonl�an a konstuktorhoz
		m_ptr = a.m_ptr;
		a.m_ptr = nullptr; 
 
		return *this;
	}
 
	T& operator*() const { return *m_ptr; }
	T* operator->() const { return m_ptr; }
	bool isNull() const { return m_ptr == nullptr; }
};
 
class Resource
{
public:
	Resource() { std::cout << "Resource acquired\n"; }
	~Resource() { std::cout << "Resource destroyed\n"; }
};
 
Auto_ptr4<Resource> generateResource()
{
	Auto_ptr4<Resource> res(new Resource);
	return res; // mozgat�skonstruktor, mert jobb�rt�k
}
 
int main()
{
	Auto_ptr4<Resource> mainres;
	mainres = generateResource(); // mozgat� �rt�kad�s
 
	return 0;
}
