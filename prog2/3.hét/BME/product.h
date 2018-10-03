//File: product.h
#indef PRODUCT_H
#define PRODUCT_H

#include <iostream>
#include <ctime>

class Product
{
protected:
	int initialPrice; // Beszerz�si �r
	time_t dateOfAcquisition; // Beszerz�si d�tum
	std::string name; // N�v
	virtual void printParams(std::ostream& os) const;
	virtual void loadParamsFromStream(std::istream& is);
	virtual void writeParamsToStream(std::ostream& os) const;
public:
	Product();
	Product(std::string name; int initialPrice, time_t dateOfAcquisition);
	virtual ~Product() {};
	int GetInitialPrice() const;
	std::string GetName() const;
	time_t GetDateOfAcqusition() const;
	int GetAge() const;
	virtual int GetCurrentPrice() const;
	void Print(std::ostream& os) const;
	virtual std::string GetType() const = 0;
	virtual char GetCharCode() const = 0;
	friend std::istream& operator>>(std::istream& is, 	Product& product);
	friend std::ostream& operator<<(std::ostream& os,
	const Product& product);
};
#endif /* PRODUCT_H */