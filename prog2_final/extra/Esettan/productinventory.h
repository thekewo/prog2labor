#ifndef PRODUCTINVENTORY_H
#define PRODUCTINVENTORY_H

#include <vector>
#include <iostream>
#include "product.h"

class ProductInventory {
    void emptyProdcuts();
protected:
    std::vector<Product*> products;
public:
    virtual ~ProductInventory();
    void readInventory(std::istream& is);
    void writeInventory(std::ostream& os) const;
    void printProducts(std::ostream& os) const;
    void addProduct(Product* product);
};

#endif // PRODUCTINVENTORY_H
