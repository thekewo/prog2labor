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
 
	// Másoló konstrukor
	// Mélymásolás a.m_ptr -> m_ptr
	Auto_ptr4(const Auto_ptr4& a)
	{
		m_ptr = new T;
		*m_ptr = *a.m_ptr;
	}
 
	//  Mozgató konstruktor
	// "Ellopja" a.m_ptr-t és átadja m_ptr-nek
	Auto_ptr4(Auto_ptr4&& a)
		: m_ptr(a.m_ptr)
	{
		a.m_ptr = nullptr; // Kiürítjük az a.m_ptr-t, hogy ne mutasson sehová,
						   // mindig jól definiált állapotban kell hagyni
	}
 
	// Másoló értékadás
	// Mélymásolás a.m_ptr -> m_ptr
	Auto_ptr4& operator=(const Auto_ptr4& a)
	{
		// Felismeri ha saját magát kellene lemásolni
		if (&a == this)
			return *this;
 
		// Biztosan üres legyen az m_ptr
		delete m_ptr;
 
		// Lemásoljuk a forrást
		m_ptr = new T;
		*m_ptr = *a.m_ptr;
 
		return *this;
	}
 
	// Mozgató értékadás
	// "Ellopja" a.m_ptr-t és átadja m_ptr-nek
	Auto_ptr4& operator=(Auto_ptr4&& a)
	{
		// Felismeri ha saját magát kellene lemásolni
		if (&a == this)
			return *this;
 
		// Biztosan üres legyen az m_ptr
		delete m_ptr;
 
		// Hasonlóan a konstuktorhoz
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
	return res; // mozgatóskonstruktor, mert jobbérték
}
 
int main()
{
	Auto_ptr4<Resource> mainres;
	mainres = generateResource(); // mozgató értékadás
 
	return 0;
}
