#ifndef PRODUCTFACTORY_H
#define PRODUCTFACTORY_H

#include <iostream>
#include "product.h"


class ProductFactory{
    static ProductFactory* instance;
public:
    virtual ~ProductFactory(){}
    virtual Product* CreateProduct(char typeCode) const = 0;
    static ProductFactory* GetInstance();
    static void Init(ProductFactory* pf);
    Product* ReadAndCreateProduct(std::istream& is);
    virtual Product* CreateProduct(char typeCode) const = 0;
};

Product* ProductFactory::ReadAndCreateProduct(std::istream& is){
    if(!is.good())
        return NULL;

    char typeCode;
    is >> typeCode;

    if(!is.good()){
        if(is.eof())
            return NULL;
        std::cout << "There was an error reading the product items" << std::endl;
        return NULL;
    }

    Product* product = CreateProduct(typeCode);
    if(product == NULL){
        std::cout << "Unknown product type." << std::endl;
    }

    return product;
}

ProductFactory* ProductFactory::instance = NULL;

void ProductFactory::Init(ProductFactory* pf){
    instance = pf;
}

ProductFactory* ProductFactory::GetInstance(){
    return instance;
}
#endif PRODUCTFACTORY_H
