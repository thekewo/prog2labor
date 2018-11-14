#ifndef COMPOSITEPRODUCT_H
#define COMPOSITEPRODUCT_H

#include <vector>
#include <iostream>
#include "product.h"
#include "productFactory.h"

class CompositeProduct: public Product{
    std::vector<Product*> parts;
protected:
    void printParams(std::ostream& os) const;
    void loadParamsFromStream(std::istream& is);
    void writeParamsToStream(std::ostream& os) const;
public:
    CompositeProduct();
    ~CompositeProduct();
    void AddPart(Product* product);
};

CompositeProduct::CompositeProduct():Product() {}

CompositeProduct::~CompositeProduct(){
    for(unsigned int i = 0; i < parts.size(); i++){
        delete parts[i];
    }
    parts.clear();
}

void CompositeProduct::AddPart(Product* product){
    parts.push_back(product);
}

void CompositeProduct::printParams(std::ostream& os) const{
    Product::printParams(os);
    os << std::endl << "Items: " ;
    for(unsigned int i = 0; i < parts.size(); i++){
        os << std::endl << " " << i << ". :";
        parts[i]->Print(os);
    }
}

void CompositeProduct::writeParamsToStream(std::ostream& os) const{
    Product::writeParamsToStream(os);
    os << ' ' << parts.size();

    for(unsigned int i = 0; i < parts.size(); i++)
        os << std::endl << *parts[i];
}

void CompositeProduct::loadParamsFromStream(std::istream& is){
    Product::loadParamsFromStream(is);
    int itemCount;
    is >> itemCount;

    for (int i = 0; i < itemCount; i++){
        Product* product = ProductFactory::GetInstance()->ReadAndCreateProduct(is);
        if(product){
            is >> *product;
            AddPart(product);
        }
    }
}


#endif COMPOSITEPRODUCT_H