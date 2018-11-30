#include "compositeproduct.h"
#include "productfactory.h"

using namespace std;

CompositeProduct::CompositeProduct(): Product() {}

CompositeProduct::~CompositeProduct() {
    for(unsigned i = 0; i < parts.size(); i++)
        delete parts[i];
    parts.clear();
}

void CompositeProduct::addPart(Product *product) {
    parts.push_back(product);
}

void CompositeProduct::printParams(std::ostream &os) const {
    Product::printParams(os);
    os << endl << "Items: ";
    for(unsigned i = 0; i < parts.size(); i++) {
        os << endl << " " << i << ". ";
        parts[i]->print(os);
    }
}

void CompositeProduct::writeParamsToStream(std::ostream &os) const {
    Product::writeParamsToStream(os);
    os << ' ' << parts.size();
    for(unsigned i = 0; i < parts.size(); i++)
        os << endl << *parts[i];
}

void CompositeProduct::loadParamsFromStream(std::istream &is) {
    Product::loadParamsFromStream(is);
    int itemCount;
    is >> itemCount;

    for(int i = 0; i < itemCount; i++) {
        Product* product = ProductFactory::getInstance()->readAndCreateProduct(is);
        if(product) {
            is >> *product;
            addPart(product);
        }
    }
}




