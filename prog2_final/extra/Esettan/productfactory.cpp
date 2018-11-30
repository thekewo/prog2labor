#include "productfactory.h"

using namespace std;

ProductFactory* ProductFactory::instance = NULL;

void ProductFactory::Init(ProductFactory* pf) {
    instance = pf;
}

ProductFactory* ProductFactory::getInstance() {
    return instance;
}

Product* ProductFactory::readAndCreateProduct(std::istream &is) {
    if (!is.good())
        return NULL;

    char typeCode;
    is >> typeCode;

    if (!is.good()){
        if(is.eof()) return NULL;

        cout << "There was an error reading the product items" << endl;

        return NULL;
    }

    Product* product = createProduct(typeCode);
    if(product == NULL) {
        cout << "Unknown product tpye." << endl;
    }
    return product;
}
