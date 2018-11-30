#ifndef HARDDISK_H
#define HARDDISK_H

#include <iostream>
#include "product.h"
#include <ctime>


class HardDisk : public Product{
    int speedRPM;
    static const char CharCode = 'h';
public:
    int GetCurrentPrice() const;
    std::string GetType() const;
    char GetCharCode() const;
};

int HardDisk::GetCurrentPrice() const{
    int ageInDays = GetAge();
    if(ageInDays < 30)
        return initialPrice;
    else if(ageInDays >= 30 && ageInDays < 90)
        return (int)(initialPrice * 0.9);
    else
        return (int)(initialPrice * 0.8);
}

std::string HardDisk::GetType() const{
    return "HardDisk";
}

char HardDisk::GetCharCode() const{
    return CharCode;
}

#endif HARDDISK_H