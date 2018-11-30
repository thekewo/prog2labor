#include "productinventory.h"
#include "productfactory.h"

using namespace std;

ProductInventory::~ProductInventory() {
    emptyProdcuts();
}

void ProductInventory::emptyProdcuts() {
    for(unsigned i = 0; i < products.size(); ++i) {
        delete products[i];
    }

    products.clear();
}

void ProductInventory::printProducts(std::ostream& os) const {
    for(unsigned i = 0; i < products.size(); ++i) {
        os << i << ".: ";
        products[i]->print(os);
        os << endl;
    }
}

void ProductInventory::readInventory(std::istream &is) {
    is >> ws;
    while(is.good()) {
        Product* product = ProductFactory::getInstance()->readAndCreateProduct(is);

        if(product) {
            is >> *product;
            addProduct(product);
        }
    }

    cout << "End of reading product items.";
}

void ProductInventory::writeInventory(std::ostream &os) const {
    for(unsigned i = 0; i < products.size(); ++i)
        os << *products[i] << endl;
}

void ProductInventory::addProduct(Product *product) {
    if(product == NULL)
        throw invalid_argument("ProductInventory::AddProduct - The product parameter can not be null.");
    products.push_back(product);
}
